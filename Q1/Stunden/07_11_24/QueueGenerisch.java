public class QueueGenerisch<T>{

    private class QueueNode {
        private T content = null;
        private QueueNode nextNode = null;

        public QueueNode(T pContent){
            content = pContent;
            nextNode = null;
        }

        public void setNext(QueueNode pNext){
            nextNode = pNext;
        }

        public QueueNode getNext(){
            return nextNode;
        }

        public T getContent(){
            return content;
        }
    }

    private QueueNode head;
    private QueueNode tail;

    /**
     Ueberprueft, ob die Queue leer ist
     */
    public boolean isEmpty(){
        if (head == null) {
            return true;
        } // end of if
        else{
            return false;
        }
    }

    /**
     * Gibt das erste Objekt der Queue zurueck
     */

    public T head(){
        return head.getContent();
    }


    /**
     * Fuegt ein neues Objekt an das Ende der Queue ein.
     */
    public void enqueue(T pContent){
        if(pContent != null){
            QueueNode newNode = new QueueNode(pContent);
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } // end of if
            else{
                tail.setNext(newNode);
                tail = newNode;
            }
        }
    }

    /**
     * Entfernt das erste Objekt aus der Queue und gibt es zurueck.
     */
    public T dequeue(){
        if (isEmpty()) {
            return null;
        } // end of if
        else{
            QueueNode current = head;
            head = head.getNext();
            if (isEmpty()) {
                head = null;
                tail = null;
            } // end of if
               return current.getContent();
        }
    }

    @Override
    public String toString() {
        String sb = "QueueGenerisch{";
        QueueNode head = this.head;

        while (head != null) {
            sb += head.content;

            if (head.nextNode != null) {
                sb += ",";
            }

            head = head.nextNode;
        }

        sb += "}";
        return sb.toString();
    }

}