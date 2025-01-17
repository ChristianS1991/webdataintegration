package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.numeric;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.numeric.PercentageSimilarity;

/**
 * Average conflict resolution: Returns the average of all values
 * @author Oliver
 *
 * @param <RecordType>
 */
public class MinProvAverage<RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<Double, RecordType> {

	@Override
	public FusedValue<Double, RecordType> resolveConflict(
			Collection<FusableValue<Double, RecordType>> values) {
		
		
		if(values.size()==0) {
			return new FusedValue<>((Double)null);
		} else {
					
			
			double sum = 0.0;
			double count = 0.0;
			
			for(FusableValue<Double, RecordType> value : values) {
				if(value.getDataSourceScore()>0.6){
				
				sum += (Double) value.getValue();
				count++;
				
				
				}
			}
			return new FusedValue<>(sum / count);
		
		}
	}

}
