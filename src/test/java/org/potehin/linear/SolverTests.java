package org.potehin.linear;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


class SolverTests {

    @Test
    public void solveTest() {

        Solver<Integer, Integer> solver = new Solver<>();
        LinearModel linearModel = getIntegerLinearModel();
        solver.solve(linearModel);
    }

    @Test
    public void getConstantArrayTest(){
        LinearModel linearModel = getIntegerLinearModel();

        float[][] constants = linearModel.getConstants();
        assert constants[0][0]==1f;
        assert constants[0][1]==2f;
        assert constants[0][2]==3f;
    }

    @Test
    public void subArrayTest(){
        int[] array = {1,2,3,4,5};
        Assertions.assertArrayEquals(Solver.subArray(array,0),new int[]{ 2,3,4,5});
        Assertions.assertArrayEquals(Solver.subArray(array,1),new int[]{1 ,3,4,5});
        Assertions.assertArrayEquals(Solver.subArray(array,2),new int[]{1,2, 4,5});
        Assertions.assertArrayEquals(Solver.subArray(array,3),new int[]{1,2,3, 5});
    }

    @Test
    public void detTest(){
        //see lesson https://www.youtube.com/watch?v=3QJaBSWmMss
        float[][]a2 = {
                {2,3},
                {1,1}
        };
        Assertions.assertEquals(-1,Solver.det(a2,new int[]{0,1},new int[]{0,1}));
        float[][]a3 = {
                { 1, 2, 1},
                { 3,-1,-1},
                {-2, 2, 3}};
        Assertions.assertEquals(-11,Solver.det(a3,new int[]{0,1,2},new int[]{0,1,2}));
        float[][]a4 = {
                { 1,-2, 0, 0},
                { 3,-1,-1, 2},
                {-2, 2, 3, 1},
                { 1, 1, 2, 2}
        };
        Assertions.assertEquals(-13,Solver.det(a4,new int[]{0,1,2,3},new int[]{0,1,2,3}));

    }

    private LinearModel getIntegerLinearModel() {

        Variable x1 = Variable.of("x1", 0);
        Variable x2 = Variable.of("x2", 0);
        Variable x3 = Variable.of("x3", 0);
        Variable x4 = Variable.of("x4", 0);

        Set<Variable> variables = new LinkedHashSet<>();
        variables.add(x1);
        variables.add(x2);
        variables.add(x3);
        variables.add(x4);

        Map<String, Float> constants1 = new LinkedHashMap<>();
        constants1.put("x1", 1f);
        constants1.put("x2", 2f);
        constants1.put("x3", 3f);

        Map<String, Float> objectiveMap = new LinkedHashMap<>();
        objectiveMap.put("x1", 1f);
        objectiveMap.put("x2", 2f);
        objectiveMap.put("x3", 3f);

        return LinearModel.create()
                .setVariables(variables)
                .addEquation(Equation.of(constants1, RatioType.EQUAL, 0))
                .setObjective(Objective.maximizeBy(objectiveMap));
    }


}
