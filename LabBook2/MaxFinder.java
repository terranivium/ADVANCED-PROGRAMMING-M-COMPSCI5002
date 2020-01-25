import java.lang.Runnable;

public class MaxFinder implements Runnable{
	public MaxFinder(){

	}

	public static void main(String[] args){
		Double[][] randArray = create2DRandArray();
		System.out.println(getMaxValue(randArray));
	}


	public static Double getMaxValue(Double[][] input){
		Double maxValue = input[0][0];
		for(int j=0;j<input.length;j++){
			for(int i=0;i<input[j].length;i++){
				if(input[j][i] > maxValue){
					maxValue=input[j][i];
				}
			}
		}
		return maxValue;
	}

	public static Double[][] create2DRandArray(){
		int nRows = 100;
		int nCols = 50;
		Double[][] randArray = new Double[nRows][nCols];
		for(int r=0;r<nRows;r++) {
			for(int c=0;c<nCols;c++) {
				randArray[r][c] = (Math.random() * 100);
			}
		}
		return randArray;
	}
}