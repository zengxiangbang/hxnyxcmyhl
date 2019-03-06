package cn.richinfo.common.utils;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 无视Https证书是否正确的Java Http Client
 */
public class HttpsUtil {


/**
 * 忽视证书HostName
 */
private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
    public boolean verify(String s, SSLSession sslsession) {
        System.out.println("WARNING: Hostname is not matched for cert.");
        return true;
    }
};

 /**
 * Ignore Certification
 */
private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {

    private X509Certificate[] certificates;

    public void checkClientTrusted(X509Certificate certificates[],
            String authType) throws CertificateException {
        if (this.certificates == null) {
            this.certificates = certificates;
            System.out.println("init at checkClientTrusted");
        }
    }


    public void checkServerTrusted(X509Certificate[] ax509certificate,
            String s) throws CertificateException {
        if (this.certificates == null) {
            this.certificates = ax509certificate;
            System.out.println("init at checkServerTrusted");
        }
//        for (int c = 0; c < certificates.length; c++) {
//            X509Certificate cert = certificates[c];
//            System.out.println(" Server certificate " + (c + 1) + ":");
//            System.out.println("  Subject DN: " + cert.getSubjectDN());
//            System.out.println("  Signature Algorithm: "
//                    + cert.getSigAlgName());
//            System.out.println("  Valid from: " + cert.getNotBefore());
//            System.out.println("  Valid until: " + cert.getNotAfter());
//            System.out.println("  Issuer: " + cert.getIssuerDN());
//        }

    }


    public X509Certificate[] getAcceptedIssuers() {
        // TODO Auto-generated method stub
        return null;
    }
};


public static String getMethod(String urlString) {

    ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
    try {
        URL url = new URL(urlString);
        /*
         * use ignore host name verifier
         */
        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();


        // Prepare SSL Context
        TrustManager[] tm = { ignoreCertificationTrustManger };
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());


        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        connection.setSSLSocketFactory(ssf);
        
        InputStream reader = connection.getInputStream();
        byte[] bytes = new byte[512];
        int length = reader.read(bytes);


        do {
            buffer.write(bytes, 0, length);
            length = reader.read(bytes);
        } while (length > 0);


        // result.setResponseData(bytes);
        System.out.println(buffer.toString());
        reader.close();
        
        connection.disconnect();
    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
    }
    String repString= new String (buffer.toByteArray());
    return repString;
}


public static void main(String[] args) {
    String urlString = "https://www.cmpassport.com/openapi/oauth/wap/login.html?forceAuthn=true&isPassive=false&callBackURL=https%3A%2F%2Fopen.mmarket.com%3A443%2Fomee-aus%2Fservices%2Foauth%2Fshow&display=wap&asDomain=hf.mm.10086.cn&authType=UPDS&clientId=300009814289&relayState=99cde5c2536369eff74d012180590585098a633443f82c6d2103fbfc18f668429677eec0ba67a70879b0805e011621805f34010a7eb808996339923a3a40ac8b7a096da80164671d0f5de21813a9ec777fcd58a5e446d05bfdd9cce1f5f6564e95f80e41c8efecc1e03f7248408018c7c6152268175e94df";
    String output = new String(HttpsUtil.getMethod(urlString));
    System.out.println(output);
}
}
