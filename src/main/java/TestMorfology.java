import preprocessing.AttributeType;
import preprocessing.TextPreprocessing;
import ru.textanalysis.tfwwt.jmorfsdk.jmorfsdk.JMorfSdk;
import ru.textanalysis.tfwwt.jmorfsdk.jmorfsdk.load.JMorfSdkLoad;
import ru.textanalysis.tfwwt.morphological.structures.grammeme.MorfologyParameters;
import ru.textanalysis.tfwwt.morphological.structures.grammeme.MorfologyParametersHelper;
import ru.textanalysis.tfwwt.morphological.structures.internal.OmoForm;
import ru.textanalysis.tfwwt.morphological.structures.storage.OmoFormList;

import java.util.*;

public class TestMorfology {

    public static void main(String[] args) throws Exception {

        TextPreprocessing proc = new TextPreprocessing("Это 100% текст по 20 физике, что в 30 раз интереснее, т.к. 35.5 человек 45,0000 ");
        Map<AttributeType, String> attrsMap = proc.convertStringToAttributesMap();
        System.out.println(attrsMap);

        GraphematicParser parser = new GParserImpl();
        List<List<String>> listBasicPhase = parser.parserSentence("Parser - это программа"
                + " начального анализа естественного текста, представленного в виде цепочки"
                + " символов, вырабатывающая информацию, необходимую для дальнейшей обработки.");
        System.out.println(listBasicPhase);


//        JMorfSdk jMorfSdk = JMorfSdkLoad.loadFullLibrary();
//
//        jMorfSdk.getAllCharacteristicsOfForm("дорогой").forEach(form -> {
//            if (form.getTheMorfCharacteristics(MorfologyParameters.Case.IDENTIFIER)
//                    == MorfologyParameters.Case.GENITIVE) {
//                System.out.println(form);
//            }
//        });
//
//        jMorfSdk.getAllCharacteristicsOfForm("дорогой").forEach(form -> {
//            if (form.getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN) {
//                System.out.println(form);
//            }
//        });
//
//
//        jMorfSdk.getAllCharacteristicsOfForm("село").forEach(form -> {
//            if (form.getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.VERB) {
//                System.out.println(form);
//            }
//        });
//
//        jMorfSdk.getAllCharacteristicsOfForm("село").forEach(form -> {
//            if (form.getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN) {
//                System.out.println(form);
//            }
//        });



//        List<Long> morfs = jMorfSdk.getMorfologyCharacteristics("стол");
//        OmoFormList oms = jMorfSdk.getAllCharacteristicsOfForm("стол");
//        oms.get(0).getTypeOfSpeech();
//        oms.get(0).getAllMorfCharacteristics();
//
//        for (long morf : morfs) {
//            System.out.println(morf);
//        }

//        jMorfSdk.getDerivativeForm("мыло", MorfologyParameters.TypeOfSpeech.NOUN, MorfologyParameters.Numbers.SINGULAR)
//                .forEach(word -> System.out.println(word));
//
//        System.out.println("------------------------");
//
//        jMorfSdk.getDerivativeForm("село", MorfologyParameters.TypeOfSpeech.NOUN, MorfologyParameters.Numbers.SINGULAR)
//                .forEach(word -> System.out.println(word));
//
//        System.out.println("------------------------");
//
        jMorfSdk.getDerivativeForm("дерево",
                MorfologyParameters.TypeOfSpeech.NOUN,
                MorfologyParameters.Numbers.SINGULAR)
                .forEach(System.out::println);

//        JMorfSdk jMorfSdk = JMorfSdkLoad.loadInAnalysisMode();

//        List<OmoForm> characteristics = jMorfSdk.getAllCharacteristicsOfForm("мама");
//        characteristics.forEach((form) -> {
//            System.out.println(form);
//        });


//        Map<String, Map<String, OmoForm>> allRes1 = new HashMap<>();
//
//        List<String> words1 = Arrays.asList("осенний", "осенней", "площадь", "стол", "играть", "конференций", "на", "бежала");
//        for (String word : words1) {
//            Map<String, OmoForm> res1 = new HashMap<>();
//            jMorfSdk.getAllCharacteristicsOfForm(word).forEach(form -> {
//                if (form.getTheMorfCharacteristics(MorfologyParameters.Gender.class) == MorfologyParameters.Gender.FEMININ) {
//                    res1.put(form.getInitialFormString(), form);
//                    System.out.println(form + " - " + word);
//                }
//            });
//            allRes1.put(word, res1);
//        }
//
//        for (String word : allRes1.keySet()) {
//            for (OmoForm form : allRes1.get(word).values()) {
//                System.out.println(form + " - " + word);
//            }
//        }
//
//        System.out.println("------------------------");
//
//        Map<String, OmoForm> res2 = new HashMap<>();
//
//        List<String> words2 = Arrays.asList("осенний", "осенней", "площадь", "стол", "играть", "конференций", "на", "бежала");
//        for (String word : words2) {
//            jMorfSdk.getAllCharacteristicsOfForm(word).forEach(form -> {
//                if (form.getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN) {
//                    res2.put(form.getInitialFormString(), form);
//                    System.out.println(form + " - " + word);
//                }
//            });
//        }
//
//        System.out.println("------------------------");
//
//        Map<String, OmoForm> res3 = new HashMap<>();
//
//        List<String> words3 = Arrays.asList("осенний", "осенней", "площадь", "стол", "играть", "конференций", "на", "бежала");
//        for (String word : words3) {
//            jMorfSdk.getAllCharacteristicsOfForm(word).forEach(form -> {
//                if (form.getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN
//                && form.getTheMorfCharacteristics(MorfologyParameters.Gender.class) == MorfologyParameters.Gender.FEMININ
//                ) {
//                    res2.put(form.getInitialFormString(), form);
//                    System.out.println(form + " - " + word);
//                }
//            });
//        }
//
//        System.out.println("------------------------");




//        List<OmoForm> characteristics10 = jMorfSdk.getAllCharacteristicsOfForm("дорогой");
//        characteristics10.forEach((form) -> {
//            System.out.println(form.getTheMorfCharacteristics(MorfologyParameters.TypeOfSpeech);
//        });
//
//
////        jMorfSdk.getMorfologyCharacteristics("дорогой").forEach(form -> {
////                System.out.println(form + " - " + word);
////        });
//
//        List<OmoForm> characteristics5 = jMorfSdk.getAllCharacteristicsOfForm("мыл");
//        characteristics5.forEach((form) -> {
//            System.out.println(form);
//        });



   /*     long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            jMorfSdk.getDerivativeForm("село", MorfologyParameters.TypeOfSpeech.NOUN);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Время обработки (10): " + (endTime - startTime) / 1000.0);
        double speed1 = 10.0 / ((endTime - startTime) / 1000.0);
        System.out.println("Скорость обработки (10): " + speed1);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            jMorfSdk.getDerivativeForm("село", MorfologyParameters.TypeOfSpeech.NOUN);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Время обработки (100): " + (endTime - startTime) / 1000.0);
        double speed2 = 100.0 / ((endTime - startTime) / 1000.0);
        System.out.println("Скорость обработки (100): " + speed2);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            jMorfSdk.getDerivativeForm("село", MorfologyParameters.TypeOfSpeech.NOUN);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Время обработки (1000): " + (endTime - startTime) / 1000.0);
        double speed3 = 1000.0 / ((endTime - startTime) / 1000.0);
        System.out.println("Скорость обработки (1000): " + speed3);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            jMorfSdk.getDerivativeForm("село", MorfologyParameters.TypeOfSpeech.NOUN);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Время обработки (10 000): " + (endTime - startTime) / 1000.0);
        double speed4 = 10000.0 / ((endTime - startTime) / 1000.0);
        System.out.println("Скорость обработки (10 000): " +speed4);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            jMorfSdk.getDerivativeForm("село", MorfologyParameters.TypeOfSpeech.NOUN);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Время обработки (100 000): " + (endTime - startTime) / 1000.0);
        double speed5 = 100000.0 / ((endTime - startTime) / 1000.0);
        System.out.println("Скорость обработки (100 000): " + speed5);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            jMorfSdk.getDerivativeForm("село", MorfologyParameters.TypeOfSpeech.NOUN);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Время обработки (1 000 000): " + (endTime - startTime) / 1000.0);
        double speed6 = 1000000.0 / ((endTime - startTime) / 1000.0);
        System.out.println("Скорость обработки (1 000 000): " + speed6);

//        startTime = System.currentTimeMillis();
//        for (int i = 0; i < 10000000; i++) {
//            jMorfSdk.getDerivativeForm("село", MorfologyParameters.TypeOfSpeech.NOUN);
//        }
//        endTime = System.currentTimeMillis();
//        System.out.println("Время обработки (10 000 000): " + (endTime - startTime) / 1000.0);
//        double speed7 = 10000000.0 / ((endTime - startTime) / 1000.0);
//        System.out.println("Скорость обработки (10 000 000): " + speed7);
//
//        startTime = System.currentTimeMillis();
//        for (int i = 0; i < 100000000; i++) {
//            jMorfSdk.getDerivativeForm("село", MorfologyParameters.TypeOfSpeech.NOUN);
//        }
//        endTime = System.currentTimeMillis();
//        System.out.println("Время обработки (100 000 000): " + (endTime - startTime) / 1000.0);
//        double speed8 = 100000000.0 / ((endTime - startTime) / 1000.0);
//        System.out.println("Скорость обработки (100 000 000): " + speed8);

        double avergeSpeed1 = (10 * speed1 + 100 * speed2 + 1000 * speed3 + 10000 * speed4 +
                100000 * speed5 + 1000000 * speed6) / 111111110.0;
        System.out.println("Скорость обработки (100 000 000): " + avergeSpeed1);

        double avergeSpeed2 = (speed1 + speed2 + speed3 + speed4 +
                speed5 + speed6 ) / 8.0;
        System.out.println("Скорость обработки (100 000 000): " + avergeSpeed2);
        */

    }
}
