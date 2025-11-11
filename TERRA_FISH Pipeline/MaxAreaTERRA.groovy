import qupath.lib.objects.PathDetectionObject
import qupath.lib.objects.classes.PathClassFactory

// Define the TERRA class and max area (in µm²)
def targetClass = PathClassFactory.getPathClass("TERRA")
def backgroundClass = PathClassFactory.getPathClass("Background")
double maxArea = 100.0  // Adjust as needed

// Get detections of class "TERRA"
def hierarchy = QP.getCurrentImageData().getHierarchy()
def terraDetections = hierarchy.getDetectionObjects().findAll {
    it.getPathClass() == targetClass
}

int countBackground = 0

for (detection in terraDetections) {
    def area = detection.getROI().getArea()
    if (area > maxArea) {
        detection.setPathClass(backgroundClass)
        countBackground++
    }
}

println "✅ Reclassified ${countBackground} large TERRA detections (area > ${maxArea} µm²) as Background."
