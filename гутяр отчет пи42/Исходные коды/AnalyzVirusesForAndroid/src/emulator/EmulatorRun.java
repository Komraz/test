package emulator;

import java.io.IOException;

/**
 * Класс-обертка для класса Emulator
 */
public class EmulatorRun implements IEmulator {
    private Emulator emulator;

    /*!
     конструктор с параметром
     \param[in] экземпляр класса Emulator
    */
    public EmulatorRun(Emulator emulator) {
        this.emulator = emulator;
    }

    /*!
     метод для запуска эмулятора
    */
    @Override
    public void execute() throws IOException {
        emulator.runEmulator();
    }
}
