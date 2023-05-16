package net.minecraft.world.entity.decoration;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class GlowItemFrame extends EntityItemFrame {
   public GlowItemFrame(EntityTypes<? extends EntityItemFrame> var0, World var1) {
      super(var0, var1);
   }

   public GlowItemFrame(World var0, BlockPosition var1, EnumDirection var2) {
      super(EntityTypes.S, var0, var1, var2);
   }

   @Override
   public SoundEffect i() {
      return SoundEffects.jo;
   }

   @Override
   public SoundEffect j() {
      return SoundEffects.jm;
   }

   @Override
   public SoundEffect k() {
      return SoundEffects.jn;
   }

   @Override
   public SoundEffect o() {
      return SoundEffects.jl;
   }

   @Override
   public SoundEffect p() {
      return SoundEffects.jp;
   }

   @Override
   protected ItemStack q() {
      return new ItemStack(Items.tf);
   }
}
