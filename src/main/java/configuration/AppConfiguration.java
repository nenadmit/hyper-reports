package configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="globalConfiguration")
public class AppConfiguration {


    private SftpConfiguration sftpConfiguration;
    private DbConfiguration dbConfiguration;
    private DataDirectories dataDirectories;

    public SftpConfiguration getSftpConfiguration() {
        return sftpConfiguration;
    }

    @XmlElement(name="sftpServer")
    public void setSftpConfiguration(SftpConfiguration sftpConfiguration) {
        this.sftpConfiguration = sftpConfiguration;
    }

    @XmlElement(name="DbConfiguration")
    public void setDbConfiguration(DbConfiguration dbConfiguration){
        this.dbConfiguration = dbConfiguration;
    }

    public DbConfiguration getDbConfiguration() {
        return dbConfiguration;
    }

    @XmlElement(name="dataDirectories")
    public void setDataDirectories(DataDirectories dataDirectories){
        this.dataDirectories = dataDirectories;
    }

    public DataDirectories getDataDirectories() {
        return dataDirectories;
    }


}
