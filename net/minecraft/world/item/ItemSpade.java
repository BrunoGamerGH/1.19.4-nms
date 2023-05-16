package net.minecraft.world.item;

import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemSpade extends ItemTool {
   protected static final Map<Block, IBlockData> a = Maps.newHashMap(
      new Builder()
         .put(Blocks.i, Blocks.kB.o())
         .put(Blocks.j, Blocks.kB.o())
         .put(Blocks.l, Blocks.kB.o())
         .put(Blocks.k, Blocks.kB.o())
         .put(Blocks.fk, Blocks.kB.o())
         .put(Blocks.rB, Blocks.kB.o())
         .build()
   );

   public ItemSpade(ToolMaterial var0, float var1, float var2, Item.Info var3) {
      super(var1, var2, var0, TagsBlock.by, var3);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      World var1 = var0.q();
      BlockPosition var2 = var0.a();
      IBlockData var3 = var1.a_(var2);
      if (var0.k() == EnumDirection.a) {
         return EnumInteractionResult.d;
      } else {
         EntityHuman var4 = var0.o();
         IBlockData var5 = a.get(var3.b());
         IBlockData var6 = null;
         if (var5 != null && var1.a_(var2.c()).h()) {
            var1.a(var4, var2, SoundEffects.uH, SoundCategory.e, 1.0F, 1.0F);
            var6 = var5;
         } else if (var3.b() instanceof BlockCampfire && var3.c(BlockCampfire.b)) {
            if (!var1.k_()) {
               var1.a(null, 1009, var2, 0);
            }

            BlockCampfire.a(var0.o(), var1, var2, var3);
            var6 = var3.a(BlockCampfire.b, Boolean.valueOf(false));
         }

         if (var6 != null) {
            if (!var1.B) {
               var1.a(var2, var6, 11);
               var1.a(GameEvent.c, var2, GameEvent.a.a(var4, var6));
               if (var4 != null) {
                  var0.n().a(1, var4, var1x -> var1x.d(var0.p()));
               }
            }

            return EnumInteractionResult.a(var1.B);
         } else {
            return EnumInteractionResult.d;
         }
      }
   }
}
