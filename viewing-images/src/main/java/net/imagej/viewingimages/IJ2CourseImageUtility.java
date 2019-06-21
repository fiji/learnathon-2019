
package net.imagej.viewingimages;

import net.imagej.Dataset;
import net.imagej.axis.Axes;

import org.scijava.log.LogService;

import ij.process.LUT;

public class IJ2CourseImageUtility {

	// copied from LutLoader()....
	public static LUT fire() {
		byte[] reds = new byte[256];
		byte[] greens = new byte[256];
		byte[] blues = new byte[256];

		int[] r = { 0, 0, 1, 25, 49, 73, 98, 122, 146, 162, 173, 184, 195, 207, 217,
			229, 240, 252, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
			255, 255 };
		int[] g = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 35, 57, 79, 101, 117,
			133, 147, 161, 175, 190, 205, 219, 234, 248, 255, 255, 255, 255 };
		int[] b = { 0, 61, 96, 130, 165, 192, 220, 227, 210, 181, 151, 122, 93, 64,
			35, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 35, 98, 160, 223, 255 };
		for (int i = 0; i < r.length; i++) {
			reds[i] = (byte) r[i];
			greens[i] = (byte) g[i];
			blues[i] = (byte) b[i];
		}

		interpolate(reds, greens, blues, r.length);

		return new LUT(reds, greens, blues);
	}

	// copied from LutLoader....
	static void interpolate(byte[] reds, byte[] greens, byte[] blues,
		int nColors)
	{
		byte[] r = new byte[nColors];
		byte[] g = new byte[nColors];
		byte[] b = new byte[nColors];
		System.arraycopy(reds, 0, r, 0, nColors);
		System.arraycopy(greens, 0, g, 0, nColors);
		System.arraycopy(blues, 0, b, 0, nColors);
		double scale = nColors / 256.0;
		int i1, i2;
		double fraction;
		for (int i = 0; i < 256; i++) {
			i1 = (int) (i * scale);
			i2 = i1 + 1;
			if (i2 == nColors) i2 = nColors - 1;
			fraction = i * scale - i1;
			// IJ.write(i+" "+i1+" "+i2+" "+fraction);
			reds[i] = (byte) ((1.0 - fraction) * (r[i1] & 255) + fraction * (r[i2] &
				255));
			greens[i] = (byte) ((1.0 - fraction) * (g[i1] & 255) + fraction * (g[i2] &
				255));
			blues[i] = (byte) ((1.0 - fraction) * (b[i1] & 255) + fraction * (b[i2] &
				255));
		}
	}

	public static void displayAxisInfo(Dataset data, LogService log) {
	
		int xIndex = data.dimensionIndex(Axes.X);
		int yIndex = data.dimensionIndex(Axes.Y);
		int zIndex = data.dimensionIndex(Axes.Z);
		int cIndex = data.dimensionIndex(Axes.CHANNEL);
		int tIndex = data.dimensionIndex(Axes.TIME);

		long xLen = data.dimension(data.dimensionIndex(Axes.X));
		long yLen = data.dimension(data.dimensionIndex(Axes.Y));
		long zLen = data.dimension(data.dimensionIndex(Axes.Z));
		long cLen = data.dimension(data.dimensionIndex(Axes.CHANNEL));
		long tLen = data.dimension(data.dimensionIndex(Axes.TIME));

		log.info("xIndex " + xIndex + " xLen " + xLen);
		log.info("yIndex " + yIndex + " yLen " + yLen);
		log.info("zIndex " + zIndex + " zLen " + zLen);
		log.info("cIndex " + cIndex + " cLen " + cLen);
		log.info("tIndex " + tIndex + " tLen " + tLen);


	}

}
