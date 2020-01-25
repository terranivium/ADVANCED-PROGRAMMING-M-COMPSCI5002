import java.lang.Runnable;

public class RunnableTest implements Runnable {
    public static void main(String[] args) {
        System.out.println("Inside : " + Thread.currentThread().getName());

        System.out.println("Creating Runnable1...");
        System.out.println("---------------------");
        Runnable runnable1 = new RunnableTest();

        System.out.println("Creating Thread1...");
        System.out.println("---------------------");
        Thread thread1 = new Thread(runnable1);

        System.out.println("Creating Runnable2...");
        System.out.println("---------------------");
        Runnable runnable2 = new RunnableTest();

        System.out.println("Creating Thread2...");
        System.out.println("---------------------");
        Thread thread2 = new Thread(runnable2);

        System.out.println("Starting Threads...");
        System.out.println("---------------------");
        thread1.start();
        thread2.start();
        
        try{
        	thread2.join();
        }catch(InterruptedException e){
        	e.printStackTrace();
        }

        // Both threads must complete before the following is printed
        System.out.println("-----------------------");
        System.out.println("PRINT END");
        System.out.println("-----------------------");

    }

    public void run() {
        try{
        	Thread.sleep(1500);
        	System.out.println("Inside : " + Thread.currentThread().getName());
        }catch(InterruptedException e){
        	e.printStackTrace();
        }
    }
}