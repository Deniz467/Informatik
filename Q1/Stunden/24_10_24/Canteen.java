public class Canteen {

    private final BeaverQueue beavers;
    private final PlateStack plates;


    public Canteen(BeaverQueue beavers, PlateStack plates) {
        this.beavers = beavers;
        this.plates = plates;
    }

    public boolean isValid() {
        Beaver beaver = beavers.head();
        Plate plate = plates.top();

        while (beaver != null && plate != null) {
            if (!validPlateForBeaver(plate, beaver)) {
                return false;
            }

            beaver = beaver.getNext();
            plate = plate.getNext();
        }

        return true;
    }

    public boolean offerPlate(Plate plate) {
        final BeaverQueue temp = new BeaverQueue();
        
        boolean offered = false;
        while (!beavers.isEmpty()) {
            final Beaver beaver = beavers.pop();

            System.out.println(String.format("Offering beaver (%s) plate (%s) isValid: %s", beaver, plate, validPlateForBeaver(plate, beaver)));
            
            if (validPlateForBeaver(plate, beaver)) {
                offered = true;
                break;
            }

            temp.queue(beaver);
        }

        System.out.println(temp);


        while (!temp.isEmpty()) {
            beavers.queue(temp.pop());
        }

        return offered;
    }

    private boolean validPlateForBeaver(Plate plate, Beaver beaver) {
        final boolean child = beaver.isChild();
        final boolean large = plate.isLarge();

        if (child) {
            if (large) {
                return false;
            }
        } else {

            if (!large) {
                return false;
            }
        }
        return true;
    }
}
