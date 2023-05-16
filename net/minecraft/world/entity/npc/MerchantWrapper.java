package net.minecraft.world.entity.npc;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.IMerchant;
import net.minecraft.world.item.trading.MerchantRecipe;
import net.minecraft.world.item.trading.MerchantRecipeList;

public class MerchantWrapper implements IMerchant {
   private final EntityHuman a;
   private MerchantRecipeList b = new MerchantRecipeList();
   private int c;

   public MerchantWrapper(EntityHuman var0) {
      this.a = var0;
   }

   @Override
   public EntityHuman fS() {
      return this.a;
   }

   @Override
   public void e(@Nullable EntityHuman var0) {
   }

   @Override
   public MerchantRecipeList fU() {
      return this.b;
   }

   @Override
   public void a(MerchantRecipeList var0) {
      this.b = var0;
   }

   @Override
   public void a(MerchantRecipe var0) {
      var0.j();
   }

   @Override
   public void l(ItemStack var0) {
   }

   @Override
   public boolean ga() {
      return this.a.Y().B;
   }

   @Override
   public int r() {
      return this.c;
   }

   @Override
   public void s(int var0) {
      this.c = var0;
   }

   @Override
   public boolean fV() {
      return true;
   }

   @Override
   public SoundEffect fW() {
      return SoundEffects.yo;
   }
}
