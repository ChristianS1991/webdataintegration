package de.uni_mannheim.informatik.wdi.usecase.geography.evaluation.countries;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.numeric.PercentageSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.geography.FusableCountry;

public class LatitudeEvaluationRule extends EvaluationRule<FusableCountry>{
	
	PercentageSimilarity sim = new PercentageSimilarity(0.05d);

	@Override
	public boolean isEqual(FusableCountry record1, FusableCountry record2) {
		return sim.calculate(record1.getLatitude(), record2.getLatitude()) == 1.0;
	}
	

}
