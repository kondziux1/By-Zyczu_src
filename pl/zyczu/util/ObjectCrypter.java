package pl.zyczu.util;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ObjectCrypter {
   private static final byte[] ivBytes = new byte[16];

   public ObjectCrypter() {
      super();
   }

   public static byte[] encrypt(byte[] klucz, byte[] wiadomosc) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
      return encrypt(ivBytes, klucz, wiadomosc);
   }

   public static byte[] decrypt(byte[] klucz, byte[] wiadomosc) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {
      return decrypt(ivBytes, klucz, wiadomosc);
   }

   private static byte[] encrypt(byte[] ivBytes, byte[] keyBytes, byte[] mes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
      AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
      SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
      Cipher cipher = null;
      cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(1, newKey, ivSpec);
      return cipher.doFinal(mes);
   }

   private static byte[] decrypt(byte[] ivBytes, byte[] keyBytes, byte[] bytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {
      AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
      SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(2, newKey, ivSpec);
      return cipher.doFinal(bytes);
   }
}
