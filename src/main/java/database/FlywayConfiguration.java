package database;

import configuration.AppConfigurer;
import configuration.DbConfiguration;
import exception.ConfigFileNotFoundException;
import org.flywaydb.core.Flyway;

public class FlywayConfiguration {

    private static Flyway flyway;

    public FlywayConfiguration() {
    }

    public static Flyway getFlyway() {

        if (flyway == null) {

            DbConfiguration config = AppConfigurer.getConfiguration().getDbConfiguration();

            flyway = Flyway.configure().dataSource(
                    config.getUrl(),
                    config.getUsername(),
                    config.getPassword()
            ).load();

        }

        return flyway;
    }
}
