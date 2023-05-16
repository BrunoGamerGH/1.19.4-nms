package net.minecraft.world.entity.animal;

import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class EntityCod extends EntityFishSchool {
   public EntityCod(EntityTypes<? extends EntityCod> var0, World var1) {
      super(var0, var1);
   }

   @Override
   public ItemStack b() {
      return new ItemStack(Items.pP);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.ex;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.ey;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.eA;
   }

   @Override
   protected SoundEffect fT() {
      return SoundEffects.ez;
   }
}
