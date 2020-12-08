package filesprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is the driver of the file processor.
 */
public class DirectoryProcessor {
    private File theFile = null;
    private SectionFactory sectionFactory;
    private ArrayList<String> fileLines = new ArrayList<>();
    private ArrayList<SectionCreatAndPlay> sectionArray;

    /**
     * The constructor. initialized DirectoryProcessor object from a given command file, and
     * creat a sections objects according to the given file.
     * @param file given file.
     * @throws TypeTwoException if there was a problem with the file.
     * @throws IOException if there was a problem with the file.
     */
    public DirectoryProcessor(String file) throws TypeTwoException, IOException {
        File txtFile = new File(file);
        if (txtFile.exists() && txtFile.isFile()){
            theFile = txtFile;
            try {
                this.creatFileContentList();
                emptyCommand(fileLines);
            }
            catch (IOException exception){
                return;
            }
            SectionFactory sectionFactory = new SectionFactory(fileLines);
            sectionArray = sectionFactory.creatAllSections(); //array with all section
            return;
        }
        throw new TypeTwoException("IOException");

}
    /**
     * This method read the file lines and add them to array such that each string in the array is a line.
     * @throws IOException if a there was a problem with the file.
     */
    private void creatFileContentList() throws IOException {
        FileReader fileReader = new FileReader(theFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String currentLine = bufferedReader.readLine();
        while ((currentLine != null)){
            this.fileLines.add(currentLine);
            currentLine = bufferedReader.readLine();
        }
        bufferedReader.close();
    }

    /**
     * This method creat array of all the files in a given directory.
     * @param sourceDir a given directory.
     * @return array of all teh files in source directory.
     * @throws TypeTwoException if there was a problem with the given directory.
     */
    private ArrayList<File> creatAllFilesArray(String sourceDir) throws TypeTwoException {
        if (!sourceDir.isEmpty()){
            File folder = new File(sourceDir);
            if (folder.isDirectory()){
                File[] files = folder.listFiles();
                ArrayList<File> allFiles = new ArrayList<>();
                allFiles.addAll(Arrays.asList(files));
                allFiles.removeIf(File::isDirectory);
                return allFiles;
            }
            throw new TypeTwoException("its not directory");
        }
        throw new TypeTwoException("No files in source directory");
    }

    /**
     * This method play all the section on the files in source directory.
     * @param filesArray all files in source directory.
     */
    private void playAll(ArrayList<File> filesArray){
        if (filesArray!=null){
            for (SectionCreatAndPlay section:sectionArray){
                for (String secResult: section.play(filesArray)){
                    System.out.println(secResult);
                }
            }
        }
    }

    /**
     * This method check if the command file is empty.
     * @param lines command file lines.
     * @throws TypeTwoException if the command file is empty.
     */
    public void emptyCommand(ArrayList<String> lines) throws TypeTwoException {
        if (lines.size()==0){
            throw new TypeTwoException("Empty command file");
        }

    }

    /**
     * This method is the driver of the file processor.
     * creat DirectoryProcessor directoryProcessor object, array of al files in source directory,
     * and call rhe play all method, if something went wrong it handel the exceptions.
     * @param arguments given arguments.
     */
    public static void playDirectoryProcessor(String[] arguments){
        DirectoryProcessor directoryProcessor;
        ArrayList<File> allFiles;
        if (arguments.length!=2) {
            TypeTwoException exception = new TypeTwoException("Only two arguments needed");
            System.err.println("ERROR: "+exception.msg);
            return;
        }
        try {
            directoryProcessor = new DirectoryProcessor(arguments[1]);
        }
        catch (TypeTwoException | IOException exception){
            System.err.println("ERROR: "+ ((TypeTwoException) exception).msg);
            return;
        }
        try {
            allFiles = directoryProcessor.creatAllFilesArray(arguments[0]);
        }
        catch (TypeTwoException exception){
            System.err.println("ERROR: "+exception.msg);
            return;
        }
        directoryProcessor.playAll(allFiles);
    }

    /**
     * The main method.
     * @param args
     */
    public static void main(String[] args) {
        playDirectoryProcessor(args);





    }
}
