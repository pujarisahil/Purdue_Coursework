#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct X{
  char a;
  int i;
  char b;
  int *p;
};

struct List {
  char * str;
  struct List * next;
};

void memdump(char* p , int len) {
  int i = 0;
  int count = 0;
  int count2 = 0;
  int j = 0;
  int z = 0;
  int z2 = 0;
  int c;

  for (i = 0; i < len; i+= 16) {
    printf("0x%016lX: ", (unsigned long) p + count);    
    for(j = 0; j < 16; j++) {
      c = p[count]&0xFF;
      count++;
      if(count <= len) {
        printf("%02X ", c);
      } else {
        printf("   ");
      }

    }
    printf(" ");
    for(z = 0; z < 16; z++) {
      c = p[count2]&0xFF;
      if(count2 <len) {
        printf("%c", (c>=32)?c:'.');
        count2++;
      }    
    }  
    printf("\n");
  }
}

void free_LinkList(struct List * head) {
  struct List * node;
  struct List * temp;
  node = head;
  while (node != NULL) {
    temp = node;
    node = node->next;
    free(temp);
  }
}

int main() {

  // Declare local variables.
  // These are stored on the stack.
  char str[20];
  int a;
  int b;
  double y;
  int int_array[4];
  struct X x;

  struct List * head;

  printf("str:       %p\n", str);
  printf("&a:        %p\n", &a);
  printf("&b:        %p\n", &b);
  printf("&y:        %p\n", &y);
  printf("&x:        %p\n", &x);
  printf("&x.a:      %p\n", &x.a);
  printf("&x.i:      %p\n", &x.i);
  printf("&x.b:      %p\n", &x.b);
  printf("&x.p:      %p\n", &x.p);
  printf("int_array: %p\n", int_array);
  printf("array[0]   %p\n", &int_array[0]);
  printf("array[1]   %p\n", &int_array[1]);
  printf("array[2]   %p\n", &int_array[2]);
  printf("array[3]   %p\n", &int_array[3]);
  printf("array[4]   %p\n", &int_array[4]);
  printf("array[5]   %p\n", &int_array[5]);
  printf("array[6]   %p\n", &int_array[6]);
  printf("array[7]   %p\n", &int_array[7]);
  printf("\n");  

  // memdump before variable assignment
  printf("\n               -----Part 3.1 before variable assignment-----\n\n");
  memdump((char *) &x.a, 96);

  strcpy(str, "Hello World\n");
  a = 5;
  b = -5;
  y = 12.625;
  int_array[0] = 0;
  int_array[1] = 10;
  int_array[2] = 20;
  int_array[3] = 30;
  x.a = 'A';
  x.i = 9;
  x.b = '0';
  x.p = &x.i;

  // memdump after variable assignment
  printf("\n               -----Part 3.1 after variable assignment-----\n\n");
  memdump((char *) &x.a, 96);

  // The following assignments may cause a compiler warning. 'Safely' ignore the warning.
  // However, using *(int_array + n) instead of int_array[n] does not cause a warning.
  int_array[4] = 1685024583;
  int_array[5] = 543521122;
  int_array[6] = 1819438935;
  int_array[7] = 100;

  // memdump after buffer overflow
  printf("\n               -----Part 3.1 after buffer overflow-----\n\n");
  memdump((char *) &x.a, 96);


  head = (struct List *) malloc(sizeof(struct List));

  // memdump before variable assignment
  printf("\n               -----Part 3.2 before variable assignment-----\n\n");
  memdump((char*) head, 128);

  head->str=strdup("Welcome ");
  head->next = (struct List *) malloc(sizeof(struct List));
  head->next->str = strdup("to ");
  head->next->next = (struct List *) malloc(sizeof(struct List));
  head->next->next->str = strdup("cs250");
  head->next->next->next = NULL;

  // memdump after variable assignment
  printf("\n               -----Part 3.2 after variable assignment-----\n\n");
  memdump((char*) head, 128);
  printf("head=0x%lx\n", (unsigned long) &head);
  printf("head=0x%lx\n", (unsigned long) head);
  printf("head->str=0x%lx\n", (unsigned long) head->str);
  printf("head->next=0x%lx\n", (unsigned long) head->next);
  printf("head->next->str=0x%lx\n", (unsigned long) head->next->str);
  printf("head->next->next=0x%lx\n", (unsigned long) head->next->next);
  printf("head->next->next->str=0x%lx\n", (unsigned long) head->next->next->str);
  printf("head->next->next->next=0x%lx\n", (unsigned long) head->next->next->next);

  free_LinkList(head);
}
