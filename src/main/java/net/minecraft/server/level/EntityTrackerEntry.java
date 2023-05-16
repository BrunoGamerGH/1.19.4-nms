package net.minecraft.server.level;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport;
import net.minecraft.network.protocol.game.PacketPlayOutEntityVelocity;
import net.minecraft.network.protocol.game.PacketPlayOutMount;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateAttributes;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemWorldMap;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import org.slf4j.Logger;

public class EntityTrackerEntry {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 1;
   private final WorldServer c;
   private final Entity d;
   private final int e;
   private final boolean f;
   private final Consumer<Packet<?>> g;
   private final VecDeltaCodec h = new VecDeltaCodec();
   private int i;
   private int j;
   private int k;
   private Vec3D l;
   private int m;
   private int n;
   private List<Entity> o;
   private boolean p;
   private boolean q;
   @Nullable
   private List<DataWatcher.b<?>> r;
   private final Set<ServerPlayerConnection> trackedPlayers;

   public EntityTrackerEntry(
      WorldServer worldserver, Entity entity, int i, boolean flag, Consumer<Packet<?>> consumer, Set<ServerPlayerConnection> trackedPlayers
   ) {
      this.trackedPlayers = trackedPlayers;
      this.l = Vec3D.b;
      this.o = Collections.emptyList();
      this.c = worldserver;
      this.g = consumer;
      this.d = entity;
      this.e = i;
      this.f = flag;
      this.h.e(entity.df());
      this.i = MathHelper.d(entity.dw() * 256.0F / 360.0F);
      this.j = MathHelper.d(entity.dy() * 256.0F / 360.0F);
      this.k = MathHelper.d(entity.ck() * 256.0F / 360.0F);
      this.q = entity.ax();
      this.r = entity.aj().c();
   }

   public void a() {
      List<Entity> list = this.d.cM();
      if (!list.equals(this.o)) {
         this.a(new PacketPlayOutMount(this.d));
         this.a(list, this.o).forEach(entityx -> {
            if (entityx instanceof EntityPlayer entityplayer && !list.contains(entityplayer)) {
               entityplayer.b.a(entityplayer.dl(), entityplayer.dn(), entityplayer.dr(), entityplayer.dw(), entityplayer.dy());
            }
         });
         this.o = list;
      }

      Entity entity = this.d;
      if (entity instanceof EntityItemFrame entityitemframe) {
         ItemStack itemstack = entityitemframe.y();
         if (this.m % 10 == 0 && itemstack.c() instanceof ItemWorldMap) {
            Integer integer = ItemWorldMap.d(itemstack);
            WorldMap worldmap = ItemWorldMap.a(integer, this.c);
            if (worldmap != null) {
               Iterator<ServerPlayerConnection> iterator = this.trackedPlayers.iterator();

               while(iterator.hasNext()) {
                  EntityPlayer entityplayer = iterator.next().f();
                  worldmap.a(entityplayer, itemstack);
                  Packet<?> packet = worldmap.a(integer, entityplayer);
                  if (packet != null) {
                     entityplayer.b.a(packet);
                  }
               }
            }
         }

         this.b();
      }

      if (this.m % this.e == 0 || this.d.at || this.d.aj().a()) {
         if (this.d.bL()) {
            int i = MathHelper.d(this.d.dw() * 256.0F / 360.0F);
            int j = MathHelper.d(this.d.dy() * 256.0F / 360.0F);
            boolean flag = Math.abs(i - this.i) >= 1 || Math.abs(j - this.j) >= 1;
            if (flag) {
               this.g.accept(new PacketPlayOutEntity.PacketPlayOutEntityLook(this.d.af(), (byte)i, (byte)j, this.d.ax()));
               this.i = i;
               this.j = j;
            }

            this.h.e(this.d.df());
            this.b();
            this.p = true;
         } else {
            ++this.n;
            int i = MathHelper.d(this.d.dw() * 256.0F / 360.0F);
            int j = MathHelper.d(this.d.dy() * 256.0F / 360.0F);
            Vec3D vec3d = this.d.df();
            boolean flag1 = this.h.d(vec3d).g() >= 7.6293945E-6F;
            Packet<?> packet1 = null;
            boolean flag2 = flag1 || this.m % 60 == 0;
            boolean flag3 = Math.abs(i - this.i) >= 1 || Math.abs(j - this.j) >= 1;
            boolean flag4 = false;
            boolean flag5 = false;
            if (this.m > 0 || this.d instanceof EntityArrow) {
               long k = this.h.a(vec3d);
               long l = this.h.b(vec3d);
               long i1 = this.h.c(vec3d);
               boolean flag6 = k < -32768L || k > 32767L || l < -32768L || l > 32767L || i1 < -32768L || i1 > 32767L;
               if (flag6 || this.n > 400 || this.p || this.q != this.d.ax()) {
                  this.q = this.d.ax();
                  this.n = 0;
                  packet1 = new PacketPlayOutEntityTeleport(this.d);
                  flag4 = true;
                  flag5 = true;
               } else if ((!flag2 || !flag3) && !(this.d instanceof EntityArrow)) {
                  if (flag2) {
                     packet1 = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(this.d.af(), (short)((int)k), (short)((int)l), (short)((int)i1), this.d.ax());
                     flag4 = true;
                  } else if (flag3) {
                     packet1 = new PacketPlayOutEntity.PacketPlayOutEntityLook(this.d.af(), (byte)i, (byte)j, this.d.ax());
                     flag5 = true;
                  }
               } else {
                  packet1 = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(
                     this.d.af(), (short)((int)k), (short)((int)l), (short)((int)i1), (byte)i, (byte)j, this.d.ax()
                  );
                  flag4 = true;
                  flag5 = true;
               }
            }

            if ((this.f || this.d.at || this.d instanceof EntityLiving && ((EntityLiving)this.d).fn()) && this.m > 0) {
               Vec3D vec3d1 = this.d.dj();
               double d0 = vec3d1.g(this.l);
               if (d0 > 1.0E-7 || d0 > 0.0 && vec3d1.g() == 0.0) {
                  this.l = vec3d1;
                  this.g.accept(new PacketPlayOutEntityVelocity(this.d.af(), this.l));
               }
            }

            if (packet1 != null) {
               this.g.accept(packet1);
            }

            this.b();
            if (flag4) {
               this.h.e(vec3d);
            }

            if (flag5) {
               this.i = i;
               this.j = j;
            }

            this.p = false;
         }

         int var21 = MathHelper.d(this.d.ck() * 256.0F / 360.0F);
         if (Math.abs(var21 - this.k) >= 1) {
            this.g.accept(new PacketPlayOutEntityHeadRotation(this.d, (byte)var21));
            this.k = var21;
         }

         this.d.at = false;
      }

      ++this.m;
      if (this.d.S) {
         boolean cancelled = false;
         if (this.d instanceof EntityPlayer) {
            Player player = (Player)this.d.getBukkitEntity();
            Vector velocity = player.getVelocity();
            PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity.clone());
            this.d.H.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               cancelled = true;
            } else if (!velocity.equals(event.getVelocity())) {
               player.setVelocity(event.getVelocity());
            }
         }

         if (!cancelled) {
            this.a(new PacketPlayOutEntityVelocity(this.d));
         }

         this.d.S = false;
      }
   }

   private Stream<Entity> a(List<Entity> list, List<Entity> list1) {
      return Stream.concat(list1.stream().filter(entity -> !list.contains(entity)), list.stream().filter(entity -> !list1.contains(entity)));
   }

   public void a(EntityPlayer entityplayer) {
      this.d.d(entityplayer);
      entityplayer.b.a(new PacketPlayOutEntityDestroy(this.d.af()));
   }

   public void b(EntityPlayer entityplayer) {
      List<Packet<PacketListenerPlayOut>> list = new ArrayList<>();
      this.sendPairingData(list::add, entityplayer);
      entityplayer.b.a(new ClientboundBundlePacket(list));
      this.d.c(entityplayer);
   }

   public void sendPairingData(Consumer<Packet<PacketListenerPlayOut>> consumer, EntityPlayer entityplayer) {
      if (!this.d.dB()) {
         Packet<PacketListenerPlayOut> packet = this.d.S();
         this.k = MathHelper.d(this.d.ck() * 256.0F / 360.0F);
         consumer.accept(packet);
         if (this.r != null) {
            consumer.accept(new PacketPlayOutEntityMetadata(this.d.af(), this.r));
         }

         boolean flag = this.f;
         if (this.d instanceof EntityLiving) {
            Collection<AttributeModifiable> collection = ((EntityLiving)this.d).eI().b();
            if (this.d.af() == entityplayer.af()) {
               ((EntityPlayer)this.d).getBukkitEntity().injectScaledMaxHealth(collection, false);
            }

            if (!collection.isEmpty()) {
               consumer.accept(new PacketPlayOutUpdateAttributes(this.d.af(), collection));
            }

            if (((EntityLiving)this.d).fn()) {
               flag = true;
            }
         }

         this.l = this.d.dj();
         if (flag && !(this.d instanceof EntityLiving)) {
            consumer.accept(new PacketPlayOutEntityVelocity(this.d.af(), this.l));
         }

         if (this.d instanceof EntityLiving) {
            List<Pair<EnumItemSlot, ItemStack>> list = Lists.newArrayList();

            for(EnumItemSlot enumitemslot : EnumItemSlot.values()) {
               ItemStack itemstack = ((EntityLiving)this.d).c(enumitemslot);
               if (!itemstack.b()) {
                  list.add(Pair.of(enumitemslot, itemstack.o()));
               }
            }

            if (!list.isEmpty()) {
               consumer.accept(new PacketPlayOutEntityEquipment(this.d.af(), list));
            }

            ((EntityLiving)this.d).z();
         }

         this.k = MathHelper.d(this.d.ck() * 256.0F / 360.0F);
         consumer.accept(new PacketPlayOutEntityHeadRotation(this.d, (byte)this.k));
         if (this.d instanceof EntityLiving entityliving) {
            for(MobEffect mobeffect : entityliving.el()) {
               consumer.accept(new PacketPlayOutEntityEffect(this.d.af(), mobeffect));
            }
         }

         if (!this.d.cM().isEmpty()) {
            consumer.accept(new PacketPlayOutMount(this.d));
         }

         if (this.d.bL()) {
            consumer.accept(new PacketPlayOutMount(this.d.cV()));
         }

         if (this.d instanceof EntityInsentient entityinsentient && entityinsentient.fI()) {
            consumer.accept(new PacketPlayOutAttachEntity(entityinsentient, entityinsentient.fJ()));
         }
      }
   }

   private void b() {
      DataWatcher datawatcher = this.d.aj();
      List<DataWatcher.b<?>> list = datawatcher.b();
      if (list != null) {
         this.r = datawatcher.c();
         this.a(new PacketPlayOutEntityMetadata(this.d.af(), list));
      }

      if (this.d instanceof EntityLiving) {
         Set<AttributeModifiable> set = ((EntityLiving)this.d).eI().a();
         if (!set.isEmpty()) {
            if (this.d instanceof EntityPlayer) {
               ((EntityPlayer)this.d).getBukkitEntity().injectScaledMaxHealth(set, false);
            }

            this.a(new PacketPlayOutUpdateAttributes(this.d.af(), set));
         }

         set.clear();
      }
   }

   private void a(Packet<?> packet) {
      this.g.accept(packet);
      if (this.d instanceof EntityPlayer) {
         ((EntityPlayer)this.d).b.a(packet);
      }
   }
}
