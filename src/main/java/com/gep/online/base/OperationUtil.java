package com.gep.online.base;

class OperationUtil {
    static int GetOperationFactorNum(char operationName) {
        int num = -1;
        switch (operationName) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '%':
                num = 2;
                break;
            case 'Q':
            case 'N':
                num = 1;
                break;
            default:
        }
        return num;
    }

    static int GetMaxFactorNum(char[] operationNames) {
        int maxNum = 0;
        for (char b : operationNames) {
            int i = GetOperationFactorNum(b);
            if (i > maxNum) {
                maxNum = i;
            }
        }
        return maxNum;
    }

    static double getFitness(GepStarter gs, Gene gene, DataSet[] ds) {
        double allFitness;
        allFitness = 0;
        for (DataSet d : ds) {
            Double[] datas = d.datas;
            Double det_result = d.result;
            double result = Expression.getResult(gs, gene, datas);
            double fitness = gs.selectRang - Math.abs(result - det_result);
            allFitness += fitness;
        }
        if (allFitness < 0) allFitness = 0.0;
        return allFitness;
    }
}
