public class Service{
    
    private int distance;    
    
    private int weight;
    
    private String start_city;
    
    private String end_city;
    
    private Service link = null;     
    
    public Service () {}
    
    public Service(int d) {
        this.distance = d; 
    }   
    
    public Service (String sc,String ec, int w, int d){
        this.start_city = sc;
        this.distance = d;
        this.weight = w;
        this.end_city = ec;
    }
    
    public String getStart_city() {
        return start_city;
    }
    
    public void setStart_city(String start_city) {
        this.start_city = start_city;
    }
    
    public String getEnd_city() {
        return end_city;
    }
    
    public void setEnd_city(String end_city) {
        this.end_city = end_city;
    }
    
    public int getDistance() {
        return distance;
    }
    
    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public Service getLink() {
        return link;
    }
    
    public void setLink(Service link) {
        this.link = link;
    }  
    
}
