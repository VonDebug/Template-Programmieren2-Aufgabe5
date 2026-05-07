package org.htw.prog2.aufgabe1.files;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mutation {
    String variant;
    HashMap<String,Double> resistances;

    public Mutation(String variant, HashMap<String, Double> resistances){
        this.variant = variant;
        this.resistances = resistances;
    }

    public String getVariant(){

        return this.variant;
    }

    public HashMap<String, Double> getResistances(){

        return resistances;
    }

    public String getSequence(String reference){

        StringBuilder buildReference = new StringBuilder(reference);

        Pattern pattern = Pattern.compile("(\\d+)([A-Z])");

        for(String mutation : variant.split(",")){

            Matcher matcher = pattern.matcher(mutation);

            if(!matcher.matches()){
                throw new IllegalArgumentException("Ungueltige Mutation: " + mutation);
            }

            int position = Integer.parseInt(matcher.group(1));

            String variable = matcher.group(2);

            buildReference.replace(position -1, position, variable);

        }


        return buildReference.toString();
    }



}
