package decompile;

/**
 * Интерфейс, содержащий методы для декомпиляции
 */
public interface IDecompile {

    /*!
     метод для конвертирования .apk в .jar
    */
    void apkToJar();

    /*!
     метод для декомпиляции .jar до исходных java-классов
     \param[in] text шифруемыей текст
    */
    void jarToJava();
}
