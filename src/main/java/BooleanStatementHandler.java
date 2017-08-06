import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Created by aran on 06/08/2017.
 */
public class BooleanStatementHandler implements IStatementHandler {
    public Node Execute(Statement statement, String fbName)
    {
       return statement.getChildNodes().get(0);
    }
}
