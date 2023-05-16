package net.minecraft.world.item.enchantment;

import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EnchantmentFrostWalker extends Enchantment {
   public EnchantmentFrostWalker(Enchantment.Rarity enchantment_rarity, EnumItemSlot... aenumitemslot) {
      super(enchantment_rarity, EnchantmentSlotType.b, aenumitemslot);
   }

   @Override
   public int a(int i) {
      return i * 10;
   }

   @Override
   public int b(int i) {
      return this.a(i) + 15;
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public int a() {
      return 2;
   }

   public static void a(EntityLiving entityliving, World world, BlockPosition blockposition, int i) {
      if (entityliving.ax()) {
         IBlockData iblockdata = Blocks.kF.o();
         int j = Math.min(16, 2 + i);
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(BlockPosition blockposition1 : BlockPosition.a(blockposition.b(-j, -1, -j), blockposition.b(j, -1, j))) {
            if (blockposition1.a(entityliving.de(), (double)j)) {
               blockposition_mutableblockposition.d(blockposition1.u(), blockposition1.v() + 1, blockposition1.w());
               IBlockData iblockdata1 = world.a_(blockposition_mutableblockposition);
               if (iblockdata1.h()) {
                  IBlockData iblockdata2 = world.a_(blockposition1);
                  if (iblockdata2.d() == Material.j
                     && iblockdata2.c(BlockFluids.a) == 0
                     && iblockdata.a((IWorldReader)world, blockposition1)
                     && world.a(iblockdata, blockposition1, VoxelShapeCollision.a())
                     && CraftEventFactory.handleBlockFormEvent(world, blockposition1, iblockdata, entityliving)) {
                     world.a(blockposition1, Blocks.kF, MathHelper.a(entityliving.dZ(), 60, 120));
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean a(Enchantment enchantment) {
      return super.a(enchantment) && enchantment != Enchantments.i;
   }
}
