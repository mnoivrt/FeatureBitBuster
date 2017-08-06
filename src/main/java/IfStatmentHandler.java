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

    public void Execute(Statement statement, String fbName, BlockStmt blockStmt) {
        if (isIfStatment(statement) &&  conditionContainsFB(((IfStmt)statement).getCondition(),fbName)){
            Node cond = ((IfStmt)statement).getCondition();
            BooleanStatementHelper booleanStatementHelper = new BooleanStatementHelper();
            Node reducedCondition = booleanStatementHelper.eval((Expression) cond,fbName);
            switch (reducedCondition.toString()){
                case "true" :
                    takeThenPart(statement, blockStmt);
                    break;
                case "false" :
                    takeElsePart(statement, blockStmt);
                    break;
                default:
                    ((IfStmt)statement).setCondition((Expression) reducedCondition);
            }
        }
    }

    private void takeThenPart(Statement statement, BlockStmt blockStmt) {
        Node thenNode = ((IfStmt) statement).getThenStmt();
        blockStmt.remove(statement);
        blockStmt.addStatement(thenNode.toString());
    }

    private void takeElsePart(Statement statement, BlockStmt blockStmt) {
        Node elseNode = ((IfStmt) statement).getElseStmt().get();
        blockStmt.remove(statement);
        blockStmt.addStatement(elseNode.toString());
    }


    private boolean isIfStatment(Statement statement){
        return  statement instanceof IfStmt;
    }

    private boolean conditionContainsFB(Node cond, String fbName){
        return cond.toString().contains(fbName);
    }
}
