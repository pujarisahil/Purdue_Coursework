.data

.balign 4
firstMessage: .asciz "Enter any string"

.balign 4
count: .word 0

.balign 4
printing: .asciz "%d\n"

.balign 4
scanFormat: .asciz "%s"

.balign 4
resultMessage: .asciz "The length of the string is"

.balign 4
format: .asciz "%s\n"

.balign 4
num1: .word 0

.balign 4
return: .word 0

.balign 4
.lcomm userInput, 128

.global main

main: 
	push {r4-r9, fp, lr}
	ldr r1, =return
	str lr, [r1]
	
	ldr r0, =format
	ldr r1, =firstMessage
	bl printf
	
	ldr r0, =scanFormat
	ldr r1, =userInput
	bl scanf
	ldr r7, =count
	ldr r7, [r7]
	ldr r8, =userInput
	ldr r9, [r8]

loop: 	cmp r9, #0
	beq done
	add r8, r8, #1
	add r7, r7, #1
	ldr r9, [r8]
	b loop

done:   mov r1, r7
	ldr r0, =printing
	bl printf

	pop {r4 - r9, fp, pc}
