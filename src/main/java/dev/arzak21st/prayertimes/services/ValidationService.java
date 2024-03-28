package dev.arzak21st.prayertimes.services;

import dev.arzak21st.prayertimes.utils.ExceptionHandlerUtil;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ValidationService {

    /* ==================== Validation Methods ==================== */
    public boolean validateDate(String date) {

        try {

            String datePattern = "\\d{4}-\\d{2}-\\d{2}";

            if(date.matches(datePattern)) {
                return true;
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }

    public boolean validateDuration(int duration) {

        try {

            if(duration >= 5 && duration <= 60) {
                return true;
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }

    public boolean validateDirectory(String directory) {

        try {

            String[] invalidSymbols = {"<", ">", "?", "\"", "|", "*"};

            if(directory.length() > 0) {

                if(!directory.contains(invalidSymbols[0]) && !directory.contains(invalidSymbols[1]) && !directory.contains(invalidSymbols[2]) && !directory.contains(invalidSymbols[3]) && !directory.contains(invalidSymbols[4]) && !directory.contains(invalidSymbols[5])) {

                    File fileDirectory = new File(directory);
                    if(fileDirectory.exists()) {
                        return true;
                    }
                }
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }

    public boolean validateURL(String urlString) {

        try {

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) { // If responseCode = 200
                return true;
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }

    public boolean validateCSVFile(String filePath) {

        CSVParser data = null;
        Reader reader = null;

        try {

            String datePattern = "\\d{4}-\\d{2}-\\d{2}";
            String timePattern = "((1[0-2])|([1-9])):\\d{2} [AP]M";
            String[] booleanValues = {"TRUE", "FALSE"};

            // Read the CSV file data
            reader = new FileReader(filePath);
            data = new CSVParser(reader, CSVFormat.DEFAULT); // Iterable

            for(CSVRecord record : data) {

                if(record.getRecordNumber() > 1) { // If the current record isn't the hreaders record

                    if(!record.get(1).matches(datePattern) || !record.get(3).matches(datePattern)
                            || !record.get(2).matches(timePattern) || !record.get(4).matches(timePattern)
                            || (!record.get(8).equals(booleanValues[0]) && !record.get(8).equals(booleanValues[1]))) { // Check if: Dates don't matche the format "yyyy-MM-dd" | Times don't matche the format: HH:mm AM/PM | Booleans are other than "TRUE" or "FALSE"
                        return false;
                    }
                }
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return true;
    }

    boolean validateCalendarId(String calendarId) {

        try {

            String calendarIdPattern = "[a-zA-Z0-9]+@group\\.calendar\\.google\\.com";

            if(calendarId.matches(calendarIdPattern)) {
                return true;
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }
}
