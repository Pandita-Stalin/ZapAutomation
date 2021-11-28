package Utils;

public class Waiting {

    public static int time(int number) {
        try {
            Thread.sleep(number);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return number;
    }
}
