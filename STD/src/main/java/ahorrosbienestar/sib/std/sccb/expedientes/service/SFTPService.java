package ahorrosbienestar.sib.std.sccb.expedientes.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.*;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuproducto.conf.controller.ParametrosConfigController;

import java.io.ByteArrayOutputStream;


@Service
public class SFTPService {
	
	private static final Logger logger = Logger.getLogger(ParametrosConfigController.class);
	private String host = "10.150.102.45";
	private String username = "SCCB";
	private String password = "5ENDmCMLmj";
	private int port = 22;
	
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
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return null;
			
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
            // Intentamos obtener los atributos del archivo
            SftpATTRS attrs = channelSftp.stat(filePath);
            // Si no es nulo, significa que el archivo existe
            return attrs != null;
        } catch (SftpException e) {
            // Si ocurre una excepción al intentar obtener los atributos, el archivo no existe
            return false;
        }
    }
}
