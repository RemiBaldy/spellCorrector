import org.junit.Test;

import static org.junit.Assert.*;

public class LevenshteinDistanceTest {
    @Test
    public void testLevensteinDistance() {
        String correctWord = "chien";
        String misspelledWord = "chein";
        LevenshteinDistance distance = new LevenshteinDistance(correctWord,misspelledWord);
        assertEquals(2, distance.computeLevensteinDistance());

        correctWord = "montagne";
        misspelledWord = "amantagnes";
        distance = new LevenshteinDistance(correctWord,misspelledWord);
        assertEquals(3, distance.computeLevensteinDistance());

    }
}