package net.minecraft.world.inventory;

import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;

public class Slot {
   public final int a;
   public final IInventory d;
   public int e;
   public final int f;
   public final int g;

   public Slot(IInventory var0, int var1, int var2, int var3) {
      this.d = var0;
      this.a = var1;
      this.f = var2;
      this.g = var3;
   }

   public void a(ItemStack var0, ItemStack var1) {
      int var2 = var1.K() - var0.K();
      if (var2 > 0) {
         this.a(var1, var2);
      }
   }

   protected void a(ItemStack var0, int var1) {
   }

   protected void b(int var0) {
   }

   protected void b_(ItemStack var0) {
   }

   public void a(EntityHuman var0, ItemStack var1) {
      this.d();
   }

   public boolean a(ItemStack var0) {
      return true;
   }

   public ItemStack e() {
      return this.d.a(this.a);
   }

   public boolean f() {
      return !this.e().b();
   }

   public void d(ItemStack var0) {
      this.e(var0);
   }

   public void e(ItemStack var0) {
      this.d.a(this.a, var0);
      this.d();
   }

   public void d() {
      this.d.e();
   }

   public int a() {
      return this.d.ab_();
   }

   public int a_(ItemStack var0) {
      return Math.min(this.a(), var0.f());
   }

   @Nullable
   public Pair<MinecraftKey, MinecraftKey> c() {
      return null;
   }

   public ItemStack a(int var0) {
      return this.d.a(this.a, var0);
   }

   public boolean a(EntityHuman var0) {
      return true;
   }

   public boolean b() {
      return true;
   }

   public Optional<ItemStack> a(int var0, int var1, EntityHuman var2) {
      if (!this.a(var2)) {
         return Optional.empty();
      } else if (!this.b(var2) && var1 < this.e().K()) {
         return Optional.empty();
      } else {
         var0 = Math.min(var0, var1);
         ItemStack var3 = this.a(var0);
         if (var3.b()) {
            return Optional.empty();
         } else {
            if (this.e().b()) {
               this.d(ItemStack.b);
            }

            return Optional.of(var3);
         }
      }
   }

   public ItemStack b(int var0, int var1, EntityHuman var2) {
      Optional<ItemStack> var3 = this.a(var0, var1, var2);
      var3.ifPresent(var1x -> this.a(var2, var1x));
      return var3.orElse(ItemStack.b);
   }

   public ItemStack f(ItemStack var0) {
      return this.b(var0, var0.K());
   }

   public ItemStack b(ItemStack var0, int var1) {
      if (!var0.b() && this.a(var0)) {
         ItemStack var2 = this.e();
         int var3 = Math.min(Math.min(var1, var0.K()), this.a_(var0) - var2.K());
         if (var2.b()) {
            this.d(var0.a(var3));
         } else if (ItemStack.d(var2, var0)) {
            var0.h(var3);
            var2.g(var3);
            this.d(var2);
         }

         return var0;
      } else {
         return var0;
      }
   }

   public boolean b(EntityHuman var0) {
      return this.a(var0) && this.a(this.e());
   }

   public int g() {
      return this.a;
   }
}
