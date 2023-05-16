package net.minecraft.world.level.block.entity;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.SculkCatalystBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class SculkCatalystBlockEntity extends TileEntity implements GameEventListener {
   private final BlockPositionSource a = new BlockPositionSource(this.p);
   private final SculkSpreader b = SculkSpreader.a();

   public SculkCatalystBlockEntity(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.J, blockposition, iblockdata);
   }

   @Override
   public PositionSource a() {
      return this.a;
   }

   @Override
   public int b() {
      return 8;
   }

   @Override
   public GameEventListener.a c() {
      return GameEventListener.a.b;
   }

   @Override
   public boolean a(WorldServer worldserver, GameEvent gameevent, GameEvent.a gameevent_a, Vec3D vec3d) {
      if (gameevent == GameEvent.q) {
         Entity entity = gameevent_a.a();
         if (entity instanceof EntityLiving entityliving) {
            if (!entityliving.ev()) {
               int i = entityliving.dX();
               if (entityliving.dV() && i > 0) {
                  this.b.a(BlockPosition.a(vec3d.a(EnumDirection.b, 0.5)), i);
                  this.a(entityliving);
               }

               entityliving.eu();
               SculkCatalystBlock.a(worldserver, this.p, this.q(), worldserver.r_());
            }

            return true;
         }
      }

      return false;
   }

   private void a(EntityLiving entityliving) {
      EntityLiving entityliving1 = entityliving.ea();
      if (entityliving1 instanceof EntityPlayer entityplayer) {
         DamageSource damagesource = entityliving.eq() == null ? this.o.af().a((EntityHuman)entityplayer) : entityliving.eq();
         CriterionTriggers.W.a(entityplayer, entityliving, damagesource);
      }
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, SculkCatalystBlockEntity sculkcatalystblockentity) {
      CraftEventFactory.sourceBlockOverride = sculkcatalystblockentity.p();
      sculkcatalystblockentity.b.a(world, blockposition, world.r_(), true);
      CraftEventFactory.sourceBlockOverride = null;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.b.a(nbttagcompound);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      this.b.b(nbttagcompound);
      super.b(nbttagcompound);
   }

   @VisibleForTesting
   public SculkSpreader d() {
      return this.b;
   }
}
