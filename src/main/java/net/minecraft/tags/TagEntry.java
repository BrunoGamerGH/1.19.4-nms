package net.minecraft.tags;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ExtraCodecs;

public class TagEntry {
   private static final Codec<TagEntry> b = RecordCodecBuilder.create(
      var0 -> var0.group(ExtraCodecs.n.fieldOf("id").forGetter(TagEntry::a), Codec.BOOL.optionalFieldOf("required", true).forGetter(var0x -> var0x.e))
            .apply(var0, TagEntry::new)
   );
   public static final Codec<TagEntry> a = Codec.either(ExtraCodecs.n, b)
      .xmap(var0 -> (TagEntry)var0.map(var0x -> new TagEntry(var0x, true), var0x -> var0x), var0 -> var0.e ? Either.left(var0.a()) : Either.right(var0));
   private final MinecraftKey c;
   private final boolean d;
   private final boolean e;

   private TagEntry(MinecraftKey var0, boolean var1, boolean var2) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
   }

   private TagEntry(ExtraCodecs.d var0, boolean var1) {
      this.c = var0.a();
      this.d = var0.b();
      this.e = var1;
   }

   private ExtraCodecs.d a() {
      return new ExtraCodecs.d(this.c, this.d);
   }

   public static TagEntry a(MinecraftKey var0) {
      return new TagEntry(var0, false, true);
   }

   public static TagEntry b(MinecraftKey var0) {
      return new TagEntry(var0, false, false);
   }

   public static TagEntry c(MinecraftKey var0) {
      return new TagEntry(var0, true, true);
   }

   public static TagEntry d(MinecraftKey var0) {
      return new TagEntry(var0, true, false);
   }

   public <T> boolean a(TagEntry.a<T> var0, Consumer<T> var1) {
      if (this.d) {
         Collection<T> var2 = var0.b(this.c);
         if (var2 == null) {
            return !this.e;
         }

         var2.forEach(var1);
      } else {
         T var2 = var0.a(this.c);
         if (var2 == null) {
            return !this.e;
         }

         var1.accept(var2);
      }

      return true;
   }

   public void a(Consumer<MinecraftKey> var0) {
      if (this.d && this.e) {
         var0.accept(this.c);
      }
   }

   public void b(Consumer<MinecraftKey> var0) {
      if (this.d && !this.e) {
         var0.accept(this.c);
      }
   }

   public boolean a(Predicate<MinecraftKey> var0, Predicate<MinecraftKey> var1) {
      return !this.e || (this.d ? var1 : var0).test(this.c);
   }

   @Override
   public String toString() {
      StringBuilder var0 = new StringBuilder();
      if (this.d) {
         var0.append('#');
      }

      var0.append(this.c);
      if (!this.e) {
         var0.append('?');
      }

      return var0.toString();
   }

   public interface a<T> {
      @Nullable
      T a(MinecraftKey var1);

      @Nullable
      Collection<T> b(MinecraftKey var1);
   }
}
