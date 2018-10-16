import org.junit.Test;

/**
 * Created by moi on 13/10/2018.
 */
public class DictionaryTest {
    @Test
    public void initializeFromTxtFile() throws Exception {
        long startTime = System.nanoTime();
        Dictionary dico = new Dictionary("petitdico.txt");
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime) / 1000000+" ms");
    }
}