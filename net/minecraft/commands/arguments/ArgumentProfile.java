package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.selector.ArgumentParserSelector;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;

public class ArgumentProfile implements ArgumentType<ArgumentProfile.a> {
   private static final Collection<String> b = Arrays.asList("Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@e");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.player.unknown"));

   public static Collection<GameProfile> a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return ((ArgumentProfile.a)var0.getArgument(var1, ArgumentProfile.a.class)).getNames((CommandListenerWrapper)var0.getSource());
   }

   public static ArgumentProfile a() {
      return new ArgumentProfile();
   }

   public ArgumentProfile.a a(StringReader var0) throws CommandSyntaxException {
      if (var0.canRead() && var0.peek() == '@') {
         ArgumentParserSelector var1 = new ArgumentParserSelector(var0);
         EntitySelector var2 = var1.t();
         if (var2.b()) {
            throw ArgumentEntity.c.create();
         } else {
            return new ArgumentProfile.b(var2);
         }
      } else {
         int var1 = var0.getCursor();

         while(var0.canRead() && var0.peek() != ' ') {
            var0.skip();
         }

         String var2 = var0.getString().substring(var1, var0.getCursor());
         return var1x -> {
            Optional<GameProfile> var2x = var1x.l().ap().a(var2);
            return Collections.singleton((GameProfile)var2x.orElseThrow(a::create));
         };
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      if (var0.getSource() instanceof ICompletionProvider) {
         StringReader var2 = new StringReader(var1.getInput());
         var2.setCursor(var1.getStart());
         ArgumentParserSelector var3 = new ArgumentParserSelector(var2);

         try {
            var3.t();
         } catch (CommandSyntaxException var6) {
         }

         return var3.a(var1, var1x -> ICompletionProvider.b(((ICompletionProvider)var0.getSource()).p(), var1x));
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return b;
   }

   @FunctionalInterface
   public interface a {
      Collection<GameProfile> getNames(CommandListenerWrapper var1) throws CommandSyntaxException;
   }

   public static class b implements ArgumentProfile.a {
      private final EntitySelector a;

      public b(EntitySelector var0) {
         this.a = var0;
      }

      @Override
      public Collection<GameProfile> getNames(CommandListenerWrapper var0) throws CommandSyntaxException {
         List<EntityPlayer> var1 = this.a.d(var0);
         if (var1.isEmpty()) {
            throw ArgumentEntity.e.create();
         } else {
            List<GameProfile> var2 = Lists.newArrayList();

            for(EntityPlayer var4 : var1) {
               var2.add(var4.fI());
            }

            return var2;
         }
      }
   }
}
