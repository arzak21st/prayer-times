package dev.arzak21st.prayertimes;

import dev.arzak21st.prayertimes.views.View;
import dev.arzak21st.prayertimes.utils.LookAndFeelUtil;

public class Main {

    public static void main(String args[]) {

        new LookAndFeelUtil().setLookAndFeel();
        new View().setVisible(true);
    }
}
