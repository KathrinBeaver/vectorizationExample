import bagofwords.BagOfWordsVectorization;
import preprocessing.AttributeType;
import preprocessing.TextPreprocessing;
import preprocessing.TextVectorization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class VectorizationExample {

    public static void main(String[] args) {
        
        String pathToTexts = "texts";
//        String pathToTexts = "texts\\test";
//        TextVectorization vectorization = new TextVectorization("humanitariesVStechnics", pathToTexts);
//        vectorization.processFiles(pathToTexts + "\\humanitaries", "humanitaries");
//        vectorization.processFiles(pathToTexts + "\\technics", "technics");
//        vectorization.saveData();

        BagOfWordsVectorization bagOfWords = new BagOfWordsVectorization("humanitariesVStechnics-bag", pathToTexts);
        bagOfWords.processFiles(pathToTexts + "\\humanitaries", "humanitaries");
        bagOfWords.processFiles(pathToTexts + "\\technics", "technics");
        bagOfWords.saveData();

    }
}
