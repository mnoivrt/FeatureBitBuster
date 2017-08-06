
import java.io.FileNotFoundException;


/**
 * Created by moshe on 03/08/2017.
 */
public class main {

    public static void main(String[] args) throws FileNotFoundException {
        FileScanner fileScanner = new FileScanner(
                "test",
                "feature.arithmetic_multipcation");
        fileScanner.scan();

    }


}
