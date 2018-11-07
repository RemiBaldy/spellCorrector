import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AppBis {
    public static void main(String[] args) throws IOException {
        long[] nanoTimes = new long[6];

        long startTime = System.nanoTime();
        Dictionary dico = new Dictionary("dico.txt");
        nanoTimes[0] = System.nanoTime()-startTime;


        BufferedReader reader = new BufferedReader(new FileReader("fautes.txt"));

        for(String word =  reader.readLine(); word != null ;word =  reader.readLine()){

            startTime = System.nanoTime();
            SpellCheckerOneWord spellCheckerOneWord = new SpellCheckerOneWord(dico, word);
            nanoTimes[1] += System.nanoTime()-startTime;

            startTime = System.nanoTime();
            spellCheckerOneWord.createTrigrams();
            nanoTimes[2] += System.nanoTime()-startTime;

            startTime = System.nanoTime();
            spellCheckerOneWord.findCorrectionsComparingTrigrams();
            nanoTimes[3] += System.nanoTime()-startTime;

            startTime = System.nanoTime();
            spellCheckerOneWord.computeLevensteinDistances();
            nanoTimes[4] += System.nanoTime()-startTime;

            startTime = System.nanoTime();
            spellCheckerOneWord.findLowestLevDistanceCorrections();
            nanoTimes[5] += System.nanoTime()-startTime;

            spellCheckerOneWord.printFinalCorrections();
        }


        for(long lg : nanoTimes)
            System.out.println(lg / 1000000+" ms");
    }
}
