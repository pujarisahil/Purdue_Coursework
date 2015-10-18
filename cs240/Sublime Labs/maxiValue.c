#include <stdio.h>

main() {
	int a, b;
	int min, max;
	while(1) {
		printf("Enter number a : \n");
		scanf("%d", &a);
		getchar();

		printf("Enter another number b: \n");
		scanf("%d", &b);
		getchar();

		if(a > b) {
			min = b;
			max = a;
		}
		else {
			min = a;
			max = b;
		}

		printf("The minimum number is %d and max number is %d \n", min , max);

		printf("Would you like to continue? Enter y for yes and n for no : \n");
		char answer;
		answer = getchar();

		if(answer =='n') {
			break;
		}
	}

	printf("Congrats you made it to the end!");



}