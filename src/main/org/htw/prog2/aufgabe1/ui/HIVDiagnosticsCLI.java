package org.htw.prog2.aufgabe1.ui;

import org.apache.commons.cli.*;
import org.apache.commons.cli.CommandLine;
import org.htw.prog2.aufgabe1.analysis.SequenceAnalysis;
import org.htw.prog2.aufgabe1.analysis.SequenceAnalysisManager;

public class HIVDiagnosticsCLI {

    public HIVDiagnosticsCLI(String[] args) throws Exception {

      CommandLine cli = parseOptions(args);

        assert cli != null;
        String referenceFileName = cli.getOptionValue('r');
        String patientSequenceFileName = cli.getOptionValue('p');
        String mutationFileName = cli.getOptionValue('m');

        SequenceAnalysis sequenceAnalysis = SequenceAnalysisManager.performAnalysis(referenceFileName, patientSequenceFileName,mutationFileName);

        sequenceAnalysis.calculateResistances();

        System.out.println("Eingelesene Mutationen: " + sequenceAnalysis.getMutations());
        System.out.println("Länge der eingelesenen Referenzsequenz: " +
                sequenceAnalysis.getReference().length() + " Aminosäuren");
        System.out.println("Anzahl der eingelesenen Patientensequenzen: " +
                sequenceAnalysis.getSequences().getNumberOfSequences());
        System.out.println("Das beste Medikament ist: " + sequenceAnalysis.getBestDrug() + " mit einer Resistenz von: " + sequenceAnalysis.getBestDrugResistance());

    }

    /**
     * Parst die Kommandozeilenargumente. Gibt null zurück, falls:
     * <ul>
     *     <li>Ein Fehler beim Parsen aufgetreten ist (z.B. eins der erforderlichen Argumente nicht angegeben wurde)</li>
     *     <li>Bei -m, -d und -r nicht die gleiche Anzahl an Argumenten angegeben wurde</li>
     * </ul>
     * @param args Array mit Kommandozeilen-Argumenten
     * @return CommandLine-Objekt mit geparsten Optionen
     */

    public static CommandLine parseOptions(String[] args) {

        Options options = new Options();
        options.addOption(Option.builder("g").
                hasArg(false).
                longOpt("graphical").
                desc("Start with graphical interface").build());
        options.addOption(Option.builder("m").
                hasArg(true).
                longOpt("mutationfile").
                required(true).
                desc("CSV file with mutation patterns.").build());
        options.addOption(Option.builder("d").
                hasArg(true).
                longOpt("drugname").
                required(true).
                desc("Drug name.").build());
        options.addOption(Option.builder("r").
                hasArg(true).
                longOpt("reference").
                required(true).
                desc("Reference sequence FASTA file.").build());
        options.addOption(Option.builder("p").
                hasArg(true).
                longOpt("patientseqs").
                required(true).
                desc("FASTA file with patient sequences.").build());
        CommandLineParser parser = new DefaultParser();
        CommandLine cli;
        try {
            cli = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("HIVDiagnostics", options);
            return null;
        }
        return cli;
    }
}
