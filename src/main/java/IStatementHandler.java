import com.github.javaparser.ast.stmt.Statement;

/**
 * Created by aran on 06/08/2017.
 */
public interface IStatementHandler {
    Statement Execute(Statement statement, String fbName);
}
