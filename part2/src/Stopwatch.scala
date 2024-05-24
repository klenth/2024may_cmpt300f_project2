/** A class that counts time from some reference point. This is useful for drawing animations. */
// (You don't need to do anything in this file)
class Stopwatch:
  
  private var startTime = System.currentTimeMillis()
  
  /** Returns the time elapsed (in seconds) since the stopwatch was last created or reset */
  def timeElapsed: Double =
    (System.currentTimeMillis() - startTime) / 1000.0
  
  /** Resets the stopwatch */
  def reset(): Unit =
    startTime = System.currentTimeMillis()
