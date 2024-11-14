package de.deniz.fairylights;

public class Main {
    public static void main(String[] args) {
        DynamicArray<LED> initialFairyLights = new DynamicArray<>();

        for (int i = 0; i < 10; i++) {
            initialFairyLights.add(new LED());
        }

        LightsController controller = new LightsController();
        controller.addFairyLight(initialFairyLights);

        controller.printFairyLights();

        System.out.println("Mode2");
        controller.shineMode2();
        controller.printFairyLights();

        System.out.println("Mode3");
        controller.shineMode3();
        controller.printFairyLights();

        
    }
}
