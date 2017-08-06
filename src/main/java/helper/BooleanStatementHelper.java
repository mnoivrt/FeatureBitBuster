package helper;

import com.github.javaparser.ast.expr.BinaryExpr;

public class BooleanStatementHelper {

    public BinaryExpr eval(BinaryExpr binaryExpr) {
        return binaryExpr;
    }
}


// 1.       foreach leaf
// 1.1          if leaf is fb
// 1.1.1            if cond is &&
// 1.1.1.1              change parent node to other child
// 1.1.2            if cond is ||
// 1.1.2.1              change parent to true
// 1.1.3            if cond is !
// 1.1.3.1
