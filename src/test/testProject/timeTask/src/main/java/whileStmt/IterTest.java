package whileStmt;

public class IterTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() - start < 2000);

        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}