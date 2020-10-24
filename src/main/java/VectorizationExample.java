import bagofwords.BagOfWordsVectorization;
import preprocessing.TextVectorization;

import java.util.HashSet;
import java.util.Set;

public class VectorizationExample {

    public static void main(String[] args) {

        /* Example of custom vectorization (use classes from preprocessing package) */
        // pathToTexts - path to texts folder
//        String pathToTexts = "texts";
        String pathToTexts = "texts\\test_new";
        TextVectorization vectorization = new TextVectorization("humanitariesVStechnics", pathToTexts);
        vectorization.processFiles(pathToTexts + "\\humanitaries", "humanitaries");
        vectorization.processFiles(pathToTexts + "\\technics", "technics");
        vectorization.saveData();

        /* Example of custom vectorization (use classes from preprocessing package) */
        // Set of all classes
        Set<String> categories = new HashSet<String>();
        categories.add("humanitaries");
        categories.add("technics");

        /*  name - name of the model
            pathToTexts - path to texts folder (subfolders must be named as classes)
            categories - set of classes
         */
        BagOfWordsVectorization bagOfWords = new BagOfWordsVectorization("humanitariesVStechnics-bag", pathToTexts, categories);
        bagOfWords.processFiles(pathToTexts);
        bagOfWords.saveData();
    }
}
