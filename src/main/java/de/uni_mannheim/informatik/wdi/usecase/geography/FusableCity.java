package de.uni_mannheim.informatik.wdi.usecase.geography;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.wdi.datafusion.Fusable;

public class FusableCity extends City implements Fusable{
	
	public static final String ELEVATION = "Elevation";
	public static final String LATITUDE = "Latitude";
	public static final String LONGITUDE = "Longitude";
	public static final String NAME = "Name";
	public static final String POPULATION_DENSITY = "Population Density";
	public static final String POPULATION = "POPULATION";
	public static final String REGION = "REGION";
	public static final String RAINFALL = "RAINFALL";
	
	private Map<String, Collection <String>> provenance = new HashMap <>();
	
	public void setRecordProvenance(Collection<String> provenance){
		this.provenance.put("RECORD", provenance);
	}
	
	public Collection<String> getRecordProvenance(){
		return provenance.get("RECORD");
	}
	
	public void setAttributeProvenance(String attribute, Collection <String> provenance){
		this. provenance.put(attribute, provenance);
	}
	
	public Collection<String> getAttributeProvenance (String attribute){
		return provenance.get(attribute);
	}
	
	public String getMergedAttributeProvenance(String attribute){
		Collection<String> prov = provenance.get(attribute);
		
		if(prov!=null){
			return StringUtils.join(prov, "+");
		}else{
			return "";
		}
	}
	
	public FusableCity(String identifier, String provenance) {
		super(identifier, provenance);
	}

	@Override
	public Collection<String> getAttributeNames() {
		return Arrays.asList(new String[] {ELEVATION, LATITUDE, LONGITUDE, NAME, POPULATION_DENSITY, POPULATION, REGION, RAINFALL});
	}

	@Override
	public boolean hasValue(String attributeName) {
		switch(attributeName){
		case ELEVATION:
			return getElevation()!=null;
		case LATITUDE:
			return getLatitude()!=null;
		case LONGITUDE:
			return getLongitude()!=null;
		case NAME:
			return getName()!=null &&!getName().isEmpty();
		case POPULATION_DENSITY:
			return getPopulationDensity()!=null;
		case POPULATION:
			return getPopulation()!=null;
		case RAINFALL:
			return getRainfall()!=null;
		case REGION:
			return getRegion()!=null &&!getRegion().isEmpty();
		default:
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("[City: Name: %s / Lat %s / Lon %s / Ele %s /  Pop %s]", getName(), getLatitude(), getLongitude(), getElevation(), getPopulation());
	}

}
