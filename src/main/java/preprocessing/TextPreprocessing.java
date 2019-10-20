package preprocessing;

import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TextPreprocessing {

    private final String RULE_NUMBERS = "[0-9]+[.,]?[0-9]*";
    private final String RULE_FLOAT_NUMBERS = "[0-9]+[.,]{1}[0-9]+";
    private final String RULE_MEASURES = "(\\d+[.,]?\\d*)+\\s?\\[а-яА-Я]{1,2}[\\.]?";
    private final String RULE_ABBREVIATION = "[А-Я-]{2,}";
    private final String RULE_PERCENTS = "\\d+[ ]?%";

    public void setjMorfSdk(JMorfSdk jMorfSdk) {
        this.jMorfSdk = jMorfSdk;
    }

    private JMorfSdk jMorfSdk;

    private Map<AttributeType, String> attributesMap = null;
    private String text = "";

    public TextPreprocessing (String text) {
        attributesMap = new HashMap<>();
        this.text = text;
    }

    public Map<AttributeType, String> convertStringToAttrisbutesMap() {
        getSimpleAttributes();
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
    private void getSimpleAttributes() {
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

        pattern = Pattern.compile(RULE_ABBREVIATION);
        Matcher matcherAbbreviation = pattern.matcher(text);
        int abbrevCount = Stream.iterate(0, i -> i + 1)
                .filter(i -> !matcherAbbreviation.find())
                .findFirst()
                .get();
        attributesMap.put(AttributeType.ABBREVIATION_COUNT, String.valueOf(abbrevCount));

        pattern = Pattern.compile(RULE_MEASURES);
        Matcher matcherMeasures = pattern.matcher(text);
        attributesMap.put(AttributeType.HAS_MEASURES, matcherMeasures.find() ? "1" : "0");
    }

    /**
     *  NAMES_NUMBERS("names_numbers"),   // Количество имен собственных (1/0)
     *  ADJECTIVES_PERCENT("adjectives_percent"),  // Процент имен прилагательных
     *  AVERAGE_SENTENCE_LENGTH("average_sentence_length"),   // Средняя длина предложения
     *  AVERAGE_WORD_LENGTH("average_word_length");   // Средняя длина слова
     */
    private void getLingvisticsAttribute(){
//        GraphematicParser parser = new GParserImpl();
//        List<List<List<String>>> sentencesList = parser.parserParagraph(text);
        TextParser graphematicParser = new TextParser(text);
        List<List<String>> sentencesList = graphematicParser.getWordsInSentence();

        float averageSentenseLength = 0;
        float averageWordLength = 0;
        int wordsCount = 0;
        AtomicInteger namesCount = new AtomicInteger();
        Set<String> adjectivesSet = new HashSet<>();

        for (List<String> sentense : sentencesList) {
            averageSentenseLength += sentense.size();
            int pos = 0;
            for (String word : sentense) {
                averageWordLength += word.length();
                wordsCount++;

                jMorfSdk.getAllCharacteristicsOfForm(word).forEach(form -> {
                    if (word.length() > 2 && form.getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.ADJECTIVEFULL
                    || form.getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.ADJECTIVESHORT) {
                        adjectivesSet.add(word);
                        return;
                    }
                });

                if (pos != 0 && !word.equals(word.toLowerCase())) {
                    jMorfSdk.getAllCharacteristicsOfForm(word).forEach(form -> {
                        if (form.getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN) {
                            namesCount.getAndIncrement();
                        }
                    });
                }
                pos++;
            }
        }

        averageSentenseLength /= sentencesList.size();
        attributesMap.put(AttributeType.AVERAGE_SENTENCE_LENGTH, String.valueOf(averageSentenseLength));
        averageWordLength /= wordsCount;
        attributesMap.put(AttributeType.AVERAGE_WORD_LENGTH, String.valueOf(averageWordLength));
        float adjectivesPercent = (adjectivesSet.size() / wordsCount) * 100;
        attributesMap.put(AttributeType.ADJECTIVES_PERCENT, String.valueOf(adjectivesPercent));
        attributesMap.put(AttributeType.NAMES_NUMBERS, String.valueOf(namesCount.get()));
    }
}
