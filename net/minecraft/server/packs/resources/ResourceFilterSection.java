package net.minecraft.server.packs.resources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.util.ResourceLocationPattern;

public class ResourceFilterSection {
   private static final Codec<ResourceFilterSection> b = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.list(ResourceLocationPattern.a).fieldOf("block").forGetter(var0x -> var0x.c)).apply(var0, ResourceFilterSection::new)
   );
   public static final MetadataSectionType<ResourceFilterSection> a = MetadataSectionType.a("filter", b);
   private final List<ResourceLocationPattern> c;

   public ResourceFilterSection(List<ResourceLocationPattern> var0) {
      this.c = List.copyOf(var0);
   }

   public boolean a(String var0) {
      return this.c.stream().anyMatch(var1x -> var1x.a().test(var0));
   }

   public boolean b(String var0) {
      return this.c.stream().anyMatch(var1x -> var1x.b().test(var0));
   }
}
