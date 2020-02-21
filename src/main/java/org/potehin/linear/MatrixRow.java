package org.potehin.linear;

import java.util.Optional;

public class MatrixRow {

    private String label = "";

    private float[] values = new float[0];

    private float rhsTotal;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }
    public float getValue(int index) {
        return values[index];
    }

    public float getRhsTotal() {
        return rhsTotal;
    }

    private MatrixRow() {

    }

    public MatrixRow multiplyAndClone(float multiplier) {
        MatrixRow result = MatrixRow.of(this.label,new float[values.length],this.rhsTotal*multiplier);

        for (int index = 0; index < values.length; index++) {
            result.values[index] = this.values[index] * multiplier;
        }
        return result;
    }

    public MatrixRow add(MatrixRow matrixRow) {
        for (int index = 0; index < values.length; index++) {
            values[index] += matrixRow.getValue(index);
        }
        this.rhsTotal += matrixRow.getRhsTotal();
        return this;
    }

    public boolean couldBeeDevidedByValue(int index) {
        return getValue(index) != 0;
    }

    public MatrixRow devideByValue(float value) {
        for (int i = 0; i < values.length; i++) {
            values[i] /= value;
        }
        this.rhsTotal = this.rhsTotal/value;
        return this;
    }

    public static MatrixRow of(String label,
                               float[] values,
                               float rhsTotal) {
        MatrixRow matrixRow = new MatrixRow();
        matrixRow.label = label;
        matrixRow.values = values;
        matrixRow.rhsTotal = rhsTotal;
        return matrixRow;
    }

    public Optional<Integer> minNegativeValueIndex() {
        return Optional.ofNullable(this.values)
                .filter(values -> values.length > 0)
                .map(values -> {
                    int result = 0;
                    for (int i = 0; i < values.length; i++) {
                        if (values[i] < values[result]) {
                            result = i;
                        }
                    }
                    return values[result] < 0 ? result : null;
                });
    }

    public Optional<Float> getRhsDevidedByValue(int index) {
        return Optional.ofNullable(this.values)
                .map(values -> values[index])
                .filter(value -> value != 0)//TODO добавить погрешность
                .map(value -> this.rhsTotal / value);

    }

    @Override
    public String toString() {
        String border = "|";
        StringBuilder sb = new StringBuilder();
        sb.append(border).append(String.format("%5S ", this.label)).append(border);
        for (float value : values) {
            sb.append(pretty(value));
        }
        sb.append(border).append(pretty(this.rhsTotal)).append(border);
        return sb.toString();
    }

    private static String pretty(float value) {
        return String.format("%7.2f ", value);
    }
}
