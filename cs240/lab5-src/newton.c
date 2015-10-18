#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define TOLERANCE 1E-6
#define MAX_ITER 12

double f(const char* funname, double x);
double fPrime(const char* funname, double x);
void printFunction();

void wrong() { //Method to invoke if a false entry is detected anywhere in the code
	exit(1);
}

int main(int argc, char** argv)
{
	if (!(argc == 3)) {
		printf("Usage: newton <poly1|sin|xsin|poly2|imaginary> <initial guess>\n");		
		wrong();
	}

	int valid = 0; //Check if the argument is valid  or not
	
	if (strcmp(argv[1], "poly1") == 0) //Compare the argument
		valid = 1;
	else if (strcmp(argv[1], "sin") == 0) //Compare the argument
		valid = 1;
	else if (strcmp(argv[1], "xsin") == 0) //Compare the argument
		valid = 1;
	else if (strcmp(argv[1], "poly2") == 0) //Compare the argument
		valid = 1;
	else if (strcmp(argv[1], "imaginary") == 0) //Compare the argument
		valid = 1;
	else
		valid = 0; //Sets the check variable to 0 if it's not equal to any of the strings mentioned above

	if (valid == 0) {
		printf("Error: %s is not a valid function name\n", argv[1]); //Prints out this error if the entered argument is invalid
		exit(1);	
	}

	printFunction(argv[1]);
	double start = atof(argv[2]);
	int i = 0;
	double y = f(argv[1], start);
	double yPrime = fPrime(argv[1], start);
	double yIteration12 = y;
	double startIteration12 = start;
	
	//Following snippet based on the algorithm provided on the handout which goes like - 

	/*x = starting point
	y = f(x)
	dy = f'(x)
	iterations = 0;
	while (abs(y) > TOLERANCE and iterations < MAX_ITERATIONS) 
  		x = x - f(x)/f'(x)
  		y = f(x)
  		iterations++
	}*/
	while (fabs(y) > TOLERANCE && i <= MAX_ITER) {
		printf("At iteration %d, x=%f, y=%f, y'=%f\n", i, start, y, yPrime);
		if (yPrime == 0.000000) {
			printf("Error: at x=%f, f'(x)=%d\n", start, (int) yPrime);
			wrong();
		}
		startIteration12 = start;
		start = start - y/yPrime;
		yIteration12 = y;
		y = f(argv[1], start);
		yPrime = fPrime(argv[1], start);
		i++;
	}
	//END OF SNIPPET

	if (!(y < 0.000001 && y > -0.000001))
		printf("Error: after 12 iterations, x=%f and f(x)=%f\n", startIteration12, yIteration12); 
	else {
		printf("At iteration %d, x=%f, y=%f, and y'=%f\n", i, start, y, yPrime);
		printf("Solution: iteration=%d x=%f y=%f\n", i, start, y); 	
	}

	return 0;
}

/* Prints the function in readable form */
void printFunction(const char * funname)
{
        char * func;

	if(strcmp(funname, "poly1") == 0)
	  func = "y = x^2 - 4 = 0";
	else if(strcmp(funname, "sin") == 0)
	  func = "y = sin(x)-.5 = 0";
	else if(strcmp(funname, "xsin") == 0)
	  func = "y = x*sin(x)-10 = 0";
	else if(strcmp(funname, "poly2") == 0)
	  func = "y = x^3+3*x^2+4*x-1 = 0";
	else if(strcmp(funname, "imaginary") == 0)
	  func = "y = x^2+1 = 0";
	else
	{
	  printf("Error: %s is not a valid function name\n", funname);
		wrong();
	}

        printf("Function: %s\n", func);
}

/* Evaluates f(x) */
double f(const char* funname, double x)
{
	if(strcmp(funname, "poly1") == 0)
		return (x*x - 4);
	else if(strcmp(funname, "sin") == 0)
	  return (sin(x)-.5);
	else if(strcmp(funname, "xsin") == 0)
	  return (x*sin(x)-10);
	else if(strcmp(funname, "poly2") == 0)
	  return (x*x*x+3*x*x+4*x-1);
	else if(strcmp(funname, "imaginary") == 0)
	  return (x*x+1);
	else
	{
	  printf("Error: %s is not a valid function name\n", funname);
		wrong();
	}
}

/* Evaluates f'(x) */
double fPrime(const char* funname, double x)
{
	if(strcmp(funname, "poly1") == 0)
		return (2*x);
	else if(strcmp(funname, "sin") == 0)
	  return (cos(x));
	else if(strcmp(funname, "xsin") == 0)
	  return (sin(x)+x*cos(x));
	else if(strcmp(funname, "poly2") == 0)
	  return (3*x*x+6*x+4);
	else if(strcmp(funname, "imaginary") == 0)
	  return (3*x*x+6*x+4);
	else
	{
	  printf("Error: %s is not a valid function name\n", funname);
		wrong();
	}
}
