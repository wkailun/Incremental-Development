package sde.virginia.edu.hw3;

public class RepresentationFormatFactory {
    public RepresentationFormat getDefaultFormat(){
        return new AlphabeticalFormat();
    }

    public RepresentationFormat getFormat(String name){
        switch (name.toLowerCase()) {
            case "alphabet" -> {
                return new AlphabeticalFormat();
            }
            case "benefit" -> {
                return new BenefitFormat();
            }
            case "population" -> {
                return new PopulationFormat();
            }
        }
        throw new IllegalArgumentException("Please choose one of three valid formats (alphabet, benefit, population");
    }
    public RepresentationFormat getFormat(String name, DisplayOrder order){
        if (name.equalsIgnoreCase("alphabet")){
            return new AlphabeticalFormat();
        }
        if (name.equalsIgnoreCase("benefit")){
            return new BenefitFormat(order);
        }
        if (name.equalsIgnoreCase("population")){
            return new PopulationFormat(order);
        }
        throw new IllegalArgumentException("Please choose one of three valid formats (alphabet, benefit, population");
    }
}
