package net.minecraft.network.protocol.login;

import java.security.PublicKey;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.CryptographyException;
import net.minecraft.util.MinecraftEncryption;

public class PacketLoginOutEncryptionBegin implements Packet<PacketLoginOutListener> {
   private final String a;
   private final byte[] b;
   private final byte[] c;

   public PacketLoginOutEncryptionBegin(String var0, byte[] var1, byte[] var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketLoginOutEncryptionBegin(PacketDataSerializer var0) {
      this.a = var0.e(20);
      this.b = var0.b();
      this.c = var0.b();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(this.b);
      var0.a(this.c);
   }

   public void a(PacketLoginOutListener var0) {
      var0.a(this);
   }

   public String a() {
      return this.a;
   }

   public PublicKey c() throws CryptographyException {
      return MinecraftEncryption.a(this.b);
   }

   public byte[] d() {
      return this.c;
   }
}
