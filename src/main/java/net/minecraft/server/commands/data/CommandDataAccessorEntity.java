package net.minecraft.server.commands.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.advancements.critereon.CriterionConditionNBT;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentNBTKey;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;

public class CommandDataAccessorEntity implements CommandDataAccessor {
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.data.entity.invalid"));
   public static final Function<String, CommandData.c> a = var0 -> new CommandData.c() {
         @Override
         public CommandDataAccessor a(CommandContext<CommandListenerWrapper> var0x) throws CommandSyntaxException {
            return new CommandDataAccessorEntity(ArgumentEntity.a(var0, var0));
         }

         @Override
         public ArgumentBuilder<CommandListenerWrapper, ?> a(
            ArgumentBuilder<CommandListenerWrapper, ?> var0x,
            Function<ArgumentBuilder<CommandListenerWrapper, ?>, ArgumentBuilder<CommandListenerWrapper, ?>> var1
         ) {
            return var0.then(CommandDispatcher.a("entity").then((ArgumentBuilder)var1.apply(CommandDispatcher.a(var0, ArgumentEntity.a()))));
         }
      };
   private final Entity c;

   public CommandDataAccessorEntity(Entity var0) {
      this.c = var0;
   }

   @Override
   public void a(NBTTagCompound var0) throws CommandSyntaxException {
      if (this.c instanceof EntityHuman) {
         throw b.create();
      } else {
         UUID var1 = this.c.cs();
         this.c.g(var0);
         this.c.a_(var1);
      }
   }

   @Override
   public NBTTagCompound a() {
      return CriterionConditionNBT.b(this.c);
   }

   @Override
   public IChatBaseComponent b() {
      return IChatBaseComponent.a("commands.data.entity.modified", this.c.G_());
   }

   @Override
   public IChatBaseComponent a(NBTBase var0) {
      return IChatBaseComponent.a("commands.data.entity.query", this.c.G_(), GameProfileSerializer.c(var0));
   }

   @Override
   public IChatBaseComponent a(ArgumentNBTKey.g var0, double var1, int var3) {
      return IChatBaseComponent.a("commands.data.entity.get", var0, this.c.G_(), String.format(Locale.ROOT, "%.2f", var1), var3);
   }
}
