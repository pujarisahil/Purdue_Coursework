#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include <time.h>

#define MAX_ROWS 5000
#define MAX_COLUMNS 5000

struct array_info
{
	int	rows;
	int	columns;
	int order;
		char *base_pointer; /* pointer to array of bytes. In this array, float numbers will be stored (4 bytes each) */
};

struct array_info* init(int, int, int);
void store(struct array_info*, int, int, float);
float fetch(struct array_info*, int, int);
double calc_time_to_read(struct array_info*);

int main()
{
	int n_rows, n_columns, order, row_index, column_index;
	float out_value, in_value;
	int in;
	struct array_info *matrix;
	printf("Enter the total number of rows: \t");
	scanf("%d", &n_rows);
	printf("Enter the total number of columns: \t");
	scanf("%d", &n_columns);
	printf("Select the order (0-Row major order, 1-Column major order): \t");
	scanf("%d", &order);
	matrix = init(n_rows, n_columns, order);
	if(matrix == NULL)
	{
		printf("\n Error Creating the Array\n");
		return 0;
	}
	printf("\n 2-D Array has been initialized\n");
	while(1)
	{
		printf("\n 1 - Store a value \n 2 - Fetch a value \n 3 - Calculate time to read the entire array  \n 4 - Exit\n Enter your choice:\t");
		scanf("%d", &in);
		switch(in)
		{
			case 1:
			printf("Enter row number, column number and a value with spaces between them:\t");
			scanf("%d %d %f", &row_index, &column_index, &in_value);
			store(matrix, row_index, column_index, in_value);
			printf("Value stored successfully\n");
			break;
			case 2:
			printf("Enter row number and column number with spaces between them:\t");
			scanf("%d %d", &row_index, &column_index);
			out_value=fetch(matrix, row_index, column_index);
			printf("Fetched value at (%d, %d)= %f\n", row_index, column_index, out_value);
			break;
			case 3:
			printf("Time to read the entire array is %f secs\n", calc_time_to_read(matrix));
			break;
			case 4:
			default:
			return 0;
		}
	}
}

#define ROW_MAJOR 0
#define COLUMN_MAJOR 1

struct array_info *init(int rows, int columns, int order) {
	if (rows > MAX_ROWS + 1 || columns > MAX_COLUMNS + 1) {
		return NULL;
	}
	struct array_info *array = malloc(sizeof(struct array_info));
	array->rows = rows;
	array->columns = columns;
	array->order = order;
	array->base_pointer = calloc(rows*columns, sizeof(float));
	return array;
}

void store(struct array_info *array, int row_index, int column_index, float value) {
	float *p = (float *) array->base_pointer;
	if (array->order == ROW_MAJOR) {
		p[row_index*array->rows + column_index] = (float)value;
	}
	else {
		p[row_index + column_index*array->columns] = (float)value;
	}
}

float fetch(struct array_info *array, int row_index, int column_index) { 
	float *p = (float *)array->base_pointer;
	if (array->order == ROW_MAJOR) {
		return p[row_index*array->rows + column_index];
	}

	else {
		return p[row_index + column_index*array->columns];
	}
}

double calc_time_to_read(struct array_info* array) {
	int i, j;
	float value;
	clock_t begin, end;
	begin = clock();
	for(i = 0; i < array->rows; i++) {
		for(j = 0; j < array->columns; j++) {
			fetch(array, i, j);
		}
	}
	end = clock();
	return (double)(end - begin)/CLOCKS_PER_SEC;
}