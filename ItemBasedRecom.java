package quan_009276839;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.GenericUserSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class ItemBasedRecom {

	public static void main(String[] args) throws IOException, TasteException {
		// TODO Auto-generated method stub

		DataModel model = new FileDataModel(new File("/Users/AaronNguyen/Desktop/ratings-4.csv"));
		
		/*
		//ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
		//ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
		//ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);
		ItemSimilarity similarity = new EuclideanDistanceSimilarity(model);
		//ItemSimilarity similarity = new GenericUserSimilarity(model);
		
		final ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
		
		List<RecommendedItem> recommendations = recommender.recommend(2, 3);
		for (RecommendedItem recommendation : recommendations) {
		  System.out.println(recommendation);
		}
		*/
		
		
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {                
                
            	//ItemSimilarity similarity = new EuclideanDistanceSimilarity(model); 
                //ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        		//ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
        		ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);
        		//ItemSimilarity similarity = new GenericUserSimilarity(model);
                        
                //Optimizer optimizer = new NonNegativeQuadraticOptimizer();
                //UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
                return new GenericItemBasedRecommender(model,similarity);                
            }
        };
        

        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();        
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.8, 1.0);    
        System.out.println("RMSE: " + score);
            
            RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();        
            IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, 4, 0.7); // evaluate precision recall at 10
            
        System.out.println("Precision: " + stats.getPrecision());
        System.out.println("Recall: " + stats.getRecall());
        System.out.println("F1 Score: " + stats.getF1Measure()); 
		
		  
	}

}

