package net.minecraft.world.item;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Optional;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockRotatable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemAxe extends ItemTool {
   protected static final Map<Block, Block> a = new Builder()
      .put(Blocks.an, Blocks.av)
      .put(Blocks.T, Blocks.ak)
      .put(Blocks.at, Blocks.aB)
      .put(Blocks.Z, Blocks.aj)
      .put(Blocks.ar, Blocks.az)
      .put(Blocks.X, Blocks.ah)
      .put(Blocks.as, Blocks.aA)
      .put(Blocks.Y, Blocks.ai)
      .put(Blocks.ap, Blocks.ax)
      .put(Blocks.V, Blocks.af)
      .put(Blocks.aq, Blocks.ay)
      .put(Blocks.W, Blocks.ag)
      .put(Blocks.ao, Blocks.aw)
      .put(Blocks.U, Blocks.ae)
      .put(Blocks.of, Blocks.og)
      .put(Blocks.oh, Blocks.oi)
      .put(Blocks.oo, Blocks.op)
      .put(Blocks.oq, Blocks.or)
      .put(Blocks.au, Blocks.aC)
      .put(Blocks.aa, Blocks.al)
      .put(Blocks.ad, Blocks.am)
      .build();

   protected ItemAxe(ToolMaterial var0, float var1, float var2, Item.Info var3) {
      super(var1, var2, var0, TagsBlock.bv, var3);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      World var1 = var0.q();
      BlockPosition var2 = var0.a();
      EntityHuman var3 = var0.o();
      IBlockData var4 = var1.a_(var2);
      Optional<IBlockData> var5 = this.b(var4);
      Optional<IBlockData> var6 = WeatheringCopper.b(var4);
      Optional<IBlockData> var7 = Optional.ofNullable((Block)((BiMap)HoneycombItem.b.get()).get(var4.b())).map(var1x -> ((Block)var1x).l(var4));
      ItemStack var8 = var0.n();
      Optional<IBlockData> var9 = Optional.empty();
      if (var5.isPresent()) {
         var1.a(var3, var2, SoundEffects.ar, SoundCategory.e, 1.0F, 1.0F);
         var9 = var5;
      } else if (var6.isPresent()) {
         var1.a(var3, var2, SoundEffects.as, SoundCategory.e, 1.0F, 1.0F);
         var1.a(var3, 3005, var2, 0);
         var9 = var6;
      } else if (var7.isPresent()) {
         var1.a(var3, var2, SoundEffects.at, SoundCategory.e, 1.0F, 1.0F);
         var1.a(var3, 3004, var2, 0);
         var9 = var7;
      }

      if (var9.isPresent()) {
         if (var3 instanceof EntityPlayer) {
            CriterionTriggers.M.a((EntityPlayer)var3, var2, var8);
         }

         var1.a(var2, var9.get(), 11);
         var1.a(GameEvent.c, var2, GameEvent.a.a(var3, var9.get()));
         if (var3 != null) {
            var8.a(1, var3, var1x -> var1x.d(var0.p()));
         }

         return EnumInteractionResult.a(var1.B);
      } else {
         return EnumInteractionResult.d;
      }
   }

   private Optional<IBlockData> b(IBlockData var0) {
      return Optional.ofNullable(a.get(var0.b())).map(var1x -> var1x.o().a(BlockRotatable.g, var0.c(BlockRotatable.g)));
   }
}
