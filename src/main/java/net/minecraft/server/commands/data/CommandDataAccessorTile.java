package net.minecraft.server.commands.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentNBTKey;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;

public class CommandDataAccessorTile implements CommandDataAccessor {
   static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.data.block.invalid"));
   public static final Function<String, CommandData.c> a = var0 -> new CommandData.c() {
         @Override
         public CommandDataAccessor a(CommandContext<CommandListenerWrapper> var0x) throws CommandSyntaxException {
            BlockPosition var1 = ArgumentPosition.a(var0, var0 + "Pos");
            TileEntity var2 = ((CommandListenerWrapper)var0.getSource()).e().c_(var1);
            if (var2 == null) {
               throw CommandDataAccessorTile.b.create();
            } else {
               return new CommandDataAccessorTile(var2, var1);
            }
         }

         @Override
         public ArgumentBuilder<CommandListenerWrapper, ?> a(
            ArgumentBuilder<CommandListenerWrapper, ?> var0x,
            Function<ArgumentBuilder<CommandListenerWrapper, ?>, ArgumentBuilder<CommandListenerWrapper, ?>> var1
         ) {
            return var0.then(CommandDispatcher.a("block").then((ArgumentBuilder)var1.apply(CommandDispatcher.a(var0 + "Pos", ArgumentPosition.a()))));
         }
      };
   private final TileEntity c;
   private final BlockPosition d;

   public CommandDataAccessorTile(TileEntity var0, BlockPosition var1) {
      this.c = var0;
      this.d = var1;
   }

   @Override
   public void a(NBTTagCompound var0) {
      IBlockData var1 = this.c.k().a_(this.d);
      this.c.a(var0);
      this.c.e();
      this.c.k().a(this.d, var1, var1, 3);
   }

   @Override
   public NBTTagCompound a() {
      return this.c.m();
   }

   @Override
   public IChatBaseComponent b() {
      return IChatBaseComponent.a("commands.data.block.modified", this.d.u(), this.d.v(), this.d.w());
   }

   @Override
   public IChatBaseComponent a(NBTBase var0) {
      return IChatBaseComponent.a("commands.data.block.query", this.d.u(), this.d.v(), this.d.w(), GameProfileSerializer.c(var0));
   }

   @Override
   public IChatBaseComponent a(ArgumentNBTKey.g var0, double var1, int var3) {
      return IChatBaseComponent.a("commands.data.block.get", var0, this.d.u(), this.d.v(), this.d.w(), String.format(Locale.ROOT, "%.2f", var1), var3);
   }
}
