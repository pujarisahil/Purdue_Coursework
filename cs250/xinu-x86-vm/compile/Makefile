#
#  Makefile for the x86 version of the Xinu operating system
#

COMPILER_ROOT = /usr/bin/

CC 	=	${COMPILER_ROOT}gcc
cPP	=	${COMPILER_ROOT}gcc -E
AS  	=	${COMPILER_ROOT}as --32
LD	=	${COMPILER_ROOT}ld
OBJCOPY	=	${COMPILER_ROOT}objcopy
LIB	= 	../lib/libxc
XINU	=	./xinu

MAKEDEP	= gcc -M -MG

DEFS	= -DBSDURG
CFLAGS_EXTRA =
INCLUDE = -I../include

# Amount to move loaded image down in memory

BRELOC  =	0x150000
BOOTPLOC=	0x150000

# Compiler flags
SDEFS	= -I../include
CFLAGS	= -march=i586 -m32 -fno-builtin -fno-stack-protector -nostdlib -c -Wall -O ${DEFS} ${INCLUDE} ${CFLAGS_EXTRA}

# Loader flags

LDFLAGS = -m elf_i386		  

TOPDIR	= ..

# Components (files for each are listed below) 

COMPS	=	system 							\
		device/tty						

# Start with empty source file list and add files for each directory
#

SRC_FILES =

#------------------------------------------------------------------------
# Files for ../system							#
#------------------------------------------------------------------------


SYSTEM_SFILES =								\
	start.S		intr.S	print.S

SYSTEM_CFILES =								\
	conf.c		getdev.c	initialize.c 	ioerr.c		\
	ionull.c	recvclr.c	i386.c			evec.c 

SYSTEM_SFULL = ${SYSTEM_SFILES:%=../system/%}
SYSTEM_CFULL = ${SYSTEM_CFILES:%=../system/%}

SRC_FILES += $(SYSTEM_SFULL)
SRC_FILES += $(SYSTEM_CFULL)

#------------------------------------------------------------------------
# Files for ../device/tty						#
#------------------------------------------------------------------------


TTY_CFILES =								\
	ttyInit.c	

#TTY_SFULL = ${TTY_SFILES:%=../device/tty/%}
TTY_CFULL = ${TTY_CFILES:%=../device/tty/%}

#SRC_FILES += $(TTY_SFULL)
SRC_FILES += $(TTY_CFULL)

#------------------------------------------------------------------------
# Files for ../shell
#------------------------------------------------------------------------



#######################################
# Generate a list of all object files
#######################################

OBJ_TMP	= $(patsubst %.c,%.o,$(SRC_FILES)) 	# substitute .c => .o
OBJ_FILES  = $(patsubst %.S,%.o,$(OBJ_TMP)) 	# substitute .S => .o


# Export variables for recursive make calls (such as the library)
export

#------------------------------------------------------------------------
# make targets
#------------------------------------------------------------------------

all:	xinu

xinu: 	Makefile ../config/Configuration xinu.mapfile configure 	\
		$(OBJ_FILES) $(LIB)/libxc.a
		@echo;echo Now Loading all object files to produce GRUB bootable xinu: 
		@echo;echo ${OBJ_FILES} 
		$(LD) $(LDFLAGS) -dn -Ttext 0x0 -e start ${OBJ_FILES}	\
			${LIB}/libxc.a	-o ${XINU}
		@echo

${LIB}/libxc.a:
		@(cd ${LIB}; make libxc.a)

objects: $(OBJ_FILES)

configure: ../include/conf.h 	../system/conf.c	
	
../include/conf.h ../system/conf.c: ../config/config ../config/Configuration
	@echo making conf.h and conf.c
	@(cd ../config; make install)

../config/config:
	make -C ../config/ -f ../config/Makefile clean all install
	
clean:
	@echo removing .o files in directories
	@echo $(COMPS)
	@rm -f ${OBJ_FILES} ${LIB}/*.a ${LIB}/*.o
	@echo removing lib ...
	@(cd ${LIB}; make clean)
	@echo   removing configuration files ...
	@rm -f ../include/conf.h ../system/conf.c
	@(cd ../config; make clean)
	@echo removing xinu ...
	@rm -f xinu
	@rm -f xinu.iso
	@rm -f ./iso/boot/xinu


depend:
	@echo;echo Making all dependencies in .deps
	@$(MAKEDEP) ${INCLUDE} ${SRC_FILES} > .deps
	@echo;echo Finished making dependencies

depclean:
	rm -f .deps
	touch .deps

###################
# Special targets #
###################
${COMPS}:
	$(MAKE) COMPS="$@" objects

# Special rules for special files

../system/print.o: ../system/print.S
	${CPP} ${SDEFS} ../system/print.S | ${AS} -o ../system/print.o

../system/intr.o: ../system/intr.S
	${CPP} ${SDEFS} ../system/intr.S | ${AS} -o ../system/intr.o



../system/start.o: ../system/start.S Makefile
	${CPP} ${SDEFS} -DBRELOC=${BRELOC} -DBOOTPLOC=${BOOTPLOC} 	\
			../system/start.S | ${AS} -o ../system/start.o


head.o: head.S
	${CPP} ${SDEFS} head.S | ${AS} -o head.o

../system/initialize.o: ../system/initialize.c
	sh mkvers.sh
	${CC} -c ${CFLAGS} -DVERSION=\""`cat version`"\" 		\
		../system/initialize.c -o ../system/initialize.o

include .deps
