package decompile;

/**
 * Класс-обертка над классом Decompile
 */
public class DecompileExecutor implements IDecompile {

    private Decompile decompile;

    /*!
     конструктор с параметром
     \param[in] pathToVirus путь к вредоносному .apk
    */
    public DecompileExecutor(String pathToVirus) {
        this.decompile = new Decompile(pathToVirus);
    }

    /*!
     метод конвертирония .apk в .jar
    */
    @Override
    public void apkToJar() {
        decompile.apkToJar();
    }

    /*!
     метод для декомпиляции .jar до исходных java-классов
    */
    @Override
    public void jarToJava() {
        decompile.jarToJava();
    }
}
