package org.htw.prog2.aufgabe1.ui;

import org.htw.prog2.aufgabe1.analysis.FullLengthSequenceAnalysis;
import org.htw.prog2.aufgabe1.analysis.SequenceAnalysis;
import org.htw.prog2.aufgabe1.analysis.SequenceAnalysisManager;
import org.htw.prog2.aufgabe1.exceptions.FileFormatException;
import org.htw.prog2.aufgabe1.exceptions.NoValidReadersException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class HIVDiagnosticsGUI extends JFrame {
    private JLabel mutationLabel;
    private JLabel referenceLabel;
    private JLabel patientSequenceLabel;
    private JLabel bestDrugLabel;
    private JLabel bestDrugResistanceLabel;

    private JButton mutationButton;
    private JButton referenceButton;
    private JButton patientSequenceButton;
    private JButton predictButton;

    private String mutationFilePath;
    private String referenceFilePath;
    private String patientFilePath;

    protected abstract class LoadListener implements ActionListener {

        String extensionDescription;
        String[] extensions;

        public LoadListener(String extensionDescription, String... extensions) {
            this.extensionDescription = extensionDescription;
            this.extensions = extensions;

        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jFileChooser.setFileFilter(new FileNameExtensionFilter(this.extensionDescription, this.extensions));

            int result = jFileChooser.showOpenDialog(jFileChooser);
            if(result == JFileChooser.APPROVE_OPTION){
                File selectedFile = jFileChooser.getSelectedFile();
                setData(selectedFile);
                if(mutationFilePath!=null && referenceFilePath!=null && patientFilePath !=null){
                    predictButton.setEnabled(true);

                    predictButton.addActionListener(predicAction -> {
                        try {
                            SequenceAnalysis sequenceAnalysis  = SequenceAnalysisManager.performAnalysis(referenceFilePath, patientFilePath, mutationFilePath);
                            sequenceAnalysis.calculateResistances();
                            bestDrugLabel.setText(sequenceAnalysis.getBestDrug());
                            bestDrugLabel.setForeground(Color.BLACK);
                            bestDrugResistanceLabel.setText(Double.toString(sequenceAnalysis.getBestDrugResistance()));
                            bestDrugResistanceLabel.setForeground(Color.BLACK);
                        } catch (Exception e) {
                            JOptionPane.showInternalMessageDialog(null, "Fehler :" + e.getMessage(), "Fehlermeldung", JOptionPane.WARNING_MESSAGE);
                        }
                    });
                }
            }


        }

        protected abstract void setData(File file);
    }

    public HIVDiagnosticsGUI() {

        super("HIV Diagnostics Tool");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("resources/folder_explore.png");

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints c = new GridBagConstraints();

        initMenuBar();

        initLabels(c);

        initButtons(c, icon);

        SequenceAnalysisManager sequenceAnalysisManager = new SequenceAnalysisManager();


    }

    private void initLabels(GridBagConstraints c){
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 30, 0, 0);
        this.mutationLabel = new JLabel("Please load a mutation CSV file");
        this.mutationLabel.setForeground(Color.red);
        add(this.mutationLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 30, 0, 0);
        this.referenceLabel = new JLabel("Please load a reference FASTA/FASTQ file");
        this.referenceLabel.setForeground(Color.red);
        add(this.referenceLabel, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 30, 0, 0);
        this.patientSequenceLabel = new JLabel("Please load a patient FASTA/FASTQ file");
        this.patientSequenceLabel.setForeground(Color.red);
        add(this.patientSequenceLabel, c);


        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Recommended Drug:"),c);

        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0,0,0,30);
        this.bestDrugLabel = new JLabel("N/A");
        this.bestDrugLabel.setForeground(Color.red);
        add(this.bestDrugLabel, c);


        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Predicted resistance for recommenced drug:"),c);

        c.gridx = 1;
        c.gridy = 5;
        c.weightx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0,0,0,30);
        this.bestDrugResistanceLabel = new JLabel("N/A");
        this.bestDrugResistanceLabel.setForeground(Color.red);
        add(this.bestDrugResistanceLabel, c);



    }

    private void initButtons(GridBagConstraints c, ImageIcon icon){

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 2, 30);
        this.mutationButton = new JButton(icon);
        add(this.mutationButton,c);
        this.mutationButton.addActionListener(new LoadListener("CSV file", "csv") {
            @Override
            protected void setData(File file) {
                mutationLabel.setText(file.getName());
                mutationLabel.setForeground(Color.BLACK);
                mutationFilePath = file.getPath();
            }
        });




        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 2, 30);
        this.referenceButton = new JButton(icon);
        add(this.referenceButton,c);
        this.referenceButton.addActionListener(new LoadListener("fasta/fastq File", "fasta", "fastq") {
            @Override
            protected void setData(File file) {
                referenceLabel.setName(file.getName());
                referenceLabel.setForeground(Color.BLACK);
                referenceFilePath = file.getPath();
            }
        });

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 2, 30);
        this.patientSequenceButton = new JButton(icon);
        add(this.patientSequenceButton,c);
        this.patientSequenceButton.addActionListener(new LoadListener("fasta/fastq File", "fasta", "fastq") {
            @Override
            protected void setData(File file) {
                patientSequenceLabel.setText(file.getName());
                patientSequenceLabel.setForeground(Color.BLACK);
                patientFilePath = file.getPath();
            }
        });


        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0, 0, 0, 30);
        this.predictButton = new JButton("Predict best drug");
        this.predictButton.setEnabled(false);
        add(this.predictButton,c);


    }

    private void init() {
    }

    private void initMenuBar() {

        JMenuBar jMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem aboutItem = new JMenuItem("About");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(aboutItem);
        fileMenu.add(exitItem);

        jMenuBar.add(fileMenu);

        setJMenuBar(jMenuBar);

        aboutItem.addActionListener(actionEvent -> {
            JOptionPane.showInternalMessageDialog(null, "HIV-Diagnostik-Tool für Proteomdaten", "Message", JOptionPane.INFORMATION_MESSAGE);
        });

        exitItem.addActionListener(actionEvent ->System.exit(0));

    }

    private void initFileChoosers() {
    }

    private void addLoaders(JLabel label, JButton button, GridBagConstraints c, ActionListener listener) {
    }
}
