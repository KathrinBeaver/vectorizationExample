package bagofwords;

import preprocessing.AttributeType;
import preprocessing.TextParser;
import preprocessing.TextPreprocessing;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class BagOfWordsVectorization {
    private String name = "dataset";
    private String folderPath = "dataset";
    private Map<String, List<List<Integer>>> vector;
    private List<List<String>> wordsList;
    private int textCount;

    public BagOfWordsVectorization(String name, String folderPath) {
        vector = new HashMap<String, List<List<Integer>>>();
        wordsList = new ArrayList<List<String>>();
        this.name = name;
        this.folderPath = folderPath;
        textCount = 0;
    }

    public void processFiles(String pathToTexts, String className) {
        vector.put(className, new ArrayList<List<Integer>>());
        try (Stream<Path> paths = Files.walk(Paths.get(pathToTexts))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(fileName -> vectorizeText(fileName, className));
        } catch (IOException e) {
            Logger.getLogger("TextParser").log(Level.WARNING, "Не удалось получить доступ к папке: " + pathToTexts);
        }
        BagOfWords bagofWords = new BagOfWords(wordsList);
        vector.put(className, bagofWords.createBag());
    }

    public void vectorizeText(Path fileName, String className) {
        try {
            String text = new String(Files.readAllBytes(fileName));
            TextParser graphematicParser = new TextParser(text);
            List<String> words = graphematicParser.getWordsInText();
            wordsList.add(words);
            textCount++;
            System.out.println(textCount + "  " + fileName);
        } catch (IOException e) {
            Logger.getLogger("TextParser").log(Level.WARNING, "Не удалось открыть файл: " + fileName);
        }
    }

    public void saveData() {
        String dataFile = folderPath + "\\" + name + ".txt";
        System.out.println("Save data to: " + dataFile);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(dataFile))) {
            writer.write("Dataset: " + name);
            writer.newLine();
            writer.newLine();

            writer.write("Classes: ");
            writer.newLine();
            for (String className : vector.keySet()){
                writer.write(className);
                writer.newLine();
            }
            writer.newLine();
            writer.newLine();

            // Map<String, List<List<List<Integer>>>>
            for (Map.Entry<String, List<List<Integer>>> attributes : vector.entrySet()){
                for (List<Integer> attrList : attributes.getValue()) {
                    StringBuilder data = new StringBuilder();
                    for (Integer value : attrList){
                        data.append(value);
                        data.append(",");
                    }
                    writer.write(data + attributes.getKey());
                    writer.newLine();
                }
            }
        }
        catch (IOException e) {
            Logger.getLogger("TextParser").log(Level.WARNING, "Не удалось сохранить файл: " + dataFile);
        }
        System.out.println("Save successful");
    }
}
