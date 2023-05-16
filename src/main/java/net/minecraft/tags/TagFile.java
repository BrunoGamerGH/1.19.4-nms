package net.minecraft.tags;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public record TagFile(List<TagEntry> entries, boolean replace) {
   private final List<TagEntry> b;
   private final boolean c;
   public static final Codec<TagFile> a = RecordCodecBuilder.create(
      var0 -> var0.group(TagEntry.a.listOf().fieldOf("values").forGetter(TagFile::a), Codec.BOOL.optionalFieldOf("replace", false).forGetter(TagFile::b))
            .apply(var0, TagFile::new)
   );

   public TagFile(List<TagEntry> var0, boolean var1) {
      this.b = var0;
      this.c = var1;
   }

   public List<TagEntry> a() {
      return this.b;
   }

   public boolean b() {
      return this.c;
   }
}
