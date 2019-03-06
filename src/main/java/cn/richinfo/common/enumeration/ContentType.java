package cn.richinfo.common.enumeration;

public enum ContentType {
	
	texthtml(0),textjson(1),textxml(2);
	
	private int value;
	ContentType(int value){
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
