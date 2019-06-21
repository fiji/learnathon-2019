
package net.imagej.viewingimages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.imagej.ImageJ;
import net.imglib2.Cursor;
import net.imglib2.FinalDimensions;
import net.imglib2.FinalInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.RealPoint;
import net.imglib2.img.Img;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.Views;

public class Ex5_ReadThunderStormCSVAndRender {

	static ArrayList<Float> x = new ArrayList<>();
	static ArrayList<Float> y = new ArrayList<>();

	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException
	{

		// create an instance of imagej
		final ImageJ ij = new ImageJ();

		// launch it
		ij.launch(args);

		// create a new empty image using ops
		Img<FloatType> img = ij.op().create().img(new FinalDimensions(1000, 1000),
			new FloatType());

		// create a gaussian
		RandomAccessibleInterval<FloatType> guassian = ij.op().create().kernelGauss(
			new double[] { 1.0, 1.0 }, new FloatType());

		// read the CSV file
		loadPoints3dCsv("../images/Ellipsoid_body_synaptic_clefts.csv", false);
		// loadPoints3dCsv("../images/thunderstorm.csv", true);

		// get min and max X,Y in real space
		float minX = Collections.min(x);
		float minY = Collections.min(y);

		float maxX = Collections.max(x);
		float maxY = Collections.max(y);

		for (int i = 0; i < x.size(); i++) {

			// convert real coordinates to pixel coordinates
			long x_ = (long) (img.dimension(0) * (x.get(i) / (maxX - minX) - minX /
				(maxX - minX)));
			long y_ = (long) (img.dimension(1) * (y.get(i) / (maxY - minY) - minY /
				(maxY - minY)));

			// given the pixel location calculate the start and end of the Gaussian
			long[] start = new long[] { (x_ - guassian.dimension(0) / 2), (y_ -
				guassian.dimension(1) / 2) };
			long[] end = new long[] { start[0] + guassian.dimension(0) - 1, start[1] +
				guassian.dimension(1) - 1 };

			if ((start[0] >= 0) && (start[1] >= 0) && (end[0] < img.dimension(0)) &&
				(end[1] < img.dimension(1)))
			{

				// get the RAI to draw the Gaussian in
				RandomAccessibleInterval<FloatType> rai = Views.interval(img,
					new FinalInterval(start, end));

				// get two Cursors to loop through the image and Gaussian
				Cursor<FloatType> c1 = Views.iterable(Views.zeroMin(rai)).cursor();
				Cursor<FloatType> c2 = Views.iterable(guassian).cursor();

				// loop through the image and Gaussian and add the Gaussian to the image
				while (c1.hasNext()) {
					c1.fwd();
					c2.fwd();

					c1.get().add(c2.get());
				}

				// ij.op().math().add(Views.iterable(Views.zeroMin(rai)), guassian,
				// Views
				// .iterable(Views.zeroMin(rai)));
			}

		}

		ij.ui().show(img);

	}

	public static List<RealPoint> loadPoints3dCsv(String csvPath,
		boolean thunderStorm) throws IOException
	{
		int xIndex = 0;
		int yIndex = 1;

		if (thunderStorm) {
			xIndex = 1;
			yIndex = 2;
		}

		List<String> lines = Files.readAllLines(Paths.get(csvPath));
		ArrayList<RealPoint> pts = new ArrayList<>();

		for (String line : lines) {
			String[] elems = line.split(",");

			try {
				x.add(Float.parseFloat(elems[xIndex]));
				y.add(Float.parseFloat(elems[yIndex]));

				RealPoint p = new RealPoint(Double.parseDouble(elems[xIndex]), Double
					.parseDouble(elems[yIndex]));
				pts.add(p);
			}
			catch (Exception ex) {

			}
		}
		return pts;
	}
}
