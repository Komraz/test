package to.adb.or.aapt;

import java.io.IOException;

/**
 * Интерфейс, содержащий методы для запуска приложения и логирования эмулятора
 */
public interface IAdb {
    /*!
     метод для запуска приложения в эмуляторе
     \param[in] pathPackage путь к пакету из .apk/путь к исполняемой активити из .apk
    */
    void runApp(String pathPackage) throws IOException;
    /*!
     метод для запуска логирования эмулятора
     \return собранные данные
    */
    String logging() throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException;
}
