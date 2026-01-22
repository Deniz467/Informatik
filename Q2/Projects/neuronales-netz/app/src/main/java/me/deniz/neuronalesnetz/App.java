package me.deniz.neuronalesnetz;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import me.deniz.neuronalesnetz.net.Net;
import me.deniz.neuronalesnetz.squishification.PositiveSquishification;
import me.deniz.neuronalesnetz.squishification.SigmoidSquishification;

public class App {
  public static void main(String[] args) throws NoSuchAlgorithmException {
    var random = SecureRandom.getInstanceStrong();

    var net = Net.create(3, random).addLayer(5, PositiveSquishification.INSTANCE)
        .addLayer(4, PositiveSquishification.INSTANCE)
        .addLayer(3, SigmoidSquishification.INSTANCE);

    var input = random.doubles(3, -1, 1).boxed().toList();
    var output = net.calculate(input);


    System.out.println("Input:");
    for (double v : input) {
      System.out.printf("%.3f ", v);
    }
    System.out.println("\n\nOutput:");
    for (double v : output) {
      System.out.printf("%.6f ", v);
    }
    System.out.println("\n\nCost: " + CostFunctions.msl(output, List.of(0.5, 0.3, 0.7)));
  }
}
