package preprocessing;

import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
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

public class TextVectorization {

    private JMorfSdk jMorfSdk;
    private String name = "dataset";
    private String folderPath = "dataset";
    private Map<String, List<Map<AttributeType, String>>> vector;
    private int textCount;

    public TextVectorization(String name, String folderPath) {
        vector = new HashMap<String, List<Map<AttributeType, String>>>();
        this.name = name;
        this.folderPath = folderPath;
        jMorfSdk = JMorfSdkFactory.loadFullLibrary();
        textCount = 0;
    }

    public void processFiles(String pathToTexts, String className) {
        vector.put(className, new ArrayList<Map<AttributeType, String>>());
        try (Stream<Path> paths = Files.walk(Paths.get(pathToTexts))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(fileName -> vectorizeText(fileName, className));
        } catch (IOException e) {
            Logger.getLogger("TextParser").log(Level.WARNING, "Не удалось получить доступ к папке: " + pathToTexts);
        }
    }

    public void vectorizeText(Path fileName, String className) {
        try {
            String text = new String(Files.readAllBytes(fileName));
            TextPreprocessing proc = new TextPreprocessing(text);
            proc.setjMorfSdk(jMorfSdk);
            Map<AttributeType, String> attributesMap = proc.convertStringToAttrisbutesMap();
            textCount++;
            System.out.println(textCount + "  " + fileName);
//            System.out.println(attributesMap);
            vector.get(className).add(attributesMap);
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

            writer.write("Attributes: ");
            writer.newLine();
            for (List<Map<AttributeType, String>> attributes : vector.values()){
                for (AttributeType attr : attributes.get(0).keySet()){
                    writer.write(attr.getCode());
                    writer.newLine();
                }
                break;
            }
            writer.newLine();
            writer.newLine();

            for (Map.Entry<String, List<Map<AttributeType, String>>> attributes : vector.entrySet()){
                for (Map<AttributeType, String> attrMap : attributes.getValue()) {
                    StringBuilder data = new StringBuilder();
//                    String data = "";
                    for (String value : attrMap.values()){
                       data.append(value);
                       data.append(",");
//                       data = data + value + ", ";
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
