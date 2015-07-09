#include "omp.h"
#include <iostream>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <sstream>

#define R 1

using namespace std;

extern double MonteCarloPi(int s);

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
