package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Material;

public class ItemSword extends ItemToolMaterial implements ItemVanishable {
   private final float a;
   private final Multimap<AttributeBase, AttributeModifier> b;

   public ItemSword(ToolMaterial var0, int var1, float var2, Item.Info var3) {
      super(var0, var3);
      this.a = (float)var1 + var0.c();
      Builder<AttributeBase, AttributeModifier> var4 = ImmutableMultimap.builder();
      var4.put(GenericAttributes.f, new AttributeModifier(m, "Weapon modifier", (double)this.a, AttributeModifier.Operation.a));
      var4.put(GenericAttributes.h, new AttributeModifier(n, "Weapon modifier", (double)var2, AttributeModifier.Operation.a));
      this.b = var4.build();
   }

   public float h() {
      return this.a;
   }

   @Override
   public boolean a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3) {
      return !var3.f();
   }

   @Override
   public float a(ItemStack var0, IBlockData var1) {
      if (var1.a(Blocks.br)) {
         return 15.0F;
      } else {
         Material var2 = var1.d();
         return var2 != Material.e && var2 != Material.g && !var1.a(TagsBlock.N) && var2 != Material.Q ? 1.0F : 1.5F;
      }
   }

   @Override
   public boolean a(ItemStack var0, EntityLiving var1, EntityLiving var2) {
      var0.a(1, var2, var0x -> var0x.d(EnumItemSlot.a));
      return true;
   }

   @Override
   public boolean a(ItemStack var0, World var1, IBlockData var2, BlockPosition var3, EntityLiving var4) {
      if (var2.h(var1, var3) != 0.0F) {
         var0.a(2, var4, var0x -> var0x.d(EnumItemSlot.a));
      }

      return true;
   }

   @Override
   public boolean a_(IBlockData var0) {
      return var0.a(Blocks.br);
   }

   @Override
   public Multimap<AttributeBase, AttributeModifier> a(EnumItemSlot var0) {
      return var0 == EnumItemSlot.a ? this.b : super.a(var0);
   }
}
