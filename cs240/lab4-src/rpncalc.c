
#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

//Refernce to Piazza @254 for using argv[][]

void wrong() {
	printf("Usage: rpncalc op1 op2 ...\n");	
	exit(1);
}

/*int main(int argc, char ** argv)
{
	double numbers[500];
	char operators[500];
	int i;
	int j = 0;
	int z = 0;
	char *test = NULL;
	long intval;
	for (i=1; i < argc; i++) {
		//char *test = NULL;
		//long intval = strtol(argv[i], &test, 10);
		//sscanf(argv[i], "%lf", &intval);
		//if(test - argv[i] == strlen(test))
			//numbers[j++] = intval;
		//else {
			//if(j > 1) {
				if (!strcmp(argv[i], "+")) {
					test = NULL;
					intval = strtol(argv[i], &test, 10);					
					numbers[j++] = intval;
					numbers[j - 2] = numbers[j - 2] + numbers[j - 1];
					--j;
				} else if (!strcmp(argv[i], "-")) {
					test = NULL;
					intval = strtol(argv[i], &test, 10);					
					numbers[j++] = intval;
					numbers[j - 2] = numbers[j - 2] - numbers[j - 1];
					--j;
				} else if (!strcmp(argv[i], "x")) {
					test = NULL;
					intval = strtol(argv[i], &test, 10);					
					numbers[j++] = intval;
					numbers[j - 2] = numbers[j - 2] * numbers[j - 1];
					--j;
				} else if (!strcmp(argv[i], "/")) {
					test = NULL;
					intval = strtol(argv[i], &test, 10);					
					numbers[j++] = intval;
					numbers[j - 2] = numbers[j - 2] / numbers[j - 1];
					--j;
				} else if (!strcmp(argv[i], "pow")) {
					test = NULL;
					intval = strtol(argv[i], &test, 10);					
					numbers[j++] = intval;
					numbers[j - 2] = pow(numbers[j - 2] , numbers[j - 1]);
					--j;
				} else {
					wrong();
				}
			//} 


			/*else if (j > 0) {

				if (!strcmp(argv[i], "log")) {
					numbers[j - 1] = log10(numbers[j - 1]);
				} else if (!strcmp(argv[i], "sin")) {
					numbers[j - 1] = sin(numbers[j - 1]);
				} else if (!strcmp(argv[i], "cos")) {
					numbers[j - 1] = cos(numbers[j - 1]);
				} else if (!strcmp(argv[i], "exp")) {
					numbers[j - 1] = exp(numbers[j - 1]);
				} else {
					wrong();
				}
			} else {
				wrong();
			}
		}
	}
	printf("%lf\n", numbers[0]);
}*/

	int main(int argc, char ** argv)
	{
	float stack[argc*argc];		// stack would store the numbers entered by the user via command line
	int i = 0;			// used to increment the arguments
	int head = 0; 			// used to increment through the stack (reference to lab 2)
	float num1;			
	float num2;			
	int usageCheck = 0;             // checks if arguments were entered. Would be 0 if nothing was entered

	for (i = 1; i < argc ; i++){				// reads through the command line arguments
		if (argv[i][0] == '+'){				// if it's a plus, it reads the last two
			num1 = (stack[head-2]);			// numbers in the stack, adds them, and places
			num2 = (stack[head-1]);			// back in the stack.
			stack[head-2] = (num1 + num2);
			head--;
			usageCheck = 1;
		}
		else if (argv[i][0] == '-'){			// if it's a negative sign, it checks the next
			if (argv[i][1] == '\0'){		// character to see if it's a \0 to signify it's 
				num1 = (stack[head-2]);		// a subtraction sign, but if it has a number after 
				num2 = (stack[head-1]);		// it, it's a negative number; if subtraction, does
				stack[head-2] = (num1 - num2);	// subtraction process; if negative, number gets placed
				head--;
				usageCheck = 1; 		// in the stack
			}
			else{
				stack[head] = atof(argv[i]); 
				head++;
				usageCheck = 1;
			}
		}
		else if (argv[i][0] == 'x'){			// multiplies if it's "x"
			num1 = (stack[head-2]);	
		num2 = (stack[head-1]);
		stack[head-2] = (num1 * num2);	
		head--;
		usageCheck = 1;
	}
		else if (argv[i][0] == '/'){			// divides if it's "/"
			num1 = (stack[head-2]);			
		num2 = (stack[head-1]);
		stack[head-2] = (num1 / num2);
		head--;
		usageCheck = 1;
	}
		else if (argv[i][0] == 'c'){			// takes cosine if first letter is "c" (Since we don't have any other type starting with c it works)
			num1 = (stack[head-1]);		
		stack[head-1] = cosf(num1);
		usageCheck = 1;
	}
		else if (argv[i][0] == 's'){			// takes sin if first letter is "s" (Since we don't have any other type starting with s it works)
			num1 = (stack[head-1]);	
		stack[head-1] = sinf(num1);
		usageCheck = 1;
	}
		else if (argv[i][0] == 'p'){			// takes power if first letter is "p" (Since we don't have any other type starting with p it works)
			num1 = (stack[head-2]);			
		num2 = (stack[head-1]);
		stack[head-2] = powf(num1, num2);
		head--;
		usageCheck = 1;
	}
		else if (argv[i][0] == 'e'){			// takes exponent if first letter is "e" (Since we don't have any other type starting with e it works)
			num1 = (stack[head-1]);
		stack[head-1] = expf(num1);
		usageCheck = 1;
	}
		else if (argv[i][0] == 'l'){			// takes log if first letter is "l" (Since we don't have any other type starting with l it works)
			num1 = (stack[head-1]);		
		stack[head-1] = logf(num1);
		usageCheck = 1;
	}
		else{ //If it's a number like a digit then it's pushed into the stack
			stack[head] = atof(argv[i]);		
		head++;					
			usageCheck = 1;				// check is set to 1 to tell the program that atleast one of the argument was passed into the body
		}				
	}			
	
	if (usageCheck == 0)					// if it's 0 it means no digits were entered
		printf("Usage: rpncalc op1 op2 ...\n");
	//FOLLOWING : Printed and done as per the specifications on handout
	else if (head == 1)					
		printf("%f\n", stack[0]);			
	else if (head > 1)					
		printf("Elements remain in the stack\n");	
	else							
		printf("Stack underflow\n");			
	return 0;
}

