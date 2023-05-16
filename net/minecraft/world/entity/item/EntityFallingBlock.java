package net.minecraft.world.entity.item;

import com.mojang.logging.LogUtils;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContextDirectional;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockAnvil;
import net.minecraft.world.level.block.BlockConcretePowder;
import net.minecraft.world.level.block.BlockFalling;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.slf4j.Logger;

public class EntityFallingBlock extends Entity {
   private static final Logger f = LogUtils.getLogger();
   private IBlockData g = Blocks.I.o();
   public int b;
   public boolean c = true;
   private boolean h;
   public boolean i;
   private int j = 40;
   private float k;
   @Nullable
   public NBTTagCompound d;
   protected static final DataWatcherObject<BlockPosition> e = DataWatcher.a(EntityFallingBlock.class, DataWatcherRegistry.n);

   public EntityFallingBlock(EntityTypes<? extends EntityFallingBlock> entitytypes, World world) {
      super(entitytypes, world);
   }

   private EntityFallingBlock(World world, double d0, double d1, double d2, IBlockData iblockdata) {
      this(EntityTypes.L, world);
      this.g = iblockdata;
      this.F = true;
      this.e(d0, d1, d2);
      this.f(Vec3D.b);
      this.I = d0;
      this.J = d1;
      this.K = d2;
      this.a(this.dg());
   }

   public static EntityFallingBlock a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      return fall(world, blockposition, iblockdata, SpawnReason.DEFAULT);
   }

   public static EntityFallingBlock fall(World world, BlockPosition blockposition, IBlockData iblockdata, SpawnReason spawnReason) {
      EntityFallingBlock entityfallingblock = new EntityFallingBlock(
         world,
         (double)blockposition.u() + 0.5,
         (double)blockposition.v(),
         (double)blockposition.w() + 0.5,
         iblockdata.b(BlockProperties.C) ? iblockdata.a(BlockProperties.C, Boolean.valueOf(false)) : iblockdata
      );
      if (CraftEventFactory.callEntityChangeBlockEvent(entityfallingblock, blockposition, iblockdata.r().g()).isCancelled()) {
         return entityfallingblock;
      } else {
         world.a(blockposition, iblockdata.r().g(), 3);
         world.addFreshEntity(entityfallingblock, spawnReason);
         return entityfallingblock;
      }
   }

   @Override
   public boolean cl() {
      return false;
   }

   public void a(BlockPosition blockposition) {
      this.am.b(e, blockposition);
   }

   public BlockPosition i() {
      return this.am.a(e);
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.a;
   }

   @Override
   protected void a_() {
      this.am.a(e, BlockPosition.b);
   }

   @Override
   public boolean bm() {
      return !this.dB();
   }

   @Override
   public void l() {
      if (this.g.h()) {
         this.ai();
      } else {
         Block block = this.g.b();
         ++this.b;
         if (!this.aP()) {
            this.f(this.dj().b(0.0, -0.04, 0.0));
         }

         this.a(EnumMoveType.a, this.dj());
         if (!this.H.B) {
            BlockPosition blockposition = this.dg();
            boolean flag = this.g.b() instanceof BlockConcretePowder;
            boolean flag1 = flag && this.H.b_(blockposition).a(TagsFluid.a);
            double d0 = this.dj().g();
            if (flag && d0 > 1.0) {
               MovingObjectPositionBlock movingobjectpositionblock = this.H
                  .a(new RayTrace(new Vec3D(this.I, this.J, this.K), this.de(), RayTrace.BlockCollisionOption.a, RayTrace.FluidCollisionOption.b, this));
               if (movingobjectpositionblock.c() != MovingObjectPosition.EnumMovingObjectType.a && this.H.b_(movingobjectpositionblock.a()).a(TagsFluid.a)) {
                  blockposition = movingobjectpositionblock.a();
                  flag1 = true;
               }
            }

            if (!this.N && !flag1) {
               if (!this.H.B && (this.b > 100 && (blockposition.v() <= this.H.v_() || blockposition.v() > this.H.ai()) || this.b > 600)) {
                  if (this.c && this.H.W().b(GameRules.h)) {
                     this.a(block);
                  }

                  this.ai();
               }
            } else {
               IBlockData iblockdata = this.H.a_(blockposition);
               this.f(this.dj().d(0.7, -0.5, 0.7));
               if (!iblockdata.a(Blocks.bP)) {
                  if (this.h) {
                     this.ai();
                     this.a(block, blockposition);
                  } else {
                     boolean flag2 = iblockdata.a(new BlockActionContextDirectional(this.H, blockposition, EnumDirection.a, ItemStack.b, EnumDirection.b));
                     boolean flag3 = BlockFalling.h(this.H.a_(blockposition.d())) && (!flag || !flag1);
                     boolean flag4 = this.g.a((IWorldReader)this.H, blockposition) && !flag3;
                     if (flag2 && flag4) {
                        if (this.g.b(BlockProperties.C) && this.H.b_(blockposition).a() == FluidTypes.c) {
                           this.g = this.g.a(BlockProperties.C, Boolean.valueOf(true));
                        }

                        if (CraftEventFactory.callEntityChangeBlockEvent(this, blockposition, this.g).isCancelled()) {
                           this.ai();
                           return;
                        }

                        if (this.H.a(blockposition, this.g, 3)) {
                           ((WorldServer)this.H).k().a.a(this, new PacketPlayOutBlockChange(blockposition, this.H.a_(blockposition)));
                           this.ai();
                           if (block instanceof Fallable) {
                              ((Fallable)block).a(this.H, blockposition, this.g, iblockdata, this);
                           }

                           if (this.d != null && this.g.q()) {
                              TileEntity tileentity = this.H.c_(blockposition);
                              if (tileentity != null) {
                                 NBTTagCompound nbttagcompound = tileentity.o();

                                 for(String s : this.d.e()) {
                                    nbttagcompound.a(s, this.d.c(s).d());
                                 }

                                 try {
                                    tileentity.a(nbttagcompound);
                                 } catch (Exception var15) {
                                    f.error("Failed to load block entity from falling block", var15);
                                 }

                                 tileentity.e();
                              }
                           }
                        } else if (this.c && this.H.W().b(GameRules.h)) {
                           this.ai();
                           this.a(block, blockposition);
                           this.a(block);
                        }
                     } else {
                        this.ai();
                        if (this.c && this.H.W().b(GameRules.h)) {
                           this.a(block, blockposition);
                           this.a(block);
                        }
                     }
                  }
               }
            }
         }

         this.f(this.dj().a(0.98));
      }
   }

   public void a(Block block, BlockPosition blockposition) {
      if (block instanceof Fallable) {
         ((Fallable)block).a(this.H, blockposition, this);
      }
   }

   @Override
   public boolean a(float f, float f1, DamageSource damagesource) {
      if (!this.i) {
         return false;
      } else {
         int i = MathHelper.f(f - 1.0F);
         if (i < 0) {
            return false;
         } else {
            Predicate<Entity> predicate;
            DamageSource damagesource1;
            if (this.g.b() instanceof Fallable fallable) {
               predicate = fallable.al_();
               damagesource1 = fallable.a(this);
            } else {
               predicate = IEntitySelector.f;
               damagesource1 = this.dG().a(this);
            }

            float f2 = (float)Math.min(MathHelper.d((float)i * this.k), this.j);
            this.H.a(this, this.cD(), predicate).forEach(entity -> {
               CraftEventFactory.entityDamage = this;
               entity.a(damagesource1, f2);
               CraftEventFactory.entityDamage = null;
            });
            boolean flag = this.g.a(TagsBlock.L);
            if (flag && f2 > 0.0F && this.af.i() < 0.05F + (float)i * 0.05F) {
               IBlockData iblockdata = BlockAnvil.e(this.g);
               if (iblockdata == null) {
                  this.h = true;
               } else {
                  this.g = iblockdata;
               }
            }

            return false;
         }
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("BlockState", GameProfileSerializer.a(this.g));
      nbttagcompound.a("Time", this.b);
      nbttagcompound.a("DropItem", this.c);
      nbttagcompound.a("HurtEntities", this.i);
      nbttagcompound.a("FallHurtAmount", this.k);
      nbttagcompound.a("FallHurtMax", this.j);
      if (this.d != null) {
         nbttagcompound.a("TileEntityData", this.d);
      }
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      this.g = GameProfileSerializer.a(this.H.a(Registries.e), nbttagcompound.p("BlockState"));
      this.b = nbttagcompound.h("Time");
      if (nbttagcompound.b("HurtEntities", 99)) {
         this.i = nbttagcompound.q("HurtEntities");
         this.k = nbttagcompound.j("FallHurtAmount");
         this.j = nbttagcompound.h("FallHurtMax");
      } else if (this.g.a(TagsBlock.L)) {
         this.i = true;
      }

      if (nbttagcompound.b("DropItem", 99)) {
         this.c = nbttagcompound.q("DropItem");
      }

      if (nbttagcompound.b("TileEntityData", 10)) {
         this.d = nbttagcompound.p("TileEntityData");
      }

      if (this.g.h()) {
         this.g = Blocks.I.o();
      }
   }

   public void b(float f, int i) {
      this.i = true;
      this.k = f;
      this.j = i;
   }

   public void j() {
      this.h = true;
   }

   @Override
   public boolean cr() {
      return false;
   }

   @Override
   public void a(CrashReportSystemDetails crashreportsystemdetails) {
      super.a(crashreportsystemdetails);
      crashreportsystemdetails.a("Immitating BlockState", this.g.toString());
   }

   public IBlockData k() {
      return this.g;
   }

   @Override
   protected IChatBaseComponent cj() {
      return IChatBaseComponent.a("entity.minecraft.falling_block_type", this.g.b().f());
   }

   @Override
   public boolean cJ() {
      return true;
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      return new PacketPlayOutSpawnEntity(this, Block.i(this.k()));
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      this.g = Block.a(packetplayoutspawnentity.n());
      this.F = true;
      double d0 = packetplayoutspawnentity.e();
      double d1 = packetplayoutspawnentity.f();
      double d2 = packetplayoutspawnentity.g();
      this.e(d0, d1, d2);
      this.a(this.dg());
   }
}
