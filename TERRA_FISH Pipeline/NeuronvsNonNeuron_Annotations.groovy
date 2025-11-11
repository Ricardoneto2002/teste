import qupath.lib.objects.classes.PathClassFactory
import qupath.lib.objects.PathAnnotationObject

def allAnnotations = QP.getAnnotationObjects()

// Step 1: Find all neuron annotations
def neuronAnnotations = allAnnotations.findAll {
    it.getPathClass()?.getName() == "Neuron"
}
println "🔍 Found ${neuronAnnotations.size()} Neuron annotations."

// Step 2: Find all other annotations assumed to be cells (not already Neuron/Non-neuron)
def cellAnnotations = allAnnotations.findAll {
    def cls = it.getPathClass()?.getName()
    !(cls in ["Neuron", "Non-neuron"])
}
println "🔍 Found ${cellAnnotations.size()} cell annotations to classify."

// Step 3: Classify each cell based on centroid position
int countNeuron = 0
int countNonNeuron = 0

cellAnnotations.each { cell ->
    def x = cell.getROI().getCentroidX()
    def y = cell.getROI().getCentroidY()

    def isInside = neuronAnnotations.any { neuron ->
        neuron.getROI().contains(x, y)
    }

    def label = isInside ? "Neuron" : "Non-neuron"
    cell.setPathClass(PathClassFactory.getPathClass(label))

    if (isInside)
        countNeuron++
    else
        countNonNeuron++
}

println "✅ Classification complete: ${countNeuron} Neuron cells, ${countNonNeuron} Non-neuron cells."
