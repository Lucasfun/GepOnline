package com.gep.online.base;

import java.util.Arrays;

class GeneUtil {
    private static Gene createRandomGene(GepStarter gs) {
        Gene gene = new Gene(gs);
        for (int k = 0; k < gs.numOfGenes; k++) {
            char[] temp = new char[gs.geneLength];
            for (int i = 0; i < gs.headLength; i++) {
                temp[i] = (gs.set[RandomUtil.getRandom(gs.set.length)]);
            }
            for (int i = 0; i < gs.tailLength; i++) {
                temp[i + gs.headLength] = (gs.termSet[RandomUtil.getRandom(gs.termSet.length)]);
            }
            gene.setGeneN(new String(temp), k);
        }
        return gene;
    }

    static Gene[] createGenes(GepStarter gs, DataSet[] dataSets) {
        Gene[] genes = new Gene[gs.populationsSize];
        for (int i = 0; i < gs.populationsSize; i++) {
            genes[i] = createRandomGene(gs);
            genes[i].setFitness(OperationUtil.getFitness(gs, genes[i], dataSets));
        }
        return genes;
    }

    static void addGenes(Gene[] genes) {
        for (Gene g : genes) {
            g.setGeneration(g.getGeneration() + 1);
        }
    }

    static void showGene(GepStarter gs, Gene gene) {
        for (int i = 0; i < gs.numOfGenes; i++) {
            System.out.print(gene.getGene()[i]);
            if (i < gs.numOfGenes - 1) {
                System.out.print(" " + Arrays.toString(gs.linkFun) + " ");
            }
        }
        System.out.printf(" = %.5f\n", gene.getFitness());
    }

    static void showGenes(GepStarter gs, Gene[] genes) {
        System.out.println("Generation:[" + genes[0].getGeneration() + "]");
        GeneUtil.showGene(gs, GeneUtil.returnBest(genes));
    }

    static Gene returnBest(Gene[] genes) {
        int best = 0;
        for (int i = 0; i < genes.length - 1; i++) {
            if (genes[best].getFitness() < genes[i + 1].getFitness()) {
                best = i + 1;
            }
        }
        return genes[best];
    }

    static boolean isEnd(Gene[] genes, double bestNum, int maxGeneration) {
        if (genes[0].getGeneration() >= maxGeneration) return true;
        for (Gene gene : genes) {
            if (gene.getFitness() >= bestNum) {
                return true;
            }
        }
        return false;
    }

    static double chromeIsActive(GepStarter gs, Gene gene, int n) {
        DataSet[] dataSets = DataSet.fun_i_DataSets(gs, n);
        double aveError = 0.0;
        double sumError = 0.0;
        for (int i = 0; i < dataSets.length; i++) {
            double truth = dataSets[i].result;
            double forecast = Expression.getResult(gs, gene, dataSets[i].datas);
            if (truth == 0) {
                if (forecast <= 0.1) sumError += 0.05;
                else if (forecast <= 5) sumError += 0.2;
                else sumError += 1.0;
            } else {
                sumError += Math.abs(truth - forecast) / truth;
            }
        }
        aveError = sumError / dataSets.length;
        return aveError;
    }

    static double chromeIsActive(GepStarter gs, Gene gene) {
        DataSet[] dataSets = DataSet.modelingDataSets(gs);
        double aveError = 0.0;
        double sumError = 0.0;
        for (DataSet dataSet : dataSets) {
            double truth = dataSet.result;
            double forecast = Expression.getResult(gs, gene, dataSet.datas);
            if (truth == 0) {
                if (forecast <= 0.1) sumError += 0.05;
                else if (forecast <= 5) sumError += 0.2;
                else sumError += 1.0;
            } else {
                sumError += Math.abs(truth - forecast) / truth;
            }
        }
        aveError = sumError / dataSets.length;
        return aveError;
    }
}
