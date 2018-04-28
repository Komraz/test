package proxy;

import javafx.scene.control.TextArea;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.littleshoot.proxy.mitm.CertificateSniffingMitmManager;
import org.littleshoot.proxy.mitm.RootCertificateException;

/**
 * Класс для запуска и остановки прокси-сервера
 */
public class ProxyServer {

    private static volatile ProxyServer proxyServer;

    private HttpProxyServer proxy;

    /*!
     метод для получения экземпляра класса ProxyServer
     \return экземпляр класса прокси-сервер
    */
    public static ProxyServer getInstance() {
        ProxyServer localProxyServer = proxyServer;
        if (localProxyServer == null) {
            synchronized (ProxyServer.class) {
                localProxyServer = proxyServer;
                if (localProxyServer == null) {
                    proxyServer = localProxyServer = new ProxyServer();
                }
            }
        }
        return proxyServer;
    }

    /*!
     метод для запуска прокси сервера
     \param[in] textArea экземпляр класса TextArea, который будет использоваться для вывода логов
    */
    public void runServer(TextArea textArea) {
        try {
            BasicConfigurationProxy.configure(textArea);
            proxy = DefaultHttpProxyServer.bootstrap()
                    .withPort(9090)
                    .withManInTheMiddle(new CertificateSniffingMitmManager())
                    .start();
        } catch (RootCertificateException e) {
            e.printStackTrace();
        }
    }

    /*!
     метод для остановки прокси-сервера
    */
    public void stopServer() {
        if (proxy != null) {
            proxy.stop();
        }
    }
}
