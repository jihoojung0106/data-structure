public class BST{ // Binary Search Tree implementation
  int frequencySum;
  int accessCountSum;
  int numKey;
  Node root;

  public BST() {
    frequencySum=0;
    accessCountSum=0;
    numKey=0;
  }
  public void insert(String key) {
    long before=frequencySum;
    root = insertRecursive(root, key);
    long after=frequencySum;
    if(before==after){numKey++;}
  }

  public Node insertRecursive(Node current, String key){
    if(current==null){return new Node(key);}
    if (key.compareTo(current.key) < 0) {
      current.left = insertRecursive(current.left, key);
    } else if (key.compareTo(current.key) > 0) {
      current.right = insertRecursive(current.right, key);
    } else {
      current.increaseFrequency();
      this.frequencySum++;
    }
    return current;
  }
  public boolean find(String key) {
    return findNodeRecursive(root, key);
  }
  public boolean findNodeRecursive(Node current, String key) {
    if (current == null) {
      return false;
    }
    current.increaseAccessCount();
    this.accessCountSum++;
    if (key.equals(current.key)) {
      return true;
    }
    if(key.compareTo(current.key)<0){
      return findNodeRecursive(current.left, key);
    }
    return findNodeRecursive(current.right,key);
  }
  public int size() {
    return numKey;
  }

  public int sumFreq() { return frequencySum+numKey;}
  public int sumProbes() { return accessCountSum;}
  public void resetCounters() {
    reset(root);
    this.accessCountSum=0;
    this.frequencySum=this.numKey;
  }

  public void print() {
    inorder(root);
  }
  public void inorder(Node root){
    if(root!=null){
      inorder(root.left);
      System.out.printf("[%s:%d:%d]\n",root.key,root.frequency,root.accessCount);
      inorder(root.right);
    }
  }
  public int height(Node root){
    if(root==null){return 0;}
    return root.height;
  }
  public void reset(Node root){
    if(root!=null){
      reset(root.left);
      root.resetAccessCount();
      root.resetFrequency();
      reset(root.right);
    }
  }
}
class Node implements Comparable<Node> {
  String key;
  int frequency;
  Node left, right;
  int accessCount;
  int height;
  public Node(String key){
    this.key = key;
    left = right = null;
    frequency=1;
    accessCount=0;
    height=1;
  }

  public void setHeight(int height){
    this.height=height;
  }

  public void increaseFrequency(){
    this.frequency++;
  }
  public void setFrequency(int frequency){
    this.frequency=frequency;
  }
  public void resetFrequency(){
    this.frequency=1;
  }
  public void increaseAccessCount(){
    this.accessCount++;
  }
  public void setAccessCount(int accessCount){
    this.accessCount= accessCount;
  }
  public void resetAccessCount(){
    this.accessCount=0;
  }
  public void setLeft(Node left){
    this.left=left;
  }
  public void setRight(Node right){
    this.right=right;
  }

  @Override
  public int compareTo(Node o) {
    return this.key.compareTo(o.key);
  }
}

