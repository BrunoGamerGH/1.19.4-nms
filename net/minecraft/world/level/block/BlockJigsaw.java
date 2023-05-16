package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.BlockPropertyJigsawOrientation;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityJigsaw;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockJigsaw extends Block implements ITileEntity, GameMasterBlock {
   public static final BlockStateEnum<BlockPropertyJigsawOrientation> a = BlockProperties.T;

   protected BlockJigsaw(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, BlockPropertyJigsawOrientation.k));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a().a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(a, var1.a().a(var0.c(a)));
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      EnumDirection var1 = var0.k();
      EnumDirection var2;
      if (var1.o() == EnumDirection.EnumAxis.b) {
         var2 = var0.g().g();
      } else {
         var2 = EnumDirection.b;
      }

      return this.o().a(a, BlockPropertyJigsawOrientation.a(var1, var2));
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityJigsaw(var0, var1);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      TileEntity var6 = var1.c_(var2);
      if (var6 instanceof TileEntityJigsaw && var3.gg()) {
         var3.a((TileEntityJigsaw)var6);
         return EnumInteractionResult.a(var1.B);
      } else {
         return EnumInteractionResult.d;
      }
   }

   public static boolean a(DefinedStructure.BlockInfo var0, DefinedStructure.BlockInfo var1) {
      EnumDirection var2 = h(var0.b);
      EnumDirection var3 = h(var1.b);
      EnumDirection var4 = n(var0.b);
      EnumDirection var5 = n(var1.b);
      TileEntityJigsaw.JointType var6 = TileEntityJigsaw.JointType.a(var0.c.l("joint"))
         .orElseGet(() -> var2.o().d() ? TileEntityJigsaw.JointType.b : TileEntityJigsaw.JointType.a);
      boolean var7 = var6 == TileEntityJigsaw.JointType.a;
      return var2 == var3.g() && (var7 || var4 == var5) && var0.c.l("target").equals(var1.c.l("name"));
   }

   public static EnumDirection h(IBlockData var0) {
      return var0.c(a).a();
   }

   public static EnumDirection n(IBlockData var0) {
      return var0.c(a).b();
   }
}
