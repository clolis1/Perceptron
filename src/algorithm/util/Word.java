package algorithm.util;

import java.util.*;

public class Word
{
    String name;
    
    double hamCondProb;
    double spamCondProb;
    
    double weight;
    
    public Word(String nameIn)
    {
        name = nameIn;
        hamCondProb = 0.0;
        spamCondProb = 0.0;
        weight = 1.0;
    }
    
    public String getWord()
    {
        return name;
    }
    
    public void setHamCondProb(double x)
    {
        hamCondProb = x;
    }
    
    public void setSpamCondProb(double x)
    {
        spamCondProb = x;
    }
    
    public double getHamCondProb()
    {
        return hamCondProb;
    }
    
    public double getSpamCondProb()
    {
        return spamCondProb;
    }
    
    public double getWeight()
    {
        return this.weight;
    }
    
    public void setWeight(double newWeight)
    {
        this.weight = newWeight;
    }
}