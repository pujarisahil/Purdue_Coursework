
all: test_resizable_table test_linked_list test_mystring wordcount

test_resizable_table: test_resizable_table.c resizable_table.c
	gcc -g -o test_resizable_table test_resizable_table.c resizable_table.c

test_linked_list: test_linked_list.c linked_list.c
	gcc -g -o test_linked_list test_linked_list.c linked_list.c

test_mystring: test_mystring.c mystring.c
	gcc -g -o test_mystring test_mystring.c mystring.c

wordcount: wordcount.c resizable_table.c
	gcc -g -o wordcount wordcount.c resizable_table.c

clean:
	rm -f *.o *.out total.txt grades1.rt test_resizable_table test_linked_list test_mystring

