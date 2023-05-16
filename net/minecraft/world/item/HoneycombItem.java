package net.minecraft.world.item;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;

public class HoneycombItem extends Item {
   public static final Supplier<BiMap<Block, Block>> a = Suppliers.memoize(
      () -> ImmutableBiMap.builder()
            .put(Blocks.qH, Blocks.qW)
            .put(Blocks.qG, Blocks.qY)
            .put(Blocks.qF, Blocks.qX)
            .put(Blocks.qE, Blocks.qZ)
            .put(Blocks.qN, Blocks.rd)
            .put(Blocks.qM, Blocks.rc)
            .put(Blocks.qL, Blocks.rb)
            .put(Blocks.qK, Blocks.ra)
            .put(Blocks.qV, Blocks.rl)
            .put(Blocks.qU, Blocks.rk)
            .put(Blocks.qT, Blocks.rj)
            .put(Blocks.qS, Blocks.ri)
            .put(Blocks.qR, Blocks.rh)
            .put(Blocks.qQ, Blocks.rg)
            .put(Blocks.qP, Blocks.rf)
            .put(Blocks.qO, Blocks.re)
            .build()
   );
   public static final Supplier<BiMap<Block, Block>> b = Suppliers.memoize(() -> ((BiMap)a.get()).inverse());

   public HoneycombItem(Item.Info var0) {
      super(var0);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      World var1 = var0.q();
      BlockPosition var2 = var0.a();
      IBlockData var3 = var1.a_(var2);
      return b(var3).map(var3x -> {
         EntityHuman var4x = var0.o();
         ItemStack var5 = var0.n();
         if (var4x instanceof EntityPlayer) {
            CriterionTriggers.M.a((EntityPlayer)var4x, var2, var5);
         }

         var5.h(1);
         var1.a(var2, var3x, 11);
         var1.a(GameEvent.c, var2, GameEvent.a.a(var4x, var3x));
         var1.a(var4x, 3003, var2, 0);
         return EnumInteractionResult.a(var1.B);
      }).orElse(EnumInteractionResult.d);
   }

   public static Optional<IBlockData> b(IBlockData var0) {
      return Optional.ofNullable((Block)((BiMap)a.get()).get(var0.b())).map(var1 -> ((Block)var1).l(var0));
   }
}
