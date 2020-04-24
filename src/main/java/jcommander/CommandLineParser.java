package jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import configuration.AppConfiguration;
import configuration.AppConfigurer;
import configuration.DataDirectories;
import database.CompanyDataProcessor;
import deserialization.XmlParser;
import exception.ConfigFileNotFoundException;
import sftp.SftpDownloader;

import java.io.File;
import java.util.List;

public class CommandLineParser {

    private AppConfiguration configuration;
    private DataDirectories directories;
    private SftpDownloader downloader;
    private CompanyDataProcessor dataProcessor;
    private CommandLineParameters parameters;

    public CommandLineParser(SftpDownloader downloader, CompanyDataProcessor dataProcessor) {

        this.downloader = downloader;
        this.dataProcessor = dataProcessor;

        this.configuration = AppConfigurer.getConfiguration();
        this.directories = configuration.getDataDirectories();
    }


    public void parse(String[] args) {

        JCommander commander = new JCommander(parameters);
        commander.setProgramName("hyper-reports");

        try {
            commander.parse(args);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
        }

        if (parameters.isHelp()) {
            commander.usage();
            return;
        }

        if (parameters.isConfig()) {
            if (parameters.getImportDirectory() != null)
                setImportDirectory(parameters.getImportDirectory());

            if (parameters.getExportDirectory() != null)
                setExportDirectory(parameters.getExportDirectory());
        }

        if (parameters.isProcess()) {
            if (directories.getImportDir().isEmpty()) {
                System.out.println("Import directory is not set");
                System.out.println("Use command -> config --data-dir | -dd <directory path> -" +
                        " for setting up a directory for storing .xml data");
            } else {
                processFiles();
                System.out.println("All .xml files have been processed!");
            }
        }

    }

    public void setImportDirectory(String dataDirectory) {
        directories.setImportDir(dataDirectory);

        XmlParser parser = new XmlParser(AppConfiguration.class);
        parser.marshall(configuration, AppConfigurer.getConfigFile());
    }

    public void setExportDirectory(String exportDirectory) {
        directories.setExportDir(exportDirectory);

        XmlParser parser = new XmlParser(AppConfiguration.class);
        parser.marshall(configuration, AppConfigurer.getConfigFile());

    }

    public List<File> downloadDataFromFtpServer() {

        return downloader.downloadFiles(directories.getImportDir());
    }

    public void processFiles() {

        List<File> dataFiles = downloadDataFromFtpServer();
        dataFiles.stream().forEach(file -> dataProcessor.saveData(file));

    }

    public void setParameters(CommandLineParameters parameters) {
        this.parameters = parameters;
    }
}
