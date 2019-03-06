package cn.richinfo.common.utils;

import java.util.Random;
import java.util.UUID;

public class RandonNum {

    /**
     * @param first 起始
     * @param last  结束
     * @return 指定范围内的正整数
     */
    public static int getRandanInteger(int first, int last) {
        Random rand = new Random();
        return first + rand.nextInt(last);
    }

    /**
     * 获取随机码数字
     *
     * @param num 长度
     * @return
     */
    public static String getrandom(Integer num) {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < num; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }

    public static String randonString() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 产生[0,rank)中随机正整数
     * @param rank
     * @return
     */
    public static int RandonNumRank(int rank){
        return new Random().nextInt(rank);
    }
    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            int a = getRandanInteger(1, 100);
            if (a <= 0 || a > 100) {

                System.out.println(a);
            }
        }
        System.out.println(RandonNumRank(3));
        System.out.println(getrandom(7));
    }
}
