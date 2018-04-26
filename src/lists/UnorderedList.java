package lists;

//import lists.Node;
import java.lang.reflect.*;

import lists.List.Node;

import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;

public class UnorderedList<X> extends List<X> {
	
	public void addAsFirst (X item) throws IllegalArgumentException{
		if (item == null)
			throw new IllegalArgumentException("Null item cannot be added.");
		
		this.first_node = new Node(item, first_node);
	}
	
	public void addAsLast (X item) throws IllegalArgumentException{
		if (item == null)
			throw new IllegalArgumentException("Null item cannot be added.");
		
		Node next_node = this.first_node;
		
		if (this.first_node == null) {
			this.addAsFirst(item);
		}
		else {
			while (next_node.getNext() != null)
				next_node = next_node.getNext();
			
			next_node.setNext(new Node(item, null));
		}
	}
	
	public void addAtIndex(int index, X item) throws IllegalArgumentException{
		
		if (item == null)
			throw new IllegalArgumentException("Null item cannot be added.");
		
		int size = this.size();
		
		//Index is the last valid index plus 1
		if (size == index) //If (size-1 +1 == index)
			this.addAsLast(item);

		//Index is bigger than the last valid index plus 1
		else if (size < index) //If (size-1 +1< index)
			throw new IndexOutOfBoundsException("Invalid index.");
		
		//Index is below the first valid index
		else if (index < 0)
			throw new IndexOutOfBoundsException("Invalid index.");
		
		//Index is the first valid index
		else if (index == 0) {
			this.addAsFirst(item);
		}
		
		//Index is between the end and the beginning of the list
		else {
			Node previousNode = this.getNode(index-1);
			previousNode.setNext(new Node(item, previousNode.getNext()));
		}
		
	}
	
}

