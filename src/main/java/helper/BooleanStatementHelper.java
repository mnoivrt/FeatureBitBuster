package helper;

import com.github.javaparser.ast.expr.*;

public class BooleanStatementHelper {

    private final FeatureBitDetector featureBitDetector;

    public BooleanStatementHelper(FeatureBitDetector featureBitDetector) {
        this.featureBitDetector = featureBitDetector;
    }

    public Expression eval(Expression expression, String fbName) {
        return eval(expression, fbName, 0);
    }

    private Expression eval(BinaryExpr binaryExpr, String fbName, int numOfnots) {
        if (isEqualToOperator(binaryExpr, BinaryExpr.Operator.AND)) {
            return handleAnd(binaryExpr, fbName, numOfnots);
        }
        if (isEqualToOperator(binaryExpr, BinaryExpr.Operator.OR)) {
            return handleOr(binaryExpr, fbName, numOfnots);
        }
        return binaryExpr;
    }

    private Expression eval(UnaryExpr unaryExpr, String fbName, int numOfnots) {
        if (isEqualToOperator(unaryExpr, UnaryExpr.Operator.LOGICAL_COMPLEMENT) && expressionIsFB(unaryExpr, fbName)) {
            return new BooleanLiteralExpr(numOfnots % 2 == 1); //false
        } else if (isLeaf(unaryExpr) && isEqualToOperator(unaryExpr, UnaryExpr.Operator.LOGICAL_COMPLEMENT)) {
            return unaryExpr;
        } else if (!isEqualToOperator(unaryExpr, UnaryExpr.Operator.LOGICAL_COMPLEMENT)) {
            return unaryExpr;
        }
        return eval(unaryExpr.getExpression(), fbName, numOfnots + 1);
    }

    private Expression eval(Expression expression, String fbName, int numOfnots) {
        if (expression instanceof BinaryExpr) {
            return eval((BinaryExpr) expression, fbName, numOfnots);
        } else if (expression instanceof UnaryExpr) {
            return eval((UnaryExpr) expression, fbName, numOfnots);
        } else if (expressionIsFB(expression, fbName)) {
            return new BooleanLiteralExpr(numOfnots % 2 == 0); //true
        }
        return expression;
    }

    private Expression handleOr(BinaryExpr binaryExpr, String fbName, int numOfnots) {
        if (expressionIsFB(binaryExpr.getLeft(), fbName) || expressionIsFB(binaryExpr.getRight(), fbName)) {
            return new BooleanLiteralExpr(numOfnots % 2 == 0); //true
        } else if (binaryExpr.getLeft() instanceof BooleanLiteralExpr && ((BooleanLiteralExpr) binaryExpr.getLeft()).getValue()) {
            return new BooleanLiteralExpr(numOfnots % 2 == 0); //true
        } else if (binaryExpr.getRight() instanceof BooleanLiteralExpr && ((BooleanLiteralExpr) binaryExpr.getRight()).getValue()) {
            return new BooleanLiteralExpr(numOfnots % 2 == 0); //true
        } else if (binaryExpr.getLeft() instanceof BooleanLiteralExpr && !((BooleanLiteralExpr) binaryExpr.getLeft()).getValue()) {
            return new BooleanLiteralExpr(numOfnots % 2 == 1); //false
        } else if (binaryExpr.getRight() instanceof BooleanLiteralExpr && !((BooleanLiteralExpr) binaryExpr.getRight()).getValue()) {
            return new BooleanLiteralExpr(numOfnots % 2 == 1); //false
        } else {
            Expression left = eval(binaryExpr.getLeft(), fbName, numOfnots);
            Expression right = eval(binaryExpr.getRight(), fbName, numOfnots);
            return eval(new BinaryExpr(left, right, BinaryExpr.Operator.OR), fbName, numOfnots);
        }
    }

    private Expression handleAnd(BinaryExpr binaryExpr, String fbName, int numOfnots) {
        if (expressionIsFB(binaryExpr.getLeft(), fbName))
            return binaryExpr.getRight();
        else if (expressionIsFB(binaryExpr.getRight(), fbName))
            return binaryExpr.getLeft();
        else if (isLeaf(binaryExpr.getLeft()) && isLeaf(binaryExpr.getRight()))
            return binaryExpr;
        else if (binaryExpr.getLeft() instanceof BooleanLiteralExpr && ((BooleanLiteralExpr) binaryExpr.getLeft()).getValue())
            return eval(binaryExpr.getRight(), fbName, numOfnots);
        else if (binaryExpr.getRight() instanceof BooleanLiteralExpr && ((BooleanLiteralExpr) binaryExpr.getRight()).getValue())
            return eval(binaryExpr.getLeft(), fbName, numOfnots);
        else if (binaryExpr.getLeft() instanceof BooleanLiteralExpr && !((BooleanLiteralExpr) binaryExpr.getLeft()).getValue())
            return new BooleanLiteralExpr(false);
        else if (binaryExpr.getRight() instanceof BooleanLiteralExpr && !((BooleanLiteralExpr) binaryExpr.getRight()).getValue())
            return new BooleanLiteralExpr(false);
        else {
            Expression left = eval(binaryExpr.getLeft(), fbName, numOfnots);
            Expression right = eval(binaryExpr.getRight(), fbName, numOfnots);
            return eval(new BinaryExpr(left, right, BinaryExpr.Operator.AND), fbName, numOfnots);
        }
    }


    private boolean expressionIsFB(Expression expression, String fbName) {
        return isLeaf(expression) && (expression instanceof MethodCallExpr)
                && isMethodArgsContainsFB((MethodCallExpr) expression, fbName);
    }

    private boolean isEqualToOperator(BinaryExpr binaryExpr, BinaryExpr.Operator operator) {
        return binaryExpr.getOperator().equals(operator);
    }
    private boolean isEqualToOperator(UnaryExpr unaryExpr, UnaryExpr.Operator operator) {
        return unaryExpr.getOperator().equals(operator);
    }

    private boolean isLeaf(Expression expression) {
        return expression instanceof MethodCallExpr || expression instanceof NameExpr;
    }

    private boolean isMethodArgsContainsFB(MethodCallExpr call, String fbName){
        for (Expression expression : call.getArguments()){
            if (expression.toString().contains(fbName))
                    return true;
        }
        return  false;
    }
}