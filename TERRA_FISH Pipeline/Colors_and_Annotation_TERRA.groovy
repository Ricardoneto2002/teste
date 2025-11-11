import java.awt.Color

// 0) Rename channels
setChannelNames("Neuron","TERRA","Nuclei")

// 1) Parse your hex and set channel colors (using getRGB())
def c1 = Color.decode("#ff00ff").getRGB()   // magenta
def c2 = Color.decode("#ffffff").getRGB()   // white
def c3 = Color.decode("#0000ff").getRGB()   // blue
setChannelColors(c1, c2, c3)

setImageType('FLUORESCENCE');
createFullImageAnnotation(true)
