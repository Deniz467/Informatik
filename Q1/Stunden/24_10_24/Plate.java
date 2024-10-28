public class Plate {
    private final  boolean large;
    private Plate next;

    public Plate(boolean large) {
        this.large = large;
    }

    public boolean isLarge() {
        return large;
    }

    public Plate getNext() {
        return next;
    }

    public void setNext(Plate plate) {
        this.next = plate;
    }


    @Override
    public String toString() {
        return "{" +
            " large='" + isLarge() + "'" +
            "}";
    }

}
