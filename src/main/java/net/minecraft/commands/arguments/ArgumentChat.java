package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.CommandSigningContext;
import net.minecraft.commands.arguments.selector.ArgumentParserSelector;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.FilteredText;

public class ArgumentChat implements SignedArgument<ArgumentChat.a> {
   private static final Collection<String> a = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

   public static ArgumentChat a() {
      return new ArgumentChat();
   }

   public static IChatBaseComponent a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      ArgumentChat.a var2 = (ArgumentChat.a)var0.getArgument(var1, ArgumentChat.a.class);
      return var2.a((CommandListenerWrapper)var0.getSource());
   }

   public static void a(CommandContext<CommandListenerWrapper> var0, String var1, Consumer<PlayerChatMessage> var2) throws CommandSyntaxException {
      ArgumentChat.a var3 = (ArgumentChat.a)var0.getArgument(var1, ArgumentChat.a.class);
      CommandListenerWrapper var4 = (CommandListenerWrapper)var0.getSource();
      IChatBaseComponent var5 = var3.a(var4);
      CommandSigningContext var6 = var4.n();
      PlayerChatMessage var7 = var6.a(var1);
      if (var7 != null) {
         a(var2, var4, var7.a(var5));
      } else {
         b(var2, var4, PlayerChatMessage.a(var3.a).a(var5));
      }
   }

   private static void a(Consumer<PlayerChatMessage> var0, CommandListenerWrapper var1, PlayerChatMessage var2) {
      MinecraftServer var3 = var1.l();
      CompletableFuture<FilteredText> var4 = a(var1, var2);
      CompletableFuture<IChatBaseComponent> var5 = var3.bg().decorate(var1.i(), var2.c());
      var1.o().append(var4x -> CompletableFuture.allOf(var4, var5).thenAcceptAsync(var4xx -> {
            PlayerChatMessage var5x = var2.a(var5.join()).a(var4.join().e());
            var0.accept(var5x);
         }, var4x));
   }

   private static void b(Consumer<PlayerChatMessage> var0, CommandListenerWrapper var1, PlayerChatMessage var2) {
      MinecraftServer var3 = var1.l();
      CompletableFuture<IChatBaseComponent> var4 = var3.bg().decorate(var1.i(), var2.c());
      var1.o().append(var3x -> var4.thenAcceptAsync(var2xx -> var0.accept(var2.a(var2xx)), var3x));
   }

   private static CompletableFuture<FilteredText> a(CommandListenerWrapper var0, PlayerChatMessage var1) {
      EntityPlayer var2 = var0.i();
      return var2 != null && var1.a(var2.cs()) ? var2.T().a(var1.b()) : CompletableFuture.completedFuture(FilteredText.a(var1.b()));
   }

   public ArgumentChat.a a(StringReader var0) throws CommandSyntaxException {
      return ArgumentChat.a.a(var0, true);
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static class a {
      final String a;
      private final ArgumentChat.b[] b;

      public a(String var0, ArgumentChat.b[] var1) {
         this.a = var0;
         this.b = var1;
      }

      public String a() {
         return this.a;
      }

      public ArgumentChat.b[] b() {
         return this.b;
      }

      IChatBaseComponent a(CommandListenerWrapper var0) throws CommandSyntaxException {
         return this.a(var0, var0.c(2));
      }

      public IChatBaseComponent a(CommandListenerWrapper var0, boolean var1) throws CommandSyntaxException {
         if (this.b.length != 0 && var1) {
            IChatMutableComponent var2 = IChatBaseComponent.b(this.a.substring(0, this.b[0].a()));
            int var3 = this.b[0].a();

            for(ArgumentChat.b var7 : this.b) {
               IChatBaseComponent var8 = var7.a(var0);
               if (var3 < var7.a()) {
                  var2.f(this.a.substring(var3, var7.a()));
               }

               if (var8 != null) {
                  var2.b(var8);
               }

               var3 = var7.b();
            }

            if (var3 < this.a.length()) {
               var2.f(this.a.substring(var3));
            }

            return var2;
         } else {
            return IChatBaseComponent.b(this.a);
         }
      }

      public static ArgumentChat.a a(StringReader var0, boolean var1) throws CommandSyntaxException {
         String var2 = var0.getString().substring(var0.getCursor(), var0.getTotalLength());
         if (!var1) {
            var0.setCursor(var0.getTotalLength());
            return new ArgumentChat.a(var2, new ArgumentChat.b[0]);
         } else {
            List<ArgumentChat.b> var3 = Lists.newArrayList();
            int var4 = var0.getCursor();

            while(true) {
               int var5;
               EntitySelector var6;
               while(true) {
                  if (!var0.canRead()) {
                     return new ArgumentChat.a(var2, var3.toArray(new ArgumentChat.b[0]));
                  }

                  if (var0.peek() == '@') {
                     var5 = var0.getCursor();

                     try {
                        ArgumentParserSelector var7 = new ArgumentParserSelector(var0);
                        var6 = var7.t();
                        break;
                     } catch (CommandSyntaxException var8) {
                        if (var8.getType() != ArgumentParserSelector.h && var8.getType() != ArgumentParserSelector.f) {
                           throw var8;
                        }

                        var0.setCursor(var5 + 1);
                     }
                  } else {
                     var0.skip();
                  }
               }

               var3.add(new ArgumentChat.b(var5 - var4, var0.getCursor() - var4, var6));
            }
         }
      }
   }

   public static class b {
      private final int a;
      private final int b;
      private final EntitySelector c;

      public b(int var0, int var1, EntitySelector var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public int a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public EntitySelector c() {
         return this.c;
      }

      @Nullable
      public IChatBaseComponent a(CommandListenerWrapper var0) throws CommandSyntaxException {
         return EntitySelector.a(this.c.b(var0));
      }
   }
}
