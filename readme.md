CSV to flare.json
=====
Converts taxonomy data (csv/txt) to multilevel flare.json data for `D3.js` visualizations

About
-----
This program converts text/csv categorical data into the flare hierarchy JSON 
format accepted by many popular `D3.js` visualizations (i.e. collapsible tree). 

Dependencies include the [json-simple](http://code.google.com/p/json-simple/) java library. 

Usage
-----
Usage is through the command line; run the program similar to any other java
application with command line arguments: `java D3Taxonomy <input file> <delimiter>`. 
Program will both print final JSON string to the console as well as save it file 
"flare.json" in the present working directory. The source may be modified as 
necesary for special use cases.

Any text delimiter is compatible, examples include `,` (commas) for comma
separated values, or ` > ` for plain text files. 
NOTE: please pay close attention to the proper delimiter in your file or the 
tree generated may contain errors/duplicate entries. 

JSON data is stored in a tree structure with a dynamically expanding list for 
sub nodes while processing.

Sample data includes the Google product taxonomy which is [downloadable from Google](https://support.google.com/merchants/answer/1705911?hl=en) 
(in both plain-text and excel (convertable to csv) formats). 
Sample d3.js visualization used is [collapsible tree.](http://bl.ocks.org/mbostock/4339083)

Samples
-----
Sample output with the Google taxonomy data along with the data itself in both
csv and txt formats can be found in the `/samples` directory of the repo. The
sample `jsontree.html` file uses the collapsible tree visualization with the 
produced json as input. It may be directly viewed with a web browser. 