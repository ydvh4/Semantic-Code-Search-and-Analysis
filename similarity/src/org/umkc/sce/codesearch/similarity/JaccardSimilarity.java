package org.umkc.sce.codesearch.similarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class JaccardSimilarity implements Similarity {
	int highlevel = 2;
	ArrayList<String> stopWords;
	ArrayList<String> garbagePattern;

	public JaccardSimilarity() {
		stopWords = new ArrayList<String>(Arrays.asList((
		// "able," +
		// "about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,"
		// +
		// "been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,"
		// +
		// "get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,just,"
		// +
		// "least,let,like,likely,may,me,might,most,must,my,neither,no,nor,not,of,off,often,on,"
		// +
		// "only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,"
		// +
		// "their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were,what,"
		// +
		// "when,where,which,while,who,whom,why,will,with,would,yet,you,your," +
				"a,b,c,d,e,g,h,i,j,k,l,n,o,p,q,r,s,t,u,v,w,x,y,z").split(",")));
		garbagePattern = new ArrayList<String>(
				Arrays.asList(("aa,bb,cc,dd,ee,ff,gg,hh,ii,jj,kk,oo,pp,qq,rr,uu,vv,ww,"
						+ "xx,yy,zz").split(",")));

	}

	public double computeSimilarity(RDFNode r1, Model m1, RDFNode r2, Model m2, int level) {
		String concept1 = r1.toString().toLowerCase();
		String concept2 = r2.toString().toLowerCase();

		concept1 = concept1.replaceAll(
				"\\^\\^http://www.w3.org/2001/xmlschema#.*", "").replaceAll(
				"http:/.*?#|/", "");
		// .replaceAll("item", "")
		// .replaceAll("[^a-z0-9\\s\\-\\.]+"," ")
		// .replaceAll(" \\.", "");
		String[] org_words1 = concept1.replaceAll("[^a-z\\s]+", "").split(
				"\\s+");

		concept2 = concept2.replaceAll(
				"\\^\\^http://www.w3.org/2001/xmlschema#.*", "").replaceAll(
				"http:/.*?#|/", "");
		// .replaceAll("item", "")
		// .replaceAll("[^a-z0-9\\s]+"," ")
		// .replaceAll(" \\.", "");
		String[] org_words2 = concept2.replaceAll("[^a-z\\s]+", "").split(
				"\\s+");

		

		// ArrayList<String> words1 = removeMeaningless(org_words1);
		// ArrayList<String> words2 = removeMeaningless(org_words2);
		ArrayList<String> words1 = new ArrayList<String>(
				Arrays.asList(org_words1));
		ArrayList<String> words2 = new ArrayList<String>(
				Arrays.asList(org_words2));

		double commons = 0;
		for (int i = 0; i < words1.size(); i++) {
			for (int j = 0; j < words2.size(); j++) {
				if ((words1.get(i).equals(words2.get(j)))) {// ||
															// specialMatch(words1.get(i),words2.get(j))==1)){
					words2.set(j, " ");
					commons = commons + 2;
					break;
				}
			}

		}

		return commons / ((double) (words1.size() + words2.size()));

	}

	public void setLevel(int level) {
		highlevel = level;
	}

	public void setWeights(Map<Property, Double> weightVector) {
		// TODO Auto-generated method stub

	}

	public String explainSimilarity(RDFNode r1, Model m1, RDFNode r2, Model m2,
			int depth, double score) {
		return r1.toString() + " vs. " + r2.toString() + " : " + score;
	}



}
