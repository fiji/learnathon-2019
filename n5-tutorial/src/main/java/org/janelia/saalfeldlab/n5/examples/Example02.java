package org.janelia.saalfeldlab.n5.examples;

import net.imglib2.Interval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.util.Grids;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Intervals;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;
import org.janelia.saalfeldlab.n5.DataType;
import org.janelia.saalfeldlab.n5.DatasetAttributes;
import org.janelia.saalfeldlab.n5.GzipCompression;
import org.janelia.saalfeldlab.n5.N5FSWriter;
import org.janelia.saalfeldlab.n5.imglib2.N5Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Example02 {

    public static void main(String[] args) throws IOException {

        final long[] dims = {50, 80, 40, 100, 3};
        final long[] spatialDims = {dims[0], dims[1], dims[2], dims[4]};
        final int[] blockSize = { 7, 11, 19, 1 };
        final int[] numBlocksPerTasks = { 2, 2, 2, 3 };
        final int[] taskBlockSize = IntStream.range(0, 4).map(i -> blockSize[i] * numBlocksPerTasks[i]).toArray();

        final Random rng = new Random(100);
        final RandomAccessibleInterval<DoubleType> img = ArrayImgs.doubles(dims);
        Views.flatIterable(img).forEach(t -> t.setReal(rng.nextDouble()));

        final String container = "5D-dataset-container-2.n5";
        final String datasetPattern = "we/made/this/%d";

        final N5FSWriter writer = new N5FSWriter(container);
        final DatasetAttributes attributes = new DatasetAttributes(
                spatialDims,
                blockSize,
                DataType.FLOAT64,
                new GzipCompression());

        final List<Interval> blocks = Grids.collectAllContainedIntervals(spatialDims, taskBlockSize);

        final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (long t = 0; t < dims[3]; ++t) {
            final IntervalView<DoubleType> timeSlice = Views.hyperSlice(img, 3, t);
                final String dataset = String.format(datasetPattern, t);
                writer.createDataset(dataset, attributes);
                for (final Interval block : blocks) {
                    es.submit(() -> {
                        // do some processing
                        final RandomAccessibleInterval<DoubleType> processed = Views.interval(timeSlice, block);
                        final long[] blockPosition = Intervals.minAsLongArray(processed);
                        Arrays.setAll(blockPosition, d -> blockPosition[d] / blockSize[d]);
                        N5Utils.saveBlock(processed, writer, dataset, attributes, blockPosition);
                        System.out.println("Saved block at position " + Arrays.toString(blockPosition));
                        return null;
                    });
                }
        }

        es.shutdown();

    }

}
