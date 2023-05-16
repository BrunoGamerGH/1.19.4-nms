package net.minecraft.network.protocol.game;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;

public class PacketPlayOutTileEntityData implements Packet<PacketListenerPlayOut> {
   private final BlockPosition a;
   private final TileEntityTypes<?> b;
   @Nullable
   private final NBTTagCompound c;

   public static PacketPlayOutTileEntityData a(TileEntity var0, Function<TileEntity, NBTTagCompound> var1) {
      return new PacketPlayOutTileEntityData(var0.p(), var0.u(), var1.apply(var0));
   }

   public static PacketPlayOutTileEntityData a(TileEntity var0) {
      return a(var0, TileEntity::aq_);
   }

   private PacketPlayOutTileEntityData(BlockPosition var0, TileEntityTypes<?> var1, NBTTagCompound var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2.g() ? null : var2;
   }

   public PacketPlayOutTileEntityData(PacketDataSerializer var0) {
      this.a = var0.f();
      this.b = var0.a(BuiltInRegistries.l);
      this.c = var0.p();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(BuiltInRegistries.l, this.b);
      var0.a(this.c);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.a;
   }

   public TileEntityTypes<?> c() {
      return this.b;
   }

   @Nullable
   public NBTTagCompound d() {
      return this.c;
   }
}
