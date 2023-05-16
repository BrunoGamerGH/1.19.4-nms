package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemTool extends ItemToolMaterial implements ItemVanishable {
   private final TagKey<Block> a;
   protected final float b;
   private final float c;
   private final Multimap<AttributeBase, AttributeModifier> d;

   protected ItemTool(float var0, float var1, ToolMaterial var2, TagKey<Block> var3, Item.Info var4) {
      super(var2, var4);
      this.a = var3;
      this.b = var2.b();
      this.c = var0 + var2.c();
      Builder<AttributeBase, AttributeModifier> var5 = ImmutableMultimap.builder();
      var5.put(GenericAttributes.f, new AttributeModifier(m, "Tool modifier", (double)this.c, AttributeModifier.Operation.a));
      var5.put(GenericAttributes.h, new AttributeModifier(n, "Tool modifier", (double)var1, AttributeModifier.Operation.a));
      this.d = var5.build();
   }

   @Override
   public float a(ItemStack var0, IBlockData var1) {
      return var1.a(this.a) ? this.b : 1.0F;
   }

   @Override
   public boolean a(ItemStack var0, EntityLiving var1, EntityLiving var2) {
      var0.a(2, var2, var0x -> var0x.d(EnumItemSlot.a));
      return true;
   }

   @Override
   public boolean a(ItemStack var0, World var1, IBlockData var2, BlockPosition var3, EntityLiving var4) {
      if (!var1.B && var2.h(var1, var3) != 0.0F) {
         var0.a(1, var4, var0x -> var0x.d(EnumItemSlot.a));
      }

      return true;
   }

   @Override
   public Multimap<AttributeBase, AttributeModifier> a(EnumItemSlot var0) {
      return var0 == EnumItemSlot.a ? this.d : super.a(var0);
   }

   public float d() {
      return this.c;
   }

   @Override
   public boolean a_(IBlockData var0) {
      int var1 = this.i().d();
      if (var1 < 3 && var0.a(TagsBlock.bz)) {
         return false;
      } else if (var1 < 2 && var0.a(TagsBlock.bA)) {
         return false;
      } else {
         return var1 < 1 && var0.a(TagsBlock.bB) ? false : var0.a(this.a);
      }
   }
}
