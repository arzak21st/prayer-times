package dev.arzak21st.prayertimes.services;

import dev.arzak21st.prayertimes.utils.ExceptionHandlerUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CSVFileGenerationService {

    /* ==================== CSV File Generation Methods ==================== */
    public boolean generateCSVFile(String city, String date, int duration, String directory, String urlString, String hostName) {

        try {

            // Get prayers data
            Document document = getHTMLDocument(urlString);
            if(document != null) {

                Element prayerTimesTable = getPrayerTimesTable(document);
                if(prayerTimesTable != null) {

                    String[] prayersNames = getPrayersNames(prayerTimesTable);
                    String[] prayersTimes = getPrayersTimes(prayerTimesTable);
                    if(prayersNames != null && prayersTimes != null) {

                        // Prepare prayers data
                        String[] subjects = prepareSubjects(prayersNames);
                        String[] startDates = prepareStartDates(date);
                        String[] startTimes = prepareStartTimes(prayersTimes);
                        String[] endDates = prepareEndDates(startDates);
                        String[] endTimes = prepareEndTimes(duration, startTimes);
                        String allDayEvent = prepareAllDayEvent();
                        String description = prepareDescription();
                        String location = prepareLocation();
                        String privacy = preparePrivacy();
                        if(startDates != null) {

                            // Write prayers data
                            BufferedWriter fileWriter = createCSVFile(city, directory, hostName, startDates);
                            if(fileWriter != null) {

                                String[] fileHeaders = setCSVFileHeaders();
                                String[][] fileItems = setCSVFileItems(subjects, startDates, startTimes, endDates, endTimes, allDayEvent, description, location, privacy);

                                boolean csvFileHeadersAreWritten = writeCSVFileHeaders(fileWriter, fileHeaders);
                                boolean csvFileItemsAreWritten = writeCSVFileItems(fileWriter, fileItems);
                                if(csvFileHeadersAreWritten && csvFileItemsAreWritten) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }

    /* ==================== Getting Prayers Data Methods ==================== */
    private Document getHTMLDocument(String urlString) {

        Connection connection = null;
        Document document = null;

        try {

            connection = Jsoup.connect(urlString);
            document = connection.get();
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return document;
    }

    private Element getPrayerTimesTable(Document document) {

        Element prayerTimesTable = null;

        try {
            prayerTimesTable = document.getElementsByTag("table").first();
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return prayerTimesTable;
    }

    private String[] getPrayersNames(Element prayerTimesTable) {

        String[] prayersNames = null;

        try {

            int prayersRowIndex = 0;
            int firstPrayerColumnIndex = 1;

            ArrayList<String> prayersNamesList = new ArrayList<String>();
            Element prayersRow = prayerTimesTable.getElementsByTag("tr").get(prayersRowIndex);

            for(int columnIndex = firstPrayerColumnIndex; columnIndex < prayersRow.children().size(); columnIndex++) {

                Element prayer = prayersRow.children().get(columnIndex);
                prayersNamesList.add(prayer.text());
            }

            prayersNames = prayersNamesList.toArray(new String[prayersNamesList.size()]);
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return prayersNames;
    }

    private String[] getPrayersTimes(Element prayerTimesTable) {

        String[] prayersTimes = null;

        try {

            int firstDayRowIndex = 1;
            int firstPrayerColumnIndex = 1;

            ArrayList<String> prayersTimesList = new ArrayList<String>();
            Elements prayerTimesTableRows = prayerTimesTable.getElementsByTag("tr");

            for(int rowIndex = firstDayRowIndex; rowIndex < prayerTimesTableRows.size(); rowIndex++) {

                Element prayerTimesRow = prayerTimesTableRows.get(rowIndex);
                for(int columnIndex = firstPrayerColumnIndex; columnIndex < prayerTimesRow.children().size(); columnIndex++) {

                    Element prayerTimes = prayerTimesRow.children().get(columnIndex);
                    prayersTimesList.add(prayerTimes.text());
                }
            }

            prayersTimes = prayersTimesList.toArray(new String[prayersTimesList.size()]);
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return prayersTimes;
    }

    /* ==================== Writing Prayers Data Methods ==================== */
    private String[] prepareSubjects(String[] prayersNames) {

        String[] subjects = prayersNames;
        return subjects;
    }

    private String[] prepareStartDates(String date) {

        String[] startDates = null;
        ArrayList<String> daysOfMonth = new ArrayList<String>();

        try {

            LocalDate localDate = LocalDate.parse(date); // Parse the date string to LocalDate object
            int year = localDate.getYear();
            int month = localDate.getMonthValue();

            YearMonth yearMonth = YearMonth.of(year, month);
            int monthDays = yearMonth.lengthOfMonth();

            for(int dayIndex = 1; dayIndex <= monthDays; dayIndex++) { // Loop through all days of the month

                String yearAsString = String.valueOf(year);
                String monthAsString = String.valueOf(String.format("%02d", month));
                String dayAsString = String.valueOf(String.format("%02d", dayIndex));

                String dateAsString = yearAsString + "-" + monthAsString + "-" + dayAsString;
                daysOfMonth.add(dateAsString); // Add date of each day to daysOfMonth
            }

            startDates = daysOfMonth.toArray(new String[monthDays]);
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return startDates;
    }

    private String[] prepareStartTimes(String[] prayersTimes) {

        String[] startTimes = prayersTimes;
        return startTimes;
    }

    private String[] prepareEndDates(String[] startDates) {

        String[] endDates = startDates;
        return endDates;
    }

    private String[] prepareEndTimes(int duration, String[] startTimes) {

        int endTimesSize = startTimes.length;
        String[] endTimes = new String[endTimesSize];

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH); // Define the time format

            for(int columnIndex = 0; columnIndex < startTimes.length; columnIndex++) { // Loop through all items within startTimes

                String startTime = startTimes[columnIndex]; // Get each item
                LocalTime oldTime = LocalTime.parse(startTime, formatter); // Parse the input time string to a LocalTime object
                LocalTime newTime = oldTime.plusMinutes(duration); // Add minutes to the time
                String endTime = newTime.format(formatter); // Format the new time as a string
                endTimes[columnIndex] = endTime;
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return endTimes;
    }

    private String prepareAllDayEvent() {

        String allDayEvent = "FALSE";
        return allDayEvent;
    }

    private String prepareDescription() {

        String description = "";
        return description;
    }

    private String prepareLocation() {

        String location = "";
        return location;
    }

    private String preparePrivacy() {

        String privacy = "TRUE";
        return privacy;
    }

    private BufferedWriter createCSVFile(String city, String directory, String hostName, String[] startDates) {

        BufferedWriter fileWriter = null;

        try {

            String folderName = "prayer_times_tables";
            String folderDirectory = directory + "/" + folderName;

            File fileDirectory = new File(folderDirectory);
            if(!fileDirectory.exists()) {
                fileDirectory.mkdirs(); // Create the directory
            }

            String dateOfStart = startDates[0];
            String dateOfEnd = startDates[startDates.length - 1];

            String fileName = city + "_prayer_times_table_from_" + dateOfStart + "_to_" + dateOfEnd + "_(" + hostName + ")" + ".csv";
            String filePath = folderDirectory + "/" + fileName;

            fileWriter = new BufferedWriter(new FileWriter(filePath));
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return fileWriter;
    }

    private String[] setCSVFileHeaders() {

        String[] fileHeaders = {"Subject", "Start Date", "Start Time", "End Date", "End Time", "All Day Event", "Description", "Location", "Private"};
        return fileHeaders;
    }

    private String[][] setCSVFileItems(String[] subjects, String[] startDates, String[] startTimes, String[] endDates, String[] endTimes, String isAllDayEvent, String description, String location, String isPrivate) {

        int fileItemsRowsSize = startTimes.length;
        int fileItemsColumnsSize = 9; // Constant
        int lastIndex = subjects.length;

        String[][] fileItems = new String[fileItemsRowsSize][fileItemsColumnsSize];

        for(int columnIndex = 0; columnIndex < fileItemsColumnsSize; columnIndex++) { // Loop through all columns of fileItems

            if(columnIndex == 0) { // Subjects column

                // Set subjects
                int index = 0;
                for(int rowIndex = 0; rowIndex < fileItemsRowsSize; rowIndex++) { // Loop through all rows of the first column of fileItems

                    fileItems[rowIndex][columnIndex] = subjects[index];

                    index++;

                    if(index == lastIndex) {
                        index = 0;
                    }
                }
            }
            else if(columnIndex == 1) { // Start dates column

                // Set start dates
                int index = 0;
                int startDatesIndex = 0;
                for(int rowIndex = 0; rowIndex < fileItemsRowsSize; rowIndex++) { // Loop through all rows of the second column of fileItems

                    fileItems[rowIndex][columnIndex] = startDates[startDatesIndex];

                    index++;

                    if(index == lastIndex) {

                        index = 0;
                        startDatesIndex++;
                    }
                }
            }
            else if(columnIndex == 2) { // Start times column

                // Set start times
                for(int rowIndex = 0; rowIndex < fileItemsRowsSize; rowIndex++) { // Loop through all rows of the third column of fileItems
                    fileItems[rowIndex][columnIndex] = startTimes[rowIndex];
                }
            }
            else if(columnIndex == 3) { // End dates

                // Set end dates
                int index = 0;
                int startDatesIndex = 0;
                for(int rowIndex = 0; rowIndex < fileItemsRowsSize; rowIndex++) { // Loop through all rows of the fourth column of fileItems

                    fileItems[rowIndex][columnIndex] = endDates[startDatesIndex];

                    index++;

                    if(index == lastIndex) {

                        index = 0;
                        startDatesIndex++;
                    }
                }
            }
            else if(columnIndex == 4) { // End times column

                // Set end times
                for(int rowIndex = 0; rowIndex < fileItemsRowsSize; rowIndex++) { // Loop through all rows of the fifth column of fileItems
                    fileItems[rowIndex][columnIndex] = endTimes[rowIndex];
                }
            }
            else if(columnIndex == 5) { // All day events column

                // Set all day events
                for(int rowIndex = 0; rowIndex < fileItemsRowsSize; rowIndex++) { // Loop through all rows of the sixth column of fileItems
                    fileItems[rowIndex][columnIndex] = isAllDayEvent;
                }
            }
            else if(columnIndex == 6) { // Description column

                // Set description
                for(int rowIndex = 0; rowIndex < fileItemsRowsSize; rowIndex++) { // Loop through all rows of the seventh column of fileItems
                    fileItems[rowIndex][columnIndex] = description;
                }
            }
            else if(columnIndex == 7) { // Location column

                // Set location
                for(int rowIndex = 0; rowIndex < fileItemsRowsSize; rowIndex++) { // Loop through all rows of the eighth column of fileItems
                    fileItems[rowIndex][columnIndex] = location;
                }
            }
            else if(columnIndex == 8) { // Private column

                // Set private
                for(int rowIndex = 0; rowIndex < fileItemsRowsSize; rowIndex++) { // Loop through all rows of the ninth column of fileItems
                    fileItems[rowIndex][columnIndex] = isPrivate;
                }
            }
            else {
                break;
            }
        }
        return fileItems;
    }

    private boolean writeCSVFileHeaders(BufferedWriter fileWriter, String[] fileHeaders) {

        try {

            for(int columnIndex = 0; columnIndex < fileHeaders.length; columnIndex++) { // Loop through all columns of fileHeaders

                fileWriter.write(fileHeaders[columnIndex]); // Write each column

                if(columnIndex < fileHeaders.length - 1) { // If columnIndex doesn't equal to the last index
                    fileWriter.write(","); // Add comma delimiter for all columns except the last one
                }
            }
            fileWriter.newLine();
            return true;
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }

    private boolean writeCSVFileItems(BufferedWriter fileWriter, String[][] fileItems) {

        try {

            for(int rowIndex = 0; rowIndex < fileItems.length; rowIndex++) { // Loop through all rows of fileItems

                for(int columnIndex = 0; columnIndex < fileItems[rowIndex].length; columnIndex++) { // Loop through all columns of each row of fileItems

                    fileWriter.write(fileItems[rowIndex][columnIndex]); // Write each column

                    if(columnIndex < fileItems[rowIndex].length - 1) { // If columnIndex doesn't equal to the last index
                        fileWriter.write(","); // Add comma delimiter for all columns except the last one
                    }
                }
                fileWriter.newLine();
            }
            fileWriter.flush();
            return true;
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }
}
