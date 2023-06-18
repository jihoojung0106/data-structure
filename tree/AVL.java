public class AVL extends BST
{
  public AVL() { super();}

  public void insert(String key) {
    long before=frequencySum;
    root = insertRecursive(root, key);
    long after=frequencySum;
    if(before==after){numKey++;}
  }
  public Node rotateRight(Node current){
    Node lc=current.left;
    Node lcrc=lc.right;
    lc.right=current;
    current.left=lcrc;
    current.height=Math.max(height(current.left),height(current.right))+1;
    lc.height=Math.max(height(lc.left),height(lc.right))+1;
    return lc;
  }
  public Node rotateLeft(Node current){
    Node rc=current.right;
    Node rclc=rc.left;

    rc.left=current;
    current.right=rclc;

    current.height=Math.max(height(current.left),height(current.right))+1;
    rc.height=Math.max(height(rc.left),height(rc.right))+1;

    return rc;
  }

  public Node insertRecursive(Node current, String key){
    boolean check=false;
    if(current==null){
      return new Node(key);}
    if (key.compareTo(current.key) < 0) {
      current.left = insertRecursive(current.left, key);
      if(height(current.left)-height(current.right)==2){
        if(key.compareTo(current.left.key) < 0){
          current=rotateRight(current);
        }
        else{
          current.left=rotateLeft(current.left);
          current=rotateRight(current);
        }
      }
    } else if (key.compareTo(current.key) > 0) {
      current.right = insertRecursive(current.right, key);
      if(height(current.right)-height(current.left)==2){
        if(key.compareTo(current.right.key) > 0){
          current=rotateLeft(current);
        }
        else{
          current.right=rotateRight(current.right);
          current=rotateLeft(current);
        }
      }
    } else {
      current.increaseFrequency();
      this.frequencySum++;
    }
    if(check){
      return current;
    }
    current.height=Math.max(height(current.left),height(current.right))+1;
    return current;
  }

}
