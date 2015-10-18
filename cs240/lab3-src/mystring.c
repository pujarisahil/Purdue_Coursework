
#include <stdlib.h>
#include "mystring.h"

int mystrlen(char * s) {
	int length = 0;
	while (s[length] != '\0')
      length++;
  	return length;
}

char * mystrcpy(char * dest, char * src) {
	char *s = dest;
	while (*dest++=*src++)
		;
	return s; 
}

char * mystrcat(char * dest, char * src) {
	char*a;
	for (a = dest; *a; ++a)
    	;
  	for (; *src; ++a, ++src)
    	*a = *src;
  	*a = 0;
  	return dest;
}

int mystrcmp(char * s1, char * s2) {
	if (*s1 < *s2)
        return -1;
 
    if (*s1 > *s2)
        return 1;
 
    if (*s1 == '\0')
        return 0;
 
    return mystrcmp(s1 + 1, s2 + 1);
}

char * mystrstr(char * hay, char * needle) {
	int i;
	while( *hay ) {
		for( i = 0; needle[i]; i++ ) {
			if( hay[i] != needle[i] )
				break;
		}
		if( !needle[i] )
			return (char *)hay;
		hay++;
	}

	return (char *)0;
}

char * mystrdup(char * s) {
	char *d = malloc (mystrlen (s) + 1);
    if (d == NULL) return NULL;
    mystrcpy (d,s);
    return d;   
}

char * mymemcpy(char * dest, char * src, int n)
{
	char* dst = (char*)dest;
    char* src2 = (char*)src;

    while (n--) {
        *dst++ = *src2++;
    }
    return dest;
}
