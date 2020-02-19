package org.potehin.linear;


public class Solver<T extends Number,V extends Number> {

    public void solve(LinearModel linearModel){
        if(linearModel == null){
            throw new IllegalArgumentException("linearModel is null");
        }
        if(!linearModel.valid()){
            throw new IllegalArgumentException("linearModel is invalid");
        }

        float[][] a = linearModel.getConstants();   // tableaux
        int m = a.length;          // number of constraints
        int n = a[0].length;          // number of original variables

        int[] basis = getInitialBasis(m);   // basis[i] = basic variable corresponding to row i
                        // only needed to print out solution, not book
    }


    private  static int[] getInitialBasis(int m){
        int[] basis = new int[m];
        for (int j = 0; j < m; j++) {
            basis[j] = j;
        }
        return basis;
    }



    public static float det(float[][]a, int[]rows, int[]columns){
        if(rows.length!=columns.length){
            throw new IllegalArgumentException("Illegal non square matrix");
        }
        if(rows.length < 2){
            throw new IllegalArgumentException("Illegal matrix size "+ rows.length);
        }

        if(rows.length==2){
            return a[rows[0]][columns[0]] * a[rows[1]][columns[1]] -
                    a[rows[0]][columns[1]] * a[rows[1]][columns[0]];
        }
        float result = 0;
        boolean negative = false;
        for(int j=0; j<columns.length; j++){
            float multiplier = a[rows[0]][columns[j]];
            float value = multiplier == 0? 0 : multiplier *
                    det(a,subArray(rows,0),subArray(columns,j));
            result = negative ? result - value : result + value;
            negative = !negative; //inverse flag
        }
        return result;
    }

    public static int[] subArray(int[] rows, int excepted) {
        if(excepted > rows.length-1 || excepted <0){
            throw new IllegalArgumentException("Excepted index is out of bonds");
        }
        int[] subArray = new int[rows.length-1];
        if(excepted  >0){
            System.arraycopy(rows,0,subArray,0,excepted);
        }
        if(excepted <rows.length-1){
            System.arraycopy(rows,excepted+1,subArray,excepted,rows.length-1-excepted);
        }
        return subArray;
    }
}
