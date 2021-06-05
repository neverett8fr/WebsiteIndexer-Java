import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class webCrawler {
    public static void main(String args[])
    {
        System.out.println("Hello World");
        System.out.println(getFile("C:/Users/Nathan/Downloads/directory-list-2.3-big.txt").get(4));


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
