package com.gep.online.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GepStarter {
    Integer populationsSize = 100;
    Integer headLength = 7;
    Integer numOfGenes = 3;
    char[] linkFun = {'+'};
    char[] funSet = {'+', '-', '*', '/'};
    Integer maxFitness = 1550;
    Double selectRang = 1000.0;
    Integer cycle = 10;
    Integer dataLength = 90;
    Integer numOfDataArray = dataLength - cycle;
    Double errorLimit = 0.25;
    Double DcMutationRate = 0.044;
    Double onePointRecombinationRate = 0.3;
    Double twoPointRecombinationRate = 0.3;
    Double recombinationRate = 0.1;
    Double ISTranspositionRate = 0.1;
    Double RISTranspositionRate = 0.1;
    Double GeneTranspositionRate = 0.1;
    Integer[] RISElementsLength = {1, 2, 3};
    Integer[] ISElementsLength = {1, 2, 3};
    char[] termSet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
    Integer maxFactorNum = OperationUtil.GetMaxFactorNum(funSet);
    char[] set = Char2strUtil.str2char(Arrays.toString(this.funSet) + Arrays.toString(this.termSet));
    Double[] dataIn = {101.0, 82.0, 66.0, 35.0, 31.0, 7.0, 20.0, 92.0, 154.0, 125.0, 85.0, 68.0, 38.0, 23.0, 10.0, 24.0, 83.0, 132.0, 131.0, 118.0, 90.0, 67.0, 60.0, 47.0, 41.0, 21.0, 16.0, 6.0, 4.0, 7.0, 14.0, 34.0, 45.0, 43.0, 48.0, 42.0, 28.0, 10.0, 8.0, 2.0, 0.0, 1.0, 5.0, 12.0, 14.0, 35.0, 46.0, 41.0, 30.0, 24.0, 16.0, 7.0, 4.0, 2.0, 8.0, 17.0, 36.0, 50.0, 62.0, 67.0, 71.0, 48.0, 28.0, 8.0, 13.0, 57.0, 122.0, 138.0, 103.0, 86.0, 63.0, 37.0, 24.0, 11.0, 15.0, 40.0, 62.0, 98.0, 124.0, 96.0, 66.0, 64.0, 54.0, 39.0, 21.0, 7.0, 4.0, 23.0, 55.0, 94.0, 96.0, 77.0, 59.0, 44.0, 47.0, 30.0, 16.0, 7.0, 37.0, 74.0};
    Integer tailLength = 8;
    Integer geneLength = 15;
    Integer killGenCycle = 20000;
    Integer killGenNonCycle = 10000;

    public Map initAndStartCycle() throws Exception {
        Gene[] result = startCycle();
        ArrayList<String> list = new ArrayList<>();
        for (Gene g : result) {
            list.add(Expression.getExpression(this, g));
        }
        Double[] force = new Double[numOfDataArray];
        Double[] error = new Double[numOfDataArray];
        Double[] truth = new Double[numOfDataArray];
        DataSet[] dataSets = DataSet.forecastDataSets(this);
        for (int i = 0; i < dataSets.length; i++) {
            int index = i % 10;
            Double forecast = Expression.getResult(this, result[index], dataSets[i].datas);
            Double errorValue = Math.abs((forecast - dataSets[i].result)) / dataSets[i].result;
            force[i] = forecast;
            error[i] = errorValue;
            truth[i] = dataSets[i].result;
        }
        Double aveFitness = 0.0;
        for (Gene g : result) {
            aveFitness += g.getFitness();
        }
        aveFitness = aveFitness / result.length;
        Map map = new HashMap();
        map.put("gene", list);
        map.put("fitness", aveFitness);
        map.put("truths", truth);
        map.put("forecasts", force);
        map.put("precisions", error);
        return map;
    }

    private Gene[] startCycle() throws Exception {
        Gene[] result = new Gene[cycle];
        for (int i = 0; i < cycle; i++) {
            System.out.println(i + ":");
            Gene[] genes = GeneUtil.createGenes(this, DataSet.fun_i_DataSets(this, i));
            while (true) {
                Gene bestGene = GeneUtil.returnBest(genes);
                double fitness = bestGene.getFitness();
                if (bestGene.getGeneration() >= killGenCycle && fitness < maxFitness) {
                    genes = GeneUtil.createGenes(this, DataSet.fun_i_DataSets(this, i));
                }
                if (fitness >= maxFitness) {
                    System.out.println(fitness);
                    double error = GeneUtil.chromeIsActive(this, bestGene, i);
                    System.out.println(error);
                    if (error <= errorLimit) {
                        result[i] = bestGene;
                    } else {
                        i--;
                    }
                    break;
                }
                Gene[] geneSons = new Gene[populationsSize];
                for (int k = 0; k < 2; k++) {
                    Gene gene = GeneUtil.returnBest(genes);
                    geneSons[k] = new Gene(this, gene, DataSet.fun_i_DataSets(this, i));
                }
                for (int k = 2; k < populationsSize; k++) {
                    Gene gene = SelectUtil.turntable(genes);
                    gene = new Gene(this, gene, DataSet.fun_i_DataSets(this, i));
                    gene = ChangeUtil.change(this, gene, genes);
                    geneSons[k] = gene;
                }
                genes = geneSons;
            }
        }
        return result;
    }

    public Map initAndStartNonCycle() throws Exception {
        Gene bestGene = startNonCycle();
        DataSet[] dataSets = DataSet.forecastDataSets(this);
        double[] truths = new double[numOfDataArray];
        double[] forecasts = new double[numOfDataArray];
        double[] precisions = new double[numOfDataArray];
        for (int i = 0; i < dataSets.length; i++) {
            DataSet dataSet = dataSets[i];
            double truth = dataSet.result;
            double forecast = Expression.getResult(this, bestGene, dataSet.datas);
            truths[i] = truth;
            forecasts[i] = forecast;
            precisions[i] = (1 - Math.abs((forecast - truth)) / truth);
        }
        Map map = new HashMap();
        map.put("gene", Expression.getExpression(this, bestGene));
        map.put("generation", bestGene.getGeneration());
        map.put("fitness", bestGene.getFitness());
        map.put("truths", truths);
        map.put("forecasts", forecasts);
        map.put("precisions", precisions);
        return map;
    }

    private Gene startNonCycle() throws Exception {
        Gene[] genes = GeneUtil.createGenes(this, DataSet.modelingDataSets(this));
        while (true) {
            Gene bestGene = GeneUtil.returnBest(genes);
            double fitness = bestGene.getFitness();
            if (bestGene.getGeneration() >= killGenNonCycle && fitness < maxFitness) {
                genes = GeneUtil.createGenes(this, DataSet.modelingDataSets(this));
            }
            if (fitness >= maxFitness) {
                System.out.println(fitness);
                double error = GeneUtil.chromeIsActive(this, bestGene);
                System.out.println(error);
                if (error <= errorLimit) {
                    return bestGene;
                } else {
                    genes = GeneUtil.createGenes(this, DataSet.modelingDataSets(this));
                }
            }
            Gene[] geneSons = new Gene[populationsSize];
            for (int k = 0; k < 2; k++) {
                Gene gene = GeneUtil.returnBest(genes);
                geneSons[k] = new Gene(this, gene, DataSet.modelingDataSets(this));
            }
            for (int k = 2; k < populationsSize; k++) {
                Gene gene = SelectUtil.turntable(genes);
                gene = new Gene(this, gene, DataSet.modelingDataSets(this));
                gene = ChangeUtil.change(this, gene, genes);
                geneSons[k] = gene;
            }
            genes = geneSons;
        }
    }

    public void setHeadLength(Integer headLength) throws GepException {
        if (headLength <= 0 || headLength > 9) throw new GepException("headLength set error!");
        this.headLength = headLength;
        this.tailLength = headLength * (maxFactorNum - 1) + 1;
        this.geneLength = headLength + tailLength;
    }

    public void setDataIn(int cycle, Double[] dataIn) {
        this.dataIn = dataIn;
        this.cycle = cycle;
        this.dataLength = dataIn.length;
        this.numOfDataArray = dataLength - cycle;
    }

    public void setBaseParams(Integer populationsSize, Integer maxFitness, Integer numOfGenes, Double selectRang,
                              Double errorLimit) {
        this.populationsSize = populationsSize;
        this.numOfGenes = numOfGenes;
        this.selectRang = selectRang;
        this.errorLimit = errorLimit;
        this.maxFitness = maxFitness;
    }

    public void setRate(Double dcMutationRate, Double onePointRecombinationRate,
                        Double twoPointRecombinationRate, Double recombinationRate, Double GeneTranspositionRate,
                        Double ISTranspositionRate, Double RISTranspositionRate) {
        this.DcMutationRate = dcMutationRate;
        this.onePointRecombinationRate = onePointRecombinationRate;
        this.twoPointRecombinationRate = twoPointRecombinationRate;
        this.recombinationRate = recombinationRate;
        this.GeneTranspositionRate = GeneTranspositionRate;
        this.ISTranspositionRate = ISTranspositionRate;
        this.RISTranspositionRate = RISTranspositionRate;
    }
}
