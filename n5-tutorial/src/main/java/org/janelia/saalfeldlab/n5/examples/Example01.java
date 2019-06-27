package org.janelia.saalfeldlab.n5.examples;

import bdv.util.BdvFunctions;
import bdv.util.volatiles.VolatileViews;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.Volatile;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.basictypeaccess.array.DoubleArray;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.volatiles.VolatileDoubleType;
import org.janelia.saalfeldlab.n5.Compression;
import org.janelia.saalfeldlab.n5.GzipCompression;
import org.janelia.saalfeldlab.n5.N5FSReader;
import org.janelia.saalfeldlab.n5.N5FSWriter;
import org.janelia.saalfeldlab.n5.imglib2.N5Utils;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example01 {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final Random rng = new Random(100);
        final ArrayImg<DoubleType, DoubleArray> img = ArrayImgs.doubles(10, 20, 30, 40);
        img.forEach(t -> t.setReal(rng.nextDouble()));

        final String container = "container.n5";

        final ExecutorService es = Executors.newFixedThreadPool(11);
        N5Utils.save(img, new N5FSWriter(container), "some/dataset", new int[] {4, 7, 6, 11}, new GzipCompression(), es);
        es.shutdown();

        RandomAccessibleInterval<DoubleType> loaded = N5Utils.openVolatile(new N5FSReader(container), "some/dataset");

        LoopBuilder.setImages(img, loaded).forEachPixel( (s, t) -> {
            if (!s.valueEquals(t))
                throw new RuntimeException("Error!");
        });

        final RandomAccessibleInterval<VolatileDoubleType> vloaded = VolatileViews.wrapAsVolatile(loaded);

        BdvFunctions.show(vloaded, "from n5");


    }
}
