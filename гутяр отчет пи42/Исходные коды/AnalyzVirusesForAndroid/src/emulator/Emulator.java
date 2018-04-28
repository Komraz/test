package emulator;

import javafx.application.Platform;
import utils.AlertDialogUtils;
import utils.DialogMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс для запуска эмулятора
 */
public class Emulator {
    private static final String PARAMS_EMU = " -avd %s -system images/system.img " +
            "-ramdisk images/ramdisk.img " +
            "-wipe-data -prop dalvik.vm.execution-mode=int:portable " +
            "-http-proxy localhost:9090";
    private static final String RUN_EMU_ERROR = "При запуске эмулятора возникла ошибка";
    private String pathEmulator;
    private String nameEmulator;

    /*!
     конструктор с параметром
     \param[in] pathToEmulator путь к файлу emulator из Android SDK
     \param[in] nameEmulator название уже созданного эмулятора
    */
    public Emulator(String pathEmulator, String nameEmulator) {
        this.pathEmulator = pathEmulator;
        this.nameEmulator = nameEmulator;
    }

    /*!
     метод для запуска эмулятора
    */
    public void runEmulator() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Process processRun = null;
                try {
                    processRun = Runtime.getRuntime().exec(pathEmulator + String.format(PARAMS_EMU, nameEmulator));
                } catch (IOException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialogUtils.showDialog(RUN_EMU_ERROR, e.getMessage());
                        }
                    });
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processRun.getErrorStream()));
                try {
                    final String messageError;
                    if ((messageError = bufferedReader.readLine()) != null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialogUtils.showDialog(DialogMessage.RUN_EMU_ERROR, messageError);
                            }
                        });
                    }
                } catch (IOException e) {}
            }
        }).start();
    }
}
