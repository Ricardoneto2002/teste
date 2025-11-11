import qupath.lib.objects.PathAnnotationObject

def hierarchy = QP.getCurrentImageData().getHierarchy()

double pixelSize = 0.2076
double minNucleusAreaUm2 = 5.0
double pixelAreaThreshold = minNucleusAreaUm2 / (pixelSize * pixelSize)  // ≈ 116 px²

def parentAnnotations = hierarchy.getAnnotationObjects().findAll {
    it.getChildObjects().any { it instanceof PathAnnotationObject }
}

int removed = 0

for (parent in parentAnnotations) {
    def child = parent.getChildObjects().find { it instanceof PathAnnotationObject }

    if (child != null) {
        def areaPx = child.getROI()?.getArea()
        if (areaPx != null && areaPx <= pixelAreaThreshold) {
            // Explicitly remove child first
            hierarchy.removeObject(child, false)
            // Then remove parent
            hierarchy.removeObject(parent, false)
            println "🗑 Removed parent + child (nucleus area = ${String.format('%.1f', areaPx)} px²)"
            removed++
        }
    }
}

println "✅ Done. Removed ${removed} cells with nucleus area ≤ 5 µm² (≤ ${Math.round(pixelAreaThreshold)} px²)"
