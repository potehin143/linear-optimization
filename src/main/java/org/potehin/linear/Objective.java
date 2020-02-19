package org.potehin.linear;

import java.util.Map;

public class Objective extends Expression {

    private ObjectiveType objectiveType;

    private Objective(){

    }

    public static Objective maximizeBy(Map<String,Float> constants){
        Objective objective = new Objective();
        objective.constants = constants;
        objective.objectiveType = ObjectiveType.MAXIMIZE;
        return objective;
    }
}
