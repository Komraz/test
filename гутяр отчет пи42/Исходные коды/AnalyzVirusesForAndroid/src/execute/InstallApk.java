package execute;

import javafx.application.Platform;
import utils.AlertDialogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс для установки .apk в эмулятор
 */
public class InstallApk {

    private static final String INSTALL = " install ";
    private static final String INSTALL_ERROR = "Ошибка при установке приложения";
    private static final String MORE_THAN_ONE_DEVICES = "error: more than one device/emulator";

    private String adb;

    /*!
     конструктор с параметрами
     \param[in] adb путь к adb файлу из Android SDK
    */
    public InstallApk(String adb) {
        this.adb = adb;
    }

    /*!
     метод для установки .apk в эмулятор
     \param[in] installPackage путь к .apk
    */
    public void installPackage(String installPackage) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(adb + INSTALL + installPackage);
        } catch (Exception e) {
            AlertDialogUtils.showDialog(INSTALL_ERROR, e.getMessage());
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        try {
            final String messageError;
            if ((messageError = bufferedReader.readLine()) != null) {
                if (MORE_THAN_ONE_DEVICES.equals(messageError)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialogUtils.showDialog(INSTALL_ERROR, messageError);
                        }
                    });
                }
            }
        } catch (IOException e) {}
    }
}
