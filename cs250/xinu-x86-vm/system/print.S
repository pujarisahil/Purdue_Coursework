.data

string: .asciz "Hello World!"    

.text
.global print

print:
	movl $string,%esi
b:

	mov (%esi), %al
	mov $0x3F8,%dx
	outb %al, %dx
	
poll:
	mov $0x3F8, %dx
	add $5, %dx
	inb %dx, %al
	mov $0x20, %bl
	and %bl, %al
	cmp %bl, %al
	jne poll
	
	add $0x1,%esi
	movb (%esi),%al
	cmp $0,%al
	jne b

	
ret