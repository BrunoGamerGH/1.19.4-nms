package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.trading.MerchantRecipeList;

public class PacketPlayOutOpenWindowMerchant implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final MerchantRecipeList b;
   private final int c;
   private final int d;
   private final boolean e;
   private final boolean f;

   public PacketPlayOutOpenWindowMerchant(int var0, MerchantRecipeList var1, int var2, int var3, boolean var4, boolean var5) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   public PacketPlayOutOpenWindowMerchant(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = MerchantRecipeList.b(var0);
      this.c = var0.m();
      this.d = var0.m();
      this.e = var0.readBoolean();
      this.f = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      this.b.a(var0);
      var0.d(this.c);
      var0.d(this.d);
      var0.writeBoolean(this.e);
      var0.writeBoolean(this.f);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public MerchantRecipeList c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }

   public boolean f() {
      return this.e;
   }

   public boolean g() {
      return this.f;
   }
}
