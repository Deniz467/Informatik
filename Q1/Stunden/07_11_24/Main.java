public class Main {
    public static void main(String[] args) {
        final QueueGenerisch<Person> queue = new QueueGenerisch<>();
        final StackGenerisch<Person> helperStack = new StackGenerisch<>();

        finalMainddsson person1 = new Person("Valentin");
        final Person person2 = new Person("Valentin mit Hut");
        final Person person3 = new Person("Valentin mit drei Armen");

        queue.enqueue(person1);
        queue.enqueue(person2);
        queue.enqueue(person3);

        System.out.println("Start Schlange: " + queue);

        while (!queue.isEmpty()) {
            helperStack.push(queue.dequeue());
        }

        System.out.println("Gestapelte Schlange: " + helperStack);

        while (!helperStack.isEmpty()) {
            queue.enqueue(helperStack.pop());
        }

        System.out.println("Umgekehrte Schlange: " + queue);
    }
}
