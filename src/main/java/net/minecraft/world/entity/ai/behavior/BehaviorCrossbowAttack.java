package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.ICrossbow;
import net.minecraft.world.entity.projectile.ProjectileHelper;
import net.minecraft.world.item.ItemCrossbow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BehaviorCrossbowAttack<E extends EntityInsentient & ICrossbow, T extends EntityLiving> extends Behavior<E> {
   private static final int c = 1200;
   private int d;
   private BehaviorCrossbowAttack.BowState e = BehaviorCrossbowAttack.BowState.a;

   public BehaviorCrossbowAttack() {
      super(ImmutableMap.of(MemoryModuleType.n, MemoryStatus.c, MemoryModuleType.o, MemoryStatus.a), 1200);
   }

   protected boolean a(WorldServer var0, E var1) {
      EntityLiving var2 = b(var1);
      return var1.b(Items.uT) && BehaviorUtil.b(var1, var2) && BehaviorUtil.a(var1, var2, 0);
   }

   protected boolean a(WorldServer var0, E var1, long var2) {
      return var1.dH().a(MemoryModuleType.o) && this.a(var0, var1);
   }

   protected void b(WorldServer var0, E var1, long var2) {
      EntityLiving var4 = b(var1);
      this.b(var1, var4);
      this.a(var1, var4);
   }

   protected void c(WorldServer var0, E var1, long var2) {
      if (var1.fe()) {
         var1.fk();
      }

      if (var1.b(Items.uT)) {
         var1.b(false);
         ItemCrossbow.a(var1.fg(), false);
      }
   }

   private void a(E var0, EntityLiving var1) {
      if (this.e == BehaviorCrossbowAttack.BowState.a) {
         var0.c(ProjectileHelper.a(var0, Items.uT));
         this.e = BehaviorCrossbowAttack.BowState.b;
         var0.b(true);
      } else if (this.e == BehaviorCrossbowAttack.BowState.b) {
         if (!var0.fe()) {
            this.e = BehaviorCrossbowAttack.BowState.a;
         }

         int var2 = var0.fi();
         ItemStack var3 = var0.fg();
         if (var2 >= ItemCrossbow.k(var3)) {
            var0.fj();
            this.e = BehaviorCrossbowAttack.BowState.c;
            this.d = 20 + var0.dZ().a(20);
            var0.b(false);
         }
      } else if (this.e == BehaviorCrossbowAttack.BowState.c) {
         --this.d;
         if (this.d == 0) {
            this.e = BehaviorCrossbowAttack.BowState.d;
         }
      } else if (this.e == BehaviorCrossbowAttack.BowState.d) {
         var0.a(var1, 1.0F);
         ItemStack var2 = var0.b(ProjectileHelper.a(var0, Items.uT));
         ItemCrossbow.a(var2, false);
         this.e = BehaviorCrossbowAttack.BowState.a;
      }
   }

   private void b(EntityInsentient var0, EntityLiving var1) {
      var0.dH().a(MemoryModuleType.n, new BehaviorPositionEntity(var1, true));
   }

   private static EntityLiving b(EntityLiving var0) {
      return var0.dH().c(MemoryModuleType.o).get();
   }

   static enum BowState {
      a,
      b,
      c,
      d;
   }
}
