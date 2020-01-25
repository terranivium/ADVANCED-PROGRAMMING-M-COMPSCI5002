public class MaxFinderThreads{

	private static final int nRows = 10;
	private static final int nCols = 5;
	private static Double[] results;
	private static Thread[] threads;

	public static void main(String[] args){
		Double[][] randArray = create2DRandArray();
		results = new Double[10];
		threads = new Thread[10];

		for(int i=0;i<nRows;i++){
			threads[i] = new Thread(new MaxValueInstance(randArray[i], results, i));
			threads[i].start();
			try{
        		threads[i].join();
        	}catch(InterruptedException e){
        		e.printStackTrace();
        	}
		}
        // threads must complete before the following is printed
        System.out.println("-----------------------");
        System.out.println("PRINT END");
        System.out.println("-----------------------");

        for(Double result:results){
			System.out.println(result);
        }

        System.out.println("-----------------------");               
	}

	public static Double[][] create2DRandArray(){
		Double[][] randArray = new Double[nRows][nCols];
		for(int r=0;r<nRows;r++) {
			for(int c=0;c<nCols;c++) {
				randArray[r][c] = Math.random();
			}
		}
		return randArray;
	}
}