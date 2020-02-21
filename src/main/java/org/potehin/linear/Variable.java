package org.potehin.linear;

import java.util.Objects;

public class Variable{

    private String name;
    private float value;

    public String getName() {
        return name;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    private Variable(){

    }
    public static Variable of(String name, float value){
        Variable variable = new Variable();
        variable.value = value;
        variable.name = name;
        return variable;
    }

    public static Variable of(String name){
        Variable variable = new Variable();
        variable.value = 0;
        variable.name = name;
        return variable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;
        Variable variable = (Variable) o;
        return Objects.equals(getName(), variable.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return String.format("%f",value);
    }
}
