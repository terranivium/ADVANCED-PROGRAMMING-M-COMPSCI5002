import java.lang.Runnable;

public class MaxValueInstance implements Runnable{

	Double[] randArray;
	Double[] results;
	int index;

	public MaxValueInstance(Double[] randArray, Double[] results, int index){
		this.randArray = randArray;
		this.results = results;
		this.index = index;
	}

	public void getMaxValue(){
		Double maxValue = this.randArray[0];
		for(int i=0;i<randArray.length;i++){
			if(randArray[i] > maxValue){
					maxValue=randArray[i];
			}
		}
		System.out.println(maxValue);
		this.results[this.index] = maxValue;
	}

	public void run() {
        System.out.println("Inside : " + Thread.currentThread().getName());
        this.getMaxValue();
    }
}