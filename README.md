# bipartite-graph-generation

This work, in the context of a Master 1 internship « Big Data Management and Analytics » at the University of Tours, supervised by Arnaud Soulet and Béatrice Markhoff), aims at generating a bipartite graph model for knowledge bases.

We rely here on Wikidata.

The current model of Barabási and Albert (based on the models of generation of large random networks of Erdos and Rényi - The random graph theory, and Watts and Strogatz - Small world model) shows that real networks are organized in a stationary state without scale, i.e the continuous addition of vertex in time, as well as the aspect of preferential attachment, i.e the probability that a new vertex connects to another one is not uniform, it depends on the connectivity of the vertex (the more this one is high, the more the probability that a new vertex connects to it is important).
This growth and preferential attachment is necessary for the development of the stationary power law distribution.

## Why bipartite graph generation ?
In knowledge bases, information is structured in the form of a triplet : subject-property-object.
We therefore have the first part listing the subjects and the second the objects.
We generate a graph for each property.


## 1st contribution :
- Generation of 2 bipartite graphs: The first one with real data (retrieved via SPARQL query on the Wikidata SPARQL endpoint), the second one with fictitious data (random bipartite graph generation algorithm with the number of facts, distinct subjects and distinct objects of the targeted property).
- Analysis of these to see if they behave similarly: visually thanks to the generation of graphs of the distributions of the real and fictitious data but also statistically thanks to the d-statistic. Comparison of their power law. (cf Wiki)

## 2nd contribution : 
- Big Data processing via mapreduce of the Wikidata Data Dumps in order to obtain the distribution of objects and their connectivity each year to be able to reproduce the graphs. To evaluate our model by adding the temporality and better understand this phenomenon of preferential attachment.
