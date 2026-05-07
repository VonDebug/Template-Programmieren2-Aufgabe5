package org.htw.prog2.aufgabe1.readers;

import com.sun.jdi.DoubleValue;
import org.htw.prog2.aufgabe1.exceptions.FileFormatException;
import org.htw.prog2.aufgabe1.files.Mutation;
import org.htw.prog2.aufgabe1.files.MutationFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class CSVFileReader implements MutationFileReader{




    public static List<String> parseDrugs(String definitionLine) throws FileFormatException {
        List<String> drugList = new ArrayList<>();
        String definitionTemplate = "\"Mutation Patterns\";\"Number of Sequences\"";

        if (!definitionLine.startsWith(definitionTemplate)) {

            throw new FileFormatException("Falsches Format");
        }
        else {

            String[] lineAsArray = (definitionLine.split(";"));

            for (int i = 2; i < lineAsArray.length; i++) {

                if(lineAsArray[i].split(" ").length != 2){
                    throw new FileFormatException("Drug name ist falsch formatiert");
                }
                else {

                    drugList.add(lineAsArray[i].replace("\"", "").split(" ")[0]);
                }
            }


            return drugList;
        }
    }

    @Override
    public MutationFile readFile(String filename) throws IOException, FileFormatException {

        MutationFile mutationFile = new MutationFile();



        HashMap<String,Double> resistances = new HashMap<>();

        boolean secondLineisDefinition = false;
        int lengthOfDefinitionLine = 0;

        BufferedReader in;
        in = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = in.readLine()) != null) {

            if(line.startsWith("#")){
                continue;
            }

            if(line.contains("\"Mutation")) {

                parseDrugs(line).forEach(drug -> mutationFile.addDrug(drug));


                lengthOfDefinitionLine = (line.split(";")).length;
                secondLineisDefinition = true;

            }

            else{
                if(line.split(";").length != lengthOfDefinitionLine) {
                    throw new FileFormatException("All lines in a CSV file must have the same number of elements.");
                }
                else{
                    LinkedList<String> drugs = mutationFile.getDrugs();
                    String[] lineParts = line.split(";");

                    for(int i = 2; i< lineParts.length; i++){
                        String raw = lineParts[i].replace('\u00A0', ' ').trim();
                        if (!raw.isEmpty()) {
                            resistances.put(drugs.get(i - 2), Double.parseDouble(raw));
                        }
                    }
                    Mutation mutation = new Mutation(lineParts[0], resistances);
                    resistances = new HashMap<>();
                    mutationFile.addMutation(mutation);

                    if(!secondLineisDefinition){
                        throw new FileFormatException("First line of mutation pattern CSV file must be a header.");
                    }

                }
            }
        }







        return mutationFile;
    }

    @Override
    public boolean canReadFile(String filename) {
        return filename.endsWith(".csv");
    }
}
