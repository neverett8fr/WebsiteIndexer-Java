import javax.rmi.ssl.SslRMIClientSocketFactory;
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

public class websiteIndexer {


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

        //C:/Users/Nathan/Downloads/


        System.out.println("Small(s), Medium(m) or Big(b) -- quick(q)");
        String dictionaryPathInput = new Scanner(System.in).nextLine();
        String dictionaryPath = "src/directory-list-2.3-big.txt";
        if(dictionaryPathInput.equals("s")){
            dictionaryPath = "src/directory-list-2.3-small.txt";
        }
        else if(dictionaryPathInput.equals("m")){
            dictionaryPath = "src/directory-list-2.3-medium.txt";
        }
        else if(dictionaryPathInput.equals("q")){
            dictionaryPath = "src/super-quick.txt";
        }

        List<String> dirList = getFile(dictionaryPath);

        System.out.println("Enter a website URL (include https://):");
        String websiteInput = new Scanner(System.in).nextLine();

        System.out.println("Search subdirectories (y/n):");
        String searchSub = new Scanner(System.in).nextLine();
        boolean searchSubBool = false;
        if (searchSub.equals("y")) searchSubBool = true;

        List<String> allValidDir = getAllDir(dirList,websiteInput, searchSubBool,  0);

        //for(int i = 0; i< allValidDir.size(); i++){
        //    System.out.println(allValidDir.get(i));
        //}

        System.out.println("Finished!");



    }

    public static List<String> getAllDir(List<String> dictionary, String website, boolean getSubDir,  int countDeep){

        List<String> output = new ArrayList<>();

        if (countDeep >10) {
            return output;
        }

        int count = 0;
        if(dictionary != null) {
            for (int i = 0; i < dictionary.size(); i++) {
                count+=1;
                if (count >=50){
                    count = 0;
                    System.out.println("Progress: " + String.format("%,.4f",(100.00/dictionary.size())*i) + "%");
                }

                if (getDirExists(website, dictionary.get(i))) {
                    System.out.println(website + "/" + dictionary.get(i));
                    output.add(website + "/" + dictionary.get(i));

                    //float x = 100;
                    //x=x/dictionary.size();
                    //x = x*i;
                    //System.out.println(x);

                    if (getSubDir && !dictionary.get(i).equals("index")) {
                        output.addAll(getAllDir(dictionary, website + "/" + dictionary.get(i), true, countDeep+1));
                    }
                }
            }
        }
        else{
            System.out.println("Empty Dictionary");
        }

        return output;

    }

    public static boolean getDirExists(String base, String dir){

        try{
            URL urlTest = new URL(base + "/" + dir);

            HttpURLConnection connection =  (HttpURLConnection)  urlTest.openConnection();
            //connection.setRequestMethod("HEAD");
            connection.setRequestMethod("GET");
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
