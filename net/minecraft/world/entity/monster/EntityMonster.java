package net.minecraft.world.entity.monster;

import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemProjectileWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.dimension.DimensionManager;

public abstract class EntityMonster extends EntityCreature implements IMonster {
   protected EntityMonster(EntityTypes<? extends EntityMonster> var0, World var1) {
      super(var0, var1);
      this.bI = 5;
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.f;
   }

   @Override
   public void b_() {
      this.eH();
      this.fX();
      super.b_();
   }

   protected void fX() {
      float var0 = this.bh();
      if (var0 > 0.5F) {
         this.ba += 2;
      }
   }

   @Override
   protected boolean R() {
      return true;
   }

   @Override
   protected SoundEffect aI() {
      return SoundEffects.ln;
   }

   @Override
   protected SoundEffect aJ() {
      return SoundEffects.lm;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.lk;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.lj;
   }

   @Override
   public EntityLiving.a ey() {
      return new EntityLiving.a(SoundEffects.ll, SoundEffects.li);
   }

   @Override
   public float a(BlockPosition var0, IWorldReader var1) {
      return -var1.y(var0);
   }

   public static boolean a(WorldAccess var0, BlockPosition var1, RandomSource var2) {
      if (var0.a(EnumSkyBlock.a, var1) > var2.a(32)) {
         return false;
      } else {
         DimensionManager var3 = var0.q_();
         int var4 = var3.e();
         if (var4 < 15 && var0.a(EnumSkyBlock.b, var1) > var4) {
            return false;
         } else {
            int var5 = var0.C().X() ? var0.c(var1, 10) : var0.C(var1);
            return var5 <= var3.d().a(var2);
         }
      }
   }

   public static boolean b(EntityTypes<? extends EntityMonster> var0, WorldAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      return var1.ah() != EnumDifficulty.a && a(var1, var3, var4) && a(var0, var1, var2, var3, var4);
   }

   public static boolean c(EntityTypes<? extends EntityMonster> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      return var1.ah() != EnumDifficulty.a && a(var0, var1, var2, var3, var4);
   }

   public static AttributeProvider.Builder fY() {
      return EntityInsentient.y().a(GenericAttributes.f);
   }

   @Override
   public boolean dV() {
      return true;
   }

   @Override
   protected boolean dW() {
      return true;
   }

   public boolean e(EntityHuman var0) {
      return true;
   }

   @Override
   public ItemStack g(ItemStack var0) {
      if (var0.c() instanceof ItemProjectileWeapon) {
         Predicate<ItemStack> var1 = ((ItemProjectileWeapon)var0.c()).e();
         ItemStack var2 = ItemProjectileWeapon.a(this, var1);
         return var2.b() ? new ItemStack(Items.nD) : var2;
      } else {
         return ItemStack.b;
      }
   }
}
