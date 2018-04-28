package virus;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Интерфейс для класса VirusFileController
 */
public interface IVirusFileController {
    /*!
     метод для обновления вредоносного .apk файла
     \param[in] file экземпляр класса File
    */
    void updateFile(File file);
    /*!
     метод для обновления пути к файлу emulator из Android SDK
     \param[in] pathEmulator путь к файлу emulator из Android SDK
    */
    void updatePathEmulator(String pathEmultor);
    /*!
     метод для обновления имени эмулятора
     \param[in] nameEmulator имя уже созданного эмулятора
    */
    void updateNameEmulator(String nameEmulator);
    /*!
     метод для обновления путь к adb файлу из Android SDK
     \param[in] pathADB путь к adb файлу
    */
    void updatePathADB(String pathADB);
    /*!
     метод для обновления пути к aapt файлу из Android SDK
     \param[in] pathAAPT путь к aapt файлу
    */
    void updatePathAAPT(String pathAAPT);
    /*!
     метод для получения пути к файлу emulator
     \return путь к файлу emulator
    */
    String getPathEmulator();
    /*!
     метод для получения пути к файлу adb
     \return путь к файлу adb
    */
    String getPathAdb();
    /*!
     метод для получения пути к файлу aapt
     \return путь к файлу aapt
    */
    String getPathAapt();
    /*!
     метод для получения данных из AndroidManifest
     \return путь к файлу emulator
    */
    Collection<String> scanVirusFileManifest();
    /*!
     метод для установки приложения в эмулятор
     \param[in] installPackageName путь к apk
    */
    void installApp(String installPackageName);
    /*!
     метод для запуска приложения в эмуляторе
    */
    void runApp() throws IOException, InterruptedException;
    /*!
     метод для запуска эмулятора
    */
    void runEmulator() throws IOException;
    /*!
     метод для сбора логов
    */
    String saveLogs() throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException;
}
