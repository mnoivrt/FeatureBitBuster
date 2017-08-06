import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Aviv on 8/6/2017.
 */


public class FileScanner {
    public static final String FILE_TYPE = "java";

    String fbName;
    String path;

    public FileScanner(String path, String fbName) {
        this.fbName = fbName;
        this.path = path;
    }

    public void scan(){
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
                if(fileExtension.equals(FILE_TYPE))
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
}
