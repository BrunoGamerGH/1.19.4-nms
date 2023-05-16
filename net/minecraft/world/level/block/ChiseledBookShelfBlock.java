package net.minecraft.world.level.block;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.jetbrains.annotations.Nullable;

public class ChiseledBookShelfBlock extends BlockTileEntity {
   private static final int c = 6;
   public static final int a = 3;
   public static final List<BlockStateBoolean> b = List.of(
      BlockProperties.bp, BlockProperties.bq, BlockProperties.br, BlockProperties.bs, BlockProperties.bt, BlockProperties.bu
   );

   public ChiseledBookShelfBlock(BlockBase.Info var0) {
      super(var0);
      IBlockData var1 = this.D.b().a(BlockFacingHorizontal.aD, EnumDirection.c);

      for(BlockStateBoolean var3 : b) {
         var1 = var1.a(var3, Boolean.valueOf(false));
      }

      this.k(var1);
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      TileEntity var7 = var1.c_(var2);
      if (var7 instanceof ChiseledBookShelfBlockEntity var6) {
         Optional<Vec2F> var7x = a(var5, var0.c(BlockFacingHorizontal.aD));
         if (var7x.isEmpty()) {
            return EnumInteractionResult.d;
         } else {
            int var8x = a(var7x.get());
            if (var0.c(b.get(var8x))) {
               a(var1, var2, var3, var6, var8x);
               return EnumInteractionResult.a(var1.B);
            } else {
               ItemStack var9 = var3.b(var4);
               if (var9.a(TagsItem.au)) {
                  a(var1, var2, var3, var6, var9, var8x);
                  return EnumInteractionResult.a(var1.B);
               } else {
                  return EnumInteractionResult.b;
               }
            }
         }
      } else {
         return EnumInteractionResult.d;
      }
   }

   private static Optional<Vec2F> a(MovingObjectPositionBlock var0, EnumDirection var1) {
      EnumDirection var2 = var0.b();
      if (var1 != var2) {
         return Optional.empty();
      } else {
         BlockPosition var3 = var0.a().a(var2);
         Vec3D var4 = var0.e().a((double)var3.u(), (double)var3.v(), (double)var3.w());
         double var5 = var4.a();
         double var7 = var4.b();
         double var9 = var4.c();

         return switch(var2) {
            case c -> Optional.of(new Vec2F((float)(1.0 - var5), (float)var7));
            case d -> Optional.of(new Vec2F((float)var5, (float)var7));
            case e -> Optional.of(new Vec2F((float)var9, (float)var7));
            case f -> Optional.of(new Vec2F((float)(1.0 - var9), (float)var7));
            case a, b -> Optional.empty();
         };
      }
   }

   private static int a(Vec2F var0) {
      int var1 = var0.j >= 0.5F ? 0 : 1;
      int var2 = a(var0.i);
      return var2 + var1 * 3;
   }

   private static int a(float var0) {
      float var1 = 0.0625F;
      float var2 = 0.375F;
      if (var0 < 0.375F) {
         return 0;
      } else {
         float var3 = 0.6875F;
         return var0 < 0.6875F ? 1 : 2;
      }
   }

   private static void a(World var0, BlockPosition var1, EntityHuman var2, ChiseledBookShelfBlockEntity var3, ItemStack var4, int var5) {
      if (!var0.B) {
         var2.b(StatisticList.c.b(var4.c()));
         SoundEffect var6 = var4.a(Items.ty) ? SoundEffects.ep : SoundEffects.eo;
         var3.a(var5, var4.a(1));
         var0.a(null, var1, var6, SoundCategory.e, 1.0F, 1.0F);
         if (var2.f()) {
            var4.g(1);
         }

         var0.a(var2, GameEvent.c, var1);
      }
   }

   private static void a(World var0, BlockPosition var1, EntityHuman var2, ChiseledBookShelfBlockEntity var3, int var4) {
      if (!var0.B) {
         ItemStack var5 = var3.a(var4, 1);
         SoundEffect var6 = var5.a(Items.ty) ? SoundEffects.es : SoundEffects.er;
         var0.a(null, var1, var6, SoundCategory.e, 1.0F, 1.0F);
         if (!var2.fJ().e(var5)) {
            var2.a(var5, false);
         }

         var0.a(var2, GameEvent.c, var1);
      }
   }

   @Nullable
   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new ChiseledBookShelfBlockEntity(var0, var1);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(BlockFacingHorizontal.aD);
      b.forEach(var1x -> var0.a(var1x));
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var3.b())) {
         TileEntity var5 = var1.c_(var2);
         if (var5 instanceof ChiseledBookShelfBlockEntity var6 && !var6.aa_()) {
            for(int var7 = 0; var7 < 6; ++var7) {
               ItemStack var8 = var6.a(var7);
               if (!var8.b()) {
                  InventoryUtils.a(var1, (double)var2.u(), (double)var2.v(), (double)var2.w(), var8);
               }
            }

            var6.a();
            var1.c(var2, this);
         }

         super.a(var0, var1, var2, var3, var4);
      }
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(BlockFacingHorizontal.aD, var0.g().g());
   }

   @Override
   public boolean d_(IBlockData var0) {
      return true;
   }

   @Override
   public int a(IBlockData var0, World var1, BlockPosition var2) {
      if (var1.k_()) {
         return 0;
      } else {
         TileEntity var5 = var1.c_(var2);
         return var5 instanceof ChiseledBookShelfBlockEntity var3 ? var3.g() + 1 : 0;
      }
   }
}
