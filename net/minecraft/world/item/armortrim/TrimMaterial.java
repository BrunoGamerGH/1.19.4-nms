package net.minecraft.world.item.armortrim;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.EnumArmorMaterial;
import net.minecraft.world.item.Item;

public record TrimMaterial(
   String assetName, Holder<Item> ingredient, float itemModelIndex, Map<EnumArmorMaterial, String> overrideArmorMaterials, IChatBaseComponent description
) {
   private final String c;
   private final Holder<Item> d;
   private final float e;
   private final Map<EnumArmorMaterial, String> f;
   private final IChatBaseComponent g;
   public static final Codec<TrimMaterial> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.STRING.fieldOf("asset_name").forGetter(TrimMaterial::a),
               RegistryFixedCodec.a(Registries.C).fieldOf("ingredient").forGetter(TrimMaterial::b),
               Codec.FLOAT.fieldOf("item_model_index").forGetter(TrimMaterial::c),
               Codec.unboundedMap(EnumArmorMaterial.h, Codec.STRING).optionalFieldOf("override_armor_materials", Map.of()).forGetter(TrimMaterial::d),
               ExtraCodecs.b.fieldOf("description").forGetter(TrimMaterial::e)
            )
            .apply(var0, TrimMaterial::new)
   );
   public static final Codec<Holder<TrimMaterial>> b = RegistryFileCodec.a(Registries.aB, a);

   public TrimMaterial(String var0, Holder<Item> var1, float var2, Map<EnumArmorMaterial, String> var3, IChatBaseComponent var4) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
   }

   public static TrimMaterial a(String var0, Item var1, float var2, IChatBaseComponent var3, Map<EnumArmorMaterial, String> var4) {
      return new TrimMaterial(var0, BuiltInRegistries.i.d(var1), var2, var4, var3);
   }

   public String a() {
      return this.c;
   }

   public Holder<Item> b() {
      return this.d;
   }

   public float c() {
      return this.e;
   }

   public Map<EnumArmorMaterial, String> d() {
      return this.f;
   }

   public IChatBaseComponent e() {
      return this.g;
   }
}
