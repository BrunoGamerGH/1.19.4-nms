package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockJukeBox;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemRecord extends Item {
   private static final Map<SoundEffect, ItemRecord> a = Maps.newHashMap();
   private final int b;
   private final SoundEffect c;
   private final int d;

   protected ItemRecord(int i, SoundEffect soundeffect, Item.Info item_info, int j) {
      super(item_info);
      this.b = i;
      this.c = soundeffect;
      this.d = j * 20;
      a.put(this.c, this);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      World world = itemactioncontext.q();
      BlockPosition blockposition = itemactioncontext.a();
      IBlockData iblockdata = world.a_(blockposition);
      if (iblockdata.a(Blocks.dS) && !iblockdata.c(BlockJukeBox.a)) {
         ItemStack itemstack = itemactioncontext.n();
         return !world.B ? EnumInteractionResult.a : EnumInteractionResult.a(world.B);
      } else {
         return EnumInteractionResult.d;
      }
   }

   public int h() {
      return this.b;
   }

   @Override
   public void a(ItemStack itemstack, @Nullable World world, List<IChatBaseComponent> list, TooltipFlag tooltipflag) {
      list.add(this.i().a(EnumChatFormat.h));
   }

   public IChatMutableComponent i() {
      return IChatBaseComponent.c(this.a() + ".desc");
   }

   @Nullable
   public static ItemRecord a(SoundEffect soundeffect) {
      return a.get(soundeffect);
   }

   public SoundEffect x() {
      return this.c;
   }

   public int y() {
      return this.d;
   }
}
