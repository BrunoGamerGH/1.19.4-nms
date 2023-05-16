package net.minecraft.world.level.block.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.ChestLock;
import net.minecraft.world.INamableTileEntity;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerBeacon;
import net.minecraft.world.inventory.IContainerProperties;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IBeaconBeam;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.potion.PotionEffect;

public class TileEntityBeacon extends TileEntity implements ITileInventory, INamableTileEntity {
   private static final int f = 4;
   public static final MobEffectList[][] a = new MobEffectList[][]{{MobEffects.a, MobEffects.c}, {MobEffects.k, MobEffects.h}, {MobEffects.e}, {MobEffects.j}};
   private static final Set<MobEffectList> g = Arrays.stream(a).flatMap(Arrays::stream).collect(Collectors.toSet());
   public static final int b = 0;
   public static final int c = 1;
   public static final int d = 2;
   public static final int e = 3;
   private static final int h = 10;
   private static final IChatBaseComponent i = IChatBaseComponent.c("container.beacon");
   List<TileEntityBeacon.BeaconColorTracker> j = Lists.newArrayList();
   private List<TileEntityBeacon.BeaconColorTracker> k = Lists.newArrayList();
   public int l;
   private int m;
   @Nullable
   public MobEffectList n;
   @Nullable
   public MobEffectList r;
   @Nullable
   public IChatBaseComponent s;
   public ChestLock t = ChestLock.a;
   private final IContainerProperties u = new IContainerProperties() {
      @Override
      public int a(int i) {
         return switch(i) {
            case 0 -> TileEntityBeacon.this.l;
            case 1 -> MobEffectList.b(TileEntityBeacon.this.n);
            case 2 -> MobEffectList.b(TileEntityBeacon.this.r);
            default -> 0;
         };
      }

      @Override
      public void a(int i, int j) {
         switch(i) {
            case 0:
               TileEntityBeacon.this.l = j;
               break;
            case 1:
               if (!TileEntityBeacon.this.o.B && !TileEntityBeacon.this.j.isEmpty()) {
                  TileEntityBeacon.a(TileEntityBeacon.this.o, TileEntityBeacon.this.p, SoundEffects.bx);
               }

               TileEntityBeacon.this.n = TileEntityBeacon.a(j);
               break;
            case 2:
               TileEntityBeacon.this.r = TileEntityBeacon.a(j);
         }
      }

      @Override
      public int a() {
         return 3;
      }
   };

   public PotionEffect getPrimaryEffect() {
      return this.n != null ? CraftPotionUtil.toBukkit(new MobEffect(this.n, getLevel(this.l), getAmplification(this.l, this.n, this.r), true, true)) : null;
   }

   public PotionEffect getSecondaryEffect() {
      return hasSecondaryEffect(this.l, this.n, this.r)
         ? CraftPotionUtil.toBukkit(new MobEffect(this.r, getLevel(this.l), getAmplification(this.l, this.n, this.r), true, true))
         : null;
   }

   public TileEntityBeacon(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.o, blockposition, iblockdata);
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityBeacon tileentitybeacon) {
      int i = blockposition.u();
      int j = blockposition.v();
      int k = blockposition.w();
      BlockPosition blockposition1;
      if (tileentitybeacon.m < j) {
         blockposition1 = blockposition;
         tileentitybeacon.k = Lists.newArrayList();
         tileentitybeacon.m = blockposition.v() - 1;
      } else {
         blockposition1 = new BlockPosition(i, tileentitybeacon.m + 1, k);
      }

      TileEntityBeacon.BeaconColorTracker tileentitybeacon_beaconcolortracker = tileentitybeacon.k.isEmpty()
         ? null
         : tileentitybeacon.k.get(tileentitybeacon.k.size() - 1);
      int l = world.a(HeightMap.Type.b, i, k);

      for(int i1 = 0; i1 < 10 && blockposition1.v() <= l; ++i1) {
         IBlockData iblockdata1 = world.a_(blockposition1);
         Block block = iblockdata1.b();
         if (block instanceof IBeaconBeam) {
            float[] afloat = ((IBeaconBeam)block).a().d();
            if (tileentitybeacon.k.size() <= 1) {
               tileentitybeacon_beaconcolortracker = new TileEntityBeacon.BeaconColorTracker(afloat);
               tileentitybeacon.k.add(tileentitybeacon_beaconcolortracker);
            } else if (tileentitybeacon_beaconcolortracker != null) {
               if (Arrays.equals(afloat, tileentitybeacon_beaconcolortracker.a)) {
                  tileentitybeacon_beaconcolortracker.a();
               } else {
                  tileentitybeacon_beaconcolortracker = new TileEntityBeacon.BeaconColorTracker(
                     new float[]{
                        (tileentitybeacon_beaconcolortracker.a[0] + afloat[0]) / 2.0F,
                        (tileentitybeacon_beaconcolortracker.a[1] + afloat[1]) / 2.0F,
                        (tileentitybeacon_beaconcolortracker.a[2] + afloat[2]) / 2.0F
                     }
                  );
                  tileentitybeacon.k.add(tileentitybeacon_beaconcolortracker);
               }
            }
         } else {
            if (tileentitybeacon_beaconcolortracker == null || iblockdata1.b((IBlockAccess)world, blockposition1) >= 15 && !iblockdata1.a(Blocks.F)) {
               tileentitybeacon.k.clear();
               tileentitybeacon.m = l;
               break;
            }

            tileentitybeacon_beaconcolortracker.a();
         }

         blockposition1 = blockposition1.c();
         ++tileentitybeacon.m;
      }

      int var15 = tileentitybeacon.l;
      if (world.U() % 80L == 0L) {
         if (!tileentitybeacon.j.isEmpty()) {
            tileentitybeacon.l = a(world, i, j, k);
         }

         if (tileentitybeacon.l > 0 && !tileentitybeacon.j.isEmpty()) {
            a(world, blockposition, tileentitybeacon.l, tileentitybeacon.n, tileentitybeacon.r);
            a(world, blockposition, SoundEffects.bv);
         }
      }

      if (tileentitybeacon.m >= l) {
         tileentitybeacon.m = world.v_() - 1;
         boolean flag = var15 > 0;
         tileentitybeacon.j = tileentitybeacon.k;
         if (!world.B) {
            boolean flag1 = tileentitybeacon.l > 0;
            if (!flag && flag1) {
               a(world, blockposition, SoundEffects.bu);

               for(EntityPlayer entityplayer : world.a(
                  EntityPlayer.class, new AxisAlignedBB((double)i, (double)j, (double)k, (double)i, (double)(j - 4), (double)k).c(10.0, 5.0, 10.0)
               )) {
                  CriterionTriggers.l.a(entityplayer, tileentitybeacon.l);
               }
            } else if (flag && !flag1) {
               a(world, blockposition, SoundEffects.bw);
            }
         }
      }
   }

   private static int a(World world, int i, int j, int k) {
      int l = 0;

      for(int i1 = 1; i1 <= 4; l = i1++) {
         int j1 = j - i1;
         if (j1 < world.v_()) {
            break;
         }

         boolean flag = true;

         for(int k1 = i - i1; k1 <= i + i1 && flag; ++k1) {
            for(int l1 = k - i1; l1 <= k + i1; ++l1) {
               if (!world.a_(new BlockPosition(k1, j1, l1)).a(TagsBlock.aJ)) {
                  flag = false;
                  break;
               }
            }
         }

         if (!flag) {
            break;
         }
      }

      return l;
   }

   @Override
   public void ar_() {
      a(this.o, this.p, SoundEffects.bw);
      super.ar_();
   }

   private static byte getAmplification(int i, @Nullable MobEffectList mobeffectlist, @Nullable MobEffectList mobeffectlist1) {
      byte b0 = 0;
      if (i >= 4 && mobeffectlist == mobeffectlist1) {
         b0 = 1;
      }

      return b0;
   }

   private static int getLevel(int i) {
      return (9 + i * 2) * 20;
   }

   public static List getHumansInRange(World world, BlockPosition blockposition, int i) {
      double d0 = (double)(i * 10 + 10);
      AxisAlignedBB axisalignedbb = new AxisAlignedBB(blockposition).g(d0).b(0.0, (double)world.w_(), 0.0);
      return world.a(EntityHuman.class, axisalignedbb);
   }

   private static void applyEffect(List list, MobEffectList mobeffectlist, int j, int b0) {
      for(EntityHuman entityhuman : list) {
         entityhuman.addEffect(new MobEffect(mobeffectlist, j, b0, true, true), Cause.BEACON);
      }
   }

   private static boolean hasSecondaryEffect(int i, @Nullable MobEffectList mobeffectlist, @Nullable MobEffectList mobeffectlist1) {
      return i >= 4 && mobeffectlist != mobeffectlist1 && mobeffectlist1 != null;
   }

   private static void a(World world, BlockPosition blockposition, int i, @Nullable MobEffectList mobeffectlist, @Nullable MobEffectList mobeffectlist1) {
      if (!world.B && mobeffectlist != null) {
         double d0 = (double)(i * 10 + 10);
         byte b0 = getAmplification(i, mobeffectlist, mobeffectlist1);
         int j = getLevel(i);
         List list = getHumansInRange(world, blockposition, i);
         applyEffect(list, mobeffectlist, j, b0);
         if (hasSecondaryEffect(i, mobeffectlist, mobeffectlist1)) {
            applyEffect(list, mobeffectlist1, j, 0);
         }
      }
   }

   public static void a(World world, BlockPosition blockposition, SoundEffect soundeffect) {
      world.a(null, blockposition, soundeffect, SoundCategory.e, 1.0F, 1.0F);
   }

   public List<TileEntityBeacon.BeaconColorTracker> c() {
      return (List<TileEntityBeacon.BeaconColorTracker>)(this.l == 0 ? ImmutableList.of() : this.j);
   }

   public PacketPlayOutTileEntityData f() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   @Nullable
   static MobEffectList a(int i) {
      MobEffectList mobeffectlist = MobEffectList.a(i);
      return g.contains(mobeffectlist) ? mobeffectlist : null;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.n = MobEffectList.a(nbttagcompound.h("Primary"));
      this.r = MobEffectList.a(nbttagcompound.h("Secondary"));
      this.l = nbttagcompound.h("Levels");
      if (nbttagcompound.b("CustomName", 8)) {
         this.s = IChatBaseComponent.ChatSerializer.a(nbttagcompound.l("CustomName"));
      }

      this.t = ChestLock.b(nbttagcompound);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Primary", MobEffectList.b(this.n));
      nbttagcompound.a("Secondary", MobEffectList.b(this.r));
      nbttagcompound.a("Levels", this.l);
      if (this.s != null) {
         nbttagcompound.a("CustomName", IChatBaseComponent.ChatSerializer.a(this.s));
      }

      this.t.a(nbttagcompound);
   }

   public void a(@Nullable IChatBaseComponent ichatbasecomponent) {
      this.s = ichatbasecomponent;
   }

   @Nullable
   @Override
   public IChatBaseComponent ab() {
      return this.s;
   }

   @Nullable
   @Override
   public Container createMenu(int i, PlayerInventory playerinventory, EntityHuman entityhuman) {
      return TileEntityContainer.a(entityhuman, this.t, this.G_())
         ? new ContainerBeacon(i, playerinventory, this.u, ContainerAccess.a(this.o, this.p()))
         : null;
   }

   @Override
   public IChatBaseComponent G_() {
      return this.Z();
   }

   @Override
   public IChatBaseComponent Z() {
      return this.s != null ? this.s : i;
   }

   @Override
   public void a(World world) {
      super.a(world);
      this.m = world.v_() - 1;
   }

   public static class BeaconColorTracker {
      final float[] a;
      private int b;

      public BeaconColorTracker(float[] afloat) {
         this.a = afloat;
         this.b = 1;
      }

      protected void a() {
         ++this.b;
      }

      public float[] b() {
         return this.a;
      }

      public int c() {
         return this.b;
      }
   }
}
