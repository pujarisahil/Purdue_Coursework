#include <stdio.h>

main() {

	printf("Welcome to the High Low game...\n");
	//printf("Think of a number between 1 and 100 and press press <enter>\n");
	
	int check = 1;
	int average, min = 1, max = 100;
	char r = '\0';
	char r2;

	while(check) {
		//printf("Welcome to the High Low game...\n");
	printf("Think of a number between 1 and 100 and press press <enter>\n");
		//getchar();
		int a = 1;
		while (a) {
			average = (min + max) / 2;
			if (r != '\n') {
				if(r == 'y' || r == 'n') {
					printf("Is it higher than %d? (y/n)\n", average);
				}
			else {
				printf("Type y or n\n");
				printf("Is it higher than %d? (y/n)\n", average);
				}
			}

			r = getchar();

			if(r == 'e') {
				printf("test");
			}

			//if(r != 'y' || r != 'n' )
			//	printf("Type y or n");
			//getchar();
			if(r == 'y') {
				min = average + 1;
			}

			if(r == 'n') {
				max = average - 1;
			}


			if( max < min) {
				printf("\n>>>>>> The number is %d\n", min);
				getchar();
				break;
			}


			//printf("%d %d\n", max, min);
		}
		
		printf("\nDo you want to continue playing (y/n)?");
		r2 = getchar();
		//getchar();
		//printf("%d\n", r2);

		if (r2 == 'y') {
			min = 1, max = 100;
			getchar();
		} else {
			check = 0;
		}
	}

	return 0;
}
