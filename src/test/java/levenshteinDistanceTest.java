import org.junit.Test;

import static org.junit.Assert.*;

public class levenshteinDistanceTest {
    @Test
    public void testLevensteinDistance() {
        String correctWord = "chien";
        String misspelledWord = "chein";
        levenshteinDistance distance = new levenshteinDistance(correctWord,misspelledWord);
        assertEquals(2, distance.computeLevensteinDistance());

        correctWord = "montagne";
        misspelledWord = "amantagnes";
        distance = new levenshteinDistance(correctWord,misspelledWord);
        assertEquals(3, distance.computeLevensteinDistance());

    }
}