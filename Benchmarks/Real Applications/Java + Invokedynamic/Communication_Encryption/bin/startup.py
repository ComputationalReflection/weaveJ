# Descargar e instalar el paquete NumPy de http://sourceforge.net/projects/numpy/
# Descargar e instalar el paquete SciPy de http://sourceforge.net/projects/scipy/

import sys
import bench

if (len(sys.argv) < 4):
    print "Pass me 1) the command 2) the confidence_level 3) p 4) the percentage to break the loop."
else:
    command = sys.argv[1]
    confidence_level = float(sys.argv[2])
    p_iterations = int(sys.argv[3])
    break_if_interval_percentage_is = float(sys.argv[4])
    interval, mean, sdev, interval_percentage = bench.startup(command, confidence_level, p_iterations, break_if_interval_percentage_is)
    print "Results Startup:"
    print "Interval:", interval
    print "Mean:", mean
    print "Standard deviation:", sdev
    print "Interval percentage:", interval_percentage

