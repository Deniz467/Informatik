package me.deniz.neuronalesnetz.neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;
import java.util.stream.DoubleStream;

import me.deniz.neuronalesnetz.Settings;
import me.deniz.neuronalesnetz.squishification.Squishification;

public class Neuron {

  private final List<Double> weights;
  private final double bias;
  private Squishification squishification;

  private Neuron(List<Double> weights, Squishification squishification, double bias) {
    this.weights = new ArrayList<>(weights);
    this.squishification = squishification;
    this.bias = bias;
  }

  public double calculate(List<Double> input) {
    Objects.requireNonNull(input, "input");

    double sum = 0;
    for (int i = 0; i < input.size(); i++) {
      var in = input.get(i);
      var weight = weights.get(i);
      sum += in * weight;
    }

    sum += bias;

    return squishification.squish(sum);
  }

  public DoubleStream weightsStream() {
    return weights.stream().mapToDouble((d) -> d);
  }

  public void setSquishification(Squishification squishification) {
    Objects.requireNonNull(squishification, "squishification");
    this.squishification = squishification;
  }

  public static Neuron create(double bias, Squishification squishification,
      RandomGenerator random) {
    var randomWeights = random.doubles(0, 1).limit(Settings.NEURON_WEIGHT_COUNT).boxed().toList();

    return new Neuron(randomWeights, squishification, bias);
  }

}
