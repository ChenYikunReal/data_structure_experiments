import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

public class RailSystem {
    
    private static Map<String, City> CITIES = new HashMap<>();
    
    private final static int MAX_NUM_OF_VERTEXS = 30;
    
    private static int NUM_OF_VERTEXS;

    /**
     * 得到顶点的数目
     * @return
     */
    public int getNumOfCity() {
        return NUM_OF_VERTEXS;
    }

    /**
     * 插入顶点
     * @param c1
     * @return
     */
    public static City insertCity(String c1) {      
        if (NUM_OF_VERTEXS >= MAX_NUM_OF_VERTEXS)      
            return null;
        NUM_OF_VERTEXS++;
        City v = new City(c1);
        CITIES.put(c1, v);
        return v;
        }

    /**
     * 插入边
     * @param c1
     * @param c2
     * @param weight
     * @param distance
     */
    public static void insertEdge(String c1, String c2, int weight,int distance) {  
         Service vex1 = new Service(c1,c2,weight,distance);
         if(!CITIES.containsKey(c1)) {
             insertCity(c1);
             CITIES.get(c1).setService(vex1);
         }
         // 索引为index1的顶点有邻接顶点       
         else if(CITIES.containsKey(c1)){            
             vex1.setLink(CITIES.get(c1).getService());      
             CITIES.get(c1).setService(vex1);
         }               
    }

    /**
     * 加载文件，构建图
     * @param address
     * @throws IOException
     */
    public static void load_services(String address) throws IOException {
        String str = "";
        StringTokenizer st;
        FileInputStream fis = new FileInputStream(address);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        while ((str = br.readLine()) != null) {
             st = new StringTokenizer(str, " ");
             insertEdge(st.nextToken(),st.nextToken(),Integer.valueOf(st.nextToken()),
                     Integer.valueOf(st.nextToken()));
         }
         br.close();
         isr.close();
         fis.close();
    }

    /**
     * 重新设计交通图
     * @param address
     * @throws IOException
     */
    public void reset(String address) throws IOException {
        CITIES.clear();
        load_services(address);
    }

    /**
     * Dijkstra算法得到最短路径
     * @param name
     */
    public static void calc_route(String name) {
        if(!CITIES.containsKey(name)) {
            System.out.println("不存在"+name+"城市！");
        }
        City begin_city = CITIES.get(name);
        //初始化路程和费用
        Iterator<String> iter = CITIES.keySet().iterator();
        ArrayList<String> allcity = new ArrayList<String>();
        while (iter.hasNext()) { 
            allcity.add(iter.next());
        }
        for (int i = 0; i < allcity.size(); i++) { 
            CITIES.get(allcity.get(i)).setTotal_fee(Integer.MAX_VALUE);
            CITIES.get(allcity.get(i)).setFrom_city(name);
        }
        Service current = begin_city.getService();
        while (current != null) {
            CITIES.get(current.getEnd_city()).setTotal_distance(current.getDistance());
            CITIES.get(current.getEnd_city()).setTotal_fee(current.getWeight());
            current = current.getLink();
        }
        begin_city.setTotal_fee(0);
        begin_city.setTotal_distance(0);
        begin_city.setVisited(true);
        // 处理从源点到其余顶点的最短路径
        for (int j = 0; j < allcity.size(); j++) {
            int min = Integer.MAX_VALUE;
            int mind = Integer.MAX_VALUE;
            String closest_city = null;
            // 比较从源点到其余顶点的路径长度
            for (int ji = 0; ji < allcity.size(); ji++) {
                // 从源点到j顶点的最短路径还没有找到
                if (CITIES.get(allcity.get(ji)).isVisited() == false && 
                        CITIES.get(allcity.get(ji)).getTotal_fee() < min) {
                    closest_city = CITIES.get(allcity.get(ji)).getName();
                    min = CITIES.get(allcity.get(ji)).getTotal_fee();
                    mind = CITIES.get(allcity.get(ji)).getTotal_distance();
                }
            }
            // 找到源点到索引为index顶点的最短路径长度
            if (closest_city != null) {
                CITIES.get(closest_city).setVisited(true);
                current = CITIES.get(closest_city).getService();
            }
            // 更新当前最短路径及距离
            for (int jii = 0; jii < allcity.size(); jii++) {
                while (current != null) {
                    if (!CITIES.get(current.getEnd_city()).isVisited() && 
                            (min + current.getWeight()) < CITIES.get(current.getEnd_city()).getTotal_fee()) {
                        CITIES.get(current.getEnd_city()).setTotal_fee(min + current.getWeight());
                        CITIES.get(current.getEnd_city()).setTotal_distance(mind + current.getDistance());
                        CITIES.get(current.getEnd_city()).setFrom_city(closest_city);
                    }
                    current = current.getLink();            
                }
            }
        }
    }

    /**
     * 输出最便宜结果
     * @param c1
     * @param c2
     */
    public static void recover_route(String c1,String c2) {
        Stack<String> st = new Stack<String>();
        st.push(c2);
        int i = 1;
        String cn = CITIES.get(c2).getFrom_city();
        st.push(cn);
        while(!cn.equals(c1)){
            i++;
            cn = CITIES.get(cn).getFrom_city();
            st.push(cn);
        }
        String roote = st.pop();
        for(int j = 0;j<i; j++) {
            roote = roote + " to " + st.pop();
        }
        System.out.println(roote+"\n");
    }
    
    public static void main(String[] args) throws IOException {
        load_services("src/com/neu/structure/resources/services.txt");
        System.out.println("Enter a start and destination city：<'quit' to exit>");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        StringTokenizer st;
        while(!str.startsWith("quit")){
            st = new StringTokenizer(str, " ");
            String c1 = st.nextToken();
            String c2 = st.nextToken();
            calc_route(c1);
            System.out.println("The cheapest route from " + c1 + " to " +c2+"\n");
            System.out.println("costs "+ CITIES.get(c2).getTotal_fee() +" euros"+
                    " and spans " + CITIES.get(c2).getTotal_distance() +" kilometers"+"\n");
            recover_route(c1,c2);
            System.out.println("Enter a start and destination city：<'quit' to exit>");
            str = br.readLine();
        } 
        System.out.println("你输入了\"quit\"，程序已经退出！");
        br.close();
    }

}
