package org.potehin.linear;

import java.util.Map;

public class Equation extends Expression{

    private RatioType ratioType;
    private float rhs;

    public RatioType getRatioType() {
        return ratioType;
    }

    public Float getRhs() {
        return rhs;
    }

    private Equation(){

    }

    public static Equation of(
            Map<String,Float> constants, RatioType ratioType, float rhs){
        Equation equation = new Equation();
        //TODO Добавить проверку
        equation.constants = constants;
        equation.ratioType = ratioType;
        equation.rhs = rhs;
        return equation;
    }

}
