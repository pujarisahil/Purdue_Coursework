#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>

#define MAXBASE 35 //Since that's the maximum base we can have (NOT USED)
#define MAXDIGITS 100 //Since that's the maximum digits we can have in the number passed via arguments (NOT USED)

void wrong() { //Method to invoke if something wrong is commited by the user
	exit(1);
}


//Following is just a normal program to convert any base to base 10
/*int main(int argc, char ** argv)  
{
	long b, n, i=0, ans = 0,*ptr;
	//long i=0,ans=0,*ptr;
	sscanf(argv[0], "%ld", &b);
	//printf("enter base??\n");
	//scanf("%lld",&b);
	sscanf(argv[2], "%ld", &n);
	//printf("Enter no.\n");
	//scanf("%lld",&n);
	ptr=&n;
	while(*ptr>0)
	{
		ans=ans+(*ptr%10)*pow(b,i);
		*ptr=*ptr/10.0;
		i++;
	}
	printf("%ld\n",ans);
	return 0;
}*/

int convertToBase10 (char baseFromArray[], char n[])
{
	int numberLength = strlen(n);		// length of number
	int baseFromArray_i = atoi(baseFromArray);		// starting base
	int i = 0;
	int j = 0;
	int numToTen = 0;			// retains the number converted to base 10
	int baseFromArray_exp = 1;			// used for exponents (Math.pow)
	int stack[100];			// retains the numbers in this array
	int test = 0;			// to check if there's nothing invalid (the digits entered)
	//int nAscii;

	for (i = 0; i < numberLength; i++){
		if (n[i] == 'A'){
			if (10 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'B'){
			if (11 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'C'){
			if (12 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'D'){
			if (13 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'E'){
			if (14 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'F'){
			if (15 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'G'){
			if (16 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'H'){
			if (17 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'I'){
			if (18 >= baseFromArray_i)	
				return -1;
		}
		else if (n[i] == 'J'){
			if (19 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'K'){
			if (20 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'L'){
			if (21 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'M'){
			if (22 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'N'){
			if (23 >= baseFromArray_i)
				return -1;
		}
		else if (n[i] == 'O'){
			if (24 >= baseFromArray_i)
				return -1;
		}
		else {
			test = (int) (n[i] - '0');
			if (test >= baseFromArray_i)
				return -1;
		}			
	}

	for (i = 0; i < numberLength; i++){
		if (n[i] >= '0' && n[i] <= '9'){
			stack[i] = (int) (n[i] - '0');
		}
		
		/*nAscii = atoi(n[i]); //FAIL ATTEMPT TO USE A SWITCH
		switch(nAscii)
     	{
        	case 10 : stack[i] = 10;
        	case 11 : stack[i] = 11;
        	case 12 : stack[i] = 12;
        	case 13 : stack[i] = 13;
        	case 14 : stack[i] = 14;
 			case 15 : stack[i] = 15;
 			case 16 : stack[i] = 16;
 			case 17 : stack[i] = 17;
 			case 18 : stack[i] = 18;
 			case 19 : stack[i] = 19;
 			case 20 : stack[i] = 20;
 			case 21 : stack[i] = 21;
 			case 22 : stack[i] = 22;
 			case 23 : stack[i] = 23;
 			case 24 : stack[i] = 24;   
     	}	*/
		else if (n[i] == 'A')
			stack[i] = 10;
		else if (n[i] == 'B')
			stack[i] = 11;
		else if (n[i] == 'C')
			stack[i] = 12;
		else if (n[i] == 'D')
			stack[i] = 13;
		else if (n[i] == 'E')
			stack[i] = 14;
		else if (n[i] == 'F')
			stack[i] = 15;
		else if (n[i] == 'G')
			stack[i] = 16;
		else if (n[i] == 'H')
			stack[i] = 17;
		else if (n[i] == 'I')
			stack[i] = 18;
		else if (n[i] == 'J')
			stack[i] = 19;
		else if (n[i] == 'K')
			stack[i] = 20;
		else if (n[i] == 'L')
			stack[i] = 21;
		else if (n[i] == 'M')
			stack[i] = 22;
		else if (n[i] == 'N')
			stack[i] = 23;
		else if (n[i] == 'O')
			stack[i] = 24;
	}


	// calculation to base 10
	for (i = 0; i < numberLength; i++){
		for (j = 0; j < (numberLength - i); j++){
			if (j == 0)	
				baseFromArray_exp = baseFromArray_exp;
			else
				baseFromArray_exp *= baseFromArray_i;
		}
		numToTen += (stack[i]*baseFromArray_exp);
		baseFromArray_exp = 1;
	}

	// returns base 10 of number
	return numToTen;
}

int convertFromBase10 (int bT, int numToTen, char converted[])
{
	int quotient = numToTen;			// would store the quotient
	int remainder = 1;			// would store "a" remainder
	int remainders[100];			// would store all remainders
	int *rptr;				// pointer to remainders
	int i = 0;				
	int r_len = 0;				// stores the length of remainders
	char insertion;				// remainder in char type to store in converted array
	int insertion_i;			// remainder in the int type

	while (quotient != 0) {
		remainder = quotient % bT;
		quotient = quotient / bT;
		remainders[i] = remainder;
		i++;
		r_len++;
	}

	rptr = &remainders[r_len-1];

	// would flip the array
	for (i = 0; i < r_len; i++) {
		insertion_i = *rptr;
		if (insertion_i == 10)
			insertion = 'A';
		else if (insertion_i == 11)
			insertion = 'B';
		else if (insertion_i == 12)
			insertion = 'C';
		else if (insertion_i == 13)
			insertion = 'D';
		else if (insertion_i == 14)
			insertion = 'E';
		else if (insertion_i == 15)
			insertion = 'F';
		else if (insertion_i == 16)
			insertion = 'G';
		else if (insertion_i == 17)
			insertion = 'H';
		else if (insertion_i == 18)
			insertion = 'I';
		else if (insertion_i == 19)
			insertion = 'J';
		else if (insertion_i == 20)
			insertion = 'K';
		else if (insertion_i == 21)
			insertion = 'L';
		else if (insertion_i == 22)
			insertion = 'M';
		else if (insertion_i == 23)
			insertion = 'N';
		else if (insertion_i == 24)
			insertion = 'O';
		else
			insertion = *rptr + '0';
		converted[i] = insertion;
		rptr--;	
	}
	
	return r_len;
}

int main(int argc, char **argv)
{
	int baseFrom, baseTo, number;					// Same as the lab description
	char baseFromS[32], baseToS[32], numberS[32], converted[100];   // arrays to hold above
	int base10;							// holds the base10 of the number
	int i = 0;							// counter

	// If the user enters the correct arguments, it'd take it further
	if (argc == 4){
		baseFrom = atoi(argv[1]);
		strcpy(baseFromS, argv[1]); //converting a string to char array
		baseTo = atoi(argv[2]);
		strcpy(baseToS, argv[2]); //converting a string to char array
		number = atoi(argv[3]);
		strcpy(numberS, argv[3]); //converting a string to char array
	}
	else {	//Invoked if improper arguments were passed
		printf("Usage:  convert <basefrom> <baseto> <number>\n");	
		wrong();
	}
	
	base10 = convertToBase10 (baseFromS, numberS); 			// Converts the number given from it's base to base 10 (decimal numbers)

	if (base10 == -1){
		printf("Number read in base %d: %s\n", baseFrom, argv[3]);
		printf("Wrong digit in number.\n");
	}
	else if (base10 != NULL){
		printf("Number read in base %d: %s\n", baseFrom, argv[3]);
		printf("Converted to base 10: %d\n", base10);
		printf("Converted to base %d: ", baseTo); 
		int r_len = convertFromBase10 (baseTo, base10, converted); //converts number from base 10 to the required base and stores it in variable r_len
		for (i = 0; i < r_len; i++){
			printf("%c", converted[i]); //prints the result obtained
		}
		printf("\n");
	}

	wrong();
}

