package to.adb.or.aapt;

import utils.AlertDialogUtils;

import java.io.IOException;

/**
 * Класс для запуска приложения в эмуляторе и логирование эмулятора
 */
public class Adb implements IAdb {
    private static final String RUN_APP = "%s shell am start -n %s";
    private static final String LOGCAT = " logcat DroidBox:W dalvikvm:W ActivityManager:I";
    private static final String LOGCAT_CLEAR = " logcat -c";

    private String ADB;

    /*!
     конструктор с параметрами
     \param[in] ADB путь к adb файлу из Android SDK
    */
    public Adb(String ADB) {
        this.ADB = ADB;
    }

    /*!
     метод для запуска приложения в эмуляторе
     \param[in] pathPackage путь к пакету из .apk/путь к исполняемой активити из .apk
    */
    @Override
    public void runApp(String pathPackage) throws IOException {
        Runtime.getRuntime().exec(String.format(RUN_APP, ADB, pathPackage));
    }

    /*!
     метод для запуска логирования эмулятора
     \return собранные данные
    */
    @Override
    public String logging() throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        Runtime.getRuntime().exec(ADB + LOGCAT_CLEAR);
        Process process = Runtime.getRuntime().exec(ADB + LOGCAT);
        return AlertDialogUtils.showLoggingDialog(process);
    }
}
