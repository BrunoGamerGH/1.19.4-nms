package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;

public class CombatEntry {
   private final DamageSource a;
   private final int b;
   private final float c;
   private final float d;
   @Nullable
   private final String e;
   private final float f;

   public CombatEntry(DamageSource var0, int var1, float var2, float var3, @Nullable String var4, float var5) {
      this.a = var0;
      this.b = var1;
      this.c = var3;
      this.d = var2;
      this.e = var4;
      this.f = var5;
   }

   public DamageSource a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public float c() {
      return this.c;
   }

   public float d() {
      return this.d;
   }

   public float e() {
      return this.d - this.c;
   }

   public boolean f() {
      return this.a.d() instanceof EntityLiving;
   }

   @Nullable
   public String g() {
      return this.e;
   }

   @Nullable
   public IChatBaseComponent h() {
      return this.a().d() == null ? null : this.a().d().G_();
   }

   @Nullable
   public Entity i() {
      return this.a().d();
   }

   public float j() {
      return this.f;
   }
}
