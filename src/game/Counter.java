package game;
/**A Counter class.
 *
 * @author neuman
 *
 */
public class Counter {
    private int num;

    /**A constructor.
     */
    public Counter() {
        this.num = 0;
    }

    /**A constructor.
     *
     * @param num is the input number
     */
    public Counter(int num) {
        this.num = num;
    }

    /**Adds number to current count.
     *
     * @param number is the number to be add
     */
    public void increase(int number) {
        this.num += number;
    }

    /**Subtracts number from current count.
     *
     * @param number is the number to be Subtracted
     */
    public void decrease(int number) {
        this.num -= number;
    }

    /**A getter - return the value of the counter.
     * @return the counter value
     */
    public int getValue() {
        return this.num;
    }

}
