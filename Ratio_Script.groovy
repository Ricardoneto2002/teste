// 1) Get all detected objects
def detections = getDetectionObjects()
if (detections.isEmpty()) {
    println "No detections found! Did you run your nucleus/cell detection first?"
    return
}

// 2) Loop through each detection and compute ratio
for (detection in detections) {
    // Grab the MeasurementList
    def meas = detection.getMeasurementList()

    // Use EXACT key names that QuPath shows in your measurement list
    double teloMean   = meas.getMeasurementValue("Telo: Nucleus: Mean")
    double centroMean = meas.getMeasurementValue("Centro: Nucleus: Mean")

    // Avoid NaN or infinite values if centroMean is zero or missing
    if (Double.isNaN(centroMean) || centroMean == 0) {
        meas.putMeasurement("Ratio", Double.NaN)
    } else {
        double ratio = teloMean / centroMean
        meas.putMeasurement("Ratio", ratio)
    }
}

// 3) Check the console output & measurement table
println "Done! You should now see a 'Ratio' column in the measurement table."


