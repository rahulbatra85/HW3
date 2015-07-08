#iterate ALOCK 100 times with 1 to 6 threads
for a in `seq 1 100`
do 
	for x in `seq 1 6`
	do 
		java ALock $x | awk '{print $3}' >> _$a 
	done
done
paste _* > 2_ALock
rm _*

#iterate MCSLOCK 100 times with 1 to 6 threads
for a in `seq 1 100`
do 
	for x in `seq 1 6`
	do 
		java MCSLock $x | awk '{print $3}' >> _$a 
	done
done
paste _* > 2_MCSLock
rm _*
