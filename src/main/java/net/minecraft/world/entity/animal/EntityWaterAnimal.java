package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;

public abstract class EntityWaterAnimal extends EntityCreature {
   protected EntityWaterAnimal(EntityTypes<? extends EntityWaterAnimal> var0, World var1) {
      super(var0, var1);
      this.a(PathType.j, 0.0F);
   }

   @Override
   public boolean dK() {
      return true;
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.e;
   }

   @Override
   public boolean a(IWorldReader var0) {
      return var0.f(this);
   }

   @Override
   public int K() {
      return 120;
   }

   @Override
   public int dX() {
      return 1 + this.H.z.a(3);
   }

   protected void b(int var0) {
      if (this.bq() && !this.aW()) {
         this.i(var0 - 1);
         if (this.cd() == -20) {
            this.i(0);
            this.a(this.dG().h(), 2.0F);
         }
      } else {
         this.i(300);
      }
   }

   @Override
   public void ao() {
      int var0 = this.cd();
      super.ao();
      this.b(var0);
   }

   @Override
   public boolean cv() {
      return false;
   }

   @Override
   public boolean a(EntityHuman var0) {
      return false;
   }

   public static boolean c(EntityTypes<? extends EntityWaterAnimal> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      int var5 = var1.m_();
      int var6 = var5 - 13;
      return var3.v() >= var6 && var3.v() <= var5 && var1.b_(var3.d()).a(TagsFluid.a) && var1.a_(var3.c()).a(Blocks.G);
   }
}
