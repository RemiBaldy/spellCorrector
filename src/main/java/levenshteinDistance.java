import java.util.Arrays;

public class levenshteinDistance {
    private char[] misspelledWord, correctWord;

    /**
     * Represent one row of distances computing used to compute the distances of the next row
     */
    private int[] levensteinDistancesVector;

    /**
     * Used to store 2 distances value for the next row before putting them into "levensteinDistancesVector"
     */
    private int aCase, anotherCase;



    public levenshteinDistance(String correctWord, String misspelledWord) {

        this.misspelledWord = misspelledWord.toCharArray();
        this.correctWord = correctWord.toCharArray();
        levensteinDistancesVector = new int[misspelledWord.length()+1];
        initialiseVectorDistances(levensteinDistancesVector);
    }

    public int computeLevensteinDistance(){
        return computeNextRows();
    }

    private int computeNextRows() {
        for (char charCorrectWord : correctWord) {
            for (int i = 0; i < levensteinDistancesVector.length; i++) {
                if (i == 0)
                    aCase = levensteinDistancesVector[0] + 1;
                else {
                    int cost = 0;
                    if (misspelledWord[i - 1] != charCorrectWord)
                        cost = 1;

                    int min = min(aCase + 1, levensteinDistancesVector[i] + 1, levensteinDistancesVector[i - 1] + cost);
                    levensteinDistancesVector[i-1] = aCase;
                    aCase = min;
                }
            }
            levensteinDistancesVector[levensteinDistancesVector.length-1] = aCase;
        }
        return levensteinDistancesVector[levensteinDistancesVector.length-1];
    }

    private int min(int number, int number2, int number3) {
        return Math.min(number,Math.min(number2, number3));
    }


    /**
     * Remove pre/postfixes if they match for the 2 words
     */
    private void reduceWords(){
        int i =0;
        while(correctWord[i]==misspelledWord[i]/* && i<correctWord.length && i<misspelledWord.length*/)
            i++;
        correctWord = Arrays.copyOfRange(correctWord, i, correctWord.length);
        misspelledWord = Arrays.copyOfRange(misspelledWord, i, misspelledWord.length);
        i=correctWord.length-1;
        int j = misspelledWord.length-1;
        while(correctWord[i]==misspelledWord[j] /*&& i>0 && j>0*/) {
            j--;
            i--;
        }
        correctWord = Arrays.copyOfRange(correctWord, 0, i+1);
        misspelledWord = Arrays.copyOfRange(misspelledWord, 0, j+1);
    }

    /**
     * fill the vector with the edit distances for the string[1...n] compared to empty string
     */
    private void initialiseVectorDistances(int[] vector){
        for(int i = 0; i < vector.length; i++)
            vector[i] = i;
    }




}
