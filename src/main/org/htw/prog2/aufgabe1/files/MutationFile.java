package org.htw.prog2.aufgabe1.files;

import java.util.ArrayList;
import java.util.LinkedList;

public class MutationFile implements HIVFile{

    ArrayList<String> drugList = new ArrayList<>();
    ArrayList<Mutation>mutations = new ArrayList<>();

    public MutationFile(){

    }


    public void addDrug(String drug){
        drugList.add(drug);
    }

    public LinkedList<String> getDrugs(){

        return new LinkedList<>(drugList);
    }

    public void addMutation(Mutation mutation){
        mutations.add(mutation);
    }

    public LinkedList<Mutation> getMutations(){

        return new LinkedList<>(mutations);
    }

    public int getNumberOfMutations(){

        return mutations.size();
    }

}
