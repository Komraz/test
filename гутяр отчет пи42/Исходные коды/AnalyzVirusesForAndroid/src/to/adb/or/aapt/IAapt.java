package to.adb.or.aapt;

/**
 * Интерфейс, содержащий методы для получения пути пакета и исполняемой активити из .apk
 */
public interface IAapt {
    String RUN_APP_ERROR = "Ошибка запуска приложения";
    /*!
     метод для получения пути пакета из .apk
     \param[in] pathToFile путь к .apk файлу
     \return путь пакета из .apk
    */
    String getPathPackage(String pathPackage);
    /*!
     метод для получения пути исполняемой активити из .apk
     \param[in] pathToFile путь к файлу
     \return путь к исполняемой активити из .apk
    */
    String getPathMainActivity(String pathPackage);
}
