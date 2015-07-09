g++ -fopenmp -g -c monte_carlo_pi.cpp -Wall -o monte_carlo_pi.o
g++ -fopenmp -g -c main.cpp -Wall -o main.o
g++ -fopenmp -g main.o monte_carlo_pi.o -o monte_carlo_pi
