def hierarchy = QP.getCurrentImageData().getHierarchy()

// Get all annotations that have a parent annotation (i.e., are children)
def childAnnotations = hierarchy.getAnnotationObjects().findAll {
    it.getParent() instanceof qupath.lib.objects.PathAnnotationObject
}

// Delete them
childAnnotations.each { hierarchy.removeObject(it, true) }

println "🧹 Deleted ${childAnnotations.size()} child annotations."
