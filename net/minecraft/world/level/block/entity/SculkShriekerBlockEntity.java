package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.MathHelper;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.Vec3D;
import org.slf4j.Logger;

public class SculkShriekerBlockEntity extends TileEntity implements VibrationListener.a {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 8;
   private static final int c = 10;
   private static final int d = 20;
   private static final int e = 5;
   private static final int f = 6;
   private static final int g = 40;
   private static final Int2ObjectMap<SoundEffect> h = SystemUtils.a(new Int2ObjectOpenHashMap(), var0 -> {
      var0.put(1, SoundEffects.zh);
      var0.put(2, SoundEffects.zi);
      var0.put(3, SoundEffects.zj);
      var0.put(4, SoundEffects.zg);
   });
   private static final int i = 90;
   public int j;
   private VibrationListener k = new VibrationListener(new BlockPositionSource(this.p), 8, this);

   public SculkShriekerBlockEntity(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.K, var0, var1);
   }

   public VibrationListener c() {
      return this.k;
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      if (var0.b("warning_level", 99)) {
         this.j = var0.h("warning_level");
      }

      if (var0.b("listener", 10)) {
         VibrationListener.a(this).parse(new Dynamic(DynamicOpsNBT.a, var0.p("listener"))).resultOrPartial(a::error).ifPresent(var0x -> this.k = var0x);
      }
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("warning_level", this.j);
      VibrationListener.a(this).encodeStart(DynamicOpsNBT.a, this.k).resultOrPartial(a::error).ifPresent(var1x -> var0.a("listener", var1x));
   }

   @Override
   public TagKey<GameEvent> a() {
      return GameEventTags.c;
   }

   @Override
   public boolean a(WorldServer var0, GameEventListener var1, BlockPosition var2, GameEvent var3, GameEvent.a var4) {
      return !this.q().c(SculkShriekerBlock.a) && a(var4.a()) != null;
   }

   @Nullable
   public static EntityPlayer a(@Nullable Entity var0) {
      if (var0 instanceof EntityPlayer var1) {
         return var1;
      } else {
         if (var0 != null) {
            EntityLiving var2 = var0.cK();
            if (var2 instanceof EntityPlayer var1) {
               return var1;
            }
         }

         if (var0 instanceof IProjectile var1) {
            Entity var3 = var1.v();
            if (var3 instanceof EntityPlayer var2) {
               return var2;
            }
         }

         if (var0 instanceof EntityItem var1) {
            Entity var9 = var1.v();
            if (var9 instanceof EntityPlayer var2) {
               return var2;
            }
         }

         return null;
      }
   }

   @Override
   public void a(WorldServer var0, GameEventListener var1, BlockPosition var2, GameEvent var3, @Nullable Entity var4, @Nullable Entity var5, float var6) {
      this.a(var0, a(var5 != null ? var5 : var4));
   }

   public void a(WorldServer var0, @Nullable EntityPlayer var1) {
      if (var1 != null) {
         IBlockData var2 = this.q();
         if (!var2.c(SculkShriekerBlock.a)) {
            this.j = 0;
            if (!this.b(var0) || this.b(var0, var1)) {
               this.a(var0, (Entity)var1);
            }
         }
      }
   }

   private boolean b(WorldServer var0, EntityPlayer var1) {
      OptionalInt var2 = WardenSpawnTracker.a(var0, this.p(), var1);
      var2.ifPresent(var0x -> this.j = var0x);
      return var2.isPresent();
   }

   private void a(WorldServer var0, @Nullable Entity var1) {
      BlockPosition var2 = this.p();
      IBlockData var3 = this.q();
      var0.a(var2, var3.a(SculkShriekerBlock.a, Boolean.valueOf(true)), 2);
      var0.a(var2, var3.b(), 90);
      var0.c(3007, var2, 0);
      var0.a(GameEvent.R, var2, GameEvent.a.a(var1));
   }

   private boolean b(WorldServer var0) {
      return this.q().c(SculkShriekerBlock.c) && var0.ah() != EnumDifficulty.a && var0.W().b(GameRules.I);
   }

   public void a(WorldServer var0) {
      if (this.b(var0) && this.j > 0) {
         if (!this.c(var0)) {
            this.d();
         }

         Warden.a(var0, Vec3D.b(this.p()), null, 40);
      }
   }

   private void d() {
      SoundEffect var0 = (SoundEffect)h.get(this.j);
      if (var0 != null) {
         BlockPosition var1 = this.p();
         int var2 = var1.u() + MathHelper.b(this.o.z, -10, 10);
         int var3 = var1.v() + MathHelper.b(this.o.z, -10, 10);
         int var4 = var1.w() + MathHelper.b(this.o.z, -10, 10);
         this.o.a(null, (double)var2, (double)var3, (double)var4, var0, SoundCategory.f, 5.0F, 1.0F);
      }
   }

   private boolean c(WorldServer var0) {
      return this.j < 4 ? false : SpawnUtil.a(EntityTypes.bi, EnumMobSpawn.k, var0, this.p(), 20, 5, 6, SpawnUtil.a.b).isPresent();
   }

   @Override
   public void f() {
      this.e();
   }
}
