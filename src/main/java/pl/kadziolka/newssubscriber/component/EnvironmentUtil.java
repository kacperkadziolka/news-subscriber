package pl.kadziolka.newssubscriber.component;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class EnvironmentUtil {

    private String port;

    private String hostname;

    /*
    private Environment environment;

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
    */

    private HttpServletRequest httpServletRequest;

    private Environment environment;

    public EnvironmentUtil(HttpServletRequest httpServletRequest, Environment environment) {
        this.httpServletRequest = httpServletRequest;
        this.environment = environment;
    }

    public String getHostname() {
        if (hostname == null && httpServletRequest != null) hostname = httpServletRequest.getHeader("Host");
        return hostname;
    }

    public String getPort() {
        if (port == null) {
            port = environment.getProperty("local.server.port");
        }
        return port;
    }

}
