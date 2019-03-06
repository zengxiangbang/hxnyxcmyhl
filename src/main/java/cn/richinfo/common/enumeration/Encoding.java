package cn.richinfo.common.enumeration;

public enum Encoding {
    UTF8(1),
    GBK(2),
    GB2312(3),
    BIG5(4);
    private int value;

    Encoding(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
