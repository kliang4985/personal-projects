
public class BST<T extends Comparable<T>>{
	public Node root; 
	
	public class Node {
		Node left; 
		Node right;
		T data;
		public Node(T data) {
			left = null;
			right = null;
			this.data = data;
		}
	}
	
	public boolean find(T data) {
		boolean retVal = false;
		Node curr =  root;
		while (curr != null) { 
			if (curr.data == data) {
				retVal = true; 
			} else if (curr.data.compareTo(data)<0) {
				curr = curr.right;
				
			} else {
				curr = curr.left;
			}
		}
		return retVal; 
	}
	public void insertRecursive(T data) {
		root = insertHelper(root, data);
	}
	public Node insertHelper(Node root, T data) {
		if (root == null) {
			return new Node(data);
		} else if (data.compareTo(root.data) > 0) {
			root.right = insertHelper(root.right, data);
		} else if (data.compareTo(root.data) < 0) {
			root.left = insertHelper(root.left, data);
		} else { // if same
			return root; 
		}
		return root; 
	}
	public void delete(T data) {
		root = deleteHelper(root, data);
	}
	public Node deleteHelper(Node current, T data) {
		if (current == null) {
			return current;
		} 
		if (current.data.compareTo(data) == 0) {
			if (current.left == null && current.right == null) {
				return null;
			} else if (current.left == null || current.right == null) {
				if(current.left == null) {
					return current.right;
				} else {
					return current.left;
				}
			} else {
				Node min = findMin(current.right);//Find largest on left or smallest on right
				current.data = min.data;
				current.right = deleteHelper(current.right, data);
				return current;
			}
			
		} else if (current.data.compareTo(data) < 0) {
			current.right = deleteHelper(current.right, data);
		} else {
			current.left = deleteHelper(current.left, data);
		}
		return current;
	}
	public void preorder(Node current) {
		if (current != null) {
			
		}
	}
	public Node findMin(Node current) {
		if (current == null) {
			return null;
		} if(current.left == null) {
			return current;
		} else {
			return findMin(current.left);
		}
	}
	
	
}
