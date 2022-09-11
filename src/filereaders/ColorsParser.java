package filereaders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Parses a color from a string.
 *
 * @author Hadas Neuman
 *
 */
public class ColorsParser {
    /**parses color definition and return the specified color.
     *
     * @param s is the string
     * @return the color specified is the string
     */
    public java.awt.Color colorFromString(String s) {

        Pattern pattern = Pattern.compile("RGB.(\\d+).(\\d+).(\\d+)");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {

            int c1 = Integer.parseInt(matcher.group(1));
            int c2 = Integer.parseInt(matcher.group(2));
            int c3 = Integer.parseInt(matcher.group(3));

            return new java.awt.Color(c1, c2, c3);
        }

        java.awt.Color color = null;
        switch (s) {
        case "black":
            color = java.awt.Color.black;
            break;
        case "blue":
            color = java.awt.Color.blue;
            break;
        case "cyan":
            color = java.awt.Color.cyan;
            break;
        case "gray":
            color = java.awt.Color.gray;
            break;
        case "lightGray":
            color = java.awt.Color.lightGray;
            break;
        case "orange":
            color = java.awt.Color.orange;
            break;
        case "pink":
            color = java.awt.Color.pink;
            break;
        case "red":
            color = java.awt.Color.red;
            break;
        case "green":
            color = java.awt.Color.green;
            break;
        case "white":
            color = java.awt.Color.white;
            break;
        case "yellow":
            color = java.awt.Color.yellow;
            break;
        default:
            color = null;
            break;
        }
        return color;
    }
}