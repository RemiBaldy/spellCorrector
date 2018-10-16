import org.junit.Test;

import java.io.IOException;

/**
 * Created by moi on 13/10/2018.
 */
public class SpellCheckerTest {


    @Test
    public void findMissspelledWords() throws Exception {

        Dictionary dico = new Dictionary("dico.txt");

        long startTime = System.nanoTime();
        SpellChecker spellChecker = new SpellChecker(dico, "corrige.txt");
        spellChecker.findProbableCorrectionsComparingTrigrams();
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime) / 1000000+" ms");

        spellChecker.printMostProbableCorrections();


        //spellChecker.printMisspelledWordsFound();
    }
    @Test
    public void testTrigramsCreation() throws IOException {
        Dictionary dico = new Dictionary("dico.txt");
        SpellChecker spellChecker = new SpellChecker(dico, "misspelledWord.txt");
        spellChecker.printTrigrams();

    }

}