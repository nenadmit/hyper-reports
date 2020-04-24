package sftp;

import com.jcraft.jsch.*;
import configuration.AppConfigurer;
import deserialization.XmlParser;
import configuration.AppConfiguration;
import configuration.SftpConfiguration;
import exception.ConfigFileNotFoundException;

import javax.xml.bind.JAXBException;
import java.io.File;

public class SftpDownloaderConfigurer {

    private SftpConfiguration config;

    public SftpDownloaderConfigurer() {

        this.config = AppConfigurer.getConfiguration().getSftpConfiguration();

    }

    /**
     * Parses the global configuration files and return a
     * Configuration class for a Sftp Server
     * TODO - Making a class with static method, which will make global configuration class available everywhere
     */

    private SftpConfiguration getConfig(File configFilePath) throws JAXBException {

        XmlParser<AppConfiguration> parser = new XmlParser<>(AppConfiguration.class);

        return parser.parse(configFilePath).getSftpConfiguration();
    }

    /**
     * Configures JSch and JSch session objects using the SftpConfiguration file
     * and data from Configuration.xml
     *
     * @return configured Session object
     */

    public Session getSession() {

        JSch jsch = new JSch();
        Session session = null;


        try {

            if (!config.getKnowHosts().isEmpty()) {
                jsch.setKnownHosts(config.getKnowHosts());
            }

            session = jsch.getSession(config.getUsername(),
                    config.getHost(),
                    config.getPort());
            session.setPassword(config.getPassword());

            if (config.getKnowHosts().isEmpty()) {
                session.setConfig("StrictHostKeyChecking", "no");
            }


        } catch (JSchException e) {
            e.printStackTrace();
        }

        return session;
    }

    public String getRootDirectory() {
        return config.getRootDirectory();
    }


}
