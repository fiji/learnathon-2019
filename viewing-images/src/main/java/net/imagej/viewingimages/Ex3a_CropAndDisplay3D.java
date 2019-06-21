
package net.imagej.viewingimages;

import io.scif.img.ImgIOException;

import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.Interval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.Intervals;
import net.imglib2.view.Views;

public class Ex3a_CropAndDisplay3D {

	@SuppressWarnings("unchecked")
	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException, ImgIOException,
		IncompatibleTypeException
	{

		// create an instance of imagej
		final ImageJ ij = new ImageJ();

		// launch it
		ij.launch(args);

		Dataset image = (Dataset) ij.io().open(
			"../images/Fibronectin.tif");

		ij.ui().show(image);

		Interval interval = Intervals.createMinMax(400, 400, 8, 700, 700, 16);

		// crop interval
		RandomAccessibleInterval<T> rai = (RandomAccessibleInterval<T>) ij.op()
			.transform().crop(image, interval);

		// alternatively you can use Views directly
		RandomAccessibleInterval<T> rai2 = (RandomAccessibleInterval<T>) Views
			.interval(image, interval);

		// display the image
		ij.ui().show("RAI volume", rai);
		
		extra(ij, image, interval);
	
	}
	
	public static <T extends RealType<T> & NativeType<T>>void extra(ImageJ ij, Dataset image, Interval interval) {
		// is there a cost to using ops
		long start = System.nanoTime();

		// crop interval
		RandomAccessibleInterval<T> rai = (RandomAccessibleInterval<T>) ij.op()
			.transform().crop(image, interval);

		long end = System.nanoTime();
		
		double opTime = (end-start)/10e6;
		
		start = System.nanoTime();

		// alternatively you can use Views directly
		RandomAccessibleInterval<T> rai2 = (RandomAccessibleInterval<T>) Views
			.interval(image, interval);

		end = System.nanoTime();
		
		double viewsTime = (end - start)/10e6;
		
		System.out.println("op time is: "+opTime);
		System.out.println("views time is: "+viewsTime);

	}
}
