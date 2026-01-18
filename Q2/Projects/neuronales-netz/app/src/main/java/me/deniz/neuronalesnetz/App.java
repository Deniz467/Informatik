package me.deniz.neuronalesnetz;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import me.deniz.neuronalesnetz.net.Net;
import me.deniz.neuronalesnetz.squishification.PositiveSquishification;
import me.deniz.neuronalesnetz.squishification.SigmoidSquishification;

public class App {
  public static void main(String[] args) throws NoSuchAlgorithmException {
    var random = SecureRandom.getInstanceStrong();

    var net = Net.create(random).addLayer(16, PositiveSquishification.INSTANCE)
        .addLayer(16, PositiveSquishification.INSTANCE)
        .addLayer(10, SigmoidSquishification.INSTANCE);

    var input = random.doubles(0, 1).limit(Settings.NEURON_WEIGHT_COUNT).boxed().toList();
    var output = net.calculate(input);


    System.out.println("Input:");
    for (double v : input) {
      System.out.printf("%.3f ", v);
    }
    System.out.println("\n\nOutput:");
    for (double v : output) {
      System.out.printf("%.6f ", v);
    }
    System.out.println();
  }
}
