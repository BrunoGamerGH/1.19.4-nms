package net.minecraft.world.item.armortrim;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import net.minecraft.EnumChatFormat;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryOps;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.EnumArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

public class ArmorTrim {
   public static final Codec<ArmorTrim> a = RecordCodecBuilder.create(
      var0 -> var0.group(TrimMaterial.b.fieldOf("material").forGetter(ArmorTrim::b), TrimPattern.b.fieldOf("pattern").forGetter(ArmorTrim::a))
            .apply(var0, ArmorTrim::new)
   );
   private static final Logger c = LogUtils.getLogger();
   public static final String b = "Trim";
   private static final IChatBaseComponent d = IChatBaseComponent.c(SystemUtils.a("item", new MinecraftKey("smithing_template.upgrade"))).a(EnumChatFormat.h);
   private final Holder<TrimMaterial> e;
   private final Holder<TrimPattern> f;
   private final Function<ArmorMaterial, MinecraftKey> g;
   private final Function<ArmorMaterial, MinecraftKey> h;

   public ArmorTrim(Holder<TrimMaterial> var0, Holder<TrimPattern> var1) {
      this.e = var0;
      this.f = var1;
      this.g = SystemUtils.b(var1x -> {
         MinecraftKey var2x = var1.a().a();
         String var3 = this.c(var1x);
         return var2x.a((UnaryOperator<String>)(var1xx -> "trims/models/armor/" + var1xx + "_leggings_" + var3));
      });
      this.h = SystemUtils.b(var1x -> {
         MinecraftKey var2x = var1.a().a();
         String var3 = this.c(var1x);
         return var2x.a((UnaryOperator<String>)(var1xx -> "trims/models/armor/" + var1xx + "_" + var3));
      });
   }

   private String c(ArmorMaterial var0) {
      Map<EnumArmorMaterial, String> var1 = this.e.a().d();
      return var0 instanceof EnumArmorMaterial && var1.containsKey(var0) ? var1.get(var0) : this.e.a().a();
   }

   public boolean a(Holder<TrimPattern> var0, Holder<TrimMaterial> var1) {
      return var0 == this.f && var1 == this.e;
   }

   public Holder<TrimPattern> a() {
      return this.f;
   }

   public Holder<TrimMaterial> b() {
      return this.e;
   }

   public MinecraftKey a(ArmorMaterial var0) {
      return this.g.apply(var0);
   }

   public MinecraftKey b(ArmorMaterial var0) {
      return this.h.apply(var0);
   }

   @Override
   public boolean equals(Object var0) {
      if (!(var0 instanceof ArmorTrim)) {
         return false;
      } else {
         ArmorTrim var1 = (ArmorTrim)var0;
         return var1.f == this.f && var1.e == this.e;
      }
   }

   public static boolean a(IRegistryCustom var0, ItemStack var1, ArmorTrim var2) {
      if (var1.a(TagsItem.aG)) {
         var1.v().a("Trim", (NBTBase)a.encodeStart(RegistryOps.a(DynamicOpsNBT.a, var0), var2).result().orElseThrow());
         return true;
      } else {
         return false;
      }
   }

   public static Optional<ArmorTrim> a(IRegistryCustom var0, ItemStack var1) {
      if (var1.a(TagsItem.aG) && var1.u() != null && var1.u().e("Trim")) {
         NBTTagCompound var2 = var1.b("Trim");
         ArmorTrim var3 = (ArmorTrim)a.parse(RegistryOps.a(DynamicOpsNBT.a, var0), var2).resultOrPartial(c::error).orElse(null);
         return Optional.ofNullable(var3);
      } else {
         return Optional.empty();
      }
   }

   public static void a(ItemStack var0, IRegistryCustom var1, List<IChatBaseComponent> var2) {
      Optional<ArmorTrim> var3 = a(var1, var0);
      if (var3.isPresent()) {
         ArmorTrim var4 = var3.get();
         var2.add(d);
         var2.add(CommonComponents.a().b(var4.a().a().a(var4.b())));
         var2.add(CommonComponents.a().b(var4.b().a().e()));
      }
   }
}
