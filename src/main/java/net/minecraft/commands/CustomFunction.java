package net.minecraft.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.CustomFunctionData;

public class CustomFunction {
   private final CustomFunction.c[] a;
   final MinecraftKey b;

   public CustomFunction(MinecraftKey var0, CustomFunction.c[] var1) {
      this.b = var0;
      this.a = var1;
   }

   public MinecraftKey a() {
      return this.b;
   }

   public CustomFunction.c[] b() {
      return this.a;
   }

   public static CustomFunction a(
      MinecraftKey var0, com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> var1, CommandListenerWrapper var2, List<String> var3
   ) {
      List<CustomFunction.c> var4 = Lists.newArrayListWithCapacity(var3.size());

      for(int var5 = 0; var5 < var3.size(); ++var5) {
         int var6 = var5 + 1;
         String var7 = var3.get(var5).trim();
         StringReader var8 = new StringReader(var7);
         if (var8.canRead() && var8.peek() != '#') {
            if (var8.peek() == '/') {
               var8.skip();
               if (var8.peek() == '/') {
                  throw new IllegalArgumentException(
                     "Unknown or invalid command '" + var7 + "' on line " + var6 + " (if you intended to make a comment, use '#' not '//')"
                  );
               }

               String var9 = var8.readUnquotedString();
               throw new IllegalArgumentException(
                  "Unknown or invalid command '" + var7 + "' on line " + var6 + " (did you mean '" + var9 + "'? Do not use a preceding forwards slash.)"
               );
            }

            try {
               ParseResults<CommandListenerWrapper> var9 = var1.parse(var8, var2);
               if (var9.getReader().canRead()) {
                  throw CommandDispatcher.a(var9);
               }

               var4.add(new CustomFunction.b(var9));
            } catch (CommandSyntaxException var10) {
               throw new IllegalArgumentException("Whilst parsing command on line " + var6 + ": " + var10.getMessage());
            }
         }
      }

      return new CustomFunction(var0, var4.toArray(new CustomFunction.c[0]));
   }

   public static class a {
      public static final CustomFunction.a a = new CustomFunction.a((MinecraftKey)null);
      @Nullable
      private final MinecraftKey b;
      private boolean c;
      private Optional<CustomFunction> d = Optional.empty();

      public a(@Nullable MinecraftKey var0) {
         this.b = var0;
      }

      public a(CustomFunction var0) {
         this.c = true;
         this.b = null;
         this.d = Optional.of(var0);
      }

      public Optional<CustomFunction> a(CustomFunctionData var0) {
         if (!this.c) {
            if (this.b != null) {
               this.d = var0.a(this.b);
            }

            this.c = true;
         }

         return this.d;
      }

      @Nullable
      public MinecraftKey a() {
         return this.d.<MinecraftKey>map(var0 -> var0.b).orElse(this.b);
      }
   }

   public static class b implements CustomFunction.c {
      private final ParseResults<CommandListenerWrapper> a;

      public b(ParseResults<CommandListenerWrapper> var0) {
         this.a = var0;
      }

      @Override
      public void execute(
         CustomFunctionData var0, CommandListenerWrapper var1, Deque<CustomFunctionData.b> var2, int var3, int var4, @Nullable CustomFunctionData.c var5
      ) throws CommandSyntaxException {
         if (var5 != null) {
            String var6 = this.a.getReader().getString();
            var5.a(var4, var6);
            int var7 = this.a(var0, var1);
            var5.a(var4, var6, var7);
         } else {
            this.a(var0, var1);
         }
      }

      private int a(CustomFunctionData var0, CommandListenerWrapper var1) throws CommandSyntaxException {
         return var0.b().execute(CommandDispatcher.a(this.a, var1x -> var1));
      }

      @Override
      public String toString() {
         return this.a.getReader().getString();
      }
   }

   @FunctionalInterface
   public interface c {
      void execute(
         CustomFunctionData var1, CommandListenerWrapper var2, Deque<CustomFunctionData.b> var3, int var4, int var5, @Nullable CustomFunctionData.c var6
      ) throws CommandSyntaxException;
   }

   public static class d implements CustomFunction.c {
      private final CustomFunction.a a;

      public d(CustomFunction var0) {
         this.a = new CustomFunction.a(var0);
      }

      @Override
      public void execute(
         CustomFunctionData var0, CommandListenerWrapper var1, Deque<CustomFunctionData.b> var2, int var3, int var4, @Nullable CustomFunctionData.c var5
      ) {
         SystemUtils.a(this.a.a(var0), var5x -> {
            CustomFunction.c[] var6x = var5x.b();
            if (var5 != null) {
               var5.a(var4, var5x.a(), var6x.length);
            }

            int var7 = var3 - var2.size();
            int var8 = Math.min(var6x.length, var7);

            for(int var9 = var8 - 1; var9 >= 0; --var9) {
               var2.addFirst(new CustomFunctionData.b(var1, var4 + 1, var6x[var9]));
            }
         }, () -> {
            if (var5 != null) {
               var5.a(var4, this.a.a(), -1);
            }
         });
      }

      @Override
      public String toString() {
         return "function " + this.a.a();
      }
   }
}
