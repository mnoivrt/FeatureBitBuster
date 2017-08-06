import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Created by aran on 06/08/2017.
 */
public class BooleanStatementHandler implements IStatementHandler {
    public void Execute(Statement statement, String fbName, BlockStmt blockStmt)
    {
        if(IsBooleanStatement(statement) && IsContainsFb(fbName, statement))
        {
        }


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
