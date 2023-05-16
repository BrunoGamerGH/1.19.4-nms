package net.minecraft.server.rcon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RemoteStatusReply {
   private final ByteArrayOutputStream a;
   private final DataOutputStream b;

   public RemoteStatusReply(int var0) {
      this.a = new ByteArrayOutputStream(var0);
      this.b = new DataOutputStream(this.a);
   }

   public void a(byte[] var0) throws IOException {
      this.b.write(var0, 0, var0.length);
   }

   public void a(String var0) throws IOException {
      this.b.writeBytes(var0);
      this.b.write(0);
   }

   public void a(int var0) throws IOException {
      this.b.write(var0);
   }

   public void a(short var0) throws IOException {
      this.b.writeShort(Short.reverseBytes(var0));
   }

   public void b(int var0) throws IOException {
      this.b.writeInt(Integer.reverseBytes(var0));
   }

   public void a(float var0) throws IOException {
      this.b.writeInt(Integer.reverseBytes(Float.floatToIntBits(var0)));
   }

   public byte[] a() {
      return this.a.toByteArray();
   }

   public void b() {
      this.a.reset();
   }
}
