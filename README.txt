CS436 Intro to Machine Learning
Spring 2016
HOMEWORK 4 README FILE

Due Date: Sunday, April 1, 2018
Submission Date: Sunday, April 1, 2018
Author(s): Christian Lolis
e-mail(s): clolis1@binghamton.edu


PURPOSE:

  This project classifies a test set of word files and decides whether they are
  "spam" or "ham" based on a Perceptron model.

PERCENT COMPLETE:

  I was responsible for the whole project. To my knowledge, it is 100% complete.

PARTS THAT ARE NOT COMPLETE:

  There are no parts that are incomplete.

BUGS:

  I believe none.

FILES:

  Included with this project are 7 files and 2 folders:
  
  build.xml, the file that helps ant compile and run the program
  Driver.java, the main file associated with the program and also contains main
  DataProcessor.java, the file responsible for parsing the input files
  Perceptron.java, the file containing the Perceptron Training algorithm
  PerceptronWithStopWords.java, the file containing the Perceptron Training algorithm that uses stopWords
  Word.java, The file that helps manage the contents of the documents
  [Folders] test and train, which contain around ~1300 documents to be parsed
  README.txt, the file that you are currently reading.

SAMPLE OUTPUT:

clolis1:~/workspace/lolis_christian_homework_4/algorithm $ ant -buildfile src/build.xml run -Darg0=0.1 -Darg1=10
Buildfile: /home/ubuntu/workspace/lolis_christian_homework_4/algorithm/src/build.xml

jar:
      [jar] Building jar: /home/ubuntu/workspace/lolis_christian_homework_4/algorithm/BUILD/jar/algorithm.jar

run:
     [java] The accuracy of the Perceptron training algorithm for text classification without stopWords is 94.56066945606695%
     [java] The accuracy of the Perceptron training algorithm for text classification with stopWords is 97.90794979079497%

BUILD SUCCESSFUL
Total time: 7 seconds  

TO COMPILE:
  
  gunzip lolis_christian_homework_4.tar.gz
  tar -zxvf lolis_christian_homework_4.tar
  cd lolis_christian_homework_4/algorithm
  ant -buildfile src/build.xml all

TO RUN:

  ant -buildfile src/build.xml run -Darg0=[eta] -Darg1=[iterations]
  /** No brackets when actually running program **/
  
EXTRA CREDIT:

  N/A

BIBLIOGRAPHY:

  I have done this assignment completely on my own. I have not copied it, nor
  have I given my solution to anyone else. I understand that if I am involved
  in plagiarism or cheating I will have to sign an official form that I have
  cheated and that this form will be stored in my official university record.
  I also understand that I will receive a grade of 0 for the involved assignment
  for my first offense and that I will receive a grade of “F” for the course for
  any additional offense.

ACKNOWLEDGEMENT:

  None.
  
NOTE:

  None.