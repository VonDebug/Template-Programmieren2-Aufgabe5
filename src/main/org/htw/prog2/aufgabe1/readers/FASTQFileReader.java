package org.htw.prog2.aufgabe1.readers;

import org.htw.prog2.aufgabe1.exceptions.FileFormatException;
import org.htw.prog2.aufgabe1.files.SequenceFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FASTQFileReader implements SequenceFileReader{



    @Override
    public SequenceFile readFile(String filename) throws IOException, FileFormatException {

        SequenceFile sequenceFile = new SequenceFile();
        boolean lasttLineWasHeader = false;
        boolean lastLineWasQuality = false;
        StringBuilder string = new StringBuilder();



        BufferedReader in;
        in = new BufferedReader(new FileReader(filename));
        String line;
        int lineCounter = 0;

        while ((line = in.readLine()) != null) {
            lineCounter++;


            if(line.startsWith("@")) {
                lastLineWasQuality = false;

                if(lasttLineWasHeader){
                    throw new FileFormatException("Two header lines are directly following each other.");

                }

                lasttLineWasHeader = true;
            }


            else if(!(line.startsWith("@")) && lineCounter==1){
                throw new FileFormatException("FASTQ File does not start with sequence header line.");
            }

            else if(line.startsWith("+")){
                lastLineWasQuality = true;
                sequenceFile.addSequence(string.toString());
                string.setLength(0);
            }

            else {
                if (lasttLineWasHeader) {
                    string.append(line);
                    lasttLineWasHeader = false;
                }
                else if(string.length()!=0 && !lastLineWasQuality){
                    string.append(line);
                }
            }

        }
        if(string.length() == 0 && lasttLineWasHeader) {
            throw new FileFormatException("The last line is a sequence header.");
        }

        return sequenceFile;

    }

    @Override
    public boolean canReadFile(String filename) {

        return filename.endsWith(".fastq");

    }





}