package net.minecraft.world.entity.ai.behavior;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.allay.AllayAi;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public class GoAndGiveItemsToTarget<E extends EntityLiving & InventoryCarrier> extends Behavior<E> {
   private static final int c = 3;
   private static final int d = 60;
   private final Function<EntityLiving, Optional<BehaviorPosition>> e;
   private final float f;

   public GoAndGiveItemsToTarget(Function<EntityLiving, Optional<BehaviorPosition>> var0, float var1, int var2) {
      super(Map.of(MemoryModuleType.n, MemoryStatus.c, MemoryModuleType.m, MemoryStatus.c, MemoryModuleType.aO, MemoryStatus.c), var2);
      this.e = var0;
      this.f = var1;
   }

   @Override
   protected boolean a(WorldServer var0, E var1) {
      return this.b(var1);
   }

   @Override
   protected boolean a(WorldServer var0, E var1, long var2) {
      return this.b(var1);
   }

   @Override
   protected void d(WorldServer var0, E var1, long var2) {
      this.e.apply(var1).ifPresent(var1x -> BehaviorUtil.a(var1, var1x, this.f, 3));
   }

   @Override
   protected void c(WorldServer var0, E var1, long var2) {
      Optional<BehaviorPosition> var4 = this.e.apply(var1);
      if (!var4.isEmpty()) {
         BehaviorPosition var5 = var4.get();
         double var6 = var5.a().f(var1.bk());
         if (var6 < 3.0) {
            ItemStack var8 = var1.w().a(0, 1);
            if (!var8.b()) {
               a(var1, var8, a(var5));
               if (var1 instanceof Allay var9) {
                  AllayAi.a((EntityLiving)var9).ifPresent(var2x -> this.a(var5, var8, var2x));
               }

               var1.dH().a(MemoryModuleType.aO, 60);
            }
         }
      }
   }

   private void a(BehaviorPosition var0, ItemStack var1, EntityPlayer var2) {
      BlockPosition var3 = var0.b().d();
      CriterionTriggers.X.a(var2, var3, var1);
   }

   private boolean b(E var0) {
      if (var0.w().aa_()) {
         return false;
      } else {
         Optional<BehaviorPosition> var1 = this.e.apply(var0);
         return var1.isPresent();
      }
   }

   private static Vec3D a(BehaviorPosition var0) {
      return var0.a().b(0.0, 1.0, 0.0);
   }

   public static void a(EntityLiving var0, ItemStack var1, Vec3D var2) {
      Vec3D var3 = new Vec3D(0.2F, 0.3F, 0.2F);
      BehaviorUtil.a(var0, var1, var2, var3, 0.2F);
      World var4 = var0.H;
      if (var4.U() % 7L == 0L && var4.z.j() < 0.9) {
         float var5 = SystemUtils.<Float>a(Allay.d, var4.r_());
         var4.a(null, var0, SoundEffects.g, SoundCategory.g, 1.0F, var5);
      }
   }
}
