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

## Solver:
* __Solver_1__: a trivial greedy algorithm for filling the knapsack, it takes items in the original order until the knapsack is full
* __Solver_2__: a slightly better algorithm comparing the first one. It first sort the items by value density (value/weight), then take items in that order until the knapsack is full
* __Solver_3__: DP(dynamic programming)  _([Here](https://www.youtube.com/watch?v=8LusJS5-AGo&t=807s) is a great youtube video simulation of this algorithm.)_
* __Solver_4__: using deep search to the binary tree which construct by whether select the current item or not
* __Solver_5__: using a classic evolution algorithm: hill-climbing; which will flip the coin(whether select an item or not) with a possibility of 1/items based on current best solution. 


## Conclusion:

The hill-climbing algorithm seems working well compared to DP on small problems after repeating it for 10,000 times. But on larger problems, even increase the repeatition for 100-fold, there doesn't seem to be any great performance increasing.
