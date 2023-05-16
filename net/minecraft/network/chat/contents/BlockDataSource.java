package net.minecraft.network.chat.contents;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.commands.arguments.coordinates.IVectorPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.entity.TileEntity;

public record BlockDataSource(String posPattern, @Nullable IVectorPosition compiledPos) implements DataSource {
   private final String a;
   @Nullable
   private final IVectorPosition b;

   public BlockDataSource(String var0) {
      this(var0, a(var0));
   }

   public BlockDataSource(String var0, @Nullable IVectorPosition var1) {
      this.a = var0;
      this.b = var1;
   }

   @Nullable
   private static IVectorPosition a(String var0) {
      try {
         return ArgumentPosition.a().a(new StringReader(var0));
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   @Override
   public Stream<NBTTagCompound> getData(CommandListenerWrapper var0) {
      if (this.b != null) {
         WorldServer var1 = var0.e();
         BlockPosition var2 = this.b.c(var0);
         if (var1.o(var2)) {
            TileEntity var3 = var1.c_(var2);
            if (var3 != null) {
               return Stream.of(var3.m());
            }
         }
      }

      return Stream.empty();
   }

   @Override
   public String toString() {
      return "block=" + this.a;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof BlockDataSource var1 && this.a.equals(var1.a)) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }
}
