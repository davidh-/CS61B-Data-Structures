#! /bin/bash

clear
echo "This file is to set up Git graph that is sufficiently complciated to check if your code behaves the same as the Staff one."
echo "You are welcome to contribute and change the script."
echo "Bo Zeng 2015 "

read -p "Are you sure your code is ready for this complicated merge and rebase test? [y/n]" -n 1 -r answer
echo
if [[ $answer =~ ^[Yy]$ ]]
then
	bash testStaffGitlet.sh
	echo "setting up your Gitlet Tests...it should not take too long"
	bash testGitlet.sh
	echo "If you have encountered any errors in your Gitlet, it means your code need a fix"

	echo "Now comparing your results with StaffGitlet..."
	read -p "Do you want to see the results for each of the test? [y/n]" -n 1 -r answer 
	echo
	if [[ $answer =~ ^[Yy]$ ]]
		then 

			if diff testResultsYours/merge_1.txt testResultsStaff/merge_1.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for merge case 1"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/merge_1.txt and testResultsStaff/merge_1.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
			if diff testResultsYours/merge_2.txt testResultsStaff/merge_2.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for merge case 2"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/merge_2.txt and testResultsStaff/merge_2.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
			if diff testResultsYours/merge_3.txt testResultsStaff/merge_3.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for merge case 3"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/merge_3.txt and testResultsStaff/merge_3.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
			if diff testResultsYours/merge_4.txt testResultsStaff/merge_4.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for merge case 4"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/merge_4.txt and testResultsStaff/merge_4.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
			if diff testResultsYours/merge_5.txt testResultsStaff/merge_5.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for merge case 5"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/merge_5.txt and testResultsStaff/merge_5.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
			if diff testResultsYours/merge_6.txt testResultsStaff/merge_6.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for merge case 6"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/merge_6.txt and testResultsStaff/merge_6.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
			if diff testResultsYours/rebase_1.txt testResultsStaff/rebase_1.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for rebase case 1"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/rebase_1.txt and testResultsStaff/rebase_1.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
			if diff testResultsYours/rebase_2.txt testResultsStaff/rebase_2.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for rebase case 2"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/rebase_2.txt and testResultsStaff/rebase_2.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
			if diff testResultsYours/rebase_3.txt testResultsStaff/rebase_3.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for rebase case 3"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/rebase_3.txt and testResultsStaff/rebase_3.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
			if diff testResultsYours/rebase_4.txt testResultsStaff/rebase_4.txt >/dev/null ; then
  				echo "Congrats! your code behaves the same as the Staff Edition in terms of merge and rebase for rebase case 4"
			else
  				echo "Sorry, your code behaves differently as the Staff Edition in terms of merge and rebase. You can check testResultsYours/rebase_4.txt and testResultsStaff/rebase_4.txt to find the difference"
  				echo "read this script file can help you debug"
			fi
	fi
fi