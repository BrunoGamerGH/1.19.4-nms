package net.minecraft.world.item;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public class ItemFireworks extends Item {
   public static final byte[] a = new byte[]{1, 2, 3};
   public static final String b = "Fireworks";
   public static final String c = "Explosion";
   public static final String d = "Explosions";
   public static final String e = "Flight";
   public static final String f = "Type";
   public static final String g = "Trail";
   public static final String h = "Flicker";
   public static final String i = "Colors";
   public static final String j = "FadeColors";
   public static final double k = 0.15;

   public ItemFireworks(Item.Info var0) {
      super(var0);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      World var1 = var0.q();
      if (!var1.B) {
         ItemStack var2 = var0.n();
         Vec3D var3 = var0.l();
         EnumDirection var4 = var0.k();
         EntityFireworks var5 = new EntityFireworks(
            var1, var0.o(), var3.c + (double)var4.j() * 0.15, var3.d + (double)var4.k() * 0.15, var3.e + (double)var4.l() * 0.15, var2
         );
         var1.b(var5);
         var2.h(1);
      }

      return EnumInteractionResult.a(var1.B);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      if (var1.fn()) {
         ItemStack var3 = var1.b(var2);
         if (!var0.B) {
            EntityFireworks var4 = new EntityFireworks(var0, var3, var1);
            var0.b(var4);
            if (!var1.fK().d) {
               var3.h(1);
            }

            var1.b(StatisticList.c.b(this));
         }

         return InteractionResultWrapper.a(var1.b(var2), var0.k_());
      } else {
         return InteractionResultWrapper.c(var1.b(var2));
      }
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      NBTTagCompound var4 = var0.b("Fireworks");
      if (var4 != null) {
         if (var4.b("Flight", 99)) {
            var2.add(
               IChatBaseComponent.c("item.minecraft.firework_rocket.flight").b(CommonComponents.q).f(String.valueOf(var4.f("Flight"))).a(EnumChatFormat.h)
            );
         }

         NBTTagList var5 = var4.c("Explosions", 10);
         if (!var5.isEmpty()) {
            for(int var6 = 0; var6 < var5.size(); ++var6) {
               NBTTagCompound var7 = var5.a(var6);
               List<IChatBaseComponent> var8 = Lists.newArrayList();
               ItemFireworksCharge.a(var7, var8);
               if (!var8.isEmpty()) {
                  for(int var9 = 1; var9 < var8.size(); ++var9) {
                     var8.set(var9, IChatBaseComponent.b("  ").b(var8.get(var9)).a(EnumChatFormat.h));
                  }

                  var2.addAll(var8);
               }
            }
         }
      }
   }

   public static void a(ItemStack var0, byte var1) {
      var0.a("Fireworks").a("Flight", var1);
   }

   @Override
   public ItemStack ad_() {
      ItemStack var0 = new ItemStack(this);
      a(var0, (byte)1);
      return var0;
   }

   public static enum EffectType {
      a(0, "small_ball"),
      b(1, "large_ball"),
      c(2, "star"),
      d(3, "creeper"),
      e(4, "burst");

      private static final IntFunction<ItemFireworks.EffectType> f = ByIdMap.a(ItemFireworks.EffectType::a, values(), ByIdMap.a.a);
      private final int g;
      private final String h;

      private EffectType(int var2, String var3) {
         this.g = var2;
         this.h = var3;
      }

      public int a() {
         return this.g;
      }

      public String b() {
         return this.h;
      }

      public static ItemFireworks.EffectType a(int var0) {
         return f.apply(var0);
      }
   }
}
