import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Created by aran on 06/08/2017.
 */
public interface IStatementHandler {
    void Execute(Statement statement, String fbName, BlockStmt blockStmt);
}
