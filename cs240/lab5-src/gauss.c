#include <stdio.h>
#include <stdlib.h>

#define MAXROWS 100
#define MAXCOLS 101
#define DEBUG_ON 1
#define EPSILON 1E-6


void printMatrix(int rows, int columns, double **matrix); //Method to print up the matrix
void swap(int rows, double **matrix); //Method to swap the rows

int rowToSwap = 0; //Keeps track of which row to swap
int column_number = 0; //Keeps track of the column number

void wrong() { //Method to invoke if some illegal entries take place
	exit(1);
}

int main(int argc, char  **argv) {
	int rows = 0, cols = 0, i, j, element, columns_left = 0; //Inital variable declarations
	char c = '0'; 
	double **matrix; //The main matrix on which computation has to take place would be stored in this

	if ((c = getchar()) != EOF) { //Read from the input
		rows = (int) c - '0'; //Way to convert a character to integer
		cols = rows + 1; //Increment the number of columuns
	}
	else { //If nothing is provided
		printf("Expected N (number of equations)\n");
		wrong();
	}
    
    //Following snippet allocates memory for the matrix
	if (!(matrix = (double **) malloc (rows*sizeof(double *)))){
		wrong();
	}
	for (i = 0; i < rows+1; i++) {
		if (!(matrix[i] = (double *) malloc (cols*sizeof(double)))) {
			wrong();
		}
	}
	//END OF THE SNIPPET

	//Following snippet adds the values entered into the matrix
	for (i = 0; i < rows; i++) {
		columns_left = cols;
		for (j = 0; j < cols; j++) {
			scanf("%d", &element);
			matrix[i][j] = (double) element;
			columns_left--;
			if ((c = getchar()) == '\n' || c == EOF)
				break;
		}
		if (columns_left > 0) {
			printf("Element a[%d][%d] is missing\n", i, j);
			wrong();
		}
	}
	//END OF THE SNIPPET

	printf("initial matrix\n");
	printMatrix (rows, cols, matrix); //Prints the inital matrix i.e the unmodified version

	double divisor = 0;
	double multiplier = 0;
	int k = 0;
	int m = 0;
	
	//Refering to a youtube video on row reducing a matix https://www.youtube.com/watch?v=9LYVi-n-6Jw . Implementing the same algorithm (the handout on course page doesn't give a 
    //complete insight on how to do Gauss Jordon
	for (i = 0; i < rows; i++) {
		swap (rows, matrix); //Swaping takes place first
		printMatrix (rows, cols, matrix); //Prints the matrix after one iteration of swap and so on
		printf("row %d /= %f\n", i, matrix[i][i]);
		divisor = matrix[i][i]; //Sets the new value for the divisor
		for (j = 0; j < cols; j++) {
			matrix[i][j] = matrix[i][j] / divisor; //Divides each row with the divisor
			if (matrix[i][j] == -0.000000) //Takes care of negative entries
				matrix[i][j] = 0.000000;
		}
		printMatrix (rows, cols, matrix); //Prints the matrix again
		for (k = 0; k < rows; k++) {
			if (k != i) {
				multiplier = matrix[k][column_number]; //Sets up new value for the multiplier
				printf("row %d -= %f row %d\n", k, multiplier, i);
				for (m = 0; m < cols; m++) {
					matrix[k][m] = matrix[k][m] - multiplier*matrix[i][m]; //Uses the multiplier and based on the formula calculates the new value to be fed
				}
				printMatrix (rows, cols, matrix);
			}
		}
		column_number++; //Increments the target column number
	}

	for (i = 0; i < rows; i++) {
		printf("%f ", matrix[i][cols-1]); //Prints the final result matrix
	}
	printf("\n");

	free (matrix); //Free's the memory that was allocated to the matrix
	//(Always good to prevent memory leaks)

	return 0;
}

void printMatrix(int rows, int columns, double **matrix)
{
	int i = 0;
	int j = 0;
	//Following snippet is pretty straight-forward it prints the matrix
	printf("MATRIX:\n");
	for (i = 0; i < rows; i++) {
		for (j = 0; j < columns; j++) {
			printf("%f ", matrix[i][j]);
		}
		printf("\n");
	}
	printf("\n");
}

void swap(int rows, double **matrix) {
	int i = 0;
	int biggest_row = rowToSwap;
	
	//IMPLEMENTATION : The way it mentioned in the handout -
	
	/*for each row r[i] of the matrix (i from 1 to n)
        find the maximum value of abs(r[s][i]) where s >= i
        swap r[i] and r[s]    // this makes the pivot nonzero if possible
        replace r[i] with r[i] / r[i][i]
        for each row r[k] of the matrix (k !=i)
                replace r[k] with r[k] - r[k][i] * r[i]
	*/

	for (i = rowToSwap+1; i < rows; i++) {	
		if (fabs(matrix[biggest_row][column_number]) >= fabs(matrix[i][column_number])) {
		}
		else {
			biggest_row = i;
		}	
	}
	matrix[rows] = matrix[rowToSwap];
	matrix[rowToSwap] = matrix[biggest_row];
	matrix[biggest_row] = matrix[rows];
	
	if (matrix[rowToSwap][column_number] == 0) {
		printf("Error: Matrix is singular\n");
		wrong();
	}

	printf("swapped %d and %d\n", rowToSwap, biggest_row); 
	
	rowToSwap++;
}
