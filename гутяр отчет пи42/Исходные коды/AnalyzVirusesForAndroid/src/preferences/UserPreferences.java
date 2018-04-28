package preferences;

import java.util.prefs.Preferences;

/**
 * Класс для сохранения пользовательской конфигурации
 */
public class UserPreferences {

    private static final String USER_PREFERENCES = "user_preferences";
    private static final String PATH_EMULATOR = "path_emulator";
    private static final String PATH_ADB = "path_adb";
    private static final String PATH_AAPT = "path_aapt";

    private Preferences userPreferences;

    /*!
     конструктор без параметров
    */
    public UserPreferences() {
        userPreferences = Preferences.userRoot().node(USER_PREFERENCES);
    }

    /*!
     метод для сохранения пути к файлу emulator из Android SDK
     \param[in] pathEmulator путь к файлу emulator из Android SDK
    */
    public void setPathEmulator(String pathEmulator) {
        userPreferences.put(PATH_EMULATOR, pathEmulator);
    }

    /*!
     метод для сохранения пути к файлу adb из Android SDK
     \param[in] pathAdb путь к файлу adb из Android SDK
    */
    public void setPathAdb(String pathAdb) {
        userPreferences.put(PATH_ADB, pathAdb);
    }

    /*!
     метод для сохранения пути к файлу aapt из Android SDK
     \param[in] pathAapt путь к файлу aapt из Android SDK
    */
    public void setPathAapt(String pathAapt) {
        userPreferences.put(PATH_AAPT, pathAapt);
    }

    /*!
     метод для получения пути к файлу emulator из Android SDK
    */
    public String getPathEmulator() {
        return userPreferences.get(PATH_EMULATOR, null);
    }

    /*!
     метод для получения пути к файлу adb из Android SDK
    */
    public String getPathAdb() {
        return userPreferences.get(PATH_ADB, null);
    }

    /*!
     метод для получения пути к файлу aapt из Android SDK
    */
    public String getPathAapt() {
        return userPreferences.get(PATH_AAPT, null);
    }
}
