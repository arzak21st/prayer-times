package dev.arzak21st.prayertimes.utils;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.UIManager;

public class LookAndFeelUtil {

    /* ==================== Look and Feel Methods ==================== */
    public void setLookAndFeel() {

        try {

            FlatMacDarkLaf flatMacDarkLaf = new FlatMacDarkLaf();
            FlatDarkLaf flatDarkLaf = new FlatDarkLaf();
            String systemLookAndFeel = UIManager.getSystemLookAndFeelClassName();

            if(flatMacDarkLaf.isSupportedLookAndFeel()) {
                UIManager.setLookAndFeel(flatMacDarkLaf);
            }
            else if(flatDarkLaf.isSupportedLookAndFeel()) {
                UIManager.setLookAndFeel(flatDarkLaf);
            }
            else {
                UIManager.setLookAndFeel(systemLookAndFeel);
            }
        }
        catch(Exception exception) {
            ExceptionHandlerUtil.getException(exception);
        }
    }
}
