package me.deniz.neuronalesnetz.net;

@FunctionalInterface
public interface ProgressListener {

  void onProgress(int current, int total, String message);

  static ProgressListener none() {
    return (c, t, m) -> {
    };
  }
}
