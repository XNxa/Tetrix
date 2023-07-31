package fr.xnxa.tetrix;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public final class ColoredShapeGenerator {

	/**
	 * convert a JavaFX Color into a string representing a web color
	 * (3 or 4 hexadecimal values for Red Green Blue and optional Opacity)
	 *
	 * @param color a JavaFX color
	 * @param omit_opacity if true, the returned web color will only contain 3 hex values (RGB). if false
	 * 		the result will contain opacity hex value as 4th field.
	 * @return hex color value of the given color ( hex RGBA or RGB string)
	 */
	static String getWebColor(Color color, boolean omit_opacity) {
		double red = color.getRed();
		double green = color.getGreen();
		double blue = color.getBlue();
		double op = color.getOpacity();
		StringBuilder sb = new StringBuilder("#");
		sb.append(transformPercentToHex(red));
		sb.append(transformPercentToHex(green));
		sb.append(transformPercentToHex(blue));
		if (!omit_opacity) {
			sb.append(transformPercentToHex(op));
		}
		return sb.toString();
	}

	static private String transformPercentToHex(double percent) {
		String in = Integer.toHexString((int) Math.round(percent * 255));
		return in.length() == 1 ? "0" + in : in;
	}



	/**
	 * Generates an Image containing the given image
	 * in which all initially white pixels will be colored with the given color.
	 *
	 * @param image an image in which all white parts will be colored with the given color
	 * @param color a JavaFX color value
	 * @return a new image that can be used in a JavaFX ImageView (for example).
	 * @throws IllegalArgumentException when pixel reader cannot be obtained from image.
	 */
	static public Image getColoredImage(String shape_name, Color color) throws IllegalArgumentException {
		InputStream is = ColoredShapeGenerator.class.getResourceAsStream("assets/" + shape_name + "_white.png");
		if(is == null) {
			System.out.println("BIZARRE InputStream null pour shape " + shape_name + " couleur " + color);
			return null;
		}
		Image image = new Image(is);
		PixelReader reader = image.getPixelReader();
		double width = image.getWidth();
		double height = image.getHeight();
		// read the pixels from original image
		// copy them in a WritableImage in memory
		// then return this WritableImage

		if(reader != null) {
			WritableImage wi = new WritableImage(Math.round((float)width),Math.round((float)height));
			PixelWriter writer = wi.getPixelWriter();
			for(int ligne=0; ligne < width; ligne ++) {
				for(int colo=0; colo < height; colo ++) {
					Color couleur_lue = reader.getColor(ligne, colo);

					if(couleur_lue.equals(Color.WHITE)) {
						writer.setColor(ligne, colo, color);
					} else {
						writer.setColor(ligne, colo, couleur_lue);
					}
				}
			}
			return wi;
		} else {
			throw new IllegalArgumentException("this image doesn't contain a valid Pixel Reader !");
		}

	}

}
