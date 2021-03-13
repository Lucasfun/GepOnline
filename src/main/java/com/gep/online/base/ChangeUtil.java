package com.gep.online.base;

class ChangeUtil {
    static Gene change(GepStarter gs, Gene gene, Gene[] genes) {
        //复制
        Gene g = gene;
        //变异
        g = dcMutation(gs, g);
        //转座
        g = ISTransposition(gs, g);
        g = RISTransposition(gs, g);
        g = GeneTranspositionRate(gs, g);
        //重组
        g = onePointRecombination(gs, g, genes);
        g = twoPointRecombination(gs, g, genes);
        g = recombination(gs, g, genes);
        return g;
    }

    private static Gene dcMutation(GepStarter gs, Gene gene) {
        if (Math.random() > gs.DcMutationRate) {
            return gene;
        }
        int r1 = RandomUtil.getRandom(gs.numOfGenes);
        String s = gene.getGene()[r1];
        int i = RandomUtil.getRandom(gs.geneLength);
        char[] chars = Char2strUtil.str2char(s);
        if (i < gs.headLength) {
            chars[i] = gs.set[RandomUtil.getRandom(gs.set.length)];
        } else {
            chars[i] = gs.termSet[RandomUtil.getRandom(gs.termSet.length)];
        }
        gene.setGeneN(Char2strUtil.char2str(chars), r1);
        return gene;
    }

    private static Gene onePointRecombination(GepStarter gs, Gene gene, Gene[] genes) {
        if (Math.random() > gs.onePointRecombinationRate) {
            return gene;
        }
        int random = RandomUtil.getRandom(gs.populationsSize);
        Gene orgGene = genes[random];
        int len = RandomUtil.getRandom(gs.geneLength);
        int no = RandomUtil.getRandom(gs.numOfGenes);
        String org = orgGene.getGene()[no];
        String det = gene.getGene()[no];
        gene.setGeneN(det.substring(0, len) + org.charAt(len) + det.substring(len + 1, gs.geneLength), no);
        return gene;
    }

    private static Gene twoPointRecombination(GepStarter gs, Gene gene, Gene[] genes) {
        if (Math.random() > gs.twoPointRecombinationRate) {
            return gene;
        }
        int length = RandomUtil.getRandom(gs.geneLength - 1, 1);
        int random = RandomUtil.getRandom(gs.populationsSize);
        Gene orgGene = genes[random];
        int len = RandomUtil.getRandom(gs.geneLength - length);
        int no = RandomUtil.getRandom(gs.numOfGenes);
        String org = orgGene.getGene()[no];
        String det = gene.getGene()[no];
        gene.setGeneN(det.substring(0, len) + org.substring(len, len + length) + det.substring(len + length,
                gs.geneLength), no);
        return gene;
    }

    private static Gene recombination(GepStarter gs, Gene gene, Gene[] genes) {
        if (Math.random() > gs.recombinationRate) {
            return gene;
        }
        int random = RandomUtil.getRandom(gs.populationsSize);
        Gene orgGene = genes[random];
        int no = RandomUtil.getRandom(gs.numOfGenes);
        String org = orgGene.getGene()[no];
        gene.setGeneN(org, no);
        return gene;
    }

    private static Gene ISTransposition(GepStarter gs, Gene gene) {
        if (Math.random() > gs.ISTranspositionRate) {
            return gene;
        }
        int no = RandomUtil.getRandom(gs.numOfGenes);
        String s = gene.getGene()[no];
        int random = (int) (Math.random() * gs.ISElementsLength.length);
        int start =
                RandomUtil.getRandom(gs.geneLength - gs.ISElementsLength[random], 1);
        int end = start + gs.ISElementsLength[random];
        int weihzhi = RandomUtil.getRandom(gs.headLength, 1);
        String head = s.substring(0, gs.headLength);
        String tail = s.substring(gs.headLength);
        String ss = s.substring(start, end);
        ss = head.substring(0, weihzhi) + ss + head.substring(weihzhi);
        ss = ss.substring(0, gs.headLength);
        ss = ss + tail;
        gene.setGeneN(ss, no);
        return gene;
    }

    private static Gene RISTransposition(GepStarter gs, Gene gene) {
        if (Math.random() > gs.RISTranspositionRate) {
            return gene;
        }
        int no = RandomUtil.getRandom(gs.numOfGenes);
        String s = gene.getGene()[no];
        int start;
        int end;
        int random = (int) (Math.random() * gs.ISElementsLength.length);
        for (int i = 0; i < 20; i++) {
            start = RandomUtil.getRandom(gs.geneLength - gs.RISElementsLength[random], 1);
            end = start + gs.RISElementsLength[random];
            for (char c : gs.funSet) {
                if (s.toCharArray()[start] == c) {
                    String head = s.substring(0, gs.headLength);
                    String tail = s.substring(gs.headLength);
                    String ss = s.substring(start, end);
                    ss += head;
                    ss = ss.substring(0, gs.headLength);
                    ss = ss + tail;
                    gene.setGeneN(ss, no);
                    return gene;
                }
            }
        }
        return gene;
    }

    private static Gene GeneTranspositionRate(GepStarter gs, Gene gene) {
        if (Math.random() > gs.GeneTranspositionRate) {
            return gene;
        }
        int no1 = RandomUtil.getRandom(gs.numOfGenes);
        int no2 = RandomUtil.getRandom(gs.numOfGenes);
        String s1 = gene.getGene()[no1];
        String s2 = gene.getGene()[no2];
        gene.setGeneN(s1, no2);
        gene.setGeneN(s2, no1);
        return gene;
    }
}
