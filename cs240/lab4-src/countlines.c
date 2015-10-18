#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>


int
main(int argc, char ** argv) {
	int c;
	int count = 0;
	printf("Program to count lines. Type a string and ctrl-d to exit\n");
	while ((c=getchar())!=-1) {
		if(c== '\n')
			count++;
	}
	printf("Total lines: %d\n",count);
}
