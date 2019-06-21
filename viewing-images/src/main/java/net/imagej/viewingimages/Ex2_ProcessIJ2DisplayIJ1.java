
package net.imagej.viewingimages;

import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import ij.ImagePlus;
import ij.gui.Line;
import ij.gui.ProfilePlot;
import ij.process.LUT;

public class Ex2_ProcessIJ2DisplayIJ1 {

	@SuppressWarnings("unchecked")
	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException
	{

		// create an instance of imagej
		final ImageJ ij = new ImageJ();

		// launch it
		ij.launch(args);

		// get bridge as IJ2 Dataset
		Dataset dataBridge = (Dataset) ij.io().open("../images/bridge.tif");

		// blur the bridge
		RandomAccessibleInterval<T> blurred = ij.op().filter().gauss(
			(RandomAccessibleInterval<T>) dataBridge, 3.0);

		// convert bridge to IJ1
		ImagePlus impBridge = ImageJFunctions.wrap(
			(RandomAccessibleInterval<T>) dataBridge, "bridge");
		// convert blurred to IJ1
		ImagePlus impBlurred = ImageJFunctions.wrap(blurred, "blurred");

		// show IJ1
		impBridge.show();
		impBlurred.show();

		// get the fire color map using the Utility
		// (note the code in the Utility was cut and pasted from LutLoader
		// I couldn't figure out how to cleanly grab the fire LUT from it....
		LUT lut = IJ2CourseImageUtility.fire();

		// set the LUT on the bridge and blurred
		impBridge.setLut(lut);
		impBridge.updateAndRepaintWindow();

		impBlurred.setLut(lut);
		impBlurred.updateAndRepaintWindow();

		// now draw an ROI (using the IJ1 interface) on each image and plot it
		impBridge.setRoi(new Line(100, 100, 200, 200));
		impBlurred.setRoi(new Line(100, 100, 200, 200));
		ProfilePlot plotter1 = new ProfilePlot(impBridge);
		ProfilePlot plotter2 = new ProfilePlot(impBlurred);

		plotter1.createWindow();
		plotter2.createWindow();

	}
}
