package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;

public class ArgumentChatComponent implements ArgumentType<IChatBaseComponent> {
   private static final Collection<String> b = Arrays.asList("\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.component.invalid", var0));

   private ArgumentChatComponent() {
   }

   public static IChatBaseComponent a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (IChatBaseComponent)var0.getArgument(var1, IChatBaseComponent.class);
   }

   public static ArgumentChatComponent a() {
      return new ArgumentChatComponent();
   }

   public IChatBaseComponent a(StringReader var0) throws CommandSyntaxException {
      try {
         IChatBaseComponent var1 = IChatBaseComponent.ChatSerializer.a(var0);
         if (var1 == null) {
            throw a.createWithContext(var0, "empty");
         } else {
            return var1;
         }
      } catch (Exception var4) {
         String var2 = var4.getCause() != null ? var4.getCause().getMessage() : var4.getMessage();
         throw a.createWithContext(var0, var2);
      }
   }

   public Collection<String> getExamples() {
      return b;
   }
}
