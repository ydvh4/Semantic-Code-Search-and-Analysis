package org.umkc.sce.codesearch.similarity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.PrintStream;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class EntitySimilarityComputation {
	
	public static void main(String [] args) throws IOException{
		int level = 2;
		String onto1 = "/home/cloudera/Desktop/dataset/rdf/hadoop-1.0.0.rdf";
		String onto2 = "/home/cloudera/Desktop/dataset/rdf/hadoop-1.1.2.rdf";
	
		Model model1 = ModelFactory.createDefaultModel();
		Model model2 = ModelFactory.createDefaultModel();
	
		try{
        	FileInputStream fstream = new FileInputStream(onto1);
        	model1.read(fstream,"");
        	FileInputStream fstream2 = new FileInputStream(onto2);
        	model2.read(fstream2,"");    
        	
        	
        	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Similarity sim = new EntityFeatureModelSimilarity();

		
		String queryString =             
			      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
			        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"+
			        "PREFIX j.0: <http://www.rdfcoder.org/2007/1.0/>  \n"+
			        "select  ?uri\n"+
			        "where { \n"+
			         "?uri rdfs:subClassOf <http://www.rdfcoder.org/2007/1.0#JPackage>" + 
			          "} \n ";
			      
			    Query query = QueryFactory.create(queryString);

			    System.out.println(queryString);

			    System.out.println("Query Result Sheet");

			    System.out.println("----------------------");

			   
			    // Execute the query and obtain results
			    QueryExecution qe = QueryExecutionFactory.create(query, model1);
			    com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
			    RDFNode r1 = null;
			    PrintStream out = new PrintStream(new FileOutputStream("1.0.0v1.1.2.txt"));
			    System.setOut(out);
			    int count=0;
			    double sc=0;
			    int simpack=0;
			    int lesssimpack=0;
			    while(results.hasNext())
			    {
			         QuerySolution soln = results.nextSolution();	         
			         r1=soln.get("uri");

			 		double score = sim.computeSimilarity(r1,model1,r1,model2,level);
			 		String ab=sim.explainSimilarity(r1, model1, r1, model2, 2, score);
			 		
			 		if(score==1)
			 		{
			 			simpack++;
			 		}
			 		else if(score>=0.50)
			 		{
			 			lesssimpack++;
			 		}
			 		count++;
			 		sc=sc+score;
			 		out.println("Package:"+r1+": "+score);
			 		out.println(ab);
			 		out.println("-------------------------------------------");

			 		
			 		
			 		
			    }
			    double sc1=sc/count;
               System.out.println("Document similarity:"+sc1);
               System.out.println("Similar Packages:"+simpack);
               System.out.println("Less Similar Packages(>0.5):"+lesssimpack);
           		
		
		

	}
	


	}


