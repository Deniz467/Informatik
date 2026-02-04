package me.deniz.neuronalesnetz.net;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.io.Serial;
import java.io.Serializable;
import java.util.random.RandomGenerator;
import me.deniz.neuronalesnetz.CostFunctions;
import me.deniz.neuronalesnetz.activation.ActivationFunction;
import me.deniz.neuronalesnetz.layer.Layer;
import me.deniz.neuronalesnetz.net.NetParam.BiasParam;
import me.deniz.neuronalesnetz.net.NetParam.WeightParam;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.NullMarked;

/**
 * A neural network.
 *
 * <p>The network consists of multiple {@link Layer} objects. Each layer contains neurons
 * with weights and a bias. A forward pass takes an input
 * vector and produces an output vector.</p>
 *
 * <p>This project contains two training methods:</p>
 * <ul>
 *   <li>{@link #train(DoubleList, DoubleList, double, double, ProgressListener)}:
 *       A slow training method that uses a numerical gradient approximation.</li>
 *   <li>{@link #trainBackprop(DoubleList, DoubleList, double, ProgressListener)}:
 *       A faster training method using backpropagation.</li>
 * </ul>
 *
 * <p><b>Note:</b> The backpropagation method was not originally written by me. I added it
 * because the numerical training method was too slow, so it was hard to properly train
 * and test the network in a reasonable time.</p>
 */
@NullMarked
public final class Net implements Serializable {

  @Serial
  private static final long serialVersionUID = 6075930074701440632L;

  /**
   * All layers of the network in order (input -> output).
   */
  private final ObjectList<Layer> layers;

  /**
   * Random generator used for initializing new layers.
   */
  private final RandomGenerator random;

  /**
   * Size of the input vector
   */
  private final int inputSize;

  /**
   * Flag that is used to remember if the network has already been trained.
   */
  private boolean isTrained = false;

  private Net(ObjectList<Layer> layers, RandomGenerator random, int inputSize) {
    this.layers = new ObjectArrayList<>(layers);
    this.random = random;
    this.inputSize = inputSize;
  }

  /**
   * Adds an already created layer to the network.
   *
   * @param layer the layer to add
   * @return this network (for chaining)
   */
  public Net addLayer(Layer layer) {
    layers.add(checkNotNull(layer, "layer"));
    return this;
  }

  /**
   * Creates and adds a new layer to the network.
   *
   * <p>The number of weights for the new layer is determined automatically:
   * if this is the first layer, it uses {@link #inputSize}.
   * Otherwise it uses the neuron count of the previous layer.</p>
   *
   * @param neuronCount number of neurons in the new layer
   * @param activationFunction activation function used by the neurons
   * @return this network (for chaining)
   */
  public Net addLayer(int neuronCount, ActivationFunction activationFunction) {
    int weightAmount = layers.isEmpty() ? inputSize : layers.getLast().getNeuronCount();
    return addLayer(Layer.create(neuronCount, weightAmount, activationFunction, random));
  }

  /**
   * Runs a forward pass through the whole network.
   *
   * <p>This method takes the input vector and passes it through all layers.
   * The returned list is the output of the last layer.</p>
   *
   * @param input input vector
   * @return output vector of the last layer
   * @throws IllegalArgumentException if the input size does not match {@link #inputSize}
   */
  public DoubleList feedForward(DoubleList input) {
    checkNotNull(input, "input");
    checkArgument(input.size() == inputSize, "Input size must match input size of net");

    var currentValues = input;
    for (Layer layer : layers) {
      currentValues = layer.computeOutputs(currentValues);
    }
    return currentValues;
  }

  /**
   * Collects all trainable parameters (weights and biases) of the whole network.
   *
   * @return list of all parameters
   */
  private ObjectList<NetParam> getAllParams() {
    var params = new ObjectArrayList<NetParam>();
    for (Layer layer : layers) {
      for (var neuron : layer.getNeurons()) {
        for (int weightIndex = 0; weightIndex < neuron.getMutableWeights().size(); weightIndex++) {
          params.add(new WeightParam(neuron, weightIndex));
        }
        params.add(new BiasParam(neuron));
      }
    }
    return params;
  }

  /**
   * Trains the network using a numerical gradient approximation.
   *
   * <p>This method works by changing one parameter slightly (+epsilon and -epsilon),
   * measuring how much the cost changes, and then updating the parameter.</p>
   *
   * @param input input vector
   * @param target target vector (expected output)
   * @param learningRate step size for the update
   * @param epsilon small value used to approximate the gradient
   * @param progress callback for progress updates
   * @throws IllegalArgumentException if input/target sizes do not match the network
   */
  public void train(
      DoubleList input,
      DoubleList target,
      double learningRate,
      double epsilon,
      ProgressListener progress
  ) {
    checkNotNull(input, "input");
    checkNotNull(target, "target");
    checkNotNull(progress, "progress");
    checkArgument(input.size() == inputSize, "Input size must match input size of net");
    checkArgument(target.size() == layers.getLast().getNeuronCount(),
        "Target size must match output size of last layer");
    checkArgument(learningRate > 0, "learningRate must be positive");
    checkArgument(epsilon > 0, "epsilon must be positive");
    checkArgument(epsilon < 1, "epsilon must be smaller than 1");

    // collect all trainable parameters (weights and biases)
    var params = getAllParams();
    int total = params.size();

    // for each parameter, compute its gradient and update it based on the learning rate
    for (int i = 0; i < total; i++) {
      var param = params.get(i);

      // save the original value to reset later
      var original = param.get();

      // try plus epsilon
      param.set(original + epsilon);
      var outputPlusEpsilon = feedForward(input);
      var costPlusEpsilon = CostFunctions.meanSquaredError(outputPlusEpsilon, target);

      // try minus epsilon
      param.set(original - epsilon);
      var outputMinusEpsilon = feedForward(input);
      var costMinusEpsilon = CostFunctions.meanSquaredError(outputMinusEpsilon, target);

      // compute gradient (how much did the cost change?)
      var gradient = (costPlusEpsilon - costMinusEpsilon) / (2 * epsilon);

      // go in the opposite direction of the gradient
      var updatedWeight = original - learningRate * gradient;

      // update the parameter
      param.set(updatedWeight);
      progress.onProgress(i + 1, total, "param " + (i + 1) + "/" + total);
    }

    progress.onProgress(total, total, "done");
  }

  /**
   * Trains the network using backpropagation.
   *
   * <p><b>Note:</b> I did not write this backpropagation approach myself.
   * I added it because the numerical training method ({@link #train(DoubleList, DoubleList, double, double, ProgressListener)}
   * was so slow that it was hard to properly train and test the network.</p>
   *
   * <p>This method is much faster because it calculates the gradients for all weights
   * and biases using one forward pass and one backward pass.</p>
   *
   * <p>This implementation uses mean squared error (MSE) and the activation function
   * derivatives provided by {@link ActivationFunction#derivative(double)}.</p>
   *
   * @param input input vector
   * @param target target vector (expected output)
   * @param learningRate step size for the update
   * @param progress callback for progress updates
   * @throws IllegalArgumentException if input/target sizes do not match the network
   */
  public void trainBackprop(
      DoubleList input,
      DoubleList target,
      double learningRate,
      ProgressListener progress
  ) {
    checkNotNull(input, "input");
    checkNotNull(target, "target");
    checkNotNull(progress, "progress");
    checkArgument(input.size() == inputSize, "Input size must match input size of net");
    checkArgument(!layers.isEmpty(), "Net must have at least one layer");
    checkArgument(target.size() == layers.getLast().getNeuronCount(),
        "Target size must match output size of last layer");
    checkArgument(learningRate > 0, "learningRate must be positive");

    // Forward pass (stores intermediate values in neurons/layers)
    feedForward(input);

    final int layerCount = layers.size();
    final int lastLayerIndex = layerCount - 1;

    // errorSignalsPerLayer[layerIndex][neuronIndex]
    // This is commonly called "delta" in many explanations:
    // it means "how much this neuron is responsible for the error".
    double[][] errorSignalsPerLayer = new double[layerCount][];

    var outputLayer = layers.getLast();
    var outputNeuronCount = outputLayer.getNeuronCount();
    errorSignalsPerLayer[lastLayerIndex] = new double[outputNeuronCount];

    // 1) Output layer error signals
    for (int neuronIndex = 0; neuronIndex < outputNeuronCount; neuronIndex++) {
      var neuron = outputLayer.getNeurons().get(neuronIndex);

      double predictedValue = neuron.getLastOutput();
      double targetValue = target.getDouble(neuronIndex);

      // dCost/dPred for MSE
      double costDerivativeWrtPrediction =
          (2.0 / outputNeuronCount) * (predictedValue - targetValue);

      // dCost/dZ = dCost/dPred * dPred/dZ
      errorSignalsPerLayer[lastLayerIndex][neuronIndex] =
          costDerivativeWrtPrediction *
              neuron.getActivationFunction().derivative(neuron.getLastWeightedInputSum());
    }

    // 2) Hidden layer error signals (propagate backwards)
    for (int layerIndex = lastLayerIndex - 1; layerIndex >= 0; layerIndex--) {
      var currentLayer = layers.get(layerIndex);
      var nextLayer = layers.get(layerIndex + 1);

      var currentNeuronCount = currentLayer.getNeuronCount();
      var nextNeuronCount = nextLayer.getNeuronCount();

      errorSignalsPerLayer[layerIndex] = new double[currentNeuronCount];

      for (int neuronIndex = 0; neuronIndex < currentNeuronCount; neuronIndex++) {
        double propagatedErrorFromNextLayer = 0.0;

        for (int nextNeuronIndex = 0; nextNeuronIndex < nextNeuronCount; nextNeuronIndex++) {
          var nextNeuron = nextLayer.getNeurons().get(nextNeuronIndex);

          double weightToNextNeuron = nextNeuron.getMutableWeights().getDouble(neuronIndex);

          propagatedErrorFromNextLayer +=
              weightToNextNeuron * errorSignalsPerLayer[layerIndex + 1][nextNeuronIndex];
        }

        var neuron = currentLayer.getNeurons().get(neuronIndex);

        errorSignalsPerLayer[layerIndex][neuronIndex] =
            propagatedErrorFromNextLayer *
                neuron.getActivationFunction().derivative(neuron.getLastWeightedInputSum());
      }
    }

    // 3) Update weights and biases using the error signals
    DoubleList previousLayerOutputs = input;

    int totalNeurons = 0;
    for (Layer layer : layers) {
      totalNeurons += layer.getNeuronCount();
    }
    int processedNeurons = 0;

    for (int layerIndex = 0; layerIndex < layerCount; layerIndex++) {
      var layer = layers.get(layerIndex);

      for (int neuronIndex = 0; neuronIndex < layer.getNeuronCount(); neuronIndex++) {
        var neuron = layer.getNeurons().get(neuronIndex);
        double errorSignal = errorSignalsPerLayer[layerIndex][neuronIndex];

        var weights = neuron.getMutableWeights();
        for (int weightIndex = 0; weightIndex < weights.size(); weightIndex++) {
          double gradientForWeight = checkNotNull(previousLayerOutputs, "previousLayerOutputs")
              .getDouble(weightIndex) * errorSignal;

          weights.set(
              weightIndex,
              weights.getDouble(weightIndex) - learningRate * gradientForWeight
          );
        }

        neuron.setBias(neuron.getBias() - learningRate * errorSignal);

        processedNeurons++;
        progress.onProgress(processedNeurons, totalNeurons,
            "layer " + (layerIndex + 1) + "/" + layerCount);
      }

      previousLayerOutputs = layer.getLastOutput();
    }

    progress.onProgress(totalNeurons, totalNeurons, "done");
  }

  public boolean isTrained() {
    return isTrained;
  }

  public void setTrained(boolean isTrained) {
    this.isTrained = isTrained;
  }

  public int getInputSize() {
    return inputSize;
  }

  @UnmodifiableView
  public ObjectList<Layer> getLayers() {
    return ObjectLists.unmodifiable(layers);
  }

  /**
   * Creates a new empty network with the given input size.
   *
   * @param inputSize input vector size
   * @param random random generator used for initialization
   * @return a new network instance
   */
  public static Net create(int inputSize, RandomGenerator random) {
    checkNotNull(random, "random");
    checkArgument(inputSize > 0, "inputSize must be positive");

    return new Net(new ObjectArrayList<>(), random, inputSize);
  }
}
