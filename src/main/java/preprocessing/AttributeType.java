package preprocessing;

public enum AttributeType {

    NUMBERS_COUNT("numbers_count"), // Количество чисел
    HAS_FLOAT_NUMBERS("has_float_numbers"),   // Наличие чисел с плавающей запятой (1/0)
    HAS_MEASURES("has_measures"),   // Наличие единиц изменения (1/0)
    ABBREVIATION_COUNT("abbreviation_count"),   // Количество сокращений (1/0)
    NAMES_NUMBERS("names_numbers"),   // Количество имен собственных (1/0)
    HAS_PERCENTS("has_percents"),  // Наличие процентов (1/0)
    ADJECTIVES_PERCENT("adjectives_percent"),  // Процент имен прилагательных
    AVERAGE_SENTENCE_LENGTH("average_sentence_length"),   // Средняя длина предложения
    AVERAGE_WORD_LENGTH("average_word_length");   // Средняя длина слова

    private String code;

    AttributeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
