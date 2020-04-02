import static com.neu.structure.core.Constant.*;

public class Main {

    public static void main(String[] args) {
        Fifo fifo = new Fifo(2);
        fifo.loadWorkLoad(BIGFIRST_RUN);
        fifo.simulate(RESULT);
    }

}
