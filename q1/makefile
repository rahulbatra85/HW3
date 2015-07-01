CC=g++
CFLAGS=-Wall -fopenmp  
LDFLAGS= -fopenmp
SOURCES=matrix_mult.cpp 
OBJECTS=$(SOURCES:.cpp=.o)
EXECUTABLE=matrix_mult

all: $(SOURCES) $(EXECUTABLE)
    
$(EXECUTABLE): $(OBJECTS) 
	$(CC) $(LDFLAGS) $(OBJECTS) -o $@

.cpp.o:
	$(CC) $(CFLAGS) $< -o $@

rm:
	rm -f *.o
