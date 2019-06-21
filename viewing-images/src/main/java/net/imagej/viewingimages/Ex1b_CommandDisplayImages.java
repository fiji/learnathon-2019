
package net.imagej.viewingimages;

import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.ops.OpService;
import net.imglib2.img.Img;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class, headless = true,
	menuPath = "Plugins>Learnathon>CommandDisplayImages")
public class Ex1b_CommandDisplayImages<T extends RealType<T> & NativeType<T>>
	implements Command
{

	@Parameter
	OpService ops;

	@Parameter(autoFill = false)
	Img<T> in;

	@Parameter(type = ItemIO.OUTPUT)
	Img<T> out;

	@Override
	public void run() {

		out = ops.create().img(in);
		ops.filter().gauss(out, in, 3.0);

		//ops.filter().addPoissonNoise(out, in);

	}

	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException
	{

		// create an instance of imagej
		final ImageJ ij = new ImageJ();

		// launch it
		ij.launch(args);

		// get bridge as IJ2 Dataset
		// Dataset
		// dataBridge=(Dataset)ij.io().open("http://imagej.net/images/bridge.gif");
		Dataset datasetBridge = (Dataset) ij.io().open("../images/bridge.tif");

		ij.ui().show(datasetBridge);
	}
}
