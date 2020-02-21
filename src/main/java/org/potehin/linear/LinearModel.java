package org.potehin.linear;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


public class LinearModel {

    private static final String Z = "Z";
    private final static String S = "S";

    private List<Equation> equations = new ArrayList<>();
    private Objective objective;
    private Variable[] variables;
    private Variable[] additions;

    private MatrixRow objectiveMatrixRow;
    private final List<MatrixRow> matrixRows = new LinkedList<>();

    private MatrixRow lastIteratedRow;

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

    public LinearModel init() {
        int numOfVariables = variables.length + equations.size();
        float[] objectiveVariables = new float[numOfVariables];
        for (int var = 0; var < variables.length; var++) {
            Variable variable = variables[var];
            objectiveVariables[var] = (-1) * this.objective.getConstant(variable.getName());
        }
        this.objectiveMatrixRow = MatrixRow.of(Z, objectiveVariables, 0);
        this.additions = new Variable[equations.size()];
        for (int eq = 0; eq < equations.size(); eq++) {
            Equation equation = equations.get(eq);
            String additionVariableLabel = getAdditionalLabel(eq);
            additions[eq] = Variable.of(additionVariableLabel, 0);
            float[] matrixRowInitialValues = new float[numOfVariables];
            for (int var = 0; var < variables.length; var++) {
                Variable variable = variables[var];
                matrixRowInitialValues[var] = equation.getConstant(variable.getName());
            }
            matrixRowInitialValues[variables.length + eq] = equation.getRatioType().getAdditionalValue();
            matrixRows.add(MatrixRow.of(additionVariableLabel, matrixRowInitialValues, equation.getRhs()));
        }
        return this;
    }

    private String getAdditionalLabel(int rowIndex) {
        return S + (rowIndex + 1);
    }


    public boolean hasNextIteration() {
        if (!valid()) {
            return false;
        }
        return objectiveMatrixRow.minNegativeValueIndex().isPresent();
    }

    public Optional<MatrixRow> nextIterationRow(int nextIterationColumnIndex) {
        return this.matrixRows.stream()
                .filter(row -> (lastIteratedRow == null || lastIteratedRow != row)
                        && row.getRhsDevidedByValue(nextIterationColumnIndex).isPresent())
                .min(Comparator.comparing(row ->
                        row.getRhsDevidedByValue(nextIterationColumnIndex).get())
                );
    }

    public void iterate() {
        Optional<Integer> minNegativeIndexOptional = objectiveMatrixRow.minNegativeValueIndex();

        if (!minNegativeIndexOptional.isPresent()) {
            throw new IllegalStateException("Have no any negative column index in objective row");
        }
        int iterationColumnIndex = minNegativeIndexOptional.get();
        Optional<MatrixRow> nextIterationRowOptional = this.nextIterationRow(iterationColumnIndex);
        if (!nextIterationRowOptional.isPresent()) {
            throw new IllegalStateException("Have no any row found for next iteration");
        }
        MatrixRow nextIterationRow = nextIterationRowOptional.get();

        float devider = nextIterationRow.getValue(iterationColumnIndex);

        this.objectiveMatrixRow.add(
                nextIterationRow
                        .multiplyAndClone((-1) * objectiveMatrixRow.getValue(iterationColumnIndex))
                        .devideByValue(devider));

        this.matrixRows.stream()
                .filter(row -> row != nextIterationRow)
                .forEach(row ->
                        row.add(nextIterationRow
                                .multiplyAndClone((-1) * row.getValue(iterationColumnIndex))
                                .devideByValue(devider)
                        )
                );

        nextIterationRow.devideByValue(devider);

        if(iterationColumnIndex<variables.length) {
            nextIterationRow.setLabel(variables[iterationColumnIndex].getName());
        }else{
            nextIterationRow.setLabel(additions[iterationColumnIndex-variables.length].getName());
        }
        this.lastIteratedRow = nextIterationRow;
    }

    public Map<String,Variable> getResults(){
        Map<String,Variable> results = Arrays.stream(this.variables)
                .collect(Collectors.toMap(Variable::getName,variable -> Variable.of(variable.getName())));
        this.matrixRows.forEach(matrixRow -> {
            if(results.containsKey(matrixRow.getLabel())){
                results.get(matrixRow.getLabel()).setValue(matrixRow.getRhsTotal());
            }
        });
        return results;
    }

    public boolean valid() {
        return this.objective != null && this.objectiveMatrixRow != null &&
                this.variables != null && this.variables.length > 0 &&
                !this.equations.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("|%5s |", ""));
        for (Variable variable : variables) {
            sb.append(String.format("%7s ", variable.getName()));
        }
        for (Variable variable : additions) {
            sb.append(String.format("%7s ", variable.getName()));
        }
        sb.append(String.format("|%7s |", ""));
        sb.append(System.lineSeparator());
        sb.append(this.objectiveMatrixRow).append(System.lineSeparator());
        this.matrixRows.forEach(matrixRow ->
                sb.append(matrixRow).append(System.lineSeparator())
        );
        return sb.toString();
    }
}
