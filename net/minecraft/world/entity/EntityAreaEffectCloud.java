package net.minecraft.world.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.ArgumentParticle;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.World;
import net.minecraft.world.level.material.EnumPistonReaction;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.slf4j.Logger;

public class EntityAreaEffectCloud extends Entity implements TraceableEntity {
   private static final Logger d = LogUtils.getLogger();
   private static final int e = 5;
   private static final DataWatcherObject<Float> f = DataWatcher.a(EntityAreaEffectCloud.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Integer> g = DataWatcher.a(EntityAreaEffectCloud.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Boolean> h = DataWatcher.a(EntityAreaEffectCloud.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<ParticleParam> i = DataWatcher.a(EntityAreaEffectCloud.class, DataWatcherRegistry.l);
   private static final float j = 32.0F;
   private static final float k = 0.5F;
   private static final float l = 3.0F;
   public static final float b = 6.0F;
   public static final float c = 0.5F;
   private PotionRegistry m = Potions.b;
   public List<MobEffect> n = Lists.newArrayList();
   private final Map<Entity, Integer> o = Maps.newHashMap();
   private int p = 600;
   public int q = 20;
   public int r = 20;
   private boolean s;
   public int t;
   public float u;
   public float aC;
   @Nullable
   private EntityLiving aD;
   @Nullable
   private UUID aE;

   public EntityAreaEffectCloud(EntityTypes<? extends EntityAreaEffectCloud> entitytypes, World world) {
      super(entitytypes, world);
      this.ae = true;
   }

   public EntityAreaEffectCloud(World world, double d0, double d1, double d2) {
      this(EntityTypes.c, world);
      this.e(d0, d1, d2);
   }

   @Override
   protected void a_() {
      this.aj().a(g, 0);
      this.aj().a(f, 3.0F);
      this.aj().a(h, false);
      this.aj().a(i, Particles.v);
   }

   public void a(float f) {
      if (!this.H.B) {
         this.aj().b(EntityAreaEffectCloud.f, MathHelper.a(f, 0.0F, 32.0F));
      }
   }

   @Override
   public void c_() {
      double d0 = this.dl();
      double d1 = this.dn();
      double d2 = this.dr();
      super.c_();
      this.e(d0, d1, d2);
   }

   public float h() {
      return this.aj().a(f);
   }

   public void a(PotionRegistry potionregistry) {
      this.m = potionregistry;
      if (!this.s) {
         this.w();
      }
   }

   private void w() {
      if (this.m == Potions.b && this.n.isEmpty()) {
         this.aj().b(g, 0);
      } else {
         this.aj().b(g, PotionUtil.a(PotionUtil.a(this.m, this.n)));
      }
   }

   public void a(MobEffect mobeffect) {
      this.n.add(mobeffect);
      if (!this.s) {
         this.w();
      }
   }

   public void refreshEffects() {
      if (!this.s) {
         this.aj().b(g, PotionUtil.a(PotionUtil.a(this.m, this.n)));
      }
   }

   public String getPotionType() {
      return BuiltInRegistries.j.b(this.m).toString();
   }

   public void setPotionType(String string) {
      this.a(BuiltInRegistries.j.a(new MinecraftKey(string)));
   }

   public int i() {
      return this.aj().a(g);
   }

   public void a(int i) {
      this.s = true;
      this.aj().b(g, i);
   }

   public ParticleParam j() {
      return this.aj().a(i);
   }

   public void a(ParticleParam particleparam) {
      this.aj().b(i, particleparam);
   }

   protected void a(boolean flag) {
      this.aj().b(h, flag);
   }

   @Override
   public boolean k() {
      return this.aj().a(h);
   }

   public int m() {
      return this.p;
   }

   public void b(int i) {
      this.p = i;
   }

   @Override
   public void inactiveTick() {
      super.inactiveTick();
      if (this.ag >= this.q + this.p) {
         this.ai();
      }
   }

   @Override
   public void l() {
      super.l();
      boolean flag = this.k();
      float f = this.h();
      if (this.H.B) {
         if (flag && this.af.h()) {
            return;
         }

         ParticleParam particleparam = this.j();
         int i;
         float f1;
         if (flag) {
            i = 2;
            f1 = 0.2F;
         } else {
            i = MathHelper.f((float) Math.PI * f * f);
            f1 = f;
         }

         for(int j = 0; j < i; ++j) {
            float f2 = this.af.i() * ((float) (Math.PI * 2));
            float f3 = MathHelper.c(this.af.i()) * f1;
            double d0 = this.dl() + (double)(MathHelper.b(f2) * f3);
            double d1 = this.dn();
            double d2 = this.dr() + (double)(MathHelper.a(f2) * f3);
            double d3;
            double d4;
            double d5;
            if (particleparam.b() == Particles.v) {
               int k = flag && this.af.h() ? 16777215 : this.i();
               d3 = (double)((float)(k >> 16 & 0xFF) / 255.0F);
               d4 = (double)((float)(k >> 8 & 0xFF) / 255.0F);
               d5 = (double)((float)(k & 0xFF) / 255.0F);
            } else if (flag) {
               d3 = 0.0;
               d4 = 0.0;
               d5 = 0.0;
            } else {
               d3 = (0.5 - this.af.j()) * 0.15;
               d4 = 0.01F;
               d5 = (0.5 - this.af.j()) * 0.15;
            }

            this.H.b(particleparam, d0, d1, d2, d3, d4, d5);
         }
      } else {
         if (this.ag >= this.q + this.p) {
            this.ai();
            return;
         }

         boolean flag1 = this.ag < this.q;
         if (flag != flag1) {
            this.a(flag1);
         }

         if (flag1) {
            return;
         }

         if (this.aC != 0.0F) {
            f += this.aC;
            if (f < 0.5F) {
               this.ai();
               return;
            }

            this.a(f);
         }

         if (this.ag % 5 == 0) {
            this.o.entrySet().removeIf(entry -> this.ag >= entry.getValue());
            List<MobEffect> list = Lists.newArrayList();

            for(MobEffect mobeffect : this.m.a()) {
               list.add(new MobEffect(mobeffect.c(), mobeffect.a(l -> l / 4), mobeffect.e(), mobeffect.f(), mobeffect.g()));
            }

            list.addAll(this.n);
            if (list.isEmpty()) {
               this.o.clear();
            } else {
               List<EntityLiving> list1 = this.H.a(EntityLiving.class, this.cD());
               if (!list1.isEmpty()) {
                  Iterator iterator1 = list1.iterator();
                  List<LivingEntity> entities = new ArrayList();

                  while(iterator1.hasNext()) {
                     EntityLiving entityliving = (EntityLiving)iterator1.next();
                     if (!this.o.containsKey(entityliving) && entityliving.fp()) {
                        double d6 = entityliving.dl() - this.dl();
                        double d7 = entityliving.dr() - this.dr();
                        double d8 = d6 * d6 + d7 * d7;
                        if (d8 <= (double)(f * f)) {
                           entities.add((LivingEntity)entityliving.getBukkitEntity());
                        }
                     }
                  }

                  AreaEffectCloudApplyEvent event = CraftEventFactory.callAreaEffectCloudApplyEvent(this, entities);
                  if (!event.isCancelled()) {
                     for(LivingEntity entity : event.getAffectedEntities()) {
                        if (entity instanceof CraftLivingEntity) {
                           EntityLiving entityliving = ((CraftLivingEntity)entity).getHandle();
                           this.o.put(entityliving, this.ag + this.r);

                           for(MobEffect mobeffect1 : list) {
                              if (mobeffect1.c().a()) {
                                 mobeffect1.c().a(this, this.s(), entityliving, mobeffect1.e(), 0.5);
                              } else {
                                 entityliving.addEffect(new MobEffect(mobeffect1), this, Cause.AREA_EFFECT_CLOUD);
                              }
                           }

                           if (this.u != 0.0F) {
                              f += this.u;
                              if (f < 0.5F) {
                                 this.ai();
                                 return;
                              }

                              this.a(f);
                           }

                           if (this.t != 0) {
                              this.p += this.t;
                              if (this.p <= 0) {
                                 this.ai();
                                 return;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public float o() {
      return this.u;
   }

   public void b(float f) {
      this.u = f;
   }

   public float p() {
      return this.aC;
   }

   public void c(float f) {
      this.aC = f;
   }

   public int q() {
      return this.t;
   }

   public void c(int i) {
      this.t = i;
   }

   public int r() {
      return this.q;
   }

   public void d(int i) {
      this.q = i;
   }

   public void a(@Nullable EntityLiving entityliving) {
      this.aD = entityliving;
      this.aE = entityliving == null ? null : entityliving.cs();
   }

   @Nullable
   public EntityLiving s() {
      if (this.aD == null && this.aE != null && this.H instanceof WorldServer) {
         Entity entity = ((WorldServer)this.H).a(this.aE);
         if (entity instanceof EntityLiving) {
            this.aD = (EntityLiving)entity;
         }
      }

      return this.aD;
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      this.ag = nbttagcompound.h("Age");
      this.p = nbttagcompound.h("Duration");
      this.q = nbttagcompound.h("WaitTime");
      this.r = nbttagcompound.h("ReapplicationDelay");
      this.t = nbttagcompound.h("DurationOnUse");
      this.u = nbttagcompound.j("RadiusOnUse");
      this.aC = nbttagcompound.j("RadiusPerTick");
      this.a(nbttagcompound.j("Radius"));
      if (nbttagcompound.b("Owner")) {
         this.aE = nbttagcompound.a("Owner");
      }

      if (nbttagcompound.b("Particle", 8)) {
         try {
            this.a(ArgumentParticle.a(new StringReader(nbttagcompound.l("Particle")), BuiltInRegistries.k.p()));
         } catch (CommandSyntaxException var5) {
            d.warn("Couldn't load custom particle {}", nbttagcompound.l("Particle"), var5);
         }
      }

      if (nbttagcompound.b("Color", 99)) {
         this.a(nbttagcompound.h("Color"));
      }

      if (nbttagcompound.b("Potion", 8)) {
         this.a(PotionUtil.c(nbttagcompound));
      }

      if (nbttagcompound.b("Effects", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("Effects", 10);
         this.n.clear();

         for(int i = 0; i < nbttaglist.size(); ++i) {
            MobEffect mobeffect = MobEffect.b(nbttaglist.a(i));
            if (mobeffect != null) {
               this.a(mobeffect);
            }
         }
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Age", this.ag);
      nbttagcompound.a("Duration", this.p);
      nbttagcompound.a("WaitTime", this.q);
      nbttagcompound.a("ReapplicationDelay", this.r);
      nbttagcompound.a("DurationOnUse", this.t);
      nbttagcompound.a("RadiusOnUse", this.u);
      nbttagcompound.a("RadiusPerTick", this.aC);
      nbttagcompound.a("Radius", this.h());
      nbttagcompound.a("Particle", this.j().a());
      if (this.aE != null) {
         nbttagcompound.a("Owner", this.aE);
      }

      if (this.s) {
         nbttagcompound.a("Color", this.i());
      }

      if (this.m != Potions.b) {
         nbttagcompound.a("Potion", BuiltInRegistries.j.b(this.m).toString());
      }

      if (!this.n.isEmpty()) {
         NBTTagList nbttaglist = new NBTTagList();

         for(MobEffect mobeffect : this.n) {
            nbttaglist.add(mobeffect.a(new NBTTagCompound()));
         }

         nbttagcompound.a("Effects", nbttaglist);
      }
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (f.equals(datawatcherobject)) {
         this.c_();
      }

      super.a(datawatcherobject);
   }

   public PotionRegistry t() {
      return this.m;
   }

   @Override
   public EnumPistonReaction C_() {
      return EnumPistonReaction.d;
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      return EntitySize.b(this.h() * 2.0F, 0.5F);
   }
}
