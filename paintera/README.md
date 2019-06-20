# Paintera Live Demo

[Paintera](https://github.com/saalfeldlab/paintera) is a ground-truth annotation and proof-reading tool for large-scale connectomics from 3D electron microscopy. At its core, it uses the multi-resolution renderer of [BigDataViewer](https://github.com/bigdataviewer/bigdataviewer-core) to render cross-sections through volumetric data at arbitrary orientations and zoom levels. 3D triangle-mesh representations of neurons are generated on the fly without the need for pre-computations. In particular, this means, that changes to segmentations through voxel data manipulations or super-voxel groupings are reflected immediately in the 3D visualization. Advanced painting tools allow for rapid generation of dense 3D ground truth annotations or correction of automatic segmentations. Super-voxel agglomerations can be proof-read with simple merge and detach operations triggered by mouse clicks. Paintera can be extended beyond its intended use-case via [SciJava plugins](https://github.com/scijava/scijava-common) that are auto-detected when provided as jars/class-files on the class path.

I will demonstrate the core functionality of Paintera in a live demo and will explain Paintera extension by example.

## Topics
 - Installation
 - Supported data
   - Raw Data
   - Label Data
   - Convert into supported format
   - Open datasets
 - Navigation
 - 3D visualization
 - Label voxel manipulations
   - Painting
   - Flood-filling 2D/3D
   - Splitting objects
   - Shape interpolation (hot new awesome feature added by @igorpisarev)
 - Super-voxel (fragment) agglomeration
 - Extensions

## Resources

 - https://github.com/saalfeldlab/paintera
 - https://github.com/saalfeldlab/paintera-conversion-helper
 - https://github.com/hanslovsky/paintera-opener-menu-entry
 - https://cremi.org/data
 - https://github.com/bigdataviewer/bigdataviewer-core
 - https://github.com/scijava/jgo
 - https://github.com/remkop/picocli
 
