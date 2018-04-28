package proxy;

import javafx.scene.control.TextArea;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Класс для настройки вывода логов прокси-сервера
 */
public class BasicConfigurationProxy extends BasicConfigurator {

    /*!
     метод для настройки вывода логов прокси-сервера
     \param[in] textArea экземпляр класса TextArea, который будет использоваться для вывода логов
    */
    public static void configure(TextArea textArea) {
        Logger root = Logger.getRootLogger();
        TextAreaAppender textAreaAppender = new TextAreaAppender(textArea);
        textAreaAppender.setLayout(new PatternLayout("%r [%t] %p %c %x - %m%n"));
        root.addAppender(textAreaAppender);
    }
}
