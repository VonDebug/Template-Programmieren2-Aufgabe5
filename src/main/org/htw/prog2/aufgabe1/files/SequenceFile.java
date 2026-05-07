package org.htw.prog2.aufgabe1.files;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class SequenceFile implements HIVFile {

    LinkedHashSet<String> orderedSequences = new LinkedHashSet<>();

    public SequenceFile(){

    }

    public void addSequence(String sequence){
        orderedSequences.add(sequence);
    }

    public HashSet<String> getSequences(){


        return new HashSet<>(orderedSequences);
    }

    public String getFirstSequence(){

        return orderedSequences.iterator().next();
    }

    public int getNumberOfSequences(){

        return orderedSequences.size();
    }

    public boolean containsSequence(String sequence){

        return getSequences().contains(sequence);
    }

}
