public class Main {
    public static void main(String[] args) {
        final DynamicArray<Person> dynArray = new DynamicArray<>();
        final Person p1 = new Person("eins");
        final Person p2 = new Person("zwei");
        final Person p3 = new Person("drei");
        final Person p4 = new Person("vier");
        final Person p5 = new Person("fünfhhh");

        dynArray.add(p1);
        dynArray.add(p2);
        dynArray.add(p3);
        dynArray.add(p4);
        dynArray.add(p5);
        printDynArray(dynArray);

        dynArray.insertAt(2, new Person("eingefügt"));
        printDynArray(dynArray);

        dynArray.set(2, new Person("eingefügt überschrieben"));
        printDynArray(dynArray);

        dynArray.delete(2);
        printDynArray(dynArray);
    }

    private static void printDynArray(DynamicArray<?> dynArray) {
        String sb = "--------------------\nDynamicArray: \n";
        for (int i = 0; i < dynArray.getLength(); i++) {
            sb += "  - " + dynArray.get(i) + "\n";
        }
        sb += "--------------------";

        System.out.println(sb);
    }
}
