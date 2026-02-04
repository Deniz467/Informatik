package me.deniz.neuronalesnetz.layer;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.io.Serial;
import java.io.Serializable;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.activation.ActivationFunction;
import me.deniz.neuronalesnetz.neuron.Neuron;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class Layer implements Serializable {

  @Serial
  private static final long serialVersionUID = -3983903089861790873L;

  private final ObjectList<Neuron> neurons;

  @Nullable
  private transient DoubleList lastInput;
  @Nullable
  private transient DoubleList lastOutput;

  private Layer(ObjectList<Neuron> neurons) {
    this.neurons = neurons;
  }

  public DoubleList computeOutputs(DoubleList input) {
    this.lastInput = input;

    var outputs = new DoubleArrayList(getNeuronCount());
    for (Neuron neuron : neurons) {
      outputs.add(neuron.computeOutput(input));
    }

    this.lastOutput = outputs;
    return outputs;
  }

  public int getNeuronCount() {
    return neurons.size();
  }

  @UnmodifiableView
  public ObjectList<Neuron> getNeurons() {
    return ObjectLists.unmodifiable(neurons);
  }

  @Nullable
  public DoubleList getLastOutput() {
    return lastOutput;
  }

  public static Layer create(
      int neuronCount,
      int weightAmount,
      ActivationFunction activationFunction,
      RandomGenerator random
  ) {
    var neurons = new ObjectArrayList<Neuron>(neuronCount);
    for (int i = 0; i < neuronCount; i++) {
      double bias = random.nextDouble(-1, 1);
      var neuron = Neuron.create(0, activationFunction, weightAmount, random);
      neurons.add(neuron);
    }

    return new Layer(neurons);
  }
}
