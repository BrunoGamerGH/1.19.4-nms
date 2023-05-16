package net.minecraft.world.entity.ai.behavior;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.phys.Vec3D;

public class BehaviorPositionEntity implements BehaviorPosition {
   private final Entity a;
   private final boolean b;

   public BehaviorPositionEntity(Entity var0, boolean var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public Vec3D a() {
      return this.b ? this.a.de().b(0.0, (double)this.a.cE(), 0.0) : this.a.de();
   }

   @Override
   public BlockPosition b() {
      return this.a.dg();
   }

   @Override
   public boolean a(EntityLiving var0) {
      Entity var2 = this.a;
      if (var2 instanceof EntityLiving var1) {
         if (!var1.bq()) {
            return false;
         } else {
            Optional<NearestVisibleLivingEntities> var2x = var0.dH().c(MemoryModuleType.h);
            return var2x.isPresent() && var2x.get().a(var1);
         }
      } else {
         return true;
      }
   }

   public Entity c() {
      return this.a;
   }

   @Override
   public String toString() {
      return "EntityTracker for " + this.a;
   }
}
