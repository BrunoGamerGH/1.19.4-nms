package net.minecraft.commands.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;

public class ArgumentTime implements ArgumentType<Integer> {
   private static final Collection<String> a = Arrays.asList("0d", "0s", "0t", "0");
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.time.invalid_unit"));
   private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.time.tick_count_too_low", var1, var0)
   );
   private static final Object2IntMap<String> d = new Object2IntOpenHashMap();
   final int e;

   private ArgumentTime(int var0) {
      this.e = var0;
   }

   public static ArgumentTime a() {
      return new ArgumentTime(0);
   }

   public static ArgumentTime a(int var0) {
      return new ArgumentTime(var0);
   }

   public Integer a(StringReader var0) throws CommandSyntaxException {
      float var1 = var0.readFloat();
      String var2 = var0.readUnquotedString();
      int var3 = d.getOrDefault(var2, 0);
      if (var3 == 0) {
         throw b.create();
      } else {
         int var4 = Math.round(var1 * (float)var3);
         if (var4 < this.e) {
            throw c.create(var4, this.e);
         } else {
            return var4;
         }
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      StringReader var2 = new StringReader(var1.getRemaining());

      try {
         var2.readFloat();
      } catch (CommandSyntaxException var5) {
         return var1.buildFuture();
      }

      return ICompletionProvider.b(d.keySet(), var1.createOffset(var1.getStart() + var2.getCursor()));
   }

   public Collection<String> getExamples() {
      return a;
   }

   static {
      d.put("d", 24000);
      d.put("s", 20);
      d.put("t", 1);
      d.put("", 1);
   }

   public static class a implements ArgumentTypeInfo<ArgumentTime, ArgumentTime.a.a> {
      public void a(ArgumentTime.a.a var0, PacketDataSerializer var1) {
         var1.writeInt(var0.b);
      }

      public ArgumentTime.a.a a(PacketDataSerializer var0) {
         int var1 = var0.readInt();
         return new ArgumentTime.a.a(var1);
      }

      public void a(ArgumentTime.a.a var0, JsonObject var1) {
         var1.addProperty("min", var0.b);
      }

      public ArgumentTime.a.a a(ArgumentTime var0) {
         return new ArgumentTime.a.a(var0.e);
      }

      public final class a implements ArgumentTypeInfo.a<ArgumentTime> {
         final int b;

         a(int var1) {
            this.b = var1;
         }

         public ArgumentTime a(CommandBuildContext var0) {
            return ArgumentTime.a(this.b);
         }

         @Override
         public ArgumentTypeInfo<ArgumentTime, ?> a() {
            return a.this;
         }
      }
   }
}
