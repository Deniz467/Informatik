package me.deniz.neuronalesnetz.net;

import me.deniz.neuronalesnetz.neuron.Neuron;

public sealed interface NetParam {

  double get();

  void set(double value);

  record WeightParam(Neuron neuron, int index) implements NetParam {

    @Override
    public double get() {
      return neuron.getMutableWeights().get(index);
    }

    @Override
    public void set(double value) {
      neuron.getMutableWeights().set(index, value);
    }
  }

  record BiasParam(Neuron neuron) implements NetParam {

    @Override
    public double get() {
      return neuron.getBias();
    }

    @Override
    public void set(double value) {
      neuron.setBias(value);
    }
  }
}
