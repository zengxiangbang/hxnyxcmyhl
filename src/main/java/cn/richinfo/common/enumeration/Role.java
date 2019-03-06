package cn.richinfo.common.enumeration;

public enum Role {

	guest(0), mobileuser(1), weixinuser(2);

	private int value;

	Role(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}
