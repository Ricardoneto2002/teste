### QuPath Pipeline for TERRA-FISH Analysis



This repository contains Groovy scripts for a multi-step pipeline involving cell and nucleus detection, classification (Neuron/Non-Neuron), TERRA foci detection, cleaning, and final cell classification.



**Step 0: Upstream Image Preprocessing (External)**

Prepare your TIF images using the following external steps before importing into QuPath:



Stitching: Use ZEN Black (or similar software) to stitch your tile-scan images.



Projection: Use FIJI to create a Maximum Intensity Projection (MIP) TIFF from the stitched image stack.



Import: Import the MIP TIFFs into QuPath.





**Step 1: Initial QuPath Setup and Full Annotation**

This script defines the correct channel properties (names, colors, image type) and creates a working annotation for the entire image.



Script: Color\_and\_Annotations\_TERRA.groovy



Action: Run this script immediately after importing your MIP TIFF.





**Step 2: Nucleus Detection and Initial Cell Filtering**

This two-part step establishes the nuclear boundaries and removes unwanted detections based on size.



Nucleus Detection: Execute the nuclei\_Script\_annotations script. This script performs the nucleus detection using StarDist 2D, but uses a low cell expansion variable to ensure the resulting cell annotations closely correspond to the DAPI nuclear signal. The initial outputs are annotations, not detections. This will be helpful in the classification step.



Script: nuclei\_Script\_annotations.groovy



Filter Small Annotations: Run the DeleteMinAreaNuclei script. This step automatically deletes unwanted cell annotations based on a defined minimum area threshold, removing debris or artifacts.



Script: DeleteMinAreaNuclei.groovy


**Step 3: Neuron Classification Setup**

This process uses supervised machine learning to identify neurons based on the HuC/D channel signal.



Pixel Classifier: Run the NeuronPixelClassifier script. This applies a pixel classifier, previously trained to recognize the HuC/D channel, to the entire annotated area.



Action: Tweaking of the classifier is often required at this stage to optimize the resulting pixel detections for the most accurate identification of neuronal bodies.



Nucleus Classification: Execute the NeuronvsNonNeuron\_annotations script. This script classifies the nucleus annotations established in Step 2 into either "Neuron" or "Non-Neuron" based on the results from the pixel classifier.



Script: NeuronvsNonNeuron\_annotations.groovy


**Step 4: Cleanup Annotations**

This step streamlines the workspace by removing temporary or non-essential annotation layers.



Script: DeleteLockedAnnotations.groovy



Action: This script deletes the full image annotation (from Step 1) and the pixel classifier annotations (from Step 3), leaving only the primary cell annotations for subsequent analysis.



**Step 5: TERRA Foci Detection and Cleanup**

This is a multi-step process to accurately identify and isolate TERRA foci.



TERRA Pixel Classifier: Run the TERRAPixelClassifier script. This applies a supervised machine learning-based pixel classifier, trained to recognize punctate TERRA signal in its specific channel.



Script: TERRAPixelClassifier.groovy



Remove Extracellular Foci: Execute the RemoveCentrois script. This crucial cleanup step removes all unwanted TERRA foci detections whose centroids fall outside of the classified cell annotations, ensuring all remaining foci belong to a nucleus.



Script: RemoveCentrois.groovy



Filter Artifacts: Execute the MaxAreaTERRA script. This filters out large or artifactual TERRA detections based on a defined maximum size threshold (foci size).



Script: MaxAreaTERRA.groovy



**Step 6: Final Annotation Resolution and Classification**

This final step resolves duplicate classifications and assigns the final comprehensive cell identity.



Delete Inner Borders: Run the DeleteChildAnnotations script. This step is necessary because the initial nucleus detection (Step 2) created two annotations per nucleus (one for the nucleus and a small expanded border). This script deletes the inner, redundant annotation to prevent double-counting or repeated classification per nucleus.



Script: DeleteChildAnnotations.groovy



Final Cell Classification: Run the TERRA(+-)\_annotations script. This script uses the results from the previous steps to assign a final, comprehensive classification to each cell annotation:



**Neuron, TERRA+**



**Neuron, TERRA-**



**Non-Neuron, TERRA+**



**Non-Neuron, TERRA-**



Script: TERRA(+-)\_annotations.groovy




Below is a representative image showcasing the final classification of cell annotations into the four categories after the full TERRA-FISH pipeline has been applied.



!\[Representative Image of TERRA-FISH Classification](TERRA-FISH\_RepresentaticePipeline.png)



The image illustrates how different cell types and TERRA positivity are visualized, enabling clear interpretation of the analysis results.


