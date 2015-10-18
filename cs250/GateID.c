/**
 * Gate Identifier
 *
 * Identifies what gate is connected using two outputs. Gives a boolean expression if the gate is not identifiable
 *
 * @author Sahil Pujari (pujari@purdue.edu)
 *
 * @lab Lab Section 04 
 *
 * @date 02/25/2015
 *
 */

#include <stdio.h>
#include <wiringPi.h>
 
#define OUT0 0
#define OUT1 2
#define IN 1
 
 
int main(void) {
    int result[4]; //Declare an int array of size 4 that will store the inputs / results
 
    if(wiringPiSetup() == -1)
        return 1;
    pinMode(OUT0, OUTPUT); //Sets up OUT0 as the first output port
    pinMode(OUT1, OUTPUT); //Sets up OUT1 as the second output port
    pinMode(IN, INPUT); //Sets up IN as the input line
 
    digitalWrite(OUT0, 0); //Writes A = 0
    digitalWrite(OUT1, 0); //Writes B = 0
 
    delay(10); //Delays by 10 milliseconds for there be enough time for read to get the current readings
 
    result[0] = digitalRead(IN); //Input with A = 0, B = 0
 
    digitalWrite(OUT0, 0); //Writes A = 0
    digitalWrite(OUT1, 1); //Writes B = 1
 
    delay(10); //Delays by 10 milliseconds for there be enough time for read to get the current readings
 
    result[1] = digitalRead(IN); //Input with A = 0, B = 1
 
    digitalWrite(OUT0, 1); //Writes A = 1
    digitalWrite(OUT1, 0); //Writes B = 0
 
    delay(10); //Delays by 10 milliseconds for there be enough time for read to get the current readings
 
    result[2] = digitalRead(IN); //Input with A = 1, B = 0
 
    digitalWrite(OUT0, 1); //Writes A = 1
    digitalWrite(OUT1, 1); //Writes B = 1
 
    delay(10); //Delays by 10 milliseconds for there be enough time for read to get the current readings
 
    result[3] = digitalRead(IN); //Input with A = 1, B = 1
 
    //For condition 0 0 0 0
    if(result[0] == 0 && result[2] == 0 && result[1] == 0 && result[3] == 0) {
        printf("This is Contraduction. A NULL condition false condition\n");
    }
 
    //For condition 0 0 0 1
    if(result[0] == 0 && result[2] == 0 && result[1] == 0 && result[3] == 1) {
        printf("This is AND gate AB\n");
    }
 
    //For condition 0 0 1 0
    if(result[0] == 0 && result[2] == 0 && result[1] == 1 && result[3] == 0) {
        printf("This is Material nonimplication. A AND NOT B\n");  
    }
 
    //For condition 0 0 1 1
    if(result[0] == 0 && result[2] == 0 && result[1] == 1 && result[3] == 1) {
        printf("This is projection function A\n");  
    }
 
    //For condition 0 1 0 0
    if(result[0] == 0 && result[2] == 1 && result[1] == 0 && result[3] == 0) {
        printf("This is converse nonimplication. NOT A AND B\n");  
    }
   
    //For condition 0 1 0 1   
    if(result[0] == 0 && result[2] == 1 && result[1] == 0 && result[3] == 1) {
        printf("This is projection function B\n");  
    }
 
    //For condition 0 1 1 0
    if(result[0] == 0 && result[2] == 1 && result[1] == 1 && result[3] == 0) {
        printf("This is EX-OR gate\n");
    }
 
    //For condition 0 1 1 1
    if(result[0] == 0 && result[2] == 1 && result[1] == 1 && result[3] == 1) {
        printf("This is OR gate\n");
    }
 
    //For condition 1 0 0 0
    if(result[0] == 1 && result[2] == 0 && result[1] == 0 && result[3] == 0) {
        printf("This is NOR gate\n");
    }
    
    //For condition 1 0 0 1
    if(result[0] == 1 && result[2] == 0 && result[1] == 0 && result[3] == 1) {
        printf("This is EX-NOR gate\n");
    }
 
    //For condition 1 0 1 0
    if(result[0] == 1 && result[2] == 0 && result[1] == 1 && result[3] == 0) {
        printf("This is negation of B. NOT B\n");  
    }
 
    //For condition 1 0 1 1
    if(result[0] == 1 && result[2] == 0 && result[1] == 1 && result[3] == 1) {
        printf("This is converse implication A OR NOT B\n");  
    }
 
    //For condition 1 1 0 0
    if(result[0] == 1 && result[2] == 1 && result[1] == 0 && result[3] == 0) {
        printf("This is negation of A. NOT A\n");  
    }
 
    //For condition 1 1 0 1
    if(result[0] == 1 && result[2] == 1 && result[1] == 0 && result[3] == 1) {
        printf("This is material implication. NOT A OR B\n");  
    }
 
    //For condition 1 1 1 0
    if(result[0] == 1 && result[2] == 1 && result[1] == 1 && result[3] == 0) {
        printf("This is NAND gate\n");
    }
 
    //For condition 1 1 1 1
    if(result[0] == 1 && result[2] == 1 && result[1] == 1 && result[3] == 1) {
        printf("This is tautology\n");  
    }

    return 0;
}
