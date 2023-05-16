package net.minecraft.world.entity;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockFireAbstract;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityLightning extends Entity {
   private static final int c = 2;
   private static final double d = 3.0;
   private static final double e = 15.0;
   private int f;
   public long b;
   private int g;
   public boolean h;
   @Nullable
   private EntityPlayer i;
   private final Set<Entity> j = Sets.newHashSet();
   private int k;
   public boolean isSilent = false;

   public EntityLightning(EntityTypes<? extends EntityLightning> entitytypes, World world) {
      super(entitytypes, world);
      this.as = true;
      this.f = 2;
      this.b = this.af.g();
      this.g = this.af.a(3) + 1;
   }

   public void a(boolean flag) {
      this.h = flag;
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.d;
   }

   @Nullable
   public EntityPlayer i() {
      return this.i;
   }

   public void b(@Nullable EntityPlayer entityplayer) {
      this.i = entityplayer;
   }

   private void o() {
      BlockPosition blockposition = this.p();
      IBlockData iblockdata = this.H.a_(blockposition);
      if (iblockdata.a(Blocks.rm)) {
         ((LightningRodBlock)iblockdata.b()).d(iblockdata, this.H, blockposition);
      }
   }

   @Override
   public void l() {
      super.l();
      if (!this.isSilent && this.f == 2) {
         if (this.H.k_()) {
            this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.ml, SoundCategory.d, 10000.0F, 0.8F + this.af.i() * 0.2F, false);
            this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.mk, SoundCategory.d, 2.0F, 0.5F + this.af.i() * 0.2F, false);
         } else {
            EnumDifficulty enumdifficulty = this.H.ah();
            if (enumdifficulty == EnumDifficulty.c || enumdifficulty == EnumDifficulty.d) {
               this.b(4);
            }

            this.o();
            b(this.H, this.p());
            this.a(GameEvent.I);
         }
      }

      --this.f;
      if (this.f < 0) {
         if (this.g == 0) {
            if (this.H instanceof WorldServer) {
               List list = this.H
                  .a(
                     this,
                     new AxisAlignedBB(this.dl() - 15.0, this.dn() - 15.0, this.dr() - 15.0, this.dl() + 15.0, this.dn() + 6.0 + 15.0, this.dr() + 15.0),
                     entityx -> entityx.bq() && !this.j.contains(entityx)
                  );

               for(EntityPlayer entityplayer : ((WorldServer)this.H).a(entityplayer -> entityplayer.e(this) < 256.0F)) {
                  CriterionTriggers.S.a(entityplayer, this, list);
               }
            }

            this.ai();
         } else if (this.f < -this.af.a(10)) {
            --this.g;
            this.f = 1;
            this.b = this.af.g();
            this.b(0);
         }
      }

      if (this.f >= 0 && !this.h) {
         if (!(this.H instanceof WorldServer)) {
            this.H.c(2);
         } else if (!this.h) {
            List list = this.H
               .a(
                  this,
                  new AxisAlignedBB(this.dl() - 3.0, this.dn() - 3.0, this.dr() - 3.0, this.dl() + 3.0, this.dn() + 6.0 + 3.0, this.dr() + 3.0),
                  Entity::bq
               );

            for(Entity entity : list) {
               entity.a((WorldServer)this.H, this);
            }

            this.j.addAll(list);
            if (this.i != null) {
               CriterionTriggers.E.a(this.i, list);
            }
         }
      }
   }

   private BlockPosition p() {
      Vec3D vec3d = this.de();
      return BlockPosition.a(vec3d.c, vec3d.d - 1.0E-6, vec3d.e);
   }

   private void b(int i) {
      if (!this.h && !this.H.B && this.H.W().b(GameRules.b)) {
         BlockPosition blockposition = this.dg();
         IBlockData iblockdata = BlockFireAbstract.a(this.H, blockposition);
         if (this.H.a_(blockposition).h()
            && iblockdata.a((IWorldReader)this.H, blockposition)
            && !this.h
            && !CraftEventFactory.callBlockIgniteEvent(this.H, blockposition, this).isCancelled()) {
            this.H.b(blockposition, iblockdata);
            ++this.k;
         }

         for(int j = 0; j < i; ++j) {
            BlockPosition blockposition1 = blockposition.b(this.af.a(3) - 1, this.af.a(3) - 1, this.af.a(3) - 1);
            iblockdata = BlockFireAbstract.a(this.H, blockposition1);
            if (this.H.a_(blockposition1).h()
               && iblockdata.a((IWorldReader)this.H, blockposition1)
               && !this.h
               && !CraftEventFactory.callBlockIgniteEvent(this.H, blockposition1, this).isCancelled()) {
               this.H.b(blockposition1, iblockdata);
               ++this.k;
            }
         }
      }
   }

   private static void b(World world, BlockPosition blockposition) {
      IBlockData iblockdata = world.a_(blockposition);
      BlockPosition blockposition1;
      IBlockData iblockdata1;
      if (iblockdata.a(Blocks.rm)) {
         blockposition1 = blockposition.a(iblockdata.c(LightningRodBlock.a).g());
         iblockdata1 = world.a_(blockposition1);
      } else {
         blockposition1 = blockposition;
         iblockdata1 = iblockdata;
      }

      if (iblockdata1.b() instanceof WeatheringCopper) {
         world.b(blockposition1, WeatheringCopper.c(world.a_(blockposition1)));
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();
         int i = world.z.a(3) + 3;

         for(int j = 0; j < i; ++j) {
            int k = world.z.a(8) + 1;
            a(world, blockposition1, blockposition_mutableblockposition, k);
         }
      }
   }

   private static void a(World world, BlockPosition blockposition, BlockPosition.MutableBlockPosition blockposition_mutableblockposition, int i) {
      blockposition_mutableblockposition.g(blockposition);

      for(int j = 0; j < i; ++j) {
         Optional<BlockPosition> optional = c(world, blockposition_mutableblockposition);
         if (!optional.isPresent()) {
            break;
         }

         blockposition_mutableblockposition.g(optional.get());
      }
   }

   private static Optional<BlockPosition> c(World world, BlockPosition blockposition) {
      for(BlockPosition blockposition1 : BlockPosition.a(world.z, 10, blockposition, 1)) {
         IBlockData iblockdata = world.a_(blockposition1);
         if (iblockdata.b() instanceof WeatheringCopper) {
            WeatheringCopper.b(iblockdata).ifPresent(iblockdata1 -> world.b(blockposition1, iblockdata1));
            world.c(3002, blockposition1, -1);
            return Optional.of(blockposition1);
         }
      }

      return Optional.empty();
   }

   @Override
   public boolean a(double d0) {
      double d1 = 64.0 * cw();
      return d0 < d1 * d1;
   }

   @Override
   protected void a_() {
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
   }

   public int j() {
      return this.k;
   }

   public Stream<Entity> k() {
      return this.j.stream().filter(Entity::bq);
   }
}
