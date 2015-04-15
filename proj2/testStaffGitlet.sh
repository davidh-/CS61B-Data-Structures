#! /bin/bash

echo "now setting up StaffGitlet"
rm -rf .gitlet
rm -rf StafftestFiles
rm -rf testResultsStaff
mkdir StafftestFiles
mkdir testResultsStaff
echo "initializing..."
java StaffGitlet init
echo "building a complex enough Git..."

echo "PII: file that's very old" > StafftestFiles/PII.txt
echo "PI: file that's no one knows what it is" > StafftestFiles/PI.txt
echo "commmon1: Apple WATCH PRICE: 20,000USD. Are you going to buy?" > StafftestFiles/common1.txt
echo "commmon2: This Summer: 61C Class sucks" > StafftestFiles/common2.txt
echo "commmon3: Should You go to MIT instead?" > StafftestFiles/common3.txt

java StaffGitlet add StafftestFiles/PII.txt
java StaffGitlet add StafftestFiles/common1.txt
java StaffGitlet add StafftestFiles/common2.txt
java StaffGitlet add StafftestFiles/common3.txt
java StaffGitlet add StafftestFiles/PI.txt

echo "ORIGIN"
java StaffGitlet commit ORIGIN

java StaffGitlet branch F

java StaffGitlet rm StafftestFiles/PII.txt
# change common2.txt
echo "commmon2: This Summer: 61C Class rocks!" > StafftestFiles/common2.txt
java StaffGitlet add StafftestFiles/common2.txt

echo "PI"
java StaffGitlet commit PI

java StaffGitlet branch C

echo "GI: file that's miracle" > StafftestFiles/GI.txt
java StaffGitlet add StafftestFiles/GI.txt
echo "GI"
java StaffGitlet commit GI

echo "GII: file that's changing everything" > StafftestFiles/GII.txt
java StaffGitlet add StafftestFiles/GII.txt
echo "commmon1: Apple WATCH PRICE: 20,000USD. Buy the cheap one you idiot!" > StafftestFiles/common1.txt
# change common1.txt
java StaffGitlet add StafftestFiles/common1.txt
echo "GII"
java StaffGitlet commit GII

java StaffGitlet branch D

java StaffGitlet rm StafftestFiles/common3.txt
echo "MI"
java StaffGitlet commit MI

echo "MII: file that's improving over G" > StafftestFiles/MII.txt
java StaffGitlet add StafftestFiles/MII.txt
echo "MII"
java StaffGitlet commit MII

java StaffGitlet add StafftestFiles/common3.txt
echo "MIII"
java StaffGitlet commit MIII

echo "HI: I added a new app to this pool" > StafftestFiles/HI.txt
echo "yes" | java StaffGitlet checkout D 
java StaffGitlet add StafftestFiles/HI.txt
echo "HI"
java StaffGitlet commit HI
# change common2.txt
echo "commmon2: This Summer: 61C Don't need it any more , take the OS class instead!" > StafftestFiles/common2.txt
java StaffGitlet add StafftestFiles/common2.txt
echo "HII"
java StaffGitlet commit HII

echo "DI: Revenue increased by this new feature!" > StafftestFiles/DI.txt
java StaffGitlet branch E
java StaffGitlet add StafftestFiles/DI.txt
java StaffGitlet rm StafftestFiles/common2.txt
echo "DI"
java StaffGitlet commit DI

echo "EI: ok we overestimated the revenue" > StafftestFiles/EI.txt
echo "yes" | java StaffGitlet checkout E 
java StaffGitlet add StafftestFiles/EI.txt
echo "EI"
java StaffGitlet commit EI

echo "CI: this is a garbage branch. Level 1" > StafftestFiles/CI.txt
echo "yes" | java StaffGitlet checkout C 
java StaffGitlet add StafftestFiles/CI.txt
echo "CI"
java StaffGitlet commit CI

java StaffGitlet rm StafftestFiles/common2.txt
echo "CII"
java StaffGitlet commit CII

java StaffGitlet add StafftestFiles/common2.txt
echo "CIII"
java StaffGitlet commit CIII

echo "CIV: this is a 'garbage' branch. Level 4" > StafftestFiles/CIV.txt
java StaffGitlet add StafftestFiles/CIV.txt
echo "CIV"
java StaffGitlet commit CIV

echo "yes" | java StaffGitlet checkout F 
java StaffGitlet rm StafftestFiles/PI.txt

echo "commmon1: We go bankrupt, no Apple Watches" > StafftestFiles/common1.txt
echo "commmon2: the department is shutting" > StafftestFiles/common2.txt
echo "commmon3: find jobs in Microsoft now." > StafftestFiles/common3.txt
java StaffGitlet add StafftestFiles/common1.txt
java StaffGitlet add StafftestFiles/common2.txt
java StaffGitlet add StafftestFiles/common3.txt
echo "PII"
java StaffGitlet commit PII

java StaffGitlet branch A

echo "FI: Luckily we are still alive" > StafftestFiles/FI.txt
java StaffGitlet rm StafftestFiles/PII.txt
java StaffGitlet add StafftestFiles/FI.txt
echo "FI"
java StaffGitlet commit FI

echo "QI: I am who is still living" > StafftestFiles/QI.txt
echo "yes" | java StaffGitlet checkout A 
java StaffGitlet add StafftestFiles/QI.txt
# change common2
echo "commmon2: a small group of people survived and we are making a new game for product!" > StafftestFiles/common2.txt
java StaffGitlet add StafftestFiles/common2.txt
echo "QI"
java StaffGitlet commit QI

java StaffGitlet branch B

java StaffGitlet rm StafftestFiles/common2.txt
echo "AI"
java StaffGitlet commit AI

java StaffGitlet rm StafftestFiles/common3.txt
# change common1
echo "commmon1: funding stage arrived! we are hiring.." > StafftestFiles/common1.txt
java StaffGitlet add StafftestFiles/common1.txt
echo "AII"
java StaffGitlet commit AII

echo "AIII: sunny day!" > StafftestFiles/AIII.txt
java StaffGitlet add StafftestFiles/AIII.txt
echo "AIII"
java StaffGitlet commit AIII

echo "yes" | java StaffGitlet checkout B 
java StaffGitlet rm StafftestFiles/common1.txt
echo "BI"
java StaffGitlet commit BI

echo "BII: sleeping, huh? I guess this is the end of our branch!" > StafftestFiles/BII.txt
java StaffGitlet rm StafftestFiles/common2.txt
java StaffGitlet add StafftestFiles/BII.txt
echo "BII"
java StaffGitlet commit BII

java StaffGitlet add StafftestFiles/common1.txt
echo "BIII"
java StaffGitlet commit BIII

echo "building finished"


echo "Now testing merge case 1...Branch A and C"
echo "yes" | java StaffGitlet checkout A >/dev/null
echo "yes" | java StaffGitlet merge C >/dev/null
cat StafftestFiles/* > testResultsStaff/merge_1.txt
echo "There should be no conflicted files"

echo "Now testing merge case 2...Branch D and Master"
echo "yes" | java StaffGitlet checkout D >/dev/null
echo "yes" | java StaffGitlet merge master >/dev/null
cat StafftestFiles/* > testResultsStaff/merge_2.txt
echo "There should be no conflicted files"

echo "Now testing merge case 3...Branch D and E"
echo "yes" | java StaffGitlet checkout D >/dev/null
echo "yes" | java StaffGitlet merge E >/dev/null
cat StafftestFiles/* > testResultsStaff/merge_3.txt
echo "There should be no conflicted files"

echo "Now testing merge case 4...Branch B and C"
echo "yes" | java StaffGitlet checkout B >/dev/null
echo "yes" | java StaffGitlet merge C >/dev/null
cat StafftestFiles/* > testResultsStaff/merge_4.txt
echo "There should be no conflicted files"

echo "Now testing merge case 5...Branch B and master"
echo "yes" | java StaffGitlet checkout B >/dev/null
echo "yes" | java StaffGitlet merge master >/dev/null
cat StafftestFiles/* > testResultsStaff/merge_5.txt
echo "There should be conflicted files"
rm -r StafftestFiles/*.conflicted

echo "Now testing merge case 6...Branch A and B"
echo "yes" | java StaffGitlet checkout A >/dev/null
echo "yes" | java StaffGitlet merge B >/dev/null
cat StafftestFiles/* > testResultsStaff/merge_6.txt
echo "There should be conflicted files"
rm -r StafftestFiles/*.conflicted

echo "Now testing rebase..."
echo "Now testing rebasing case 1...Branch A to master"
echo "yes" | java StaffGitlet checkout A >/dev/null
echo "yes" | java StaffGitlet rebase master >/dev/null

cat StafftestFiles/* > testResultsStaff/rebase_1.txt

echo "Now testing rebasing case 2...Branch C to D"
echo "yes" | java StaffGitlet checkout C >/dev/null
echo "yes" | java StaffGitlet rebase D >/dev/null

cat StafftestFiles/* > testResultsStaff/rebase_2.txt

echo "Now testing rebasing case 3...Branch B to E"
echo "yes" | java StaffGitlet checkout B >/dev/null
echo "yes" | java StaffGitlet rebase E >/dev/null

cat StafftestFiles/* > testResultsStaff/rebase_3.txt

echo "Now testing rebasing case 4...Branch master to C"
echo "yes" | java StaffGitlet checkout master >/dev/null
echo "yes" | java StaffGitlet rebase C >/dev/null

cat StafftestFiles/* > testResultsStaff/rebase_4.txt



