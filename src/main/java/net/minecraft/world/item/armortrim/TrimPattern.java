package net.minecraft.world.item.armortrim;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;

public record TrimPattern(MinecraftKey assetId, Holder<Item> templateItem, IChatBaseComponent description) {
   private final MinecraftKey c;
   private final Holder<Item> d;
   private final IChatBaseComponent e;
   public static final Codec<TrimPattern> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               MinecraftKey.a.fieldOf("asset_id").forGetter(TrimPattern::a),
               RegistryFixedCodec.a(Registries.C).fieldOf("template_item").forGetter(TrimPattern::b),
               ExtraCodecs.b.fieldOf("description").forGetter(TrimPattern::c)
            )
            .apply(var0, TrimPattern::new)
   );
   public static final Codec<Holder<TrimPattern>> b = RegistryFileCodec.a(Registries.aC, a);

   public TrimPattern(MinecraftKey var0, Holder<Item> var1, IChatBaseComponent var2) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
   }

   public IChatBaseComponent a(Holder<TrimMaterial> var0) {
      return this.e.e().c(var0.a().e().a());
   }

   public MinecraftKey a() {
      return this.c;
   }

   public Holder<Item> b() {
      return this.d;
   }

   public IChatBaseComponent c() {
      return this.e;
   }
}
