package com.ericsson.log;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smcgrath on 7/11/2017.
 */
public class Main {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "ID,Lock,Unlock,Time";



    private static class Record {

        private String ID;
        private String lock;
        private String unlock;
        private String time;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getLock() {
            return lock;
        }

        public void setLock(String lock) {
            this.lock = lock;
        }

        public String getUnlock() {
            return unlock;
        }

        public void setUnlock(String unlock) {
            this.unlock = unlock;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "ID='" + ID + '\'' +
                    ", lock='" + lock + '\'' +
                    ", unlock='" + unlock + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

    }

    public static void main(String[] args) throws Exception {


        Scanner s = new Scanner(new FileReader(new File("C:\\temp\\lock-unlock.txt")));
        //Record currentRecord = null;
        List<Record> list = new ArrayList<>();

        while (s.hasNextLine()) {
            String id, lock, unlock, time;
            String line = s.nextLine();

            // String[] pieces = line.split("\\s+");

            Record newRec = new Record();

            id  = regexChecker("ieatrcxb\\d{4}-\\d", line);
            newRec.setID(id);

            lock  = regexChecker("\\bLock\\b", line);
            //newRec.lock = regexChecker(".*\\\\bLock\\\\b", line);
            newRec.setLock(lock);

            //newRec.unlock = regexChecker(".*\\\\bUnlock\\\\b.*", line);
            unlock = regexChecker("\\bUnlock\\b", line);
            newRec.setUnlock(unlock);

            time = regexChecker("([0-1]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)", line);
            newRec.setTime(time);

            list.sort(Comparator.comparing(Record::getID));
            list.add(newRec);

            //System.out.println(Arrays.toString(list.toArray()));

        }
        writeCSVFile("C:\\temp\\lock-unlock.csv", list);
    }

    public static String regexChecker(String regEx, String str2Check) {

        Pattern checkRegex = Pattern.compile(regEx);
        Matcher regexMatcher = checkRegex.matcher(str2Check);
        
        String regMat = "  ";
        while(regexMatcher.find()){
            if(regexMatcher.group().length() !=0){
                System.out.println("Inside the "+ regexMatcher.group());
                regMat = regexMatcher.group();
            }
            //System.out.println("Inside the "+ regexMatcher.group().trim());
        }

         return regMat;
    }

    public static void writeCSVFile(String fileName, List<Record> list){

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header
            fileWriter.append(FILE_HEADER.toString());
            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);
            //Write a new student object list to the CSV file
            for (Record record : list) {
                fileWriter.append(String.valueOf(record.ID));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(record.lock);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(record.unlock);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(record.time);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            System.out.println("CSV file was created successfully !!!");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }

}






