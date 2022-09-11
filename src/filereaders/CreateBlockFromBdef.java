package filereaders;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Creates Block From Bdef line.
 *
 * @author Hadas Neuman
 *
 */
public class CreateBlockFromBdef {

    /**Creates a block according to the definitions associated.
     * The block will be located at position (xpos, ypos).
     *
     * @param s is the definition
     * @return  a block created according to the string
     * @throws Exception if find a problem
     */
    public BlockInfo createBlock(String s) throws Exception {
        String symbol = null;
        int width = -1;
        int height = -1;
        int hitPoints = -1;
        //List<String> fills = new ArrayList();
        String[] fills = null;

        Color stroke = null;

        //List<String> images = new ArrayList();
        String[] images = null;
        Color[] colors = null;

        // Make sure the last character is space:
        s += " ";

        //Finding the different parameters

        //symbol
        //height:
        Pattern pattern1 = Pattern.compile("symbol:(\\w+?)\\s");
        Matcher matcher = pattern1.matcher(s);
        if (matcher.find()) {
            symbol = matcher.group(1);
        } else {
            throw new Exception("Missing symbol");
        }


        //height:
        pattern1 = Pattern.compile("height:(\\d+)");
        matcher = pattern1.matcher(s);
        if (matcher.find()) {
            height = Integer.parseInt(matcher.group(1));
        } else {
            throw new Exception("Missing height");
        }

        //width:
        pattern1 = Pattern.compile("width:(\\d+)");
        matcher = pattern1.matcher(s);
        if (matcher.find()) {
            width = Integer.parseInt(matcher.group(1));
        } else {
            throw new Exception("Missing width");
        }

        //hit_points:
        pattern1 = Pattern.compile("hit_points:(\\d+)");
        matcher = pattern1.matcher(s);
        if (matcher.find()) {
            hitPoints = Integer.parseInt(matcher.group(1));
            fills = new String[hitPoints];
            images = new String[hitPoints];
            colors = new Color[hitPoints];

        } else {
            throw new Exception("Missing hit points");
        }

        //stroke:
        pattern1 = Pattern.compile("stroke:color.(.+?)\\)\\s");
        matcher = pattern1.matcher(s);
        if (matcher.find()) {
            ColorsParser tempColor = new ColorsParser();
            stroke = tempColor.colorFromString(matcher.group(1));
        }

        //fill:

        pattern1 = Pattern.compile("fill(.+?)\\s");
        Matcher m = pattern1.matcher(s);

        while (m.find()) {

            String tempFill = m.group(1);

            //finding if there is a number in the string
            Pattern pattern2 = Pattern.compile("(\\d+)");
            Matcher matcher2 = pattern2.matcher(tempFill);
            int i;
            if (matcher2.find()) { //there is a number
                i = Integer.parseInt(matcher2.group(1));
            } else {
                i = 1; //only one fill available
            }

            pattern2 = Pattern.compile(":(.+)");
            matcher2 = pattern2.matcher(tempFill);
            if (matcher2.find()) { //finding the filling string
                fills[i - 1] = matcher2.group(1);
            }
        }

        //check whether the filling is a color or an image

        for (int j = 0; j < fills.length; j++) {
            if (fills[j] == null) {
                fills[j] = fills[j - 1];
            }
            Pattern pattern2 = Pattern.compile("image.(.+)\\)");
            Matcher matcher2 = pattern2.matcher(fills[j]);
            if (matcher2.find()) {
                //images[j] = "resources/" + matcher2.group(1);
                images[j] = "" + matcher2.group(1);
            } else { //this is a color
                pattern2 = Pattern.compile("color.(.+)\\)");
                matcher2 = pattern2.matcher(fills[j]);
                if (matcher2.find()) {
                    ColorsParser tempColor = new ColorsParser();
                    colors[j] = tempColor.colorFromString(matcher2.group(1));
                }
            }
        }



        //creating the block;

        BlockInfo bi = new BlockInfo(symbol, width, height, colors, images, hitPoints, stroke);
        return bi;



    }
}
