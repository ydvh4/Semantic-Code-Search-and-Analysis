package edu.umkc.sce.codesearch.search;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.function.library.strlen;
import com.hp.hpl.jena.util.FileManager;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import java.io.InputStream;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/json")
public class JSONService {
    

	@GET
	@Path("/autocom")
	@Produces(MediaType.APPLICATION_JSON)

    public ArrayList<String> autocom(@QueryParam("find") String find,@QueryParam("term") String keyword) {
            Model model = ModelFactory.createDefaultModel();
            int length= keyword.length();
            ArrayList<String>a=new ArrayList<String>();
		 // use the FileManager to find the input file
		 InputStream in = FileManager.get().open( "C:\\hadoop-1.1.2.rdf" );
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
        "select  ?uri\n"+
        "where { \n"+
         "?uri rdfs:subClassOf <http://www.rdfcoder.org/2007/1.0#"+find+">" + 
          "} \n ";
      
    Query query = QueryFactory.create(queryString);

    System.out.println(queryString);

    System.out.println("Query Result Sheet");

    System.out.println("----------------------");

   
    // Execute the query and obtain results
    QueryExecution qe = QueryExecutionFactory.create(query, model);
    com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
    
    while(results.hasNext())
    {
         QuerySolution soln = results.nextSolution();
        //String uri = soln.get("uri").toString();
         
         String uri = soln.get("uri").toString();
         String out=uri.replace("http://www.rdfcoder.org/2007/1.0#jpackage:","");
     
     a.add(out);
      

    }
    ArrayList<String> b= new ArrayList<String>();
     for (String s : a) {
    if(s.toLowerCase().contains(keyword.toLowerCase())){
       b.add(s);
    }
     }
    qe.close();
       return b;
                 
                 
             
                    
       }  
        
         
    @GET
	@Path("/searchpack")
	@Produces(MediaType.APPLICATION_JSON)

    public ArrayList<String> searchpack(@QueryParam("keyword") String keyword) {
        
        Model model = ModelFactory.createDefaultModel();
 
		 // use the FileManager to find the input file
		 InputStream in = FileManager.get().open( "C:\\hadoop-1.1.2.rdf" );
ArrayList<String> a= new ArrayList<String>();
		// read the RDF/XML file
		model.read(in, null);
     String queryString = null;
    // Create a new query
      queryString =        
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
        "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
        "select DISTINCT ?class\n"+
        "where { \n"+
         "?x \n"+
           "j.0:contains_class ?class\n" + 
              "FILTER (REGEX(STR(?class), '"+keyword +"', 'i')) "+
          "} \n ";
      Query query = QueryFactory.create(queryString);

    System.out.println(queryString);

    System.out.println("Query Result Sheet");

    System.out.println("----------------------");

   
    // Execute the query and obtain results
    QueryExecution qe = QueryExecutionFactory.create(query, model);
    com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
    
    while(results.hasNext())
    {
         QuerySolution soln = results.nextSolution();
        //String uri = soln.get("uri").toString();
         
        String nameclass = soln.get("class").toString();
                 String out=nameclass.replace("http://www.rdfcoder.org/2007/1.0#jpackage:","");

     a.add("class:"+out);
    }
    
        // Output query results    
    System.out.print(a.size());
    qe.close();
    return a;
      }

    @GET
	@Path("/searchclass")
	@Produces(MediaType.APPLICATION_JSON)

    public ArrayList<String> searchclass(@QueryParam("keyword") String keyword,@QueryParam("ec") String ec,@QueryParam("ic") String ic,@QueryParam("cm") String cm,@QueryParam("hv") String hv) {
        
        Model model = ModelFactory.createDefaultModel();
 
		 // use the FileManager to find the input file
		 InputStream in = FileManager.get().open( "C:\\hadoop-1.1.2.rdf" );
		
ArrayList<String> a= new ArrayList<String>();
		// read the RDF/XML file
		model.read(in, null);
     String queryString = null;
          queryString =        
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
        "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
        "select ?class ?method ?impint ?eclass ?visibility\n"+
        "where { \n"+
         "?x "+
           "j.0:contains_class ?class \n" + 
              "FILTER (REGEX(STR(?class), 'jclass:"+keyword +"', 'i'))  \n"+
         "OPTIONAL {{ ?class "+
           "j.0:contains_method ?method  \n" +
                  "FILTER (REGEX(STR(?method), 'jmethod:"+cm +"', 'i')) } UNION \n"+
          " { ?class "+
           "j.0:implements_int ?int \n" +
                  "FILTER (REGEX(STR(?int), '"+ic +"', 'i')) }\n"+
         "UNION { ?class "+
           "j.0:extends_class ?eclass \n" +
                  "FILTER (REGEX(STR(?eclass), '"+ec +"', 'i')) }\n"+
         "UNION { ?class "+
           "j.0:has_visibility ?visibility \n" +
                  "FILTER (REGEX(STR(?visibility), '"+hv +"', 'i')) }} \n"+
                  "} \n ";
      
      
    Query query = QueryFactory.create(queryString);

    System.out.println(queryString);

    System.out.println("Query Result Sheet");

    System.out.println("----------------------");

   
    // Execute the query and obtain results
    QueryExecution qe = QueryExecutionFactory.create(query, model);
    com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
    
    while(results.hasNext())
    {
         QuerySolution soln = results.nextSolution();
        //String uri = soln.get("uri").toString();
        String class1 = soln.get("class").toString().replace("http://www.rdfcoder.org/2007/1.0#jpackage:","");
        String method="";
        if(soln.contains("method")) 
        method = soln.get("method").toString().replace("http://www.rdfcoder.org/2007/1.0#jpackage:","");
        String eclass ="";
        if(soln.contains("eclass")) 
               eclass=soln.get("eclass").toString().replace("http://www.rdfcoder.org/2007/1.0#jpackage:","");
        String visibility ="";
        if(soln.contains("visibility")) 
        visibility=soln.get("visibility").toString();
         String cons = "";
         String impint="";
         if(soln.contains("impint")) 
                impint= soln.get("impint").toString();
           
     a.add("Class:"+class1+"<br/>Method:"+method+"<br/>Extends Class:"+eclass+"<br/>Visibility:"+visibility+"<br/>Constructor:"+cons+"<br/>Implements Interface:"+impint);
     
    }
    
        // Output query results    
    System.out.print(a.size());
    qe.close();
    return a;
      }
 @GET
	@Path("/searchmethod")
	@Produces(MediaType.APPLICATION_JSON)

    public ArrayList<String> searchmethod(@QueryParam("keyword") String keyword,@QueryParam("thr") String thr,@QueryParam("rt") String rt,@QueryParam("hv1") String hv1) {
        
        Model model = ModelFactory.createDefaultModel();
 
		 InputStream in = FileManager.get().open( "C:\\hadoop-1.1.2.rdf" );
		if (in == null) {
		    throw new IllegalArgumentException(
		                                 "File:C:\\Users\\ydvh4\\Desktop\\Code-Search-master\\hadoop.rdf not found");
		}
ArrayList<String> a= new ArrayList<String>();
		// read the RDF/XML file
		model.read(in, null);
     String queryString = null;
               queryString =        
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
        "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
        "select ?throws ?signature ?visibility\n"+
        "where { \n"+
         "?x "+
           "j.0:throws ?throws \n" +
                       "FILTER (REGEX(STR(?throws), '"+thr +"', 'i')) \n"+
       
          "?x "+
           "j.0:contains_signature ?signature \n" + 
                       "FILTER (REGEX(STR(?signature), 'jmethod:"+keyword +"', 'i')) \n"+
          "?x "+
           "j.0:has_visibility ?visibility \n" + 
                       "FILTER (REGEX(STR(?visibility), '"+hv1 +"', 'i')) \n"+
          "} \n ";
      
      
    Query query = QueryFactory.create(queryString);

    System.out.println(queryString);

    

   
    // Execute the query and obtain results
    QueryExecution qe = QueryExecutionFactory.create(query, model);
    com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
    String signature="";
    while(results.hasNext())
    {
         QuerySolution soln = results.nextSolution();
        //String uri = soln.get("uri").toString();
         
        String throws1 = soln.get("throws").toString();
         String out=throws1.replace("http://www.rdfcoder.org/2007/1.0#jpackage:","");

     
        signature = soln.get("signature").toString();
        String out1=signature.replace("http://www.rdfcoder.org/2007/1.0#jpackage:","");
       
        String visibility = soln.get("visibility").toString();
            String[] package1=out1.split(".jclass");
            String[] cls=package1[1].split(".jmethod");
            String[] meth=cls[1].split(".jsignature");
       a.add("Package:"+package1[0]+"<br/>Class"+cls[0]+"<br/>Method"+meth[0]+"<br/>Throws:"+out+"<br/>Visibility:"+visibility);
       
         
    }
    qe.close();
       return a;
      }
        @GET
	@Path("/searchint")
	@Produces(MediaType.APPLICATION_JSON)

    public ArrayList<String> searchint(@QueryParam("keyword") String keyword) {
        
        Model model = ModelFactory.createDefaultModel();
 
		 // use the FileManager to find the input file
 InputStream in = FileManager.get().open( "C:\\hadoop-1.1.2.rdf" );
		ArrayList<String> a= new ArrayList<String>();
		// read the RDF/XML file
		model.read(in, null);
     String queryString = null;
        queryString =        
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
        "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
        "select  ?method ?attribute\n"+
        "where { \n"+
         "<http://www.rdfcoder.org/2007/1.0#jpackage:"+keyword+">\n"+
           "j.0:contains_method ?method." + 
                    "<http://www.rdfcoder.org/2007/1.0#jpackage:"+keyword+">\n"+
           "j.0:contains_attribute ?attribute." + 
          "} \n ";
      
      
    Query query = QueryFactory.create(queryString);

    System.out.println(queryString);

    

   
    // Execute the query and obtain results
    QueryExecution qe = QueryExecutionFactory.create(query, model);
    com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
    
    while(results.hasNext())
    {
         QuerySolution soln = results.nextSolution();
        //String uri = soln.get("uri").toString();
         
        String method = soln.get("method").toString();
       String attribute = soln.get("attribute").toString();
        a.add("Method:"+method);
      a.add("Attribute:"+attribute);
    }
    qe.close();
      return a;
      }
       @GET
	@Path("/searchcon")
	@Produces(MediaType.APPLICATION_JSON)

    public ArrayList<String> searchcon(@QueryParam("keyword") String keyword) {
        
        Model model = ModelFactory.createDefaultModel();
 InputStream in = FileManager.get().open( "C:\\hadoop-1.1.2.rdf" );
		ArrayList<String> a= new ArrayList<String>();
		// read the RDF/XML file
		model.read(in, null);
     String queryString = null;
         queryString =        
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
        "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
        "select  ?parameter ?visibility\n"+
        "where { \n"+
          "<http://www.rdfcoder.org/2007/1.0#jpackage:"+keyword+">\n"+
           "j.0:contains_parameter ?parameter." + 
          "<http://www.rdfcoder.org/2007/1.0#jpackage:"+keyword+"> \n"+
           "j.0:has_visibility ?visibility." + 
          "} \n ";
      
      
    Query query = QueryFactory.create(queryString);

    System.out.println(queryString);

    

   
    // Execute the query and obtain results
    QueryExecution qe = QueryExecutionFactory.create(query, model);
    com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
    String signature="";
    while(results.hasNext())
    {
         QuerySolution soln = results.nextSolution();
        //String uri = soln.get("uri").toString();
         
        String parameter = soln.get("parameter").toString();
        String visibility = soln.get("visibility").toString();
        a.add("Parameter:"+parameter);
      a.add("Visibility:"+visibility);
    }
    qe.close();
       
    return a;
      }
    
    }  
	
