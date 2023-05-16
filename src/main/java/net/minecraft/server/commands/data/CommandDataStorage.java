package net.minecraft.server.commands.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.commands.arguments.ArgumentNBTKey;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.storage.PersistentCommandStorage;

public class CommandDataStorage implements CommandDataAccessor {
   static final SuggestionProvider<CommandListenerWrapper> b = (var0, var1) -> ICompletionProvider.a(a(var0).a(), var1);
   public static final Function<String, CommandData.c> a = var0 -> new CommandData.c() {
         @Override
         public CommandDataAccessor a(CommandContext<CommandListenerWrapper> var0x) {
            return new CommandDataStorage(CommandDataStorage.a(var0), ArgumentMinecraftKeyRegistered.e(var0, var0));
         }

         @Override
         public ArgumentBuilder<CommandListenerWrapper, ?> a(
            ArgumentBuilder<CommandListenerWrapper, ?> var0x,
            Function<ArgumentBuilder<CommandListenerWrapper, ?>, ArgumentBuilder<CommandListenerWrapper, ?>> var1
         ) {
            return var0.then(
               CommandDispatcher.a("storage")
                  .then((ArgumentBuilder)var1.apply(CommandDispatcher.a(var0, ArgumentMinecraftKeyRegistered.a()).suggests(CommandDataStorage.b)))
            );
         }
      };
   private final PersistentCommandStorage c;
   private final MinecraftKey d;

   static PersistentCommandStorage a(CommandContext<CommandListenerWrapper> var0) {
      return ((CommandListenerWrapper)var0.getSource()).l().aG();
   }

   CommandDataStorage(PersistentCommandStorage var0, MinecraftKey var1) {
      this.c = var0;
      this.d = var1;
   }

   @Override
   public void a(NBTTagCompound var0) {
      this.c.a(this.d, var0);
   }

   @Override
   public NBTTagCompound a() {
      return this.c.a(this.d);
   }

   @Override
   public IChatBaseComponent b() {
      return IChatBaseComponent.a("commands.data.storage.modified", this.d);
   }

   @Override
   public IChatBaseComponent a(NBTBase var0) {
      return IChatBaseComponent.a("commands.data.storage.query", this.d, GameProfileSerializer.c(var0));
   }

   @Override
   public IChatBaseComponent a(ArgumentNBTKey.g var0, double var1, int var3) {
      return IChatBaseComponent.a("commands.data.storage.get", var0, this.d, String.format(Locale.ROOT, "%.2f", var1), var3);
   }
}
