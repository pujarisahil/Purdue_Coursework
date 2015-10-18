.data

.balign 4
num1: .word 0

.balign 4
return: .word 0

.balign 4
startMessage: .asciz "Enter any 5 numbers"

.balign 4
printingFormat: .asciz "%d\n"

.balign 4
scanFormat: .asciz "%d"

.balign 4
stringFormat: .asciz "%s\n"

.balign 4
count: .word 0

.balign 4
num2: .word 0

.balign 4
num3: .word 0

.balign 4
num4: .word 0

.balign 4
num5: .word 0

.balign 4
answer: .word 0

.global main

main :
	push {r4 - r9, fp, lr}

	ldr r1, =return
	str lr, [r1]

	ldr r0, =stringFormat
	ldr r1, =startMessage
	bl printf
	
	ldr r5, =answer
	ldr r5, [r5]
	ldr r4, =count
	ldr r4, [r4]

loop:   cmp r4, #5
	beq stop
	ldr r0, =scanFormat
	ldr r1, =num1
	bl scanf
	ldr r6, =num1
	ldr r6, [r6]
	add r4, r4, #1
	add r5, r5, r6
	b loop

stop: ldr r0, =printingFormat
	mov r1, r5
	bl printf

	pop {r4 - r9, fp, pc}
