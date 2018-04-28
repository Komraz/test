package to.adb.or.aapt;

import utils.AlertDialogUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Класс для получания имени пакета и исполняем активити .apk файла
 */
public class Aapt implements IAapt {

    private static final String LAUNCHABLE = "%s dump badging %s | grep %s";
    private static final String ACTIVITY = "launchable-activity";
    private static final String PACKAGE = "package";

    private String AAPT;

    /*!
     конструктор с параметрами
     \param[in] AAPT путь к aapt файлу из Android SDK
    */
    public Aapt(String AAPT) {
        this.AAPT = AAPT;
    }

    /*!
     метод для получения пути из .apk
     \param[in] pathToFile путь к .apk
     \param[in] param строка с параметрами (package или launchable-activity)
     \return путь из .apk
    */
    private String getPath(String pathToFile, String param) {
        String path = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", String.format(LAUNCHABLE, AAPT, pathToFile, param)})
                                    .getInputStream()));
            path = bufferedReader.readLine();
            path = path.substring(path.indexOf("'") + 1, path.length());
            path = path.substring(0, path.indexOf("'"));
        } catch (Exception e) {
            AlertDialogUtils.showDialog(RUN_APP_ERROR, e.getMessage());
        }
        return path;
    }

    /*!
     метод для получения пути пакета из .apk
     \param[in] pathToFile путь к .apk файлу
     \return путь пакета из .apk
    */
    @Override
    public String getPathPackage(String pathToFile) {
        return getPath(pathToFile, PACKAGE);
    }

    /*!
     метод для получения пути исполняемой активити из .apk
     \param[in] pathToFile путь к файлу
     \return путь к исполняемой активити из .apk
    */
    @Override
    public String getPathMainActivity(String pathToFile) {
        return getPath(pathToFile, ACTIVITY);
    }
}