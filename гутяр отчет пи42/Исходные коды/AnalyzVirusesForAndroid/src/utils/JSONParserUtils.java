package utils;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

/**
 * Класс для парсинга собранных логов в формате JSON
 */
public class JSONParserUtils {

    private static final String ENCODING_ERROR_TITLE = "Ошибка преобразования строки";
    private static final String ENCODING_ERROR_CONTENT = "Ошибка кодировки";
    private static final String DATA = "data";
    private static final String REGEX_STRING_TO_PART = "\"";
    private static final String PATH = "path";
    private static final String REGEX_DROID_BOX = "DroidBox:";

    /*!
     метод для создания json файла, содержищего логи
     \param[in] process экземпляр класса Process
     \return собранные данные
    */
    public static String createJsonFile(Process process) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String lineLog = null;
        StringBuilder lineJSON = new StringBuilder("{");
        FileWriter fileWriter = new FileWriter("logging.json");
        while ((lineLog = bufferedReader.readLine()) != null) {
            String[] json = lineLog.split(REGEX_DROID_BOX);
            if (json.length > 1) {
                String[] pasreArray = json[1].split(REGEX_STRING_TO_PART);
                lineJSON
                        .append(pasreArray[0])
                        .append(" ")
                        .append(pasreArray[1])
                        .append(" ")
                        .append(pasreArray[2])
                        .append(" ");
                for (int i = 3; i < pasreArray.length; i++) {
                    lineJSON.append(parseString(pasreArray, i));
                    i += 3;
                }
                lineJSON.append("\n");
            }
        }
        lineJSON.append("}");
        process.destroy();
        fileWriter.append(lineJSON.toString());
        fileWriter.close();
        return lineJSON.toString();
    }

    /*!
     метод для добавления преобразованной строки
     \param[in] array массив строк
     \param[in] num номер элемента в массиве
     \param[in] stringBuilder экземпляр класса StringBuilder
     \param[in] value значение, полученное в результате перевода hex в строку
    */
    private static void addParse(String[] array, int num,
                                   StringBuilder stringBuilder, String value) {
        stringBuilder
                .append(array[num])
                .append(" ")
                .append(array[++num])
                .append(" ")
                .append(value)
                .append(array[num + 2])
                .append(" ");
    }

    /*!
     метод парсинга строк
     \param[in] array массив строк
     \param[in] num номер элемента массива
     \return преобразованная строка
    */
    private static String parseString(String[] array, int num) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isPathOrData(array[num])) {
            addParse(array, num, stringBuilder, hexToString(array[num + 2]));
        } else {
            addParse(array, num, stringBuilder, array[num + 2]);
        }
        return stringBuilder.toString();
    }

    /*!
     метод для проверки является ли переданный ключ путем или данными
     \param[in] value значение ключа
     \return true/false
    */
    private static boolean isPathOrData(String value) {
        if (DATA.equals(value)) {
            return true;
        } else if (PATH.equals(value)) {
            return true;
        }
        return false;
    }

    /*!
     метод для перевода hex в строку
     \param[in] hex строка с шестнадцатиричными данными
     \return строка, полученная в результате перевода hex в строку
    */
    private static String hexToString(String hex) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            byte[] buf = DatatypeConverter.parseHexBinary(hex);
            stringBuilder.append(new String(buf, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            AlertDialogUtils.showInformationDialog(ENCODING_ERROR_TITLE,
                    ENCODING_ERROR_CONTENT);
        }
        return stringBuilder.toString();
    }
}
