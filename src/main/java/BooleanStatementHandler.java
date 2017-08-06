import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Created by aran on 06/08/2017.
 */
public class BooleanStatementHandler implements IStatementHandler {
    public Node Execute(Statement statement, String fbName)
    {
        if(IsBooleanStatement(statement) && IsContainsFb(fbName, statement))
        {
            return statement.getChildNodes().get(0);
        }
        return null;

    }

    private boolean IsBooleanStatement(Statement statement)
    {
        return
                statement instanceof ExpressionStmt &&
                ((ExpressionStmt) statement).getExpression() instanceof VariableDeclarationExpr;

    }

    private static boolean IsContainsFb(String fbName, Statement statement){
        return statement.getTokenRange().toString().contains(fbName);
    }
}
