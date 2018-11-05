import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        long[] nanoTimes = new long[4];

        long startTime = System.nanoTime();
        Dictionary dico = new Dictionary("dico.txt");
        nanoTimes[0] = System.nanoTime()-startTime;

        startTime = System.nanoTime();
        SpellChecker spellChecker = new SpellChecker(dico, "fautes.txt");
        nanoTimes[1] = System.nanoTime()-startTime;

        startTime = System.nanoTime();
        spellChecker.findProbableCorrectionsComparingTrigrams();
        nanoTimes[2] = System.nanoTime()-startTime;

        startTime = System.nanoTime();
        spellChecker.computeLevensteinDistances();
        nanoTimes[3] = System.nanoTime()-startTime;

        spellChecker.storeLowestLevDistanceCorrectionsAsFinalCorrections();
        spellChecker.printFinalCorrections();

        for(long lg : nanoTimes)
            System.out.println(lg / 1000000+" ms");
    }
}
