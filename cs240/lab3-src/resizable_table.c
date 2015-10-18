
#include <stdlib.h>
#include <assert.h>
#include <stdio.h>
#include <string.h>

#include "resizable_table.h"

//
// It returns a new RESIZABLE_TABLE. It allocates it dynamically,
// and initializaes the values. The initial maximum size of the array is 10.
//
RESIZABLE_TABLE * rtable_create() {

	// Allocate a RESIZABLE_TABLE
	RESIZABLE_TABLE * table = (RESIZABLE_TABLE *) malloc(sizeof(RESIZABLE_TABLE));
	if (table == NULL) {
		return NULL;
	}
	
	// Initialze the members of RESIZABLE_TABLE
	table->maxElements = INITIAL_SIZE_RESIZABLE_TABLE;
	table->currentElements = 0;
	table->array = (struct RESIZABLE_TABLE_ENTRY *)
	malloc(table->maxElements*sizeof(RESIZABLE_TABLE_ENTRY));
	if (table->array==NULL) {
		return NULL;
	}
	
	return table;
}

//
// It prints the elements in the array assuming the value is a string in the form:
//
//======== Table =======
//currentElements=2 maxElements=10
//0: "George"       "23 Oak St, West Lafayette, 47906"
//1: "Peter"        "27 Oak St, West Lafayette, 47906"
//======== End Table =======
//
void rtable_print_str(RESIZABLE_TABLE * table)
{
	int i = 0;
	printf("\n======== Table =======\n");
	printf("currentElements=%d maxElements=%d\n", table->currentElements, table->maxElements);
	for (i=0; i<table->currentElements; i++) {
		printf("%d: \"%s\" \"%s\"\n", i, table->array[i].name, (char*) table->array[i].value);
	}
	printf("======== End Table =======\n");
	return;
}

//
// It prints the elements in the array assuming the value is an int:
//
//======== Table =======
//currentElements=2 maxElements=10
//0: "George"               101
//1: "Peter"                245
//======== End Table =======
//
void rtable_print_int(RESIZABLE_TABLE * table)
{
	int i = 0;
	printf("\n======== Table =======\n");
	printf("currentElements=%d maxElements=%d\n", table->currentElements, table->maxElements);
	for (i=0; i<table->currentElements; i++) {
		printf("%d: \"%s\" %ld\n", i, table->array[i].name, (long) table->array[i].value);
	}
	printf("======== End Table =======\n");
	return;
}

//
// Adds one pair name/value to the table. If the name already exists it will
// Substitute its value. Otherwise, it will store name/value in a new entry.
// If the new entry does not fit, it will double the size of the array.
// The name string is duplicated with strdup() before assigning it to the
// table.
//
int rtable_add(RESIZABLE_TABLE * table, char * name, void * value) {
	/*int i = 0;
	int j = 0;
	char *foo = malloc(sizeof(char) * 1024);
	foo = name;
	
	for(i = 0; i < table->currentElements; i++) {
        if(strcmp(table->array[i].name, strdup(foo)) == 0) {
            table->array[i].value = value;
			return;
        }
    }
	for(j = 0; j < table->currentElements; j++) {
        if((table->currentElements) > (table->maxElements)) {
            table->maxElements = table->maxElements * 2;
        }
    }
	
	table->array[table->currentElements].name = strdup(foo);
	table->array[table->currentElements].value = value;
	table->currentElements++;*/

	int i = 0;
	for (i = 0; i < table->currentElements; i++) {
		if (strcmp(table->array[i].name, name) == 0) {
			table->array[i].value = (long) value;
			return 0;
		}
	}

	if (table->currentElements == table->maxElements) {
		table->maxElements = 2*(table->maxElements);
		RESIZABLE_TABLE_ENTRY * array_temp = (RESIZABLE_TABLE_ENTRY *) realloc (table->array, table->maxElements*sizeof(RESIZABLE_TABLE_ENTRY));
		table->array = array_temp;
	}

	table->array[table->currentElements].name = strdup(name);
	table->array[table->currentElements].value = (long)value;
	table->currentElements++;

	return 0;
}

//
// Add name and value into table where value is a string (char *)
// Implement on top of rtable_add.
//
int rtable_add_str(RESIZABLE_TABLE * table, char * name, char * str_value)
{	
	return rtable_add(table, name, (void *) strdup(str_value));
}

//
// Add name and value into table where value is a long
// Implement on top of rtable_add.
//
int rtable_add_int(RESIZABLE_TABLE * table, char * name, long int_value)
{
	return rtable_add(table, name, (void *) int_value );
}

//
// Returns the value that correspond to the name or NULL if the
// name does not exist in the table.
//
void * rtable_lookup(RESIZABLE_TABLE * table, char * name) {
	int i = 0;
	for(i = 0; i < table->currentElements;i++) {
		if(strcmp(table->array[i].name, name) == 0) {
			return table->array[i].value;
		}
	}
	return NULL;
}

//
// It removes the entry with that name from the table. The entries after the entry
// removed will shift downwards. Also the name and value strings will be freed.
//
int rtable_remove(RESIZABLE_TABLE * table, char * name) {
	/*int i = 0;
	int j = 0;
	int position = 0;
	for(i = 0; i < table->currentElements;i++) {
        if(strcmp(table->array[i].name, strdup(name)) == 0) {
            position = i;
			//free(table->array[i].name);
			//free(table->array[i].value);
        	for(j = position; j < table->currentElements - 1;j++) {
            	table->array[j].name = strdup(table->array[j+1].name);
				table->array[j].value = table->array[j+1].value;
   			}
		table->currentElements--;
		break;
		}
    }*/

		int i = 0;
		int j = 0;
		for (i = 0; i < table->currentElements; i++) {
			if (strcmp(table->array[i].name, name) == 0) {
				for (j = i; j < table->currentElements; j++) {
					if (j < table->currentElements-1) {
						strcpy(table->array[j].name,table->array[j+1].name);
						strcpy(table->array[j].value,table->array[j+1].value);
					}
					else {
						memset(table->array[j].name,0,10);
						memset(table->array[j].value,0,10);
					}
				}
			//free(table->array[i].name);
			//free(table->array[i].value);
				table->currentElements--;
				return 1;
			}
		}
		return 0;
	}

//
// It returns in *name and *value the name and value that correspond to
// the ith entry. It will return 1 if successful, or 0 otherwise.
//
	int rtable_get_ith(RESIZABLE_TABLE * table, int ith, char ** name, void ** value) {
    /*if((strcmp(table->array[ith].name, &name) == 0) && (strcmp(table->array[ith].value, &value) == 0)) {
            return 1;
        }
    return 0;*/

        if (ith < (table->currentElements)) {
        	*name = table->array[ith].name;
        	*value = table->array[ith].value;
        	return 1;
        }
        else
        	return 0;
    }

//
// It removes the ith entry from the table. The entries after the entry removed are
// moved downwards to use the empty space. Also the name/value strings are freed.
//
    int rtable_remove_ith(RESIZABLE_TABLE * table, int ith) {
	/*free(table->array[ith].name);
	free(table->array[ith].value);
	int i = 0;
	int j = 0;
    for(j = ith; j < table->currentElements;j++) {
        table->array[j].name = table->array[j+1].name;
		table->array[j].value = table->array[j+1].value;
   	}
	table->currentElements--;
	return 0;*/
	int j = 0;
	if (ith < table->currentElements) {
		for (j = ith; j < table->currentElements; j++) {
			if (j < table->currentElements-1) {
				strcpy(table->array[j].name, table->array[j+1].name);
				strcpy(table->array[j].value, table->array[j+1].value);
			}
			else {
				memset(table->array[j].name,0,10);
				memset(table->array[j].value,0,10);
			}
		}
		
		//free(table->array[ith].name);	
		//free(table->array[ith].value);
		table->currentElements--;
		return 1;
	}

	return 0;
}

//
// It returns the number of elements in the table.
//
int rtable_number_elements(RESIZABLE_TABLE * table) {
	return table->currentElements;
}

//
// It returns the maximum number of elements in the table
//
int rtable_max_elements(RESIZABLE_TABLE * table) {
	return table->maxElements;
}

#define MAXLINE 512

//
// It saves the table in a file called file_name with values as char *. The format of the
// file is as follows:
//
// name1\n
// str1\n
// \n
// name2\n
// str2\n
// ...
//
// Notice that there is an empty line between each name/value pair.
//
int rtable_save_str(RESIZABLE_TABLE * table, char * file_name) {
	/*FILE *ftpr;
	ftpr = fopen(strdup(file_name), "w");
	if(ftpr==NULL) {
		printf("Error!");
		exit(1);
	}
	int i = 0;
	for(i = 0; i < table->currentElements;i++) {
        fprintf(ftpr, "%s\n", strdup(table->array[i].name));
		fprintf(ftpr, "%s\n", strdup(table->array[i].value));
		fprintf(ftpr, "\n");
    }
	fclose(ftpr);
	return 0;*/
	int i = 0;
	FILE *file;
	
	file = fopen(file_name, "wt");
	if (file == NULL ) {
		return 0;
	}
	else {
		for (i = 0; i < table->currentElements; i++) {
			fprintf(file, "%s\n%s\n\n", table->array[i].name, table->array[i].value);
		}
	}
	fclose(file);
	return 1;
}

//
// It reads the table from the file_name indicated asumiing that the values are
// char *. If the table already has entries, it will clear the entries.
//
int rtable_read_str(RESIZABLE_TABLE * table, char * file_name) {
	//return 0;
	FILE *file;
	
	int clear = 0;
	for (clear = 0; clear < table->currentElements; clear++) {
		memset(table->array[clear].name, 0, 10);
		memset(table->array[clear].value, 0, 10);
	}

	file = fopen(file_name, "r");
	if (file == NULL) {
		return 0;
	}
	else {
		char c;
		int i = 0;
		int j = 0;
		int numLines = 1;
		char name[100];
		char value[100];
		int decider = 1;

		while ((c=fgetc(file)) != EOF) {
			if (c != '\n') {
				if (decider) {
					if(c!=32) {
					name[i] = c;
					i++;
				}
				}
				else {
					value[j] = c;
					j++;
				}
			}
			else {
				numLines++;
				if ( (numLines % 3) == 0) {
					name[i] = '\0';
					value[j] = '\0';
					rtable_add_str(table, name, value);
					i = 0;	
					j = 0;
					numLines = 0;
					memset(name, 0, 100);
					memset(value, 0, 100);
					decider = 1;
					continue;
				}
				else if ( (numLines % 2) == 0) {
					decider = 0;
					continue;
				}
				else {
				}	
			}


		}
	}	

	fclose(file);
	return 1;


}

//
// It saves the table in a file called file_name with values as int. The format of the
// file is as follows:
//
// name1\n
// int1\n
// \n
// name2\n
// int2\n
// ...
//
// Notice that there is an empty line between each name/value pair.
//
int rtable_save_int(RESIZABLE_TABLE * table, char * file_name) {
	int i = 0;
	FILE *file;
	file = fopen(file_name, "wt");
	if (file == NULL ) {
		return 0;
	}
	else {
		for (i = 0; i < table->currentElements; i++) {
			fprintf(file, "%s\n%s\n\n", table->array[i].name, table->array[i].value);
		}
	}
	fclose(file);
	return 1;
}

//
// It reads the table from the file_name indicated asumiing that the values are
// char *. If the table already has entries, it will clear the entries.
//
int rtable_read_int(RESIZABLE_TABLE * table, char * file_name) {
	FILE *file;
	int clear = 0;
	for (clear = 0; clear < table->currentElements; clear++) {
		memset(table->array[clear].name, 0, 10);
		memset(table->array[clear].value, 0, 10);
	}
	file = fopen(file_name, "r");
	if (file == NULL) {
		return 0;
	}
	else {
		char c;
		int i = 0;
		int j = 0;
		int numLines = 1;
		char name[100];
		char value[100];
		int decider = 1;
		while ((c=fgetc(file)) != EOF) {
			if (c != '\n') {
				if (decider == 1) {
					if(c != 32) {
					name[i] = c;
					i++;
				}
				}
				else {
					value[j] = c;
					j++;
				}
			}
			else {
				numLines++;
				if ( (numLines % 3) == 0) {
					name[i] = '\0';
					value[j] = '\0';
					rtable_add_int(table, name, atof(value));
					i = 0;	
					j = 0;
					numLines = 0;
					memset(name, 0, 100);
					memset(value, 0, 100);
					decider = 1;
					continue;
				}
				else if ( (numLines % 2) == 0) {
					decider = 0;
					continue;
				}
				else {
				}	
			}
		}
	}	
	fclose(file);
	return 1;
}

//
// It sorts the table according to the name. The parameter ascending determines if the
// order si ascending (1) or descending(0).
//
void rtable_sort(RESIZABLE_TABLE * table, int ascending)
{	
	int i = 0;
	int j = 0;
	char *temp;
	if (ascending == 1) {
		for (i = 0; i < table->currentElements-1; i++) {
			for (j = 0; j < table->currentElements-1; j++) {
				if ((strcmp(table->array[j].name, table->array[j+1].name)) > 0) {
					temp = strdup(table->array[j+1].name);
					table->array[j+1].name = strdup(table->array[j].name);
					table->array[j].name = strdup(temp);

					temp = strdup(table->array[j+1].value);
					table->array[j+1].value = strdup(table->array[j].value);
					table->array[j].value = strdup(temp);	
				}			
			}
		}
	}
	else {
		for (i = 0; i < table->currentElements-1; i++) {
			for (j = 0; j < table->currentElements-1; j++) {
				if ((strcmp(table->array[j].name, table->array[j+1].name)) < 0) {
					temp = strdup(table->array[j+1].name);
					table->array[j+1].name = strdup(table->array[j].name);
					table->array[j].name = strdup(temp);

					temp = strdup(table->array[j+1].value);
					table->array[j+1].value = strdup(table->array[j].value);
					table->array[j].value = strdup(temp);
				}
			}
		}
	}
}

//
// It sorts the table according to the name. The parameter ascending determines if the
// order si ascending (1) or descending(0).
//
void rtable_sort_by_intval(RESIZABLE_TABLE * table, int ascending)
{
}

//
// It removes the first entry in the table.
// All entries are moved down one position.
// It also frees memory allocated for name and value.
//
int rtable_remove_first(RESIZABLE_TABLE * table) {
	int j = 0;
	if (table->array[0].name != NULL) {
		for (j = 0; j < table->currentElements; j++) {
			if (j < table->currentElements-1) {
				strcpy(table->array[j].name, table->array[j+1].name);
				strcpy(table->array[j].value, table->array[j+1].value);
			}
			else {
				memset(table->array[j].name,0,10);
				memset(table->array[j].value,0,10);
			}
		}

		//free(table->array[0].name);
		//free(table->array[0].value);
		table->currentElements--;
		return 1;
	}
	else
		return 0;
}

//
// It removes the last entry in the table.
// It also frees memory allocated for name and value.
//
int rtable_remove_last(RESIZABLE_TABLE * table) {
	//return 0;
	if (table->currentElements > 0) {
		int j = 0;
		memset(table->array[table->currentElements-1].name,0,10);
		memset(table->array[table->currentElements-1].value,0,10);
		free(table->array[table->currentElements-1].value);
		free(table->array[table->currentElements-1].name);
		table->currentElements--;	
		return 1;
	}
	else 
		return 0;
}

//
// Insert a name/value pair at the beginning of the table.
// The entries are moved one position upwards.
// There is no check if the name already exists. The entry is added
// at the beginning of the table.
//
int rtable_insert_first(RESIZABLE_TABLE * table, char *name, void * value) {
	if (table->currentElements == table->maxElements) {
		table->maxElements = 2*(table->maxElements);
		RESIZABLE_TABLE_ENTRY * array_temp = (RESIZABLE_TABLE_ENTRY *) realloc (table->array, table->maxElements*sizeof(RESIZABLE_TABLE_ENTRY));
		table->array = array_temp;
	}
	
	int i = 0;
	for (i = table->currentElements; i > 0; i--) {
		table->array[i].name = strdup(table->array[i-1].name);
		table->array[i].value = strdup(table->array[i-1].value);
	} 	
	strcpy(table->array[0].name, name);
	strcpy(table->array[0].value, value);
	table->currentElements++;
	return 0;
}

//
// Insert a name/value pair at the end of the table.
// The entries are moved one position upwards.
// There is no check if the name already exists. The entry is added
// at the end of the table.
//
int rtable_insert_last(RESIZABLE_TABLE * table, char *name, void * value) {
	if (table->currentElements == table->maxElements) {
		table->maxElements = 2*(table->maxElements);
		RESIZABLE_TABLE_ENTRY * array_temp = (RESIZABLE_TABLE_ENTRY *) realloc (table->array, table->maxElements*sizeof(RESIZABLE_TABLE_ENTRY));
		table->array = array_temp;
	}		

	table->array[table->currentElements].name = strdup(name);
	table->array[table->currentElements].value = strdup(value);
	table->currentElements++;
	return 0;
}

