package de.uni_mannheim.informatik.wdi.usecase.geography.evaluation.cities;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.numeric.ContinuousPercentageSimlarity;
import de.uni_mannheim.informatik.wdi.usecase.geography.FusableCity;

public class PopulationEvaluationRule extends EvaluationRule<FusableCity>{
	
	ContinuousPercentageSimlarity sim = new ContinuousPercentageSimlarity();

	@Override
	public boolean isEqual(FusableCity record1, FusableCity record2) {
		if (record1.getPopulation() != null){
//			System.out.println("Population: " + record1.getPopulation() + " & " + record2.getPopulation() + " = "+ sim.calculate(record1.getPopulation(), record2.getPopulation()));
			return sim.calculate(record1.getPopulation(), record2.getPopulation()) > 0.98;
		}else{
			return true;
		}
		
	}
	

}