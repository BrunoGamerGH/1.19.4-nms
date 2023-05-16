package net.minecraft.world.effect;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateHealth;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityExhaustionEvent.ExhaustionReason;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class MobEffectList {
   private final Map<AttributeBase, AttributeModifier> a = Maps.newHashMap();
   private final MobEffectInfo b;
   private final int c;
   @Nullable
   private String d;
   private Supplier<MobEffect.a> e = () -> null;

   @Nullable
   public static MobEffectList a(int i) {
      return BuiltInRegistries.e.a(i);
   }

   public static int a(MobEffectList mobeffectlist) {
      return BuiltInRegistries.e.a(mobeffectlist);
   }

   public static int b(@Nullable MobEffectList mobeffectlist) {
      return BuiltInRegistries.e.a(mobeffectlist);
   }

   protected MobEffectList(MobEffectInfo mobeffectinfo, int i) {
      this.b = mobeffectinfo;
      this.c = i;
   }

   public Optional<MobEffect.a> b() {
      return Optional.ofNullable(this.e.get());
   }

   public void a(EntityLiving entityliving, int i) {
      if (this == MobEffects.j) {
         if (entityliving.eo() < entityliving.eE()) {
            entityliving.heal(1.0F, RegainReason.MAGIC_REGEN);
         }
      } else if (this == MobEffects.s) {
         if (entityliving.eo() > 1.0F) {
            entityliving.a(entityliving.dG().poison, 1.0F);
         }
      } else if (this == MobEffects.t) {
         entityliving.a(entityliving.dG().p(), 1.0F);
      } else if (this == MobEffects.q && entityliving instanceof EntityHuman) {
         ((EntityHuman)entityliving).causeFoodExhaustion(0.005F * (float)(i + 1), ExhaustionReason.HUNGER_EFFECT);
      } else if (this == MobEffects.w && entityliving instanceof EntityHuman) {
         if (!entityliving.H.B) {
            EntityHuman entityhuman = (EntityHuman)entityliving;
            int oldFoodLevel = entityhuman.fT().a;
            FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, i + 1 + oldFoodLevel);
            if (!event.isCancelled()) {
               entityhuman.fT().a(event.getFoodLevel() - oldFoodLevel, 1.0F);
            }

            ((EntityPlayer)entityhuman)
               .b
               .a(new PacketPlayOutUpdateHealth(((EntityPlayer)entityhuman).getBukkitEntity().getScaledHealth(), entityhuman.fT().a, entityhuman.fT().b));
         }
      } else if ((this != MobEffects.f || entityliving.en()) && (this != MobEffects.g || !entityliving.en())) {
         if (this == MobEffects.g && !entityliving.en() || this == MobEffects.f && entityliving.en()) {
            entityliving.a(entityliving.dG().o(), (float)(6 << i));
         }
      } else {
         entityliving.heal((float)Math.max(4 << i, 0), RegainReason.MAGIC);
      }
   }

   public void a(@Nullable Entity entity, @Nullable Entity entity1, EntityLiving entityliving, int i, double d0) {
      if ((this != MobEffects.f || entityliving.en()) && (this != MobEffects.g || !entityliving.en())) {
         if (this == MobEffects.g && !entityliving.en() || this == MobEffects.f && entityliving.en()) {
            int j = (int)(d0 * (double)(6 << i) + 0.5);
            if (entity == null) {
               entityliving.a(entityliving.dG().o(), (float)j);
            } else {
               entityliving.a(entityliving.dG().c(entity, entity1), (float)j);
            }
         } else {
            this.a(entityliving, i);
         }
      } else {
         int j = (int)(d0 * (double)(4 << i) + 0.5);
         entityliving.heal((float)j, RegainReason.MAGIC);
      }
   }

   public boolean a(int i, int j) {
      if (this == MobEffects.j) {
         int k = 50 >> j;
         return k > 0 ? i % k == 0 : true;
      } else if (this == MobEffects.s) {
         int k = 25 >> j;
         return k > 0 ? i % k == 0 : true;
      } else if (this == MobEffects.t) {
         int k = 40 >> j;
         return k > 0 ? i % k == 0 : true;
      } else {
         return this == MobEffects.q;
      }
   }

   public boolean a() {
      return false;
   }

   protected String c() {
      if (this.d == null) {
         this.d = SystemUtils.a("effect", BuiltInRegistries.e.b(this));
      }

      return this.d;
   }

   public String d() {
      return this.c();
   }

   public IChatBaseComponent e() {
      return IChatBaseComponent.c(this.d());
   }

   public MobEffectInfo f() {
      return this.b;
   }

   public int g() {
      return this.c;
   }

   public MobEffectList a(AttributeBase attributebase, String s, double d0, AttributeModifier.Operation attributemodifier_operation) {
      AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(s), this::d, d0, attributemodifier_operation);
      this.a.put(attributebase, attributemodifier);
      return this;
   }

   public MobEffectList a(Supplier<MobEffect.a> supplier) {
      this.e = supplier;
      return this;
   }

   public Map<AttributeBase, AttributeModifier> h() {
      return this.a;
   }

   public void a(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
      for(Entry<AttributeBase, AttributeModifier> entry : this.a.entrySet()) {
         AttributeModifiable attributemodifiable = attributemapbase.a(entry.getKey());
         if (attributemodifiable != null) {
            attributemodifiable.d(entry.getValue());
         }
      }
   }

   public void b(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
      for(Entry<AttributeBase, AttributeModifier> entry : this.a.entrySet()) {
         AttributeModifiable attributemodifiable = attributemapbase.a(entry.getKey());
         if (attributemodifiable != null) {
            AttributeModifier attributemodifier = entry.getValue();
            attributemodifiable.d(attributemodifier);
            attributemodifiable.c(new AttributeModifier(attributemodifier.a(), this.d() + " " + i, this.a(i, attributemodifier), attributemodifier.c()));
         }
      }
   }

   public double a(int i, AttributeModifier attributemodifier) {
      return attributemodifier.d() * (double)(i + 1);
   }

   public boolean i() {
      return this.b == MobEffectInfo.a;
   }
}
