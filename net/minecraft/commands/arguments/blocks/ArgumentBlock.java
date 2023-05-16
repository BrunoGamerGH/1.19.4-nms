package net.minecraft.commands.arguments.blocks;

import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.IBlockState;

public class ArgumentBlock {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.block.tag.disallowed"));
   public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> IChatBaseComponent.a("argument.block.id.invalid", object));
   public static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
      (object, object1) -> IChatBaseComponent.a("argument.block.property.unknown", object, object1)
   );
   public static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
      (object, object1) -> IChatBaseComponent.a("argument.block.property.duplicate", object1, object)
   );
   public static final Dynamic3CommandExceptionType e = new Dynamic3CommandExceptionType(
      (object, object1, object2) -> IChatBaseComponent.a("argument.block.property.invalid", object, object2, object1)
   );
   public static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType(
      (object, object1) -> IChatBaseComponent.a("argument.block.property.novalue", object, object1)
   );
   public static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.block.property.unclosed"));
   public static final DynamicCommandExceptionType h = new DynamicCommandExceptionType(object -> IChatBaseComponent.a("arguments.block.tag.unknown", object));
   private static final char i = '[';
   private static final char j = '{';
   private static final char k = ']';
   private static final char l = '=';
   private static final char m = ',';
   private static final char n = '#';
   private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> o = SuggestionsBuilder::buildFuture;
   private final HolderLookup<Block> p;
   private final StringReader q;
   private final boolean r;
   private final boolean s;
   private final Map<IBlockState<?>, Comparable<?>> t = Maps.newLinkedHashMap();
   private final Map<String, String> u = Maps.newHashMap();
   private MinecraftKey v = new MinecraftKey("");
   @Nullable
   private BlockStateList<Block, IBlockData> w;
   @Nullable
   private IBlockData x;
   @Nullable
   private NBTTagCompound y;
   @Nullable
   private HolderSet<Block> z;
   private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> A = o;

   private ArgumentBlock(HolderLookup<Block> holderlookup, StringReader stringreader, boolean flag, boolean flag1) {
      this.p = holderlookup;
      this.q = stringreader;
      this.r = flag;
      this.s = flag1;
   }

   public static ArgumentBlock.a a(HolderLookup<Block> holderlookup, String s, boolean flag) throws CommandSyntaxException {
      return a(holderlookup, new StringReader(s), flag);
   }

   public static ArgumentBlock.a a(HolderLookup<Block> holderlookup, StringReader stringreader, boolean flag) throws CommandSyntaxException {
      int i = stringreader.getCursor();

      try {
         ArgumentBlock argumentblock = new ArgumentBlock(holderlookup, stringreader, false, flag);
         argumentblock.a();
         return new ArgumentBlock.a(argumentblock.x, argumentblock.t, argumentblock.y);
      } catch (CommandSyntaxException var5) {
         stringreader.setCursor(i);
         throw var5;
      }
   }

   public static Either<ArgumentBlock.a, ArgumentBlock.b> b(HolderLookup<Block> holderlookup, String s, boolean flag) throws CommandSyntaxException {
      return b(holderlookup, new StringReader(s), flag);
   }

   public static Either<ArgumentBlock.a, ArgumentBlock.b> b(HolderLookup<Block> holderlookup, StringReader stringreader, boolean flag) throws CommandSyntaxException {
      int i = stringreader.getCursor();

      try {
         ArgumentBlock argumentblock = new ArgumentBlock(holderlookup, stringreader, true, flag);
         argumentblock.a();
         return argumentblock.z != null
            ? Either.right(new ArgumentBlock.b(argumentblock.z, argumentblock.u, argumentblock.y))
            : Either.left(new ArgumentBlock.a(argumentblock.x, argumentblock.t, argumentblock.y));
      } catch (CommandSyntaxException var5) {
         stringreader.setCursor(i);
         throw var5;
      }
   }

   public static CompletableFuture<Suggestions> a(HolderLookup<Block> holderlookup, SuggestionsBuilder suggestionsbuilder, boolean flag, boolean flag1) {
      StringReader stringreader = new StringReader(suggestionsbuilder.getInput());
      stringreader.setCursor(suggestionsbuilder.getStart());
      ArgumentBlock argumentblock = new ArgumentBlock(holderlookup, stringreader, flag, flag1);

      try {
         argumentblock.a();
      } catch (CommandSyntaxException var7) {
      }

      return argumentblock.A.apply(suggestionsbuilder.createOffset(stringreader.getCursor()));
   }

   private void a() throws CommandSyntaxException {
      if (this.r) {
         this.A = this::l;
      } else {
         this.A = this::k;
      }

      if (this.q.canRead() && this.q.peek() == '#') {
         this.d();
         this.A = this::h;
         if (this.q.canRead() && this.q.peek() == '[') {
            this.f();
            this.A = this::e;
         }
      } else {
         this.c();
         this.A = this::i;
         if (this.q.canRead() && this.q.peek() == '[') {
            this.e();
            this.A = this::e;
         }
      }

      if (this.s && this.q.canRead() && this.q.peek() == '{') {
         this.A = o;
         this.g();
      }
   }

   private CompletableFuture<Suggestions> a(SuggestionsBuilder suggestionsbuilder) {
      if (suggestionsbuilder.getRemaining().isEmpty()) {
         suggestionsbuilder.suggest(String.valueOf(']'));
      }

      return this.c(suggestionsbuilder);
   }

   private CompletableFuture<Suggestions> b(SuggestionsBuilder suggestionsbuilder) {
      if (suggestionsbuilder.getRemaining().isEmpty()) {
         suggestionsbuilder.suggest(String.valueOf(']'));
      }

      return this.d(suggestionsbuilder);
   }

   private CompletableFuture<Suggestions> c(SuggestionsBuilder suggestionsbuilder) {
      String s = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);

      for(IBlockState<?> iblockstate : this.x.x()) {
         if (!this.t.containsKey(iblockstate) && iblockstate.f().startsWith(s)) {
            suggestionsbuilder.suggest(iblockstate.f() + "=");
         }
      }

      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> d(SuggestionsBuilder suggestionsbuilder) {
      String s = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);
      if (this.z != null) {
         for(Holder<Block> holder : this.z) {
            for(IBlockState<?> iblockstate : holder.a().n().d()) {
               if (!this.u.containsKey(iblockstate.f()) && iblockstate.f().startsWith(s)) {
                  suggestionsbuilder.suggest(iblockstate.f() + "=");
               }
            }
         }
      }

      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> e(SuggestionsBuilder suggestionsbuilder) {
      if (suggestionsbuilder.getRemaining().isEmpty() && this.b()) {
         suggestionsbuilder.suggest(String.valueOf('{'));
      }

      return suggestionsbuilder.buildFuture();
   }

   private boolean b() {
      if (this.x != null) {
         return this.x.q();
      } else {
         if (this.z != null) {
            for(Holder<Block> holder : this.z) {
               if (holder.a().o().q()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private CompletableFuture<Suggestions> f(SuggestionsBuilder suggestionsbuilder) {
      if (suggestionsbuilder.getRemaining().isEmpty()) {
         suggestionsbuilder.suggest(String.valueOf('='));
      }

      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> g(SuggestionsBuilder suggestionsbuilder) {
      if (suggestionsbuilder.getRemaining().isEmpty()) {
         suggestionsbuilder.suggest(String.valueOf(']'));
      }

      if (suggestionsbuilder.getRemaining().isEmpty() && this.t.size() < this.x.x().size()) {
         suggestionsbuilder.suggest(String.valueOf(','));
      }

      return suggestionsbuilder.buildFuture();
   }

   private static <T extends Comparable<T>> SuggestionsBuilder a(SuggestionsBuilder suggestionsbuilder, IBlockState<T> iblockstate) {
      for(T t0 : iblockstate.a()) {
         if (t0 instanceof Integer integer) {
            suggestionsbuilder.suggest(integer);
         } else {
            suggestionsbuilder.suggest(iblockstate.a(t0));
         }
      }

      return suggestionsbuilder;
   }

   private CompletableFuture<Suggestions> a(SuggestionsBuilder suggestionsbuilder, String s) {
      boolean flag = false;
      if (this.z != null) {
         for(Holder<Block> holder : this.z) {
            Block block = holder.a();
            IBlockState<?> iblockstate = block.n().a(s);
            if (iblockstate != null) {
               a(suggestionsbuilder, iblockstate);
            }

            if (!flag) {
               for(IBlockState<?> iblockstate1 : block.n().d()) {
                  if (!this.u.containsKey(iblockstate1.f())) {
                     flag = true;
                     break;
                  }
               }
            }
         }
      }

      if (flag) {
         suggestionsbuilder.suggest(String.valueOf(','));
      }

      suggestionsbuilder.suggest(String.valueOf(']'));
      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> h(SuggestionsBuilder suggestionsbuilder) {
      if (suggestionsbuilder.getRemaining().isEmpty() && this.z != null) {
         boolean flag = false;
         boolean flag1 = false;

         for(Holder<Block> holder : this.z) {
            Block block = holder.a();
            flag |= !block.n().d().isEmpty();
            flag1 |= block.o().q();
            if (flag && flag1) {
               break;
            }
         }

         if (flag) {
            suggestionsbuilder.suggest(String.valueOf('['));
         }

         if (flag1) {
            suggestionsbuilder.suggest(String.valueOf('{'));
         }
      }

      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> i(SuggestionsBuilder suggestionsbuilder) {
      if (suggestionsbuilder.getRemaining().isEmpty()) {
         if (!this.w.d().isEmpty()) {
            suggestionsbuilder.suggest(String.valueOf('['));
         }

         if (this.x.q()) {
            suggestionsbuilder.suggest(String.valueOf('{'));
         }
      }

      return suggestionsbuilder.buildFuture();
   }

   private CompletableFuture<Suggestions> j(SuggestionsBuilder suggestionsbuilder) {
      return ICompletionProvider.a(this.p.e().map(TagKey::b), suggestionsbuilder, String.valueOf('#'));
   }

   private CompletableFuture<Suggestions> k(SuggestionsBuilder suggestionsbuilder) {
      return ICompletionProvider.a(this.p.c().map(ResourceKey::a), suggestionsbuilder);
   }

   private CompletableFuture<Suggestions> l(SuggestionsBuilder suggestionsbuilder) {
      this.j(suggestionsbuilder);
      this.k(suggestionsbuilder);
      return suggestionsbuilder.buildFuture();
   }

   private void c() throws CommandSyntaxException {
      int i = this.q.getCursor();
      this.v = MinecraftKey.a(this.q);
      Block block = this.p.a(ResourceKey.a(Registries.e, this.v)).orElseThrow(() -> {
         this.q.setCursor(i);
         return b.createWithContext(this.q, this.v.toString());
      }).a();
      this.w = block.n();
      this.x = block.o();
   }

   private void d() throws CommandSyntaxException {
      if (!this.r) {
         throw a.createWithContext(this.q);
      } else {
         int i = this.q.getCursor();
         this.q.expect('#');
         this.A = this::j;
         MinecraftKey minecraftkey = MinecraftKey.a(this.q);
         this.z = this.p.a(TagKey.a(Registries.e, minecraftkey)).orElseThrow(() -> {
            this.q.setCursor(i);
            return h.createWithContext(this.q, minecraftkey.toString());
         });
      }
   }

   private void e() throws CommandSyntaxException {
      this.q.skip();
      this.A = this::a;
      this.q.skipWhitespace();

      while(this.q.canRead() && this.q.peek() != ']') {
         this.q.skipWhitespace();
         int i = this.q.getCursor();
         String s = this.q.readString();
         IBlockState<?> iblockstate = this.w.a(s);
         if (iblockstate == null) {
            this.q.setCursor(i);
            throw c.createWithContext(this.q, this.v.toString(), s);
         }

         if (this.t.containsKey(iblockstate)) {
            this.q.setCursor(i);
            throw d.createWithContext(this.q, this.v.toString(), s);
         }

         this.q.skipWhitespace();
         this.A = this::f;
         if (!this.q.canRead() || this.q.peek() != '=') {
            throw f.createWithContext(this.q, this.v.toString(), s);
         }

         this.q.skip();
         this.q.skipWhitespace();
         this.A = suggestionsbuilder -> a(suggestionsbuilder, iblockstate).buildFuture();
         int j = this.q.getCursor();
         this.a(iblockstate, this.q.readString(), j);
         this.A = this::g;
         this.q.skipWhitespace();
         if (this.q.canRead()) {
            if (this.q.peek() != ',') {
               if (this.q.peek() != ']') {
                  throw g.createWithContext(this.q);
               }
               break;
            }

            this.q.skip();
            this.A = this::c;
         }
      }

      if (this.q.canRead()) {
         this.q.skip();
      } else {
         throw g.createWithContext(this.q);
      }
   }

   private void f() throws CommandSyntaxException {
      this.q.skip();
      this.A = this::b;
      int i = -1;
      this.q.skipWhitespace();

      while(this.q.canRead() && this.q.peek() != ']') {
         this.q.skipWhitespace();
         int j = this.q.getCursor();
         String s = this.q.readString();
         if (this.u.containsKey(s)) {
            this.q.setCursor(j);
            throw d.createWithContext(this.q, this.v.toString(), s);
         }

         this.q.skipWhitespace();
         if (!this.q.canRead() || this.q.peek() != '=') {
            this.q.setCursor(j);
            throw f.createWithContext(this.q, this.v.toString(), s);
         }

         this.q.skip();
         this.q.skipWhitespace();
         this.A = suggestionsbuilder -> this.a(suggestionsbuilder, s);
         i = this.q.getCursor();
         String s1 = this.q.readString();
         this.u.put(s, s1);
         this.q.skipWhitespace();
         if (this.q.canRead()) {
            i = -1;
            if (this.q.peek() != ',') {
               if (this.q.peek() != ']') {
                  throw g.createWithContext(this.q);
               }
               break;
            }

            this.q.skip();
            this.A = this::d;
         }
      }

      if (this.q.canRead()) {
         this.q.skip();
      } else {
         if (i >= 0) {
            this.q.setCursor(i);
         }

         throw g.createWithContext(this.q);
      }
   }

   private void g() throws CommandSyntaxException {
      this.y = new MojangsonParser(this.q).f();
   }

   private <T extends Comparable<T>> void a(IBlockState<T> iblockstate, String s, int i) throws CommandSyntaxException {
      Optional<T> optional = iblockstate.b(s);
      if (optional.isPresent()) {
         this.x = this.x.a(iblockstate, optional.get());
         this.t.put(iblockstate, optional.get());
      } else {
         this.q.setCursor(i);
         throw e.createWithContext(this.q, this.v.toString(), iblockstate.f(), s);
      }
   }

   public static String a(IBlockData iblockdata) {
      StringBuilder stringbuilder = new StringBuilder(iblockdata.c().e().map(resourcekey -> resourcekey.a().toString()).orElse("air"));
      if (!iblockdata.x().isEmpty()) {
         stringbuilder.append('[');
         boolean flag = false;

         for(UnmodifiableIterator unmodifiableiterator = iblockdata.y().entrySet().iterator(); unmodifiableiterator.hasNext(); flag = true) {
            Entry<IBlockState<?>, Comparable<?>> entry = (Entry)unmodifiableiterator.next();
            if (flag) {
               stringbuilder.append(',');
            }

            a(stringbuilder, entry.getKey(), entry.getValue());
         }

         stringbuilder.append(']');
      }

      return stringbuilder.toString();
   }

   private static <T extends Comparable<T>> void a(StringBuilder stringbuilder, IBlockState<T> iblockstate, Comparable<?> comparable) {
      stringbuilder.append(iblockstate.f());
      stringbuilder.append('=');
      stringbuilder.append(iblockstate.a((T)comparable));
   }

   public static record a(IBlockData blockState, Map<IBlockState<?>, Comparable<?>> properties, @Nullable NBTTagCompound nbt) {
      private final IBlockData a;
      private final Map<IBlockState<?>, Comparable<?>> b;
      @Nullable
      private final NBTTagCompound c;

      public a(IBlockData blockState, Map<IBlockState<?>, Comparable<?>> properties, @Nullable NBTTagCompound nbt) {
         this.a = blockState;
         this.b = properties;
         this.c = nbt;
      }
   }

   public static record b(HolderSet<Block> tag, Map<String, String> vagueProperties, @Nullable NBTTagCompound nbt) {
      private final HolderSet<Block> a;
      private final Map<String, String> b;
      @Nullable
      private final NBTTagCompound c;

      public b(HolderSet<Block> tag, Map<String, String> vagueProperties, @Nullable NBTTagCompound nbt) {
         this.a = tag;
         this.b = vagueProperties;
         this.c = nbt;
      }
   }
}
