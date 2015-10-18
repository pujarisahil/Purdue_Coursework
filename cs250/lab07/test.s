.balign 4
message1: .asciz "Enter a string: "

.balign 4
message2: .asciz "Your string has a length of %d\n"

/* Message 3 */
.balign 4
message3: .asciz "Your string is '%s'.\n"

/* format for scanf */
.balign 4
pattern: .asciz "%s"

/*Where we will store the length of the string */
.balign 4
strLen: .word 0

/*where scanf will store the string */
.lcomm string, 128

/*Return value */
.balign 4
return: .word 0

/* Text Section */
.text

end:
        ldr lr, add_of_return /*load in the return address to lr */
        ldr lr, [lr] /* load the value at the address lr into lr */
        bx lr  /* branch exchange (basically end the program) */

add_of_return: .word return

calcStrLen:
        /*load in the first character to reg 2
          compare it to 0 and branch to label
          'done' if the two are the same */

        ldr r2, [r1]
        beq done

        /* increment the counter (r0) and the
           string pointer (r1) */
        add r0, r0, #1
        add r1, r1, #1

        /* store counter value into strLen */
           str r0, [r3]

        /* branch back to top of 'calcStrLen' */
           b calcStrLen

done:
        /* load the address of strLen to reg 1
           and then get the value at reg 1 and
           store it into reg 1 */
        ldr r1, add_of_strLen
        ldr r1, [r1]

        /* load in the address of message2 into
           reg 0 */
        ldr r0, add_of_message2

        /* print */
        bl printf

        /* branch to label 'end' */
        b end

.global main

main:
        /* load return address into reg 1
           and then get the value at the
           address and store it into reg 1 */
        ldr r1, add_of_return
        str lr, [r1]

        /* load address of message1 into reg 0
           and then print message1 */
        ldr r0, add_of_message1
        bl printf

        /* load in the necessary elements to
           scan in a string from the user */
        ldr r0, add_of_pattern
        ldr r1, add_of_string
        bl scanf

        /* prints the string given by the user */
        ldr r0, add_of_message3
        ldr r1, add_of_string
        bl printf

        /* load in the address of the user's string */
        ldr r1, add_of_string

        /*load in the address of strLen, then
          go to the address and store the value
          into r0 */
        ldr r0, add_of_strLen
        ldr r0, [r0]

        /* store strLen's address in reg 3 */
        ldr r3, add_of_strLen

        /* branch to label 'calcStrLen' */
        bl calcStrLen

/*Definitions of address values used */
add_of_message1: .word message1
add_of_message2: .word message2
add_of_message3: .word message3
add_of_pattern: .word pattern
add_of_string: .word string
add_of_strLen: .word strLen

/* external */
.global printf
.global scanf            
