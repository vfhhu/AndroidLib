package xyz.vfhhu.lib.android.utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUtils {
    public static final String RSA = "RSA";// 非对称加密密钥算法
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
    public static final String ECB_PKCS1_OAEP_PADDING = "RSA/ECB/OAEPPadding";//加密填充方式
    public static final String RSA_PADDING = ECB_PKCS1_OAEP_PADDING;//加密填充方式

    final public static int DEFAULT_KEY_SIZE = 2048;//秘钥默认长度
    private static int DEFAULT_SPLIT_SIZE = (DEFAULT_KEY_SIZE / 8);    // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
    private static int DEFAULT_BUFFERSIZE = DEFAULT_SPLIT_SIZE - 42;// 当前秘钥支持加密的最大字节数

    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(DEFAULT_KEY_SIZE);
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048
     *                  一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            DEFAULT_SPLIT_SIZE=keyLength/8;
            DEFAULT_BUFFERSIZE = DEFAULT_SPLIT_SIZE - 42;
            if(DEFAULT_SPLIT_SIZE<64)return null;
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String bytesToEncodedString(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.URL_SAFE| Base64.NO_WRAP);
    }

    public static byte[] encodedStringToBytes(String encodedString) {
        return Base64.decode(encodedString, Base64.URL_SAFE);
    }
    public static String Base64Encoded(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.URL_SAFE| Base64.NO_WRAP);
    }

    public static byte[] Base64Decoded(String encodedString) {
        return Base64.decode(encodedString, Base64.URL_SAFE);
    }
    public static PublicKey getPublicKey(byte bytes[]){
        try {
            return KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(bytes));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static PrivateKey getPrivateKey(byte bytes[]){
        try {
            return KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(bytes));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 用公钥对字符串进行加密
     *
     * @param data 原文
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PublicKey keyPublic = kf.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(RSA_PADDING);
        cp.init(Cipher.ENCRYPT_MODE, keyPublic);
        return cp.doFinal(data);
    }

    /**
     * 使用私钥进行解密
     */
    public static byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey) throws Exception {
        // 得到私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);

        // 解密数据
        Cipher cp = Cipher.getInstance(RSA_PADDING);
        cp.init(Cipher.DECRYPT_MODE, keyPrivate);
        byte[] arr = cp.doFinal(encrypted);
        return arr;
    }

    /**
     * 用公钥对字符串进行分段加密
     */
    public static byte[] encryptByPublicKeyForSpilt(byte[] data, byte[] publicKey) throws Exception {
        int dataLen = data.length;
        if (dataLen <= DEFAULT_BUFFERSIZE) {
            return encryptByPublicKey(data, publicKey);
        }
//        List<Byte> allBytes = new ArrayList<Byte>();
        ByteArrayOutputStream allBytes = new ByteArrayOutputStream();


        int offSet = 0;
        byte[] cache;
        int i = 0;
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PublicKey keyPublic = kf.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(RSA_PADDING);
        cp.init(Cipher.ENCRYPT_MODE, keyPublic);

        while (dataLen - offSet > 0) {
            if (dataLen - offSet > DEFAULT_BUFFERSIZE) {
                cache = cp.doFinal(data, offSet, DEFAULT_BUFFERSIZE);
            } else {
                cache = cp.doFinal(data, offSet, dataLen - offSet);
            }
            allBytes.write(cache);
            i++;
            offSet = i * DEFAULT_BUFFERSIZE;
        }
        return  allBytes.toByteArray();
    }




    /**
     * 使用私钥分段解密
     */
    public static byte[] decryptByPrivateKeyForSpilt(byte[] encrypted, byte[] privateKey) throws Exception {
        int splitLen = DEFAULT_SPLIT_SIZE;
        if (splitLen <= 0) {
            return decryptByPrivateKey(encrypted, privateKey);
        }
        int dataLen = encrypted.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        // 得到私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);

        // 解密数据
        Cipher cp = Cipher.getInstance(RSA_PADDING);
        cp.init(Cipher.DECRYPT_MODE, keyPrivate);

        while (dataLen - offSet > 0) {
            if (dataLen - offSet > splitLen) {
                cache = cp.doFinal(encrypted, offSet, splitLen);
            } else {
                cache = cp.doFinal(encrypted, offSet, dataLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * splitLen;
        }
        return out.toByteArray();





//        List<Byte> allBytes = new ArrayList<Byte>();
//        int latestStartIndex = 0;
//        for (int i = 0; i < dataLen; i++) {
//            byte bt = encrypted[i];
//            boolean isMatchSplit = false;
//            if (i == dataLen - 1) {
//                // 到data的最后了
//                byte[] part = new byte[dataLen - latestStartIndex];
//                System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
//                byte[] decryptPart = decryptByPrivateKey(part, privateKey);
//                for (byte b : decryptPart) {
//                    allBytes.add(b);
//                }
//                latestStartIndex = i + splitLen;
//                i = latestStartIndex - 1;
//            } else if (bt == DEFAULT_SPLIT[0]) {
//                // 这个是以split[0]开头
//                if (splitLen > 1) {
//                    if (i + splitLen < dataLen) {
//                        // 没有超出data的范围
//                        for (int j = 1; j < splitLen; j++) {
//                            if (DEFAULT_SPLIT[j] != encrypted[i + j]) {
//                                break;
//                            }
//                            if (j == splitLen - 1) {
//                                // 验证到split的最后一位，都没有break，则表明已经确认是split段
//                                isMatchSplit = true;
//                            }
//                        }
//                    }
//                } else {
//                    // split只有一位，则已经匹配了
//                    isMatchSplit = true;
//                }
//            }
//            if (isMatchSplit) {
//                byte[] part = new byte[i - latestStartIndex];
//                System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
//                byte[] decryptPart = decryptByPrivateKey(part, privateKey);
//                for (byte b : decryptPart) {
//                    allBytes.add(b);
//                }
//                latestStartIndex = i + splitLen;
//                i = latestStartIndex - 1;
//            }
//        }
//        byte[] bytes = new byte[allBytes.size()];
//        {
//            int i = 0;
//            for (Byte b : allBytes) {
//                bytes[i++] = b.byteValue();
//            }
//        }
//        return bytes;
    }
}