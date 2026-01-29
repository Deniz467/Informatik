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
        .addLayer(4, PositiveSquishification.INSTANCE).addLayer(3, SigmoidSquishification.INSTANCE);

    var input = random.doubles(3, -1, 1).boxed().toList();
    var target = List.of(0.5, 0.3, 0.7);
    var output = net.calculate(input);

    System.out.println("Before training:");
    System.out.println("Input:");
    for (double v : input) {
      System.out.printf("%.3f ", v);
    }
    System.out.println("\n\nOutput:");
    for (double v : output) {
      System.out.printf("%.6f ", v);
    }
    System.out.println("\n\nCost: " + CostFunctions.msl(output, target));

    // Train the network
    for (int i = 0; i < 1000; i++) {
      net.train(input, target, 0.01, 0.0001);
    }

    output = net.calculate(input);
    System.out.println("\nAfter training:");
    System.out.println("Output:");
    for (double v : output) {
      System.out.printf("%.6f ", v);
    }
    System.out.println("\n\nCost: " + CostFunctions.msl(output, target));
  }
}
