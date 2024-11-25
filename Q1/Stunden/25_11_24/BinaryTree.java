public class BinaryTree<T> {

  private T content;
  private BinaryTree<T> left;
  private BinaryTree<T> right;

  public BinaryTree() {
  }

  public BinaryTree(T content) {
    this.content = content; 
  }

  public boolean hasItem() {
    return content != null;
  }

  public T getItem() {
    return content;
  }

  public void setItem(T content) {
    this.content = content;
  }

  public void deleteItem() {
    this.content = null;
  }

  public boolean isLeaf() {
    return left == null && right == null;
  }

  public boolean hasLeft() {
    return left != null;
  }

  public BinaryTree<T> getLeft() {
    return left;
  }

  public void setLeft(BinaryTree<T> tree) {
    left = tree;
  }

  public void deleteLeft() {
    left = null;
  }

  public boolean hasRight() {
    return right != null;
  }

  public BinaryTree<T> getRight() {
    return right;
  }

  public void setRight(BinaryTree<T> tree) {
    right = tree;
  }

  public void deleteRight() {
    right = null;
  }
}
