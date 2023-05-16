package net.minecraft.commands.arguments;

import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.selector.ArgumentParserSelector;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;

public class ArgumentEntity implements ArgumentType<EntitySelector> {
   private static final Collection<String> g = Arrays.asList("Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.toomany"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.player.toomany"));
   public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.player.entities"));
   public static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.notfound.entity"));
   public static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.notfound.player"));
   public static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.selector.not_allowed"));
   final boolean h;
   final boolean i;

   protected ArgumentEntity(boolean flag, boolean flag1) {
      this.h = flag;
      this.i = flag1;
   }

   public static ArgumentEntity a() {
      return new ArgumentEntity(true, false);
   }

   public static Entity a(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
      return ((EntitySelector)commandcontext.getArgument(s, EntitySelector.class)).a((CommandListenerWrapper)commandcontext.getSource());
   }

   public static ArgumentEntity b() {
      return new ArgumentEntity(false, false);
   }

   public static Collection<? extends Entity> b(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
      Collection<? extends Entity> collection = c(commandcontext, s);
      if (collection.isEmpty()) {
         throw d.create();
      } else {
         return collection;
      }
   }

   public static Collection<? extends Entity> c(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
      return ((EntitySelector)commandcontext.getArgument(s, EntitySelector.class)).b((CommandListenerWrapper)commandcontext.getSource());
   }

   public static Collection<EntityPlayer> d(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
      return ((EntitySelector)commandcontext.getArgument(s, EntitySelector.class)).d((CommandListenerWrapper)commandcontext.getSource());
   }

   public static ArgumentEntity c() {
      return new ArgumentEntity(true, true);
   }

   public static EntityPlayer e(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
      return ((EntitySelector)commandcontext.getArgument(s, EntitySelector.class)).c((CommandListenerWrapper)commandcontext.getSource());
   }

   public static ArgumentEntity d() {
      return new ArgumentEntity(false, true);
   }

   public static Collection<EntityPlayer> f(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
      List<EntityPlayer> list = ((EntitySelector)commandcontext.getArgument(s, EntitySelector.class)).d((CommandListenerWrapper)commandcontext.getSource());
      if (list.isEmpty()) {
         throw e.create();
      } else {
         return list;
      }
   }

   public EntitySelector a(StringReader stringreader) throws CommandSyntaxException {
      return this.parse(stringreader, false);
   }

   public EntitySelector parse(StringReader stringreader, boolean overridePermissions) throws CommandSyntaxException {
      boolean flag = false;
      ArgumentParserSelector argumentparserselector = new ArgumentParserSelector(stringreader);
      EntitySelector entityselector = argumentparserselector.parse(overridePermissions);
      if (entityselector.a() > 1 && this.h) {
         if (this.i) {
            stringreader.setCursor(0);
            throw b.createWithContext(stringreader);
         } else {
            stringreader.setCursor(0);
            throw a.createWithContext(stringreader);
         }
      } else if (entityselector.b() && this.i && !entityselector.c()) {
         stringreader.setCursor(0);
         throw c.createWithContext(stringreader);
      } else {
         return entityselector;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandcontext, SuggestionsBuilder suggestionsbuilder) {
      Object object = commandcontext.getSource();
      if (object instanceof ICompletionProvider icompletionprovider) {
         StringReader stringreader = new StringReader(suggestionsbuilder.getInput());
         stringreader.setCursor(suggestionsbuilder.getStart());
         ArgumentParserSelector argumentparserselector = new ArgumentParserSelector(stringreader, icompletionprovider.c(2));

         try {
            argumentparserselector.t();
         } catch (CommandSyntaxException var8) {
         }

         return argumentparserselector.a(suggestionsbuilder, suggestionsbuilder1 -> {
            Collection<String> collection = icompletionprovider.p();
            Iterable<String> iterable = (Iterable<String>)(this.i ? collection : Iterables.concat(collection, icompletionprovider.x()));
            ICompletionProvider.b(iterable, suggestionsbuilder1);
         });
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return g;
   }

   public static class Info implements ArgumentTypeInfo<ArgumentEntity, ArgumentEntity.Info.Template> {
      private static final byte a = 1;
      private static final byte b = 2;

      public void a(ArgumentEntity.Info.Template argumententity_info_template, PacketDataSerializer packetdataserializer) {
         int i = 0;
         if (argumententity_info_template.b) {
            i |= 1;
         }

         if (argumententity_info_template.c) {
            i |= 2;
         }

         packetdataserializer.writeByte(i);
      }

      public ArgumentEntity.Info.Template a(PacketDataSerializer packetdataserializer) {
         byte b0 = packetdataserializer.readByte();
         return new ArgumentEntity.Info.Template((b0 & 1) != 0, (b0 & 2) != 0);
      }

      public void a(ArgumentEntity.Info.Template argumententity_info_template, JsonObject jsonobject) {
         jsonobject.addProperty("amount", argumententity_info_template.b ? "single" : "multiple");
         jsonobject.addProperty("type", argumententity_info_template.c ? "players" : "entities");
      }

      public ArgumentEntity.Info.Template a(ArgumentEntity argumententity) {
         return new ArgumentEntity.Info.Template(argumententity.h, argumententity.i);
      }

      public final class Template implements ArgumentTypeInfo.a<ArgumentEntity> {
         final boolean b;
         final boolean c;

         Template(boolean flag, boolean flag1) {
            this.b = flag;
            this.c = flag1;
         }

         public ArgumentEntity a(CommandBuildContext commandbuildcontext) {
            return new ArgumentEntity(this.b, this.c);
         }

         @Override
         public ArgumentTypeInfo<ArgumentEntity, ?> a() {
            return Info.this;
         }
      }
   }
}
