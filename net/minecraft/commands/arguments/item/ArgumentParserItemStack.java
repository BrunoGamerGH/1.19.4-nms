package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ArgumentParserItemStack {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.item.tag.disallowed"));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.item.id.invalid", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("arguments.item.tag.unknown", var0));
   private static final char d = '{';
   private static final char e = '#';
   private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> f = SuggestionsBuilder::buildFuture;
   private final HolderLookup<Item> g;
   private final StringReader h;
   private final boolean i;
   private Either<Holder<Item>, HolderSet<Item>> j;
   @Nullable
   private NBTTagCompound k;
   private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> l = f;

   private ArgumentParserItemStack(HolderLookup<Item> var0, StringReader var1, boolean var2) {
      this.g = var0;
      this.h = var1;
      this.i = var2;
   }

   public static ArgumentParserItemStack.a a(HolderLookup<Item> var0, StringReader var1) throws CommandSyntaxException {
      int var2 = var1.getCursor();

      try {
         ArgumentParserItemStack var3 = new ArgumentParserItemStack(var0, var1, false);
         var3.d();
         Holder<Item> var4 = (Holder)var3.j.left().orElseThrow(() -> new IllegalStateException("Parser returned unexpected tag name"));
         return new ArgumentParserItemStack.a(var4, var3.k);
      } catch (CommandSyntaxException var5) {
         var1.setCursor(var2);
         throw var5;
      }
   }

   public static Either<ArgumentParserItemStack.a, ArgumentParserItemStack.b> b(HolderLookup<Item> var0, StringReader var1) throws CommandSyntaxException {
      int var2 = var1.getCursor();

      try {
         ArgumentParserItemStack var3 = new ArgumentParserItemStack(var0, var1, true);
         var3.d();
         return var3.j.mapBoth(var1x -> new ArgumentParserItemStack.a(var1x, var3.k), var1x -> new ArgumentParserItemStack.b(var1x, var3.k));
      } catch (CommandSyntaxException var4) {
         var1.setCursor(var2);
         throw var4;
      }
   }

   public static CompletableFuture<Suggestions> a(HolderLookup<Item> var0, SuggestionsBuilder var1, boolean var2) {
      StringReader var3 = new StringReader(var1.getInput());
      var3.setCursor(var1.getStart());
      ArgumentParserItemStack var4 = new ArgumentParserItemStack(var0, var3, var2);

      try {
         var4.d();
      } catch (CommandSyntaxException var6) {
      }

      return var4.l.apply(var1.createOffset(var3.getCursor()));
   }

   private void a() throws CommandSyntaxException {
      int var0 = this.h.getCursor();
      MinecraftKey var1 = MinecraftKey.a(this.h);
      Optional<? extends Holder<Item>> var2 = this.g.a(ResourceKey.a(Registries.C, var1));
      this.j = Either.left(var2.orElseThrow(() -> {
         this.h.setCursor(var0);
         return b.createWithContext(this.h, var1);
      }));
   }

   private void b() throws CommandSyntaxException {
      if (!this.i) {
         throw a.createWithContext(this.h);
      } else {
         int var0 = this.h.getCursor();
         this.h.expect('#');
         this.l = this::b;
         MinecraftKey var1 = MinecraftKey.a(this.h);
         Optional<? extends HolderSet<Item>> var2 = this.g.a(TagKey.a(Registries.C, var1));
         this.j = Either.right(var2.orElseThrow(() -> {
            this.h.setCursor(var0);
            return c.createWithContext(this.h, var1);
         }));
      }
   }

   private void c() throws CommandSyntaxException {
      this.k = new MojangsonParser(this.h).f();
   }

   private void d() throws CommandSyntaxException {
      if (this.i) {
         this.l = this::d;
      } else {
         this.l = this::c;
      }

      if (this.h.canRead() && this.h.peek() == '#') {
         this.b();
      } else {
         this.a();
      }

      this.l = this::a;
      if (this.h.canRead() && this.h.peek() == '{') {
         this.l = f;
         this.c();
      }
   }

   private CompletableFuture<Suggestions> a(SuggestionsBuilder var0) {
      if (var0.getRemaining().isEmpty()) {
         var0.suggest(String.valueOf('{'));
      }

      return var0.buildFuture();
   }

   private CompletableFuture<Suggestions> b(SuggestionsBuilder var0) {
      return ICompletionProvider.a(this.g.e().map(TagKey::b), var0, String.valueOf('#'));
   }

   private CompletableFuture<Suggestions> c(SuggestionsBuilder var0) {
      return ICompletionProvider.a(this.g.c().map(ResourceKey::a), var0);
   }

   private CompletableFuture<Suggestions> d(SuggestionsBuilder var0) {
      this.b(var0);
      return this.c(var0);
   }

   public static record a(Holder<Item> item, @Nullable NBTTagCompound nbt) {
      private final Holder<Item> a;
      @Nullable
      private final NBTTagCompound b;

      public a(Holder<Item> var0, @Nullable NBTTagCompound var1) {
         this.a = var0;
         this.b = var1;
      }
   }

   public static record b(HolderSet<Item> tag, @Nullable NBTTagCompound nbt) {
      private final HolderSet<Item> a;
      @Nullable
      private final NBTTagCompound b;

      public b(HolderSet<Item> var0, @Nullable NBTTagCompound var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
