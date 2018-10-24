import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Created by moi on 13/10/2018.
 */
class SpellChecker {

    private Dictionary dictionary;

    /**
     * Before levensteinDistanceComputing :
     * Each misspelled word is associated with the correct words with same trigrams and the number of times trigrams matched
     * between the correct and misspelled word, exemple : (chietn(chien,3)(chienne, 3)...)
     *
     * After : Each misspelled word is associated with the correct words and the levenstein distance corresponding.
     *
     */
    private Hashtable<String, Hashtable<String, Integer>> missSpelledWordsProbableCorrections;

    /**
     * Liste des trigrammes de mots faux associés à ces mots faux, exemple : (pas (passert, passert)(passat, passat)...)
     */
    private Hashtable<String, Hashtable<String, String>> trigrams;


    public SpellChecker(Dictionary dictionary, String fileWithSpellingErrorsPath) throws IOException {
        this.dictionary = dictionary;
        missSpelledWordsProbableCorrections = new Hashtable<>();
        trigrams = new Hashtable<>();
        initialiseDataStructures(fileWithSpellingErrorsPath);
    }


    public void initialiseDataStructures(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        for(String word =  reader.readLine(); word != null ;word =  reader.readLine())
            if (isMissspelledWord(word)) {
                missSpelledWordsProbableCorrections.put(word, new Hashtable<>());
                createTrigrams(word);
            }
        reader.close();
    }

    private void createTrigrams(String word) {
        String missSpelledWord = "<"+word+">";
        StringBuilder trigram;
        for(int i =0; i < missSpelledWord.length()-2; i++){
            trigram = new StringBuilder();
            for(int j = i; j < i+3;j++)
                trigram.append(missSpelledWord.charAt(j));

            //System.out.println("addTrigram "+ trigram + " word "+word);
            addTrigram(trigram.toString(), word);
        }
    }

    private void addTrigram(String trigram, String word) {
        Hashtable<String, String> missspelledWords = trigrams.get(trigram);
        if(missspelledWords != null)
            missspelledWords.put(word, word);
        else
            trigrams.put(trigram,new Hashtable<String, String>(){{put(word, word);}});
    }

    private Boolean isMissspelledWord(String word){
        return !dictionary.contains(word);
    }


    public void printMisspelledWordsFound() {
        for (Map.Entry<String, Hashtable<String, Integer>> stringHashtableEntry : missSpelledWordsProbableCorrections.entrySet()) {
            String key = stringHashtableEntry.getKey();
            System.out.println(key);
        }
    }


    /**
     * Find the correct words which trigrams match with the misspelled words trigrams contained in trigrams hashtable
     * and  call incrementCorrectWordsMatchingCount
     */
    public void findProbableCorrectionsComparingTrigrams(){
        for (Map.Entry<String, Hashtable<String, String>> misspelledTrigram : trigrams.entrySet()) {
            for (Map.Entry<String, Hashtable<String, String>> dictioTrigram : dictionary.getTrigramsDictionary().entrySet()) {
                if(misspelledTrigram.getKey().equals(dictioTrigram.getKey())){
                    //System.out.println(misspelledTrigram.getKey() + " = " + dictioTrigram.getKey());
                    for(String misspelledWord : misspelledTrigram.getValue().keySet()){
                        for(String correctWord : dictioTrigram.getValue().keySet()){
                            //System.out.println(misspelledWord + "correction : "+correctWord );
                            incrementCorrectWordMatchingCount(misspelledWord, correctWord);
                        }
                    }

                }
            }
        }
    }

    /**
     * insert the correct word found or increment his count if already there in the missSpelledWordsProbableCorrections hashtable     *
     * @param misspelledWord word
     * @param correctWord word
     */
    private void incrementCorrectWordMatchingCount(String misspelledWord , String correctWord){
        Hashtable<String,Integer> corrections = missSpelledWordsProbableCorrections.get(misspelledWord);
        corrections.put(correctWord, corrections.getOrDefault(correctWord,0)+1);
    }

    /**
     * Iterate on missSpelledWordsProbableCorrections and compute levenstein distance for each (misspelledWord, correctWord) couple.
     * Then add this distance in the missSpelledWordsProbableCorrections hashtable.
     */
    public void computeLevensteinDistances(){
        for (Map.Entry<String, Hashtable<String, Integer>> misspellWords : missSpelledWordsProbableCorrections.entrySet()) {
            Hashtable<String, Integer> correctWords = misspellWords.getValue();
            for(String correctWord : correctWords.keySet()){
                correctWords.put(correctWord, computeLevensteinDistance(correctWord,misspellWords.getKey()));
            }
        }
    }

    /**
     * Use the class levensteinDistance to calculate it
     * @param correctWord
     * @param misspelledWord
     * @return
     */
    private int computeLevensteinDistance(String correctWord, String misspelledWord){
        return new levenshteinDistance(correctWord, misspelledWord).computeLevensteinDistance();
    }

    public void printMostProbableCorrectionAfterLevenstein(){
        for (Map.Entry<String, Hashtable<String, Integer>> misspellWords : missSpelledWordsProbableCorrections.entrySet()) {
            Set<String> correctWordsKeys = misspellWords.getValue().keySet();
            for (String correctWordkKey : correctWordsKeys){
                //if(misspellWords.get(correctWordkKey))
            }
        }
    }

    private void lowestLevensteinDistanceOf1MisspelledWord(){

    }


    public void printTrigrams(){
        for (Map.Entry<String, Hashtable<String, String>> trigrams : trigrams.entrySet()) {
            System.out.println(trigrams.getKey());
        }
    }
    public void printProbableCorrections(){
        for (Map.Entry<String, Hashtable<String, Integer>> misspellWords : missSpelledWordsProbableCorrections.entrySet()) {
            Hashtable<String, Integer> correctWords = misspellWords.getValue();
            for(String correctWord : correctWords.keySet()){
                System.out.println(correctWord + " : " + correctWords.get(correctWord));
            }
        }
    }
    public void printMostProbableCorrections(){
        for (Map.Entry<String, Hashtable<String, Integer>> misspellWords : missSpelledWordsProbableCorrections.entrySet()) {
            Hashtable<String, Integer> correctWords = misspellWords.getValue();
            Pair<String, Integer> highestCountWord = new Pair<>("test",0);
            for(String correctWord : correctWords.keySet()){
                Integer correctWordCount = correctWords.get(correctWord);
                if(highestCountWord.getValue() < correctWordCount)
                    highestCountWord = new Pair<>(correctWord, correctWordCount);
            }
            System.out.println(highestCountWord.getKey());
            System.out.println(highestCountWord.getValue());
        }
    }


}
