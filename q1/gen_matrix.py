import argparse
import random

file1="A"
file2="B"
file3="C"
maxROWA=100
maxCOLA=100
maxROWB=100
maxCOLB=100
#maxROWA=5
#maxCOLA=5
#maxROWB=5
#maxCOLB=5

noFloat=0

def main():
	A = [[0 for col in range(maxCOLA)] for row in range(maxROWA)]
	B = [[0 for col in range(maxCOLB)] for row in range(maxROWB)]
	C = [[0 for col in range(maxCOLB)] for row in range(maxROWA)]
	f1 = open(file1, 'w')
	f1.write(str(maxROWA)+" "+str(maxCOLA))
	f1.write("\n")

	for r in range(0,maxROWA):
		for c in range(0,maxCOLA):
			if noFloat > 0:
				val = random.randint(0,10)
			else:
				val = random.uniform(0.0,1000000.0)
			A[r][c] = val
			f1.write(str(val)+" ");
		
		f1.write("\n");
	
	f1.close();

	f2 = open(file2, 'w')
	f2.write(str(maxROWA)+" "+str(maxCOLA))
	f2.write("\n")

	for r in range(0,maxROWA):
		for c in range(0,maxCOLA):
			if noFloat > 0:
				val = random.randint(0,10)
			else:
				val = random.uniform(0.0,1000000.0)
			B[r][c] = val
			f2.write(str(val)+" ");
		
		f2.write("\n");
	
	f2.close();

	C = [[0 for col in range(maxCOLB)] for row in range(maxROWA)]
	for i in range(0,maxROWA):
		for j in range(0,maxCOLB):
				for c in range(0,maxCOLA): 
					C[i][j] += A[i][c]*B[c][j];

	f3 = open(file3, 'w')
	f3.write(str(maxROWA)+" "+str(maxCOLB))
	f3.write("\n")

	for i in range(0,maxCOLB):
		for j in range(0,maxCOLA): 
			f3.write(str(C[i][j] )+" ");
		
		f3.write("\n");
	
	f3.close();

if __name__ == "__main__":
    main()

