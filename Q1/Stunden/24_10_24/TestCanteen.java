public class TestCanteen {
    public static void main(String[] args) {
        final BeaverQueue beavers = new BeaverQueue();
        final PlateStack plates = new PlateStack();

        beavers.queue(new Beaver(true));
        beavers.queue(new Beaver(false));
        beavers.queue(new Beaver(false));
        beavers.queue(new Beaver(true));
        beavers.queue(new Beaver(false));

        plates.push(new Plate(true));
        plates.push(new Plate(false));
        plates.push(new Plate(false));
        plates.push(new Plate(true));
        plates.push(new Plate(false));

        final Canteen canteen = new Canteen(beavers, plates);
        final boolean valid = canteen.isValid();

        if (valid) {
            System.out.println("Die Bieber und Teller befinden sich in der richtigen Reihenfolge.");
        } else {
            System.err.println("Die Bieber oder die Teller befinden sich nicht in der richtigen Reihenfolge");
        }

        while (!plates.isEmpty() && !beavers.isEmpty()) {
            final Plate plate = plates.pop();

            final boolean offered = canteen.offerPlate(plate);
            System.out.println(offered);

            if (!offered) {
                System.err.println("Es konnte kein passender Biber für für den Teller gefunden werden: " + plate);
                break;
            }
        }
    }

    
}
