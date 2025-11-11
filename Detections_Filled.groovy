def annotations = getCellObjects()
def detections = annotations.collect {
    PathObjects.createDetectionObject(it.getROI(), it.getPathClass(), it.getMeasurementList())
}

addObjects(detections)
