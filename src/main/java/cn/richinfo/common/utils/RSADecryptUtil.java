package cn.richinfo.common.utils;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;


/**
 * ClassName: RSAEncrypt <br/>
 * Description: RSA加密解密算法 <br/>
 * Date: 2016年12月2日 上午11:28:09 <br/>
 * <br/>
 *
 * @author 
 *         <p>
 *         修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息<br/>
 */

public class RSADecryptUtil {
    private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcB0LVnUKLHSIqy+0cdwqn6xAK3pKZ96SsaCareYEePzg2sIbPYpSG4OZJG8A050oEgByj7lkzJTFtxKzs+I7J3jZYYFhwm0nybLB9ABAJGXCtXCzQ/Izpac1b/m/LHRFFupBsLY2VUZBZ49/j7ro16ur4zAye8QQL3C6On0oWnwIDAQAB";

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    private static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 公钥解密过程
     *
     * @param data 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String data) {
        byte[] cipherData = Base64Utils.decode(data);
        Cipher cipher = null;
        try {
            // 这里不能直接使用RSA。必须使用RSA/ECB/PKCS1Padding。否则服务器无法解密
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, loadPublicKeyByStr(PUCLIC_KEY));
            int inputLen = cipherData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(cipherData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(cipherData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData);
        } catch (Exception e) {
            return null;
        }
    }

    //demo，可直接运行得到结果phone=18703857236&provinceCode=371
    public static void main(String[] args) {
        String before = "PJXstFBzRe/yg0Tb6YjQt4uOpAy2eCxI77tRvcFKTq98kVInNfdHYjeihWopL2s6iqgZd4uqiafCelxjX3x8UGVhPYaSZjAbjgGxRsK5vzqwrV0/lFhraTAEf2at/CORiwq2+ZZEeYTekDFRczmV6JOoFFDGcOin0sZqLLhuuPY=";

        String before2 = "TVySnwC5VAGvpsO3rNmhk95xCzFKoaiCHXya+l0WvIQiOXA23CmhJVYZivItWOV8hOKIyQlz+f0capuK0qAjP3rPjfCENODaUtW5134FLPGJYopKKz35KphzeG2jG0Z0aBa3e/v+F4E1g54AD5ZcKjqZMwaN3UykpM181RIpezg=";

        String decrypt = decrypt(before);
        String decrypt2 = decrypt(before2);
        System.out.println(decrypt);
        System.out.println(decrypt2);


        
    }
}  