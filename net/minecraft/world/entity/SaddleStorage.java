package net.minecraft.world.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class SaddleStorage {
   private static final int a = 140;
   private static final int b = 700;
   private final DataWatcher c;
   private final DataWatcherObject<Integer> d;
   private final DataWatcherObject<Boolean> e;
   public boolean f;
   public int g;

   public SaddleStorage(DataWatcher datawatcher, DataWatcherObject<Integer> datawatcherobject, DataWatcherObject<Boolean> datawatcherobject1) {
      this.c = datawatcher;
      this.d = datawatcherobject;
      this.e = datawatcherobject1;
   }

   public void a() {
      this.f = true;
      this.g = 0;
   }

   public boolean a(RandomSource randomsource) {
      if (this.f) {
         return false;
      } else {
         this.f = true;
         this.g = 0;
         this.c.b(this.d, randomsource.a(841) + 140);
         return true;
      }
   }

   public void b() {
      if (this.f && this.g++ > this.e()) {
         this.f = false;
      }
   }

   public float c() {
      return this.f ? 1.0F + 1.15F * MathHelper.a((float)this.g / (float)this.e() * (float) Math.PI) : 1.0F;
   }

   public int e() {
      return this.c.a(this.d);
   }

   public void setBoostTicks(int ticks) {
      this.f = true;
      this.g = 0;
      this.c.b(this.d, ticks);
   }

   public void a(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Saddle", this.d());
   }

   public void b(NBTTagCompound nbttagcompound) {
      this.a(nbttagcompound.q("Saddle"));
   }

   public void a(boolean flag) {
      this.c.b(this.e, flag);
   }

   public boolean d() {
      return this.c.a(this.e);
   }
}
