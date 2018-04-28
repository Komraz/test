package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Утилитный класс для показа диалоговых окон
 */
public class AlertDialogUtils {

    private static final String ERROR = "Ошибка";
    private static final String LOGGING = "Логгирование";
    private static final String LOGGING_STOP = "Ведется сбор логгов";
    private static final String STOP = "Остановить";
    private static final String CLOSE = "Свернуть";
    private static final String WAIT_EXECUTE_OPERATION = "Ожидайте выполнение операции";

    /*!
     метод для отображения диалогового окна, предупреждающего об ошибке
     \param[in] headerText текст подзаголовка
     \param[in] contentText текст контента
    */
    public static void showDialog(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(headerText);
        alert.getDialogPane().setContent(new Label(contentText));
        alert.showAndWait();
    }

    /*!
     метод для отображения диалогового окна процесса логирования
     \param[in] process экземпляр класса Process
     \return собранные данные
    */
    public static String showLoggingDialog(Process process) throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LOGGING);
        alert.setHeaderText(LOGGING_STOP);
        alert.setGraphic(new ImageView(AlertDialogUtils.class
                .getResource("progress.gif").toExternalForm()));
        ButtonType stop = new ButtonType(STOP);
        alert.getButtonTypes().setAll(stop);
        Optional<ButtonType> click = alert.showAndWait();
        if (click.get() == stop) {
            Class<?> pid  = process.getClass();
            Field field = pid.getDeclaredField("pid");
            field.setAccessible(true);
            Runtime.getRuntime().exec("kill -2 " + field.getInt(process));
            return JSONParserUtils.createJsonFile(process);
        }
        return null;
    }

    /*!
     метод для отображения диалогового окна процесса
     \param[in] headerText текст подзаголовка
    */
    public static Alert showProcessDialog(String headerText) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(WAIT_EXECUTE_OPERATION);
        alert.setHeaderText(headerText);
        alert.setGraphic(new ImageView(AlertDialogUtils.class
                .getResource("progress.gif").toExternalForm()));
        ButtonType close = new ButtonType(CLOSE);
        alert.getButtonTypes().setAll(close);
        return alert;
    }

    /*!
     метод для отображения диалоговых окно в зависимости от типа
     \param[in] title текст заголовка
     \param[in] headerText текст подзаголовка
     \param[in] alertType тип диалогового окна
    */
    private static void showDialog(String title, String headerText,
                                   Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    /*!
     метод для отображения информационного диалогового окна
     \param[in] title текст заголовка
     \param[in] headerText текст подзаголовка
     \param[in] alertType тип диалогового окна
    */
    public static void showInformationDialog(String title, String headerText) {
        showDialog(title, headerText, Alert.AlertType.INFORMATION);
    }

    /*!
     метод для отображения диалогового окна, предупреждающего об ошибке
     \param[in] title текст заголовка
     \param[in] headerText текст подзаголовка
     \param[in] alertType тип диалогового окна
    */
    public static void showErrorDialog(String title, String headerText) {
        showDialog(title, headerText, Alert.AlertType.ERROR);
    }
}
