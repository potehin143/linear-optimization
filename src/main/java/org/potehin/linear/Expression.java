package org.potehin.linear;

import java.util.Map;

public abstract class Expression {

    protected Map<String,Float> constants;

    public Float getConstant(String name){
        return this.constants.getOrDefault(name,0f);
    }
}
