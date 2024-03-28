package dev.arzak21st.prayertimes.views;

import dev.arzak21st.prayertimes.services.ViewService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class View extends JFrame {

    /* ==================== Variables ==================== */
    ViewService viewService = new ViewService();

    /* ==================== Constructors ==================== */
    public View() {

        initComponents();
        setIconImage();
        setInputsValues();
        setActionEvents();
    }

    /* ==================== Constructors Methods ==================== */
    public void setIconImage() {
        viewService.setIconImage(this);
    }

    public void setInputsValues() {

        viewService.setLocationsBoxesValues(countriesBox, citiesBox);
        viewService.setDateBoxesValues(yearsBox, monthsBox);
        viewService.setDurationBoxValue(durationsBox);
        viewService.setDirectoryFieldValue(directoryField);
    }

    public void setActionEvents() {

        countriesBox.addActionListener((e) -> viewService.countriesBoxActionPerformedEvent(countriesBox, citiesBox));
        citiesBox.addActionListener((e) -> viewService.citiesBoxActionPerformedEvent(citiesBox));
        yearsBox.addActionListener((e) -> viewService.yearsBoxActionPerformedEvent(yearsBox, monthsBox));
        monthsBox.addActionListener((e) -> viewService.monthsBoxActionPerformedEvent(monthsBox));
        durationsBox.addActionListener((e) -> viewService.durationsBoxActionPerformedEvent(durationsBox));
        directoryField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewService.directoryFieldMouseClickedEvent(directoryField);
            }
        });

        generateButton.addActionListener((e) -> viewService.generateButtonActionPerformedEvent(citiesBox, yearsBox, monthsBox, durationsBox, directoryField));
        importButton.addActionListener((e) -> viewService.importButtonActionPerformedEvent());
    }

    /* ==================== IDE Methods ==================== */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uiPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        citiesLabel = new javax.swing.JLabel();
        generateButton = new javax.swing.JButton();
        directoryLabel = new javax.swing.JLabel();
        directoryField = new javax.swing.JTextField();
        durationsLabel = new javax.swing.JLabel();
        monthsBox = new javax.swing.JComboBox<>();
        yearsBox = new javax.swing.JComboBox<>();
        durationsBox = new javax.swing.JComboBox<>();
        importButton = new javax.swing.JButton();
        citiesBox = new javax.swing.JComboBox<>();
        yearsLabel = new javax.swing.JLabel();
        monthsLabel = new javax.swing.JLabel();
        countriesBox = new javax.swing.JComboBox<>();
        countriesLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Prayer Times");
        setResizable(false);

        titleLabel.setFont(new java.awt.Font("Courgette", 1, 32)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Prayer Times");

        citiesLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        citiesLabel.setText(" City:");

        generateButton.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        generateButton.setText("Generate CSV File");
        generateButton.setToolTipText("Obtaine prayer times and put them as Google Calendar events within a CSV file.");

        directoryLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        directoryLabel.setText(" Directory:");

        directoryField.setEditable(false);
        directoryField.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        directoryField.setToolTipText("The directory where the CSV file will be generated.");

        durationsLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        durationsLabel.setText(" Duration (min):");

        monthsBox.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        monthsBox.setToolTipText("The month in which the prayer times are obtained.");

        yearsBox.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        yearsBox.setToolTipText("The year in which the prayer times are obtained.");

        durationsBox.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        durationsBox.setToolTipText("The duration of the events to be created in Google Calendar.");

        importButton.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        importButton.setText("Import CSV File");
        importButton.setToolTipText("Import a generated CSV file to Google Calendar.");

        citiesBox.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        citiesBox.setToolTipText("The city of which the prayer times are obtained.");

        yearsLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        yearsLabel.setText(" Year:");

        monthsLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        monthsLabel.setText(" Month:");

        countriesBox.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        countriesBox.setToolTipText("Helps to select a city.");

        countriesLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        countriesLabel.setText(" Country:");

        javax.swing.GroupLayout uiPanelLayout = new javax.swing.GroupLayout(uiPanel);
        uiPanel.setLayout(uiPanelLayout);
        uiPanelLayout.setHorizontalGroup(
            uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uiPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(directoryLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(directoryField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(durationsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(durationsBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uiPanelLayout.createSequentialGroup()
                        .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(yearsBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(yearsLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(monthsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(monthsBox, 0, 252, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uiPanelLayout.createSequentialGroup()
                        .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(countriesBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(countriesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(citiesBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 252, Short.MAX_VALUE)
                            .addComponent(citiesLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uiPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(importButton, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        uiPanelLayout.setVerticalGroup(
            uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uiPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(citiesLabel)
                    .addComponent(countriesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(citiesBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(countriesBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearsLabel)
                    .addComponent(monthsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(monthsBox)
                    .addComponent(yearsBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(durationsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(durationsBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(directoryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(uiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uiPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uiPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> citiesBox;
    private javax.swing.JLabel citiesLabel;
    private javax.swing.JComboBox<String> countriesBox;
    private javax.swing.JLabel countriesLabel;
    private javax.swing.JTextField directoryField;
    private javax.swing.JLabel directoryLabel;
    private javax.swing.JComboBox<String> durationsBox;
    private javax.swing.JLabel durationsLabel;
    private javax.swing.JButton generateButton;
    private javax.swing.JButton importButton;
    private javax.swing.JComboBox<String> monthsBox;
    private javax.swing.JLabel monthsLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel uiPanel;
    private javax.swing.JComboBox<String> yearsBox;
    private javax.swing.JLabel yearsLabel;
    // End of variables declaration//GEN-END:variables
}
