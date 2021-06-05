import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.Scanner;

public class webCrawler {
    public static void main(String args[])
    {
        //"C:/Users/Nathan/Downloads/directory-list-2.3-big.txt"

        // enter base website e.g. www.google.com, etc.
        // load in text file with common directories
        // search to see if website + "/directory(x)" valid

        //List<String> dirList = getFile("C:/Users/Nathan/Downloads/directory-list-2.3-big.txt");
        //for (int i = 0; i < dirList.size(); i++){
        //    System.out.println(dirList.get(i));
        //}

        List<String> dirList = getFile("C:/Users/Nathan/Downloads/directory-list-2.3-big.txt");

        System.out.println("Enter a website URL (include https://):");
        String websiteInput = new Scanner(System.in).nextLine();

        System.out.println("Search subdirectories (y/n):");
        String searchSub = new Scanner(System.in).nextLine();
        boolean searchSubBool = false;
        if (searchSub.equals("y")) searchSubBool = true;

        List<String> allValidDir = getAllDir(dirList,websiteInput, searchSubBool);

        //for(int i = 0; i< allValidDir.size(); i++){
        //    System.out.println(allValidDir.get(i));
        //}




    }

    public static List<String> getAllDir(List<String> dictionary, String website, boolean getSubDir){

        List<String> output = new ArrayList<>();

        for (int i = 0; i < dictionary.size(); i++){
            if(getDirExists(website, dictionary.get(i))){
                System.out.println(website + "/"+dictionary.get(i));
                output.add(dictionary.get(i));

                if(getSubDir){
                    output.addAll(getAllDir(dictionary, website+"/"+dictionary.get(i), true));
                }
            }
        }

        return output;

    }

    public static boolean getDirExists(String base, String dir){

        try{
            URL urlTest = new URL(base + "/" + dir);

            HttpURLConnection connection =  (HttpURLConnection)  urlTest.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();

            if (connection.getResponseCode() == 200){
                return true;

            }

        }
        catch (Exception e){
            return false;
        }

        return false;

    }



    public static List<String> getFile(String filePath){

        try {
            List<String> fileOutput;
            fileOutput = Files.readAllLines(Paths.get(filePath),
                    Charset.defaultCharset());

            return fileOutput;
        }
        catch (Exception e){
            return null;
        }

    }
}
