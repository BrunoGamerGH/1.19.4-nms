package net.minecraft.network.chat.contents;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.locale.LocaleLanguage;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatFormatted;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.world.entity.Entity;

public class TranslatableContents implements ComponentContents {
   public static final Object[] b = new Object[0];
   private static final IChatFormatted c = IChatFormatted.e("%");
   private static final IChatFormatted d = IChatFormatted.e("null");
   private final String e;
   @Nullable
   private final String f;
   private final Object[] g;
   @Nullable
   private LocaleLanguage h;
   private List<IChatFormatted> i = ImmutableList.of();
   private static final Pattern j = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public TranslatableContents(String var0, @Nullable String var1, Object[] var2) {
      this.e = var0;
      this.f = var1;
      this.g = var2;
   }

   private void d() {
      LocaleLanguage var0 = LocaleLanguage.a();
      if (var0 != this.h) {
         this.h = var0;
         String var1 = this.f != null ? var0.a(this.e, this.f) : var0.a(this.e);

         try {
            Builder<IChatFormatted> var2 = ImmutableList.builder();
            this.a(var1, var2::add);
            this.i = var2.build();
         } catch (TranslatableFormatException var4) {
            this.i = ImmutableList.of(IChatFormatted.e(var1));
         }
      }
   }

   private void a(String var0, Consumer<IChatFormatted> var1) {
      Matcher var2 = j.matcher(var0);

      try {
         int var3 = 0;

         int var4;
         int var6;
         for(var4 = 0; var2.find(var4); var4 = var6) {
            int var5 = var2.start();
            var6 = var2.end();
            if (var5 > var4) {
               String var7 = var0.substring(var4, var5);
               if (var7.indexOf(37) != -1) {
                  throw new IllegalArgumentException();
               }

               var1.accept(IChatFormatted.e(var7));
            }

            String var7 = var2.group(2);
            String var8 = var0.substring(var5, var6);
            if ("%".equals(var7) && "%%".equals(var8)) {
               var1.accept(c);
            } else {
               if (!"s".equals(var7)) {
                  throw new TranslatableFormatException(this, "Unsupported format: '" + var8 + "'");
               }

               String var9 = var2.group(1);
               int var10 = var9 != null ? Integer.parseInt(var9) - 1 : var3++;
               var1.accept(this.a(var10));
            }
         }

         if (var4 < var0.length()) {
            String var5 = var0.substring(var4);
            if (var5.indexOf(37) != -1) {
               throw new IllegalArgumentException();
            }

            var1.accept(IChatFormatted.e(var5));
         }
      } catch (IllegalArgumentException var12) {
         throw new TranslatableFormatException(this, var12);
      }
   }

   private IChatFormatted a(int var0) {
      if (var0 >= 0 && var0 < this.g.length) {
         Object var1 = this.g[var0];
         if (var1 instanceof IChatBaseComponent) {
            return (IChatBaseComponent)var1;
         } else {
            return var1 == null ? d : IChatFormatted.e(var1.toString());
         }
      } else {
         throw new TranslatableFormatException(this, var0);
      }
   }

   @Override
   public <T> Optional<T> a(IChatFormatted.b<T> var0, ChatModifier var1) {
      this.d();

      for(IChatFormatted var3 : this.i) {
         Optional<T> var4 = var3.a(var0, var1);
         if (var4.isPresent()) {
            return var4;
         }
      }

      return Optional.empty();
   }

   @Override
   public <T> Optional<T> a(IChatFormatted.a<T> var0) {
      this.d();

      for(IChatFormatted var2 : this.i) {
         Optional<T> var3 = var2.a(var0);
         if (var3.isPresent()) {
            return var3;
         }
      }

      return Optional.empty();
   }

   @Override
   public IChatMutableComponent a(@Nullable CommandListenerWrapper var0, @Nullable Entity var1, int var2) throws CommandSyntaxException {
      Object[] var3 = new Object[this.g.length];

      for(int var4 = 0; var4 < var3.length; ++var4) {
         Object var5 = this.g[var4];
         if (var5 instanceof IChatBaseComponent) {
            var3[var4] = ChatComponentUtils.a(var0, (IChatBaseComponent)var5, var1, var2);
         } else {
            var3[var4] = var5;
         }
      }

      return IChatMutableComponent.a(new TranslatableContents(this.e, this.f, var3));
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof TranslatableContents var1 && Objects.equals(this.e, var1.e) && Objects.equals(this.f, var1.f) && Arrays.equals(this.g, var1.g)) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      int var0 = Objects.hashCode(this.e);
      var0 = 31 * var0 + Objects.hashCode(this.f);
      return 31 * var0 + Arrays.hashCode(this.g);
   }

   @Override
   public String toString() {
      return "translation{key='" + this.e + "'" + (this.f != null ? ", fallback='" + this.f + "'" : "") + ", args=" + Arrays.toString(this.g) + "}";
   }

   public String a() {
      return this.e;
   }

   @Nullable
   public String b() {
      return this.f;
   }

   public Object[] c() {
      return this.g;
   }
}
