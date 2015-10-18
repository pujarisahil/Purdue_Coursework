#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>

#define READABLE_FILE "file_to_read.txt" /* File to be read during read operations */
#define BYTES_TO_READ_WRITE 819200 /* 800 KB */
#define MAX_BUFFER  1048576 /* 1 MB*/

/* Function for write without buffer */
void mywritec(char);

/* Functions for write with buffer */
void mywritebufsetup(int);
void myputc(char);
void mywriteflush(void);

/* Function for read without buffer */
int myreadc(void);

/* Functions for read with buffer */
void myreadbufsetup(int);
int mygetc(void);

/* Declare global variables for write operations here */
int fd_write = 1;
char write_buf[MAX_BUFFER];
char *wp;
int write_buf_size;
int write_current_size = 0;

/* Declare global variables for read operations here */
int fd_read;
char read_buf[MAX_BUFFER];
char *rp;
int read_buf_size;
int read_count;

/* Main function starts */
int main() {
    clock_t begin, end;
    int option, n, temp;
    const char *a="Writing byte by byte\n";
    const char *b="Writing using buffers\n";
    unsigned long i, bytes_to_write = BYTES_TO_READ_WRITE, bytes_to_read = BYTES_TO_READ_WRITE;
    char ch;

    while(1) {
        printf("\n 1 - Write without buffering \n 2 - Write with buffering");
        printf("\n 3 - Read without buffering \n 4 - Read with buffering");
        printf(("\n 5 - Exit \n Enter the option: "));
        scanf("%d", &option);
        switch(option) {
            case 1: /* Write without buffer */
            begin = clock();
            for (i=0;i<bytes_to_write;i++) {
                ch = a[i%strlen(a)];
                mywritec(ch);
            }
            end = clock();
            printf("\n Time to write without buffering: %f secs\n",(double)(end - begin)/CLOCKS_PER_SEC);
            break;

            case 2:  /* Write with buffer */
            printf("\n Enter the buffer size in bytes: ");
            scanf("%d", &n);
            mywritebufsetup(n);
            begin = clock();
            for (i=0;i<bytes_to_write;i++) {
                ch = b[i%strlen(b)];
                myputc(ch);
            }
            mywriteflush();
            end = clock();
            printf("\n Time to write with buffering: %f secs\n",(double)(end - begin)/CLOCKS_PER_SEC);
            break;

            case 3:  /* Read without buffer */
            fd_read = open(READABLE_FILE, O_RDONLY);
            if(fd_read < 0) {
                printf("\n Error opening the readable file \n");
                return 1;
            }
            int c;
            begin = clock();
                for (i=0;i<bytes_to_read;i++) {  /* you may need to modify this slightly to print the character received and also check for end of file */
            c = myreadc();
            printf("%c", c);
            if(c == -1)
            {
                printf("\n End of file \n");
                break;
            }
        }
        end = clock();
        if(close(fd_read)) {
            printf("\n Error while closing the file \n ");
        }
        printf("\n Time to read without buffering: %f secs\n",(double)(end - begin)/CLOCKS_PER_SEC);
        break;

            case 4:  /* Read with buffer */
        printf("\n Enter the buffer size in bytes: ");
        scanf("%d", &n);
        myreadbufsetup(n);
        fd_read = open(READABLE_FILE, O_RDONLY);
        if(fd_read < 0) {
            printf("\n Error opening the readable file \n");
            return 1;
        }
        begin = clock();
                for (i=0;i<bytes_to_read;i++) { /* you may need to modify this slightly to print the character received and also check for end of file */
        if(mygetc() == -1) {
            printf("\n End of file \n");
            break;
        }
    }
    end = clock();
    if(close(fd_read)) {
        printf("\n Error while closing the file \n ");
    }
    printf("\n Time to read with buffering: %f secs\n",(double)(end - begin)/CLOCKS_PER_SEC);
    break;

    default:
    return 0;
}
}
}

void mywritec(char ch) {
    /* Use the system call write() to write char 'ch' to standard output (file descriptor 1) */
    void *v = (void *) &ch;
    write(fd_write, (void *)v, 1);
}

void mywritebufsetup(int n) {
    /* verify that n is a positive integer less than or equal to MAX_BUFFER, and store n
     * in global variable write_buf_size */
    if (n <= 0 || n > MAX_BUFFER) {
        return;
    }
    write_buf_size = n;
    /* in global variable write_buf_size */
    wp = &(write_buf[0]);
}

void myputc(char ch) {
   /* If the buffer is full (contains write_buf_size characters), write the entire buffer to standard */
    /*    output using the write() system call and reset wp to the first location of the buffer */

    *wp = ch;
    wp++;

    if (wp == &write_buf[write_buf_size - 1]) {
        write(fd_write, write_buf, write_buf_size);
        wp = &write_buf[0];
    }
    /* Note that myputc() will be called multiple times before the buffer is written out */
}

void mywriteflush(void) {
    /* Note: this function will be called after all calls to myputc() have been made */
    /* if any characters remain in the write buffer, write them to standard output */
    //while(*wp != 0)
      //  write(1, (void *) wp, 1);
    char *ptr = &write_buf[0];
    int counter = 0;

    while(ptr < wp) {
        counter++;
        ptr++;
    }

    write(fd_write,write_buf,counter);
}

int myreadc(void) {
    /* if read() indicates end-of-file, return -1 to the caller. */ 
    /* Use read() system call to read a character from standard input (file descriptor: fd_read) */
    int c;
    read(fd_read, &c, 1);
    if (c == EOF)
        return -1;
    /* Otherwise, return the character that was read in the low-order byte of the integer (be careful to avoid sign extension). */
    c &= 0xf;
    return c;
}

void myreadbufsetup(int n) {

    /* Verify that n is a positive integer less than or equal to MAX_BUFFER, and store n in global variable read_buf_size. */
    if (n >= 0 && n <= MAX_BUFFER)
        read_buf_size = n;
    /* Set read_count to zero */
    read_count = 0;
}

int mygetc() {
    /* If read_count is less than or equal to zero, call read() to read up to read_buf_size bytes into read_buf from */
    /*   standard input (file descriptor 0), and set read_count to the number of bytes actually read. Set rp to the */
    /*   first location in the buffer.  If the read_count is zero (the read call returned end-of-file), return -1 to */
    /*   the caller to indicate end-of-file. */
    if (read_count <= 0) {
        read_count = read(fd_read, read_buf, read_buf_size);
        rp = &read_buf[0];
        if (read_count == 0)
            return -1;
    }
    /* Extract the next character from the buffer, increment rp and decrement read_count.  Return the character extracted */
    /*   from the buffer in the low-order byte of an integer (be careful to avoid sign extension). */
    char ch = *rp;
    rp++;
    read_count--;
    return ch;
    /* Note that this function will be called multiple times before the whole buffer is emptied. */
}
