package net.minecraft.core.cauldron;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.IDyeable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemLiquidUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockShulkerBox;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.TileEntityBanner;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent.ChangeReason;

public interface CauldronInteraction {
   Map<Item, CauldronInteraction> a = a();
   Map<Item, CauldronInteraction> b = a();
   Map<Item, CauldronInteraction> c = a();
   Map<Item, CauldronInteraction> d = a();
   CauldronInteraction e = (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> a(
         world, blockposition, entityhuman, enumhand, itemstack, Blocks.ft.o().a(LayeredCauldronBlock.e, Integer.valueOf(3)), SoundEffects.cr
      );
   CauldronInteraction f = (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> a(
         world, blockposition, entityhuman, enumhand, itemstack, Blocks.fu.o(), SoundEffects.cu
      );
   CauldronInteraction g = (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> a(
         world, blockposition, entityhuman, enumhand, itemstack, Blocks.fv.o().a(LayeredCauldronBlock.e, Integer.valueOf(3)), SoundEffects.cv
      );
   CauldronInteraction h = (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> {
      Block block = Block.a(itemstack.c());
      if (!(block instanceof BlockShulkerBox)) {
         return EnumInteractionResult.d;
      } else {
         if (!world.B) {
            if (!LayeredCauldronBlock.lowerFillLevel(iblockdata, world, blockposition, entityhuman, ChangeReason.SHULKER_WASH)) {
               return EnumInteractionResult.a;
            }

            ItemStack itemstack1 = new ItemStack(Blocks.kM);
            if (itemstack.t()) {
               itemstack1.c(itemstack.u().h());
            }

            entityhuman.a(enumhand, itemstack1);
            entityhuman.a(StatisticList.Z);
         }

         return EnumInteractionResult.a(world.B);
      }
   };
   CauldronInteraction i = (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> {
      if (TileEntityBanner.c(itemstack) <= 0) {
         return EnumInteractionResult.d;
      } else {
         if (!world.B) {
            if (!LayeredCauldronBlock.lowerFillLevel(iblockdata, world, blockposition, entityhuman, ChangeReason.BANNER_WASH)) {
               return EnumInteractionResult.a;
            }

            ItemStack itemstack1 = itemstack.o();
            itemstack1.f(1);
            TileEntityBanner.d(itemstack1);
            if (!entityhuman.fK().d) {
               itemstack.h(1);
            }

            if (itemstack.b()) {
               entityhuman.a(enumhand, itemstack1);
            } else if (entityhuman.fJ().e(itemstack1)) {
               entityhuman.bO.b();
            } else {
               entityhuman.a(itemstack1, false);
            }

            entityhuman.a(StatisticList.Y);
         }

         return EnumInteractionResult.a(world.B);
      }
   };
   CauldronInteraction j = (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> {
      Item item = itemstack.c();
      if (!(item instanceof IDyeable)) {
         return EnumInteractionResult.d;
      } else {
         IDyeable idyeable = (IDyeable)item;
         if (!idyeable.a(itemstack)) {
            return EnumInteractionResult.d;
         } else {
            if (!world.B) {
               if (!LayeredCauldronBlock.lowerFillLevel(iblockdata, world, blockposition, entityhuman, ChangeReason.ARMOR_WASH)) {
                  return EnumInteractionResult.a;
               }

               idyeable.f_(itemstack);
               entityhuman.a(StatisticList.X);
            }

            return EnumInteractionResult.a(world.B);
         }
      }
   };

   static Object2ObjectOpenHashMap<Item, CauldronInteraction> a() {
      return SystemUtils.a(
         new Object2ObjectOpenHashMap(),
         object2objectopenhashmap -> object2objectopenhashmap.defaultReturnValue(
               (CauldronInteraction)(iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> EnumInteractionResult.d
            )
      );
   }

   EnumInteractionResult interact(IBlockData var1, World var2, BlockPosition var3, EntityHuman var4, EnumHand var5, ItemStack var6);

   static void b() {
      a(a);
      a.put(Items.rr, (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> {
         if (PotionUtil.d(itemstack) != Potions.c) {
            return EnumInteractionResult.d;
         } else {
            if (!world.B) {
               if (!LayeredCauldronBlock.changeLevel(iblockdata, world, blockposition, Blocks.ft.o(), entityhuman, ChangeReason.BOTTLE_EMPTY)) {
                  return EnumInteractionResult.a;
               }

               Item item = itemstack.c();
               entityhuman.a(enumhand, ItemLiquidUtil.a(itemstack, entityhuman, new ItemStack(Items.rs)));
               entityhuman.a(StatisticList.W);
               entityhuman.b(StatisticList.c.b(item));
               world.a(null, blockposition, SoundEffects.cg, SoundCategory.e, 1.0F, 1.0F);
               world.a(null, GameEvent.B, blockposition);
            }

            return EnumInteractionResult.a(world.B);
         }
      });
      a(b);
      b.put(
         Items.pG,
         (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> a(
               iblockdata,
               world,
               blockposition,
               entityhuman,
               enumhand,
               itemstack,
               new ItemStack(Items.pH),
               iblockdata1 -> iblockdata1.c(LayeredCauldronBlock.e) == 3,
               SoundEffects.cx
            )
      );
      b.put(Items.rs, (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> {
         if (!world.B) {
            if (!LayeredCauldronBlock.lowerFillLevel(iblockdata, world, blockposition, entityhuman, ChangeReason.BOTTLE_FILL)) {
               return EnumInteractionResult.a;
            }

            Item item = itemstack.c();
            entityhuman.a(enumhand, ItemLiquidUtil.a(itemstack, entityhuman, PotionUtil.a(new ItemStack(Items.rr), Potions.c)));
            entityhuman.a(StatisticList.W);
            entityhuman.b(StatisticList.c.b(item));
            world.a(null, blockposition, SoundEffects.ch, SoundCategory.e, 1.0F, 1.0F);
            world.a(null, GameEvent.A, blockposition);
         }

         return EnumInteractionResult.a(world.B);
      });
      b.put(
         Items.rr,
         (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> {
            if (iblockdata.c(LayeredCauldronBlock.e) != 3 && PotionUtil.d(itemstack) == Potions.c) {
               if (!world.B) {
                  if (!LayeredCauldronBlock.changeLevel(
                     iblockdata, world, blockposition, iblockdata.a(LayeredCauldronBlock.e), entityhuman, ChangeReason.BOTTLE_EMPTY
                  )) {
                     return EnumInteractionResult.a;
                  }
   
                  entityhuman.a(enumhand, ItemLiquidUtil.a(itemstack, entityhuman, new ItemStack(Items.rs)));
                  entityhuman.a(StatisticList.W);
                  entityhuman.b(StatisticList.c.b(itemstack.c()));
                  world.a(null, blockposition, SoundEffects.cg, SoundCategory.e, 1.0F, 1.0F);
                  world.a(null, GameEvent.B, blockposition);
               }
   
               return EnumInteractionResult.a(world.B);
            } else {
               return EnumInteractionResult.d;
            }
         }
      );
      b.put(Items.oJ, j);
      b.put(Items.oI, j);
      b.put(Items.oH, j);
      b.put(Items.oG, j);
      b.put(Items.tL, j);
      b.put(Items.tR, i);
      b.put(Items.tY, i);
      b.put(Items.ug, i);
      b.put(Items.uc, i);
      b.put(Items.ud, i);
      b.put(Items.ua, i);
      b.put(Items.ue, i);
      b.put(Items.tU, i);
      b.put(Items.tZ, i);
      b.put(Items.tW, i);
      b.put(Items.tT, i);
      b.put(Items.tS, i);
      b.put(Items.tX, i);
      b.put(Items.ub, i);
      b.put(Items.uf, i);
      b.put(Items.tV, i);
      b.put(Items.iF, h);
      b.put(Items.iM, h);
      b.put(Items.iU, h);
      b.put(Items.iQ, h);
      b.put(Items.iR, h);
      b.put(Items.iO, h);
      b.put(Items.iS, h);
      b.put(Items.iI, h);
      b.put(Items.iN, h);
      b.put(Items.iK, h);
      b.put(Items.iH, h);
      b.put(Items.iG, h);
      b.put(Items.iL, h);
      b.put(Items.iP, h);
      b.put(Items.iT, h);
      b.put(Items.iJ, h);
      c.put(
         Items.pG,
         (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> a(
               iblockdata, world, blockposition, entityhuman, enumhand, itemstack, new ItemStack(Items.pI), iblockdata1 -> true, SoundEffects.cA
            )
      );
      a(c);
      d.put(
         Items.pG,
         (iblockdata, world, blockposition, entityhuman, enumhand, itemstack) -> a(
               iblockdata,
               world,
               blockposition,
               entityhuman,
               enumhand,
               itemstack,
               new ItemStack(Items.pJ),
               iblockdata1 -> iblockdata1.c(LayeredCauldronBlock.e) == 3,
               SoundEffects.cB
            )
      );
      a(d);
   }

   static void a(Map<Item, CauldronInteraction> map) {
      map.put(Items.pI, f);
      map.put(Items.pH, e);
      map.put(Items.pJ, g);
   }

   static EnumInteractionResult a(
      IBlockData iblockdata,
      World world,
      BlockPosition blockposition,
      EntityHuman entityhuman,
      EnumHand enumhand,
      ItemStack itemstack,
      ItemStack itemstack1,
      Predicate<IBlockData> predicate,
      SoundEffect soundeffect
   ) {
      if (!predicate.test(iblockdata)) {
         return EnumInteractionResult.d;
      } else {
         if (!world.B) {
            if (!LayeredCauldronBlock.changeLevel(iblockdata, world, blockposition, Blocks.fs.o(), entityhuman, ChangeReason.BUCKET_FILL)) {
               return EnumInteractionResult.a;
            }

            Item item = itemstack.c();
            entityhuman.a(enumhand, ItemLiquidUtil.a(itemstack, entityhuman, itemstack1));
            entityhuman.a(StatisticList.W);
            entityhuman.b(StatisticList.c.b(item));
            world.a(null, blockposition, soundeffect, SoundCategory.e, 1.0F, 1.0F);
            world.a(null, GameEvent.A, blockposition);
         }

         return EnumInteractionResult.a(world.B);
      }
   }

   static EnumInteractionResult a(
      World world,
      BlockPosition blockposition,
      EntityHuman entityhuman,
      EnumHand enumhand,
      ItemStack itemstack,
      IBlockData iblockdata,
      SoundEffect soundeffect
   ) {
      if (!world.B) {
         if (!LayeredCauldronBlock.changeLevel(iblockdata, world, blockposition, iblockdata, entityhuman, ChangeReason.BUCKET_EMPTY)) {
            return EnumInteractionResult.a;
         }

         Item item = itemstack.c();
         entityhuman.a(enumhand, ItemLiquidUtil.a(itemstack, entityhuman, new ItemStack(Items.pG)));
         entityhuman.a(StatisticList.V);
         entityhuman.b(StatisticList.c.b(item));
         world.a(null, blockposition, soundeffect, SoundCategory.e, 1.0F, 1.0F);
         world.a(null, GameEvent.B, blockposition);
      }

      return EnumInteractionResult.a(world.B);
   }
}
