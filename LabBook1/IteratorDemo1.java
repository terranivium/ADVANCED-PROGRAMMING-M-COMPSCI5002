import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
 
public class IteratorDemo1{
 
  public static void main(String args[]){
    ArrayList<Double> rndNum = new ArrayList<Double>();
    
    Random r = new Random();

    for(int i=0;i<20;i++){
      Double input1 = r.nextDouble() - 0.5;
      if(input1 >= 0){
        rndNum.add(input1);
      }  
    }    
 
    Iterator<Double> goThrough = rndNum.iterator();
 
    while(goThrough.hasNext()){
      Double obj = goThrough.next();
      System.out.println(obj);
    }
  }
}