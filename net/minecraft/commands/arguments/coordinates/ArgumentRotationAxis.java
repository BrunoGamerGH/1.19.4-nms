package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.IChatBaseComponent;

public class ArgumentRotationAxis implements ArgumentType<EnumSet<EnumDirection.EnumAxis>> {
   private static final Collection<String> a = Arrays.asList("xyz", "x");
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("arguments.swizzle.invalid"));

   public static ArgumentRotationAxis a() {
      return new ArgumentRotationAxis();
   }

   public static EnumSet<EnumDirection.EnumAxis> a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (EnumSet<EnumDirection.EnumAxis>)var0.getArgument(var1, EnumSet.class);
   }

   public EnumSet<EnumDirection.EnumAxis> a(StringReader var0) throws CommandSyntaxException {
      EnumSet<EnumDirection.EnumAxis> var1 = EnumSet.noneOf(EnumDirection.EnumAxis.class);

      while(var0.canRead() && var0.peek() != ' ') {
         char var2 = var0.read();

         EnumDirection.EnumAxis var3 = switch(var2) {
            case 'x' -> EnumDirection.EnumAxis.a;
            case 'y' -> EnumDirection.EnumAxis.b;
            case 'z' -> EnumDirection.EnumAxis.c;
            default -> throw b.create();
         };
         if (var1.contains(var3)) {
            throw b.create();
         }

         var1.add(var3);
      }

      return var1;
   }

   public Collection<String> getExamples() {
      return a;
   }
}
