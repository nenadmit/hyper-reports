package configuration;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="fileDirectory")
public class DataDirectories {

    private String importDir;
    private String exportDir;


    public String getImportDir() {
        return importDir;
    }

    @XmlElement(name="import-dir")
    public void setImportDir(String importDir) {
        this.importDir = importDir;
    }

    public String getExportDir() {
        return exportDir;
    }

    @XmlElement(name="export-dir")
    public void setExportDir(String exportDir) {
        this.exportDir = exportDir;
    }

}
