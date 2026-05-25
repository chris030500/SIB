package ahorrosbienestar.sib.std.docto_unificado.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

@Service
public class HashService {

    public static String getFileChecksum(MessageDigest digest, File file) throws IOException {

        byte[] byteArray = new byte[1024];

        try (FileInputStream fis = new FileInputStream(file)) {
            int bytesCount;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
        }

        byte[] bytes = digest.digest();

        StringBuilder sb = new StringBuilder();
        for (byte value : bytes) {
            sb.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}