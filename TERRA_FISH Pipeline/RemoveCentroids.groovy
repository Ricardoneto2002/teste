import qupath.lib.objects.PathDetectionObject
import qupath.lib.objects.PathAnnotationObject

def imageData = QP.getCurrentImageData()
def hierarchy = imageData.getHierarchy()

def annotations = hierarchy.getAnnotationObjects()
def detections = hierarchy.getDetectionObjects()

int removed = 0

for (detection in detections) {
    def cx = detection.getROI().getCentroidX()
    def cy = detection.getROI().getCentroidY()
    boolean isInside = false

    for (annotation in annotations) {
        if (annotation.getROI().contains(cx, cy)) {
            isInside = true
            break
        }
    }

    if (!isInside) {
        hierarchy.removeObject(detection, true)
        removed++
    }
}

print "✅ Removed ${removed} detections not inside any annotation (based on centroid check)."
