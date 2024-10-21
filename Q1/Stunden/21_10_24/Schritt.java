public class Schritt {
    private Schritt previous = null;

    public Schritt getPrevious() {
        return previous;
    }

    public void setPrevious(Schritt previous) {
        this.previous = previous;
    }

    public void execute() {
        
    }

    public void rollback() {

    }
}
