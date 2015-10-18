#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char ** argv) {
	int c;
	char a[5000];
	int count = 0;
	int i = 0;
	printf("Program to separate text to words. Type a string and ctrl-d to exit\n");
	while ((c=getchar())!=EOF) {
		if((c != ' ') && (c != '\n')) {
			a[i] = (char) c;
			i++;
		}
		else if (i > 0) {
			a[i]='\0';
			printf("Word %d: %s\n",count,a);
			count++;
			i = 0;
		}
	}
	printf("words total = %d\n", count);
	//words total = 5
}
