# knapsack-problem

## Input Format:

```
n K
v_0 w_0
v_1 w_1
... ...
v_n-1 w_n-1
```

* Every problem has input contains n + 1 lines
* n: number of items in the problem
* K: the capacity of the knapsack
* The remaining n lines present the data for each of the items: value & weight

## Output Format:

```
V
x_0 x_1 x_2 ... x_n-1
```

* V: the total value of the items selected to go into the knapsack
* The second line is a list of n 0/1-values, one for each of the x_i variables. (0 -> didn't take it; 1 -> take it)

## Run the program:
1. First compile the java file: `javac Solver_#.java` 
2. Then run the python code: `python ./solver.py ../data/<inputFileName>`

## Example:
```
python ./solver.py ../data/ks_4_0 
> 19
> 0 0 1 1
```
