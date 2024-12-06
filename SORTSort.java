//Written by Dylan Lesesne

import java.util.ArrayList;
import java.util.Scanner;

// Class to store each string and its "SORT" count
class StringEntry {
    String text;
    int sortCount;

    public StringEntry(String text, int sortCount) {
        this.text = text;
        this.sortCount = sortCount;
    }
}

// Main class to analyze strings for "SORT"
public class SORTSort {
    // Method to count occurrences of "SORT" in a string
    public static int countSorts(String str) {
        String lowerCaseStr = str.toLowerCase();
        String target = "sort";
        int count = 0, index = 0;
        while ((index = lowerCaseStr.indexOf(target, index)) != -1) {
            count++;
            index += target.length();
        }
        return count;
    }

    // Merge Sort to sort StringEntry objects 
    public static void mergeSort(ArrayList<StringEntry> entries, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(entries, left, middle);
            mergeSort(entries, middle + 1, right);
            merge(entries, left, middle, right);
        }
    }

    private static void merge(ArrayList<StringEntry> entries, int left, int middle, int right) {
        ArrayList<StringEntry> leftList = new ArrayList<>(entries.subList(left, middle + 1));
        ArrayList<StringEntry> rightList = new ArrayList<>(entries.subList(middle + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).sortCount <= rightList.get(j).sortCount) {
                entries.set(k++, leftList.get(i++));
            } else {
                entries.set(k++, rightList.get(j++));
            }
        }

        while (i < leftList.size()) {
            entries.set(k++, leftList.get(i++));
        }
        while (j < rightList.size()) {
            entries.set(k++, rightList.get(j++));
        }
    }

    // Method to enter and sort strings based on the number of "SORTs"
    public static void startSorting() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<StringEntry> entries = new ArrayList<>();

        System.out.println("Enter any number of strings, and I will sort by 'SORT' count. Enter 'quit' to finish:");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) break;

            int count = countSorts(input);
            entries.add(new StringEntry(input, count));
        }

        if (!entries.isEmpty()) {
            mergeSort(entries, 0, entries.size() - 1);

            System.out.println("\nSORTed results:");
            for (StringEntry entry : entries) {
                System.out.println(entry.text);
            }
        }

        System.out.println("\nWould you like to sort more Strings? (yes/no)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            startSorting();
        } else {
            System.out.println("Goodbye!");
        }
    }

    public static void main(String[] args) {
        startSorting();
    }
}
