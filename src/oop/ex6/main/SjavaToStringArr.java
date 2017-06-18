package oop.ex6.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Convert an s-java file to String array.
 */
class SjavaToStringArr {
    /**
     * Returns the lines of the commands file in an array
     *
     * @param path - The path of the commands file (may be absolute or relative)
     * @return - The lines of the commands file in an array as String[].
     */
    static String[] toStringArray(String path) throws IOException {
        // A list for the file's lines
        List<String> commandLinesList = new ArrayList<>();

        // Create a new reader to read the file's lines
        BufferedReader reader = new BufferedReader(new FileReader(path));

        //Add file lines to fileContent
        String line = reader.readLine();
        while (line != null) {
            commandLinesList.add(line);
            line = reader.readLine();
        }

        reader.close();

        // Convert the list to a String array and return it
        String[] lines = new String[commandLinesList.size()];
        commandLinesList.toArray(lines);
        return lines;
    }
}
