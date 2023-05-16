package net.minecraft.world.entity;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityDamageEvent;
import org.slf4j.Logger;

public class Interaction extends Entity implements Attackable, Targeting {
   private static final Logger b = LogUtils.getLogger();
   private static final DataWatcherObject<Float> c = DataWatcher.a(Interaction.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Float> d = DataWatcher.a(Interaction.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Boolean> e = DataWatcher.a(Interaction.class, DataWatcherRegistry.k);
   private static final String f = "width";
   private static final String g = "height";
   private static final String h = "attack";
   private static final String i = "interaction";
   private static final String j = "response";
   @Nullable
   public Interaction.PlayerAction k;
   @Nullable
   public Interaction.PlayerAction l;

   public Interaction(EntityTypes<?> entitytypes, World world) {
      super(entitytypes, world);
      this.ae = true;
   }

   @Override
   protected void a_() {
      this.am.a(c, 1.0F);
      this.am.a(d, 1.0F);
      this.am.a(e, false);
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      if (nbttagcompound.b("width", 99)) {
         this.a(nbttagcompound.j("width"));
      }

      if (nbttagcompound.b("height", 99)) {
         this.b(nbttagcompound.j("height"));
      }

      if (nbttagcompound.e("attack")) {
         DataResult<Pair<Interaction.PlayerAction, NBTBase>> dataresult = Interaction.PlayerAction.a.decode(DynamicOpsNBT.a, nbttagcompound.c("attack"));
         Logger logger = b;
         dataresult.resultOrPartial(SystemUtils.a("Interaction entity", logger::error)).ifPresent(pair -> this.k = (Interaction.PlayerAction)pair.getFirst());
      } else {
         this.k = null;
      }

      if (nbttagcompound.e("interaction")) {
         DataResult<Pair<Interaction.PlayerAction, NBTBase>> dataresult = Interaction.PlayerAction.a.decode(DynamicOpsNBT.a, nbttagcompound.c("interaction"));
         Logger logger = b;
         dataresult.resultOrPartial(SystemUtils.a("Interaction entity", logger::error)).ifPresent(pair -> this.l = (Interaction.PlayerAction)pair.getFirst());
      } else {
         this.l = null;
      }

      this.a(nbttagcompound.q("response"));
      this.a(this.am());
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("width", this.j());
      nbttagcompound.a("height", this.k());
      if (this.k != null) {
         Interaction.PlayerAction.a.encodeStart(DynamicOpsNBT.a, this.k).result().ifPresent(nbtbase -> nbttagcompound.a("attack", nbtbase));
      }

      if (this.l != null) {
         Interaction.PlayerAction.a.encodeStart(DynamicOpsNBT.a, this.l).result().ifPresent(nbtbase -> nbttagcompound.a("interaction", nbtbase));
      }

      nbttagcompound.a("response", this.o());
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      super.a(datawatcherobject);
      if (d.equals(datawatcherobject) || c.equals(datawatcherobject)) {
         this.a(this.am());
      }
   }

   @Override
   public boolean bl() {
      return false;
   }

   @Override
   public boolean bm() {
      return true;
   }

   @Override
   public EnumPistonReaction C_() {
      return EnumPistonReaction.d;
   }

   @Override
   public boolean r(Entity entity) {
      if (entity instanceof EntityHuman entityhuman) {
         DamageSource source = entityhuman.dG().a(entityhuman);
         EntityDamageEvent event = CraftEventFactory.callNonLivingEntityDamageEvent(this, source, 1.0, false);
         if (event.isCancelled()) {
            return true;
         } else {
            this.k = new Interaction.PlayerAction(entityhuman.cs(), this.H.U());
            if (entityhuman instanceof EntityPlayer entityplayer) {
               CriterionTriggers.g.a(entityplayer, this, source, (float)event.getFinalDamage(), 1.0F, false);
            }

            return !this.o();
         }
      } else {
         return false;
      }
   }

   @Override
   public EnumInteractionResult a(EntityHuman entityhuman, EnumHand enumhand) {
      if (this.H.B) {
         return this.o() ? EnumInteractionResult.a : EnumInteractionResult.b;
      } else {
         this.l = new Interaction.PlayerAction(entityhuman.cs(), this.H.U());
         return EnumInteractionResult.b;
      }
   }

   @Override
   public void l() {
   }

   @Nullable
   @Override
   public EntityLiving L_() {
      return this.k != null ? this.H.b(this.k.a()) : null;
   }

   @Nullable
   @Override
   public EntityLiving P_() {
      return this.l != null ? this.H.b(this.l.a()) : null;
   }

   public void a(float f) {
      this.am.b(c, f);
   }

   public float j() {
      return this.am.a(c);
   }

   public void b(float f) {
      this.am.b(d, f);
   }

   public float k() {
      return this.am.a(d);
   }

   public void a(boolean flag) {
      this.am.b(e, flag);
   }

   public boolean o() {
      return this.am.a(e);
   }

   private EntitySize p() {
      return EntitySize.b(this.j(), this.k());
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      return this.p();
   }

   @Override
   protected AxisAlignedBB am() {
      return this.p().a(this.de());
   }

   public static record PlayerAction(UUID player, long timestamp) {
      private final UUID b;
      private final long c;
      public static final Codec<Interaction.PlayerAction> a = RecordCodecBuilder.create(
         instance -> instance.group(
                  UUIDUtil.a.fieldOf("player").forGetter(Interaction.PlayerAction::a), Codec.LONG.fieldOf("timestamp").forGetter(Interaction.PlayerAction::b)
               )
               .apply(instance, Interaction.PlayerAction::new)
      );

      public PlayerAction(UUID player, long timestamp) {
         this.b = player;
         this.c = timestamp;
      }

      public UUID a() {
         return this.b;
      }

      public long b() {
         return this.c;
      }
   }
}
