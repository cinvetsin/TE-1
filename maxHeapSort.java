import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class maxHeapSort {

    public static void heapify(int arr[], int n, int i){
        int largest = i; // Initialize largest as root
        int left = 2*i+1;
        int right = 2*i+2;

        // If left child is larger than root
        if(left < n && arr[left] > arr[largest]){
            largest = left;
        }

        // If right child is larger than root
        if(right < n && arr[right] > arr[largest]){
            largest = right;
        }

        // If root is not largest
        if(largest != i){
            // Swap arr[i] with arr[largest]
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            // Debug: print the array after each swap
            System.out.println("Heapify swap: " + arr[i] + " with " + arr[largest]);
            printArray(arr);

            heapify(arr, n, largest);
        }
    }

    public static void heapSort(int arr[]){
        int n = arr.length;

        // Build heap
        for(int i=n/2-1; i>=0; i--){
            heapify(arr, n, i);
        }
    }

    public static int[] readFromCSV(String filename) throws IOException {
        ArrayList<Integer> numbers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        }
        return numbers.stream().mapToInt(i -> i).toArray();
    }    

    public static void printArray(int arr[]){
        int n = arr.length;
        for(int i=0; i<n; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        
        String csvFile = "reversed_large.csv"; // You may change which dataset to be read
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
    
        heapSort(arr);
    
        System.out.println("Sorted array (root to leaves from right to left):");
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
