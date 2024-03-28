package dev.arzak21st.prayertimes.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PreferencesUtil {

    /* ==================== Preferences Methods ==================== */
    public static String getPreference(String key) {

        String preference = null;

        try {

            Properties properties = new Properties();
            File file = getPreferencesFile();
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            preference = properties.getProperty(key);
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return preference;
    }

    public static void savePreference(String key, String value) {

        try {

            Properties properties = new Properties();
            File file = getPreferencesFile();
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            properties.setProperty(key, value);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, "Preferences");
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public static File getPreferencesFile() {

        String filePath = System.getProperty("user.dir") + File.separator + "prayer-times-preferences.properties";
        File file = new File(filePath);
        if(!file.exists()) {

            try {
                file.createNewFile();
            }
            catch(Exception exception) {
                ExceptionHandlerUtil.getException(exception);
            }
        }
        return file;
    }
}
