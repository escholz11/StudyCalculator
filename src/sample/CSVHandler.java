package sample;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class CSVHandler {

    // default values initiated here to be set later
    private String column;
    private LinkedList list = new LinkedList();
    private String[] split = null;
    private String csvPath;
    public static int Repeating = 0;
    public static int Counter = 0;

    public LinkedList getList() {
        return this.list;
    }

    /**
     * main method
     *
     * @param args
     * @return no return
     */
    public static void main(String[] args) {
        String csvPath = "C:/Users/Main/IdeaProjects/Study Calculator/src/sample/telematik.csv";
        CSVHandler c = new CSVHandler();
        c.readFile(csvPath);
        //c.readFile(csvPath);
        //c.printFile();
        //c.writeFile();
    }

    /**
     * read file from directory
     *
     * @return file content in ArrayList
     */
    public List readFile(String path) {
        try {
            FileReader file = new FileReader(
                    path);
            BufferedReader data = new BufferedReader(file);
            while ((column = data.readLine()) != null) {
                split = column.split(";");
                for (int i = 0; i < split.length; i++) {
                    // dont use empty column
                    if (!(split[i].equals("")) && !(split[i].equals("question")) && !(split[i].equals("answer1")) && !(split[i].equals("answer2")) && !(split[i].equals("answer3")) && !(split[i].equals("answer4")) && !(split[i].equals("Fragen")) && !(split[i].equals("Antworten"))) {
                        // remove space between 2 ';' entfernen
                        list.add(split[i].trim());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Datei nicht gefunden");
        } catch (IOException e) {
            System.out.println("E/A-Fehler");
        }

        return list;
    }

    /**
     * print content from ArrayList
     *
     * @param no param
     * @return no return
     */
    public void printFile() {
        int max = list.size();
        for (int i = 0; i < max; i++) {
            System.out.println(list.get(i));

        }
    }

    public String csvSelector(String studyCourse) {
        if (studyCourse.equals("Telematik")) {
            csvPath = "csvfiles/telematik.csv";
        }

        if (studyCourse.equals("Bioinformatik")) {
            csvPath = "csvfiles/bioinformatik.csv";
        }

        if (studyCourse.equals("Maschinenbau")) {
            csvPath = "csvfiles/maschinenbau.csv";
        }

        if (studyCourse.equals("Wirtschaftsingenieurwesen")) {
            csvPath = "csvfiles/wirtschaftsingenieurwesen.csv";
        }

        if (studyCourse.equals("Logistik")) {
            csvPath = "csvfiles/logistik.csv";
        }

        return csvPath;
    }

    public String csvSolutionSelector(String studyCourse) {
        if (studyCourse.equals("Telematik")) {
            csvPath = "csvfiles/telematikloesung.csv";
        }

        if (studyCourse.equals("Bioinformatik")) {
            csvPath = "csvfiles/bioinformatikloesung.csv";
        }

        if (studyCourse.equals("Maschinenbau")) {
            csvPath = "csvfiles/maschinenbauloesung.csv";
        }

        if (studyCourse.equals("Wirtschaftsingenieurwesen")) {
            csvPath = "csvfiles/wirtschaftsingenieurwesenloesung.csv";
        }

        if (studyCourse.equals("Logistik")) {
            csvPath = "csvfiles/logistikloesung.csv";
        }

        return csvPath;
    }
}