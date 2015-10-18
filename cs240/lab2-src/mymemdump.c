
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void mymemdump(FILE * fd, char * p , int len) {

    int i = 0;
    int count = 0;
    int count2 = 0;
    int j = 0;
    int z = 0;
    int z2 = 0;
    int c;

    for (i = 0; i < len; i+= 16) {
    fprintf(fd, "0x%016lX: ", (unsigned long) p + count);    
        for(j = 0; j < 16; j++) {
            c = p[count]&0xFF;
            count++;
            if(count <= len) {
                fprintf(fd, "%02X ", c);
            } else {
                fprintf(fd, "   ");
            }

        }
        fprintf(fd, " ");
        //for(int k=0; k< (j-16)*3 ; k++){
        //    fprintf(fd," ");
        //}
        
        for(z = 0; z < 16; z++) {
            c = p[count2]&0xFF;
            if(count2 <len) {
                fprintf(fd, "%c", (c>=32)?c:'.');
                count2++;
            }    
        }  
        fprintf(fd,"\n");
    }
}