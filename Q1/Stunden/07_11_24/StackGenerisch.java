public class StackGenerisch<T> {

    private class StackNode {
        private T content;
        private StackNode next;

        public StackNode(T pContent){
            content = pContent;
            next = null;
        }

        public T getContent(){
            return content;
        }

        public StackNode getNext(){
            return next;
        }

        public void setNext(StackNode pNode){
            next = pNode;
        }

    }

    private StackNode topNode = null;

    public boolean isEmpty(){
        if(topNode==null){
            return true;
        }
        else{
            return false;
        }
    }

    public void push(T pContent){
        StackNode newNode = new StackNode(pContent);
        newNode.setNext(topNode);
        topNode = newNode;
    }

    public T pop(){
        StackNode currentNode = topNode;
        topNode = currentNode.getNext();
        return currentNode.getContent();
    }

    public T top(){
            return topNode.getContent();
        }


    @Override
    public String toString() {
        String sb = "StackGenerisch{";
        StackNode head = this.topNode;

        while (head != null) {
            sb += head.content;

            if (head.next != null) {
                sb += ",";
            }

            head = head.next;
        }

        sb += "}";
        return sb.toString();
    }
}