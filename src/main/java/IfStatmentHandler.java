import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Created by moshe on 06/08/2017.
 */
public class IfStatmentHandler implements IStatementHandler {
    @Override
    public void Execute(Statement statement, String fbName, BlockStmt blockStmt) {
        if (isIfStatment(statement) &&  conditionContainsFB(((IfStmt)statement).getCondition(),fbName)){
            if (isConditionContainsOnlyFB((IfStmt) statement, fbName)){
                Node thenNode = ((IfStmt) statement).getThenStmt();
                blockStmt.remove(statement);
                blockStmt.addStatement(thenNode.toString());
            }
        }
    }

    private boolean isConditionContainsOnlyFB(IfStmt statement, String fbName) {
        return statement.getCondition().toString().equals("fb.isEnabled(\"" + fbName + "\")");
    }

    private boolean isIfStatment(Statement statement){
        return  statement instanceof IfStmt;
    }

    private boolean conditionContainsFB(Node cond, String fbName){
        return cond.toString().contains(fbName);
    }
}
