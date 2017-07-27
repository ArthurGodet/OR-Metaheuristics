# Operational Research Project

Initially, this was a school project where we were asked to implement the Memetic metaheuristic for the permutation flowshop problem (referred as the Flowshop problem here).
However, we are students interested in Optimisation as we are studying Computer Science for Decision Support.
Our curiosity led us to implement several other metaheuristics in order to compare their results.
We also created our own metaheuristic, the Wolf Pack Algorithm, which is based on the behaviour of a wolf pack.
Finally, we updated our system so that it can also solve instances of the famous Travelling Salesman Problem (TSP).

## Getting Started

Concerning the code in itself. 
* The crossovers package contains different ways crossover can be made on our representation of DNA (here, an array of integers representing the order in which the tasks are done for the Flowshop problem or the order in which the cities are visited for the TSP).
* The definition package contains several classes used to build the instances, the solution, etc.
* The metaheuristics package contains the implementation of all the metaheuristics we worked on.
* The neighborhoods package contains different ways to compute the neighborhood of a solution in the search space.
* The util package contains generic methods we implemented to manage time and random numbers generation.

For the rest of the project.
* The folder doc contains the Javadoc associated to the code.
* The folder instances contains the data for instances of the Flowshop problem and for the TSP.
* The folder results contains all the results obtained so far by the different algorithms for the different instances of the two problems.
* The folder summary contains the results obtained so far in a summarized shape.
* The file summary.ods allows to see the results more easier and allows to compare the different algorithms with graphics.
* The script summarize.py treats the data in the results folder in order to create the text files in the summary folder.

## Running the code

Before running the code, you must create a folder in the results folder with the name of your computer's session. It is the only thing you'll have to configure in order to make our program work as it should.

The Main class will then produce the following results : every metaheuristic will be called twice (the two process occurring in two different threads in the same time) on every one of the ten first instances of the Flowshop problem. The results obtained by the different algorithms are written in files with the name of the algorithm in the folder you created. 

If you then execute the Python script named summarize, it will update the data in the text files in the folder summary. If you then open the file summary.ods and update the links, you will have all the results got so far on the instances, with several graphics to compare the algorthms.

## Authors

* **Arthur Godet** - *Code (implementation of algorithms and factorization) | Design of the Wolf Pack metaheuristic* - [ArthurGodet](https://github.com/ArthurGodet)
* **Joachim Hotonnier** - *Code (structure, factorization and utils)* - [jhtr](https://github.com/jhtr)
* **Marie Deur** - *Code*

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the BSD License - see the [LICENSE.md](LICENSE.md) file for details.
    