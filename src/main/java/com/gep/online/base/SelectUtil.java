package com.gep.online.base;

import java.util.ArrayList;
import java.util.List;

class SelectUtil {
    static Gene turntable(Gene[] genes) throws Exception {
        double allFit = 0;
        for (Gene g : genes) {
            allFit += g.getFitness();
        }
        double random = Math.random();
        double proabSum = 0;
        for (Gene gene : genes) {
            if (gene.getFitness() == 0) continue;
            proabSum += gene.getFitness() / allFit;
            if (proabSum >= random) {
                return gene;
            }
        }
        throw new Exception("");
    }

    public static Gene elite(Gene[] genes) {
        Gene geneR = genes[0];
        for (Gene gene : genes) {
            if (geneR.getFitness() < gene.getFitness()) {
                geneR = gene;
            }
        }
        return geneR;
    }

    public static Gene championship(int num, Gene[] genes) {
        List<Gene> genesR = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            genesR.add(genes[RandomUtil.getRandom(genes.length)]);
        }
        Gene geneR = genesR.get(0);
        for (Gene g : genesR) {
            if (geneR.getFitness() < g.getFitness()) {
                geneR = g;
            }
        }
        return geneR;
    }
}
