import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Created by moshe on 03/08/2017.
 */
public class main {

    public static void main(String[] args) throws FileNotFoundException {
        String fbName = "feature.arithmetic_addition";
        FileInputStream in = new FileInputStream("./test.java");

        CompilationUnit cu = JavaParser.parse(in);
        NodeList<TypeDeclaration<?>> types = cu.getTypes();

        //iterate through all calls members, finding methods
        for (TypeDeclaration<?> type : types) {
            NodeList<BodyDeclaration<?>> members = type.getMembers();
            for (BodyDeclaration<?> member : members) {
                if (member instanceof MethodDeclaration) {
                    removeFBFromMethod(fbName, (MethodDeclaration) member);

                }
            }
        }
        System.out.println("output: ");
        System.out.println(cu.toString());

    }

    //parsing methods, and remove fb if it appears in it
    private static void removeFBFromMethod(String fbName, MethodDeclaration member) {
        MethodDeclaration method = member;
        BlockStmt blockStmt = method.getBody().get();
        for(Statement statement : blockStmt.getStatements()){
            if(isIf(statement)){
                if(condContainsFb(fbName, statement)){
                    Node thenNode = removeFeatureBitFromIfStatment(fbName,statement);
                    blockStmt.remove(statement);
                    blockStmt.addStatement(thenNode.toString());
                }
            }
        }
    }

    //check  if statement and remove fb it it appears in it
    private static Node removeFeatureBitFromIfStatment(String fbName, Statement statement) {
        Node cond = getIfCond(statement);
        if (cond.getTokenRange().get().toString().equals("fb.isEnabled(\"" + fbName + "\")")){
          return getIfDoWhenTrue(statement);
        }
        else{
            //the if condition is more complicated than just asking if the fb is enabled
            return null;
        }
    }


    private static boolean condContainsFb(String fbName, Statement statement){
        return getIfCond(statement).toString().contains(fbName);
    }

    private static boolean isIf(Statement statement){
        return statement.getTokenRange().get().getBegin().toString().equals("if");
    }


    //if expression has (at most) 3 childs. first child is the cond, second is the doWhenTrue and the third is the else
    private static Node getIfCond(Statement statement){
      return   getIfChildByIndex(statement, 0);
    }

    private static Node getIfDoWhenTrue(Statement statement){
        return   getIfChildByIndex(statement, 1);
    }

    private static Node getIfDoWhenFalse(Statement statement){
        return   getIfChildByIndex(statement, 2);
    }

    private static Node getIfChildByIndex(Statement statement, int index){
        return statement.getChildNodes().get(index);
    }
}
