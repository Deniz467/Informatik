package me.deniz.neuronalesnetz.activation;

import java.io.Serializable;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Activation_function">Activation Function</a>
 */
public interface ActivationFunction extends Serializable {

  double activate(double x);

  double derivative(double x);
}
