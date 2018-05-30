package algorithm.util;

import java.util.*;

//This possess the same function as a vector but takes less time to sort through.

public class WordTree
{
    public Word thisWord;
    public WordTree earlierAlphabetically;
    public WordTree laterAlphabetically;
    public int numDupes;
    public int hamInstances;
    public int spamInstances;
    
    public WordTree()
    {
        thisWord = null;
        earlierAlphabetically = null;
        laterAlphabetically = null;
        numDupes = 0;
        hamInstances = 0;
        spamInstances = 0;
    }
    
    public WordTree(String s)
    {
        thisWord = new Word(s);
        earlierAlphabetically = null;
        laterAlphabetically = null;
        numDupes = 0;
        hamInstances = 0;
        spamInstances = 0;
    }
    
    public Word getWord()
    {
        return this.thisWord;
    }
    
    public void setWord(Word w)
    {
        thisWord = w;
    }
    
    public void setEarlierNode(WordTree w)
    {
        this.earlierAlphabetically = w;
    }
    
    public WordTree getEarlierNode()
    {
        return this.earlierAlphabetically;
    }
    
    public void setLaterNode(WordTree w)
    {
        this.laterAlphabetically = w;
    }
    
    public WordTree getLaterNode(WordTree w)
    {
        return this.laterAlphabetically;
    }
    
    //String to be added, and whether the word was found in Ham or Spam
    //False for Spam, True for Ham
    public void addWord(String s, boolean hamOrSpam)
    {
        if (this.thisWord == null)
        {
            thisWord = new Word(s);
            if (hamOrSpam)
            {
                hamInstances++;
            }
            else
            {
                spamInstances++;
            }
            return;
        }
        if (s.compareTo(thisWord.getWord()) > 0)
        {
            if (this.laterAlphabetically == null)
            {
                this.laterAlphabetically = new WordTree();
            }
            this.laterAlphabetically.addWord(s, hamOrSpam);
        }
        else if (s.compareTo(thisWord.getWord()) < 0)
        {
            if (this.earlierAlphabetically == null)
            {
                this.earlierAlphabetically = new WordTree();
            }
            this.earlierAlphabetically.addWord(s, hamOrSpam);
        }
        else
        {
            numDupes++;
            if (hamOrSpam)
            {
                hamInstances++;
            }
            else
            {
                spamInstances++;
            }
        }
    }
    
    //Returns 1 + numDupes if the given string is in the tree, otherwise returns 0
    public int findWord(String s)
    {
        if (s.compareTo(thisWord.getWord()) == 0)
        {
            return 1 + this.numDupes;
        }
        else if (s.compareTo(this.thisWord.getWord()) > 0)
        {
            if (this.laterAlphabetically == null)
            {
                return 0;
            }
            else
            {
                return this.laterAlphabetically.findWord(s);
            }
        }
        else
        {
            if (this.earlierAlphabetically == null)
            {
                return 0;
            }
            else
            {
                return this.earlierAlphabetically.findWord(s);
            }
        }
    }
    
    //Returns the Word object containing the specified string
    //Assumes word exists in the tree
    public Word getWord(String s)
    {
        if (s.compareTo(this.thisWord.getWord()) == 0)
        {
            return this.getWord();
        }
        else if (s.compareTo(this.thisWord.getWord()) > 0)
        {
            return laterAlphabetically.getWord(s);
        }
        else
        {
            return earlierAlphabetically.getWord(s);
        }
    }
    
    //Returns the number of words in the tree
    public int countWords()
    {
        int counter = 0;
        if (this.earlierAlphabetically != null)
        {
            counter += earlierAlphabetically.countWords();
        }
        counter++;
        counter += this.numDupes;
        if (this.laterAlphabetically != null)
        {
            counter += laterAlphabetically.countWords();
        }
        return counter;
    }
    
    public int countUniqueWords()
    {
        int counter = 0;
        if (this.earlierAlphabetically != null)
        {
            counter += earlierAlphabetically.countUniqueWords();
        }
        counter++;
        if (this.laterAlphabetically != null)
        {
            counter += laterAlphabetically.countUniqueWords();
        }
        return counter;
    }
    
    public void setConditionalProbabilities(int totalWords, int totalUniqueWords, int totalHamWords, int totalSpamWords)
    {
//System.out.println("Hello?");
        if (this.earlierAlphabetically != null && this.earlierAlphabetically.getWord() != null)
        {
            earlierAlphabetically.setConditionalProbabilities(totalWords, totalUniqueWords, totalHamWords, totalSpamWords);
        }
        
        double k = 1.0;
        
        double tempHam = ((double)this.hamInstances + k)/((double)totalHamWords + k * (double)totalUniqueWords);
        this.getWord().setHamCondProb(tempHam);
        
        double tempSpam = ((double)this.spamInstances + k)/((double)totalSpamWords + k * (double)totalUniqueWords);
        this.getWord().setSpamCondProb(tempSpam);
        
        if (this.laterAlphabetically != null && this.laterAlphabetically.getWord() != null)
        {
            laterAlphabetically.setConditionalProbabilities(totalWords, totalUniqueWords, totalHamWords, totalSpamWords);
        }
    }
    
    //Pre-requisite that the string argument must be in the word tree.
    public double getHamConditionalProbability(String s)
    {
        if (s.compareTo(thisWord.getWord()) > 0)
        {
            return laterAlphabetically.getHamConditionalProbability(s);
        }
        else if (s.compareTo(this.thisWord.getWord()) == 0)
        {
            return thisWord.getHamCondProb();
        }
        else
        {
            return earlierAlphabetically.getHamConditionalProbability(s);
        }
    }
    
    //Pre-requisite that the string argument must be in the word tree.
    public double getSpamConditionalProbability(String s)
    {
        if (s.compareTo(thisWord.getWord()) > 0)
        {
            return laterAlphabetically.getSpamConditionalProbability(s);
        }
        else if (s.compareTo(this.thisWord.getWord()) == 0)
        {
            return thisWord.getSpamCondProb();
        }
        else
        {
            return earlierAlphabetically.getSpamConditionalProbability(s);
        }
    }
    
    //Returns the weight of the word
    public double getWeight()
    {
        return thisWord.getWeight();
    }
    
    //Sets the weight of the word.
    public void setWeight(double newWeight)
    {
        thisWord.setWeight(newWeight);
    }
    
    public int getHamInstances()
    {
        return hamInstances;
    }
    
    public int getSpamInstances()
    {
        return spamInstances;
    }
}