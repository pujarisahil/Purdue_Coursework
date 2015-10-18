#!/bin/bash

# Starts Alfred in a background process with pipes in and out of it

javac TaxBracket.java
javac Matrix.java
coproc java TaxBracket
# Alfred's stdin is now referred to with ${COPROC[1]}
echo 8460,3 >&${COPROC[1]}
if [ $? != 0 ]; then
	echo -e "Problem"
fi
echo 25500,5 >&${COPROC[1]}
if [ $? != 0 ]; then
	echo -e "Problem"
fi
echo 76000,2 >&${COPROC[1]}
if [ $? != 0 ]; then
	echo -e "Problem"
fi

echo 123000,4 >&${COPROC[1]}
if [ $? != 0 ]; then
	echo -e "Problem"
fi

echo 186350,1 >&${COPROC[1]}
if [ $? != 0 ]; then
	echo -e "Problem"
fi

echo 186351,2 >&${COPROC[1]}
if [ $? != 0 ]; then
	echo -e "Problem"
fi

echo 20 >&${COPROC[1]}
# Alfred's stdout is found with ${COPROC[0]}
cat <&${COPROC[0]} > output.txt

#grep output.txt compare.txt
diff output.txt compare.txt > result.txt


echo -e "$PWD\n\n\n______________________________________\n\n" >> /homes/pujari/ultimate/something.txt
#echo "${PWD##*/}" >> /homes/pujari/ultimate/something.txt
diff output.txt compare.txt >> /homes/pujari/ultimate/something.txt
#comm -23 <(sort compare.txt) <(sort output.txt) > result.txt
#comm -23 <(sort output.txt) <(sort compare.txt) > result2.txt
#sdiff -s output.txt compare.txt
