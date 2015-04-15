#! /bin/bash

echo "now setting up your own Gitlet"
rm -rf .gitlet
rm -rf yourtestFiles
rm -rf testResultsYours
mkdir yourtestFiles
mkdir testResultsYours
echo "initializing..."
java Gitlet init
echo "building a complex enough Git..."

echo "PII: file that's very old" > yourtestFiles/PII.txt
echo "PI: file that's no one knows what it is" > yourtestFiles/PI.txt
echo "commmon1: Apple WATCH PRICE: 20,000USD. Are you going to buy?" > yourtestFiles/common1.txt
echo "commmon2: This Summer: 61C Class sucks" > yourtestFiles/common2.txt
echo "commmon3: Should You go to MIT instead?" > yourtestFiles/common3.txt

java Gitlet add yourtestFiles/PII.txt
java Gitlet add yourtestFiles/common1.txt
java Gitlet add yourtestFiles/common2.txt
java Gitlet add yourtestFiles/common3.txt
java Gitlet add yourtestFiles/PI.txt

echo "ORIGIN"
java Gitlet commit ORIGIN

java Gitlet branch F

java Gitlet rm yourtestFiles/PII.txt
# change common2.txt
echo "commmon2: This Summer: 61C Class rocks!" > yourtestFiles/common2.txt
java Gitlet add yourtestFiles/common2.txt

echo "PI"
java Gitlet commit PI

java Gitlet branch C

echo "GI: file that's miracle" > yourtestFiles/GI.txt
java Gitlet add yourtestFiles/GI.txt
echo "GI"
java Gitlet commit GI

echo "GII: file that's changing everything" > yourtestFiles/GII.txt
java Gitlet add yourtestFiles/GII.txt
echo "commmon1: Apple WATCH PRICE: 20,000USD. Buy the cheap one you idiot!" > yourtestFiles/common1.txt
# change common1.txt
java Gitlet add yourtestFiles/common1.txt
echo "GII"
java Gitlet commit GII

java Gitlet branch D

java Gitlet rm yourtestFiles/common3.txt
echo "MI"
java Gitlet commit MI

echo "MII: file that's improving over G" > yourtestFiles/MII.txt
java Gitlet add yourtestFiles/MII.txt
echo "MII"
java Gitlet commit MII

java Gitlet add yourtestFiles/common3.txt
echo "MIII"
java Gitlet commit MIII

echo "HI: I added a new app to this pool" > yourtestFiles/HI.txt
echo "yes" | java Gitlet checkout D 
java Gitlet add yourtestFiles/HI.txt
echo "HI"
java Gitlet commit HI
# change common2.txt
echo "commmon2: This Summer: 61C Don't need it any more , take the OS class instead!" > yourtestFiles/common2.txt
java Gitlet add yourtestFiles/common2.txt
echo "HII"
java Gitlet commit HII

echo "DI: Revenue increased by this new feature!" > yourtestFiles/DI.txt
java Gitlet branch E
java Gitlet add yourtestFiles/DI.txt
java Gitlet rm yourtestFiles/common2.txt
echo "DI"
java Gitlet commit DI

echo "EI: ok we overestimated the revenue" > yourtestFiles/EI.txt
echo "yes" | java Gitlet checkout E 
java Gitlet add yourtestFiles/EI.txt
echo "EI"
java Gitlet commit EI

echo "CI: this is a garbage branch. Level 1" > yourtestFiles/CI.txt
echo "yes" | java Gitlet checkout C 
java Gitlet add yourtestFiles/CI.txt
echo "CI"
java Gitlet commit CI

java Gitlet rm yourtestFiles/common2.txt
echo "CII"
java Gitlet commit CII

java Gitlet add yourtestFiles/common2.txt
echo "CIII"
java Gitlet commit CIII

echo "CIV: this is a 'garbage' branch. Level 4" > yourtestFiles/CIV.txt
java Gitlet add yourtestFiles/CIV.txt
echo "CIV"
java Gitlet commit CIV

echo "yes" | java Gitlet checkout F 
java Gitlet rm yourtestFiles/PI.txt

echo "commmon1: We go bankrupt, no Apple Watches" > yourtestFiles/common1.txt
echo "commmon2: the department is shutting" > yourtestFiles/common2.txt
echo "commmon3: find jobs in Microsoft now." > yourtestFiles/common3.txt
java Gitlet add yourtestFiles/common1.txt
java Gitlet add yourtestFiles/common2.txt
java Gitlet add yourtestFiles/common3.txt
echo "PII"
java Gitlet commit PII

java Gitlet branch A

echo "FI: Luckily we are still alive" > yourtestFiles/FI.txt
java Gitlet rm yourtestFiles/PII.txt
java Gitlet add yourtestFiles/FI.txt
echo "FI"
java Gitlet commit FI

echo "QI: I am who is still living" > yourtestFiles/QI.txt
echo "yes" | java Gitlet checkout A 
java Gitlet add yourtestFiles/QI.txt
# change common2
echo "commmon2: a small group of people survived and we are making a new game for product!" > yourtestFiles/common2.txt
java Gitlet add yourtestFiles/common2.txt
echo "QI"
java Gitlet commit QI

java Gitlet branch B

java Gitlet rm yourtestFiles/common2.txt
echo "AI"
java Gitlet commit AI

java Gitlet rm yourtestFiles/common3.txt
# change common1
echo "commmon1: funding stage arrived! we are hiring.." > yourtestFiles/common1.txt
java Gitlet add yourtestFiles/common1.txt
echo "AII"
java Gitlet commit AII

echo "AIII: sunny day!" > yourtestFiles/AIII.txt
java Gitlet add yourtestFiles/AIII.txt
echo "AIII"
java Gitlet commit AIII

echo "yes" | java Gitlet checkout B 
java Gitlet rm yourtestFiles/common1.txt
echo "BI"
java Gitlet commit BI

echo "BII: sleeping, huh? I guess this is the end of our branch!" > yourtestFiles/BII.txt
java Gitlet rm yourtestFiles/common2.txt
java Gitlet add yourtestFiles/BII.txt
echo "BII"
java Gitlet commit BII

java Gitlet add yourtestFiles/common1.txt
echo "BIII"
java Gitlet commit BIII

echo "building finished"


echo "Now testing merge case 1...Branch A and C"
echo "yes" | java Gitlet checkout A >/dev/null
echo "yes" | java Gitlet merge C >/dev/null
cat yourtestFiles/* > testResultsYours/merge_1.txt
echo "There should be no conflicted files"

echo "Now testing merge case 2...Branch D and Master"
echo "yes" | java Gitlet checkout D >/dev/null
echo "yes" | java Gitlet merge master >/dev/null
cat yourtestFiles/* > testResultsYours/merge_2.txt
echo "There should be no conflicted files"

echo "Now testing merge case 3...Branch D and E"
echo "yes" | java Gitlet checkout D >/dev/null
echo "yes" | java Gitlet merge E >/dev/null
cat yourtestFiles/* > testResultsYours/merge_3.txt
echo "There should be no conflicted files"

echo "Now testing merge case 4...Branch B and C"
echo "yes" | java Gitlet checkout B >/dev/null
echo "yes" | java Gitlet merge C >/dev/null
cat yourtestFiles/* > testResultsYours/merge_4.txt
echo "There should be no conflicted files"

echo "Now testing merge case 5...Branch B and master"
echo "yes" | java Gitlet checkout B >/dev/null
echo "yes" | java Gitlet merge master >/dev/null
cat yourtestFiles/* > testResultsYours/merge_5.txt
echo "There should be conflicted files"
rm -r yourtestFiles/*.conflicted

echo "Now testing merge case 6...Branch A and B"
echo "yes" | java Gitlet checkout A >/dev/null
echo "yes" | java Gitlet merge B >/dev/null
cat yourtestFiles/* > testResultsYours/merge_6.txt
echo "There should be conflicted files"
rm -r yourtestFiles/*.conflicted

echo "Now testing rebase..."
echo "Now testing rebasing case 1...Branch A to master"
echo "yes" | java Gitlet checkout A >/dev/null
echo "yes" | java Gitlet rebase master >/dev/null

cat yourtestFiles/* > testResultsYours/rebase_1.txt

echo "Now testing rebasing case 2...Branch C to D"
echo "yes" | java Gitlet checkout C >/dev/null
echo "yes" | java Gitlet rebase D >/dev/null

cat yourtestFiles/* > testResultsYours/rebase_2.txt

echo "Now testing rebasing case 3...Branch B to E"
echo "yes" | java Gitlet checkout B >/dev/null
echo "yes" | java Gitlet rebase E >/dev/null

cat yourtestFiles/* > testResultsYours/rebase_3.txt

echo "Now testing rebasing case 4...Branch master to C"
echo "yes" | java Gitlet checkout master >/dev/null
echo "yes" | java Gitlet rebase C >/dev/null

cat yourtestFiles/* > testResultsYours/rebase_4.txt



