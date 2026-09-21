package ahorrosbienestar.sib.std.sccb.expedientes.service;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import ahorrosbienestar.sib.std.comunes.Constantes;

@Service
public class SFTPService {

	private static final Logger logger =
	        LoggerFactory.getLogger(SFTPService.class);

    @Value("${sftp.sccb.host}")
    private String host;

    @Value("${sftp.sccb.username}")
    private String username;

    @Value("${sftp.sccb.password}")
    private String password;

    @Value("${sftp.sccb.port}")
    private int port;

    public byte[] findImageFiles(String baseRemoteDirectory, String[] directories, String pathFile) {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            JSch jsch = new JSch();

            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            for (String directory : directories) {
                String remotePath = baseRemoteDirectory + "/" + directory + "/Expedientes" + pathFile;

                if (fileExists(channelSftp, remotePath)) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    channelSftp.get(remotePath, outputStream);
                    return outputStream.toByteArray();
                }
            }

        } catch (JSchException | SftpException e) {
            logger.error(Constantes.MENSAJE_DE_ERROR, e);
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }

        return null;
    }

    private boolean fileExists(ChannelSftp channelSftp, String filePath) {
        try {
            SftpATTRS attrs = channelSftp.stat(filePath);
            return attrs != null;
        } catch (SftpException e) {
            logger.debug("Archivo no encontrado en SFTP: " + filePath, e);
            return false;
        }
    }
}