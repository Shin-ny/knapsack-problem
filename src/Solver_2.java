import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 *
 */
public class Solver_2 {
    
    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Read the instance, solve it, and print the solution in the standard output
     */
    public static void solve(String[] args) throws IOException {
        String fileName = null;
        
        // get the temp file name
        for(String arg : args){
            if(arg.startsWith("-file=")){
                fileName = arg.substring(6);
            } 
        }
        if(fileName == null)
            return;
        
        // read the lines out of the file
        List<String> lines = new ArrayList<String>();

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        try {
            String line = null;
            while (( line = input.readLine()) != null){
                lines.add(line);
            }
        }
        finally {
            input.close();
        }
        
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        int items = Integer.parseInt(firstLine[0]);
        int capacity = Integer.parseInt(firstLine[1]);

        int[] values = new int[items];
        int[] weights = new int[items];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i-1] = Integer.parseInt(parts[0]);
          weights[i-1] = Integer.parseInt(parts[1]);
        }

        //construct the valueDensity and sort it:
        double[][] valueDen = new double[items][2];
        for(int i=0; i < items; i++) {
            valueDen[i][0] = (double)values[i] / (double)weights[i];
            valueDen[i][1] = i;
        }

        //sort the item by value density
        //sort from small to large!!
        java.util.Arrays.sort(valueDen, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(a[0], b[0]);
            }
        }); 


        //Take the item one by one from the biggest value density
        int value = 0;
        int weight = 0;
        int[] taken = new int[items];
        for(int i=items - 1; i >= 0; i--){
            if(weight + weights[(int)(valueDen[i][1])] <= capacity){
                taken[(int)(valueDen[i][1])] = 1;
                value += values[(int)(valueDen[i][1])];
                weight += weights[(int)(valueDen[i][1])];
            } else {
                taken[(int)(valueDen[i][1])] = 0;
            }
        }
        
        // prepare the solution in the specified output format
        System.out.println(value);
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");        
    }
}