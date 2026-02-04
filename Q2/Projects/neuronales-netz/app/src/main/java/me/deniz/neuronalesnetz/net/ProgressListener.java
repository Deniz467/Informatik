package me.deniz.neuronalesnetz.net;

/**
 * Listener interface for reporting training progress.
 *
 * <p>This interface is used to report how far a training process has progressed.
 * It can be implemented to show progress bars, log messages, or ignore progress
 * completely.</p>
 *
 * <p>The listener is called regularly during training with the current progress
 * values.</p>
 */
@FunctionalInterface
public interface ProgressListener {

  /**
   * Called to report progress.
   *
   * @param current the current progress value
   * @param total the total value when the task is finished
   * @param message an optional message describing the current state
   */
  void onProgress(int current, int total, String message);

  /**
   * Returns a {@code ProgressListener} that does nothing.
   *
   * <p>This can be used when no progress reporting is needed.</p>
   *
   * @return a progress listener that ignores all progress updates
   */
  static ProgressListener none() {
    return (c, t, m) -> {
    };
  }
}
