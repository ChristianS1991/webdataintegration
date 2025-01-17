package de.uni_mannheim.informatik.wdi.usecase.geography.execution;

import java.io.File;
import java.util.List;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.PartitioningBlocker;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.GoldStandard;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.MatchingEvaluator;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.Performance;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.Correspondence;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.MatchingEngine;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecord;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecordCSVFormatter;
import de.uni_mannheim.informatik.wdi.usecase.geography.City;
import de.uni_mannheim.informatik.wdi.usecase.geography.CityFactory;
import de.uni_mannheim.informatik.wdi.usecase.geography.blockingfunctions.CityBlockingFunction;
import de.uni_mannheim.informatik.wdi.usecase.geography.comparators.city.CityLatitudeComparatorAbsolute;
import de.uni_mannheim.informatik.wdi.usecase.geography.comparators.city.CityLongtitudeComparatorAbsolute;
import de.uni_mannheim.informatik.wdi.usecase.geography.comparators.city.CityNameComparatorLevenshtein;

public class MatchDbpCitiesToGnCities {

    public static void main(String[] args) throws Exception{
        LinearCombinationMatchingRule<City> rule = new LinearCombinationMatchingRule<City>(0.98);
        
//        rule.addComparator(new CityNameComparatorLevenshtein(), 1d);
        rule.addComparator(new CityLatitudeComparatorAbsolute(), 0.5d);
        rule.addComparator(new CityLongtitudeComparatorAbsolute(), 0.5d);

        PartitioningBlocker<City> blocker = new PartitioningBlocker<City>(new CityBlockingFunction());
        
        MatchingEngine<City> engine = new MatchingEngine<City>(rule, blocker);
        
        
        DataSet<City> ds1 = new DataSet<City>();
        DataSet<City> ds2 = new DataSet<City>();
        ds1.loadFromXML(
                new File("usecase/geography/input/dbp_cities.xml"),
                new CityFactory(), "/countries/country/cities/city");
        ds2.loadFromXML(
                new File("usecase/geography/input/geonames_cities.xml"),
                new CityFactory(), "/countries/country/cities/city");
        
        List<Correspondence<City>> correspondences = engine.runMatching(ds1, ds2);
        
     // write the correspondences to the output file
//     	engine.writeCorrespondences(correspondences, new File("usecase/geography/output/dbp_cities_geonames_cities_correspondences.csv"));
        
        printCorrespondences(correspondences);
        System.out.println(correspondences.size());
        
     // load the gold standard (test set)
        GoldStandard gsTest = new GoldStandard();
        gsTest.loadFromCSVFile(new File(
                "usecase/geography/goldstandard/identityresolution/dbp_cities_geonames_cities.csv"));

        // evaluate the result
        MatchingEvaluator<City> evaluator = new MatchingEvaluator<>(true);
        Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);
        
        // print the evaluation result
        System.out.println(String.format(
                "Precision: %.4f\nRecall: %.4f\nF1: %.4f", perfTest.getPrecision(),
                perfTest.getRecall(), perfTest.getF1()));
        
//        DataSet<DefaultRecord> features = engine.generateTrainingDataForLearning(ds1, ds2, gsTest);
//        features.writeCSV(
//        		new File("usecase/geography/goldstandard/identityresolution/dbp_cities_geonames_cities.csv"), 
//        		new DefaultRecordCSVFormatter());
        
    }
    
    
    private static void printCorrespondences(List<Correspondence<City>> correspondences) {
        // sort the correspondences
        
        for(Correspondence<City> corr : correspondences){
            
//            if (corr.getSimilarityScore() < 0.8) {
                System.out.println(corr.getFirstRecord().getName() + "(" + corr.getFirstRecord().getIdentifier()
                        + ")\t" + corr.getSimilarityScore() + "\t" + corr.getSecondRecord().getName() + "("
                        + corr.getSecondRecord().getIdentifier() + ")");
//            }
        
        }
        
        
    }
    
    

}
