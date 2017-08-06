import com.github.javaparser.ast.stmt.Statement;

/**
 * Created by aran on 06/08/2017.
 */
public class BooleanStatementHandler implements IStatementHandler {
    public Statement Execute(Statement statement, String fbName)
    {
       return statement;
    }
}
