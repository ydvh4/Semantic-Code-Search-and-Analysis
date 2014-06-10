package org.umkc.sce.codesearch.codeanalytics;
import java.io.InputStream;
import java.util.ArrayList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import com.hp.hpl.jena.util.FileManager;
public class Analytics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = ModelFactory.createDefaultModel();
        
        ArrayList<String>a=new ArrayList<String>();
	 // use the FileManager to find the input file
	 InputStream in = FileManager.get().open( "/home/cloudera/Desktop/dataset/rdf/hadoop-0.1.0.rdf" );
	if (in == null) {
	    throw new IllegalArgumentException(
	                                 "File:C:\\Users\\ydvh4\\Desktop\\Code-Search-master\\hadoop.rdf not found");
	}

	// read the RDF/XML file
	model.read(in, null);

String queryString =        
         
          
  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
    "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
    "SELECT (COUNT(*) AS ?no) { ?s ?p ?o  }";
     
  
Query query = QueryFactory.create(queryString);

// Execute the query and obtain results
QueryExecution qe = QueryExecutionFactory.create(query, model);
com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();

while(results.hasNext())
{
     QuerySolution soln = results.nextSolution();
    //String uri = soln.get("uri").toString();
     
     String uri = soln.get("no").toString();
     
 
     System.out.print(uri); 

}
String queryString1 =        


"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
  "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
  "SELECT (COUNT(DISTINCT ?s ) AS ?no) { { ?s ?p ?o  } UNION { ?o ?p ?s } FILTER(!isBlank(?s) && !isLiteral(?s)) }  ";
   

query = QueryFactory.create(queryString1);

//Execute the query and obtain results
qe = QueryExecutionFactory.create(query, model);
results =  qe.execSelect();

while(results.hasNext())
{
   QuerySolution soln = results.nextSolution();
  //String uri = soln.get("uri").toString();
   
   String uri = soln.get("no").toString();
   

   System.out.print("Entities:"+uri); 

}
String queryString2 =        


"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
  "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
  "SELECT (COUNT(distinct ?o) AS ?no) { ?s rdf:type ?o }";
   

query = QueryFactory.create(queryString2);

//Execute the query and obtain results
qe = QueryExecutionFactory.create(query, model);
results =  qe.execSelect();

while(results.hasNext())
{
   QuerySolution soln = results.nextSolution();
  //String uri = soln.get("uri").toString();
   
   String uri = soln.get("no").toString();
   

   System.out.print("Distinct Classes:"+uri); 

}
String queryString3 =        


"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
  "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
  "SELECT (count(distinct ?p) as ?no) { ?s ?p ?o }";
   

query = QueryFactory.create(queryString3);

//Execute the query and obtain results
qe = QueryExecutionFactory.create(query, model);
results =  qe.execSelect();

while(results.hasNext())
{
   QuerySolution soln = results.nextSolution();
  //String uri = soln.get("uri").toString();
   
   String uri = soln.get("no").toString();
   

   System.out.print("Distinct Predicates:"+uri); 

}
String queryString4 =        


"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
  "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
  "SELECT (COUNT(DISTINCT ?s ) AS ?no) {  ?s ?p ?o   } ";
   

query = QueryFactory.create(queryString4);

//Execute the query and obtain results
qe = QueryExecutionFactory.create(query, model);
results =  qe.execSelect();

while(results.hasNext())
{
   QuerySolution soln = results.nextSolution();
  //String uri = soln.get("uri").toString();
   
   String uri = soln.get("no").toString();
   

   System.out.print("Distinct Subject Nodes:"+uri); 

} 
String queryString5 =        


"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
  "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
  "SELECT (COUNT(DISTINCT ?o ) AS ?no) {  ?s ?p ?o  filter(!isLiteral(?o)) }    ";
   

query = QueryFactory.create(queryString5);

//Execute the query and obtain results
qe = QueryExecutionFactory.create(query, model);
results =  qe.execSelect();

while(results.hasNext())
{
   QuerySolution soln = results.nextSolution();
  //String uri = soln.get("uri").toString();
   
   String uri = soln.get("no").toString();
   

   System.out.print("Distinct object Nodes:"+uri); 

} 

	}

}
