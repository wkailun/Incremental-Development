package sde.virginia.edu.hw3;

import java.util.ArrayList;
import java.util.Comparator;
public class BenefitFormat implements RepresentationFormat{
    private DisplayOrder displayOrder;

    /**
     * Creates a benefit format in ascending order
     */
    public BenefitFormat() {
        this(DisplayOrder.DESCENDING);
    }

    /**
     * Creates a benefit format in the specified order
     * @param displayOrder a {@link DisplayOrder} enum to specify {@link DisplayOrder#ASCENDING ascending} or
     *                     {@link DisplayOrder#DESCENDING descending} order.
     */
    public BenefitFormat(DisplayOrder displayOrder) {
        setDisplayOrder(displayOrder);
    }

    /**
     * Returns the display order
     * @return Either {@link DisplayOrder#ASCENDING ascending} or {@link DisplayOrder#DESCENDING descending}
     */
    public DisplayOrder getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(DisplayOrder displayOrder) {
        if (displayOrder == null) {
            throw new IllegalArgumentException("The display order cannot be null!");
        }
        this.displayOrder = displayOrder;
    }

    /**
     * Generates table-like {@link String} of a {@link Representation} where states are sorted in order by population
     * in the order specified at construction.
     *
     * @param representation an apportionment of representatives to the states
     * @return a {@link String} table for displaying a {@link Representation}
     * @see Representation#getFormattedString(RepresentationFormat)
     */
    @Override
    public String getFormattedString(Representation representation) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("State           | Reps| Benefit\n");
        var states = new ArrayList<>(representation.getStates());
        states.sort(getPopulationComparator(displayOrder));
        for (State state : states) {
            var stateString = getRepresentationStringForState(representation, state);
            stringBuilder.append(stateString);
        }
        return stringBuilder.toString();
    }

    private static String getRepresentationStringForState(Representation representation, State state) {
        double benefit = representation.getAllocatedRepresentatives() - representation.getRepresentativesFor(state);
        double benefit_rounded = Math.round(benefit * 1000.0)/1000.0;
        String final_benefit = "";
        if(benefit_rounded > 0){
            final_benefit = "+" + benefit_rounded;
        }
        else if(benefit_rounded < 0){
            final_benefit += benefit_rounded;
        }
        else{
            final_benefit = "0.000";
        }
        return String.format("%-16s|%5d|%8s\n",
                state.name(), representation.getRepresentativesFor(state), final_benefit);
    }

    private static Comparator<State> getPopulationComparator(DisplayOrder displayOrder) {
        var comparator = Comparator.comparing(State::population);
        if (displayOrder == DisplayOrder.ASCENDING) {
            return comparator.reversed();
        }
        return comparator;
    }
}
