import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 *
 */
public class Solver_5 {

    public static int[] taken;
    public static int value = 0;
    public static int[] values;
    public static int[] weights;
    public static int items;
    public static int capacity;

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


        
        taken = new int[items];
        int maxTries = 100000;

        //making the original node:
        for(int i=0; i < items; i++) {
            double a = Math.random();
            if(a < 1 / (double)items) 
                taken[i] = 1;
        }

        Node origin = new Node();
        origin.value = score(taken);
        value = origin.value;
        origin.taken = taken;

        for(int i=0; i < maxTries; i++) {
            Node n = hillClimber(origin);
            origin = n;
        }



        
        // prepare the solution in the specified output format
        System.out.println(value);
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");        
    }

    public static class Node {
        int value;
        int[] taken = new int[items];
    }

    public static Node hillClimber(Node oldN) {
        Node n = new Node();
        for(int i=0; i < items; i++) {
            double a = Math.random();
            if(a < 1 / (double)items) 
                n.taken[i] = 1 - oldN.taken[i];
            else
                n.taken[i] = oldN.taken[i];
        }

        int currentValue = score(n.taken);
        n.value = currentValue;

        if(currentValue > value) {
            taken = n.taken;
            value = n.value;
            return n;
        } else if(currentValue > oldN.value) {
            return n;
        } else {
            return oldN;
        }
        
    }

    public static int score(int[] taken) {
        int weight = 0;
        int currentValue = 0;
        for(int i=0; i < items; i++){
            weight = weight + taken[i] * weights[i];
            currentValue = currentValue + taken[i] * values[i];
        }
        if(weight > capacity) return 0;
        return currentValue;
    }

    public static int penalizedScore(int[] taken) {
        int weight = 0;
        int currentValue = 0;
        for(int i=0; i < items; i++){
            weight = weight + taken[i] * weights[i];
            currentValue = currentValue + taken[i] * values[i];
        }
        if(weight > capacity) return -currentValue;
        return currentValue;
    }




}
