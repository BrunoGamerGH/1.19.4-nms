package net.minecraft.world.entity.boss.enderdragon.phases;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEnderDragon;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.slf4j.Logger;

public class DragonControllerManager {
   private static final Logger a = LogUtils.getLogger();
   private final EntityEnderDragon b;
   private final IDragonController[] c = new IDragonController[DragonControllerPhase.c()];
   @Nullable
   private IDragonController d;

   public DragonControllerManager(EntityEnderDragon entityenderdragon) {
      this.b = entityenderdragon;
      this.a(DragonControllerPhase.k);
   }

   public void a(DragonControllerPhase<?> dragoncontrollerphase) {
      if (this.d == null || dragoncontrollerphase != this.d.i()) {
         if (this.d != null) {
            this.d.e();
         }

         EnderDragonChangePhaseEvent event = new EnderDragonChangePhaseEvent(
            (CraftEnderDragon)this.b.getBukkitEntity(),
            this.d == null ? null : CraftEnderDragon.getBukkitPhase(this.d.i()),
            CraftEnderDragon.getBukkitPhase(dragoncontrollerphase)
         );
         this.b.H.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return;
         }

         dragoncontrollerphase = CraftEnderDragon.getMinecraftPhase(event.getNewPhase());
         this.d = this.b(dragoncontrollerphase);
         if (!this.b.H.B) {
            this.b.aj().b(EntityEnderDragon.b, dragoncontrollerphase.b());
         }

         a.debug("Dragon is now in phase {} on the {}", dragoncontrollerphase, this.b.H.B ? "client" : "server");
         this.d.d();
      }
   }

   public IDragonController a() {
      return this.d;
   }

   public <T extends IDragonController> T b(DragonControllerPhase<T> dragoncontrollerphase) {
      int i = dragoncontrollerphase.b();
      if (this.c[i] == null) {
         this.c[i] = dragoncontrollerphase.a(this.b);
      }

      return (T)this.c[i];
   }
}
