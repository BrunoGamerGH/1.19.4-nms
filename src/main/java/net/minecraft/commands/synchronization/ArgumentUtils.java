package net.minecraft.commands.synchronization;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.Logger;

public class ArgumentUtils {
   private static final Logger a = LogUtils.getLogger();
   private static final byte b = 1;
   private static final byte c = 2;

   public static int a(boolean var0, boolean var1) {
      int var2 = 0;
      if (var0) {
         var2 |= 1;
      }

      if (var1) {
         var2 |= 2;
      }

      return var2;
   }

   public static boolean a(byte var0) {
      return (var0 & 1) != 0;
   }

   public static boolean b(byte var0) {
      return (var0 & 2) != 0;
   }

   private static <A extends ArgumentType<?>> void a(JsonObject var0, ArgumentTypeInfo.a<A> var1) {
      a(var0, var1.a(), var1);
   }

   private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.a<A>> void a(JsonObject var0, ArgumentTypeInfo<A, T> var1, ArgumentTypeInfo.a<A> var2) {
      var1.a((T)var2, var0);
   }

   private static <T extends ArgumentType<?>> void a(JsonObject var0, T var1) {
      ArgumentTypeInfo.a<T> var2 = ArgumentTypeInfos.b(var1);
      var0.addProperty("type", "argument");
      var0.addProperty("parser", BuiltInRegistries.w.b(var2.a()).toString());
      JsonObject var3 = new JsonObject();
      a(var3, var2);
      if (var3.size() > 0) {
         var0.add("properties", var3);
      }
   }

   public static <S> JsonObject a(CommandDispatcher<S> var0, CommandNode<S> var1) {
      JsonObject var2 = new JsonObject();
      if (var1 instanceof RootCommandNode) {
         var2.addProperty("type", "root");
      } else if (var1 instanceof LiteralCommandNode) {
         var2.addProperty("type", "literal");
      } else if (var1 instanceof ArgumentCommandNode var3) {
         a(var2, var3.getType());
      } else {
         a.error("Could not serialize node {} ({})!", var1, var1.getClass());
         var2.addProperty("type", "unknown");
      }

      JsonObject var3 = new JsonObject();

      for(CommandNode<S> var5 : var1.getChildren()) {
         var3.add(var5.getName(), a(var0, var5));
      }

      if (var3.size() > 0) {
         var2.add("children", var3);
      }

      if (var1.getCommand() != null) {
         var2.addProperty("executable", true);
      }

      if (var1.getRedirect() != null) {
         Collection<String> var4 = var0.getPath(var1.getRedirect());
         if (!var4.isEmpty()) {
            JsonArray var5 = new JsonArray();

            for(String var7 : var4) {
               var5.add(var7);
            }

            var2.add("redirect", var5);
         }
      }

      return var2;
   }

   public static <T> Set<ArgumentType<?>> a(CommandNode<T> var0) {
      Set<CommandNode<T>> var1 = Sets.newIdentityHashSet();
      Set<ArgumentType<?>> var2 = Sets.newHashSet();
      a(var0, var2, var1);
      return var2;
   }

   private static <T> void a(CommandNode<T> var0, Set<ArgumentType<?>> var1, Set<CommandNode<T>> var2) {
      if (var2.add(var0)) {
         if (var0 instanceof ArgumentCommandNode var3) {
            var1.add(var3.getType());
         }

         var0.getChildren().forEach(var2x -> a(var2x, var1, var2));
         CommandNode<T> var3 = var0.getRedirect();
         if (var3 != null) {
            a(var3, var1, var2);
         }
      }
   }
}
