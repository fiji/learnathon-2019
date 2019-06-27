
package net.imagej.viewingimages;

import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;

public class Ex4a_3DPermute {

	@SuppressWarnings("unchecked")
	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException
	{

		// create an instance of imagej
		final ImageJ ij = new ImageJ();

		ij.launch(args);

		// open data
		// Dataset data = (Dataset) ij.io().open(
		// "../images/Tumor1_Fibronectin_Red_SMA_green_40x_fov2.czi");
		Dataset data = (Dataset) ij.io().open(
			"../images/CHUM_CR_R12802_SDTIRF_coreg_2018_05_04_mai_40X_fovA.czi");

		IJ2CourseImageUtility.displayAxisInfo(data, ij.log());
		ij.ui().show("Data ", data);

		IntervalView<T> dataXZY = Views.permute((RandomAccessibleInterval<T>) data.getImgPlus(),
			1, 3);

		ij.ui().show("Data XZY", dataXZY);

	}
}
