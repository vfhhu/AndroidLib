package xyz.vfhhu.lib.android.utils;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by leo3x on 2018/7/16.
 */

public class MagicCrypt {
    //-----類別常數-----
    /**
     * 預設的Initialization Vector，為16 Bits的0
     */
    private static final IvParameterSpec DEFAULT_IV = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    /**
     * 加密演算法使用AES
     */
    private static final String ALGORITHM = "AES";
    /**
     * AES使用CBC模式與PKCS5Padding
     */
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    //-----物件變數-----
    /**
     * 取得AES加解密的密鑰
     */
    private Key key;
    /**
     * AES CBC模式使用的Initialization Vector
     */
    private IvParameterSpec iv;
    /**
     * Cipher 物件
     */
    private Cipher cipher;

    //-----建構子-----
    /**
     * 建構子，使用128 Bits的AES密鑰(計算任意長度密鑰的MD5)和預設IV
     *
     * @param key 傳入任意長度的AES密鑰
     */
    public MagicCrypt(final String key) {
        this(key, 128);
    }

    /**
     * 建構子，使用128 Bits或是256 Bits的AES密鑰(計算任意長度密鑰的MD5或是SHA256)和預設IV
     *
     * @param key 傳入任意長度的AES密鑰
     * @param bit 傳入AES密鑰長度，數值可以是128、256 (Bits)
     */
    public MagicCrypt(final String key, final int bit) {
        this(key, bit, null);
    }

    /**
     * 建構子，使用128 Bits或是256 Bits的AES密鑰(計算任意長度密鑰的MD5或是SHA256)，用MD5計算IV值
     *
     * @param key 傳入任意長度的AES密鑰
     * @param bit 傳入AES密鑰長度，數值可以是128、256 (Bits)
     * @param iv 傳入任意長度的IV字串
     */
    public MagicCrypt(final String key, final int bit, final String iv) {
        if (bit == 256) {
            this.key = new SecretKeySpec(getHash("SHA-256", key), ALGORITHM);
        } else {
            this.key = new SecretKeySpec(getHash("MD5", key), ALGORITHM);
        }
        if (iv != null) {
            this.iv = new IvParameterSpec(getHash("MD5", iv));
        } else {
            this.iv = DEFAULT_IV;
        }

        init();
    }

    //-----物件方法-----
    /**
     * 取得字串的雜湊值
     *
     * @param algorithm 傳入雜驟演算法
     * @param text 傳入要雜湊的字串
     * @return 傳回雜湊後資料內容
     */
    private static byte[] getHash(final String algorithm, final String text) {
        try {
            return getHash(algorithm, text.getBytes("UTF-8"));
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 取得資料的雜湊值
     *
     * @param algorithm 傳入雜驟演算法
     * @param data 傳入要雜湊的資料
     * @return 傳回雜湊後資料內容
     */
    private static byte[] getHash(final String algorithm, final byte[] data) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(data);
            return digest.digest();
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 初始化
     */
    private void init() {
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 加密文字
     *
     * @param str 傳入要加密的文字
     * @return 傳回加密後的文字
     */
    public String encrypt(final String str) {
        try {
            return encrypt(str.getBytes("UTF-8"));
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 加密資料
     *
     * @param data 傳入要加密的資料
     * @return 傳回加密後的資料
     */
    public String encrypt(final byte[] data) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            final byte[] encryptData = cipher.doFinal(data);

            return new String(android.util.Base64.encode(encryptData, android.util.Base64.DEFAULT), Charset.forName("UTF-8"));
//            return new String(Base64.getEncoder().encode(encryptData), "UTF-8");
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 解密文字
     *
     * @param str 傳入要解密的文字
     * @return 傳回解密後的文字
     */
    public String decrypt(final String str) {
        try {
            return decrypt(android.util.Base64.decode(str,android.util.Base64.DEFAULT));

//            return decrypt(Base64.getDecoder().decode(str));
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 解密文字
     *
     * @param data 傳入要解密的資料
     * @return 傳回解密後的文字
     */
    public String decrypt(final byte[] data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            final byte[] decryptData = cipher.doFinal(data);
            return new String(decryptData, "UTF-8");
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
