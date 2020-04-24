package jcommander;

import com.beust.jcommander.Parameter;

public class CommandLineParameters {


    @Parameter(names="config",
    description = "Used for configuring the import and export directories")
    private boolean config = false;

    @Parameter(names = {"--data-dir", "-dd"},
            required = false,
            description = "Path to directory for saving .xml files",
            validateWith = DirectoryParameterValidator.class)
    private String importDirectory;

    @Parameter(names = {"--export-dir", "-ed"},
            description = "Path to a directory for exporting .xlsx files",
            required = false,
            validateWith = DirectoryParameterValidator.class)
    private String exportDirectory;

    @Parameter(names={"process", "-p"},
    description = "Downloads data from sftp server and saves it to the database")
    private boolean process = false;

    @Parameter(names={"--help","-h"},
    description = "Shows available commands",
    help = true)
    private boolean help = false;

    @Parameter(names={"company","-c"})
    private String companyName;

    @Parameter(names={"--month","-m"})
    private int month;

    @Parameter(names={"--year","-y"})
    public int year;

    @Parameter(names={"--quarter","-q"})
    public String quarter;


    public boolean isConfig() {
        return config;
    }

    public void setConfig(boolean config) {
        this.config = config;
    }

    public String getImportDirectory() {
        return importDirectory;
    }

    public void setImportDirectory(String importDirectory) {
        this.importDirectory = importDirectory;
    }

    public String getExportDirectory() {
        return exportDirectory;
    }

    public void setExportDirectory(String exportDirectory) {
        this.exportDirectory = exportDirectory;
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }
}
