
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

public class Ex3b_CropOutOfBounds {

	@SuppressWarnings("unchecked")
	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException, ImgIOException,
		IncompatibleTypeException
	{

		// create an instance of imagej
		final ImageJ ij = new ImageJ();

		// launch it
		ij.launch(args);

		RandomAccessibleInterval<T> image = (RandomAccessibleInterval<T>) ij.io().open(
			"../images/boats.tif");
		
		ij.ui().show(image);

		Interval interval = Intervals.createMinMax(-100, 100, 200, 700);
			
		// Try cropping with an interval that goes out of bounds... 
	//	RandomAccessibleInterval<T> rai = (RandomAccessibleInterval<T>) Views
		//	.interval(image, interval);

		// try again but extend image... (comment out above code, comment in this code)
	RandomAccessibleInterval<T> rai = (RandomAccessibleInterval<T>) Views
				.interval(Views.extendZero(image), interval);

		// display the image
		ij.ui().show("RAI", rai);
		
	}
}
