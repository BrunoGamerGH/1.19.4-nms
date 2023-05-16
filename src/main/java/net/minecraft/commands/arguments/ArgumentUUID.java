package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;

public class ArgumentUUID implements ArgumentType<UUID> {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.uuid.invalid"));
   private static final Collection<String> b = Arrays.asList("dd12be42-52a9-4a91-a8a1-11c01849e498");
   private static final Pattern c = Pattern.compile("^([-A-Fa-f0-9]+)");

   public static UUID a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (UUID)var0.getArgument(var1, UUID.class);
   }

   public static ArgumentUUID a() {
      return new ArgumentUUID();
   }

   public UUID a(StringReader var0) throws CommandSyntaxException {
      String var1 = var0.getRemaining();
      Matcher var2 = c.matcher(var1);
      if (var2.find()) {
         String var3 = var2.group(1);

         try {
            UUID var4 = UUID.fromString(var3);
            var0.setCursor(var0.getCursor() + var3.length());
            return var4;
         } catch (IllegalArgumentException var6) {
         }
      }

      throw a.create();
   }

   public Collection<String> getExamples() {
      return b;
   }
}
