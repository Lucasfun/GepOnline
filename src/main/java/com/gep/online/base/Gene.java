package com.gep.online.base;

import java.util.Arrays;

class Gene {
    private String[] gene;
    private int generation;
    private double fitness;

    Gene(GepStarter gs) {
        this.gene = new String[gs.numOfGenes];
        this.generation = 0;
        this.fitness = 0;
    }

    Gene(GepStarter gs, Gene gene, DataSet[] dataSets) {
        this.gene = new String[gs.numOfGenes];
        System.arraycopy(gene.getGene(), 0, this.gene, 0, gs.numOfGenes);
        this.generation = gene.getGeneration() + 1;
        this.fitness = OperationUtil.getFitness(gs, gene, dataSets);
    }

    String[] getGene() {
        return gene;
    }

    void setGeneN(String geneN, int n) {
        this.gene[n] = geneN;
    }

    int getGeneration() {
        return generation;
    }

    void setGeneration(int generation) {
        this.generation = generation;
    }

    double getFitness() {
        return fitness;
    }

    void setFitness(double fitness) {
        this.fitness = fitness;
    }

    String printGene(GepStarter gs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gs.numOfGenes; i++) {
            sb.append(this.getGene()[i]);
            if (i < gs.numOfGenes - 1) {
                sb.append(" ");
                sb.append(Arrays.toString(gs.linkFun));
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}