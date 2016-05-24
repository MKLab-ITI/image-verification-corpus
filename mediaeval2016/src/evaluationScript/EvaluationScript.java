import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EvaluationScript {

	
	public static void evaluate(HashMap<String,String> map_IdsLabels_groundtruth, HashMap<String,String> map_IdsLabels_predicted) {
			
		int counterPredictionsWithValidLabels = 0;
		
		int truePositives = 0;
		int trueNegatives = 0;
		int falsePositives = 0;
		int falseNegatives = 0;
		
		int sumPositiveInstances = 0;
		
		double precision = 0.0;
		double recall = 0.0;
		double fScore = 0.0;
		
		Iterator iter0 = map_IdsLabels_groundtruth.entrySet().iterator();
		while (iter0.hasNext()) {
			Map.Entry mEntry = (Map.Entry) iter0.next();
			
			if (mEntry.getValue().equals("fake")) {
				sumPositiveInstances++;
			}
		}
		
		Iterator iter = map_IdsLabels_predicted.entrySet().iterator();
		 
		while (iter.hasNext()) {
			Map.Entry mEntry = (Map.Entry) iter.next();
			String predicted = mEntry.getValue().toString();
			String actual 	 = map_IdsLabels_groundtruth.get(mEntry.getKey());
			
			
			if (predicted.equals("fake")) {
				counterPredictionsWithValidLabels++;
				if (predicted.equals(actual)) truePositives++;
				else falsePositives++;	
			}
			else if (predicted.equals("real")){
				counterPredictionsWithValidLabels++;
				if (predicted.equals(actual)) trueNegatives++;
				else falseNegatives++;	
			}
		}
		
		precision = (double) truePositives / (truePositives + falsePositives);
		recall	  = (double) truePositives / sumPositiveInstances;
		fScore	  = (double) 2*precision*recall / (precision+recall);
		
		System.out.println(counterPredictionsWithValidLabels+" out of "+map_IdsLabels_groundtruth.size() +" tweets predicted as fake or real.");
		System.out.println("Recall: "	 + recall);
		System.out.println("Precision: " + precision);
		System.out.println("F-Score: "	 + fScore);
	}
	
	public static HashMap<String, String> loadHashmapfromFile(String file)
			throws IOException {
		// load hashmap from file
		HashMap<String, String> hashmap = new HashMap<String, String>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = in.readLine()) != null) {
				String parts[] = line.split("\t");
				hashmap.put(parts[0], parts[1]);
			}
			in.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return hashmap;
	}
	
	public static void main(String[] args) {
		
		try {
			
			HashMap<String,String> map_IdsLabels_groundtruth = loadHashmapfromFile("");
			HashMap<String,String> map_IdsLabels_predicted   = loadHashmapfromFile("");
			
			evaluate(map_IdsLabels_groundtruth, map_IdsLabels_predicted);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
