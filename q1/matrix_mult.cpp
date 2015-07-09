#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstring>
#include <cstdlib>
#include "omp.h"

using namespace std;

void printMatrix(double *M, int rows, int cols){
	cout << rows << " " << cols << endl;
	for(int r=0; r<rows; r++){
		for(int c=0; c<cols; c++){
			cout << M[r*cols + c] << " ";
		}
		cout << endl;
	}

}

bool MatrixMult(int rowA, int colA, double* A, int rowB, int colB, double* B,
  double* C, int T) {
  // TODO: Implement your parallel multiplication for two matrices of doubles
  // by using OpenMP
  // parameter rowA: indicates the number of rows of the matrix A
  // parameter colA: indicates the number of columns of the matrix A
  // parameter A: indicates the matrix A
  // parameter rowB: indicates the number of rows of the matrix B
  // parameter colB: indicates the number of columns of the matrix B
  // parameter B: indicates the matrix B
  // parameter C: indicates the matrix C, which is the results of A x B
  // parameter T: indicates the number of threads
  // return true if A and B can be multiplied; otherwise, return false 
	//cout << "Starting MatrixMult: ROWA=" << rowA << " COLA=" << colA 
	//    << " ROWB=" << rowB << "COLB=" << colB << " T=" << T << endl;
	if(rowB != colA){
		return false;
	}

	int i,j, c;
	omp_set_num_threads(T);
	#pragma omp parallel shared(A,B,C) private(i,j,c)
	{
		/*#pragma omp master 
		{
			cout << "OPENMP: numThreads " << omp_get_num_threads() << endl;	
		}*/

		#pragma omp for schedule(dynamic)
		for(i=0; i<rowA; i++){
			for(j=0; j<colB; j++){
				C[i*colB+j] = 0;
				for(c=0; c<colA; c++){
					C[i*colB+j] += A[i*colA+c]*B[c*colB+j];
				}
			}
		}	
	}
	return true;
}

int main(int argc, const char *argv[]) {
  int ROWA;
  int COLA;
  int ROWB;
  int COLB;
  int T;

  double* A;
  double* B;
  double* C;

  // TODO: Initialize the necessary data
	if(argc != 4){
		cout << "Invalid args: ./matrix_mult [file1] [file2] [num threads]" << endl;
		return -1;
	}
	//cout << "File1: " << argv[1] << " File2: " << argv[2] << " Number of threads: " 
	//    << argv[3] << endl;

	string tstr(argv[3]);
	stringstream ss(tstr); 
	ss >> T;
		
	//Open and read files
	fstream file1, file2;
	
	file1.open(argv[1]);	
	file1 >> ROWA;
	file1 >> COLA;
	A = new double[ROWA*COLA];
	for(int r=0; r<ROWA; r++){
		for(int c=0; c<COLA; c++){
			file1 >> A[r*COLA + c];
		}
	}
	file1.close();
	//printMatrix(A,ROWA,COLA);
	
	file2.open(argv[2]);
	file2 >> ROWB;
	file2 >> COLB;
	B = new double[ROWB*COLB];
	for(int r=0; r<ROWB; r++){
		for(int c=0; c<COLB; c++){
			file2 >> B[r*COLB + c];
		}
	}
	file2.close();
	//printMatrix(B,ROWB,COLB);

	C = new double[ROWA*COLB];

	double start = omp_get_wtime();
	double end;
	
    if(MatrixMult(ROWA, COLA, A, ROWB, COLB, B, C, T)) {
    // TODO: Output the results
	    end = omp_get_wtime();
	    printMatrix(C,ROWA,COLB);
    } 
    else {
        cout << "the colA != rowB MatrixMult return false" << endl;
        return -1;
    }

	//Only output to standard output.Done above
	/*ofstream file3("C_result");
	file3 << ROWA << " " << COLB << endl;
	cout << ROWA << " " << COLB << endl;
	for(int r=0; r<ROWA; r++){
		for(int c=0; c<COLB; c++){
			file3 << C[r*COLB + c] << " ";
			cout <<  C[r*COLB + c] << " ";		
		}
		file3 << endl;
		cout << endl;
	}*/

	//file3.close();
    
    cout << endl;	
	//cout << "OPENMP: execution time: " << end - start << endl;
	
    return 0;
}





