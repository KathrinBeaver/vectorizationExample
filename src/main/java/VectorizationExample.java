import bagofwords.BagOfWordsVectorization;
import preprocessing.AttributeType;
import preprocessing.TextPreprocessing;
import preprocessing.TextVectorization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class VectorizationExample {

    public static void main(String[] args) {

//        TextPreprocessing proc = new TextPreprocessing("Это 100% текст по 20 физике, что в 30 раз интереснее, т.к. 35.5 человек 45,0000. КПД двигателя очень высокий. ");
//        Map<AttributeType, String> attrsMap = proc.convertStringToAttrisbutesMap();
//        System.out.println(attrsMap);

        String pathToTexts = "texts";
//        String pathToTexts = "texts\\test";
        TextVectorization vectorization = new TextVectorization("humanitariesVStechnics", pathToTexts);
        vectorization.processFiles(pathToTexts + "\\humanitaries", "humanitaries");
        vectorization.processFiles(pathToTexts + "\\technics", "technics");
        vectorization.saveData();

        Set<String> categories = new HashSet<String>();
        categories.add("humanitaries");
        categories.add("technics");

        BagOfWordsVectorization bagOfWords = new BagOfWordsVectorization("humanitariesVStechnics-bag", pathToTexts, categories);
        bagOfWords.processFiles(pathToTexts);

        bagOfWords.saveData();

    }
}
