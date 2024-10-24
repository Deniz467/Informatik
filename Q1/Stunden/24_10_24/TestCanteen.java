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
        plates.push(new Plate(true));
        plates.push(new Plate(true));
        plates.push(new Plate(false));

        final boolean valid = validate(beavers, plates);

        if (valid) {
            System.out.println("Die Bieber und Teller befinden sich in der richtigen Reihenfolge.");
        } else {
            System.err.println("Die Bieber oder die Teller befinden sich nicht in der richtigen Reihenfolge");
        }

    }

    private static boolean validate(BeaverQueue beavers, PlateStack plates) {
        while (!beavers.isEmpty() && !plates.isEmpty()) {
            final Beaver beaver = beavers.pop();
            final Plate plate = plates.pop();
            final boolean child = beaver.isChild();
            final boolean large = plate.isLarge();

            if (child) {
                if (large) {
                    return false;
                }
                continue;
            } else {
                if (!large) {
                    return false;
                }

                continue;
            }
        }

        return true;
    }
}
