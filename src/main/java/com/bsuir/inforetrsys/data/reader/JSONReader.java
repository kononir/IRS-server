package com.bsuir.inforetrsys.data.reader;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONReader {
    public List<String> read(String fileName) {
        try {
            Reader reader = new FileReader(fileName);
            JSONArray array = (JSONArray) new JSONParser().parse(reader);
            reader.close();

            Iterator iterator = array.iterator();
            List<String> words = new ArrayList<>();
            while (iterator.hasNext()) {
                words.add((String) iterator.next());
            }

            return words;
        } catch (IOException | ParseException e) {
            throw new JSONReaderException("Error when read file with name " + fileName, e);
        }
    }
}
