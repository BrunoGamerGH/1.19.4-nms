package net.minecraft.commands;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.World;

public interface ICompletionProvider {
   Collection<String> p();

   default Collection<String> w() {
      return this.p();
   }

   default Collection<String> x() {
      return Collections.emptyList();
   }

   Collection<String> q();

   Stream<MinecraftKey> r();

   Stream<MinecraftKey> s();

   CompletableFuture<Suggestions> a(CommandContext<?> var1);

   default Collection<ICompletionProvider.b> y() {
      return Collections.singleton(ICompletionProvider.b.b);
   }

   default Collection<ICompletionProvider.b> z() {
      return Collections.singleton(ICompletionProvider.b.b);
   }

   Set<ResourceKey<World>> t();

   IRegistryCustom u();

   FeatureFlagSet v();

   default void a(IRegistry<?> var0, ICompletionProvider.a var1, SuggestionsBuilder var2) {
      if (var1.a()) {
         a(var0.j().map(TagKey::b), var2, "#");
      }

      if (var1.b()) {
         a(var0.e(), var2);
      }
   }

   CompletableFuture<Suggestions> a(ResourceKey<? extends IRegistry<?>> var1, ICompletionProvider.a var2, SuggestionsBuilder var3, CommandContext<?> var4);

   boolean c(int var1);

   static <T> void a(Iterable<T> var0, String var1, Function<T, MinecraftKey> var2, Consumer<T> var3) {
      boolean var4 = var1.indexOf(58) > -1;

      for(T var6 : var0) {
         MinecraftKey var7 = var2.apply(var6);
         if (var4) {
            String var8 = var7.toString();
            if (a(var1, var8)) {
               var3.accept(var6);
            }
         } else if (a(var1, var7.b()) || var7.b().equals("minecraft") && a(var1, var7.a())) {
            var3.accept(var6);
         }
      }
   }

   static <T> void a(Iterable<T> var0, String var1, String var2, Function<T, MinecraftKey> var3, Consumer<T> var4) {
      if (var1.isEmpty()) {
         var0.forEach(var4);
      } else {
         String var5 = Strings.commonPrefix(var1, var2);
         if (!var5.isEmpty()) {
            String var6 = var1.substring(var5.length());
            a(var0, var6, var3, var4);
         }
      }
   }

   static CompletableFuture<Suggestions> a(Iterable<MinecraftKey> var0, SuggestionsBuilder var1, String var2) {
      String var3 = var1.getRemaining().toLowerCase(Locale.ROOT);
      a(var0, var3, var2, var0x -> var0x, var2x -> var1.suggest(var2 + var2x));
      return var1.buildFuture();
   }

   static CompletableFuture<Suggestions> a(Stream<MinecraftKey> var0, SuggestionsBuilder var1, String var2) {
      return a(var0::iterator, var1, var2);
   }

   static CompletableFuture<Suggestions> a(Iterable<MinecraftKey> var0, SuggestionsBuilder var1) {
      String var2 = var1.getRemaining().toLowerCase(Locale.ROOT);
      a(var0, var2, var0x -> var0x, var1x -> var1.suggest(var1x.toString()));
      return var1.buildFuture();
   }

   static <T> CompletableFuture<Suggestions> a(Iterable<T> var0, SuggestionsBuilder var1, Function<T, MinecraftKey> var2, Function<T, Message> var3) {
      String var4 = var1.getRemaining().toLowerCase(Locale.ROOT);
      a(var0, var4, var2, var3x -> var1.suggest(var2.apply(var3x).toString(), (Message)var3.apply(var3x)));
      return var1.buildFuture();
   }

   static CompletableFuture<Suggestions> a(Stream<MinecraftKey> var0, SuggestionsBuilder var1) {
      return a(var0::iterator, var1);
   }

   static <T> CompletableFuture<Suggestions> a(Stream<T> var0, SuggestionsBuilder var1, Function<T, MinecraftKey> var2, Function<T, Message> var3) {
      return a(var0::iterator, var1, var2, var3);
   }

   static CompletableFuture<Suggestions> a(String var0, Collection<ICompletionProvider.b> var1, SuggestionsBuilder var2, Predicate<String> var3) {
      List<String> var4 = Lists.newArrayList();
      if (Strings.isNullOrEmpty(var0)) {
         for(ICompletionProvider.b var6 : var1) {
            String var7 = var6.c + " " + var6.d + " " + var6.e;
            if (var3.test(var7)) {
               var4.add(var6.c);
               var4.add(var6.c + " " + var6.d);
               var4.add(var7);
            }
         }
      } else {
         String[] var5 = var0.split(" ");
         if (var5.length == 1) {
            for(ICompletionProvider.b var7 : var1) {
               String var8 = var5[0] + " " + var7.d + " " + var7.e;
               if (var3.test(var8)) {
                  var4.add(var5[0] + " " + var7.d);
                  var4.add(var8);
               }
            }
         } else if (var5.length == 2) {
            for(ICompletionProvider.b var7 : var1) {
               String var8 = var5[0] + " " + var5[1] + " " + var7.e;
               if (var3.test(var8)) {
                  var4.add(var8);
               }
            }
         }
      }

      return b(var4, var2);
   }

   static CompletableFuture<Suggestions> b(String var0, Collection<ICompletionProvider.b> var1, SuggestionsBuilder var2, Predicate<String> var3) {
      List<String> var4 = Lists.newArrayList();
      if (Strings.isNullOrEmpty(var0)) {
         for(ICompletionProvider.b var6 : var1) {
            String var7 = var6.c + " " + var6.e;
            if (var3.test(var7)) {
               var4.add(var6.c);
               var4.add(var7);
            }
         }
      } else {
         String[] var5 = var0.split(" ");
         if (var5.length == 1) {
            for(ICompletionProvider.b var7 : var1) {
               String var8 = var5[0] + " " + var7.e;
               if (var3.test(var8)) {
                  var4.add(var8);
               }
            }
         }
      }

      return b(var4, var2);
   }

   static CompletableFuture<Suggestions> b(Iterable<String> var0, SuggestionsBuilder var1) {
      String var2 = var1.getRemaining().toLowerCase(Locale.ROOT);

      for(String var4 : var0) {
         if (a(var2, var4.toLowerCase(Locale.ROOT))) {
            var1.suggest(var4);
         }
      }

      return var1.buildFuture();
   }

   static CompletableFuture<Suggestions> b(Stream<String> var0, SuggestionsBuilder var1) {
      String var2 = var1.getRemaining().toLowerCase(Locale.ROOT);
      var0.filter(var1x -> a(var2, var1x.toLowerCase(Locale.ROOT))).forEach(var1::suggest);
      return var1.buildFuture();
   }

   static CompletableFuture<Suggestions> a(String[] var0, SuggestionsBuilder var1) {
      String var2 = var1.getRemaining().toLowerCase(Locale.ROOT);

      for(String var6 : var0) {
         if (a(var2, var6.toLowerCase(Locale.ROOT))) {
            var1.suggest(var6);
         }
      }

      return var1.buildFuture();
   }

   static <T> CompletableFuture<Suggestions> b(Iterable<T> var0, SuggestionsBuilder var1, Function<T, String> var2, Function<T, Message> var3) {
      String var4 = var1.getRemaining().toLowerCase(Locale.ROOT);

      for(T var6 : var0) {
         String var7 = var2.apply(var6);
         if (a(var4, var7.toLowerCase(Locale.ROOT))) {
            var1.suggest(var7, (Message)var3.apply(var6));
         }
      }

      return var1.buildFuture();
   }

   static boolean a(String var0, String var1) {
      for(int var2 = 0; !var1.startsWith(var0, var2); ++var2) {
         var2 = var1.indexOf(95, var2);
         if (var2 < 0) {
            return false;
         }
      }

      return true;
   }

   public static enum a {
      a,
      b,
      c;

      public boolean a() {
         return this == a || this == c;
      }

      public boolean b() {
         return this == b || this == c;
      }
   }

   public static class b {
      public static final ICompletionProvider.b a = new ICompletionProvider.b("^", "^", "^");
      public static final ICompletionProvider.b b = new ICompletionProvider.b("~", "~", "~");
      public final String c;
      public final String d;
      public final String e;

      public b(String var0, String var1, String var2) {
         this.c = var0;
         this.d = var1;
         this.e = var2;
      }
   }
}
