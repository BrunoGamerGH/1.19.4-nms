package net.minecraft.world.entity.player;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerAbilities {
   public boolean a;
   public boolean b;
   public boolean c;
   public boolean d;
   public boolean e = true;
   public float f = 0.05F;
   public float g = 0.1F;

   public void a(NBTTagCompound var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("invulnerable", this.a);
      var1.a("flying", this.b);
      var1.a("mayfly", this.c);
      var1.a("instabuild", this.d);
      var1.a("mayBuild", this.e);
      var1.a("flySpeed", this.f);
      var1.a("walkSpeed", this.g);
      var0.a("abilities", var1);
   }

   public void b(NBTTagCompound var0) {
      if (var0.b("abilities", 10)) {
         NBTTagCompound var1 = var0.p("abilities");
         this.a = var1.q("invulnerable");
         this.b = var1.q("flying");
         this.c = var1.q("mayfly");
         this.d = var1.q("instabuild");
         if (var1.b("flySpeed", 99)) {
            this.f = var1.j("flySpeed");
            this.g = var1.j("walkSpeed");
         }

         if (var1.b("mayBuild", 1)) {
            this.e = var1.q("mayBuild");
         }
      }
   }

   public float a() {
      return this.f;
   }

   public void a(float var0) {
      this.f = var0;
   }

   public float b() {
      return this.g;
   }

   public void b(float var0) {
      this.g = var0;
   }
}
