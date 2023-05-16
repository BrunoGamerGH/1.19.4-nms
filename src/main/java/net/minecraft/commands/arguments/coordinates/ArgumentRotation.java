package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;

public class ArgumentRotation implements ArgumentType<IVectorPosition> {
   private static final Collection<String> b = Arrays.asList("0 0", "~ ~", "~-5 ~5");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.rotation.incomplete"));

   public static ArgumentRotation a() {
      return new ArgumentRotation();
   }

   public static IVectorPosition a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (IVectorPosition)var0.getArgument(var1, IVectorPosition.class);
   }

   public IVectorPosition a(StringReader var0) throws CommandSyntaxException {
      int var1 = var0.getCursor();
      if (!var0.canRead()) {
         throw a.createWithContext(var0);
      } else {
         ArgumentParserPosition var2 = ArgumentParserPosition.a(var0, false);
         if (var0.canRead() && var0.peek() == ' ') {
            var0.skip();
            ArgumentParserPosition var3 = ArgumentParserPosition.a(var0, false);
            return new VectorPosition(var3, var2, new ArgumentParserPosition(true, 0.0));
         } else {
            var0.setCursor(var1);
            throw a.createWithContext(var0);
         }
      }
   }

   public Collection<String> getExamples() {
      return b;
   }
}
