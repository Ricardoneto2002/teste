import java.awt.Color

// 0) Rename channels
setChannelNames("Telo","Centro","Nuclei")

// 1) Parse your hex and set channel colors (using getRGB())
def c1 = Color.decode("#ff0000").getRGB()   // red
def c2 = Color.decode("#00ff00").getRGB()   // green
def c3 = Color.decode("#0000ff").getRGB()   // blue
setChannelColors(c1, c2, c3)

// 2) Continue with your workflow
setImageType('FLUORESCENCE')
resetSelection()


// 3) Make a full-image annotation
createFullImageAnnotation(true)
