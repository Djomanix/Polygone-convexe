/**
 * Main of the demo
 * @author romis_000
 */
public class Session_SDD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Window w = new Window();
        Thread t1 = new Thread(w);
        t1.start();
    }
}
