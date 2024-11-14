package de.deniz.fairylights;

public class Main {
    public static void main(String[] args) throws Exception {
        DynamicArray<LED> initialFairyLights = new DynamicArray<>();

        for (int i = 0; i < 10; i++) {
            initialFairyLights.add(new LED());
        }

        LightsController controller = new LightsController();
        controller.addFairyLight(initialFairyLights);

        controller.printFairyLights();

        System.out.println("Mode1");
        controller.shineMode1();
        controller.printFairyLights();

        System.out.println("Mode2");
        controller.shineMode2();
        controller.printFairyLights();

        System.out.println("Mode3");
        controller.shineMode3();
        controller.printFairyLights();

        int mode = 1;
        while(true) {
            switch(mode) {
                case 1:
                controller.shineMode1();
                break;
                case 2:
                controller.shineMode2();
                break;
                case 3:
                controller.shineMode3();
            }
            controller.printFairyLights();

            if (mode++ > 3) {
                mode = 1;
            }

            Thread.sleep(100);
        }        
    }
}
