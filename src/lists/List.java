package lists;

//import lists.Node;
import java.lang.reflect.*;
import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;

public class List<X> implements Cloneable {
	protected Node first_node;

	
	protected X cloneOfX (X x)
    {
        X ret = null;

        try
        {
            Class<?> classe = x.getClass();
            Class<?>[] tipoDoParametroFormal = null; // pq clone tem 0 parametros
            Method metodo = classe.getMethod ("clone", tipoDoParametroFormal);
            Object[] parametroReal = null;// pq clone tem 0 parametros
            ret = ((X)metodo.invoke (x, parametroReal));
        }
        catch (NoSuchMethodException erro)
        {}
        catch (InvocationTargetException erro)
        {}
        catch (IllegalAccessException erro)
        {}

        return ret;
    }
	
	
	public X get(int index) throws IllegalArgumentException{
		if ((index < 0) || (index > this.size()-1))
			throw new IllegalArgumentException("Invalid index");
		
		return this.getNode(index).getInfo();
	}
	
	protected Node getNode(int index) {

		Node node = this.first_node;
		
		for (int i = 0; i < index; i++)
		{
			node = node.getNext();
		}
		
		return node;
	}
	
	public X get(X info) {
		boolean info_is_found = false;
		
		Node node = this.first_node;
		
		while (node.getNext() != null) {
			if (node.getInfo().equals(info))
				info_is_found = true;
			else
				node = node.getNext();
		}
		
		if ((info_is_found) || (node.getInfo().equals(info)))
			return node.getInfo();
		else
			return null;
	}
	
	
	
	public void clear() {
		this.first_node = null;
	}
	
	public int size() {
		
		if (this.empty())
			return 0;
		
		Node node = this.first_node;
		
		int i = 1;
		while (node.getNext() != null) {
			node = node.getNext();
			i++;
		}
		
		return i;
	}
	
	public boolean empty() {
		return first_node == null;
	}
	
	public void removeFirst() {
		this.first_node = this.first_node.getNext();
	}
	
	public void remove(int index) throws IllegalArgumentException{
		if ((index < 0) || (index > this.size()-1))
			throw new IllegalArgumentException("Invalid index");
		
		if (index == 0) {
			this.removeFirst();
			return;
		}
		
		Node pNode = this.getNode(index-1);
		pNode.setNext(pNode.getNext().getNext());
	}
	
	public void removeLast() {
		this.remove(this.size()-1);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first_node == null) ? 0 : first_node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		List other = (List) obj;
		if (first_node == null) {
			if (other.first_node != null)
				return false;
		} else if (!first_node.equals(other.first_node))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		if (this.first_node == null)
			return "{}";
		
		String ret = "{";

		Node node = this.first_node;		
		
		ret += node.getInfo() + (node.getNext() != null ? ", " : "");
		
		while (node.getNext() != null) {
			if (node.getNext() != null) {
				node = node.getNext();
				if (node.getNext() != null)
					ret += node.getInfo().toString() + ", ";
				else
					ret += node.getInfo().toString();
			}
				
		}
		
		ret += "}";
		return ret;
	}
	
	public List<X> clone(){
		List<X> ret = new List();
		ret.first_node = this.first_node.clone();
		return ret;
	}

	public class Node implements Cloneable{
		private X info;
		private Node next;
		
		public Node(X info) throws IllegalArgumentException{
			if (info == null)
				throw new IllegalArgumentException("Null item cannot be added.");
			
			this.info = info;
		}
		

		protected Node(X info, Node next) throws IllegalArgumentException{
			if (info == null)
				throw new IllegalArgumentException("Null item cannot be added.");
			
			this.info = info;
			this.next = next;
		}
		
		public X getInfo() {
			return info;
		}
		
		public void setInfo(X info) {
			this.info = info;
		}
		
		public Node getNext() {
			return next;
		}
		
		public void setNext(Node next) {
			this.next = next;
		}
		
		@Override
		public String toString() {
			//return "Node [info=" + info + ", next=" + next + "]";
			return "Node [" + info + "]";
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((info == null) ? 0 : info.hashCode());
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			
			if (obj == null)
				return false;
			
			if (getClass() != obj.getClass())
				return false;
			
			Node other = (Node) obj;
			
			if (info == null) {
				if (other.info != null)
					return false;
				
			} else if (!info.equals(other.info))
				return false;
			
			if (next == null) {
				if (other.next != null)
					return false;
				
			} else if (!next.equals(other.next))
				return false;
			
			return true;
		}
		
		protected X cloneOfX (X x)
	    {
	        X ret = null;

	        try
	        {
	            Class<?> classe = x.getClass();
	            Class<?>[] tipoDoParametroFormal = null; // pq clone tem 0 parametros
	            Method metodo = classe.getMethod ("clone", tipoDoParametroFormal);
	            Object[] parametroReal = null;// pq clone tem 0 parametros
	            ret = ((X)metodo.invoke (x, parametroReal));
	        }
	        catch (NoSuchMethodException erro)
	        {}
	        catch (InvocationTargetException erro)
	        {}
	        catch (IllegalAccessException erro)
	        {}

	        return ret;
	    }
		
		public Node clone() {
			Node ret;
			X info;
			Node next = null;
			
			if (this.info instanceof Cloneable)
				info = cloneOfX(this.info);
			else
				info = this.info;
			
			if (this.getNext() != null) 
				next = this.getNext().clone();
			
			ret = new Node(info, next);
			return ret;
		}
	}
}
