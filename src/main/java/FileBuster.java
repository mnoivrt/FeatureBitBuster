import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by moshe on 06/08/2017.
 */
public class FileBuster {

    String fbName;
    String path;

    public FileBuster(String path, String fbName){
        this.fbName = fbName;
        this.path = path;
    }

    public void bust(){
        File[] files = new File(path).listFiles();
        traverseFiles(files);
    }

    private void traverseFiles(File[] files) {
        for (File file : files) {
            String fileName = file.getName();

            if (file.isDirectory()) {
                traverseFiles(file.listFiles());
            } else {
                String fileExtension = getFileExtension(fileName);
                if(fileExtension.equals("java"))
                    if(isFileContainsFb(file))
                        System.out.println("returned File: " + file.getAbsolutePath());
            }
        }
    }

    private boolean isFileContainsFb(File file) {
        boolean fileContainsFb = false;
        try{
            Path filePath = Paths.get(file.getName());
            String content = new String(Files.readAllBytes(filePath));
            fileContainsFb = content.contains(fbName);
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
        return fileContainsFb;
    }

    private static String getFileExtension(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }

        return extension;
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
