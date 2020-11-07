public class Job {
    
    /**
     * 使用者(打印机)
     */
    private String user;
    
    /**
     * 打印页数
     */
    private int numberOfPages;

    public Job(String user, int numberOfPages) {
        super();
        this.user = user;
        this.numberOfPages = numberOfPages;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

}
