package Btree;

import java.util.Stack;

public class BTree {

	  private int T;

	  public class Node {
		  int degree;
		  int key[] = new int[2 * T - 1];
		  Node child[] = new Node[2 * T];
		  boolean leaf = true;

	    public int Find(int k) {
	    	for (int i = 0; i < this.degree; i++) {
	    		if (this.key[i] == k) {
	    			return i;
	    		}
	    	}
	    	return -1;
	    };
	  
	  }

	  public BTree(int t) {
		  T = t;
		  root = new Node();
		  root.degree = 0;
		  root.leaf = true;
	  }

	  private Node root;

	  private void split(Node x, int pos, Node y) {
		  Node z = new Node();
		  z.leaf = y.leaf;
		  z.degree = T - 1;
		  for (int j = 0; j < T - 1; j++) {
			  z.key[j] = y.key[j + T];
		  }
		  if (!y.leaf) {
			  for (int j = 0; j < T; j++) {
				  z.child[j] = y.child[j + T];
			  }
		  }
		  y.degree = T - 1;
		  for (int j = x.degree; j >= pos + 1; j--) {
			  x.child[j + 1] = x.child[j];
		  }
		  x.child[pos + 1] = z;

		  for (int j = x.degree - 1; j >= pos; j--) {
			  x.key[j + 1] = x.key[j];
		  }
		  x.key[pos] = y.key[T - 1];
		  x.degree = x.degree + 1;
	  }

	  public void insert(final int key) {
		  Node r = root;
		  if (r.degree == 2 * T - 1) {
			  Node s = new Node();
			  root = s;
			  s.leaf = false;
			  s.degree = 0;
			  s.child[0] = r;
			  split(s, 0, r);
			  _insert(s, key);
		  } else {
			  _insert(r, key);
		  }
	  }

	  final private void _insert(Node x, int k) {

	    if (x.leaf) {
	    	int i = 0;
	    	for (i = x.degree - 1; i >= 0 && k < x.key[i]; i--) {
	    		x.key[i + 1] = x.key[i];
	    	}
	    	x.key[i + 1] = k;
	    	x.degree = x.degree + 1;
	    	} else {
	    		int i = 0;
	    		for (i = x.degree - 1; i >= 0 && k < x.key[i]; i--) {
	    		}
	    		;
	    		i++;
	    		Node tmp = x.child[i];
	    		if (tmp.degree == 2 * T - 1) {
	    			split(x, i, tmp);
	    			if (k > x.key[i]) {
	    				i++;
	    			}
	    		}
	    		_insert(x.child[i], k);
	    	}

	  }

	  public void display() {
	    display(root);
	  }

	  private void display(Node x) {
		  assert (x == null);
		  for (int i = 0; i < x.degree; i++) {
			  System.out.print(x.key[i] + " ");
		  }
		  if (!x.leaf) {
			  for (int i = 0; i < x.degree + 1; i++) {
	    		display(x.child[i]);
			  }
	      
		  }
	    
	  }
	  
	  public void Search(int id) {
		  if(Search(root, id) == null){
			  System.out.println("\nItem não encontrado!\n");
		  }else{
			  System.out.println("\nElemento " + id + " encontrado!\n");
		  }
		    
	  }
	  
	  private Node Search(Node x, int key) {
		    int i = 0;
		    if (x == null)
		    	return x;
		    for (i = 0; i < x.degree; i++) {
		    	if (key < x.key[i]) {
		    		break;
		    	}
		    	if (key == x.key[i]) {
		    		return x;
		    	}
		    }
		    if (x.leaf) {
		    	return null;
		    } else {
		    	return Search(x.child[i], key);
		    }
	  }
	  
	  private void Remove(Node x, int key) {
		    int pos = x.Find(key);
		    if (pos != -1) {
		    	if (x.leaf) {
		    		int i = 0;
		    		for (i = 0; i < x.degree && x.key[i] != key; i++) {
		    		}
		    		;
		    		for (; i < x.degree; i++) {
		    			if (i != 2 * T - 2) {
		    				x.key[i] = x.key[i + 1];
		    			}
		    		}
		    		x.degree--;
		    		return;
		    	}
		    	if (!x.leaf) {

		    		Node pred = x.child[pos];
		    		int predKey = 0;
		    		if (pred.degree >= T) {
		    			for (;;) {
		    				if (pred.leaf) {
		    					System.out.println(pred.degree);
		    					predKey = pred.key[pred.degree - 1];
		    					break;
		    				} else {
		    					pred = pred.child[pred.degree];
		    				}
		    			}
		    			Remove(pred, predKey);
		    			x.key[pos] = predKey;
		    			return;
		    		}

		    		Node nextNode = x.child[pos + 1];
		    		if (nextNode.degree >= T) {
		    			int nextKey = nextNode.key[0];
		    			if (!nextNode.leaf) {
		    				nextNode = nextNode.child[0];
		    				for (;;) {
		    					if (nextNode.leaf) {
		    						nextKey = nextNode.key[nextNode.degree - 1];
		    						break;
		    					} else {
		    						nextNode = nextNode.child[nextNode.degree];
		    					}
		    				}
		    			}
		    			Remove(nextNode, nextKey);
		    			x.key[pos] = nextKey;
		    			return;
		    		}

		    		int temp = pred.degree + 1;
		    		pred.key[pred.degree++] = x.key[pos];
		    		for (int i = 0, j = pred.degree; i < nextNode.degree; i++) {
		    			pred.key[j++] = nextNode.key[i];
		    			pred.degree++;
		    		}
		    		for (int i = 0; i < nextNode.degree + 1; i++) {
		    			pred.child[temp++] = nextNode.child[i];
		    		}

		    		x.child[pos] = pred;
		    		for (int i = pos; i < x.degree; i++) {
		    			if (i != 2 * T - 2) {
		    				x.key[i] = x.key[i + 1];
		    			}
		    		}
		    		for (int i = pos + 1; i < x.degree + 1; i++) {
		    			if (i != 2 * T - 1) {
		    				x.child[i] = x.child[i + 1];
		    			}
		    		}
		    		x.degree--;
		    		if (x.degree == 0) {
		    			if (x == root) {
		    				root = x.child[0];
		    			}
		    			x = x.child[0];
		    		}
		    		Remove(pred, key);
		    		return;
		    	}
		    } else {
		    	for (pos = 0; pos < x.degree; pos++) {
		    		if (x.key[pos] > key) {
		    			break;
		    		}
		    	}
		    	Node tmp = x.child[pos];
		    	if (tmp.degree >= T) {
		    		Remove(tmp, key);
		    		return;
		    	}
		    	if (true) {
		    		Node nb = null;
		    		int devider = -1;

		    		if (pos != x.degree && x.child[pos + 1].degree >= T) {
		    			devider = x.key[pos];
		    			nb = x.child[pos + 1];
		    			x.key[pos] = nb.key[0];
		    			tmp.key[tmp.degree++] = devider;
		    			tmp.child[tmp.degree] = nb.child[0];
		    			for (int i = 1; i < nb.degree; i++) {
		    				nb.key[i - 1] = nb.key[i];
		    			}
		    			for (int i = 1; i <= nb.degree; i++) {
		    				nb.child[i - 1] = nb.child[i];
		    			}
		    			nb.degree--;
		    			Remove(tmp, key);
		    			return;
		    		} else if (pos != 0 && x.child[pos - 1].degree >= T) {

		    			devider = x.key[pos - 1];
		    			nb = x.child[pos - 1];
		    			x.key[pos - 1] = nb.key[nb.degree - 1];
		    			Node child = nb.child[nb.degree];
		    			nb.degree--;

		    			for (int i = tmp.degree; i > 0; i--) {
		    				tmp.key[i] = tmp.key[i - 1];
		    			}
		    			tmp.key[0] = devider;
		    			for (int i = tmp.degree + 1; i > 0; i--) {
		    				tmp.child[i] = tmp.child[i - 1];
		    			}
		    			tmp.child[0] = child;
		    			tmp.degree++;
		    			Remove(tmp, key);
		    			return;
		    		} else {
		    			Node lt = null;
		    			Node rt = null;
		    			@SuppressWarnings("unused")
						boolean last = false;
		    			if (pos != x.degree) {
		    				devider = x.key[pos];
		    				lt = x.child[pos];
		    				rt = x.child[pos + 1];
		    			} else {
		    				devider = x.key[pos - 1];
		    				rt = x.child[pos];
		    				lt = x.child[pos - 1];
		    				last = true;
		    				pos--;
		    			}
		    			for (int i = pos; i < x.degree - 1; i++) {
		    				x.key[i] = x.key[i + 1];
		    			}
		    			for (int i = pos + 1; i < x.degree; i++) {
		    				x.child[i] = x.child[i + 1];
		    			}
		    			x.degree--;
		    			lt.key[lt.degree++] = devider;

		    			for (int i = 0, j = lt.degree; i < rt.degree + 1; i++, j++) {
		    				if (i < rt.degree) {
		    					lt.key[j] = rt.key[i];
		    				}
		    				lt.child[j] = rt.child[i];
		    			}
		    			lt.degree += rt.degree;
		    			if (x.degree == 0) {
		    				if (x == root) {
		    					root = x.child[0];
		    				}
		    				x = x.child[0];
		    			}
		    			Remove(lt, key);
		    			return;
		    		}
		    	}
		   }
	  }

	  public void Remove(int key) {
		  Node x = Search(root, key);
		  if (x == null) {
			  return;
		  }
		  Remove(root, key);
	  }

	  public void Task(int a, int b) {
		  Stack<Integer> st = new Stack<>();
		  FindKeys(a, b, root, st);
		  while (st.isEmpty() == false) {
			  this.Remove(root, st.pop());
		  }
	  }

	  private void FindKeys(int a, int b, Node x, Stack<Integer> st) {
		  int i = 0;
		  for (i = 0; i < x.degree && x.key[i] < b; i++) {
			  if (x.key[i] > a) {
		        st.push(x.key[i]);
		      }
		  }
		  if (!x.leaf) {
		      for (int j = 0; j < i + 1; j++) {
		        FindKeys(a, b, x.child[j], st);
		      }
		  }
	  }

	  public boolean Contain(int k) {
		  if (this.Search(root, k) != null) {
			  return true;
		  } else {
		      return false;
		  }
	  }	  
	  
}
	  