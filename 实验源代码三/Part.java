import java.util.*;

public class Part {

    /**
     * 名称
     */
	private String name;
	
	/**
	 * 存储所有子结点及其对应数量
	 */
	private Map<Part, Integer> subParts;

	/**
	 * 选用HashMap，更快更高效
	 * @param name
	 */
	public Part(String name) {
		this.name = name;
		subParts = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public Map<Part, Integer> getSubParts() {
		return subParts;
	}

	public String describe() {
		StringBuilder builder = new StringBuilder();
		if (subParts.size() == 0) {
		    builder.append("There are no subparts in ").append(name);
			return builder.toString();
		}
		builder.append("Part ").append(name).append(" subparts are: \n");
		for (Map.Entry<Part, Integer> entry : subParts.entrySet()) {
		    builder.append("    ").append(entry.getValue()).append(" ")
		        .append(entry.getKey().getName()).append("\n");
		}
		return builder.toString();
	}
//
//	/**
//	 * count_howmany 的非递归实现
//	 * @param target
//	 * @return
//	 */
//	public int count_howmany2(Part target) {
//		Queue<Part> queue = new LinkedList<>();
//		Queue<Integer> visited = new LinkedList<>();
//		Part current = null;
//		queue.offer(this);
//		while (!queue.isEmpty()) {
//			current = queue.poll();
//			for (Map.Entry<Part, Integer> entry : current.getSubParts().entrySet()) {
//				if (target.getName().equals(entry.getKey().getName())) {
//					visited.offer(entry.getValue());
//					break;
//				} else if (contains(target, entry.getKey())) {
//					queue.offer(entry.getKey());
//					visited.offer(entry.getValue());
//				}
//			}
//		}
//		if (visited.isEmpty()) {
//			return 0;
//		}
//		int result = 1;
//		while (!visited.isEmpty()) {
//		    result *= visited.poll();
//		}
//		return result;
//	}

	public int count_howmany(Part target) {
		return count_howmany(target, this);
	}

	private int count_howmany(Part target, Part part) {
		int result = 0;
		for (Part p : part.subParts.keySet()) {
			if (p.getName().equals(target.getName())) {
			    result = part.subParts.get(p);
			} else {
				int i =  part.subParts.get(p) * count_howmany(target, p);
				if (i > 0) {
				    result = i;
				}
			}
		}
		return result;
	}

	public void add_part(String x, int quantity, String y) {
		Part lookup = new NameContainer(this).lookup(x);
		lookup.getSubParts().put(new Part(y), quantity);
	}

//	/**
//	 * 非递归辅助算法
//	 * @param target
//	 * @param p
//	 * @return
//	 */
//	private boolean contains(Part target, Part p) {
//		Queue<Part> queue = new LinkedList<>();
//		Part current = p;
//		queue.offer(current);
//		while (!queue.isEmpty()) {
//			current = queue.poll();
//			for (Part part : current.getSubParts().keySet()) {
//				if (part.getName().equals(target.getName())) {
//					return true;
//				} else {
//					queue.offer(part);
//				}
//			}
//		}
//		return false;
//	}

}
