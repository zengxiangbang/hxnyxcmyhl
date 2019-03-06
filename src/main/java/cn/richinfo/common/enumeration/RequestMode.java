package cn.richinfo.common.enumeration;

public enum RequestMode {

	ajax(0), http(1), https(2);

	private int value;

	RequestMode(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}