package net.minecraft.world.entity;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public interface IEntityAngerable {
   String a_ = "AngerTime";
   String b_ = "AngryAt";

   int a();

   void a(int var1);

   @Nullable
   UUID b();

   void a(@Nullable UUID var1);

   void c();

   default void c(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("AngerTime", this.a());
      if (this.b() != null) {
         nbttagcompound.a("AngryAt", this.b());
      }
   }

   default void a(World world, NBTTagCompound nbttagcompound) {
      this.a(nbttagcompound.h("AngerTime"));
      if (world instanceof WorldServer) {
         if (!nbttagcompound.b("AngryAt")) {
            this.a(null);
         } else {
            UUID uuid = nbttagcompound.a("AngryAt");
            this.a(uuid);
            Entity entity = ((WorldServer)world).a(uuid);
            if (entity != null) {
               if (entity instanceof EntityInsentient) {
                  this.a((EntityInsentient)entity);
               }

               if (entity.ae() == EntityTypes.bt) {
                  this.c((EntityHuman)entity);
               }
            }
         }
      }
   }

   default void a(WorldServer worldserver, boolean flag) {
      EntityLiving entityliving = this.P_();
      UUID uuid = this.b();
      if ((entityliving == null || entityliving.ep()) && uuid != null && worldserver.a(uuid) instanceof EntityInsentient) {
         this.N_();
      } else {
         if (entityliving != null && !Objects.equals(uuid, entityliving.cs())) {
            this.a(entityliving.cs());
            this.c();
         }

         if (this.a() > 0 && (entityliving == null || entityliving.ae() != EntityTypes.bt || !flag)) {
            this.a(this.a() - 1);
            if (this.a() == 0) {
               this.N_();
            }
         }
      }
   }

   default boolean a_(EntityLiving entityliving) {
      return !this.c(entityliving) ? false : (entityliving.ae() == EntityTypes.bt && this.b(entityliving.H) ? true : entityliving.cs().equals(this.b()));
   }

   default boolean b(World world) {
      return world.W().b(GameRules.K) && this.R_() && this.b() == null;
   }

   default boolean R_() {
      return this.a() > 0;
   }

   default void a_(EntityHuman entityhuman) {
      if (entityhuman.H.W().b(GameRules.J) && entityhuman.cs().equals(this.b())) {
         this.N_();
      }
   }

   default void S_() {
      this.N_();
      this.c();
   }

   default void N_() {
      this.a(null);
      this.a(null);
      this.setTarget(null, TargetReason.FORGOT_TARGET, true);
      this.a(0);
   }

   @Nullable
   EntityLiving ea();

   void a(@Nullable EntityLiving var1);

   void c(@Nullable EntityHuman var1);

   void i(@Nullable EntityLiving var1);

   boolean setTarget(@Nullable EntityLiving var1, TargetReason var2, boolean var3);

   boolean c(EntityLiving var1);

   @Nullable
   EntityLiving P_();
}
