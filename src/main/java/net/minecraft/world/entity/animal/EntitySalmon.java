package net.minecraft.world.entity.animal;

import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class EntitySalmon extends EntityFishSchool {
   public EntitySalmon(EntityTypes<? extends EntitySalmon> var0, World var1) {
      super(var0, var1);
   }

   @Override
   public int fU() {
      return 5;
   }

   @Override
   public ItemStack b() {
      return new ItemStack(Items.pO);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.tC;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.tD;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.tF;
   }

   @Override
   protected SoundEffect fT() {
      return SoundEffects.tE;
   }
}
