
package net.imagej.viewingimages;

import net.imagej.ImageJ;
import net.imagej.ops.OpService;
import net.imglib2.Point;
import net.imglib2.algorithm.region.hypersphere.HyperSphere;
import net.imglib2.img.Img;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
*/

@Plugin(type = Command.class,
	menuPath = "Plugins>Learnathon>Draw A Sphere (IJ2)")
public class DrawSphereInCenter<T extends RealType<T> & NativeType<T>>
	implements Command
{

	@Parameter
	OpService ops;

	@Parameter
	Img<T> img;

	@Override
	public void run() {
		final Point center = new Point(img.numDimensions());

		for (int d = 0; d < img.numDimensions(); d++)
			center.setPosition(img.dimension(d) / 2, d);

		long radius = Math.min(img.dimension(0), Math.min(img.dimension(1), img
			.dimension(2)));

		T intensity = ops.stats().max(Views.iterable(img));

		HyperSphere<T> hyperSphere = new HyperSphere<>(img, center, radius);

		for (final T value : hyperSphere) {
			value.setReal(intensity.getRealFloat());
		}
	}
}
