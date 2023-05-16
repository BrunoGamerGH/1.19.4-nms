package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import org.slf4j.Logger;

public class SculkSensorBlockEntity extends TileEntity implements VibrationListener.a {
   private static final Logger a = LogUtils.getLogger();
   private VibrationListener b;
   public int c;

   public SculkSensorBlockEntity(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.I, var0, var1);
      this.b = new VibrationListener(new BlockPositionSource(this.p), ((SculkSensorBlock)var1.b()).d(), this);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.c = var0.h("last_vibration_frequency");
      if (var0.b("listener", 10)) {
         VibrationListener.a(this).parse(new Dynamic(DynamicOpsNBT.a, var0.p("listener"))).resultOrPartial(a::error).ifPresent(var0x -> this.b = var0x);
      }
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("last_vibration_frequency", this.c);
      VibrationListener.a(this).encodeStart(DynamicOpsNBT.a, this.b).resultOrPartial(a::error).ifPresent(var1x -> var0.a("listener", var1x));
   }

   public VibrationListener c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   @Override
   public boolean w() {
      return true;
   }

   @Override
   public boolean a(WorldServer var0, GameEventListener var1, BlockPosition var2, GameEvent var3, @Nullable GameEvent.a var4) {
      return !var2.equals(this.p()) || var3 != GameEvent.f && var3 != GameEvent.i ? SculkSensorBlock.n(this.q()) : false;
   }

   @Override
   public void a(WorldServer var0, GameEventListener var1, BlockPosition var2, GameEvent var3, @Nullable Entity var4, @Nullable Entity var5, float var6) {
      IBlockData var7 = this.q();
      if (SculkSensorBlock.n(var7)) {
         this.c = VibrationListener.a(var3);
         SculkSensorBlock.a(var4, var0, this.p, var7, a(var6, var1.b()));
      }
   }

   @Override
   public void f() {
      this.e();
   }

   public static int a(float var0, int var1) {
      double var2 = (double)var0 / (double)var1;
      return Math.max(1, 15 - MathHelper.a(var2 * 15.0));
   }

   public void a(int var0) {
      this.c = var0;
   }
}
