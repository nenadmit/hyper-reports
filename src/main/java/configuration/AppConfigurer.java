package configuration;

import deserialization.XmlParser;
import exception.ConfigFileNotFoundException;

import javax.xml.bind.JAXBException;
import java.io.File;

public class AppConfigurer {

    private static AppConfiguration configuration;
    private static File configFile;

    private AppConfigurer() {

    }

    public static AppConfiguration getConfiguration() {

        File file = new File("src/main/resources/Configuration.xml");

        if (configuration == null) {
            try {
                configuration = new XmlParser<AppConfiguration>(AppConfiguration.class)
                        .parse(file);

                configFile = file;
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
        return configuration;
    }

    public static File getConfigFile() {
        return configFile;
    }
}
