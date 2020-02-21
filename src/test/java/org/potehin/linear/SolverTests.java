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
        LinearModel linearModel = getTestLinearModel();
        Assertions.assertTrue(linearModel.hasNextIteration());
        solver.solve(linearModel);
    }

    @Test
    public void getConstantArrayTest(){
        LinearModel linearModel = getTestLinearModel();
        System.out.println("Iteration 1");
        System.out.println(linearModel);
        linearModel.iterate();
        System.out.println("Iteration 1");
        System.out.println(linearModel);
        linearModel.iterate();
        System.out.println("Iteration 2");
        System.out.println(linearModel);
        linearModel.iterate();
        System.out.println("Iteration 3 ");
        System.out.println(linearModel);

        Assertions.assertFalse(linearModel.hasNextIteration());
        System.out.println(linearModel.getResults());
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

    private LinearModel getTestLinearModel() {

        Set<Variable> variables = new LinkedHashSet<>();
        variables.add( Variable.of("x1"));
        variables.add( Variable.of("x2"));

        Map<String, Float> a1 = new LinkedHashMap<>();
        a1.put("x1", 2f);   a1.put("x2", 1f);

        Map<String, Float> a2 = new LinkedHashMap<>();
        a2.put("x1", 1f);   a2.put("x2", 3f);

        Map<String, Float> a3 = new LinkedHashMap<>();
        a3.put("x1", 0f);   a3.put("x2", 1f);

        Map<String, Float> z = new LinkedHashMap<>();
        z.put("x1", 4f);
        z.put("x2", 6f);

        return LinearModel.create()
                .setVariables(variables)
                .addEquation(Equation.of(a1, RatioType.LESS_EQUAL, 64))
                .addEquation(Equation.of(a2, RatioType.LESS_EQUAL, 72))
                .addEquation(Equation.of(a3, RatioType.LESS_EQUAL, 20))
                .setObjective(Objective.maximizeBy(z)).init();
    }


}
