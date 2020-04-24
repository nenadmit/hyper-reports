package configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="dbConfiguration")
public class DbConfiguration {

    private String url;
    private String username;
    private String password;
    private String driver;

    public String getUrl() {
        return url;
    }

    @XmlElement(name="url")
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    @XmlElement(name="username")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement(name="password")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    @XmlElement(name="driver")
    public void setDriver(String driver) {
        this.driver = driver;
    }
}
