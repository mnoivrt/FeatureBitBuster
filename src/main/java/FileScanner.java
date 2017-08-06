import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Aviv on 8/6/2017.
 */


public class FileScanner {
    final String FILE_TYPE = "java";

    FileBuster fileBuster;
    String path;
    int numOfFiles;
    int currentFileNumber = 0;
    List<String> affectedFiles;

    public FileScanner(String path, String fbName) {
        fileBuster = new FileBuster(fbName);
        this.path = path;
        this.affectedFiles = new LinkedList<String>();
    }

    public void scan() throws FileNotFoundException {
        File[] files = new File(path).listFiles();
        numOfFiles = getNumOfFiles(files);
        System.out.println("Processing");
        traverseFiles(files);
        printResults();
    }

    private int getNumOfFiles(File[] files){
        int numOfFiles = 0;
        for (File file : files) {
            String fileName = file.getName();

            if (file.isDirectory()) {
                numOfFiles += getNumOfFiles(file.listFiles());
            } else {
                String fileExtension = getFileExtension(fileName);
                if(fileExtension.equals(FILE_TYPE))
                    numOfFiles++;
            }
        }
        return numOfFiles;
    }

    private void traverseFiles(File[] files) throws FileNotFoundException {
        for (File file : files) {
            String fileName = file.getName();

            if (file.isDirectory()) {
                traverseFiles(file.listFiles());
            } else {
                String fileExtension = getFileExtension(fileName);
                if(fileExtension.equals(FILE_TYPE)) {
                    currentFileNumber++;
                    printProgress(fileName);
                    if (isFileContainsFb(file)) {
                        String newContent = fileBuster.RemoveFeatureBitFromFile(file.getAbsolutePath());
                        writeContentToFile(newContent,file.getAbsolutePath());
                        affectedFiles.add(file.getAbsolutePath());
                    }
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

    private  void printProgress(String fileName)  {
        StringBuilder string = new StringBuilder(140);
        int percent = (int) (currentFileNumber * 100 / numOfFiles);
        String appendixMsg;
        if(percent==100) {
            fileName = "Finished";
        }

        string
                .append('\r')
                .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
                .append(String.format(" %d%% [", percent))
                .append(String.join("", Collections.nCopies(percent, "=")))
                .append('>')
                .append(String.join("", Collections.nCopies(100 - percent, " ")))
                .append(']')
                .append(String.join("", Collections.nCopies((int) (Math.log10(numOfFiles)) - (int) (Math.log10(currentFileNumber)), " ")))
                .append(String.format(" [%d/%d] "+ fileName, currentFileNumber, numOfFiles, fileName));

        System.out.print(string);

        try{
            Thread.sleep(300);
        }
        catch (Exception ex)
        {

        }
    }

    public String printResults(){
        StringBuilder sbResult = new StringBuilder();

        sbResult.append(affectedFiles.size() + " files were affected: \n");
        for (String filename: affectedFiles){
            sbResult.append(filename + "\n");
        }


        System.out.print(sbResult.toString());
        return sbResult.toString();
    }

    private void writeContentToFile(String content, String path){
        try{
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.print(content);
            writer.close();
        } catch (IOException e) {
            // do something
        }
    }
}
