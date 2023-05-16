package net.minecraft.world.item.alchemy;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class PotionUtil {
   public static final String a = "CustomPotionEffects";
   public static final String b = "CustomPotionColor";
   public static final String c = "Potion";
   private static final int d = 16253176;
   private static final IChatBaseComponent e = IChatBaseComponent.c("effect.none").a(EnumChatFormat.h);

   public static List<MobEffect> a(ItemStack var0) {
      return a(var0.u());
   }

   public static List<MobEffect> a(PotionRegistry var0, Collection<MobEffect> var1) {
      List<MobEffect> var2 = Lists.newArrayList();
      var2.addAll(var0.a());
      var2.addAll(var1);
      return var2;
   }

   public static List<MobEffect> a(@Nullable NBTTagCompound var0) {
      List<MobEffect> var1 = Lists.newArrayList();
      var1.addAll(c(var0).a());
      a(var0, var1);
      return var1;
   }

   public static List<MobEffect> b(ItemStack var0) {
      return b(var0.u());
   }

   public static List<MobEffect> b(@Nullable NBTTagCompound var0) {
      List<MobEffect> var1 = Lists.newArrayList();
      a(var0, var1);
      return var1;
   }

   public static void a(@Nullable NBTTagCompound var0, List<MobEffect> var1) {
      if (var0 != null && var0.b("CustomPotionEffects", 9)) {
         NBTTagList var2 = var0.c("CustomPotionEffects", 10);

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            NBTTagCompound var4 = var2.a(var3);
            MobEffect var5 = MobEffect.b(var4);
            if (var5 != null) {
               var1.add(var5);
            }
         }
      }
   }

   public static int c(ItemStack var0) {
      NBTTagCompound var1 = var0.u();
      if (var1 != null && var1.b("CustomPotionColor", 99)) {
         return var1.h("CustomPotionColor");
      } else {
         return d(var0) == Potions.b ? 16253176 : a(a(var0));
      }
   }

   public static int a(PotionRegistry var0) {
      return var0 == Potions.b ? 16253176 : a(var0.a());
   }

   public static int a(Collection<MobEffect> var0) {
      int var1 = 3694022;
      if (var0.isEmpty()) {
         return 3694022;
      } else {
         float var2 = 0.0F;
         float var3 = 0.0F;
         float var4 = 0.0F;
         int var5 = 0;

         for(MobEffect var7 : var0) {
            if (var7.g()) {
               int var8 = var7.c().g();
               int var9 = var7.e() + 1;
               var2 += (float)(var9 * (var8 >> 16 & 0xFF)) / 255.0F;
               var3 += (float)(var9 * (var8 >> 8 & 0xFF)) / 255.0F;
               var4 += (float)(var9 * (var8 >> 0 & 0xFF)) / 255.0F;
               var5 += var9;
            }
         }

         if (var5 == 0) {
            return 0;
         } else {
            var2 = var2 / (float)var5 * 255.0F;
            var3 = var3 / (float)var5 * 255.0F;
            var4 = var4 / (float)var5 * 255.0F;
            return (int)var2 << 16 | (int)var3 << 8 | (int)var4;
         }
      }
   }

   public static PotionRegistry d(ItemStack var0) {
      return c(var0.u());
   }

   public static PotionRegistry c(@Nullable NBTTagCompound var0) {
      return var0 == null ? Potions.b : PotionRegistry.a(var0.l("Potion"));
   }

   public static ItemStack a(ItemStack var0, PotionRegistry var1) {
      MinecraftKey var2 = BuiltInRegistries.j.b(var1);
      if (var1 == Potions.b) {
         var0.c("Potion");
      } else {
         var0.v().a("Potion", var2.toString());
      }

      return var0;
   }

   public static ItemStack a(ItemStack var0, Collection<MobEffect> var1) {
      if (var1.isEmpty()) {
         return var0;
      } else {
         NBTTagCompound var2 = var0.v();
         NBTTagList var3 = var2.c("CustomPotionEffects", 9);

         for(MobEffect var5 : var1) {
            var3.add(var5.a(new NBTTagCompound()));
         }

         var2.a("CustomPotionEffects", var3);
         return var0;
      }
   }

   public static void a(ItemStack var0, List<IChatBaseComponent> var1, float var2) {
      a(a(var0), var1, var2);
   }

   public static void a(List<MobEffect> var0, List<IChatBaseComponent> var1, float var2) {
      List<Pair<AttributeBase, AttributeModifier>> var3 = Lists.newArrayList();
      if (var0.isEmpty()) {
         var1.add(e);
      } else {
         for(MobEffect var5 : var0) {
            IChatMutableComponent var6 = IChatBaseComponent.c(var5.i());
            MobEffectList var7 = var5.c();
            Map<AttributeBase, AttributeModifier> var8 = var7.h();
            if (!var8.isEmpty()) {
               for(Entry<AttributeBase, AttributeModifier> var10 : var8.entrySet()) {
                  AttributeModifier var11 = var10.getValue();
                  AttributeModifier var12 = new AttributeModifier(var11.b(), var7.a(var5.e(), var11), var11.c());
                  var3.add(new Pair(var10.getKey(), var12));
               }
            }

            if (var5.e() > 0) {
               var6 = IChatBaseComponent.a("potion.withAmplifier", var6, IChatBaseComponent.c("potion.potency." + var5.e()));
            }

            if (!var5.a(20)) {
               var6 = IChatBaseComponent.a("potion.withDuration", var6, MobEffectUtil.a(var5, var2));
            }

            var1.add(var6.a(var7.f().a()));
         }
      }

      if (!var3.isEmpty()) {
         var1.add(CommonComponents.a);
         var1.add(IChatBaseComponent.c("potion.whenDrank").a(EnumChatFormat.f));

         for(Pair<AttributeBase, AttributeModifier> var5 : var3) {
            AttributeModifier var6 = (AttributeModifier)var5.getSecond();
            double var7 = var6.d();
            double var9;
            if (var6.c() != AttributeModifier.Operation.b && var6.c() != AttributeModifier.Operation.c) {
               var9 = var6.d();
            } else {
               var9 = var6.d() * 100.0;
            }

            if (var7 > 0.0) {
               var1.add(
                  IChatBaseComponent.a(
                        "attribute.modifier.plus." + var6.c().a(), ItemStack.c.format(var9), IChatBaseComponent.c(((AttributeBase)var5.getFirst()).c())
                     )
                     .a(EnumChatFormat.j)
               );
            } else if (var7 < 0.0) {
               var9 *= -1.0;
               var1.add(
                  IChatBaseComponent.a(
                        "attribute.modifier.take." + var6.c().a(), ItemStack.c.format(var9), IChatBaseComponent.c(((AttributeBase)var5.getFirst()).c())
                     )
                     .a(EnumChatFormat.m)
               );
            }
         }
      }
   }
}
