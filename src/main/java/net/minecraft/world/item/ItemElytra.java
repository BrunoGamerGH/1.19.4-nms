package net.minecraft.world.item;

import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDispenser;

public class ItemElytra extends Item implements Equipable {
   public ItemElytra(Item.Info var0) {
      super(var0);
      BlockDispenser.a(this, ItemArmor.a);
   }

   public static boolean d(ItemStack var0) {
      return var0.j() < var0.k() - 1;
   }

   @Override
   public boolean a(ItemStack var0, ItemStack var1) {
      return var1.a(Items.uQ);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      return this.a(this, var0, var1, var2);
   }

   @Override
   public SoundEffect ak_() {
      return SoundEffects.ad;
   }

   @Override
   public EnumItemSlot g() {
      return EnumItemSlot.e;
   }
}
