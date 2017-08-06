import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Aviv on 8/6/2017.
 */


public class FileScanner {
    public static final String FILE_TYPE = "java";

    FileBuster fileBuster;
    String path;

    public FileScanner(String path, String fbName) {
        fileBuster = new FileBuster(fbName);
        this.path = path;
    }

    public void scan() throws FileNotFoundException {
        File[] files = new File(path).listFiles();
        traverseFiles(files);
    }

    private void traverseFiles(File[] files) throws FileNotFoundException {
        for (File file : files) {
            String fileName = file.getName();

            if (file.isDirectory()) {
                traverseFiles(file.listFiles());
            } else {
                String fileExtension = getFileExtension(fileName);
                if(fileExtension.equals(FILE_TYPE))
                    if(isFileContainsFb(file)) {
                        fileBuster.RemoveFeatureBitFromFile(file.getAbsolutePath());
                        System.out.println("returned File: " + file.getAbsolutePath());
                    }
            }
        }
    }

    private boolean isFileContainsFb(File file) {
        boolean fileContainsFb = false;

        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];

            fis.read(data);
            fis.close();
            String content = new String(data, "UTF-8");
            fileContainsFb = content.contains(fileBuster.getFbName());
        }catch (Exception ex) {
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
