package configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sftpServer")
public class SftpConfiguration {

    private String host;
    private String username;
    private String password;
    private String rootDirectory;
    private String saveToUrl;
    private String knowHosts;
    private int port;

    public String getHost() {
        return host;
    }

    @XmlElement(name="host")
    public void setHost(String host) {
        this.host = host;
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

    public String getRootDirectory() {
        return rootDirectory;
    }

    @XmlElement(name="rootDirectory")
    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public String getKnowHosts() {
        return knowHosts;
    }

    @XmlElement(name="knownHosts")
    public void setKnowHosts(String knowHosts) {
        this.knowHosts = knowHosts;
    }

    public int getPort() {
        return port;
    }

    @XmlElement(name="port")
    public void setPort(int port) {
        this.port = port;
    }

    public String getSaveToUrl() {
        return saveToUrl;
    }

    @XmlElement(name="saveToUrl")
    public void setSaveToUrl(String saveToUrl) {
        this.saveToUrl = saveToUrl;
    }



}
