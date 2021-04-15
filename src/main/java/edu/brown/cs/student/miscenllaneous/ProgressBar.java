package edu.brown.cs.student.miscenllaneous;

/**
 * Class for building and using progress bars in the console.
 */
public class ProgressBar {

  private static final int PERCENTMULTIPLIER = 100;
  private final String task;
  private final int length;
  private int count = 0;

  /**
   * Public constructor.
   *
   * @param task the task message to add to the progress bar
   * @param length the length of the bar
   */
  public ProgressBar(String task, int length) {
    this.task = task;
    this.length = length;
  }

  /**
   * Prints out an updated bar.
   */
  public void update() {
    this.count += 1;
    String middleWhiteSpace = new String(new char[length - count])
            .replace("\0", " ");
    String middleProgress = new String(new char[count])
            .replace("\0", "=");
    String middle = middleProgress + middleWhiteSpace;
    long percent = Math.round((double) count / (double) length * PERCENTMULTIPLIER);
    String carriage;
    if (count == length) {
      carriage = "\n\n";
    } else {
      carriage = "\r";
    }
    String bar = new StringBuilder()
            .append(this.task).append(":  ")
            .append(count)
            .append("/")
            .append(length)
            .append(" [")
            .append(middle).append("]").append("  ")
            .append(percent)
            .append("%")
            .append(carriage)
            .toString();
    System.out.print(bar);
  }
}
