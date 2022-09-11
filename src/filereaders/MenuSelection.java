package filereaders;

/**A selection in the Menu.
 *
 * @author Hadas Neuman
 * @param <T> is the returned value
 */

public class MenuSelection<T> {

    private String key;
    private String message;
    private T returnVal;

    /**A constructor.
     * @param key is the key
     * @param message is the message to show
     * @param returnVal is the value to return
     */
    public MenuSelection(String key, String message, T returnVal) {
        super();
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**Gets the key.
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**Gets the message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**Gets the returned value.
     * @return the returned value
     */
    public T getReturnVal() {
        return returnVal;
    }


}
