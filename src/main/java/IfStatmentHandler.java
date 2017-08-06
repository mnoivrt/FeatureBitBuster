import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import helper.BooleanStatementHelper;

/**
 * Created by moshe on 06/08/2017.
 */
public class IfStatmentHandler implements IStatementHandler {
    @Override
    public void Execute(Statement statement, String fbName, BlockStmt blockStmt) {
        if (isIfStatment(statement) &&  conditionContainsFB(((IfStmt)statement).getCondition(),fbName)){
            Node cond = ((IfStmt)statement).getCondition();
            if (isConditionContainsOnlyFB(cond, fbName)){
                Node thenNode = ((IfStmt) statement).getThenStmt();
                blockStmt.remove(statement);
                blockStmt.addStatement(thenNode.toString());
            }
            else {
                BooleanStatementHelper booleanStatementHelper = new BooleanStatementHelper();
                Node reducedCondition = booleanStatementHelper.eval((BinaryExpr)cond,fbName);
                ((IfStmt)statement).setCondition((Expression) reducedCondition);
            }
        }
    }

    private boolean isConditionContainsOnlyFB(Node cond, String fbName) {
        return cond.toString().equals("fb.isEnabled(\"" + fbName + "\")");
    }

    private boolean isIfStatment(Statement statement){
        return  statement instanceof IfStmt;
    }

    private boolean conditionContainsFB(Node cond, String fbName){
        return cond.toString().contains(fbName);
    }
}
