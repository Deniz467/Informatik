public class Beaver {
    private final boolean child;

    private Beaver next;

    public Beaver(boolean child) {
        this.child = child;
    }

    public boolean isChild() {
        return child;
    }

    public void setNext(Beaver biber) {
        this.next = biber;
    }

    public Beaver getNext() {
        return next;
    }


    @Override
    public String toString() {
        return "{" +
            " child='" + isChild() + "'" +
            "}";
    }

}
