package net.minecraft.world.entity.decoration;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityLeash extends EntityHanging {
   public static final double e = 0.375;

   public EntityLeash(EntityTypes<? extends EntityLeash> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityLeash(World world, BlockPosition blockposition) {
      super(EntityTypes.ah, world, blockposition);
      this.e((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
   }

   @Override
   protected void r() {
      this.p((double)this.c.u() + 0.5, (double)this.c.v() + 0.375, (double)this.c.w() + 0.5);
      double d0 = (double)this.ae().k() / 2.0;
      double d1 = (double)this.ae().l();
      this.a(new AxisAlignedBB(this.dl() - d0, this.dn(), this.dr() - d0, this.dl() + d0, this.dn() + d1, this.dr() + d0));
   }

   @Override
   public void a(EnumDirection enumdirection) {
   }

   @Override
   public int t() {
      return 9;
   }

   @Override
   public int v() {
      return 9;
   }

   @Override
   protected float a(EntityPose entitypose, EntitySize entitysize) {
      return 0.0625F;
   }

   @Override
   public boolean a(double d0) {
      return d0 < 1024.0;
   }

   @Override
   public void a(@Nullable Entity entity) {
      this.a(SoundEffects.mh, 1.0F, 1.0F);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
   }

   @Override
   public EnumInteractionResult a(EntityHuman entityhuman, EnumHand enumhand) {
      if (this.H.B) {
         return EnumInteractionResult.a;
      } else {
         boolean flag = false;
         double d0 = 7.0;
         List<EntityInsentient> list = this.H
            .a(EntityInsentient.class, new AxisAlignedBB(this.dl() - 7.0, this.dn() - 7.0, this.dr() - 7.0, this.dl() + 7.0, this.dn() + 7.0, this.dr() + 7.0));

         for(EntityInsentient entityinsentient : list) {
            if (entityinsentient.fJ() == entityhuman) {
               if (CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, this, entityhuman, enumhand).isCancelled()) {
                  ((EntityPlayer)entityhuman).b.a(new PacketPlayOutAttachEntity(entityinsentient, entityinsentient.fJ()));
               } else {
                  entityinsentient.b(this, true);
                  flag = true;
               }
            }
         }

         boolean flag1 = false;
         if (!flag) {
            boolean die = true;

            for(EntityInsentient entityinsentient1 : list) {
               if (entityinsentient1.fI() && entityinsentient1.fJ() == this) {
                  if (CraftEventFactory.callPlayerUnleashEntityEvent(entityinsentient1, entityhuman, enumhand).isCancelled()) {
                     die = false;
                  } else {
                     entityinsentient1.a(true, !entityhuman.fK().d);
                     flag1 = true;
                  }
               }
            }

            if (die) {
               this.ai();
            }
         }

         if (flag || flag1) {
            this.a(GameEvent.b, entityhuman);
         }

         return EnumInteractionResult.b;
      }
   }

   @Override
   public boolean s() {
      return this.H.a_(this.c).a(TagsBlock.R);
   }

   public static EntityLeash b(World world, BlockPosition blockposition) {
      int i = blockposition.u();
      int j = blockposition.v();
      int k = blockposition.w();

      for(EntityLeash entityleash : world.a(
         EntityLeash.class, new AxisAlignedBB((double)i - 1.0, (double)j - 1.0, (double)k - 1.0, (double)i + 1.0, (double)j + 1.0, (double)k + 1.0)
      )) {
         if (entityleash.x().equals(blockposition)) {
            return entityleash;
         }
      }

      EntityLeash entityleash1 = new EntityLeash(world, blockposition);
      world.b(entityleash1);
      return entityleash1;
   }

   @Override
   public void w() {
      this.a(SoundEffects.mi, 1.0F, 1.0F);
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      return new PacketPlayOutSpawnEntity(this, 0, this.x());
   }

   @Override
   public Vec3D u(float f) {
      return this.p(f).b(0.0, 0.2, 0.0);
   }

   @Override
   public ItemStack dt() {
      return new ItemStack(Items.tM);
   }
}
