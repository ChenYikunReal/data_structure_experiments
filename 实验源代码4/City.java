public class City {
    
    private String name;
    
    private String from_city;
    
    private int total_fee;   
    
    private int total_distance;
    
    private boolean visited;
    
    private Service service;
    
    public City(String name) {
        this.name = name;
        service = null;
        visited = false;
    }
    
    public City(String name,String from_city,int total_fee,int total_distance,boolean visited,Service service){
        this.name = name;
        this.from_city = from_city;
        this.total_fee = total_fee;
        this.total_distance = total_distance;
        this.visited = visited;
        this.service = service;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getFrom_city() {
        return from_city;
    }
    
    public void setFrom_city(String from_city) {
        this.from_city = from_city;
    }
    
    public int getTotal_fee() {
        return total_fee;
    }
    
    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }
    
    public int getTotal_distance() {
        return total_distance;
    }
    
    public void setTotal_distance(int total_distance) {
        this.total_distance = total_distance;
    }
    
    public boolean isVisited() {
        return visited;
    }
    
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "City [name=" + name + "]";
    }
    
    
}
