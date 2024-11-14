package de.deniz.fairylights;

public class LED {
    private boolean shining;

    public void lightsUp() {
        shining = true;
    }

    public void lightsDown() {
        shining = false;
    }

    public boolean shining() {
        return shining;
    }
}
