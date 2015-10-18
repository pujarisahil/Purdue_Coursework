.data

.balign 4
messageFirst: .asciz "Enter the first Number"

.balign 4
resultMessage: .asciz "The bigger number is "

.balign 4
equalMessage: .asciz "The numbers are equal"

.balign 4
num1: .word 0

.balign 4
num2: .word 0

.balign 4
format: .asciz "%s\n"

.balign 4
scanFormat: .asciz "%d"

.balign 4
printFormat: .asciz "%d\n"

.balign 4
return: .word 0

.balign 4
messageTwo: .asciz "Enter the second Number"

.text

equalCondition: ldr r1, =equalMessage
		ldr r0, =format
		bl printf
		ldr lr, =return
		ldr lr, [lr]
		bx lr

compareFunction: cmp r4, r5
		 bgt higherFunction
		 blt lowerFunction	 
		ldr lr, =return
		ldr lr, [lr]
		bx lr

higherFunction: ldr r6, =num1
		ldr r1, [r6]
		ldr r0, =printFormat
		bl printf

		ldr lr, =return
		ldr lr, [lr]
		bx lr

lowerFunction: ldr r6, =num2
		ldr r1, [r6]
		ldr r0, =printFormat
		bl printf

		ldr lr, =return
		ldr lr, [lr]
		bx lr

.global main

main:

	ldr r1, =return
	str lr, [r1]

	ldr r0, =format
	ldr r1, =messageFirst
	bl printf
	
	ldr r0, =scanFormat
	ldr r1, =num1	
	bl scanf
	
	ldr r0, =format
	ldr r1, =messageTwo
	bl printf
	
	ldr r0, =scanFormat
	ldr r1, =num2
	bl scanf
	
	ldr r6, =num1
	ldr r4, [r6]

	ldr r6, =num2
	ldr r5, [r6]
	
	ldr r1, =resultMessage
	ldr r0, =format
	bl printf

	cmp r4, r5
	beq equalCondition
	bne compareFunction


