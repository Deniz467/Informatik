package me.deniz.neuronalesnetz.neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;
import java.util.stream.DoubleStream;

import me.deniz.neuronalesnetz.squishification.Squishification;

public class Neuron {

  private final List<Double> weights;
  private double bias;
  private Squishification squishification;

  private Neuron(List<Double> weights, Squishification squishification, double bias) {
    this.weights = new ArrayList<>(weights);
    this.squishification = squishification;
    this.bias = bias;
  }

  public double calculate(List<Double> input) {
    Objects.requireNonNull(input, "input");
    /*
     * if (weights.size() > input.size()) { throw new IllegalArgumentException("Input size (" +
     * input.size() + ") is smaller than weights size (" + weights.size() + ")"); }
     * 
     * for (int i = 0; i < input.size(); i++) { var in = input.get(i); var weight = weights.get(i);
     * sum += in * weight; }
     * 
     * 
     * 
     * double sum = 0;
     * 
     * for (double in : input) { for (double w : weights) { sum += in * w; } }
     * 
     * sum += bias;
     * 
     * return squishification.squish(sum);
     */

    if (input.size() != weights.size()) {
      throw new IllegalArgumentException(
          "Input size (" + input.size() + ") does not match weights size (" + weights.size() + ")");
    }

    double sum = 0;
    for (int i = 0; i < input.size(); i++) {
      sum += input.get(i) * weights.get(i);
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

  public List<Double> getWeights() {
    return weights;
  }

  public double getBias() {
    return bias;
  }

  public void setBias(double bias) {
    this.bias = bias;
  }

  public static Neuron create(double bias, Squishification squishification, int weightAmount,
      RandomGenerator random) {
    var randomWeights = random.doubles(0, 1).limit(weightAmount).boxed().toList();

    return new Neuron(randomWeights, squishification, bias);
  }

}
