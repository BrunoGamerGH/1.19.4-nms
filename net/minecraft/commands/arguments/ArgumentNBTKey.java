package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class ArgumentNBTKey implements ArgumentType<ArgumentNBTKey.g> {
   private static final Collection<String> d = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("arguments.nbtpath.node.invalid"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("arguments.nbtpath.too_deep"));
   public static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("arguments.nbtpath.nothing_found", var0));
   static final DynamicCommandExceptionType e = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.data.modify.expected_list", var0));
   static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.data.modify.invalid_index", var0));
   private static final char g = '[';
   private static final char h = ']';
   private static final char i = '{';
   private static final char j = '}';
   private static final char k = '"';

   public static ArgumentNBTKey a() {
      return new ArgumentNBTKey();
   }

   public static ArgumentNBTKey.g a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (ArgumentNBTKey.g)var0.getArgument(var1, ArgumentNBTKey.g.class);
   }

   public ArgumentNBTKey.g a(StringReader var0) throws CommandSyntaxException {
      List<ArgumentNBTKey.h> var1 = Lists.newArrayList();
      int var2 = var0.getCursor();
      Object2IntMap<ArgumentNBTKey.h> var3 = new Object2IntOpenHashMap();
      boolean var4 = true;

      while(var0.canRead() && var0.peek() != ' ') {
         ArgumentNBTKey.h var5 = a(var0, var4);
         var1.add(var5);
         var3.put(var5, var0.getCursor() - var2);
         var4 = false;
         if (var0.canRead()) {
            char var6 = var0.peek();
            if (var6 != ' ' && var6 != '[' && var6 != '{') {
               var0.expect('.');
            }
         }
      }

      return new ArgumentNBTKey.g(var0.getString().substring(var2, var0.getCursor()), var1.toArray(new ArgumentNBTKey.h[0]), var3);
   }

   private static ArgumentNBTKey.h a(StringReader var0, boolean var1) throws CommandSyntaxException {
      switch(var0.peek()) {
         case '"': {
            String var2 = var0.readString();
            return a(var0, var2);
         }
         case '[': {
            var0.skip();
            int var2 = var0.peek();
            if (var2 == 123) {
               NBTTagCompound var3 = new MojangsonParser(var0).f();
               var0.expect(']');
               return new ArgumentNBTKey.d(var3);
            } else {
               if (var2 == 93) {
                  var0.skip();
                  return ArgumentNBTKey.a.a;
               }

               int var3 = var0.readInt();
               var0.expect(']');
               return new ArgumentNBTKey.c(var3);
            }
         }
         case '{': {
            if (!var1) {
               throw a.createWithContext(var0);
            }

            NBTTagCompound var2 = new MojangsonParser(var0).f();
            return new ArgumentNBTKey.f(var2);
         }
         default: {
            String var2 = b(var0);
            return a(var0, var2);
         }
      }
   }

   private static ArgumentNBTKey.h a(StringReader var0, String var1) throws CommandSyntaxException {
      if (var0.canRead() && var0.peek() == '{') {
         NBTTagCompound var2 = new MojangsonParser(var0).f();
         return new ArgumentNBTKey.e(var1, var2);
      } else {
         return new ArgumentNBTKey.b(var1);
      }
   }

   private static String b(StringReader var0) throws CommandSyntaxException {
      int var1 = var0.getCursor();

      while(var0.canRead() && a(var0.peek())) {
         var0.skip();
      }

      if (var0.getCursor() == var1) {
         throw a.createWithContext(var0);
      } else {
         return var0.getString().substring(var1, var0.getCursor());
      }
   }

   public Collection<String> getExamples() {
      return d;
   }

   private static boolean a(char var0) {
      return var0 != ' ' && var0 != '"' && var0 != '[' && var0 != ']' && var0 != '.' && var0 != '{' && var0 != '}';
   }

   static Predicate<NBTBase> a(NBTTagCompound var0) {
      return var1 -> GameProfileSerializer.a(var0, var1, true);
   }

   static class a implements ArgumentNBTKey.h {
      public static final ArgumentNBTKey.a a = new ArgumentNBTKey.a();

      private a() {
      }

      @Override
      public void a(NBTBase var0, List<NBTBase> var1) {
         if (var0 instanceof NBTList) {
            var1.addAll((NBTList)var0);
         }
      }

      @Override
      public void a(NBTBase var0, Supplier<NBTBase> var1, List<NBTBase> var2) {
         if (var0 instanceof NBTList var3) {
            if (var3.isEmpty()) {
               NBTBase var4 = var1.get();
               if (var3.b(0, var4)) {
                  var2.add(var4);
               }
            } else {
               var2.addAll(var3);
            }
         }
      }

      @Override
      public NBTBase a() {
         return new NBTTagList();
      }

      @Override
      public int a(NBTBase var0, Supplier<NBTBase> var1) {
         if (!(var0 instanceof NBTList)) {
            return 0;
         } else {
            NBTList<?> var2 = (NBTList)var0;
            int var3 = var2.size();
            if (var3 == 0) {
               var2.b(0, var1.get());
               return 1;
            } else {
               NBTBase var4 = var1.get();
               int var5 = var3 - (int)var2.stream().filter(var4::equals).count();
               if (var5 == 0) {
                  return 0;
               } else {
                  var2.clear();
                  if (!var2.b(0, var4)) {
                     return 0;
                  } else {
                     for(int var6 = 1; var6 < var3; ++var6) {
                        var2.b(var6, var1.get());
                     }

                     return var5;
                  }
               }
            }
         }
      }

      @Override
      public int a(NBTBase var0) {
         if (var0 instanceof NBTList var1) {
            int var2 = var1.size();
            if (var2 > 0) {
               var1.clear();
               return var2;
            }
         }

         return 0;
      }
   }

   static class b implements ArgumentNBTKey.h {
      private final String a;

      public b(String var0) {
         this.a = var0;
      }

      @Override
      public void a(NBTBase var0, List<NBTBase> var1) {
         if (var0 instanceof NBTTagCompound) {
            NBTBase var2 = ((NBTTagCompound)var0).c(this.a);
            if (var2 != null) {
               var1.add(var2);
            }
         }
      }

      @Override
      public void a(NBTBase var0, Supplier<NBTBase> var1, List<NBTBase> var2) {
         if (var0 instanceof NBTTagCompound var3) {
            NBTBase var4;
            if (var3.e(this.a)) {
               var4 = var3.c(this.a);
            } else {
               var4 = var1.get();
               var3.a(this.a, var4);
            }

            var2.add(var4);
         }
      }

      @Override
      public NBTBase a() {
         return new NBTTagCompound();
      }

      @Override
      public int a(NBTBase var0, Supplier<NBTBase> var1) {
         if (var0 instanceof NBTTagCompound var2) {
            NBTBase var3 = var1.get();
            NBTBase var4 = var2.a(this.a, var3);
            if (!var3.equals(var4)) {
               return 1;
            }
         }

         return 0;
      }

      @Override
      public int a(NBTBase var0) {
         if (var0 instanceof NBTTagCompound var1 && var1.e(this.a)) {
            var1.r(this.a);
            return 1;
         }

         return 0;
      }
   }

   static class c implements ArgumentNBTKey.h {
      private final int a;

      public c(int var0) {
         this.a = var0;
      }

      @Override
      public void a(NBTBase var0, List<NBTBase> var1) {
         if (var0 instanceof NBTList var2) {
            int var3 = var2.size();
            int var4 = this.a < 0 ? var3 + this.a : this.a;
            if (0 <= var4 && var4 < var3) {
               var1.add((NBTBase)var2.get(var4));
            }
         }
      }

      @Override
      public void a(NBTBase var0, Supplier<NBTBase> var1, List<NBTBase> var2) {
         this.a(var0, var2);
      }

      @Override
      public NBTBase a() {
         return new NBTTagList();
      }

      @Override
      public int a(NBTBase var0, Supplier<NBTBase> var1) {
         if (var0 instanceof NBTList var2) {
            int var3 = var2.size();
            int var4 = this.a < 0 ? var3 + this.a : this.a;
            if (0 <= var4 && var4 < var3) {
               NBTBase var5 = (NBTBase)var2.get(var4);
               NBTBase var6 = var1.get();
               if (!var6.equals(var5) && var2.a(var4, var6)) {
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int a(NBTBase var0) {
         if (var0 instanceof NBTList var1) {
            int var2 = var1.size();
            int var3 = this.a < 0 ? var2 + this.a : this.a;
            if (0 <= var3 && var3 < var2) {
               var1.c(var3);
               return 1;
            }
         }

         return 0;
      }
   }

   static class d implements ArgumentNBTKey.h {
      private final NBTTagCompound a;
      private final Predicate<NBTBase> b;

      public d(NBTTagCompound var0) {
         this.a = var0;
         this.b = ArgumentNBTKey.a(var0);
      }

      @Override
      public void a(NBTBase var0, List<NBTBase> var1) {
         if (var0 instanceof NBTTagList var2) {
            var2.stream().filter(this.b).forEach(var1::add);
         }
      }

      @Override
      public void a(NBTBase var0, Supplier<NBTBase> var1, List<NBTBase> var2) {
         MutableBoolean var3 = new MutableBoolean();
         if (var0 instanceof NBTTagList var4) {
            var4.stream().filter(this.b).forEach(var2x -> {
               var2.add(var2x);
               var3.setTrue();
            });
            if (var3.isFalse()) {
               NBTTagCompound var5 = this.a.h();
               var4.add(var5);
               var2.add(var5);
            }
         }
      }

      @Override
      public NBTBase a() {
         return new NBTTagList();
      }

      @Override
      public int a(NBTBase var0, Supplier<NBTBase> var1) {
         int var2 = 0;
         if (var0 instanceof NBTTagList var3) {
            int var4 = var3.size();
            if (var4 == 0) {
               var3.add(var1.get());
               ++var2;
            } else {
               for(int var5 = 0; var5 < var4; ++var5) {
                  NBTBase var6 = var3.k(var5);
                  if (this.b.test(var6)) {
                     NBTBase var7 = var1.get();
                     if (!var7.equals(var6) && var3.a(var5, var7)) {
                        ++var2;
                     }
                  }
               }
            }
         }

         return var2;
      }

      @Override
      public int a(NBTBase var0) {
         int var1 = 0;
         if (var0 instanceof NBTTagList var2) {
            for(int var3 = var2.size() - 1; var3 >= 0; --var3) {
               if (this.b.test(var2.k(var3))) {
                  var2.c(var3);
                  ++var1;
               }
            }
         }

         return var1;
      }
   }

   static class e implements ArgumentNBTKey.h {
      private final String a;
      private final NBTTagCompound b;
      private final Predicate<NBTBase> c;

      public e(String var0, NBTTagCompound var1) {
         this.a = var0;
         this.b = var1;
         this.c = ArgumentNBTKey.a(var1);
      }

      @Override
      public void a(NBTBase var0, List<NBTBase> var1) {
         if (var0 instanceof NBTTagCompound) {
            NBTBase var2 = ((NBTTagCompound)var0).c(this.a);
            if (this.c.test(var2)) {
               var1.add(var2);
            }
         }
      }

      @Override
      public void a(NBTBase var0, Supplier<NBTBase> var1, List<NBTBase> var2) {
         if (var0 instanceof NBTTagCompound var3) {
            NBTBase var4 = var3.c(this.a);
            if (var4 == null) {
               NBTBase var6 = this.b.h();
               var3.a(this.a, var6);
               var2.add(var6);
            } else if (this.c.test(var4)) {
               var2.add(var4);
            }
         }
      }

      @Override
      public NBTBase a() {
         return new NBTTagCompound();
      }

      @Override
      public int a(NBTBase var0, Supplier<NBTBase> var1) {
         if (var0 instanceof NBTTagCompound var2) {
            NBTBase var3 = var2.c(this.a);
            if (this.c.test(var3)) {
               NBTBase var4 = var1.get();
               if (!var4.equals(var3)) {
                  var2.a(this.a, var4);
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int a(NBTBase var0) {
         if (var0 instanceof NBTTagCompound var1) {
            NBTBase var2 = var1.c(this.a);
            if (this.c.test(var2)) {
               var1.r(this.a);
               return 1;
            }
         }

         return 0;
      }
   }

   static class f implements ArgumentNBTKey.h {
      private final Predicate<NBTBase> a;

      public f(NBTTagCompound var0) {
         this.a = ArgumentNBTKey.a(var0);
      }

      @Override
      public void a(NBTBase var0, List<NBTBase> var1) {
         if (var0 instanceof NBTTagCompound && this.a.test(var0)) {
            var1.add(var0);
         }
      }

      @Override
      public void a(NBTBase var0, Supplier<NBTBase> var1, List<NBTBase> var2) {
         this.a(var0, var2);
      }

      @Override
      public NBTBase a() {
         return new NBTTagCompound();
      }

      @Override
      public int a(NBTBase var0, Supplier<NBTBase> var1) {
         return 0;
      }

      @Override
      public int a(NBTBase var0) {
         return 0;
      }
   }

   public static class g {
      private final String a;
      private final Object2IntMap<ArgumentNBTKey.h> b;
      private final ArgumentNBTKey.h[] c;

      public g(String var0, ArgumentNBTKey.h[] var1, Object2IntMap<ArgumentNBTKey.h> var2) {
         this.a = var0;
         this.c = var1;
         this.b = var2;
      }

      public List<NBTBase> a(NBTBase var0) throws CommandSyntaxException {
         List<NBTBase> var1 = Collections.singletonList(var0);

         for(ArgumentNBTKey.h var5 : this.c) {
            var1 = var5.a(var1);
            if (var1.isEmpty()) {
               throw this.a(var5);
            }
         }

         return var1;
      }

      public int b(NBTBase var0) {
         List<NBTBase> var1 = Collections.singletonList(var0);

         for(ArgumentNBTKey.h var5 : this.c) {
            var1 = var5.a(var1);
            if (var1.isEmpty()) {
               return 0;
            }
         }

         return var1.size();
      }

      private List<NBTBase> d(NBTBase var0) throws CommandSyntaxException {
         List<NBTBase> var1 = Collections.singletonList(var0);

         for(int var2 = 0; var2 < this.c.length - 1; ++var2) {
            ArgumentNBTKey.h var3 = this.c[var2];
            int var4 = var2 + 1;
            var1 = var3.a(var1, this.c[var4]::a);
            if (var1.isEmpty()) {
               throw this.a(var3);
            }
         }

         return var1;
      }

      public List<NBTBase> a(NBTBase var0, Supplier<NBTBase> var1) throws CommandSyntaxException {
         List<NBTBase> var2 = this.d(var0);
         ArgumentNBTKey.h var3 = this.c[this.c.length - 1];
         return var3.a(var2, var1);
      }

      private static int a(List<NBTBase> var0, Function<NBTBase, Integer> var1) {
         return var0.stream().map(var1).reduce(0, (var0x, var1x) -> var0x + var1x);
      }

      public static boolean a(NBTBase var0, int var1) {
         if (var1 >= 512) {
            return true;
         } else {
            if (var0 instanceof NBTTagCompound var2) {
               for(String var5 : var2.e()) {
                  NBTBase var6 = var2.c(var5);
                  if (var6 != null && a(var6, var1 + 1)) {
                     return true;
                  }
               }
            } else if (var0 instanceof NBTTagList) {
               for(NBTBase var5 : (NBTTagList)var0) {
                  if (a(var5, var1 + 1)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      public int a(NBTBase var0, NBTBase var1) throws CommandSyntaxException {
         if (a(var1, this.a())) {
            throw ArgumentNBTKey.b.create();
         } else {
            NBTBase var2 = var1.d();
            List<NBTBase> var3 = this.d(var0);
            if (var3.isEmpty()) {
               return 0;
            } else {
               ArgumentNBTKey.h var4 = this.c[this.c.length - 1];
               MutableBoolean var5 = new MutableBoolean(false);
               return a(var3, var3x -> var4.a(var3x, () -> {
                     if (var5.isFalse()) {
                        var5.setTrue();
                        return var2;
                     } else {
                        return var2.d();
                     }
                  }));
            }
         }
      }

      private int a() {
         return this.c.length;
      }

      public int a(int var0, NBTTagCompound var1, List<NBTBase> var2) throws CommandSyntaxException {
         List<NBTBase> var3 = new ArrayList<>(var2.size());

         for(NBTBase var5 : var2) {
            NBTBase var6 = var5.d();
            var3.add(var6);
            if (a(var6, this.a())) {
               throw ArgumentNBTKey.b.create();
            }
         }

         Collection<NBTBase> var4 = this.a(var1, NBTTagList::new);
         int var5 = 0;
         boolean var6 = false;

         for(NBTBase var8 : var4) {
            if (!(var8 instanceof NBTList)) {
               throw ArgumentNBTKey.e.create(var8);
            }

            NBTList<?> var9 = (NBTList)var8;
            boolean var10 = false;
            int var11 = var0 < 0 ? var9.size() + var0 + 1 : var0;

            for(NBTBase var13 : var3) {
               try {
                  if (var9.b(var11, var6 ? var13.d() : var13)) {
                     ++var11;
                     var10 = true;
                  }
               } catch (IndexOutOfBoundsException var16) {
                  throw ArgumentNBTKey.f.create(var11);
               }
            }

            var6 = true;
            var5 += var10 ? 1 : 0;
         }

         return var5;
      }

      public int c(NBTBase var0) {
         List<NBTBase> var1 = Collections.singletonList(var0);

         for(int var2 = 0; var2 < this.c.length - 1; ++var2) {
            var1 = this.c[var2].a(var1);
         }

         ArgumentNBTKey.h var2 = this.c[this.c.length - 1];
         return a(var1, var2::a);
      }

      private CommandSyntaxException a(ArgumentNBTKey.h var0) {
         int var1 = this.b.getInt(var0);
         return ArgumentNBTKey.c.create(this.a.substring(0, var1));
      }

      @Override
      public String toString() {
         return this.a;
      }
   }

   interface h {
      void a(NBTBase var1, List<NBTBase> var2);

      void a(NBTBase var1, Supplier<NBTBase> var2, List<NBTBase> var3);

      NBTBase a();

      int a(NBTBase var1, Supplier<NBTBase> var2);

      int a(NBTBase var1);

      default List<NBTBase> a(List<NBTBase> var0) {
         return this.a(var0, this::a);
      }

      default List<NBTBase> a(List<NBTBase> var0, Supplier<NBTBase> var1) {
         return this.a(var0, (var1x, var2x) -> this.a(var1x, var1, var2x));
      }

      default List<NBTBase> a(List<NBTBase> var0, BiConsumer<NBTBase, List<NBTBase>> var1) {
         List<NBTBase> var2 = Lists.newArrayList();

         for(NBTBase var4 : var0) {
            var1.accept(var4, var2);
         }

         return var2;
      }
   }
}
