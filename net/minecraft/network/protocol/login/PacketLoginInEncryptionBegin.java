package net.minecraft.network.protocol.login;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import javax.crypto.SecretKey;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.CryptographyException;
import net.minecraft.util.MinecraftEncryption;

public class PacketLoginInEncryptionBegin implements Packet<PacketLoginInListener> {
   private final byte[] a;
   private final byte[] b;

   public PacketLoginInEncryptionBegin(SecretKey var0, PublicKey var1, byte[] var2) throws CryptographyException {
      this.a = MinecraftEncryption.a(var1, var0.getEncoded());
      this.b = MinecraftEncryption.a(var1, var2);
   }

   public PacketLoginInEncryptionBegin(PacketDataSerializer var0) {
      this.a = var0.b();
      this.b = var0.b();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(this.b);
   }

   public void a(PacketLoginInListener var0) {
      var0.a(this);
   }

   public SecretKey a(PrivateKey var0) throws CryptographyException {
      return MinecraftEncryption.a(var0, this.a);
   }

   public boolean a(byte[] var0, PrivateKey var1) {
      try {
         return Arrays.equals(var0, MinecraftEncryption.b(var1, this.b));
      } catch (CryptographyException var4) {
         return false;
      }
   }
}
