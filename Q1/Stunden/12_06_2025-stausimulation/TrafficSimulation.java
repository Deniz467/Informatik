import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import java.util.random.RandomGenerator;

public class TrafficSimulation {
  private static int MAX_SPEED = 5;
  private static final List<StreetEntry> street = new ArrayList<>();

  public static void main(String[] args) throws Exception {
    SecureRandom random = SecureRandom.getInstanceStrong();

    for (int i = 0; i < 100; i++) {
      if (random.nextInt(100) > 30) {
        street.add(StreetEntry.empty());
      } else {
        int speed = random.nextInt(1, MAX_SPEED);
        Car car = new Car(speed);
        street.add(car.toEntry());
      }
    }

    System.out.println(buildStreet());
    simulateStreet(random);
    System.out.println(buildStreet());
  }

  private static void simulateStreet(RandomGenerator random) {
    for (StreetEntry entry : street.reversed()) {
      Car car = entry.car;
      car.increaseSpeed();
      car.hitTheBrake();
      car.dawdle(random);
      car.move();
    }
  }

  private static String buildStreet() {
    final StringBuilder sb = new StringBuilder();
    for (StreetEntry entry : street) {
      sb.append(entry);
    }
    return sb.toString();
  }


  public record StreetEntry(boolean isEmpty, Car car) {
    public static StreetEntry empty() {
      return new StreetEntry(true, null);
    }

    public static StreetEntry fromCar(Car car) {
      return new StreetEntry(false, car);
    }

    @Override
    public String toString() {
      return isEmpty ? "." : "A";
    }
  }



  public static class Car {
    private int speed;
    private StreetEntry entry;

    public Car(int speed) {
      this.speed = speed;
    }

    public StreetEntry toEntry() {
      return entry == null ? (entry = StreetEntry.fromCar(this)) : entry;
    }

    public void increaseSpeed() {
      if (speed < MAX_SPEED) {
        speed++;
      }
    }

    public void hitTheBrake() {
      int thisIndex = street.indexOf(entry);

      int counter = 0;
      StreetEntry nearestEntry = null;
      for (int i = thisIndex; i < street.size(); i++) {
        StreetEntry streetEntry = street.get(i);
        if (streetEntry.isEmpty)
          break;
        counter++;
        nearestEntry = streetEntry;
      }
      if (nearestEntry == null)
        return;

      if (counter < nearestEntry.car.speed) {
        this.speed = Math.min(speed, counter);
      }
    }

    public void dawdle(RandomGenerator random) {
      if (random.nextInt(0, 100) < 30) {
        speed = Math.min(0, speed - 1);
      }
    }

    public void move() {
      int index = street.indexOf(entry);
      int newPosition = (index + speed) % street.size();
      street.set(index, StreetEntry.empty());
      StreetEntry previous = street.set(newPosition, entry);
      if (previous != null && !previous.isEmpty) {
        System.out.println("Replaced car at position " + newPosition + " with a new car.");
      }
    }

    public double getSpeed() {
      return speed;
    }
  }
}
