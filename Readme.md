********Project Structure*******

1) CashDispenser/input.txt: This file contains the available cash balance in system.
	E.g. 10 1000 i.e., There are 1000 notes available of 10Rs.
2) The log file gets generated in C:\\ folder as cashwithdrawal.log.


********Steps to run the Project*******

1) Go to directory from command line where jar and input files are present.
2) Keep both jar file and input.txt in same folder.
3) Execute below command to run the project
	For Interactive, Run below command to withdraw amount. We will pass amount as the command line parameters(space separated) as argument
		java -jar CashDispenser-0.9-SNAPSHOT-jar-with-dependencies.jar <space separated amount to be withdrawn>
	For Eg:
		java -jar CashDispenser-0.9-SNAPSHOT-jar-with-dependencies.jar 5000 200 10 10000

***************Assumptions***************

1) System only takes amount which are multiple of 10
2) System only contains denomination which are multiple of 10
3) Currently system supports multiple withdrawal at same time by creating multiple threads
4) To show multiple withdrawals, we are taking multiple amount as parameters to the system.

********Sample Input/Output Example*********

Input:

	java -jar CashWithdrawalSystem-0.9-SNAPSHOT-jar-with-dependencies.jar 300 10 10000


Output:
--------------------------------------------
2018-06-17 20:46:44 INFO :- Denominations dispensed for amount 300 
6 notes of 50 Rs.
--------------------------------------------
2018-06-17 20:46:44 INFO :- Denominations dispensed for amount 10 
1 notes of 10 Rs.
--------------------------------------------
2018-06-17 20:46:44 INFO :- Denominations dispensed for amount 10000 
1 notes of 2000 Rs. 
16 notes of 500 Rs.

