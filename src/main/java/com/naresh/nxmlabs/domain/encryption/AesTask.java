package com.naresh.nxmlabs.domain.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AesTask {
  public static void main(String[] args) {
    String key = "This is a secret";
    AesTask aesTask = new AesTask();
    ClassLoader classLoader = aesTask.getClass().getClassLoader();
    File inputFile = new File(classLoader.getResource("SampleTextFile.txt").getFile());
    File encryptedFile = new File("output_file.encrypted");
    File decryptedFile = new File("decrypted-file.txt");

    try {
      AesTask.fileProcessor(Cipher.ENCRYPT_MODE, key, inputFile, encryptedFile);
      AesTask.fileProcessor(Cipher.DECRYPT_MODE, key, encryptedFile, decryptedFile);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  static void fileProcessor(int cipherMode, String key, File inputFile, File outputFile) {
    try {
      Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(cipherMode, secretKey);

      FileInputStream inputStream = new FileInputStream(inputFile);
      byte[] inputBytes = new byte[(int) inputFile.length()];
      inputStream.read(inputBytes);

      byte[] outputBytes = cipher.doFinal(inputBytes);

      FileOutputStream outputStream = new FileOutputStream(outputFile);
      outputStream.write(outputBytes);

      inputStream.close();
      outputStream.close();

    } catch (NoSuchPaddingException | NoSuchAlgorithmException
        | InvalidKeyException | BadPaddingException
        | IllegalBlockSizeException | IOException e) {
      e.printStackTrace();
    }
  }
}