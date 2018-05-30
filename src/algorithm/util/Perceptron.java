package algorithm.util;

import java.util.*;
import java.lang.Math;
import java.io.File;

import algorithm.fileOperations.*;

public class Perceptron
{
    Vector<File> files;            //Vector of files from a folder
    
    WordTree vocabulary;           //Holds all words
    WordTree stopWords;            //Holds all stop words
    WordTree vocabularyWords;      //Holds all words; no duplicates
    
    int numDocs;                   //Total number of documents
    double eta;
    int iterations;
    
    public Perceptron(double d, int x)
    {
        files = new Vector<File>();
        vocabulary = new WordTree();
        stopWords = new WordTree();
        vocabularyWords = new WordTree();
        numDocs = 0;
        eta = d;
        iterations = x;
    }
    
    //For the purposes of this homework, this method creates the class vector and the document vector from within
    public void trainAndTestPerceptron()
    {
        List<String> results = new ArrayList<String>();

        File[] files;
        
        File stopwords = new File("stopwords.txt");
        
        DataProcessor dp = new DataProcessor(stopwords);
        
        if (dp.canReadNext())
        {
            String temp = dp.readNext();
            temp = temp.replaceAll("[^A-Za-z]+", "");
            if (!temp.equals(""))
            {
                stopWords.setWord(new Word(temp));
            }
        }
        
        while (dp.canReadNext())
        {
            String temp = dp.readNext();
            temp = temp.replaceAll("[^A-Za-z]+", "");
            if (!temp.equals(""))
            {
                //The boolean value is moot because stopWords' ham and spam instances are never accessed.
                stopWords.addWord(temp, true);
            }
        }
        
        double bias = 1.0;
        
        while (iterations > 0)
        {
            System.gc();
            files = new File("src/algorithm/train/ham/").listFiles();
            for (File file : files)
            {
                numDocs++;
                dp = new DataProcessor(file);
                Vector<String> words = new Vector<String>();
                while (dp.canReadNext())
                {
                    String temp = dp.readNext();
                    temp = temp.replaceAll("[^A-Za-z]+", "");
                    
                    if (stopWords.findWord(temp) == 0 && !temp.equals(""))
                    {
                        vocabulary.addWord(temp, true);
                        words.add(temp);
                    }
                }
                // All words have been accounted for.
                // Let's determine perceptron output.
                double sumOfWeights = bias;
                
                for (int i = 0; i < words.size(); i++)
                {
                    sumOfWeights += vocabulary.getWord(words.get(i)).getWeight();
                }
                
                double p_output;
                if (sumOfWeights > 0)
                {
                    p_output = 1.0;
                }
                else
                {
                    p_output = -1.0;
                }
                
                // Now let's update all the weights.
                for (int i = 0; i < words.size(); i++)
                {
                    vocabulary.getWord(words.get(i)).setWeight(vocabulary.getWord(words.get(i)).getWeight() + (eta*(-1.0 - p_output)));
                }
                bias += (eta*(-1.0 - p_output));
            }
            
            files = new File("src/algorithm/train/spam/").listFiles();
            
            for (File file : files)
            {
                numDocs++;
                dp = new DataProcessor(file);
                Vector<String> words = new Vector<String>();
                while (dp.canReadNext())
                {
                    String temp = dp.readNext();
                    temp = temp.replaceAll("[^A-Za-z]+", "");
                    
                    if (stopWords.findWord(temp) == 0 && !temp.equals(""))
                    {
                        vocabulary.addWord(temp, true);
                        words.add(temp);
                    }
                }
                // All words have been accounted for.
                // Let's determine perceptron output.
                double sumOfWeights = 1.0; // Bias of 1.0
                
                for (int i = 0; i < words.size(); i++)
                {
                    sumOfWeights += vocabulary.getWord(words.get(i)).getWeight();
                }
                
                double p_output;
                if (sumOfWeights > 0)
                {
                    p_output = 1.0;
                }
                else
                {
                    p_output = -1.0;
                }
                
                // Now let's update all the weights.
                for (int i = 0; i < words.size(); i++)
                {
                    vocabulary.getWord(words.get(i)).setWeight(vocabulary.getWord(words.get(i)).getWeight() + (eta*(1.0 - p_output)));
                }
                bias += (eta*(-1.0 - p_output));
            }
            iterations--;
        }
        
        int totalWords = vocabulary.countWords();
        int totalUniqueWords = vocabulary.countUniqueWords();
        
        int numTests = 0;
        int numCorrect = 0;
        
        double score = bias;
        
        files = new File("src/algorithm/test/ham/").listFiles();
        
        Vector<String> testWords = new Vector<String>();
        
        for (File file : files)
        {
            score = bias;
            
            dp = new DataProcessor(file);
            while (dp.canReadNext())
            {
                String temp = dp.readNext();
                temp = temp.replaceAll("[^A-Za-z]+", "");
                
                if (stopWords.findWord(temp) == 0 && !temp.equals(""))
                {
                    testWords.addElement(temp);
                }
            }
            
            for (int i = 0; i < testWords.size(); i++)
            {
                if (vocabulary.findWord(testWords.get(i)) > 0)
                {
                    score += vocabulary.getWord(testWords.get(i)).getWeight();
                }
            }
            
            numTests++;
            if (score <= 0)
            {
                numCorrect++;
            }
            
            for (int i = 0; i < testWords.size(); i++)
            {
                if (vocabulary.findWord(testWords.get(i)) > 0)
                {
                    bias += vocabulary.getWord(testWords.get(i)).getWeight();
                }
            }
            
            testWords.removeAllElements();
        }
        
        files = new File("src/algorithm/test/spam/").listFiles();
        
        for (File file : files)
        {
            score = 1.0;
            
            dp = new DataProcessor(file);
            while (dp.canReadNext())
            {
                String temp = dp.readNext();
                temp = temp.replaceAll("[^A-Za-z]+", "");
                
                if (stopWords.findWord(temp) == 0 && !temp.equals(""))
                {
                    testWords.addElement(temp);
                }
            }
            
            for (int i = 0; i < testWords.size(); i++)
            {
                if (vocabulary.findWord(testWords.get(i)) > 0)
                {
                    score += vocabulary.getWord(testWords.get(i)).getWeight();
                }
            }
            
            numTests++;
            if (score > 0)
            {
                numCorrect++;
            }
            
            for (int i = 0; i < testWords.size(); i++)
            {
                if (vocabulary.findWord(testWords.get(i)) > 0)
                {
                    bias += vocabulary.getWord(testWords.get(i)).getWeight();
                }
            }
            
            testWords.removeAllElements();
        }
        
        double accuracyPerceptron = (((double)numCorrect)/((double)numTests));
        accuracyPerceptron = accuracyPerceptron * 100.0;
        System.out.println("The accuracy of the Perceptron training algorithm for text classification without stopWords is " + accuracyPerceptron + "%");
    }
}
