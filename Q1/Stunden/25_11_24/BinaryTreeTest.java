public class BinaryTreeTest {
  public static void main(String[] args) {
    //Die Simpsons, als String Variablen
    String lisa = "Lisa Simpson";
    String marge = "Marge Simpson";
    String homer = "Homer Simpson";
    String jacqueline = "Jacqueline Bouvier";
    String clancy = "Clancy Bouvier";
    String mona = "Mona Simpson";
    String abraham = "Abraham J. Simpson";

    //Ein Binärbaum besteht aus einem Knoten (mit Content) und aus einem linken/rechten Teilbaum. Entsprechend benötigen wir für
    //jeden Knoten ein eigenes Baum-Objekt, hier generisch mit Typ String.
    //Ergänze die fehlenden Objekte.

    BinaryTree<String> lisaB = new BinaryTree<>(lisa);
    BinaryTree<String> margeB = new BinaryTree<>(marge);
    BinaryTree<String> homerB = new BinaryTree<>(homer);
    BinaryTree<String> jacquelineB = new BinaryTree<>(jacqueline);
    BinaryTree<String> clancyB = new BinaryTree<>(clancy);
    BinaryTree<String> monaB = new BinaryTree<>(mona);
    BinaryTree<String> abrahamB = new BinaryTree<>(abraham);


    //Nun wird der Stammbaum der Simpsons aufgebaut. Verknüpfe die obigen Bäume mit linkem und rechten Teilbaum.
    //Beginne mit Lisa.

    lisaB.setLeft(margeB);
    margeB.setLeft(jacquelineB);
    margeB.setRight(clancyB);
    lisaB.setRight(homerB);
    homerB.setLeft(monaB);
    homerB.setRight(abrahamB);


    System.out.println("Lisa (" + lisaB.getItem() + "): Links: " + lisaB.getLeft().getItem()
        + " Rechts: " + lisaB.getRight().getItem());
    System.out.println("Marge (" + margeB.getItem() + "): Links: " + margeB.getLeft().getItem()
        + " Rechts: " + margeB.getRight().getItem());
    System.out.println("Clancy leaf: " + clancyB.isLeaf());
    System.out.println("Jacqueline leaf: " + jacquelineB.isLeaf());
    System.out.println("Homer (" + homerB.getItem() + "): Links: " + homerB.getLeft().getItem()
        + " Rechts: " + homerB.getRight().getItem());
    System.out.println("Mona leaf: " + monaB.isLeaf());
    System.out.println("Abraham leaf: " + abrahamB.isLeaf());
  }
}
