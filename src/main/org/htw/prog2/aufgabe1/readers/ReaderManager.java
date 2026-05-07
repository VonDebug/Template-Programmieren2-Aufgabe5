package org.htw.prog2.aufgabe1.readers;

import org.htw.prog2.aufgabe1.exceptions.NoValidReadersException;

import java.util.ArrayList;
import java.util.List;

public class ReaderManager <T extends HIVFileReader> {

    private final List<T> readers = new ArrayList<>();

    public void addReader(T reader){

        readers.add(reader);


    }

    public T getReaderForFile(String filename)throws NoValidReadersException {

        for (T fileReader : readers) {
            if(fileReader.canReadFile(filename)){
                return fileReader;
            }

        }throw new NoValidReadersException("Kein Reader entdeckt");

    }

}

