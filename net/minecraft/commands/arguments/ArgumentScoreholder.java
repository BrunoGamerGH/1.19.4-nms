package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.selector.ArgumentParserSelector;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;

public class ArgumentScoreholder implements ArgumentType<ArgumentScoreholder.b> {
   public static final SuggestionProvider<CommandListenerWrapper> a = (var0, var1) -> {
      StringReader var2 = new StringReader(var1.getInput());
      var2.setCursor(var1.getStart());
      ArgumentParserSelector var3 = new ArgumentParserSelector(var2);

      try {
         var3.t();
      } catch (CommandSyntaxException var5) {
      }

      return var3.a(var1, var1x -> ICompletionProvider.b(((CommandListenerWrapper)var0.getSource()).p(), var1x));
   };
   private static final Collection<String> b = Arrays.asList("Player", "0123", "*", "@e");
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.scoreHolder.empty"));
   final boolean d;

   public ArgumentScoreholder(boolean var0) {
      this.d = var0;
   }

   public static String a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return b(var0, var1).iterator().next();
   }

   public static Collection<String> b(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return a(var0, var1, Collections::emptyList);
   }

   public static Collection<String> c(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return a(var0, var1, ((CommandListenerWrapper)var0.getSource()).l().aF()::e);
   }

   public static Collection<String> a(CommandContext<CommandListenerWrapper> var0, String var1, Supplier<Collection<String>> var2) throws CommandSyntaxException {
      Collection<String> var3 = ((ArgumentScoreholder.b)var0.getArgument(var1, ArgumentScoreholder.b.class))
         .getNames((CommandListenerWrapper)var0.getSource(), var2);
      if (var3.isEmpty()) {
         throw ArgumentEntity.d.create();
      } else {
         return var3;
      }
   }

   public static ArgumentScoreholder a() {
      return new ArgumentScoreholder(false);
   }

   public static ArgumentScoreholder b() {
      return new ArgumentScoreholder(true);
   }

   public ArgumentScoreholder.b a(StringReader var0) throws CommandSyntaxException {
      if (var0.canRead() && var0.peek() == '@') {
         ArgumentParserSelector var1 = new ArgumentParserSelector(var0);
         EntitySelector var2 = var1.t();
         if (!this.d && var2.a() > 1) {
            throw ArgumentEntity.a.create();
         } else {
            return new ArgumentScoreholder.c(var2);
         }
      } else {
         int var1 = var0.getCursor();

         while(var0.canRead() && var0.peek() != ' ') {
            var0.skip();
         }

         String var2 = var0.getString().substring(var1, var0.getCursor());
         if (var2.equals("*")) {
            return (var0x, var1x) -> {
               Collection<String> var2x = var1x.get();
               if (var2x.isEmpty()) {
                  throw c.create();
               } else {
                  return var2x;
               }
            };
         } else {
            Collection<String> var3 = Collections.singleton(var2);
            return (var1x, var2x) -> var3;
         }
      }
   }

   public Collection<String> getExamples() {
      return b;
   }

   public static class a implements ArgumentTypeInfo<ArgumentScoreholder, ArgumentScoreholder.a.a> {
      private static final byte a = 1;

      public void a(ArgumentScoreholder.a.a var0, PacketDataSerializer var1) {
         int var2 = 0;
         if (var0.b) {
            var2 |= 1;
         }

         var1.writeByte(var2);
      }

      public ArgumentScoreholder.a.a a(PacketDataSerializer var0) {
         byte var1 = var0.readByte();
         boolean var2 = (var1 & 1) != 0;
         return new ArgumentScoreholder.a.a(var2);
      }

      public void a(ArgumentScoreholder.a.a var0, JsonObject var1) {
         var1.addProperty("amount", var0.b ? "multiple" : "single");
      }

      public ArgumentScoreholder.a.a a(ArgumentScoreholder var0) {
         return new ArgumentScoreholder.a.a(var0.d);
      }

      public final class a implements ArgumentTypeInfo.a<ArgumentScoreholder> {
         final boolean b;

         a(boolean var1) {
            this.b = var1;
         }

         public ArgumentScoreholder a(CommandBuildContext var0) {
            return new ArgumentScoreholder(this.b);
         }

         @Override
         public ArgumentTypeInfo<ArgumentScoreholder, ?> a() {
            return a.this;
         }
      }
   }

   @FunctionalInterface
   public interface b {
      Collection<String> getNames(CommandListenerWrapper var1, Supplier<Collection<String>> var2) throws CommandSyntaxException;
   }

   public static class c implements ArgumentScoreholder.b {
      private final EntitySelector a;

      public c(EntitySelector var0) {
         this.a = var0;
      }

      @Override
      public Collection<String> getNames(CommandListenerWrapper var0, Supplier<Collection<String>> var1) throws CommandSyntaxException {
         List<? extends Entity> var2 = this.a.b(var0);
         if (var2.isEmpty()) {
            throw ArgumentEntity.d.create();
         } else {
            List<String> var3 = Lists.newArrayList();

            for(Entity var5 : var2) {
               var3.add(var5.cu());
            }

            return var3;
         }
      }
   }
}
