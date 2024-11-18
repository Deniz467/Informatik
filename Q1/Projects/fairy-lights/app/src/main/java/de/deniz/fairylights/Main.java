package de.deniz.fairylights;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    final Scanner scanner = new Scanner(System.in);
    DynamicArray<LED> initialFairyLights = new DynamicArray<>();

    for (int i = 0; i < 10; i++) {
      initialFairyLights.add(new LED());
    }
    LightsController controller = new LightsController();
    controller.addFairyLight(initialFairyLights);

    controller.printFairyLights();

    loop: {
      while (scanner.hasNext()) {
        final String mode = scanner.nextLine().trim().toLowerCase();
        switch (mode) {
          case "mode 1":
            controller.shineMode1();
            break;
          case "mode 2":
            controller.shineMode2();
            break;
          case "mode 3":
            controller.shineMode3();
            break;
          case "stop":
            break loop;
          case "off":
            controller.turnOff();
            break;
        }
        if (mode.startsWith("add ")) {
          final String[] splittetInput = mode.split(" ");
            if (splittetInput.length < 2) {
              System.err.println(
                  "Du musst die Länge der Lichterkette, die angehangen werden soll, angeben!");
              break;
            }

            try {
              final int length = Integer.parseInt(splittetInput[1]);
              final DynamicArray<LED> lights = new DynamicArray<>();
              for (int i = 0; i < length; i++) {
                lights.add(new LED());
              }

              controller.addFairyLight(lights);
            } catch (NumberFormatException e) {
              System.err.println("Du musst eine gültige Ganzzahl eingeben!");
              break;
            }
        }


        controller.printFairyLights();
      }
    }
    
    scanner.close();   
  }
}
