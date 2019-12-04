package bagofwords;

import preprocessing.TextParser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class BagOfWordsVectorization {
    private String name = "dataset";
    private String folderPath = "dataset";
    private Map<String, List<List<Integer>>> vector;
    private List<List<String>> wordsList;
    private Map<String, Integer> numberOfItemsInClass;

    public Set<String> getSetOfCategories() {
        return setOfCategories;
    }

    public void setSetOfCategories(Set<String> setOfCategories) {
        this.setOfCategories = setOfCategories;
    }

    private Set<String> setOfCategories;

    private int textCount;
    private long bagSize;

    public BagOfWordsVectorization(String name, String folderPath, Set<String> categories) {
        vector = new HashMap<String, List<List<Integer>>>();
        wordsList = new ArrayList<List<String>>();
        this.name = name;
        this.folderPath = folderPath;
        textCount = 0;
        setOfCategories = new HashSet<String>();
        if (categories != null) {
            setOfCategories = categories;
        }
        numberOfItemsInClass = new LinkedHashMap<String, Integer>();
    }

    public BagOfWordsVectorization(String name, String folderPath) {
        this(name, folderPath, null);
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths
                    .filter(Files::isDirectory)
                    .forEach(dirName -> setOfCategories.add(dirName.toString()));
        } catch (IOException e) {
            Logger.getLogger("TextParser").log(Level.WARNING, "Не удалось получить доступ к папке: " + folderPath);
        }
    }

    public void processFiles(String pathToTexts) {

        for (String className : setOfCategories) {
            try (Stream<Path> paths = Files.walk(Paths.get(pathToTexts + "\\" + className))) {
                paths.filter(Files::isRegularFile)
                        .forEach(fileName -> vectorizeText(fileName, className));
            } catch (IOException e) {
                Logger.getLogger("TextParser").log(Level.WARNING, "Не удалось получить доступ к папке: " + pathToTexts);
            }
        }
        BagOfWords bagofWords = new BagOfWords(wordsList);

        List<List<Integer>> bag = bagofWords.createBag();
        this.bagSize = bag.get(0).size();
        for (String className : numberOfItemsInClass.keySet()) {
            vector.put(className, new ArrayList<List<Integer>>());
            for (int i = 0; i < numberOfItemsInClass.get(className); i++) {
                vector.get(className).add(bag.get(i));
            }
        }
    }

    public void vectorizeText(Path fileName, String className) {

        if (!numberOfItemsInClass.containsKey(className)) {
            numberOfItemsInClass.put(className, 0);
        }

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
        numberOfItemsInClass.put(className, numberOfItemsInClass.get(className) + 1);
    }

    public void saveData() {
        String dataFile = folderPath + "\\" + name + ".arff";
        System.out.println("Save data to: " + dataFile);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(dataFile))) {
            writer.write("@relation " + name);
            writer.newLine();

            for (int i = 0; i < bagSize; i++) {
                writer.write("@attribute " + String.valueOf(i) + " numeric");
                writer.newLine();
            }

            writer.write("@attribute Species {");
            String classesNames = "";
            for (String className : vector.keySet()) {
                classesNames += "'" + className + "', ";
            }
            writer.write(classesNames.substring(0, classesNames.length() - 2));
            writer.write("}");
            writer.newLine();

            writer.write("@data ");
            writer.newLine();

            for (Map.Entry<String, List<List<Integer>>> attributes : vector.entrySet()){
                    for(List<Integer> list : attributes.getValue()) {
                        StringBuilder data = new StringBuilder();
                        for (Integer value : list) {
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
    public static void main(String[] args)
    {
        Set<String> categories = new HashSet<String>();
        categories.add("humanitaries");
        categories.add("technics");
        BagOfWordsVectorization bagOfWordsVectorization = new BagOfWordsVectorization("testVectors", ".\\texts", categories);
        bagOfWordsVectorization.processFiles(".\\texts");
        bagOfWordsVectorization.saveData();
    }
}
