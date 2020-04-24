package sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.util.*;

public class SftpDownloader {

    private SftpDownloaderConfigurer configurer;
    private XmlFilesService fileService;
    private ChannelSftp channelSftp;

    public SftpDownloader(SftpDownloaderConfigurer configurer, XmlFilesService fileService) {

        this.fileService = fileService;
        this.configurer = configurer;

    }

    /**
     * Instantiates the session object using SftpConfigurer and creates an instance
     * of ChannelSftp.
     * Downloads files and saves them to @param saveFilePath
     *
     * @return List<File> of saved files.
     */

    public List<File> downloadFiles(String saveFilePath) {

        if(saveFilePath.charAt(saveFilePath.length()-1) != '/'){
            saveFilePath = saveFilePath + '/';
        }

        Session session = configurer.getSession();
        List<File> fileList = new ArrayList<>();
        List<String> filenameList = new ArrayList<>();

        try {

            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(configurer.getRootDirectory());

            List<ChannelSftp.LsEntry> list = channelSftp.ls("*");

            int counter = 0;
            System.out.println("Found: " + list.size() + " number of files!");
            for (ChannelSftp.LsEntry file : list) {
                Optional<String> optional = fileService.find(file.getFilename());

                if (!optional.isPresent()) {
                    System.out.println("Downloading file: " + file.getFilename() + " " + counter++ + "/" + list.size());
                    channelSftp.get(file.getFilename(), saveFilePath);
                    fileList.add(new File(saveFilePath + file.getFilename()));

                    filenameList.add(file.getFilename());
                }else{

                    System.out.println(file.getFilename() + " is already parsed - skipping! " + counter++ + "/" + list.size());
                }

            }

            fileService.saveAll(filenameList);

        } catch (SftpException | JSchException e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            channelSftp.disconnect();
        }

        return fileList;
    }

}



