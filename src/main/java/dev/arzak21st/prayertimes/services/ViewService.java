package dev.arzak21st.prayertimes.services;

import dev.arzak21st.prayertimes.utils.ExceptionHandlerUtil;
import dev.arzak21st.prayertimes.utils.PreferencesUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javazoom.jl.player.Player;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ViewService {

    /* ==================== Variables ==================== */
    ValidationService validationService = new ValidationService();
    CSVFileGenerationService csvFileGenerationService = new CSVFileGenerationService();
    CSVFileImportationService csvFileImportationService = new CSVFileImportationService();

    /* ==================== UI Methods ==================== */
    public void setIconImage(JFrame frame) {

        try {

            String iconFilePath = "/images/prayer-times-icon-blue.png";
            URL iconFileURL = getClass().getResource(iconFilePath);

            ImageIcon imageIcon = new ImageIcon(iconFileURL);
            frame.setIconImage(imageIcon.getImage());
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    /* ==================== Inputs Values Methods ==================== */
    public void setLocationsBoxesValues(JComboBox countriesBox, JComboBox citiesBox) {

        try {

            // Read the CSV file data
            InputStream inputStream = ViewService.class.getResourceAsStream("/others/cities.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            CSVParser data = new CSVParser(reader, CSVFormat.DEFAULT); // Iterable
            List<CSVRecord> records = data.getRecords();

            // Countries box
            records.get(0).forEach(country -> countriesBox.addItem(country));
            countriesBox.setSelectedItem("Morocco");

            String country = PreferencesUtil.getPreference("country");
            if(country != null) {
                countriesBox.setSelectedItem(country);
            }

            // Cities box
            String selectedCountry = String.valueOf(countriesBox.getSelectedItem());

            int countryIndex = 0;
            for(int headerIndex = 0; headerIndex < records.get(0).size(); headerIndex++) {

                String header = records.get(0).get(headerIndex);
                if(header.equals(selectedCountry)) {

                    countryIndex = headerIndex;
                    break;
                }
            }

            citiesBox.removeAllItems();
            for(CSVRecord record : records) {

                if(record.getRecordNumber() > 1) {

                    String city = record.get(countryIndex);
                    if(!city.equals("") && !city.contains("...")) {
                        citiesBox.addItem(city);
                    }
                }
            }
            citiesBox.setSelectedItem("Marrakech");

            String city = PreferencesUtil.getPreference("city");
            if(city != null) {
                citiesBox.setSelectedItem(city);
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void setDateBoxesValues(JComboBox yearsBox, JComboBox monthsBox) {

        try {

            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            int currentMonth = currentDate.getMonthValue();

            // Years box
            for(int yearIndex = currentYear; yearIndex <= currentYear + 1; yearIndex++) {

                String year = String.valueOf(yearIndex);
                yearsBox.addItem(year);
            }
            yearsBox.setSelectedIndex(0);

            String year = PreferencesUtil.getPreference("year");
            if(year != null) {
                yearsBox.setSelectedItem(year);
            }

            // Months box
            monthsBox.removeAllItems();
            for(int monthIndex = 1; monthIndex <= 12; monthIndex++) {

                YearMonth yearMonth = YearMonth.of(currentYear, monthIndex);
                String month = yearMonth.getMonth().toString(); // Ex: may
                String newMonth = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase(); // Ex: May
                monthsBox.addItem(newMonth);
            }
            monthsBox.setSelectedIndex(currentMonth - 1);

            String month = PreferencesUtil.getPreference("month");
            if(month != null) {
                monthsBox.setSelectedItem(month);
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void setDurationBoxValue(JComboBox durationsBox) {

        try {

            int minDuration = 5;
            int maxDuration = 60;
            for(int durationIndex = minDuration; durationIndex <= maxDuration; durationIndex++) {

                String duration = String.valueOf(durationIndex);
                durationsBox.addItem(duration);
            }
            durationsBox.setSelectedItem("20");

            String duration = PreferencesUtil.getPreference("duration");
            if(duration != null) {
                durationsBox.setSelectedItem(duration);
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void setDirectoryFieldValue(JTextField directoryField) {

        try {

            String homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
            directoryField.setText(homeDirectory);

            String directory = PreferencesUtil.getPreference("directory");
            if(directory != null) {
                directoryField.setText(directory);
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    /* ==================== Events Methods ==================== */
    public void countriesBoxActionPerformedEvent(JComboBox countriesBox, JComboBox citiesBox) {

        try {

            // Read the CSV file data
            InputStream inputStream = ViewService.class.getResourceAsStream("/others/cities.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            CSVParser data = new CSVParser(reader, CSVFormat.DEFAULT); // Iterable
            List<CSVRecord> records = data.getRecords();

            // Cities box
            String selectedCountry = String.valueOf(countriesBox.getSelectedItem());

            int countryIndex = 0;
            for(int headerIndex = 0; headerIndex < records.get(0).size(); headerIndex++) {

                String header = records.get(0).get(headerIndex);
                if(header.equals(selectedCountry)) {

                    countryIndex = headerIndex;
                    break;
                }
            }

            citiesBox.removeAllItems();
            for(CSVRecord record : records) {

                if(record.getRecordNumber() > 1) {

                    String city = record.get(countryIndex);
                    if(!city.equals("") && !city.contains("...")) {
                        citiesBox.addItem(city);
                    }
                }
            }
            citiesBox.setSelectedIndex(0);

            String country = String.valueOf(countriesBox.getSelectedItem());
            PreferencesUtil.savePreference("country", country);

            String city = String.valueOf(citiesBox.getSelectedItem());
            PreferencesUtil.savePreference("city", city);
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void citiesBoxActionPerformedEvent(JComboBox citiesBox) {

        try {

            String city = String.valueOf(citiesBox.getSelectedItem());
            PreferencesUtil.savePreference("city", city);
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void yearsBoxActionPerformedEvent(JComboBox yearsBox, JComboBox monthsBox) {

        try {

            int setectedYear = Integer.parseInt(String.valueOf(yearsBox.getSelectedItem()));

            monthsBox.removeAllItems();
            for(int monthIndex = 1; monthIndex <= 12; monthIndex++) {

                YearMonth yearMonth = YearMonth.of(setectedYear, monthIndex);
                String month = yearMonth.getMonth().toString();
                String newMonth = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
                monthsBox.addItem(newMonth);
            }
            monthsBox.setSelectedIndex(0);

            String year = String.valueOf(yearsBox.getSelectedItem());
            PreferencesUtil.savePreference("year", year);

            String month = String.valueOf(monthsBox.getSelectedItem());
            PreferencesUtil.savePreference("month", month);
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void monthsBoxActionPerformedEvent(JComboBox monthsBox) {

        try {

            String month = String.valueOf(monthsBox.getSelectedItem());
            PreferencesUtil.savePreference("month", month);
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void durationsBoxActionPerformedEvent(JComboBox durationsBox) {

        try {

            String duration = String.valueOf(durationsBox.getSelectedItem());
            PreferencesUtil.savePreference("duration", duration);
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void directoryFieldMouseClickedEvent(JTextField directoryField) {

        try {

            JFileChooser directoryChooser = new JFileChooser(); // Create a JFileChooser dialog

            String directory = PreferencesUtil.getPreference("directory"); // Get the last choosed directory
            if(directory == null) {
                directory = FileSystemView.getFileSystemView().getHomeDirectory().getPath(); // Get the home directory
            }

            directoryChooser.setCurrentDirectory(new File(directory)); // Set the current directory to the home directory
            directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Set file selection mode to directories only

            int response = directoryChooser.showOpenDialog(null); // Open the file chooser dialog

            if(response == JFileChooser.APPROVE_OPTION) { // If the user clicked the 'Open' button

                directory = directoryChooser.getSelectedFile().getAbsolutePath(); // Get the selected directory
                directoryField.setText(directory); // Set the selected directory within directoryField
                PreferencesUtil.savePreference("directory", directory); // Set the last choosed directory
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void generateButtonActionPerformedEvent(JComboBox citiesBox, JComboBox yearsBox, JComboBox monthsBox, JComboBox durationsBox, JTextField directoryField) {

        try {

            String city = String.valueOf(citiesBox.getSelectedItem()).toLowerCase().replace(" ", "-").replace("'", "");
            String year = String.valueOf(yearsBox.getSelectedItem());
            String month = String.valueOf(String.format("%02d", monthsBox.getSelectedIndex() + 1));
            String day = "01";
            String date = year + "-" + month + "-" + day;

            int duration = Integer.parseInt(String.valueOf(durationsBox.getSelectedItem()));

            String directory = directoryField.getText();

            String urlString = "https://timesprayer.com/en/list-prayer-in-" + city + "-" + year + "-" + month + ".html";
            String hostName = new URL(urlString).getHost();

            boolean dateIsValid = validationService.validateDate(date);
            if(dateIsValid) {

                boolean durationIsValid = validationService.validateDuration(duration);
                if(durationIsValid) {

                    boolean directoryIsValid = validationService.validateDirectory(directory);
                    if(directoryIsValid) {

                        boolean urlIsValid = validationService.validateURL(urlString);
                        if(urlIsValid) {

                            boolean csvFileIsGenerated = csvFileGenerationService.generateCSVFile(city, date, duration, directory, urlString, hostName);
                            if(csvFileIsGenerated) {

                                // Alert the user
                                String title = "Great!";
                                String message = "The CSV file was generated successfully.";
                                JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void importButtonActionPerformedEvent() {

        try {

            JFileChooser csvFileChooser = new JFileChooser();

            String csvFileDirectory = PreferencesUtil.getPreference("csvFileDirectory");
            if(csvFileDirectory == null) {
                csvFileDirectory = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
            }

            csvFileChooser.setCurrentDirectory(new File(csvFileDirectory));

            FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV Files", "csv");
            csvFileChooser.setFileFilter(csvFilter);

            int response = csvFileChooser.showOpenDialog(null);

            if(response == JFileChooser.APPROVE_OPTION) {

                String csvFilePath = csvFileChooser.getSelectedFile().getAbsolutePath();

                csvFileDirectory = csvFilePath.substring(0, csvFilePath.lastIndexOf(File.separator));
                PreferencesUtil.savePreference("csvFileDirectory", csvFileDirectory);

                boolean csvFileIsValid = validationService.validateCSVFile(csvFilePath);
                if(csvFileIsValid) {

                    String title = "Calendar ID";
                    String message = "Enter your calendar ID:";
                    String defaultValue = PreferencesUtil.getPreference("calendarId");
                    String value = (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE, null, null, defaultValue);
                    if(value != null) {

                        String calendarId = value.toString().replace(" ", "");
                        boolean calendarIdIsValid = validationService.validateCalendarId(calendarId);
                        if(calendarIdIsValid) {

                            PreferencesUtil.savePreference("calendarId", calendarId);
                            boolean csvFileIsImported = csvFileImportationService.importCSVFile(csvFilePath, calendarId);
                            if(csvFileIsImported) {

                                // Alert the user
                                title = "Taqabbal Allah!";
                                message = "The CSV file was imported successfully.";
                                JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }

    public void jokeButtonActionPerformedEvent() {

        int activeThreadsCount = Thread.activeCount();
        if(activeThreadsCount <= 3) {

            Thread soundThread = new Thread(() -> { // Fourth Thread

                Player player = null;
                String soundFilePath = null;
                InputStream inputStream = null;

                try {

                    soundFilePath = "/sounds/AreYouReady.mp3";
                    inputStream = Player.class.getResourceAsStream(soundFilePath);
                    player = new Player(inputStream);
                    player.play();

                    String title = "!";
                    String message = "Are you ready?";
                    String[] options = {"Ready for what?", "No"};

                    int option = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if(option == JOptionPane.YES_OPTION) { // Display

                        soundFilePath = "/sounds/JohnCena!.mp3";
                        inputStream = Player.class.getResourceAsStream(soundFilePath);
                        player = new Player(inputStream);
                        player.play();
                    }
                }
                catch(Exception exception) {
                    ExceptionHandlerUtil.getException(exception);
                }
                finally {
                    player.close();
                }
            });
            soundThread.start();
        }
    }
}
