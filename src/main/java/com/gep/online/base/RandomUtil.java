package com.gep.online.base;

class RandomUtil {
    public static double getDoubleRandom(Double large, Double little) {
        return Math.random() * (large - little) + little;
    }

    static int getRandom(int large, int little) {
        return (int) (Math.random() * (large - little) + little);
    }

    static int getRandom(int large) {
        return getRandom(large, 0);
    }
}
