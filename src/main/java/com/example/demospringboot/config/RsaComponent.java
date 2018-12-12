package com.example.demospringboot.config;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 作者 E-mail:
 * @version 1.0
 * @date 创建时间：2016年7月28日 下午1:45:27
 * @parameter
 * @return
 * @since
 */
@Component
public class RsaComponent {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static String ALGORITHM = "RSA";

    private static String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdVHYLB4h5a9PhwBBSvzVsz5+lDVyg8E/zMIcSTsrWKiuXJu0EVwIEWSwZQril16D3B3yv5JfR//oP6KI9zXkbkebUuimqx9fz5QYCtL4NVfjQT8syyScB3vXhTz0s5NB+ezvTmSxCxRAm5tV+/dagPCNAm6PDkKDApdw0ltKDNQIDAQAB";

    private static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN1UdgsHiHlr0+HAEFK/NWzPn6UNXKDwT/MwhxJOytYqK5cm7QRXAgRZLBlCuKXXoPcHfK/kl9H/+g/ooj3NeRuR5tS6KarH1/PlBgK0vg1V+NBPyzLJJwHe9eFPPSzk0H57O9OZLELFECbm1X791qA8I0Cbo8OQoMCl3DSW0oM1AgMBAAECgYEArN00vPSLQNpYyG7r0NUKcwvOUmkXOZO3vebe+AuWHKMfHJUIdWVHbAIBkVEtSkoZrBaq6e4OLbRRqG83mDGNLelg4Ah8Xtlw/dsk6EPXiKuffctqqEGbzD6bhPzksZ75HTNg3lJUDaTrH81qXJa++sOtIBrTanIDX7wjEvy8uRkCQQDzahefBVKltENzC+7VZ2BQ/R9VhC+byDJcwXirK+7PW5EXiAoZ0J11whreZFOQwlMCFipIAmwdqcEHyDhLaP3fAkEA6MYNOyswgOLYU1CN4czdmNKiDIzVSImaXz8QTf7TLR6aUwRzXjJx0iMFidtMcxfVx+P1AgAUxhGG6rwn6E55awJBAN6zWhkrjYn9exhu+nxUsFJow5pB7izg5PLoL7ar4znj1MNqu5MSwn1SsbJ2p93xbHCkRtrLHI+nF+w+ywJzdJECQHq+JK/0oEj2VVfpfEd6/cbeGvE/OiBb3vkmifR20OhT4NCx4hvjwb0rFnqRFwkBoosbG1EIteuTxm709J488y0CQFD6wSo3aC+pqiOwrhd5+eos/+mEQLqkdPWUbB/y20N3eHF6ne1EMlawJps71q5kd2KEcwaqH9B8QiTLzKpTrk8=";


    /**
     * 公钥加密算法
     * cryptograph:密文
     */
    public String encrypt(String source) throws Exception {
        String pk = "";
        pk = public_key;
        /** 将文件中的公钥对象读出 */
        PublicKey publicKey = getPublicKey(pk);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        /** 执行加密操作 */
        int inputLen = source.getBytes().length;
        String result = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(source.getBytes(), offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(source.getBytes(), offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            result = encoder.encode(encryptedData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return result;
    }

    /**
     * 私钥解密算法
     * cryptograph:密文
     */
    public String decrypt(String cryptograph) throws Exception {
        String pk = "";
        pk = private_key;
        PrivateKey privateKey = getPrivateKey(pk);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        BASE64Decoder decoder = new BASE64Decoder();
        int inputLen = decoder.decodeBuffer(cryptograph).length;
        ByteArrayOutputStream out = null;
        byte[] decryptedData = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(decoder.decodeBuffer(cryptograph), offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(decoder.decodeBuffer(cryptograph), offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            decryptedData = out.toByteArray();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return new String(decryptedData, "utf-8");
    }

    /**
     * 私钥加密算法
     * cryptograph:密文
     */
    public String encryptPrivate(String source) throws Exception {
        /** 将文件中的公钥对象读出 */
        PrivateKey privateKey = getPrivateKey(private_key);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        /** 执行加密操作 */
        int inputLen = source.getBytes().length;
        ByteArrayOutputStream out = null;
        byte[] encryptedData = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(source.getBytes(), offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(source.getBytes(), offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(encryptedData);
    }

    /**
     * 公钥解密算法
     * cryptograph:密文
     */
    public String decryptPublic(String cryptograph) throws Exception {
        PublicKey publicKey = getPublicKey(public_key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        BASE64Decoder decoder = new BASE64Decoder();
        int inputLen = decoder.decodeBuffer(cryptograph).length;
        ByteArrayOutputStream out = null;
        byte[] decryptedData = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(decoder.decodeBuffer(cryptograph), offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(decoder.decodeBuffer(cryptograph), offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            decryptedData = out.toByteArray();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return new String(decryptedData);
    }

    /**
     * 从字符串来转化成PublicKey
     *
     * @param key
     * @return
     * @throws Exception
     * @author caoyc
     * @date 创建时间：2016年7月28日 下午2:21:07
     * @parameter
     */
    public PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 从字符串来转化成PrivateKey
     *
     * @param key
     * @return
     * @throws Exception
     * @author caoyc
     * @date 创建时间：2016年7月28日 下午2:21:34
     * @parameter
     */
    public PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

}
