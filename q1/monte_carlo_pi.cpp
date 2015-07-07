#include "omp.h"
#include <iostream>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <sstream>

#define R 1

using namespace std;

double randf(double low, double high){
    return (rand()/(double)(RAND_MAX))*abs(low-high)+low;
}

double MonteCarloPi(int s) {
  // TODO: Implement your Monte Carlo Method
  // parameter s: number of points you randomly choose
  // return the value of pi

	int c = 0;
	double x, y;
	#pragma omp parallel 
	{
		#pragma omp master 
		{
			cout << "OPENMP: numThreads " << omp_get_num_threads() << endl;	
		}

		#pragma omp for private(x,y) reduction(+:c)
		for(int i=0; i<s; i++){
			//Randomly choose point
			x = randf(-R,R);
			y = randf(-R,R); 
		
			if ((x*x + y*y) < R){
				c++;
			}
		}	
	}

	cout << "C: "<< c <<" S: "<< s <<endl;

	double pi = 4*double(c)/double(s);
	return pi;
}


int main(int argc, const char *argv[]) {

	if(argc != 2){
		cout << "Invalid args: ./monte_carlo_pi [s]" << endl;
		return -1;
	} 

	srand (time(NULL));
	int s;
	string tstr(argv[1]);
	stringstream ss(tstr); 
	ss >> s;
	
	double start = omp_get_wtime();
	double pi = MonteCarloPi(s);
	double end = omp_get_wtime();

	cout << "OPENMP: execution time: " << end - start << endl;
	cout << "PI: " << pi << endl;

	return 0;
}
