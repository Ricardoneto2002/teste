### QuPath Pipeline for Telo-FISH Analysis



This repository contains Groovy scripts for automated nucleus detection, filtering, and Telo/Centro mean intensity ratio (TCR) calculation for Telo-FISH images in QuPath.



**Step 0: Upstream Image Preprocessing (External)**

Prepare your TIF images using the following external steps before importing into QuPath:



Stitching: Use ZEN Black (or similar software) to stitch your tile-scan images.



Projection: Use FIJI to create a Maximum Intensity Projection (MIP) TIFF from the stitched image stack.



Import: Import the MIP TIFFs into QuPath.





**Step 1: Initial QuPath Setup and Full Annotation**

This script sets the correct channel properties (names, colors, image type) and creates a working annotation for the entire image.



Run Script: Color\_and\_annotation\_TELO.groovy



Action: Run this script immediately after importing your MIP TIFF.







**Step 2: Nucleus Detection and Filtering**
This step detects nuclei using StarDist 2D and then filters out small objects that may be noise or debris.

Select the Annotation: Ensure the full image annotation created in Step 1 is selected.

Run Script: Execute MinAreaNuclei\_Script.groovy.Result: The script runs the StarDist model and automatically filters the resulting detections, removing unwanted nuclei detections.



**Step 3: Calculate Telo/Centro Ratio (TCR)**
This script calculates the mean intensity ratio for Telo and Centro signals for every detected nucleus.

Run Script: Ratio\_Script.groovyAction

Result: A new measurement column named "Ratio" is generated, calculated as TeloMean/CentroMean.


**Step 4: Export Measurements**

The final step uses QuPath's built-in functionality to save your results.



Go to Measure > Export measurements...



Select the desired measurements (ensuring the new "Ratio" column is included).



Save the data as a .txt or .csv file for downstream statistical analysis.


**Step 5: Heatmap Visualization (Optional)**

Use the detections filled script to generate a clear visual map of your cell population based on the calculated measurements (e.g., coloring cells based on the "Ratio" value).



Script: Detections\_Filled\_Script.groovy



Action: Execute this script to apply a "Detections Filled" display style. This is useful for creating final image outputs or quick visual checks of your results.



