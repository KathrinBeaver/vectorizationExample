package preprocessing;

import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TextPreprocessing {

    private final String RULE_NUMBERS = "[0-9]+[.,]?[0-9]*";
    private final String RULE_FLOAT_NUMBERS = "[0-9]+[.,]{1}[0-9]+";
//    private final String RULE_MEASURES = "^[а-яА-я-]+$";
//    private final String RULE_ABBREVIATION = "^[а-яА-я-]+$";
    private final String RULE_PERCENTS = "\\d+[ ]?%";

    private Map<AttributeType, String> attributesMap = null;
    private String text = "";

    public TextPreprocessing (String text) {
        attributesMap = new HashMap<>();
        this.text = text;
    }

    public Map<AttributeType, String> convertStringToAttributesMap() {
        getSimpleAttrinbutes();
        getLingvisticsAttribute();
        return attributesMap;
    }

    /**
     *  NUMBERS_COUNT("numbers_count"), // Количество чисел
     *  HAS_FLOAT_NUMBERS("has_float_numbers"),   // Наличие чисел с плавающей запятой (1/0)
     *  HAS_MEASURES("has_measures"),   // Наличие единиц изменения (1/0)
     *  ABBREVIATION_COUNT("abbreviation_count"),   // Количество сокращений и аббревиатур (1/0)
     *  HAS_PERCENTS("has_percents"),  // Наличие процентов (1/0)
     */
    private void getSimpleAttrinbutes() {

        Pattern pattern = Pattern.compile(RULE_NUMBERS);
        final Matcher matcher = pattern.matcher(text);
        int numbersCount = Stream.iterate(0, i -> i + 1)
                .filter(i -> !matcher.find())
                .findFirst()
                .get();
        attributesMap.put(AttributeType.NUMBERS_COUNT, String.valueOf(numbersCount));

        pattern = Pattern.compile(RULE_FLOAT_NUMBERS);
        Matcher matcherNumbers = pattern.matcher(text);
        attributesMap.put(AttributeType.HAS_FLOAT_NUMBERS, matcherNumbers.find() ? "1" : "0");

        pattern = Pattern.compile(RULE_PERCENTS);
        Matcher matcherPercent = pattern.matcher(text);
        attributesMap.put(AttributeType.HAS_PERCENTS, matcherPercent.find() ? "1" : "0");
//        System.out.println("RegExp: " + matcher.matches());
    }

    /**
     *  NAMES_NUMBERS("names_numbers"),   // Количество имен собственных (1/0)
     *  ADJECTIVES_PERCENT("adjectives_percent"),  // Процент имен прилагательных
     *  AVERAGE_SENTENCE_LENGTH("average_sentence_length"),   // Средняя длина предложения
     *  AVERAGE_WORD_LENGTH("average_word_length");   // Средняя длина слова
     */
    private void getLingvisticsAttribute(){
        JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();
    }
}
