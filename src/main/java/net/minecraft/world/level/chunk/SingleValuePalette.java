package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.PacketDataSerializer;
import org.apache.commons.lang3.Validate;

public class SingleValuePalette<T> implements DataPalette<T> {
   private final Registry<T> a;
   @Nullable
   private T b;
   private final DataPaletteExpandable<T> c;

   public SingleValuePalette(Registry<T> var0, DataPaletteExpandable<T> var1, List<T> var2) {
      this.a = var0;
      this.c = var1;
      if (var2.size() > 0) {
         Validate.isTrue(var2.size() <= 1, "Can't initialize SingleValuePalette with %d values.", (long)var2.size());
         this.b = var2.get(0);
      }
   }

   public static <A> DataPalette<A> a(int var0, Registry<A> var1, DataPaletteExpandable<A> var2, List<A> var3) {
      return new SingleValuePalette<>(var1, var2, var3);
   }

   @Override
   public int a(T var0) {
      if (this.b != null && this.b != var0) {
         return this.c.onResize(1, var0);
      } else {
         this.b = var0;
         return 0;
      }
   }

   @Override
   public boolean a(Predicate<T> var0) {
      if (this.b == null) {
         throw new IllegalStateException("Use of an uninitialized palette");
      } else {
         return var0.test(this.b);
      }
   }

   @Override
   public T a(int var0) {
      if (this.b != null && var0 == 0) {
         return this.b;
      } else {
         throw new IllegalStateException("Missing Palette entry for id " + var0 + ".");
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      this.b = this.a.b(var0.m());
   }

   @Override
   public void b(PacketDataSerializer var0) {
      if (this.b == null) {
         throw new IllegalStateException("Use of an uninitialized palette");
      } else {
         var0.d(this.a.a(this.b));
      }
   }

   @Override
   public int a() {
      if (this.b == null) {
         throw new IllegalStateException("Use of an uninitialized palette");
      } else {
         return PacketDataSerializer.a(this.a.a(this.b));
      }
   }

   @Override
   public int b() {
      return 1;
   }

   @Override
   public DataPalette<T> c() {
      if (this.b == null) {
         throw new IllegalStateException("Use of an uninitialized palette");
      } else {
         return this;
      }
   }
}
