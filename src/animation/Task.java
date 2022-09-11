package animation;

/**The task interface.
 *
 * @author Hadas Neuman
 *
 * @param <T> is the task to run
 */
public interface Task<T> {
    /**Runs the task.
     *
     * @return the action to do
     */
    T run();
}