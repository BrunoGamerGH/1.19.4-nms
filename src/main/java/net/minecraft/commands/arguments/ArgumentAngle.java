package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.coordinates.ArgumentParserPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.MathHelper;

public class ArgumentAngle implements ArgumentType<ArgumentAngle.a> {
   private static final Collection<String> c = Arrays.asList("0", "~", "~-5");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.angle.incomplete"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.angle.invalid"));

   public static ArgumentAngle a() {
      return new ArgumentAngle();
   }

   public static float a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return ((ArgumentAngle.a)var0.getArgument(var1, ArgumentAngle.a.class)).a((CommandListenerWrapper)var0.getSource());
   }

   public ArgumentAngle.a a(StringReader var0) throws CommandSyntaxException {
      if (!var0.canRead()) {
         throw a.createWithContext(var0);
      } else {
         boolean var1 = ArgumentParserPosition.b(var0);
         float var2 = var0.canRead() && var0.peek() != ' ' ? var0.readFloat() : 0.0F;
         if (!Float.isNaN(var2) && !Float.isInfinite(var2)) {
            return new ArgumentAngle.a(var2, var1);
         } else {
            throw b.createWithContext(var0);
         }
      }
   }

   public Collection<String> getExamples() {
      return c;
   }

   public static final class a {
      private final float a;
      private final boolean b;

      a(float var0, boolean var1) {
         this.a = var0;
         this.b = var1;
      }

      public float a(CommandListenerWrapper var0) {
         return MathHelper.g(this.b ? this.a + var0.k().j : this.a);
      }
   }
}
