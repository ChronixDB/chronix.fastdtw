[![Build Status](https://travis-ci.org/ChronixDB/chronix.fastdtw.svg?branch=master)](https://travis-ci.org/ChronixDB/chronix.fastdtw)
[![Coverage Status](https://coveralls.io/repos/github/ChronixDB/chronix.fastdtw/badge.svg?branch=master)](https://coveralls.io/github/ChronixDB/chronix.fastdtw?branch=master)
[![Stories in Ready](https://badge.waffle.io/ChronixDB/chronix.fastdtw.png?label=ready&title=Ready)](https://waffle.io/ChronixDB/chronix.fastdtw)
[![Download](https://api.bintray.com/packages/chronix/maven/chronix-fastdtw/images/download.svg) ](https://bintray.com/chronix/maven/chronix-fastdtw/_latestVersion)
# Chronix FastDTW - Dynamic Time Warping (DTW) with a linear time and memory complexity 
An implementation of the Fast DTW algorithm used in Chronix.
This implementation is 40 times faster than the provided implementation at [Google Code](http://code.google.com/p/fastdtw/).

## Performance comparison
This implementation includes some performance optimizations.
The following results show the benefits.
 
### Test-Dataset:
2 months of CPU load with 442.005 data points.
- Raw [Dataset](https://github.com/ChronixDB/chronix.fastdtw/blob/master/src/test/resources/CPU-Load.csv)

### Versions:
- Chronix [Results](https://github.com/ChronixDB/chronix.fastdtw/blob/master/results/chronix-optimization.csv)
- Original [Results](https://github.com/ChronixDB/chronix.fastdtw/blob/master/results/source-runtime.csv)

### Comparison
Times are in milliseconds.

| Search Radius|Original|Chronix|
| ------------- |:-------------:| -----:|
|1 | 35.448 | 0.694 |
|5 | 37.629 | 0.719 |
|10| 43.572 |	0.843 |
|15| 41.557 | 1.028 |
|20| 40.303 | 1.139 |
|25| 39.797 | 1.338 | 
|30| 40.597 |	1.567 |

## Authors:
This work is initially started by:
- Stan Salvador, stansalvador@hotmail.com
- Philip Chan, pkc@cs.fit.edu

## Contributors
- Florian Lautenschlager, @flolaut
