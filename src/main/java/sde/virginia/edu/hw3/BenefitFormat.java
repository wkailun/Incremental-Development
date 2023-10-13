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
     * Generates table-like {@link String} of a {@link Representation} where states are sorted in order by benefit
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
        states.sort(getBenefitComparator(displayOrder, representation));
        for (State state : states) {
            var stateString = getBenefitStringForState(representation, state);
            stringBuilder.append(stateString);
        }
        return stringBuilder.toString();
    }

    private static double getBenefit(Representation representation, State state){
        double totalpop = 0;
        for(State s: representation.getStates()){
            totalpop += s.population();
        }
        double divisor = totalpop/representation.getAllocatedRepresentatives();
        double quota = state.population()/divisor;
        double benefit = representation.getRepresentativesFor(state)- quota;
        return Math.round(benefit * 1000.0)/1000.0;
    }

    private static String getBenefitStringForState(Representation representation, State state) {
        double benefit = getBenefit(representation, state);
        String final_benefit = "";
        if(benefit > 0){
            final_benefit = "+" + benefit;
        }
        else if(benefit < 0){
            final_benefit += benefit;
        }
        else{
            final_benefit = "0.000";
        }
        return String.format("%-16s|%5d|%8s\n",
                state.name(), representation.getRepresentativesFor(state), final_benefit);
    }

    private static Comparator<State> getBenefitComparator(DisplayOrder displayOrder, Representation representation) {
        Comparator<State> comparator = (state1, state2) -> {
            double benefit1 = getBenefit(representation, state1);
            double benefit2 = getBenefit(representation, state2);
            if (displayOrder == DisplayOrder.ASCENDING) {
                return Double.compare(benefit1, benefit2);
            }
            else {
                return Double.compare(benefit2, benefit1);
            }
        };
        return comparator;
    }

}
