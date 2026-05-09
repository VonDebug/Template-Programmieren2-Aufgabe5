package org.htw.prog2.aufgabe1.analysis;

import org.htw.prog2.aufgabe1.exceptions.FileFormatException;
import org.htw.prog2.aufgabe1.exceptions.NoValidReadersException;
import org.htw.prog2.aufgabe1.files.MutationFile;
import org.htw.prog2.aufgabe1.files.SequenceFile;
import org.htw.prog2.aufgabe1.readers.*;

import java.io.IOException;

public class SequenceAnalysisManager {
    public static SequenceAnalysis performAnalysis(String referenceFileName, String patientSequenceFileName,
                                                   String mutationFileName) throws Exception {

        ReaderManager<SequenceFileReader> sequenceReaderManager = new ReaderManager<>();
        sequenceReaderManager.addReader(new FASTAFileReader());
        sequenceReaderManager.addReader(new FASTQFileReader());


        ReaderManager<MutationFileReader> mutationReaderManager = new ReaderManager<>();
        mutationReaderManager.addReader(new CSVFileReader());

        try {

            SequenceFile referencefile = sequenceReaderManager.getReaderForFile(referenceFileName).readFile(referenceFileName);
            SequenceFile patientseqs = sequenceReaderManager.getReaderForFile(patientSequenceFileName).readFile(patientSequenceFileName);
            MutationFile patterns = mutationReaderManager.getReaderForFile(mutationFileName).readFile(mutationFileName);


            FullLengthSequenceAnalysis fullLengthSequenceAnalysis = new FullLengthSequenceAnalysis(referencefile.getFirstSequence(), patientseqs, patterns);
            fullLengthSequenceAnalysis.determineBestDrug();

            return fullLengthSequenceAnalysis;

        } catch (Exception e) {
            if(e instanceof NoValidReadersException){throw new NoValidReadersException("");}
            else if (e instanceof FileFormatException){throw new FileFormatException("");}
            else if (e instanceof IOException){throw new IOException();}
            else {throw new Exception("Unbekannter Fehler: " + e.getMessage());}

        }


    }
}
