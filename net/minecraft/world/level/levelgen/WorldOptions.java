package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalLong;
import net.minecraft.util.RandomSource;
import org.apache.commons.lang3.StringUtils;

public class WorldOptions {
   public static final MapCodec<WorldOptions> a = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               Codec.LONG.fieldOf("seed").stable().forGetter(WorldOptions::b),
               Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter(WorldOptions::c),
               Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter(WorldOptions::d),
               Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(var0x -> var0x.f)
            )
            .apply(var0, var0.stable(WorldOptions::new))
   );
   public static final WorldOptions b = new WorldOptions((long)"North Carolina".hashCode(), true, true);
   private final long c;
   private final boolean d;
   private final boolean e;
   private final Optional<String> f;

   public WorldOptions(long var0, boolean var2, boolean var3) {
      this(var0, var2, var3, Optional.empty());
   }

   public static WorldOptions a() {
      return new WorldOptions(f(), true, false);
   }

   private WorldOptions(long var0, boolean var2, boolean var3, Optional<String> var4) {
      this.c = var0;
      this.d = var2;
      this.e = var3;
      this.f = var4;
   }

   public long b() {
      return this.c;
   }

   public boolean c() {
      return this.d;
   }

   public boolean d() {
      return this.e;
   }

   public boolean e() {
      return this.f.isPresent();
   }

   public WorldOptions a(boolean var0) {
      return new WorldOptions(this.c, this.d, var0, this.f);
   }

   public WorldOptions b(boolean var0) {
      return new WorldOptions(this.c, var0, this.e, this.f);
   }

   public WorldOptions a(OptionalLong var0) {
      return new WorldOptions(var0.orElse(f()), this.d, this.e, this.f);
   }

   public static OptionalLong a(String var0) {
      var0 = var0.trim();
      if (StringUtils.isEmpty(var0)) {
         return OptionalLong.empty();
      } else {
         try {
            return OptionalLong.of(Long.parseLong(var0));
         } catch (NumberFormatException var2) {
            return OptionalLong.of((long)var0.hashCode());
         }
      }
   }

   public static long f() {
      return RandomSource.a().g();
   }
}
