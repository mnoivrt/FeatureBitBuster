package helper;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;

public class BooleanStatementHelper {

    public Expression eval(BinaryExpr binaryExpr, String fbName) {
        Node parent = binaryExpr.getParentNode().get();
        if (isEqualToOperator(binaryExpr, BinaryExpr.Operator.AND)) {
            if (expressionIsFB(binaryExpr.getLeft(), fbName))
                return ((BinaryExpr) binaryExpr).getRight();
            if (expressionIsFB(binaryExpr.getRight(), fbName))
                return ((BinaryExpr) binaryExpr).getLeft();
        }
        if (isEqualToOperator(binaryExpr, BinaryExpr.Operator.OR)) {
            if (expressionIsFB(binaryExpr.getLeft(), fbName) || expressionIsFB(binaryExpr.getRight(), fbName))
                return new BooleanLiteralExpr(true);
        }
        return binaryExpr;
    }

    public Expression eval(UnaryExpr unaryExpr, String fbName) {
        if (isEqualToOperator(unaryExpr, UnaryExpr.Operator.LOGICAL_COMPLEMENT) && expressionIsFB(unaryExpr, fbName)) {
            return new BooleanLiteralExpr(false);
        }
        return unaryExpr;
    }

    public Expression eval(Expression expression, String fbName) {
        if (expression instanceof BinaryExpr) {
            return eval((BinaryExpr) expression, fbName);
        } else if (expression instanceof UnaryExpr) {
            return eval((UnaryExpr) expression, fbName);
        } else if ((expression instanceof MethodCallExpr || expression instanceof NameExpr) && expressionIsFB(expression, fbName)) {
            return new BooleanLiteralExpr(true);
        }
        return expression;
    }


    private boolean expressionIsFB(Expression expression, String fbName) {
        return expression.toString().endsWith("fb.isEnabled(\"" + fbName + "\")");
    }

    private boolean isEqualToOperator(BinaryExpr binaryExpr, BinaryExpr.Operator operator) {
        return binaryExpr.getOperator().equals(operator);
    }
    private boolean isEqualToOperator(UnaryExpr unaryExpr, UnaryExpr.Operator operator) {
        return unaryExpr.getOperator().equals(operator);
    }

}