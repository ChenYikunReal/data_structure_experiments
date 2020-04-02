import static com.neu.structure.main.Constant.*;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static NameContainer NAME_CONTAINER = new NameContainer();
    
    private static Part HOSPITAL;

    private static void loadData(String fileName) {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            Part current = null;
            Part prev = null;
            while ((line = in.readLine()) != null) {
                String[] split = line.split(SPLIT_CHARACTER);
                String part_name = split[0];
                int sub_quantity = Integer.parseInt(split[1]);
                String sub_name = split[2];
                NAME_CONTAINER.setHospital(HOSPITAL);
                if (prev == null) {
                    prev = NAME_CONTAINER.lookup(part_name);
                    HOSPITAL = prev;
                } else {
                    prev = NAME_CONTAINER.lookup(part_name);
                }
                current = NAME_CONTAINER.lookup(sub_name);
                prev.getSubParts().put(current, sub_quantity);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void query(String fromFileName, String toFileName) {
        try (Scanner scanner = new Scanner(new FileReader(fromFileName));
             PrintWriter writer = new PrintWriter(new FileWriter(toFileName))) {
            String line = "";
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                String[] split = line.split(SPLIT_CHARACTER);
                if (split.length == 2) {
                    String name = split[1];
                    Part lookup = NAME_CONTAINER.lookup(name);
                    String describe = lookup.describe();
                    builder.append("whatis: \n").append(describe);
                } else if (split.length == 3) {
                    Part lookup = NAME_CONTAINER.lookup(split[2]);
                    int count = lookup.count_howmany(NAME_CONTAINER.lookup(split[1]));
                    builder.append("howmany: ").append(split[2]).append(" has ")
                        .append(count).append(" ").append(split[1]).append("\n");
                }
            }
            writer.println(builder.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadData(DEFINITIONS_FILE);
        query(QUERIES_FILE, RESULT_FILE);
    }
}
