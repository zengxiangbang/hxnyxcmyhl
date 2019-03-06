package cn.richinfo.common.enumeration;

public enum HttpMethod {
	 
	 
    POST( 1),
     
    GET( 2);
     
    private int value;
    HttpMethod(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
     
}