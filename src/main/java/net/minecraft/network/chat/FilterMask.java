package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.INamable;
import org.apache.commons.lang3.StringUtils;

public class FilterMask {
   public static final Codec<FilterMask> a = INamable.a(FilterMask.a::values).dispatch(FilterMask::c, FilterMask.a::a);
   public static final FilterMask b = new FilterMask(new BitSet(0), FilterMask.a.b);
   public static final FilterMask c = new FilterMask(new BitSet(0), FilterMask.a.a);
   public static final ChatModifier d = ChatModifier.a
      .a(EnumChatFormat.i)
      .a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.c("chat.filtered")));
   static final Codec<FilterMask> e = Codec.unit(c);
   static final Codec<FilterMask> f = Codec.unit(b);
   static final Codec<FilterMask> g = ExtraCodecs.q.xmap(FilterMask::new, FilterMask::d);
   private static final char h = '#';
   private final BitSet i;
   private final FilterMask.a j;

   private FilterMask(BitSet var0, FilterMask.a var1) {
      this.i = var0;
      this.j = var1;
   }

   private FilterMask(BitSet var0) {
      this.i = var0;
      this.j = FilterMask.a.c;
   }

   public FilterMask(int var0) {
      this(new BitSet(var0), FilterMask.a.c);
   }

   private FilterMask.a c() {
      return this.j;
   }

   private BitSet d() {
      return this.i;
   }

   public static FilterMask a(PacketDataSerializer var0) {
      FilterMask.a var1 = var0.b(FilterMask.a.class);

      return switch(var1) {
         case a -> c;
         case b -> b;
         case c -> new FilterMask(var0.y(), FilterMask.a.c);
      };
   }

   public static void a(PacketDataSerializer var0, FilterMask var1) {
      var0.a(var1.j);
      if (var1.j == FilterMask.a.c) {
         var0.a(var1.i);
      }
   }

   public void a(int var0) {
      this.i.set(var0);
   }

   @Nullable
   public String a(String var0) {
      return switch(this.j) {
         case a -> var0;
         case b -> null;
         case c -> {
            char[] var1 = var0.toCharArray();

            for(int var2 = 0; var2 < var1.length && var2 < this.i.length(); ++var2) {
               if (this.i.get(var2)) {
                  var1[var2] = '#';
               }
            }

            yield new String(var1);
         }
      };
   }

   @Nullable
   public IChatBaseComponent b(String var0) {
      return switch(this.j) {
         case a -> IChatBaseComponent.b(var0);
         case b -> null;
         case c -> {
            IChatMutableComponent var1 = IChatBaseComponent.h();
            int var2 = 0;
            boolean var3 = this.i.get(0);

            while(true) {
               int var4 = var3 ? this.i.nextClearBit(var2) : this.i.nextSetBit(var2);
               var4 = var4 < 0 ? var0.length() : var4;
               if (var4 == var2) {
                  yield var1;
               }

               if (var3) {
                  var1.b(IChatBaseComponent.b(StringUtils.repeat('#', var4 - var2)).c(d));
               } else {
                  var1.f(var0.substring(var2, var4));
               }

               var3 = !var3;
               var2 = var4;
            }
         }
      };
   }

   public boolean a() {
      return this.j == FilterMask.a.a;
   }

   public boolean b() {
      return this.j == FilterMask.a.b;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         FilterMask var1 = (FilterMask)var0;
         return this.i.equals(var1.i) && this.j == var1.j;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int var0 = this.i.hashCode();
      return 31 * var0 + this.j.hashCode();
   }

   static enum a implements INamable {
      a("pass_through", () -> FilterMask.e),
      b("fully_filtered", () -> FilterMask.f),
      c("partially_filtered", () -> FilterMask.g);

      private final String d;
      private final Supplier<Codec<FilterMask>> e;

      private a(String var2, Supplier var3) {
         this.d = var2;
         this.e = var3;
      }

      @Override
      public String c() {
         return this.d;
      }

      private Codec<FilterMask> a() {
         return (Codec<FilterMask>)this.e.get();
      }
   }
}
