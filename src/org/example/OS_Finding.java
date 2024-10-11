package org.example;

import java.io.*;
import java.util.*;

public class OS_Finding {

    // Function to read the file and parse the integers into an array
    public static int[] readInputFile(String fileName) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            numbers.add(Integer.parseInt(line.trim()));
        }
        reader.close();
        // Convert List to array
        return numbers.stream().mapToInt(Integer::intValue).toArray();
    }

    // Partition function with random pivot
    public static int randomized_partition(int[] A, int left, int right) {
        Random rand = new Random();
        int i = rand.nextInt(right - left + 1) + left;  // Random pivot
        swap(A, i, right);  // Swap pivot to the end
        return partition(A, left, right);  // Perform regular partition
    }

    // Regular partition function used in QuickSort
    public static int partition(int[] A, int left, int right) {
        int pivot = A[right];
        int index = left;
        for (int i = left; i < right; i++) {
            if (A[i] <= pivot) {
                swap(A, i, index);
                index++;
            }
        }
        swap(A, index, right);  // Move pivot to its correct position
        return index;  // Return the position of the pivot
    }

    // Function to swap two elements in an array
    public static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    // RandomizedSelect function to find the ith order statistic
    public static int RandomizedSelect(int[] A, int p, int r, int i) {
        if (p == r) {
            return A[p];  // If there's only one element, return it
        }
        int q = randomized_partition(A, p, r);
        int k = q - p + 1;  // Number of elements in the left partition
        if (i == k) {
            return A[q];  // The pivot is the ith smallest element
        } else if (i < k) {
            return RandomizedSelect(A, p, q - 1, i);  // Search in the left partition
        } else {
            return RandomizedSelect(A, q + 1, r, i - k);  // Search in the right partition
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("use this file by putting this into terminal: java OS_Finding data.txt <i>");
            return;
        }

        try {
            String fileName = args[0];
            int i = Integer.parseInt(args[1]);  // Parse the value of i

            // Read the array from the file
            int[] A = readInputFile(fileName);
            int n = A.length;

            if (i < 1 || i > n) {
                System.out.println("null");  // i is out of bounds
            } else {
                // Call RandomizedSelect to find the ith order statistic
                int ithOrderStatistic = RandomizedSelect(A, 0, n - 1, i);
                System.out.println(ithOrderStatistic);
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for i: " + e.getMessage());
        }
    }
}
