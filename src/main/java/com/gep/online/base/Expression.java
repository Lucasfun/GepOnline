package com.gep.online.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expression {
    public static double getResult(GepStarter gs, Gene gene, Double[] data) {
        int geneLength = 2 * gs.headLength + 1;
        double result = 0.0;
        for (int i = 0; i < gs.numOfGenes; i++) {
            char[] gene_to_char = gene.getGene()[i].toCharArray();
            double[] gene_to_double = new double[geneLength + 1];
            for (int j = 0; j < geneLength; j++) {
                for (int h = 0; h < gs.termSet.length; h++) {
                    if (gene_to_char[j] == gs.termSet[h]) {
                        gene_to_double[j + 1] = data[h];
                    }
                }
            }
            if (OperationUtil.GetOperationFactorNum(gene_to_char[0]) == -1) {
                for (int h = 0; h < gs.termSet.length; h++) {
                    if (gene_to_char[0] == gs.termSet[h]) {
                        result += data[h];
                    }
                }
            } else {
                for (int k = gs.headLength - 1, t = gs.headLength; k >= 0; k--, t--) {
                    if (gene_to_char[k] == 'S') {
                        gene_to_double[t] = Math.sin(gene_to_double[2 * t]);
                    }
                    if (gene_to_char[k] == 'C') {
                        gene_to_double[t] = Math.cos(gene_to_double[2 * t]);
                    }
                    if (gene_to_char[k] == 'Q') {
                        gene_to_double[t] = Math.cos(gene_to_double[2 * t]);
                    }
                    if (gene_to_char[k] == '+') {
                        gene_to_double[t] = gene_to_double[2 * t] + gene_to_double[2 * t + 1];
                    }
                    if (gene_to_char[k] == '-') {
                        gene_to_double[t] = gene_to_double[2 * t] - gene_to_double[2 * t + 1];
                    }
                    if (gene_to_char[k] == '*') {
                        gene_to_double[t] = gene_to_double[2 * t] * gene_to_double[2 * t + 1];
                    }
                    if (gene_to_char[k] == '/') {
                        if (gene_to_double[2 * t + 1] == 0) {
                            return 0.0;
                        }
                        gene_to_double[t] = gene_to_double[2 * t] / gene_to_double[2 * t + 1];
                    }
                }
                result += gene_to_double[1];
            }
        }
        if (result < 0) {
            result = Math.abs(result);
        }
        return result;
    }

    public Expression() {
        super();
    }

    static String getExpression(GepStarter gs, Gene gene) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < gs.numOfGenes; k++) {
            int i = 0;
            int j = 0;
            int pos = 0;
            char[] str2Char = gene.getGene()[k].toCharArray();
            ArrayList<Character> list = new ArrayList<>();
            if (OperationUtil.GetOperationFactorNum(str2Char[i]) != -1) {
                list.add((char) (i + '0'));
                int length = Expression.activeLength(gene.getGene()[k]);
                while (i < length) {
                    if (list.contains((char) (i + '0'))) {
                        pos = list.lastIndexOf((char) (i + '0'));
                        if (OperationUtil.GetOperationFactorNum(str2Char[i]) == 2) {
                            j++;
                            if (OperationUtil.GetOperationFactorNum(str2Char[j]) != -1) {
                                list.add(pos, ')');
                                list.add(pos, (char) (j + '0'));
                                list.add(pos, '(');
                                pos = list.lastIndexOf((char) (i + '0'));
                            } else {
                                list.add(pos, (char) (j + '0'));
                                pos = list.lastIndexOf((char) (i + '0'));
                            }
                            j++;
                            if (OperationUtil.GetOperationFactorNum(str2Char[j]) != -1) {
                                list.add(pos + 1, ')');
                                list.add(pos + 1, (char) (j + '0'));
                                list.add(pos + 1, '(');
                            } else {
                                list.add(pos + 1, (char) (j + '0'));
                            }
                        } else if (OperationUtil.GetOperationFactorNum(str2Char[i]) == 1) {
                            j++;
                            if (OperationUtil.GetOperationFactorNum(str2Char[j]) != -1) {
                                list.add(pos + 1, ')');
                                list.add(pos + 1, (char) (j + '0'));
                                list.add(pos + 1, '(');
                            } else {
                                list.add(pos + 1, (char) (j + '0'));
                            }
                        }
                    }
                    i++;
                }
                for (Character ch : list) {
                    char n = Character.valueOf(ch);
                    int o = (int) (ch - '0');
                    if (0 <= o && o < str2Char.length) {
                        sb.append(str2Char[o]);
                        continue;
                    }
                    sb.append(Character.valueOf(ch));
                }
            } else {
                sb.append(str2Char[0]);
            }
            if (k < gs.numOfGenes - 1) {
                sb.append(Arrays.toString(gs.linkFun));
            }
        }
        return sb.toString();
    }

    static int activeLength(String geneN) {
        char[] str2char = geneN.toCharArray();
        int head = 0;
        int tail = 0;
        for (int i = 0; i < str2char.length; i++) {
            if (i == 0) {
                if (OperationUtil.GetOperationFactorNum(str2char[i]) == 2) {
                    head += 2;
                }
                if (OperationUtil.GetOperationFactorNum(str2char[i]) == 1) {
                    head += 1;
                }
                i++;
            }
            if (OperationUtil.GetOperationFactorNum(str2char[i]) == 2) {
                head += 2;
            }
            if (OperationUtil.GetOperationFactorNum(str2char[i]) == 1) {
                head += 1;
            }
            tail += 1;
            if (head == tail) break;
        }
        return head + 1;
    }
}