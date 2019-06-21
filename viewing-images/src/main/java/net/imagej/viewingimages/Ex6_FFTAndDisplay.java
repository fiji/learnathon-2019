
package net.imagej.viewingimages;

import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.ops.Ops;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.converter.ComplexImaginaryFloatConverter;
import net.imglib2.converter.ComplexPhaseFloatConverter;
import net.imglib2.converter.ComplexRealFloatConverter;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.complex.ComplexFloatType;
import net.imglib2.type.numeric.real.FloatType;

import ij.IJ;
import ij.ImagePlus;

public class Ex6_FFTAndDisplay {

	@SuppressWarnings("unchecked")
	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException
	{
		// create an instance of imagej
		final ImageJ ij = new ImageJ();
		
		ij.launch(args);

		// launch it
		ij.launch(args);
		
		Dataset dataBridge = (Dataset)ij.io().open(
				"../images/bridge.tif");
		
		ImgPlus<T> impBridge=(ImgPlus<T>)dataBridge.getImgPlus();

		ij.ui().show(impBridge);
		
		RandomAccessibleInterval<FloatType> fft=ij.op().filter().fft(impBridge);
	
		// default is power spectrum
		ImageJFunctions.show(fft).setTitle("fft power spectrum");
		// real values
		ImageJFunctions.show(fft, new ComplexRealFloatConverter<FloatType>()).setTitle("fft real values");
		// imaginary values
		ImageJFunctions.show(fft, new ComplexImaginaryFloatConverter<FloatType>()).setTitle("fft imaginary values");
		

	}
}
