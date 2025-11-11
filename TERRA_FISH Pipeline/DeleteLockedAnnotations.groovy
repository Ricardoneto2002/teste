def hierarchy = QP.getCurrentImageData().getHierarchy()

// Get all annotations that are locked
def lockedAnnotations = hierarchy.getAnnotationObjects().findAll {
    it.isLocked()
}

lockedAnnotations.each {
    hierarchy.removeObject(it, true)
}

println "🧹 Deleted ${lockedAnnotations.size()} locked annotations."
