package dev.arzak21st.prayertimes.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import dev.arzak21st.prayertimes.utils.ExceptionHandlerUtil;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVFileImportationService {

    /* ==================== CSV File Importation Methods ==================== */
    public boolean importCSVFile(String filePath, String calendarId) {

        try {

            CSVParser data = getCSVFileData(filePath);
            if(data != null) {

                boolean eventsAreInserted = insertEvents(data, calendarId);
                if(eventsAreInserted) {
                    return true;
                }
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }

    /* ==================== Getting Prayers Data Methods ==================== */
    private CSVParser getCSVFileData(String filePath) {

        CSVParser data = null;
        Reader reader = null;

        try {

            // Read the CSV file data
            reader = new FileReader(filePath);
            data = new CSVParser(reader, CSVFormat.DEFAULT); // Iterable
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return data;
    }

    /* ==================== Importing Prayers Data Methods ==================== */
    private boolean insertEvents(CSVParser data, String calendarId) {

        try {

            String applicationName = "prayer-times";
            String credentialsFilePath = "/others/credentials.json";
            List<String> scopes = Arrays.asList(CalendarScopes.CALENDAR_EVENTS);
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            Credential credentials = getCredentials(credentialsFilePath, scopes, applicationName, httpTransport, jsonFactory);
            if(credentials != null) {

                // Initialize Calendar service with valid OAuth credentials
                Calendar client = new Calendar.Builder(httpTransport, jsonFactory, credentials).setApplicationName(applicationName).build();

                // Batch insert events
                BatchRequest batch = client.batch();

                // Callback for handling the responses
                JsonBatchCallback<Event> callback = new JsonBatchCallback<Event>() {
                    @Override
                    public void onSuccess(Event event, HttpHeaders responseHeaders) throws IOException {
                        System.out.println("Event inserted: " + event.getHtmlLink());
                    }

                    @Override
                    public void onFailure(GoogleJsonError googleJsonError, HttpHeaders responseHeaders) throws IOException {
                        throw new IOException("Error inserting event: " + googleJsonError.toString());
                    }
                };

                for(CSVRecord record : data) {

                    if(record.getRecordNumber() > 1) { // If the current record isn't the hreaders record

                        // Create an event
                        Event event = new Event();

                        // Subject
                        event.setSummary(record.get(0));

                        // Start Date + Start Time
                        String startDate = record.get(1);
                        String startTime = LocalTime.parse(record.get(2), DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)).format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")); // SSS for milliseconds
                        DateTime startDateTime = new DateTime(startDate + "T" + startTime);
                        EventDateTime eventStartDateTime = new EventDateTime().setDateTime(startDateTime);
                        String startTimeOffset = eventStartDateTime.getDateTime().toString().substring(eventStartDateTime.getDateTime().toString().indexOf(".") + 4);
                        startDateTime = new DateTime(startDate + "T" + startTime + startTimeOffset);
                        eventStartDateTime = new EventDateTime().setDateTime(startDateTime);
                        event.setStart(eventStartDateTime);

                        // End Date + End Time
                        String endDate = record.get(3);
                        String endTime = LocalTime.parse(record.get(4), DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)).format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")); // Ex: 2:16 PM -> 14:16:00.000
                        DateTime endDateTime = new DateTime(endDate + "T" + endTime);
                        EventDateTime eventEndDateTime = new EventDateTime().setDateTime(endDateTime);
                        String endTimeOffset = eventEndDateTime.getDateTime().toString().substring(eventEndDateTime.getDateTime().toString().indexOf(".") + 4);
                        endDateTime = new DateTime(endDate + "T" + endTime + endTimeOffset);
                        eventEndDateTime = new EventDateTime().setDateTime(endDateTime);
                        event.setEnd(eventEndDateTime);

                        // All Day Event!

                        // Description + Location
                        event.setDescription(record.get(6));
                        event.setLocation(record.get(7));

                        // Private
                        if(record.get(8).equals("TRUE")) {
                            event.setVisibility("private");
                        }
                        else {
                            event.setVisibility("public");
                        }

                        // Queue the insert request
                        client.events().insert(calendarId, event).queue(batch, callback);
                    }
                }

                // Execute the insert requests
                batch.execute();
                return true;
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
        return false;
    }

    private Credential getCredentials(final String credentialsFilePath, final List<String> scopes, final String applicationName, final NetHttpTransport httpTransport, final JsonFactory jsonFactory) {

        Credential credential = null;

        try {

            // Load client secrets
            InputStream inputStream = ViewService.class.getResourceAsStream(credentialsFilePath);
            if(inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
            }
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(inputStream));

            // Build flow and trigger user authorization request
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, scopes).setAccessType("offline").build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }

        return credential;
    }
}
