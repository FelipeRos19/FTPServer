package fun.felipe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class HashUtils {

    public static String calculateFileHash(File file) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            try (InputStream inputStream = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    messageDigest.update(buffer, 0, read);
                }
            }

            byte[] hashBytes = messageDigest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02X", b));
            }

            return hexString.toString();
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
        return null;
    }
}
