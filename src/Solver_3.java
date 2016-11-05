import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 *
 */
public class Solver_3 {

    public static int capacity;
    public static int[] weights;
    public static int[] values;
    public static int[] taken;
    public static int trace;
    public static int[][] table;
    public static int items;
    
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
        items = Integer.parseInt(firstLine[0]);
        capacity = Integer.parseInt(firstLine[1]);

        values = new int[items];
        weights = new int[items];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i-1] = Integer.parseInt(parts[0]);
          weights[i-1] = Integer.parseInt(parts[1]);
        }




        //Dynamic programming:
        int value = 0;
        int weight = 0;
        taken = new int[items];
        table = new int[items][capacity+1];
        trace = capacity;
        for(int i=0; i < items; i++) {
            MakeTable(i);
        }
        for(int i=items-1; i >= 0; i--) {
            TraceBack(i);
        }

        for(int i=0; i < items; i++) {
            if(taken[i] == 1) {
                value = value + values[i];
            }
        }
        


        // prepare the solution in the specified output format
        System.out.println(value+" 0");
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");        
    }


    public static void MakeTable(int i) {
        if(i == 0) {
            for(int j=0; j < capacity+1; j++) {
            if(weights[0] <= j) {
                table[0][j] = values[0];
                table[1][j] = values[0];
            }
        }
        } else if(i == items - 1) {
            for(int j=0; j < capacity+1; j++) {
            if(weights[i] <= j) {
                if(values[i] + table[i-1][j-weights[i]] > table[i-1][j]) {
                    table[i][j] = values[i] + table[i-1][j-weights[i]];
                } else {
                    table[i][j] = table[i-1][j];
                }
                
            }
        }
        } else {
            for(int j=0; j < capacity+1; j++) {
            if(weights[i] <= j) {
                if(values[i] + table[i-1][j-weights[i]] > table[i-1][j]) {
                    table[i][j] = values[i] + table[i-1][j-weights[i]];
                    table[i+1][j] = table[i][j];
                } else {
                    table[i][j] = table[i-1][j];
                    table[i+1][j] = table[i][j];
                }
                
            }
        }
        }
    }
    
    public static void TraceBack(int i) {
        if(i == 0) {
            //do nothing (already trace back to the top)
        } else {
            if(table[i][trace] == table[i-1][trace]) {
                taken[i] = 0;
            } else {
                taken[i] = 1;
                trace = trace - weights[i];
            }
        }
    }
}