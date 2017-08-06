import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by moshe on 06/08/2017.
 */
public class FileBuster {

    String fbName;

    public FileBuster(String fbName) {
        this.fbName = fbName;
    }

    public String RemoveFeatureBitFromFile(String path) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(path);
        CompilationUnit compilationUnit = JavaParser.parse(in);

        NodeList<TypeDeclaration<?>> types = compilationUnit.getTypes();
        for (TypeDeclaration<?> type : types) {
            for (BodyDeclaration<?> member : type.getMembers()) {
                if (member instanceof MethodDeclaration) {
                    removeFBFromMethod(fbName, (MethodDeclaration) member);
                }
            }
        }

//            System.out.println("output: ");
//            System.out.println(compilationUnit.toString());
        return compilationUnit.toString();

    }

    private void removeFBFromMethod(String fbName, MethodDeclaration member) {
        IfStatmentHandler ifStatementHandler = new IfStatmentHandler();
        BooleanStatementHandler booleanStatementHandler = new BooleanStatementHandler();
        MethodDeclaration method = member;
        BlockStmt blockStmt = method.getBody().get();
        NodeList<Statement> statements =   blockStmt.getStatements();
        for(Statement statement : statements){
            ifStatementHandler.Execute(statement,fbName,blockStmt);
            booleanStatementHandler.Execute(statement, fbName, blockStmt);
        }
    }


    public String getFbName() {
        return fbName;
    }
}
