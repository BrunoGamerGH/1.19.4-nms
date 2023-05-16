package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.CustomFunction;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;

public class ArgumentTag implements ArgumentType<ArgumentTag.a> {
   private static final Collection<String> a = Arrays.asList("foo", "foo:bar", "#foo");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("arguments.function.tag.unknown", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("arguments.function.unknown", var0));

   public static ArgumentTag a() {
      return new ArgumentTag();
   }

   public ArgumentTag.a a(StringReader var0) throws CommandSyntaxException {
      if (var0.canRead() && var0.peek() == '#') {
         var0.skip();
         final MinecraftKey var1 = MinecraftKey.a(var0);
         return new ArgumentTag.a() {
            @Override
            public Collection<CustomFunction> a(CommandContext<CommandListenerWrapper> var0) throws CommandSyntaxException {
               return ArgumentTag.b(var0, var1);
            }

            @Override
            public Pair<MinecraftKey, Either<CustomFunction, Collection<CustomFunction>>> b(CommandContext<CommandListenerWrapper> var0) throws CommandSyntaxException {
               return Pair.of(var1, Either.right(ArgumentTag.b(var0, var1)));
            }
         };
      } else {
         final MinecraftKey var1 = MinecraftKey.a(var0);
         return new ArgumentTag.a() {
            @Override
            public Collection<CustomFunction> a(CommandContext<CommandListenerWrapper> var0) throws CommandSyntaxException {
               return Collections.singleton(ArgumentTag.a(var0, var1));
            }

            @Override
            public Pair<MinecraftKey, Either<CustomFunction, Collection<CustomFunction>>> b(CommandContext<CommandListenerWrapper> var0) throws CommandSyntaxException {
               return Pair.of(var1, Either.left(ArgumentTag.a(var0, var1)));
            }
         };
      }
   }

   static CustomFunction a(CommandContext<CommandListenerWrapper> var0, MinecraftKey var1) throws CommandSyntaxException {
      return ((CommandListenerWrapper)var0.getSource()).l().aA().a(var1).orElseThrow(() -> c.create(var1.toString()));
   }

   static Collection<CustomFunction> b(CommandContext<CommandListenerWrapper> var0, MinecraftKey var1) throws CommandSyntaxException {
      Collection<CustomFunction> var2 = ((CommandListenerWrapper)var0.getSource()).l().aA().b(var1);
      if (var2 == null) {
         throw b.create(var1.toString());
      } else {
         return var2;
      }
   }

   public static Collection<CustomFunction> a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return ((ArgumentTag.a)var0.getArgument(var1, ArgumentTag.a.class)).a(var0);
   }

   public static Pair<MinecraftKey, Either<CustomFunction, Collection<CustomFunction>>> b(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return ((ArgumentTag.a)var0.getArgument(var1, ArgumentTag.a.class)).b(var0);
   }

   public Collection<String> getExamples() {
      return a;
   }

   public interface a {
      Collection<CustomFunction> a(CommandContext<CommandListenerWrapper> var1) throws CommandSyntaxException;

      Pair<MinecraftKey, Either<CustomFunction, Collection<CustomFunction>>> b(CommandContext<CommandListenerWrapper> var1) throws CommandSyntaxException;
   }
}
