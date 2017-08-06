import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by moshe on 06/08/2017.
 */
public class FileBuster {

    String fbName;
    String path;

    public FileBuster(String path, String fbName) {
        this.fbName = fbName;
        this.path = path;
    }

    public void RemoveFeatureBitFromFile() throws FileNotFoundException {
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
    }

    private static void removeFBFromMethod(String fbName, MethodDeclaration member) {
        MethodDeclaration method = member;
        BlockStmt blockStmt = method.getBody().get();
        for(Statement statement : blockStmt.getStatements()){
                //here we should call the statment handlers
            }
        }

}
