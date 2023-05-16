package net.minecraft.world.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.level.World;

public class BundleItem extends Item {
   private static final String b = "Items";
   public static final int a = 64;
   private static final int c = 4;
   private static final int d = MathHelper.f(0.4F, 0.4F, 1.0F);

   public BundleItem(Item.Info var0) {
      super(var0);
   }

   public static float d(ItemStack var0) {
      return (float)o(var0) / 64.0F;
   }

   @Override
   public boolean a(ItemStack var0, Slot var1, ClickAction var2, EntityHuman var3) {
      if (var2 != ClickAction.b) {
         return false;
      } else {
         ItemStack var4 = var1.e();
         if (var4.b()) {
            this.a(var3);
            p(var0).ifPresent(var2x -> b(var0, var1.f(var2x)));
         } else if (var4.c().ag_()) {
            int var5 = (64 - o(var0)) / k(var4);
            int var6 = b(var0, var1.b(var4.K(), var5, var3));
            if (var6 > 0) {
               this.b(var3);
            }
         }

         return true;
      }
   }

   @Override
   public boolean a(ItemStack var0, ItemStack var1, Slot var2, ClickAction var3, EntityHuman var4, SlotAccess var5) {
      if (var3 == ClickAction.b && var2.b(var4)) {
         if (var1.b()) {
            p(var0).ifPresent(var2x -> {
               this.a(var4);
               var5.a(var2x);
            });
         } else {
            int var6 = b(var0, var1);
            if (var6 > 0) {
               this.b(var4);
               var1.h(var6);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      if (a(var3, var1)) {
         this.c(var1);
         var1.b(StatisticList.c.b(this));
         return InteractionResultWrapper.a(var3, var0.k_());
      } else {
         return InteractionResultWrapper.d(var3);
      }
   }

   @Override
   public boolean e(ItemStack var0) {
      return o(var0) > 0;
   }

   @Override
   public int f(ItemStack var0) {
      return Math.min(1 + 12 * o(var0) / 64, 13);
   }

   @Override
   public int g(ItemStack var0) {
      return d;
   }

   private static int b(ItemStack var0, ItemStack var1) {
      if (!var1.b() && var1.c().ag_()) {
         NBTTagCompound var2 = var0.v();
         if (!var2.e("Items")) {
            var2.a("Items", new NBTTagList());
         }

         int var3 = o(var0);
         int var4 = k(var1);
         int var5 = Math.min(var1.K(), (64 - var3) / var4);
         if (var5 == 0) {
            return 0;
         } else {
            NBTTagList var6 = var2.c("Items", 10);
            Optional<NBTTagCompound> var7 = a(var1, var6);
            if (var7.isPresent()) {
               NBTTagCompound var8 = var7.get();
               ItemStack var9 = ItemStack.a(var8);
               var9.g(var5);
               var9.b(var8);
               var6.remove(var8);
               var6.c(0, var8);
            } else {
               ItemStack var8 = var1.o();
               var8.f(var5);
               NBTTagCompound var9 = new NBTTagCompound();
               var8.b(var9);
               var6.c(0, var9);
            }

            return var5;
         }
      } else {
         return 0;
      }
   }

   private static Optional<NBTTagCompound> a(ItemStack var0, NBTTagList var1) {
      return var0.a(Items.qc)
         ? Optional.empty()
         : var1.stream()
            .filter(NBTTagCompound.class::isInstance)
            .map(NBTTagCompound.class::cast)
            .filter(var1x -> ItemStack.d(ItemStack.a(var1x), var0))
            .findFirst();
   }

   private static int k(ItemStack var0) {
      if (var0.a(Items.qc)) {
         return 4 + o(var0);
      } else {
         if ((var0.a(Items.vw) || var0.a(Items.vv)) && var0.t()) {
            NBTTagCompound var1 = ItemBlock.a(var0);
            if (var1 != null && !var1.c("Bees", 10).isEmpty()) {
               return 64;
            }
         }

         return 64 / var0.f();
      }
   }

   private static int o(ItemStack var0) {
      return q(var0).mapToInt(var0x -> k(var0x) * var0x.K()).sum();
   }

   private static Optional<ItemStack> p(ItemStack var0) {
      NBTTagCompound var1 = var0.v();
      if (!var1.e("Items")) {
         return Optional.empty();
      } else {
         NBTTagList var2 = var1.c("Items", 10);
         if (var2.isEmpty()) {
            return Optional.empty();
         } else {
            int var3 = 0;
            NBTTagCompound var4 = var2.a(0);
            ItemStack var5 = ItemStack.a(var4);
            var2.c(0);
            if (var2.isEmpty()) {
               var0.c("Items");
            }

            return Optional.of(var5);
         }
      }
   }

   private static boolean a(ItemStack var0, EntityHuman var1) {
      NBTTagCompound var2 = var0.v();
      if (!var2.e("Items")) {
         return false;
      } else {
         if (var1 instanceof EntityPlayer) {
            NBTTagList var3 = var2.c("Items", 10);

            for(int var4 = 0; var4 < var3.size(); ++var4) {
               NBTTagCompound var5 = var3.a(var4);
               ItemStack var6 = ItemStack.a(var5);
               var1.a(var6, true);
            }
         }

         var0.c("Items");
         return true;
      }
   }

   private static Stream<ItemStack> q(ItemStack var0) {
      NBTTagCompound var1 = var0.u();
      if (var1 == null) {
         return Stream.empty();
      } else {
         NBTTagList var2 = var1.c("Items", 10);
         return var2.stream().map(NBTTagCompound.class::cast).map(ItemStack::a);
      }
   }

   @Override
   public Optional<TooltipComponent> h(ItemStack var0) {
      NonNullList<ItemStack> var1 = NonNullList.a();
      q(var0).forEach(var1::add);
      return Optional.of(new BundleTooltip(var1, o(var0)));
   }

   @Override
   public void a(ItemStack var0, World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      var2.add(IChatBaseComponent.a("item.minecraft.bundle.fullness", o(var0), 64).a(EnumChatFormat.h));
   }

   @Override
   public void a(EntityItem var0) {
      ItemLiquidUtil.a(var0, q(var0.i()));
   }

   private void a(Entity var0) {
      var0.a(SoundEffects.cF, 0.8F, 0.8F + var0.Y().r_().i() * 0.4F);
   }

   private void b(Entity var0) {
      var0.a(SoundEffects.cE, 0.8F, 0.8F + var0.Y().r_().i() * 0.4F);
   }

   private void c(Entity var0) {
      var0.a(SoundEffects.cD, 0.8F, 0.8F + var0.Y().r_().i() * 0.4F);
   }
}
