package de.deniz.fairylights;

public class LightsController {
    private final DynamicArray<LED> fairyLight = new DynamicArray<>();

    public void turnOff(){
        for (int i = 0; i < fairyLight.getLength(); i++) {
            fairyLight.get(i).lightsDown();
        }
    }

    public void shineMode1() {
        for (int i = 0; i < fairyLight.getLength(); i++) {
            fairyLight.get(i).lightsUp();
        }
    }

    public void shineMode2() {
        boolean shine = true;
        for (int i = 0; i < fairyLight.getLength(); i++) {
            LED light = fairyLight.get(i);

            if (shine) {
                light.lightsUp();
            } else {
                light.lightsDown();
            }

            shine = !shine;
        }
    }

    public void shineMode3() {
        int untilNextShine = 2;
        for (int i = 0; i < fairyLight.getLength(); i++) {
            LED light = fairyLight.get(i);

            if (untilNextShine++ == 2) {
                light.lightsUp();
                untilNextShine = 0;
            } else {
                light.lightsDown();
            }
        }
    }

    public void addFairyLight(DynamicArray<LED> add) {
        for (int i = 0; i < add.getLength(); i++) {
            fairyLight.add(add.get(i));
        }        
    }

    public void printFairyLights() {
        String sb = "";
        int lenght = fairyLight.getLength();

        for (int i = 0; i < lenght; i++) {
            sb+= fairyLight.get(i).shining() ? " 1 " : " 0 ";

            if (i != lenght - 1) {
                sb+= "-";
            }
        }

        System.out.println(sb);
    }
}
