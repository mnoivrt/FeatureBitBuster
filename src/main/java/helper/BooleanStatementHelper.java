package helper;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;

public class BooleanStatementHelper {

    public Expression eval(BinaryExpr binaryExpr, String fbName) {
        Node parent = binaryExpr.getParentNode().get();
        return ((BinaryExpr) binaryExpr).getRight();

    }
}