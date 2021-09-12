package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Homework0 {

    /**
     * Prompts user for number inputs and prints the difference between the largest and smallest
     */
    public void calculateBetweenLargestAndSmallest(){

        List<Integer> numList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a number");
        String input = scanner.nextLine();
        numList.add(Integer.parseInt(input));

        // Quits on character zero
        while(!input.equals("0")){
            System.out.println("Enter another number or enter '0' to quit");
            input = scanner.nextLine();
            numList.add(Integer.parseInt(input));
        }

        // At least two inputs are needed besides zero
        if(numList.size() >= 3){
            numList.remove(numList.size() - 1);

            Collections.sort(numList);

            System.out.println("Difference between largest number and smallest number is " +
                    (numList.get(numList.size() - 1) - numList.get(0)));
        } else {
            System.out.println("You must enter at least 2 numbers");
        }
    }

    /**
     * Prompts user for two dates and calculates the difference between them
     */
    public void calculateDifferenceBetweenTwoDates() {

        // Map for number of days in each month
        Map<String, Integer> calendarMap = calendarMap();

        // Map for indexes of each month
        Map<String, Integer> monthIndexes = monthIndexes();

        // Lookup array for months
        String [] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the first date");
        String firstDate = scanner.nextLine();

        System.out.println("Please enter the second date");
        String secondDate = scanner.nextLine();

        String firstDateSplit [] = firstDate.split(" ");
        String secondDateSplit [] = secondDate.split(" ");

        // There must be entry for date and month
        if(firstDateSplit.length <= 1 || secondDateSplit.length <= 1) {
            System.out.println("Month followed by day must be entered");
            return;
        }

        Integer firstDateDay = Integer.parseInt(firstDateSplit[1]);
        Integer daysOnFirstDateMonth = calendarMap.get(firstDateSplit[0]);

        Integer secondDateDay = Integer.parseInt(secondDateSplit[1]);

        Integer indexOfFirstMonth = monthIndexes.get(firstDateSplit[0]);
        Integer indexOfSecondMonth = monthIndexes.get(secondDateSplit[0]);

        // Months must be in chronological order
        if(indexOfFirstMonth > indexOfSecondMonth){
            System.out.println("The dates you entered are not in the correct order");
            return;
        }

        int differenceBetweenDates = 0;

        // Add up the days in between the two months
        for(int i = indexOfFirstMonth + 1; i < indexOfSecondMonth; i++){
            differenceBetweenDates += calendarMap.get(months[i - 1]);
        }

        // Add remaining days from the first month
        differenceBetweenDates += (daysOnFirstDateMonth - firstDateDay);

        // Add days from the second month
        differenceBetweenDates += secondDateDay;

        System.out.println("The difference between the days you entered is " + differenceBetweenDates);
    }

    /**
     * Helper for number of days in a month
     * @return
     */
    private static Map<String, Integer> calendarMap() {

        Map<String, Integer> calendarMap = new HashMap<>();

        calendarMap.put("Jan", 31);
        calendarMap.put("Feb", 28); // Leap years can't be calculated because user is not prompted for a year
        calendarMap.put("Mar", 31);
        calendarMap.put("Apr", 30);
        calendarMap.put("May", 31);
        calendarMap.put("Jun", 30);
        calendarMap.put("Jul", 31);
        calendarMap.put("Aug", 31);
        calendarMap.put("Sep", 30);
        calendarMap.put("Oct", 31);
        calendarMap.put("Nov", 30);
        calendarMap.put("Dec", 31);

        return calendarMap;
    }

    /**
     * Helper for index of months
     * @return
     */
    private static Map<String, Integer> monthIndexes(){

        Map<String, Integer> monthIndexes = new HashMap<>();

        monthIndexes.put("Jan", 1);
        monthIndexes.put("Feb", 2);
        monthIndexes.put("Mar", 3);
        monthIndexes.put("Apr", 4);
        monthIndexes.put("May", 5);
        monthIndexes.put("Jun", 6);
        monthIndexes.put("Jul", 7);
        monthIndexes.put("Aug", 8);
        monthIndexes.put("Sep", 9);
        monthIndexes.put("Oct", 10);
        monthIndexes.put("Nov", 11);
        monthIndexes.put("Dec", 12);

        return monthIndexes;
    }

    /**
     * Method for reading from file
     * @param fileName
     */
    public void readFromFile(String fileName){

        List<String> lines = new ArrayList<>();

        try {

            File file = new File(fileName);
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine()){
                String data = reader.nextLine();
                lines.add(data);
            }

            countNumberOfCharacters(lines);
        } catch (FileNotFoundException ex){
            System.out.println("File not found " + ex.getCause());
        }
    }

    /**
     * Method for counting number of characters in a file and sorting
     * @param lines
     */
    public void countNumberOfCharacters(List<String> lines){

        int countOfCharacters[] = new int[26];
        Map<Character, Integer> charsCountMap = new LinkedHashMap<>();

        /**
         * Increment the array indexed by the number of characters in the alphabet
         */
        for(String line : lines){
            for(char c : line.toLowerCase().toCharArray()){
                if(c >= 'a' && c <= 'z'){
                    int index = c - 'a';
                    countOfCharacters[index]++;
                }
            }
        }

        /**
         * Map for storing characters and their count as a key value pair
         */
        for(int index = 0; index < countOfCharacters.length; index++){
            char c = (char) (index + 97);
            charsCountMap.put(c, countOfCharacters[index]);
        }

        /**
         * Sorts the map by value
         */
        Map<Character, Integer> sortedCount =
                charsCountMap.entrySet().stream().sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (el1, el2) -> el1, LinkedHashMap::new));

        for(char c: sortedCount.keySet()){
            System.out.println("Character: " + c + " Count: " + sortedCount.get(c));
        }
    }

    public static void main(String[] args) {

        Homework0 hw = new Homework0();
        hw.calculateBetweenLargestAndSmallest();
        hw.calculateDifferenceBetweenTwoDates();
        hw.readFromFile("mobydick.txt");
    }
}
