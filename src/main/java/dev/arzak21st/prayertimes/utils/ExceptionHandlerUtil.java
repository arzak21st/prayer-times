package dev.arzak21st.prayertimes.utils;

import java.awt.Dimension;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ExceptionHandlerUtil {

    /* ==================== Exception Handling Methods ==================== */
    public static void getException(Exception exception) {

        String title = "Error :(";
        String message = exception.toString();
        String[] options = {"Display", "Cancel"};

        int option = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
        if(option == JOptionPane.YES_OPTION) { // "Display"
            getStackTreacePrompt(exception);
        }
    }

    private static void getStackTreacePrompt(Exception exception) {

        String exceptionTitle = "";
        String exceptionMessage = getStackTreace(exception);

        JTextArea exceptionArea = new JTextArea(exceptionMessage);
        JScrollPane exceptionPane = new JScrollPane(exceptionArea);
        exceptionPane.setPreferredSize(new Dimension(1000, 400));

        JOptionPane.showMessageDialog(null, exceptionPane, exceptionTitle, JOptionPane.ERROR_MESSAGE);
    }

    private static String getStackTreace(Exception exception) {

        String exceptionMessage = "";

        StringWriter stringWriter = new StringWriter(); // The object that will have the exception message
        PrintWriter printWriter = new PrintWriter(stringWriter); // Writes the exception message to the object "stringWriter"
        exception.printStackTrace(printWriter); // Prints the exception message to the object "printWriter"
        exceptionMessage = stringWriter.toString(); // Gets the object "stringWriter" as a string

        return exceptionMessage;
    }
}
