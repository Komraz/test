package to.adb.or.aapt;

import utils.AlertDialogUtils;

import java.io.IOException;

/**
 * Класс, обеспечивающий взаимодействие между реализациями интерфейсов IAapt и IAdb
 */
public class IndirectTo {
    private IAapt aapt;
    private IAdb adb;

    /*!
     конструктор с параметрами
     \param[in] aapt экземпляр класса, реализующего интерфейс IAapt
     \param[in] adb экземпляр класса, реализующего интерфейс IAdb
    */
    public IndirectTo(IAapt aapt, IAdb adb) {
        this.aapt = aapt;
        this.adb = adb;
    }

    /*!
     метод для запуска приложения в эмуляторе
     \param[in] pathToFile путь к .apk
    */
    public void runApp(String pathToFile)  {
        if (!pathToFile.isEmpty()) {
            String pathPackage = aapt.getPathPackage(pathToFile);
            String pathActivity = aapt.getPathMainActivity(pathToFile);
            try {
                adb.runApp(pathPackage + "/" + pathActivity);
            } catch (IOException e) {
                AlertDialogUtils.showDialog(Aapt.RUN_APP_ERROR, e.getMessage());
            }
        }
    }

    /*!
     метод для запуска логирования эмулятора
     \return собранные данные
    */
    public String logging() throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        return adb.logging();
    }
}
