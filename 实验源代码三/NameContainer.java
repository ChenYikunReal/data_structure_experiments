import java.util.LinkedList;
import java.util.Queue;

public class NameContainer {

    /**
     * 根结点
     */
	private Part hospital;

	/**
	 * 默认构造器
	 */
	public NameContainer() {}

	/**
	 * 单参数构造器
	 * @param hospital
	 */
	public NameContainer(Part hospital) {
		this.hospital = hospital;
	}

	public Part getHospital() {
		return hospital;
	}

	public void setHospital(Part hospital) {
		this.hospital = hospital;
	}

	public Part lookup(String name) {
		Queue<Part> queue = new LinkedList<>();
		Part current = hospital;
		if (current != null) {
			queue.offer(current);
		}
		while (!queue.isEmpty()) {
			current = queue.poll();
			if (current.getName().equals(name)) {
				return current;
			}
			for (Part p : current.getSubParts().keySet()) {
				queue.offer(p);
			}
		}
		return new Part(name);
	}
}
