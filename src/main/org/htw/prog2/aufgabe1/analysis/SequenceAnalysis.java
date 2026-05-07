package org.htw.prog2.aufgabe1.analysis;

import org.htw.prog2.aufgabe1.files.MutationFile;
import org.htw.prog2.aufgabe1.files.SequenceFile;

import java.util.HashMap;


public abstract class SequenceAnalysis {

    String reference;
    SequenceFile sequences;
    MutationFile mutations;
    HashMap<String, Double> resistances = null;
    String bestDrugKey;

    public SequenceAnalysis(String reference, SequenceFile sequences, MutationFile mutations) {
        this.reference = reference;
        this.sequences = sequences;
        this.mutations = mutations;

    }

    public abstract void calculateResistances();

    public HashMap<String, Double> getResistances() {
        if(this.resistances == null){
            calculateResistances();
        }
        return this.resistances;
    }

    public void determineBestDrug(){

        getResistances();

        double lowestResistance = Double.MAX_VALUE;
        for(String key : this.resistances.keySet()){

            double resistance = this.resistances.get(key);

            if(resistance < lowestResistance){
                lowestResistance = resistance;
                this.bestDrugKey = key;
            }
        }


    }

    public SequenceFile getSequences(){
        return this.sequences;
    }

    public MutationFile getMutations(){
        return this.mutations;
    }

    public String getReference(){
        return this.reference;
    }

    public double getBestDrugResistance(){
        return this.resistances.get(this.bestDrugKey);
    }

    public String getBestDrug() {
        return this.bestDrugKey;
    }

    public String getDrugDescriptions() {
        return "";
    }

}
