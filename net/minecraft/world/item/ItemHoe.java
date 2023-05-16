package net.minecraft.world.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemHoe extends ItemTool {
   protected static final Map<Block, Pair<Predicate<ItemActionContext>, Consumer<ItemActionContext>>> a = Maps.newHashMap(
      ImmutableMap.of(
         Blocks.i,
         Pair.of(ItemHoe::b, b(Blocks.cB.o())),
         Blocks.kB,
         Pair.of(ItemHoe::b, b(Blocks.cB.o())),
         Blocks.j,
         Pair.of(ItemHoe::b, b(Blocks.cB.o())),
         Blocks.k,
         Pair.of(ItemHoe::b, b(Blocks.j.o())),
         Blocks.rB,
         Pair.of((Predicate<ItemActionContext>)var0 -> true, a(Blocks.j.o(), Items.dq))
      )
   );

   protected ItemHoe(ToolMaterial var0, int var1, float var2, Item.Info var3) {
      super((float)var1, var2, var0, TagsBlock.bw, var3);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      World var1 = var0.q();
      BlockPosition var2 = var0.a();
      Pair<Predicate<ItemActionContext>, Consumer<ItemActionContext>> var3 = (Pair)a.get(var1.a_(var2).b());
      if (var3 == null) {
         return EnumInteractionResult.d;
      } else {
         Predicate<ItemActionContext> var4 = (Predicate)var3.getFirst();
         Consumer<ItemActionContext> var5 = (Consumer)var3.getSecond();
         if (var4.test(var0)) {
            EntityHuman var6 = var0.o();
            var1.a(var6, var2, SoundEffects.kC, SoundCategory.e, 1.0F, 1.0F);
            if (!var1.B) {
               var5.accept(var0);
               if (var6 != null) {
                  var0.n().a(1, var6, var1x -> var1x.d(var0.p()));
               }
            }

            return EnumInteractionResult.a(var1.B);
         } else {
            return EnumInteractionResult.d;
         }
      }
   }

   public static Consumer<ItemActionContext> b(IBlockData var0) {
      return var1 -> {
         var1.q().a(var1.a(), var0, 11);
         var1.q().a(GameEvent.c, var1.a(), GameEvent.a.a(var1.o(), var0));
      };
   }

   public static Consumer<ItemActionContext> a(IBlockData var0, IMaterial var1) {
      return var2 -> {
         var2.q().a(var2.a(), var0, 11);
         var2.q().a(GameEvent.c, var2.a(), GameEvent.a.a(var2.o(), var0));
         Block.a(var2.q(), var2.a(), var2.k(), new ItemStack(var1));
      };
   }

   public static boolean b(ItemActionContext var0) {
      return var0.k() != EnumDirection.a && var0.q().a_(var0.a().c()).h();
   }
}
