package download;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Класс для поиска .apk по файловой системе эмулятора (в том числе установленных), а также для скачивания,
 * найденных .apk
 */
public class DownloadApk {

    private static final String SHELL = " shell ";
    private static final String SEARCH_FILES = "ls **/*.apk";
    private static final String SEARCH_INSTALL_PACKAGES = "pm list packages -f";
    private static final String FILES_NOT_FOUND = "**/*.apk: No such file or directory";
    private static final String SPLIT_PACKAGE = "package:";
    private static final String SPLIT_EQUALS = "=";
    private static final String PULL = " pull ";

    private String pathToAdb;

    /*!
     конструктор с параметром
     \param[in] pathToAdb путь к adb файул из Android SDK
    */
    public DownloadApk(String pathToAdb) {
        this.pathToAdb = pathToAdb;
    }

    /*!
     метод для поиска .apk по файловой системе, а также установленных .apk
     \return список, найденных на эмуляторе .apk файлов
    */
    public ObservableList<String> getListApk() {
        ObservableList<String> listApk = FXCollections.observableList(new ArrayList<>());
        try {
            Process process = Runtime.getRuntime()
                    .exec(pathToAdb + SHELL + SEARCH_FILES);
            listApk.addAll(getListFiles(process, true));
            process = Runtime.getRuntime()
                    .exec(pathToAdb + SHELL + SEARCH_INSTALL_PACKAGES);
            listApk.addAll(getListFiles(process, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listApk;
    }

    /*!
     метод для формирования списка найденных .apk
     \return список .apk файлов
    */
    private Collection<String> getListFiles(Process process, boolean filesNotFound) {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        Collection<String> listApk = new ArrayList<>();
        try {
            String pathToApk = bufferedReader.readLine();
            if (filesNotFound && !FILES_NOT_FOUND.equals(pathToApk)) {
                listApk.add(pathToApk);
            } else if (!filesNotFound) {
                listApk.add(getPath(pathToApk));
            }
            while ((pathToApk = bufferedReader.readLine()) != null) {
                if (!filesNotFound) {
                    listApk.add(getPath(pathToApk));
                } else {
                    listApk.add(pathToApk);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listApk;
    }

    /*!
     метод для получения пути к .apk
     \param[in] pathToApk строка, в которой содержится путь к .apk
     \return путь к .apk
    */
    private String getPath(String pathToApk) {
        return pathToApk.split(SPLIT_PACKAGE)[1].split(SPLIT_EQUALS)[0];
    }

    /*!
     метод для скачавания .apk с эмулятора
     \param[in] pathToApk путь к .apk на эмуляторе
     \param[in] pathToDownload путь для скачивания
    */
    public void download(String pathToApk, String pathToDownload) {
        try {
            Runtime.getRuntime().exec(pathToAdb + PULL + pathToApk + " " + pathToDownload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
