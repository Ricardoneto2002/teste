import qupath.lib.objects.PathAnnotationObject
import qupath.lib.objects.PathDetectionObject
import qupath.lib.objects.classes.PathClassFactory

def hierarchy = QP.getCurrentImageData().getHierarchy()

// Get all annotations that already have class "Neuron" or "Non-neuron"
def annotatedCells = hierarchy.getAnnotationObjects().findAll {
    def cls = it.getPathClass()?.getName()
    return cls == "Neuron" || cls == "Non-neuron"
}

// Get all TERRA detections
def terraDetections = hierarchy.getDetectionObjects().findAll {
    it.getPathClass()?.getName() == "TERRA"
}

int countPos = 0
int countNeg = 0

annotatedCells.each { cell ->
    def roi = cell.getROI()

    // Check if any TERRA detection centroid is inside this annotation
    boolean hasTERRA = terraDetections.any { det ->
        def cx = det.getROI().getCentroidX()
        def cy = det.getROI().getCentroidY()
        roi.contains(cx, cy)
    }

    // Clean previous TERRA+ or TERRA- label (if re-running script)
    def baseClass = cell.getPathClass()?.getName()?.replaceAll(/,?\s*TERRA[+-]/, "")?.trim()
    def newClass = baseClass + (hasTERRA ? ", TERRA+" : ", TERRA-")
    cell.setPathClass(PathClassFactory.getPathClass(newClass))

    if (hasTERRA) countPos++ else countNeg++
}

println "✅ Updated ${annotatedCells.size()} annotations:"
println " - TERRA+: ${countPos}"
println " - TERRA-: ${countNeg}"
