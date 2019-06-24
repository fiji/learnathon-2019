
package net.imagej.viewingimages;

import io.scif.img.ImgIOException;

import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imagej.ops.Ops;
import net.imglib2.Interval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.Intervals;
import net.imglib2.view.Views;

public class Ex3c_CropHyperslice {

	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException, ImgIOException,
		IncompatibleTypeException
	{

		// where are we in the file system??
		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		// create an instance of imagej
		final ImageJ ij = new ImageJ();

		// launch it
		ij.launch(args);

		final Dataset image = (Dataset) ij.io().open("../images/mitosis.tif"); // convenient
																																						// example
																																						// stack

		ij.ui().show(image);

		for (int d = 0; d < image.numDimensions(); d++) {
			ij.log().info(image.axis(d).type());
			ij.log().info(image.dimension(d));
			ij.log().info("");
		}
		
		int xIndex = image.dimensionIndex(Axes.X);
		int yIndex = image.dimensionIndex(Axes.Y);
		int zIndex = image.dimensionIndex(Axes.Z);
		int cIndex = image.dimensionIndex(Axes.CHANNEL);
		int tIndex = image.dimensionIndex(Axes.TIME);

		long xLen = image.dimension(image.dimensionIndex(Axes.X));
		long yLen = image.dimension(image.dimensionIndex(Axes.Y));
		long zLen = image.dimension(image.dimensionIndex(Axes.Z));
		long cLen = image.dimension(image.dimensionIndex(Axes.CHANNEL));
		long tLen = image.dimension(image.dimensionIndex(Axes.TIME));

		System.out.println("xIndex " + xIndex + " xLen " + xLen);
		System.out.println("yIndex " + yIndex + " yLen " + yLen);
		System.out.println("zIndex " + zIndex + " zLen " + zLen);
		System.out.println("cIndex " + cIndex + " cLen " + cLen);
		System.out.println("tIndex " + tIndex + " tLen " + tLen);

		// we can use Views to get a hyperslice using a dimensions index and position
		RandomAccessibleInterval<T> raiViews = (RandomAccessibleInterval<T>) Views
			.hyperSlice(image.getImgPlus(), cIndex, 0);

		System.out.println("num dimensions of hyperslice is " + raiViews
			.numDimensions());

		// display the image... note that something isn't quite right
		ij.ui().show("RAI volume", raiViews);

		// the cropped section is an RAI. To get it to display correctly we need to
		// use the dtaasetservice to convert it to an ImgPlus with ocrrect axis
		DatasetService datasetService = ij.dataset();
		AxisType[] axisTypes = new AxisType[] { Axes.X, Axes.Y, Axes.Z,
			Axes.TIME };
		ImgPlus imgPlusVolume = new ImgPlus(datasetService.create(raiViews), "image",
			axisTypes);

		// now the viewer should display the image with correct axis
		ij.ui().show("ImgPlus volume", imgPlusVolume);
	}
}
