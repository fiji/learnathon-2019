# Paintera Live Demo

[Paintera](https://github.com/saalfeldlab/paintera) is a ground-truth annotation and proof-reading tool for large-scale connectomics from 3D electron microscopy. At its core, it uses the multi-resolution renderer of [BigDataViewer](https://github.com/bigdataviewer/bigdataviewer-core) to render cross-sections through volumetric data at arbitrary orientations and zoom levels. 3D triangle-mesh representations of neurons are generated on the fly without the need for pre-computations. In particular, this means, that changes to segmentations through voxel data manipulations or super-voxel groupings are reflected immediately in the 3D visualization. Advanced painting tools allow for rapid generation of dense 3D ground truth annotations or correction of automatic segmentations. Super-voxel agglomerations can be proof-read with simple merge and detach operations triggered by mouse clicks. Paintera can be extended beyond its intended use-case via [SciJava plugins](https://github.com/scijava/scijava-common) that are auto-detected when provided as jars/class-files on the class path.

I will demonstrate the core functionality of Paintera in a live demo and will explain Paintera extension by example.

## Installation

Paintera depends on Java 8, JavaFX, and [Apache Maven](https://maven.apache.org). [Oracle
JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) ships
with JavaFX. Alternatively, [OpenJDK](https://openjdk.java.net/install) does not ship with JavaFX and JavaFX needs to be installed separately. Many Linux distributions provide OpenJDK 8 and JavaFX through their package managers, e.g.

```sh
# Arch Linux
pacman -S jdk8-openjdk
pacman -S java-openjfx
pacman -S maven

# Ubuntu
apt install default-jre default-jdk
apt install openjfx
apt install maven
```

On Ubuntu 18.04, you will have to install OpenJFX 8 explicitly and then immediately pin this version:

```sh
sudo apt install openjfx=8u161-b12-1ubuntu2 libopenjfx-java=8u161-b12-1ubuntu2 libopenjfx-jni=8u161-b12-1ubuntu2
sudo apt-mark hold openjfx libopenjfx-java libopenjfx-jni
```
If your are on 18.10 or newer, add the bionic repositories following instruction on https://bugs.launchpad.net/ubuntu/+source/openjfx/+bug/1799946.

Paintera is available for installation through [conda](https://conda.io):
```sh
conda install -c conda-forge -c hanslovsky paintera
```
Or, to create a new environment for Paintera with the name *paintera*:
```sh
conda create -n paintera -c conda-forge -c hanslovsky paintera
conda activate paintera
```

Installation through conda is made possible by [jgo](https://github.com/scijava/jgo) that runs any Java main class from maven coordinates, e.g. the current Paintera snapshot (either from your local repository or from the SciJava maven repository):
```sh
$ jgo org.janelia.saalfeldlab:paintera:0.14.1-SNAPSHOT+org.slf4j:slf4j-simple:1.7.25 --version
[JavaFX Application Thread] INFO org.janelia.saalfeldlab.paintera.PainteraCommandLineArgs - Paintera version: 0.14.1-SNAPSHOT-7dca6099d77df4841f0edd569d94a5f2353e07cd
```
The `+org.slf4j:slf4j-simple:1.7.25` portion of the *endpoint* adds [slf4j logger bindings](https://www.slf4j.org) to ensure that the log is output to the command line.
Jgo is installed as a dependency of paintera or is available through the conda-forge channel:

```sh
conda install -c conda-forge jgo
```

## Topics
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
 
