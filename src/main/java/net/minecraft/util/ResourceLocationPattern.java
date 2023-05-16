package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.minecraft.resources.MinecraftKey;

public class ResourceLocationPattern {
   public static final Codec<ResourceLocationPattern> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ExtraCodecs.k.optionalFieldOf("namespace").forGetter(var0x -> var0x.b), ExtraCodecs.k.optionalFieldOf("path").forGetter(var0x -> var0x.d)
            )
            .apply(var0, ResourceLocationPattern::new)
   );
   private final Optional<Pattern> b;
   private final Predicate<String> c;
   private final Optional<Pattern> d;
   private final Predicate<String> e;
   private final Predicate<MinecraftKey> f;

   private ResourceLocationPattern(Optional<Pattern> var0, Optional<Pattern> var1) {
      this.b = var0;
      this.c = var0.map(Pattern::asPredicate).orElse(var0x -> true);
      this.d = var1;
      this.e = var1.map(Pattern::asPredicate).orElse(var0x -> true);
      this.f = var0x -> this.c.test(var0x.b()) && this.e.test(var0x.a());
   }

   public Predicate<String> a() {
      return this.c;
   }

   public Predicate<String> b() {
      return this.e;
   }

   public Predicate<MinecraftKey> c() {
      return this.f;
   }
}
