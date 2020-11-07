import static com.neu.structure.core.Constant.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import com.neu.structure.queue.Queue;

public abstract class Simulator {

    /**
     * 每张纸打印的时间，计算得到是2，但是为了保持可扩展性，还是没有规定死
     */
    private int seconds_per_page;
    
    /**
     * 工作队列
     */
    private Queue<Event> workload;
    
    /**
     * 等待队列
     */
    private Queue<Event> waiting;

    /**
     * 根据Java语法，该抽象类不能实例化，构造器只用于子类继承使用
     * @param seconds_per_page
     */
    public Simulator(int seconds_per_page) {
        workload = new Queue<>();
        waiting = new Queue<>();
        this.seconds_per_page = seconds_per_page;
    }

    public int getSeconds_per_page() {
        return seconds_per_page;
    }

    public void setSeconds_per_page(int seconds_per_page) {
        this.seconds_per_page = seconds_per_page;
    }

    public Queue<Event> getWorkload() {
        return workload;
    }

    public Queue<Event> getWaiting() {
        return waiting;
    }

    public void loadWorkLoad(String fileName) {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = in.readLine()) != null) {
                String[] split = line.split(SPLIT_CHARACTER);
                Job job = new Job(split[2], Integer.parseInt(split[1]));
                Event event = new Event(job, Integer.parseInt(split[0]));
                workload.enQueue(event);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 指定的抽象方法
     * @param fileName
     */
    public abstract void simulate(String fileName);

}