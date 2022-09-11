package animation;

/**The Menu Interface.
 *
 * @author Hadas Neuman
 *
 * @param <T> is the returned value
 */
public interface Menu<T> extends Animation {
    /**Adds a selection.
     *
     * @param key is the key
     * @param message is the message to display
     * @param returnVal is the task to do
     */
    void addSelection(String key, String message, T returnVal);

    /**Gets the task status.
     *
     * @return the task status
     */
    T getStatus();

    /**Adds a sub-menu.
     *
     * @param key is the key
     * @param message is the message to display
     * @param subMenu is the sub-menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
