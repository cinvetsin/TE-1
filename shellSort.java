import java.util.*;
import java.io.*;

public class shellSort {
    public static final int C = 4;

    public static void exchange(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void compareExchange(int[] arr, int i, int j) {
        if (((i < j) && (arr[i] > arr[j])) || ((i > j) && (arr[i] < arr[j]))) {
            exchange(arr, i, j);
        }
    }

    public static void permuteRandom(int arr[], Random rand) {
        for (int i = 0; i < arr.length; i++) { // Use knuth random permutation algorithm
            exchange(arr, i, rand.nextInt(arr.length - i) + i);
        }
    }

    // Compare-exchange two regions of length offset each
    public static void compareExchange(int[] arr, int s, int t, int offset, Random rand) {
        int mate[] = new int[offset]; // index offset array
        for (int count = 0; count < C; count++) {
            for (int i = 0; i < offset; i++) {
                mate[i] = i;
                permuteRandom(mate, rand);
            }
            for (int i = 0; i < offset; i++) {
                compareExchange(arr, s + i, t + mate[i]);
            }
        }
    }

    public static void randomizedShellSort(int[] arr){
        int n = arr.length;
        Random rand = new Random();
        for (int offset = n/2; offset > 0; offset /= 2) {
            for (int i=0; i < n - offset; i += offset){ // compare-exchange up
                compareExchange(arr,i,i+offset,offset,rand);
                System.out.println("After compare-exchange up with offset " + offset + ":");
                printArray(arr); // Debug print
            }
            for (int i=n-offset; i >= offset; i -= offset){ // compare-exchange down
                compareExchange(arr,i-offset,i,offset,rand);
                System.out.println("After compare-exchange down with offset " + offset + ":");
                printArray(arr); // Debug print
            }
            for (int i=0; i < n-3*offset; i += offset){ // compare 3 hops up
                compareExchange(arr,i,i+3*offset,offset,rand);
                System.out.println("After compare 3 hops up with offset " + offset + ":");
                printArray(arr); // Debug print
            }
            for (int i=0; i < n-2*offset; i += offset){ // compare 2 hops up
                compareExchange(arr,i,i+2*offset,offset,rand);
                System.out.println("After compare 2 hops up with offset " + offset + ":");
                printArray(arr); // Debug print
            }
            for (int i=0; i < n; i += 2*offset){ // compare odd-even regions
                compareExchange(arr,i,i+offset,offset,rand);
                System.out.println("After compare odd-even regions with offset " + offset + ":");
                printArray(arr); // Debug print
            }
            for (int i=offset; i < n-offset; i += 2*offset){ // compare even-odd regions
                compareExchange(arr,i,i+offset,offset,rand);
                System.out.println("After compare even-odd regions with offset " + offset + ":");
                printArray(arr); // Debug print
            }
        }   
    }

    public static void printArray(int arr[]){
        int n = arr.length;
        for(int i=0; i<n; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public static int[] readFromCSV(String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            List<Integer> numbers = new ArrayList<>();
            while (scanner.hasNextInt()) {
                numbers.add(scanner.nextInt());
            }
            return numbers.stream().mapToInt(Integer::intValue).toArray();
        }
    }     

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        
        String csvFile = "reversed_small.csv"; // Please change the file name here, as you wished
        int[] arr = new int[0];
        try {
            arr = readFromCSV(csvFile);
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        // For example
        // int n = 8; // Size of the array
        // int upperLimit = 100; // Upper limit of random integers (exclusive)
        
        // Random rand = new Random(); // Instance of Random
        // int[] arr = new int[n];

        // for (int i = 0; i < arr.length; i++) {
        //     arr[i] = rand.nextInt(upperLimit);
        // }

        System.out.println("Original array:");
        printArray(arr);
    
        randomizedShellSort(arr);
    
        System.out.println("Sorted array:");
        printArray(arr);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;  // time in milliseconds
        System.out.println("Execution time in milliseconds: " + elapsedTime);

        long totalMemory = runtime.totalMemory();  // Total allocated memory
        long freeMemory = runtime.freeMemory();    // Unused memory from the allocated memory
        long usedMemory = totalMemory - freeMemory; // Used memory
        double percentage = ((double) usedMemory/ (double) totalMemory)*100;

        System.out.println("Total Memory (in bytes): " + totalMemory);
        System.out.println("Free Memory (in bytes): " + freeMemory);
        System.out.println("Used Memory (in bytes): " + usedMemory);
        System.out.println("Percentage of Used Memory: " + String.format("%.10f", percentage) +"%");
    }
}