package net.minecraft.util;

import com.google.common.primitives.Longs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.network.PacketDataSerializer;

public class MinecraftEncryption {
   private static final String h = "AES";
   private static final int i = 128;
   private static final String j = "RSA";
   private static final int k = 1024;
   private static final String l = "ISO_8859_1";
   private static final String m = "SHA-1";
   public static final String a = "SHA256withRSA";
   public static final int b = 256;
   private static final String n = "-----BEGIN RSA PRIVATE KEY-----";
   private static final String o = "-----END RSA PRIVATE KEY-----";
   public static final String c = "-----BEGIN RSA PUBLIC KEY-----";
   private static final String p = "-----END RSA PUBLIC KEY-----";
   public static final String d = "\n";
   public static final Encoder e = Base64.getMimeEncoder(76, "\n".getBytes(StandardCharsets.UTF_8));
   public static final Codec<PublicKey> f = Codec.STRING.comapFlatMap(var0 -> {
      try {
         return DataResult.success(b(var0));
      } catch (CryptographyException var2) {
         return DataResult.error(var2::getMessage);
      }
   }, MinecraftEncryption::a);
   public static final Codec<PrivateKey> g = Codec.STRING.comapFlatMap(var0 -> {
      try {
         return DataResult.success(a(var0));
      } catch (CryptographyException var2) {
         return DataResult.error(var2::getMessage);
      }
   }, MinecraftEncryption::a);

   public static SecretKey a() throws CryptographyException {
      try {
         KeyGenerator var0 = KeyGenerator.getInstance("AES");
         var0.init(128);
         return var0.generateKey();
      } catch (Exception var1) {
         throw new CryptographyException(var1);
      }
   }

   public static KeyPair b() throws CryptographyException {
      try {
         KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
         var0.initialize(1024);
         return var0.generateKeyPair();
      } catch (Exception var1) {
         throw new CryptographyException(var1);
      }
   }

   public static byte[] a(String var0, PublicKey var1, SecretKey var2) throws CryptographyException {
      try {
         return a(var0.getBytes("ISO_8859_1"), var2.getEncoded(), var1.getEncoded());
      } catch (Exception var4) {
         throw new CryptographyException(var4);
      }
   }

   private static byte[] a(byte[]... var0) throws Exception {
      MessageDigest var1 = MessageDigest.getInstance("SHA-1");

      for(byte[] var5 : var0) {
         var1.update(var5);
      }

      return var1.digest();
   }

   private static <T extends Key> T a(String var0, String var1, String var2, MinecraftEncryption.a<T> var3) throws CryptographyException {
      int var4 = var0.indexOf(var1);
      if (var4 != -1) {
         var4 += var1.length();
         int var5 = var0.indexOf(var2, var4);
         var0 = var0.substring(var4, var5 + 1);
      }

      try {
         return var3.apply(Base64.getMimeDecoder().decode(var0));
      } catch (IllegalArgumentException var6) {
         throw new CryptographyException(var6);
      }
   }

   public static PrivateKey a(String var0) throws CryptographyException {
      return a(var0, "-----BEGIN RSA PRIVATE KEY-----", "-----END RSA PRIVATE KEY-----", MinecraftEncryption::b);
   }

   public static PublicKey b(String var0) throws CryptographyException {
      return a(var0, "-----BEGIN RSA PUBLIC KEY-----", "-----END RSA PUBLIC KEY-----", MinecraftEncryption::a);
   }

   public static String a(PublicKey var0) {
      if (!"RSA".equals(var0.getAlgorithm())) {
         throw new IllegalArgumentException("Public key must be RSA");
      } else {
         return "-----BEGIN RSA PUBLIC KEY-----\n" + e.encodeToString(var0.getEncoded()) + "\n-----END RSA PUBLIC KEY-----\n";
      }
   }

   public static String a(PrivateKey var0) {
      if (!"RSA".equals(var0.getAlgorithm())) {
         throw new IllegalArgumentException("Private key must be RSA");
      } else {
         return "-----BEGIN RSA PRIVATE KEY-----\n" + e.encodeToString(var0.getEncoded()) + "\n-----END RSA PRIVATE KEY-----\n";
      }
   }

   private static PrivateKey b(byte[] var0) throws CryptographyException {
      try {
         EncodedKeySpec var1 = new PKCS8EncodedKeySpec(var0);
         KeyFactory var2 = KeyFactory.getInstance("RSA");
         return var2.generatePrivate(var1);
      } catch (Exception var3) {
         throw new CryptographyException(var3);
      }
   }

   public static PublicKey a(byte[] var0) throws CryptographyException {
      try {
         EncodedKeySpec var1 = new X509EncodedKeySpec(var0);
         KeyFactory var2 = KeyFactory.getInstance("RSA");
         return var2.generatePublic(var1);
      } catch (Exception var3) {
         throw new CryptographyException(var3);
      }
   }

   public static SecretKey a(PrivateKey var0, byte[] var1) throws CryptographyException {
      byte[] var2 = b(var0, var1);

      try {
         return new SecretKeySpec(var2, "AES");
      } catch (Exception var4) {
         throw new CryptographyException(var4);
      }
   }

   public static byte[] a(Key var0, byte[] var1) throws CryptographyException {
      return a(1, var0, var1);
   }

   public static byte[] b(Key var0, byte[] var1) throws CryptographyException {
      return a(2, var0, var1);
   }

   private static byte[] a(int var0, Key var1, byte[] var2) throws CryptographyException {
      try {
         return a(var0, var1.getAlgorithm(), var1).doFinal(var2);
      } catch (Exception var4) {
         throw new CryptographyException(var4);
      }
   }

   private static Cipher a(int var0, String var1, Key var2) throws Exception {
      Cipher var3 = Cipher.getInstance(var1);
      var3.init(var0, var2);
      return var3;
   }

   public static Cipher a(int var0, Key var1) throws CryptographyException {
      try {
         Cipher var2 = Cipher.getInstance("AES/CFB8/NoPadding");
         var2.init(var0, var1, new IvParameterSpec(var1.getEncoded()));
         return var2;
      } catch (Exception var3) {
         throw new CryptographyException(var3);
      }
   }

   interface a<T extends Key> {
      T apply(byte[] var1) throws CryptographyException;
   }

   public static record b(long salt, byte[] signature) {
      private final long b;
      private final byte[] c;
      public static final MinecraftEncryption.b a = new MinecraftEncryption.b(0L, ByteArrays.EMPTY_ARRAY);

      public b(PacketDataSerializer var0) {
         this(var0.readLong(), var0.b());
      }

      public b(long var0, byte[] var2) {
         this.b = var0;
         this.c = var2;
      }

      public boolean a() {
         return this.c.length > 0;
      }

      public static void a(PacketDataSerializer var0, MinecraftEncryption.b var1) {
         var0.writeLong(var1.b);
         var0.a(var1.c);
      }

      public byte[] b() {
         return Longs.toByteArray(this.b);
      }

      public long c() {
         return this.b;
      }

      public byte[] d() {
         return this.c;
      }
   }

   public static class c {
      private static final SecureRandom a = new SecureRandom();

      public static long a() {
         return a.nextLong();
      }
   }
}
