package com.gep.online.base;

import java.util.Arrays;

class Char2strUtil {
    static char[] str2char(String str) {
        return str.replace(" ", "").replace(
                ",", "").replace("[", "").replace("]", "").toCharArray();
    }

    static String char2str(char[] chars) {
        return Arrays.toString(chars).replace(" ", "").replace(
                ",", "").replace("[", "").replace("]", "");
    }
}
