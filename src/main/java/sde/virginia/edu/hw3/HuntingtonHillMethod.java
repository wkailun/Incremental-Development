package sde.virginia.edu.hw3;

import java.util.List;

public class HuntingtonHillMethod implements ApportionmentMethod{
    @Override
    public Representation getRepresentation(List<State> states, int targetRepresentatives) {
        validateInputs(states,targetRepresentatives);

        if (targetRepresentatives < states.size()){
            throw new UnsolvableApportionmentException(this,states,targetRepresentatives);
        }

        var allocated_reps = targetRepresentatives - states.size();
        var representation = new Representation();

        for (State state : states) {
            representation.setRepresentativesFor(state, 1);
        }

        while (allocated_reps > 0) {
            var max_priority = 0.0;

            for (int i = 0; i < states.size(); i++) {
                var current_state = states.get(i);
                var state_representatives = representation.getRepresentativesFor(current_state);
                var state_population = current_state.population();
                var state_priority = getPriority(state_population, state_representatives);

                if (state_priority > max_priority){
                    max_priority = state_priority;
                    representation.setRepresentativesFor(current_state, state_representatives + 1);
                }
            }
            allocated_reps--;
        }



        return representation;
    }

    public double getPriority(int state_population, int current_reps){
        return state_population/(Math.sqrt(current_reps * (current_reps + 1)));
    }
}
