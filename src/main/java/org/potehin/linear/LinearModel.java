package org.potehin.linear;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class LinearModel {

    private List<Equation> equations = new ArrayList<>();

    private Objective objective;

    private Variable[] variables;

    private LinearModel() {

    }

    public List<Equation> getEquations() {
        return equations;
    }

    public Objective getObjective() {
        return objective;
    }

    public Variable[] getVariables() {
        return variables;
    }

    public static LinearModel create() {
        return new LinearModel();
    }


    public LinearModel setVariables(Set<Variable> variables) {
        this.variables = new Variable[variables.size()];
        int index = 0;
        for (Variable variable : variables) {
            this.variables[index] = variable;
            index++;
        }
        return this;
    }


    public LinearModel addEquation(Equation equation) {
        this.equations.add(equation);
        return this;
    }

    public LinearModel setObjective(Objective objective) {
        this.objective = objective;
        return this;
    }

    public float[][] getConstants() {
        float[][] constants = new float[equations.size()][variables.length];

        for(int eq = 0; eq < equations.size(); eq++ ){
            Equation equation = equations.get(eq);
            for(int var = 0; var< variables.length ; var ++){
                Variable variable = variables[var];
                constants[eq][var] = equation.getConstant(variable.getName());
            }
        }


        return constants;
    }

    public boolean valid() {
        return this.objective != null &&
                this.variables != null && this.variables.length > 0 &&
                !this.equations.isEmpty();
    }
}
