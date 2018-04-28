package emulator;

import java.io.IOException;

/**
 * Интерфейс, содержащий метод для запуска эмулятора
 */
public interface IEmulator {

    /*!
     метод для запуска эмулятора
    */
    void execute() throws IOException;
}
