import java.util.function.Function;

public class Musikverwaltungsprogramm {

  public static void main(String[] args) {
    Musiktitel titel1 = new Musiktitel("Shape of You", "Ed Sheeran", "Pop");
    Musiktitel titel2 = new Musiktitel("Hotel California", "Eagles", "Rock");
    Musiktitel titel3 = new Musiktitel("Blinding Lights", "The Weeknd", "Synthwave/Pop");
    Musiktitel titel4 = new Musiktitel("Smells Like Teen Spirit", "Nirvana", "Grunge");
    Musiktitel titel5 = new Musiktitel("Rolling in the Deep", "Adele", "Soul/Pop");

    DynamicArray<Musiktitel> playlist = new DynamicArray<>();

    playlist.add(titel1);
    playlist.add(titel2);
    playlist.add(titel3);
    playlist.add(titel4);
    playlist.add(titel5);

    Musiktitel shapeOfYou = findByTitle(playlist, "Shape of YOu");
    DynamicArray<Musiktitel> adele = findByInterpret(playlist, "adele");
    DynamicArray<Musiktitel> rock = findByGenre(playlist, "Rock");

    System.out.println("\nShape of you: " + shapeOfYou);

    System.out.println("\n\nSongs von Adele: ");
    for (int i = 0; i < adele.getLength(); i++) {
      System.out.println("- " + adele.get(i) + "\n");
    }

    System.out.println("\nRock Songs: ");
    for (int i = 0; i < rock.getLength(); i++) {
      System.out.println("- " + rock.get(i) + "\n");
    }
  }

  private static Musiktitel findByTitle(DynamicArray<Musiktitel> playlist, String titleName) {
    DynamicArray<Musiktitel> results =
        findTitle(playlist, (title) -> title.getTitel().equalsIgnoreCase(titleName));

    return results.isEmpty() ? null : results.get(0);
  }

  private static DynamicArray<Musiktitel> findByInterpret(
    DynamicArray<Musiktitel> playlist, 
    String interpret
  ) {

    return findTitle(playlist, title -> title.getInterpret().equalsIgnoreCase(interpret));
  }
  
  private static DynamicArray<Musiktitel> findByGenre(
    DynamicArray<Musiktitel> playlist,
    String genre
  ) {
    return findTitle(playlist, title -> title.getGenre().equalsIgnoreCase(genre));
  }

  private static DynamicArray<Musiktitel> findTitle(
    DynamicArray<Musiktitel> playlist,
    Function<Musiktitel, Boolean> filter
    ) {
    DynamicArray<Musiktitel> results = new DynamicArray<>();

    for (int i = 0; i < playlist.getLength(); i++) {
      Musiktitel title = playlist.get(i);
      if (filter.apply(title)) {
        results.add(title);
      }
    }

    return results;
  }
}
