import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by moi on 13/10/2018.
 */
class SpellCheckerOneWord {

    private Dictionary dictionary;
    private String missSpelledWord;


    /**
     * Trigram list of the word
     */
    private ArrayList<String> trigrams;


    /**
     * Before levensteinDistanceComputing :
     * Hashtable<Correct words with same trigrams, number of times trigrams matched>
     *
     * After : Hashtable<Correct words with same trigrams, levenstein distance>
     */
    private Hashtable<String, Integer> misSpelledWordCorrections;


    /**
     * List of corrections possible with the lowest levenstein distance
     */
    private ArrayList<String> misSpelledWordsFinalCorrections;


    public SpellCheckerOneWord(Dictionary dictionary, String missSpelledWord) throws IOException {
        this.dictionary = dictionary;
        this.missSpelledWord = missSpelledWord;
        misSpelledWordCorrections = new Hashtable<>();
        trigrams = new ArrayList<>();
    }

    /**
     * Create the trigrams of the missSpelled word and store them in trigrams ArrayList
     */
    public void createTrigrams() {
        String missSpelledWordModified = "<"+missSpelledWord+">";
        StringBuilder trigram;
        for(int i =0; i < missSpelledWordModified.length()-2; i++){
            trigram = new StringBuilder();
            for(int j = i; j < i+3;j++)
                trigram.append(missSpelledWordModified.charAt(j));
            trigrams.add(trigram.toString());
        }
    }


    /**
     * Find the correct words which trigrams match with the misspelled words trigrams (contained in trigrams hashtable)
     * and call incrementCorrectWordsMatchingCount
     */
    public void findCorrectionsComparingTrigrams(){
        for (String trigram : trigrams) {
            for (Map.Entry<String, Hashtable<String, String>> dictioTrigram : dictionary.getTrigramsDictionary().entrySet()) {
                if(trigram.equals(dictioTrigram.getKey())){
                    for(String correctWord : dictioTrigram.getValue().keySet()){
                        addOrIncrementCorrectWordMatching(correctWord);
                    }
                }
            }
        }
    }

    /**
     * insert the correct word found or increment his count if already there in the missSpelledWordsProbableCorrections hashtable
     * @param correctWord word
     */
    private void addOrIncrementCorrectWordMatching(String correctWord){
        misSpelledWordCorrections.put(correctWord, misSpelledWordCorrections.getOrDefault(correctWord,0)+1);
    }


    /**
     * Iterate on missSpelledWordsProbableCorrections and compute levenstein distance for each (misspelledWord, correctWord) couple.
     * Then add this distance in the missSpelledWordsProbableCorrections hashtable.
     */
    public void computeLevensteinDistances(){
            int maxTrigramMatchesCount = getMaxTrigramsMatchingCount();

            Hashtable<String, Integer> correctWordsHighestTrigrams= new Hashtable<>();

            for(Map.Entry<String, Integer> correctWord : misSpelledWordCorrections.entrySet()){
                int TrigramMatchesCount =  correctWord.getValue();
                if(TrigramMatchesCount == maxTrigramMatchesCount - 1 || TrigramMatchesCount == maxTrigramMatchesCount)
                    correctWordsHighestTrigrams.put(correctWord.getKey(), computeLevensteinDistance(correctWord.getKey(),missSpelledWord));
            }
            misSpelledWordCorrections = correctWordsHighestTrigrams;
    }

    /**
     * Find the highest number of trigrams matched count among the possible corrections
     * for a misspelled word (Map.Entry<String, Hashtable<String, Integer>> misspellWords)
     * @return maxTrigramMatchesCount
     */
    private int getMaxTrigramsMatchingCount(){
        int maxTrigramMatchesCount = 0;
        for(Map.Entry<String, Integer> correctWord : misSpelledWordCorrections.entrySet()) {
            int TrigramMatchesCount = correctWord.getValue();
            if (TrigramMatchesCount > maxTrigramMatchesCount)
                maxTrigramMatchesCount = TrigramMatchesCount;
        }
        return maxTrigramMatchesCount;
    }


    /**
     * Use the class levensteinDistance to calculate it
     * @param correctWord
     * @param misspelledWord
     * @return Levenstein distance
     */
    private int computeLevensteinDistance(String correctWord, String misspelledWord){
        return new LevenshteinDistance(correctWord, misspelledWord).computeLevensteinDistance();
    }


    /**
     * For each misspelled word : find the corrections with the lowest leveinstein distance and add them to misSpelledWordsFinalCorrections hashtable.
     */
    public void findLowestLevDistanceCorrections(){
            int minLevensteinDistance = 50;/*needed a high default value*/
            for (Map.Entry<String, Integer> correctWord : misSpelledWordCorrections.entrySet()){
                int levensteinDistance = correctWord.getValue();;
                if(minLevensteinDistance > levensteinDistance) {
                    minLevensteinDistance = levensteinDistance;
                    createNewFinalCorrectionsList(correctWord.getKey());
                }
                else if(minLevensteinDistance == levensteinDistance){
                    misSpelledWordsFinalCorrections.add(correctWord.getKey());
                }
            }
    }

    private void createNewFinalCorrectionsList(String correctWord) {
        misSpelledWordsFinalCorrections = new ArrayList<>();
        misSpelledWordsFinalCorrections.add(correctWord);
    }


    public void printFinalCorrections(){
        System.out.println(missSpelledWord + " : ");
        for(String correctWord : misSpelledWordsFinalCorrections){
                System.out.println("\t"+correctWord);
        }
        System.out.println();
    }


}
