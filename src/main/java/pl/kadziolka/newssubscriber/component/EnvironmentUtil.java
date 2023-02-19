package pl.kadziolka.newssubscriber.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class EnvironmentUtil {

    private Environment environment;

    private String port;

    private String hostname;

    @Autowired
    public EnvironmentUtil(Environment environment) {
        this.environment = environment;
    }

    public String getPort() {
        if (port == null) {
            port = environment.getProperty("local.server.port");
        }
        return port;
    }

    public String getHostname() throws UnknownHostException {
        if (hostname == null) {
            hostname = InetAddress.getLocalHost().getHostAddress();
        }
        return hostname;
    }
}
