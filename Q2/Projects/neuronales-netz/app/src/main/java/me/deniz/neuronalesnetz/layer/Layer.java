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

/**
 * Represents one layer of neurons in the neural network.
 *
 * <p>A layer contains multiple {@link Neuron} objects.
 * All neurons in a layer receive the same input values and
 * produce a list of output values.</p>
 */
@NullMarked
public final class Layer implements Serializable {

  @Serial
  private static final long serialVersionUID = -3983903089861790873L;

  /**
   * The list of neurons in this layer.
   */
  private final ObjectList<Neuron> neurons;

  /**
   * The output values from the last computation.
   * This value is only used during training and is not serialized.
   */
  @Nullable
  private transient DoubleList lastOutput;

  private Layer(ObjectList<Neuron> neurons) {
    this.neurons = neurons;
  }

  /**
   * Computes the output values of this layer for the given input.
   *
   * <p>The same input vector is passed to all neurons in the layer.
   * Each neuron computes its own output value.</p>
   *
   * @param input the input values
   * @return the output values of this layer
   */
  public DoubleList computeOutputs(DoubleList input) {
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

  /**
   * Creates a new layer with randomly initialized neurons.
   *
   * <p>Each neuron is created with the same activation function
   * and the same number of input weights.</p>
   *
   * @param neuronCount the number of neurons in the layer
   * @param weightAmount the number of weights per neuron
   * @param activationFunction the activation function for all neurons
   * @param random the random generator used for initialization
   * @return a new layer instance
   */
  public static Layer create(
      int neuronCount,
      int weightAmount,
      ActivationFunction activationFunction,
      RandomGenerator random
  ) {
    var neurons = new ObjectArrayList<Neuron>(neuronCount);
    for (int i = 0; i < neuronCount; i++) {
//      double bias = random.nextDouble(-1, 1);
      var neuron = Neuron.create(0, activationFunction, weightAmount, random);
      neurons.add(neuron);
    }

    return new Layer(neurons);
  }
}
