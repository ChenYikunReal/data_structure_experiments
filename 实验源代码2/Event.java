public class Event {
    
    /**
     * 打印任务对象
     */
    private Job job;
    
    /**
     * 作业提交的时间(秒)
     */
    private int arrivalTime;

    public Event(Job job, int arrivalTime) {
        super();
        this.job = job;
        this.arrivalTime = arrivalTime;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "Event [job=" + job + ", arrivalTime=" + arrivalTime + "]";
    }

}
