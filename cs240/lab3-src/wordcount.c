
#include <stdio.h>
#include <stdlib.h>
#include "resizable_table.h"

void
printUsage() {
	printf("Usage: wordcount [-w|-s] file\n");
}

int
main(int argc, char **argv) {
	RESIZABLE_TABLE * table = rtable_create();
	if ( argc < 2) {
		printUsage();
		exit(1);
	}
	return 0;
}


