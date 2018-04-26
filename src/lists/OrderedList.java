package lists;
import java.math.*;
public class OrderedList<X extends Comparable> extends List<X> {
	public void add(X info) {
		int index = search(info)-1;
		
		if (index <= 0)
			first_node = new Node(info, first_node);
		else {
		Node n = getNode(index);
		n.setNext(new Node(info, n.getNext()));
		}
	}
	
	protected int search(X info) {
		boolean found = false;
		
		int last = this.size()-1;
		int middle = Math.floorDiv(last, 2);
		int first = 0;
		int compare_to = 0;
		
		while (first <= last && !found) {
			compare_to = this.get(middle).compareTo(info);
			if (compare_to > 0) {
				first = middle+1;
				middle = Math.floorDiv((last + first), 2);
			}
			else if (compare_to < 0){
				last = middle-1;
				middle = Math.floorDiv((last + first), 2);
			}
			else if (compare_to == 0) {
				found = true;
				return middle;
			}
		}
		
		return first;
	}
	
	public OrderedList<X> minus(OrderedList<X> l) throws IllegalArgumentException {
		if (l == null)
			throw new IllegalArgumentException("Null argument");
		
		OrderedList<X> ret = new OrderedList<X>();
		
		Node n = this.first_node;
		Node nl = l.first_node;
		
		while ((n != null) && (nl != null)) {
			X n_info = n.getInfo();
			X nl_info = nl.getInfo();
			
			int compare_to = n_info.compareTo(nl_info);
			
			if (compare_to < 0) {
				ret.add(n_info);
				n = n.getNext();
			}
			else if (compare_to > 0) {
				nl = nl.getNext();
			}
			else {
				n = n.getNext();
			}
		}
		
		return ret;
	}
}
