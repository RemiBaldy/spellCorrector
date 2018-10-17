import java.util.Arrays;

public class levenshteinDistance {
    private char[] misspelledWord, correctWord;
    private int[] misspelledVector;
    private int[] correctVector;
    int[][] matrixDistances;

    public levenshteinDistance(String correctWord, String misspelledWord) {
        this.misspelledWord = misspelledWord.toCharArray();
        this.correctWord = correctWord.toCharArray();

        matrixDistances = new int[misspelledWord.length()+1][correctWord.length()+1];
        initialiseDistances();
        /*misspelledVector = new int[misspelledWord.length()+1];
        correctVector = new int[correctWord.length()+1];
        initialiseVector(misspelledVector);*/
    }

    private void initialiseDistances() {
        for (int i = 0; i < matrixDistances.length; i++) {
            for (int j = 0; j < matrixDistances[0].length; j++) {

            }
        }

    }

    /**
     * Remove pre/postfixes if they match for the 2 words
     *
     */
    public void reduceWords(){
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
     * fill the vector with the edit distances for the empty string compared to the other string[1...n]
     */
    public void initialiseVector(int[] vector){
        for(int i = 0; i < vector.length; i++)
            vector[i] = i;
    }

    public int computeLevDistance(){




        /*int deletionCost, insertionCost, substitutionCost = 0;

        for(int i=0; i<correctWord.length; i++){
            correctVector[0] = i+1;
            for(int j=0; j<misspelledVector.length-1;j++){
                deletionCost = misspelledVector[j+1]+1;
                insertionCost = correctVector[j]+1;

                if(correctWord[i] == misspelledWord[j])
                    substitutionCost = misspelledVector[j];
                else
                    substitutionCost = misspelledVector[j]+1;

                correctVector[j+1] = Math.min(Math.min(deletionCost, insertionCost), deletionCost);

            }
        }*/
    }


}
