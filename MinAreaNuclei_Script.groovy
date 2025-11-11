import qupath.ext.stardist.StarDist2D
import qupath.lib.scripting.QP
import qupath.lib.objects.PathDetectionObject

// Load model
def modelPath = "C:/Users/ricar/Documents/GIMM/QuPath/Extensions/dsb2018_heavy_augment.pb"

def stardist = StarDist2D
    .builder(modelPath)
    .channels("Nuclei")               // Adjust channel name if needed
    .normalizePercentiles(1, 99)
    .threshold(0.5)
    .pixelSize(0.2)
    .cellExpansion(0.5)
    .measureShape()
    .measureIntensity()
    .build()

// Run detection
def imageData = QP.getCurrentImageData()
def annotations = QP.getSelectedObjects()
if (annotations.isEmpty()) {
    print "⚠️ Select an annotation before running."
    return
}

stardist.detectObjects(imageData, annotations)
stardist.close()

// Now filter by the *nucleus* area measurement (not ROI area)
double minArea = 5


annotations.each { parent ->
    def kept = []
    parent.getChildObjects().each { obj ->
        if (obj instanceof PathDetectionObject) {
            def nucleusArea = obj.getMeasurementList().getMeasurementValue("Nucleus: Area µm^2")
            if (!Double.isNaN(nucleusArea) && nucleusArea >= minArea) {
                kept << obj
            }
        }
    }
    parent.clearPathObjects()
    parent.addPathObjects(kept)
}

print "✅ Filtered detections with nucleus area ≥ ${minArea} µm²"
