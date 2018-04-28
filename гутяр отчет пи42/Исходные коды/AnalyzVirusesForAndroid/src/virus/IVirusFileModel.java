package virus;

import java.io.File;

/**
 * Интерфейс для класса VirusFileModel
 */
public interface IVirusFileModel {
    /*!
     метод для изменения файла вредоносного .apk
     \param[in] file экземпляр класса File
    */
    void setFile(File file);
    /*!
     метод для изменения пути к файлу emulator из Android SDK
     \param[in] pathEmulator путь к файлу emulator из Android SDK
    */
    void setPathEmulator(String pathEmulator);
    /*!
     метод для изменения имени эмулятора
     \param[in] nameEmulator имя уже созданного эмулятора
    */
    void setNameEmulator(String nameEmulator);
    /*!
     метод для обновления путь к adb файлу из Android SDK
     \param[in] pathADB путь к adb файлу
    */
    void setPathADB(String pathADB);
    /*!
     метод для обновления пути к aapt файлу из Android SDK
     \param[in] pathAAPT путь к aapt файлу
    */
    void setPathAAPT(String pathAAPT);
    /*!
     метод для получения пути к файлу emulator
     \return путь к файлу emulator
    */
    String getPathEmulator();
    /*!
     метод для получения имени эмулятора
     \return имя эмулятора
    */
    String getNameEmulator();
    /*!
     метод для получения вредоносного .apk файда
     \return вредоносный .apk файл
    */
    File getFile();
    /*!
     метод для получения пути к файлу adb
     \return путь к файлу adb
    */
    String getPathADB();
    /*!
     метод для получения пути к файлу aapt
     \return путь к файлу aapt
    */
    String getPathAAPT();
}
