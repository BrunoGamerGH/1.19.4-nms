package net.minecraft.network.chat.contents;

import java.util.stream.Stream;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;

public record StorageDataSource(MinecraftKey id) implements DataSource {
   private final MinecraftKey a;

   public StorageDataSource(MinecraftKey var0) {
      this.a = var0;
   }

   @Override
   public Stream<NBTTagCompound> getData(CommandListenerWrapper var0) {
      NBTTagCompound var1 = var0.l().aG().a(this.a);
      return Stream.of(var1);
   }

   @Override
   public String toString() {
      return "storage=" + this.a;
   }
}
