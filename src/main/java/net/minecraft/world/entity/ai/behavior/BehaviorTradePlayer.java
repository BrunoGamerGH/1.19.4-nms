package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantRecipe;

public class BehaviorTradePlayer extends Behavior<EntityVillager> {
   private static final int c = 900;
   private static final int d = 40;
   @Nullable
   private ItemStack e;
   private final List<ItemStack> f = Lists.newArrayList();
   private int g;
   private int h;
   private int i;

   public BehaviorTradePlayer(int var0, int var1) {
      super(ImmutableMap.of(MemoryModuleType.q, MemoryStatus.a), var0, var1);
   }

   public boolean a(WorldServer var0, EntityVillager var1) {
      BehaviorController<?> var2 = var1.dH();
      if (!var2.c(MemoryModuleType.q).isPresent()) {
         return false;
      } else {
         EntityLiving var3 = var2.c(MemoryModuleType.q).get();
         return var3.ae() == EntityTypes.bt && var1.bq() && var3.bq() && !var1.y_() && var1.f(var3) <= 17.0;
      }
   }

   public boolean a(WorldServer var0, EntityVillager var1, long var2) {
      return this.a(var0, var1) && this.i > 0 && var1.dH().c(MemoryModuleType.q).isPresent();
   }

   public void b(WorldServer var0, EntityVillager var1, long var2) {
      super.d(var0, var1, var2);
      this.d(var1);
      this.g = 0;
      this.h = 0;
      this.i = 40;
   }

   public void c(WorldServer var0, EntityVillager var1, long var2) {
      EntityLiving var4 = this.d(var1);
      this.a(var4, var1);
      if (!this.f.isEmpty()) {
         this.e(var1);
      } else {
         c(var1);
         this.i = Math.min(this.i, 40);
      }

      --this.i;
   }

   public void d(WorldServer var0, EntityVillager var1, long var2) {
      super.b(var0, var1, var2);
      var1.dH().b(MemoryModuleType.q);
      c(var1);
      this.e = null;
   }

   private void a(EntityLiving var0, EntityVillager var1) {
      boolean var2 = false;
      ItemStack var3 = var0.eK();
      if (this.e == null || !ItemStack.c(this.e, var3)) {
         this.e = var3;
         var2 = true;
         this.f.clear();
      }

      if (var2 && !this.e.b()) {
         this.b(var1);
         if (!this.f.isEmpty()) {
            this.i = 900;
            this.a(var1);
         }
      }
   }

   private void a(EntityVillager var0) {
      a(var0, this.f.get(0));
   }

   private void b(EntityVillager var0) {
      for(MerchantRecipe var2 : var0.fU()) {
         if (!var2.p() && this.a(var2)) {
            this.f.add(var2.d());
         }
      }
   }

   private boolean a(MerchantRecipe var0) {
      return ItemStack.c(this.e, var0.b()) || ItemStack.c(this.e, var0.c());
   }

   private static void c(EntityVillager var0) {
      var0.a(EnumItemSlot.a, ItemStack.b);
      var0.a(EnumItemSlot.a, 0.085F);
   }

   private static void a(EntityVillager var0, ItemStack var1) {
      var0.a(EnumItemSlot.a, var1);
      var0.a(EnumItemSlot.a, 0.0F);
   }

   private EntityLiving d(EntityVillager var0) {
      BehaviorController<?> var1 = var0.dH();
      EntityLiving var2 = var1.c(MemoryModuleType.q).get();
      var1.a(MemoryModuleType.n, new BehaviorPositionEntity(var2, true));
      return var2;
   }

   private void e(EntityVillager var0) {
      if (this.f.size() >= 2 && ++this.g >= 40) {
         ++this.h;
         this.g = 0;
         if (this.h > this.f.size() - 1) {
            this.h = 0;
         }

         a(var0, this.f.get(this.h));
      }
   }
}
