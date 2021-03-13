package com.gep.online.base;

class DataSet {
    Double[] datas;
    Double result;

    DataSet(Double[] datas, Double result) {
        this.datas = datas;
        this.result = result;
    }

    public Double[] getDatas() {
        return datas;
    }

    public Double getResult() {
        return result;
    }

    public static DataSet[] modelingDataSets(GepStarter gs) {
        DataSet[] dataSets = new DataSet[gs.numOfDataArray - gs.cycle];
        for (int i = 0; i < gs.numOfDataArray - gs.cycle; i++) {
            Double[] temp = new Double[gs.cycle];
            for (int j = 0; j < gs.cycle; j++) {
                temp[j] = gs.dataIn[i + j];
            }
            dataSets[i] = new DataSet(temp, gs.dataIn[i + gs.cycle]);
        }
        return dataSets;
    }

    public static DataSet[] fun_i_DataSets(GepStarter gs, int n) {
        DataSet[] dataSets = DataSet.modelingDataSets(gs);
        DataSet[] result = new DataSet[dataSets.length / gs.cycle];
        for (int j = 0; j < dataSets.length / gs.cycle; j++) {
            result[j] = dataSets[gs.cycle * j + n];
        }
        return result;
    }

    public static DataSet[] forecastDataSets(GepStarter gs) {
        DataSet[] dataSets = new DataSet[gs.numOfDataArray];
        for (int i = 0; i < gs.numOfDataArray; i++) {
            Double[] temp = new Double[gs.cycle];
            for (int j = 0; j < gs.cycle; j++) {
                temp[j] = gs.dataIn[i + j];
            }
            dataSets[i] = new DataSet(temp, gs.dataIn[i + gs.cycle]);
        }
        return dataSets;
    }
}
