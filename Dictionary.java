import java.io.*;
import java.util.Hashtable;



/**
 * Created by moi on 12/10/2018.
 */
class Dictionary {

    private Hashtable<String, String> dictionary;
    private Hashtable<String, Hashtable<String, String>> trigramsDictionary;

    public Hashtable<String, String> getDictionary() {
        return dictionary;
    }

    public Hashtable<String, Hashtable<String, String>> getTrigramsDictionary() {
        return trigramsDictionary;
    }



    public Dictionary(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        dictionary = new Hashtable<>();
        trigramsDictionary = new Hashtable<>();
        initialiseDictionaryAndTrigrams(reader);

        reader.close();
    }

    public void initialiseDictionaryAndTrigrams(BufferedReader reader) throws IOException {
        for(String word =  reader.readLine(); word!=null;word =  reader.readLine()) {
            dictionary.put(word, word);
            createTrigrams(word);
            //addTrigramsToDictionary(createTrigrams(word), word);
            //addToTrigramDictionary(word);
        }
    }

    public void addTrigramsToDictionary(String[] trigrams, String word){

        for(String trigram : trigrams){
            Hashtable<String, String> wordsContainTrigram = trigramsDictionary.get(trigram);
            if(wordsContainTrigram != null)
                wordsContainTrigram.put(word, word);
            else
                trigramsDictionary.put(trigram,new Hashtable<String, String>(){{put(word, word);}});
        }
        /*
        String editedLine = "<"+word+">";
            String trigram;
            for(int i =0; i < editedLine.length()-2; i++){
                trigram = "";
                for(int j = i; j < i+3;j++)
                    trigram += editedLine.charAt(j);
                Hashtable<String, String> wordsContainTrigram = trigramsDictionary.get(trigram);
                if(wordsContainTrigram != null)
                    wordsContainTrigram.put(word, word);
                else
                    trigramsDictionary.put(trigram,new Hashtable<String, String>(){{put(word, word);}});*/
        }
    }



    public void createTrigrams(String word){
        String editedLine = "<"+word+">";
        //String[] trigrams = new String[editedLine.length()-2];
        String trigram;
        for(int i =0; i < editedLine.length()-2; i++) {
            trigram = "";
            for (int j = i; j < i + 3; j++)
                trigram += editedLine.charAt(j);
            addTrigram(trigram, word);
            //trigrams[0] = trigram;
        }
        //return  trigrams;
    }

    public void addTrigram(String trigram, String word) {
        Hashtable<String, String> wordsContainTrigram = trigramsDictionary.get(trigram);
        if(wordsContainTrigram != null)
            wordsContainTrigram.put(word, word);
        else
            trigramsDictionary.put(trigram,new Hashtable<String, String>(){{put(word, word);}});
    }


    public Boolean contains(Object value){
        return dictionary.contains(value);
    }

}
