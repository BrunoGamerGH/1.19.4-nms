package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockFlowerPot extends Block {
   private static final Map<Block, Block> c = Maps.newHashMap();
   public static final float a = 3.0F;
   protected static final VoxelShape b = Block.a(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
   private final Block d;

   public BlockFlowerPot(Block var0, BlockBase.Info var1) {
      super(var1);
      this.d = var0;
      c.put(var0, this);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b;
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      ItemStack var6 = var3.b(var4);
      Item var7 = var6.c();
      IBlockData var8 = (var7 instanceof ItemBlock ? c.getOrDefault(((ItemBlock)var7).e(), Blocks.a) : Blocks.a).o();
      boolean var9 = var8.a(Blocks.a);
      boolean var10 = this.g();
      if (var9 != var10) {
         if (var10) {
            var1.a(var2, var8, 3);
            var3.a(StatisticList.ah);
            if (!var3.fK().d) {
               var6.h(1);
            }
         } else {
            ItemStack var11 = new ItemStack(this.d);
            if (var6.b()) {
               var3.a(var4, var11);
            } else if (!var3.i(var11)) {
               var3.a(var11, false);
            }

            var1.a(var2, Blocks.fQ.o(), 3);
         }

         var1.a(var3, GameEvent.c, var2);
         return EnumInteractionResult.a(var1.B);
      } else {
         return EnumInteractionResult.b;
      }
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return this.g() ? super.a(var0, var1, var2) : new ItemStack(this.d);
   }

   private boolean g() {
      return this.d == Blocks.a;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1 == EnumDirection.a && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   public Block b() {
      return this.d;
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
