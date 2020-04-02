import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class Fifo extends Simulator {

    public Fifo(int seconds_per_page) {
        super(seconds_per_page);
    }

    private static double getFloat(int var1, int var2) {
        DecimalFormat format = new DecimalFormat("0.0000");
        return Double.parseDouble(format.format((float) var1 / var2));
    }

    private int getRequireTime(int page_count) {
        return getSeconds_per_page() * page_count;
    }

    private boolean is_time_to_arrive(int currentTime, Event current) {
        return currentTime == current.getArrivalTime();
    }

    private void printOut(PrintWriter writer, Event event, int time, String kind) {
        writer.println("      " + kind + ": " + event.getJob().getNumberOfPages()
                + (event.getJob().getNumberOfPages() == 1 ?
                " page " : " pages ") + "from " + event.getJob().getUser() + 
                " at " + time + " seconds");
    }

    private int processWorkLoadQueue(int time, PrintWriter writer) {
        int temp = 0;
        Event current = getWorkload().getHead();
        while (current != null && is_time_to_arrive(time, current)) {
            printOut(writer, current, time, "Arriving");
            getWaiting().enQueue(getWorkload().deQueue());
            current = getWorkload().getHead();
            temp -= time;
        }
        return temp;
    }

    private int[] processWaitingQueue(PrintWriter writer, int time, int prev) {
        int[] result = new int[2];
        if (!getWaiting().isEmpty()) {
            Event waiting = getWaiting().getHead();
            if (prev == time) {
                printOut(writer, waiting, time, "Servicing");
                result[0] = time;
                result[1] = getRequireTime(waiting.getJob().getNumberOfPages());
                getWaiting().deQueue();
            }
        } else {
            result[1]++;
        }
        return result;
    }

    private void printHeading(PrintWriter writer) {
        writer.println("FIFO Simulation\n");
    }

    private void printEnding(PrintWriter writer, int totalCount, int aggregate) {
        writer.println("\n      Total jobs: " + totalCount);
        writer.println("      Aggregate latency: " + aggregate + " seconds");
        writer.println("      Mean latency: " + getFloat(aggregate, totalCount)
            + " seconds");
    }

    @Override
    public void simulate(String fileName) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            printHeading(out);
            int time = 0;
            int totalCount = getWorkload().size();
            int prev = 0;
            int aggregate = 0;
            while (!getWorkload().isEmpty() || !getWaiting().isEmpty()) {
                if (!getWorkload().isEmpty()) {
                    aggregate += processWorkLoadQueue(time, out);
                }
                int[] ints = processWaitingQueue(out, time, prev);
                aggregate += ints[0];
                prev += ints[1];
                time++;
            }
            printEnding(out, totalCount, aggregate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}