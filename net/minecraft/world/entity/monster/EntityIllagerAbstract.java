package net.minecraft.world.entity.monster;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.ai.goal.PathfinderGoalDoorOpen;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.level.World;

public abstract class EntityIllagerAbstract extends EntityRaider {
   protected EntityIllagerAbstract(EntityTypes<? extends EntityIllagerAbstract> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected void x() {
      super.x();
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.d;
   }

   public EntityIllagerAbstract.a q() {
      return EntityIllagerAbstract.a.a;
   }

   @Override
   public boolean c(EntityLiving var0) {
      return var0 instanceof EntityVillagerAbstract && var0.y_() ? false : super.c(var0);
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e,
      f,
      g,
      h;
   }

   protected class b extends PathfinderGoalDoorOpen {
      public b(EntityRaider var1) {
         super(var1, false);
      }

      @Override
      public boolean a() {
         return super.a() && EntityIllagerAbstract.this.gh();
      }
   }
}
