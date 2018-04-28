package virus;

import java.io.File;

/**
 * Класс-модель для хранения информации о настройках анализатора
 */
public class VirusFileModel implements IVirusFileModel {
    private File file;
    private String pathEmulator;
    private String nameEmulator;
    private String pathADB;
    private String pathAAPT;

    /*!
     метод для изменения файла вредоносного .apk
     \param[in] file экземпляр класса File
    */
    @Override
    public void setFile(File file) {
        this.file = file;
    }

    /*!
     метод для изменения пути к файлу emulator из Android SDK
     \param[in] pathEmulator путь к файлу emulator из Android SDK
    */
    @Override
    public void setPathEmulator(String pathEmulator) {
        this.pathEmulator = pathEmulator;
    }

    /*!
     метод для изменения имени эмулятора
     \param[in] nameEmulator имя уже созданного эмулятора
    */
    @Override
    public void setNameEmulator(String nameEmulator) {
        this.nameEmulator = nameEmulator;
    }

    /*!
     метод для получения пути к файлу emulator
     \return путь к файлу emulator
    */
    @Override
    public String getPathEmulator() {
        return pathEmulator;
    }

    /*!
     метод для получения имени эмулятора
     \return имя эмулятора
    */
    @Override
    public String getNameEmulator() {
        return nameEmulator;
    }

    /*!
     метод для получения вредоносного .apk файда
     \return вредоносный .apk файл
    */
    @Override
    public File getFile() {
        return file;
    }

    /*!
     метод для обновления путь к adb файлу из Android SDK
     \param[in] pathADB путь к adb файлу
    */
    @Override
    public void setPathADB(String pathADB) {
        this.pathADB = pathADB;
    }

    /*!
     метод для обновления пути к aapt файлу из Android SDK
     \param[in] pathAAPT путь к aapt файлу
    */
    @Override
    public void setPathAAPT(String pathAAPT) {
        this.pathAAPT = pathAAPT;
    }

    /*!
     метод для получения пути к файлу adb
     \return путь к файлу adb
    */
    @Override
    public String getPathADB() {
        return pathADB;
    }

    /*!
     метод для получения пути к файлу aapt
     \return путь к файлу aapt
    */
    @Override
    public String getPathAAPT() {
        return pathAAPT;
    }
}
