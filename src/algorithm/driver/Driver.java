package algorithm.driver;
import algorithm.util.*;
import algorithm.fileOperations.*;

import java.util.*;
import java.lang.Math;

public class Driver
{
    public static void main(String[] args)
    {
        Driver driver = new Driver();
        driver.checkArgs(args);
        
        Perceptron p = new Perceptron(Double.parseDouble(args[0]), Integer.parseInt(args[1]));
        p.trainAndTestPerceptron();
        
        PerceptronWithStopWords pw = new PerceptronWithStopWords(Double.parseDouble(args[0]), Integer.parseInt(args[1]));
        pw.trainAndTestPerceptron();
    }
    
    public void checkArgs(String[] args)
    {
        if(args.length != 2){
            System.err.println("ERROR: Please enter 2 argument. The first is the eta value. 0.1 is a good choice... The second is the number of iterations.");
			System.exit(-1);
        }
    }
}
