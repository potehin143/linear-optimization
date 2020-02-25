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
        LinearModel linearModel = getSimpleLinearModel();
        Assertions.assertTrue(linearModel.hasNextIteration());
        solver.solve(linearModel);
    }

    @Test
    public void simpleSolverTest(){
        LinearModel linearModel = getSimpleLinearModel();
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
    public void simpleSolverTest2(){
        LinearModel linearModel = getSimpleLinearModel2();
        int counter = 0;
        System.out.println(linearModel);
        while (linearModel.hasNextIteration()){
            linearModel.iterate();
            counter++;
            System.out.println("Iteration "+counter);
            System.out.println(linearModel);
        }
        System.out.println(linearModel.getResults());
    }

    @Test
    public void complexSolverTest(){
        LinearModel linearModel = getComplexLinearModel();
        int counter = 0;
        System.out.println(linearModel);
        while (linearModel.hasNextIteration()){
            linearModel.iterate();
            counter++;
            System.out.println("Iteration "+counter);
            System.out.println(linearModel);
        }
        System.out.println(linearModel.getResults());
    }

    @Test
    public void complexSolverTest2(){
        LinearModel linearModel = getComplexLinearModel2();
        int counter = 0;
        System.out.println(linearModel);
        while (linearModel.hasNextIteration()){
            linearModel.iterate();
            counter++;
            System.out.println("Iteration "+counter);
            System.out.println(linearModel);
        }
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

    private LinearModel getSimpleLinearModel() {

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

    private LinearModel getSimpleLinearModel2(){

        Set<Variable> variables = new LinkedHashSet<>();
        variables.add( Variable.of("x1"));
        variables.add( Variable.of("x2"));

        Map<String, Float> a1 = new LinkedHashMap<>();
        a1.put("x1", 1f);   a1.put("x2", 3f);

        Map<String, Float> a2 = new LinkedHashMap<>();
        a2.put("x1", 2f);   a2.put("x2", 1f);

        Map<String, Float> a3 = new LinkedHashMap<>();
        a3.put("x1", 0f);   a3.put("x2", 1f);

        Map<String, Float> a4 = new LinkedHashMap<>();
        a4.put("x1", 3f);   a4.put("x2", 0f);

        Map<String, Float> z = new LinkedHashMap<>();
        z.put("x1", 2f);
        z.put("x2", 3f);

        return LinearModel.create()
                .setVariables(variables)
                .addEquation(Equation.of(a1,RatioType.LESS_EQUAL,18f))
                .addEquation(Equation.of(a2,RatioType.LESS_EQUAL,16f))
                .addEquation(Equation.of(a3,RatioType.LESS_EQUAL,5f))
                .addEquation(Equation.of(a4,RatioType.LESS_EQUAL,21f))
                .setObjective(Objective.maximizeBy(z))
                .init();
    }

    private LinearModel getComplexLinearModel(){

        Set<Variable> variables = new LinkedHashSet<>();
        variables.add( Variable.of("x1"));
        variables.add( Variable.of("x2"));
        variables.add( Variable.of("x3"));

        variables.add( Variable.of("x4"));
        variables.add( Variable.of("x5"));
        variables.add( Variable.of("x6"));

        variables.add( Variable.of("x7"));
        variables.add( Variable.of("x8"));
        variables.add( Variable.of("x9"));

        variables.add( Variable.of("x10"));
        variables.add( Variable.of("x11"));
        variables.add( Variable.of("x12"));

        variables.add( Variable.of("x13"));
        variables.add( Variable.of("x14"));
        variables.add( Variable.of("x15"));

        variables.add( Variable.of("x16"));
        variables.add( Variable.of("x17"));
        variables.add( Variable.of("x18"));

        Map<String, Float> a1 = new LinkedHashMap<>();
        a1.put("x1", 1f);   a1.put("x2", 1f);  a1.put("x3", 1f);

        Map<String, Float> a2 = new LinkedHashMap<>();
        a2.put("x4", 1f);   a2.put("x5", 1f);  a2.put("x6", 1f);
        a2.put("x7", 1f);   a2.put("x8", 1f);   a2.put("x9", 1f);

        Map<String, Float> a3 = new LinkedHashMap<>();
        a3.put("x10", 1f);   a3.put("x11", 1f); a3.put("x12", 1f);

        Map<String, Float> a4 = new LinkedHashMap<>();
        a4.put("x13", 1f);   a4.put("x14", 1f);  a4.put("x15", 1f);
        a4.put("x16", 1f);   a4.put("x17", 1f);  a4.put("x18", 1f);

        Map<String, Float> a5 = new LinkedHashMap<>();
        a5.put("x1", 1f);   a5.put("x4", 1f);  a5.put("x7", 1f);
        a5.put("x10", 1f);   a5.put("13", 1f);  a5.put("x16", 1f);

        Map<String, Float> a6 = new LinkedHashMap<>();
        a6.put("x2", 1f);   a6.put("x5", 1f); a6.put("x8", 1f);
        a6.put("x11", 1f);   a6.put("x14", 1f); a6.put("x17", 1f);

        Map<String, Float> a7 = new LinkedHashMap<>();
        a7.put("x3", 1f);   a7.put("x6", 1f); a7.put("x9", 1f);
        a7.put("x12", 1f);   a7.put("x15", 1f); a7.put("x18", 1f);

        Map<String, Float> z = new LinkedHashMap<>();
        z.put("x1", 150f); z.put("x2", 280f); z.put("x3", 400f);
        z.put("x4", 280f); z.put("x5", 450f); z.put("x6", 660f);
        z.put("x7", 250f); z.put("x8", 525f); z.put("x9", 760f);
        z.put("x10", 50f); z.put("x11", 75f); z.put("x12", 120f);
        z.put("x13", 75f); z.put("x14", 120f); z.put("x15", 175f);
         z.put("x16", 50f); z.put("x17", 200f); z.put("x18", 450f);

        return LinearModel.create()
                .setVariables(variables)
                .addEquation(Equation.of(a1,RatioType.LESS_EQUAL,70))
                .addEquation(Equation.of(a2,RatioType.LESS_EQUAL,30))

                .addEquation(Equation.of(a3,RatioType.LESS_EQUAL,70))
                .addEquation(Equation.of(a4,RatioType.LESS_EQUAL,30))

                .addEquation(Equation.of(a5,RatioType.LESS_EQUAL,30))
                .addEquation(Equation.of(a6,RatioType.LESS_EQUAL,30))
                .addEquation(Equation.of(a7,RatioType.LESS_EQUAL,40))

                .setObjective(Objective.maximizeBy(z))
                .init();
    }

    private LinearModel getComplexLinearModel2(){

        Set<Variable> variables = new LinkedHashSet<>();
        variables.add( Variable.of("x1"));
        variables.add( Variable.of("x2"));
        variables.add( Variable.of("x3"));

        variables.add( Variable.of("x4"));
        variables.add( Variable.of("x5"));
        variables.add( Variable.of("x6"));

        variables.add( Variable.of("x7"));
        variables.add( Variable.of("x8"));
        variables.add( Variable.of("x9"));


        Map<String, Float> a1 = new LinkedHashMap<>();
        a1.put("x1", 1f);   a1.put("x2", 1f);  a1.put("x3", 1f);

        Map<String, Float> a2 = new LinkedHashMap<>();
        a2.put("x4", 1f);   a2.put("x5", 1f);  a2.put("x6", 1f);
        a2.put("x7", 1f);   a2.put("x8", 1f);   a2.put("x9", 1f);

        Map<String, Float> a5 = new LinkedHashMap<>();
        a5.put("x1", 1f);   a5.put("x4", 1f);  a5.put("x7", 1f);

        Map<String, Float> a6 = new LinkedHashMap<>();
        a6.put("x2", 1f);   a6.put("x5", 1f); a6.put("x8", 1f);

        Map<String, Float> a7 = new LinkedHashMap<>();
        a7.put("x3", 1f);   a7.put("x6", 1f); a7.put("x9", 1f);

        Map<String, Float> z = new LinkedHashMap<>();
        z.put("x1", 150f); z.put("x2", 280f); z.put("x3", 400f);
        z.put("x4", 280f); z.put("x5", 450f); z.put("x6", 660f);
        z.put("x7", 250f); z.put("x8", 525f); z.put("x9", 760f);

        return LinearModel.create()
                .setVariables(variables)
                .addEquation(Equation.of(a1,RatioType.LESS_EQUAL,70))
                .addEquation(Equation.of(a2,RatioType.LESS_EQUAL,30))

                .addEquation(Equation.of(a5,RatioType.LESS_EQUAL,30))
                .addEquation(Equation.of(a6,RatioType.LESS_EQUAL,30))
                .addEquation(Equation.of(a7,RatioType.LESS_EQUAL,40))

                .setObjective(Objective.maximizeBy(z))
                .init();
    }
}
