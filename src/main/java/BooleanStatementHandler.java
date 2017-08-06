import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import helper.BooleanStatementHelper;

/**
 * Created by aran on 06/08/2017.
 */
public class BooleanStatementHandler implements IStatementHandler {
    public void Execute(Statement statement, String fbName, BlockStmt blockStmt)
    {
        if(IsBooleanStatement(statement) && IsContainsFb(fbName, statement))
        {
            BooleanStatementHelper helper = new BooleanStatementHelper();
            Expression newCondition = helper.eval(ExtractBoolCondition(statement), fbName);
            UpdateBoolCondition(statement, newCondition);
        }
    }

    private void UpdateBoolCondition(Statement statement, Expression newCondition)
    {
        ((VariableDeclarator)((VariableDeclarationExpr)((ExpressionStmt) statement).getExpression()).getVariable(0)).setInitializer(newCondition);
    }

    private Expression ExtractBoolCondition(Statement statement)
    {
        return ((VariableDeclarator)((VariableDeclarationExpr)((ExpressionStmt) statement).getExpression()).getVariable(0)).getInitializer().get();
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
