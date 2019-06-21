
package net.imagej.viewingimages;

import java.io.IOException;

import ij.IJ;
import ij.ImagePlus;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

public class Ex1a_OpenAndDisplayImages {

	public static <T extends RealType<T> & NativeType<T>> void main(final String[] args) throws IOException {
		// create an instance of imagej
		final ImageJ ij = new ImageJ();

		// launch it
		ij.launch(args);

		ImagePlus impBridge = IJ.openImage("../images/bridge.tif");
		impBridge.show();

		// get bridge as IJ2 Dataset
		// Dataset
		Dataset datasetBridge = (Dataset) ij.io().open("../images/bridge.tif");
		// show the IJ1 ImagePlus
		impBridge.show();
		impBridge.setTitle("ImageJ1 ImagePlus");

		// show the IJ2 Dataset
		ij.ui().show("Bridge IJ3 ij.ui().show", datasetBridge);

		// show using imagej functions
		ImageJFunctions.show((RandomAccessibleInterval<T>) datasetBridge.getImgPlus())
				.setTitle("Bridge IJ2 ImageJFunctions.show");
	}

}
