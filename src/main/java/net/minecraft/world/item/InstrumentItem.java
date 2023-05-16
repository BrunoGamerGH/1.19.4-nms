package net.minecraft.world.item;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;

public class InstrumentItem extends Item {
   private static final String a = "instrument";
   private final TagKey<Instrument> b;

   public InstrumentItem(Item.Info var0, TagKey<Instrument> var1) {
      super(var0);
      this.b = var1;
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      super.a(var0, var1, var2, var3);
      Optional<ResourceKey<Instrument>> var4 = this.d(var0).flatMap(Holder::e);
      if (var4.isPresent()) {
         IChatMutableComponent var5 = IChatBaseComponent.c(SystemUtils.a("instrument", var4.get().a()));
         var2.add(var5.a(EnumChatFormat.h));
      }
   }

   public static ItemStack a(Item var0, Holder<Instrument> var1) {
      ItemStack var2 = new ItemStack(var0);
      a(var2, var1);
      return var2;
   }

   public static void a(ItemStack var0, TagKey<Instrument> var1, RandomSource var2) {
      Optional<Holder<Instrument>> var3 = BuiltInRegistries.al.b(var1).flatMap(var1x -> var1x.a(var2));
      var3.ifPresent(var1x -> a(var0, var1x));
   }

   private static void a(ItemStack var0, Holder<Instrument> var1) {
      NBTTagCompound var2 = var0.v();
      var2.a("instrument", var1.e().orElseThrow(() -> new IllegalStateException("Invalid instrument")).a().toString());
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      Optional<? extends Holder<Instrument>> var4 = this.d(var3);
      if (var4.isPresent()) {
         Instrument var5 = var4.get().a();
         var1.c(var2);
         a(var0, var1, var5);
         var1.ge().a(this, var5.b());
         var1.b(StatisticList.c.b(this));
         return InteractionResultWrapper.b(var3);
      } else {
         return InteractionResultWrapper.d(var3);
      }
   }

   @Override
   public int b(ItemStack var0) {
      Optional<? extends Holder<Instrument>> var1 = this.d(var0);
      return var1.<Integer>map(var0x -> var0x.a().b()).orElse(0);
   }

   private Optional<? extends Holder<Instrument>> d(ItemStack var0) {
      NBTTagCompound var1 = var0.u();
      if (var1 != null && var1.b("instrument", 8)) {
         MinecraftKey var2 = MinecraftKey.a(var1.l("instrument"));
         if (var2 != null) {
            return BuiltInRegistries.al.b(ResourceKey.a(Registries.A, var2));
         }
      }

      Iterator<Holder<Instrument>> var2 = BuiltInRegistries.al.c(this.b).iterator();
      return var2.hasNext() ? Optional.of(var2.next()) : Optional.empty();
   }

   @Override
   public EnumAnimation c(ItemStack var0) {
      return EnumAnimation.i;
   }

   private static void a(World var0, EntityHuman var1, Instrument var2) {
      SoundEffect var3 = var2.a().a();
      float var4 = var2.c() / 16.0F;
      var0.a(var1, var1, var3, SoundCategory.c, var4, 1.0F);
      var0.a(GameEvent.D, var1.de(), GameEvent.a.a(var1));
   }
}
