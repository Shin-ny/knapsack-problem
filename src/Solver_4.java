import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 *
 */
public class Solver_4 {

    public static int capacity;
    public static int[] weights;
    public static int[] values;
    public static int items;
    public static int[] taken;

    public static double[][] valueDen;
    public static int currentBest = 0; //for the deepsearch
    public static int value = 0;
    
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
        valueDen = new double[items][3];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i-1] = Integer.parseInt(parts[0]);
          weights[i-1] = Integer.parseInt(parts[1]);
        }


        //construct the valueDensity
        for(int i=0; i < items; i++) {
            valueDen[i][0] = (double)values[i] / (double)weights[i]; //valueDensity
            valueDen[i][1] = i; //where is this item
            valueDen[i][2] = 0; //whether this item has been chosen or not
        }

        //sort the item by value density
        //sort from small to large!!
        java.util.Arrays.sort(valueDen, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(a[0], b[0]);
            }
        }); 

        /*
         * Waiting to be solved: the estimatedOptimal function is somehow wrong!
         *
         * Algorithms 3:
         * Branch and Bound: Deepsearch
         * Although implement the method described in the course
         * There are two problems:
         * 1) The output is not optimal at all(even worse than dynamic programming)
         * 2) Can't trace back to see which is selected or not
        */
        int weight = 0;
        taken = new int[items];
        DeepSearch(capacity, 0, GenerateSelect(), true);
        DeepSearch(capacity, 0, GenerateSelect(), false);
        value = currentBest;
        

        // prepare the solution in the specified output format
        System.out.println(value);
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");        
    }


    public static double EstimateOptimal(int select, boolean get) {

        double linearOptimal = 0;
        int weightTemp = 0;
        int index = items-1;

        if(!get) {
            for(int i=0; i < items; i++) {
                if(select == (int)valueDen[i][1])
                    valueDen[i][0] = 0;
            }
        }



        while(weightTemp < capacity && index >= 0) {
            int i = (int)valueDen[index][1];
            if(valueDen[index][0] != 0) {
                weightTemp += weights[i];
                if(weightTemp <= capacity) {
                  linearOptimal += valueDen[index][0] * weights[i];
                } else {
                    double fraction = (double)(capacity - (weightTemp-weights[i])) / (double)weights[i];
                    linearOptimal += values[i] * fraction;
                    weightTemp = capacity;
                }
            } 
            index--;
        }

        return linearOptimal;     
    }

    public static int GenerateSelect() {

        /* try to decide whether get the item or not by the order of some random order
         * but when coming to the end, a lot of item is already considered not selected, 
         * and random will be hard to find some item that hasn't been considered
         * So that after a few times of random and still can't find an item that hasn't been considered
         * then just get the most front unconsidered one.
        */ 
        Random ran = new Random();
        int select = ran.nextInt(items);
        int num = 0;
        while(valueDen[select][2] == 1 && num <= 5) {
            select = ran.nextInt(items);
            num++;
        }
        if(valueDen[select][2] != 1) {
            valueDen[select][2] = 1;
            return (int)valueDen[select][1];
        } else {
            for(int i=0; i < items; i++) {
                if(valueDen[i][2] != 1) {
                    valueDen[i][2] = 1;
                    return (int)valueDen[i][1];
                }      
            }
        }
        return -1;

        // //try to decide whether get the item or not by the order of valueDen
        // int select = items - 1;
        // while(valueDen[select][2] == 1 && select >= 0) {
        //     select = select - 1;
        // }
        // if(select == 0) {
        //     return -1;
        // } else {
        //     valueDen[select][2] = 1;
        //     return (int)valueDen[select][1];
        // }
        
        
    }



        // DeepSearch(Remaincapacity, currentValue, CurrentPacage(from 0), whetherTakeornot);
    public static void DeepSearch(int room, int value, int select, boolean get) {

        if(select == - 1) {
            return;
        }

        double optimal = EstimateOptimal(select, get);
        
        if(optimal <= currentBest) { //no need to dig in, can abandon the whole tree
            return;
        } else {
            int newValue;
            int newRoom;

            if(get) {
                newValue = value + values[select];
                newRoom = room - weights[select];
            } else {
                newValue = value;
                newRoom = room;
            }

            if(newRoom < 0) return; //out of capacity

            if(newValue > currentBest) {
                currentBest = newValue;
                if(get) {
                    taken[select] = 1;
                }
            }
            

            DeepSearch(newRoom, newValue, GenerateSelect(), true);
            DeepSearch(newRoom, newValue, GenerateSelect(), false);

        }
        
    }  
}