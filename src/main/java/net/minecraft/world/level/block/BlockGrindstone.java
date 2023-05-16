package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.TileInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerGrindstone;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyAttachPosition;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockGrindstone extends BlockAttachable {
   public static final VoxelShape a = Block.a(2.0, 0.0, 6.0, 4.0, 7.0, 10.0);
   public static final VoxelShape b = Block.a(12.0, 0.0, 6.0, 14.0, 7.0, 10.0);
   public static final VoxelShape c = Block.a(2.0, 7.0, 5.0, 4.0, 13.0, 11.0);
   public static final VoxelShape d = Block.a(12.0, 7.0, 5.0, 14.0, 13.0, 11.0);
   public static final VoxelShape e = VoxelShapes.a(a, c);
   public static final VoxelShape f = VoxelShapes.a(b, d);
   public static final VoxelShape g = VoxelShapes.a(e, f);
   public static final VoxelShape h = VoxelShapes.a(g, Block.a(4.0, 4.0, 2.0, 12.0, 16.0, 14.0));
   public static final VoxelShape i = Block.a(6.0, 0.0, 2.0, 10.0, 7.0, 4.0);
   public static final VoxelShape j = Block.a(6.0, 0.0, 12.0, 10.0, 7.0, 14.0);
   public static final VoxelShape k = Block.a(5.0, 7.0, 2.0, 11.0, 13.0, 4.0);
   public static final VoxelShape l = Block.a(5.0, 7.0, 12.0, 11.0, 13.0, 14.0);
   public static final VoxelShape m = VoxelShapes.a(i, k);
   public static final VoxelShape n = VoxelShapes.a(j, l);
   public static final VoxelShape E = VoxelShapes.a(m, n);
   public static final VoxelShape F = VoxelShapes.a(E, Block.a(2.0, 4.0, 4.0, 14.0, 16.0, 12.0));
   public static final VoxelShape G = Block.a(2.0, 6.0, 0.0, 4.0, 10.0, 7.0);
   public static final VoxelShape H = Block.a(12.0, 6.0, 0.0, 14.0, 10.0, 7.0);
   public static final VoxelShape I = Block.a(2.0, 5.0, 7.0, 4.0, 11.0, 13.0);
   public static final VoxelShape K = Block.a(12.0, 5.0, 7.0, 14.0, 11.0, 13.0);
   public static final VoxelShape L = VoxelShapes.a(G, I);
   public static final VoxelShape M = VoxelShapes.a(H, K);
   public static final VoxelShape N = VoxelShapes.a(L, M);
   public static final VoxelShape O = VoxelShapes.a(N, Block.a(4.0, 2.0, 4.0, 12.0, 14.0, 16.0));
   public static final VoxelShape P = Block.a(2.0, 6.0, 7.0, 4.0, 10.0, 16.0);
   public static final VoxelShape Q = Block.a(12.0, 6.0, 7.0, 14.0, 10.0, 16.0);
   public static final VoxelShape R = Block.a(2.0, 5.0, 3.0, 4.0, 11.0, 9.0);
   public static final VoxelShape S = Block.a(12.0, 5.0, 3.0, 14.0, 11.0, 9.0);
   public static final VoxelShape T = VoxelShapes.a(P, R);
   public static final VoxelShape U = VoxelShapes.a(Q, S);
   public static final VoxelShape V = VoxelShapes.a(T, U);
   public static final VoxelShape W = VoxelShapes.a(V, Block.a(4.0, 2.0, 0.0, 12.0, 14.0, 12.0));
   public static final VoxelShape X = Block.a(7.0, 6.0, 2.0, 16.0, 10.0, 4.0);
   public static final VoxelShape Y = Block.a(7.0, 6.0, 12.0, 16.0, 10.0, 14.0);
   public static final VoxelShape Z = Block.a(3.0, 5.0, 2.0, 9.0, 11.0, 4.0);
   public static final VoxelShape aa = Block.a(3.0, 5.0, 12.0, 9.0, 11.0, 14.0);
   public static final VoxelShape ab = VoxelShapes.a(X, Z);
   public static final VoxelShape ac = VoxelShapes.a(Y, aa);
   public static final VoxelShape ad = VoxelShapes.a(ab, ac);
   public static final VoxelShape ae = VoxelShapes.a(ad, Block.a(0.0, 2.0, 4.0, 12.0, 14.0, 12.0));
   public static final VoxelShape af = Block.a(0.0, 6.0, 2.0, 9.0, 10.0, 4.0);
   public static final VoxelShape ag = Block.a(0.0, 6.0, 12.0, 9.0, 10.0, 14.0);
   public static final VoxelShape ah = Block.a(7.0, 5.0, 2.0, 13.0, 11.0, 4.0);
   public static final VoxelShape ai = Block.a(7.0, 5.0, 12.0, 13.0, 11.0, 14.0);
   public static final VoxelShape aj = VoxelShapes.a(af, ah);
   public static final VoxelShape ak = VoxelShapes.a(ag, ai);
   public static final VoxelShape al = VoxelShapes.a(aj, ak);
   public static final VoxelShape am = VoxelShapes.a(al, Block.a(4.0, 2.0, 4.0, 16.0, 14.0, 12.0));
   public static final VoxelShape an = Block.a(2.0, 9.0, 6.0, 4.0, 16.0, 10.0);
   public static final VoxelShape ao = Block.a(12.0, 9.0, 6.0, 14.0, 16.0, 10.0);
   public static final VoxelShape ap = Block.a(2.0, 3.0, 5.0, 4.0, 9.0, 11.0);
   public static final VoxelShape aq = Block.a(12.0, 3.0, 5.0, 14.0, 9.0, 11.0);
   public static final VoxelShape ar = VoxelShapes.a(an, ap);
   public static final VoxelShape as = VoxelShapes.a(ao, aq);
   public static final VoxelShape at = VoxelShapes.a(ar, as);
   public static final VoxelShape au = VoxelShapes.a(at, Block.a(4.0, 0.0, 2.0, 12.0, 12.0, 14.0));
   public static final VoxelShape av = Block.a(6.0, 9.0, 2.0, 10.0, 16.0, 4.0);
   public static final VoxelShape aw = Block.a(6.0, 9.0, 12.0, 10.0, 16.0, 14.0);
   public static final VoxelShape ax = Block.a(5.0, 3.0, 2.0, 11.0, 9.0, 4.0);
   public static final VoxelShape ay = Block.a(5.0, 3.0, 12.0, 11.0, 9.0, 14.0);
   public static final VoxelShape az = VoxelShapes.a(av, ax);
   public static final VoxelShape aA = VoxelShapes.a(aw, ay);
   public static final VoxelShape aB = VoxelShapes.a(az, aA);
   public static final VoxelShape aC = VoxelShapes.a(aB, Block.a(2.0, 0.0, 4.0, 14.0, 12.0, 12.0));
   private static final IChatBaseComponent aR = IChatBaseComponent.c("container.grindstone_title");

   protected BlockGrindstone(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(aD, EnumDirection.c).a(J, BlockPropertyAttachPosition.b));
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   private VoxelShape n(IBlockData var0) {
      EnumDirection var1 = var0.c(aD);
      switch((BlockPropertyAttachPosition)var0.c(J)) {
         case a:
            if (var1 != EnumDirection.c && var1 != EnumDirection.d) {
               return F;
            }

            return h;
         case b:
            if (var1 == EnumDirection.c) {
               return W;
            } else if (var1 == EnumDirection.d) {
               return O;
            } else {
               if (var1 == EnumDirection.f) {
                  return am;
               }

               return ae;
            }
         case c:
            if (var1 != EnumDirection.c && var1 != EnumDirection.d) {
               return aC;
            }

            return au;
         default:
            return F;
      }
   }

   @Override
   public VoxelShape c(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.n(var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return this.n(var0);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return true;
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         var3.a(var0.b(var1, var2));
         var3.a(StatisticList.aD);
         return EnumInteractionResult.b;
      }
   }

   @Override
   public ITileInventory b(IBlockData var0, World var1, BlockPosition var2) {
      return new TileInventory((var2x, var3x, var4) -> new ContainerGrindstone(var2x, var3x, ContainerAccess.a(var1, var2)), aR);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(aD, var1.a(var0.c(aD)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(aD)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(aD, J);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
