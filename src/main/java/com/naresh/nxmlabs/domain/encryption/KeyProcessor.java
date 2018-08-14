package com.naresh.nxmlabs.domain.encryption;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyProcessor {
  private KeyPair keyPair;
  private PublicKey publicKey;
  private PrivateKey privateKey;
  private byte[] encrypted;
  private byte[] secret;

  private static final String ALGORITHM = "RSA";
  private static final String MESSAGE = "Sample input for private key encryption and public key decryption";
  private static final String ANOTHER_MESSAGE = "Sample input for public key encryption and private key decryption";

  public PublicKey getPublicKey() {
    return publicKey;
  }

  @Autowired
  public KeyProcessor() throws NoSuchAlgorithmException {
    //Task1
    keyPair = buildKeyPair();
    publicKey = keyPair.getPublic();
    privateKey = keyPair.getPrivate();
  }

  public byte[] getEncrypted() throws Exception {
    //Task2
    encrypted = encrypt(privateKey, MESSAGE);
    return encrypted;
  }

  public byte[] getDecryptedMessage() throws Exception {
    secret = decrypt(publicKey, encrypted);
    return secret;
  }

  public byte[] encryptWithPublicKey() throws Exception {
    //Task 3
    // encrypt the message with public key
    encrypted = encryptWithPublic(publicKey, ANOTHER_MESSAGE);
    return encrypted;
  }

  public byte[] decryptWithPrivate() throws Exception {
    // decrypt the message with public key
    secret = decryptWithPrivate(privateKey, encrypted);
    return secret;
  }

  private static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
    final int keySize = 2048;
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(keySize);
    return keyPairGenerator.genKeyPair();
  }

  private static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, privateKey);

    return cipher.doFinal(message.getBytes());
  }

  private static byte[] decrypt(PublicKey publicKey, byte[] encrypted) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, publicKey);

    return cipher.doFinal(encrypted);
  }

  private static byte[] encryptWithPublic(PublicKey publicKey, String message) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

    return cipher.doFinal(message.getBytes());
  }

  private static byte[] decryptWithPrivate(PrivateKey privateKey, byte[] encrypted) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, privateKey);

    return cipher.doFinal(encrypted);
  }

}