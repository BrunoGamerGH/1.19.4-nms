package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3D;

public class DamageSource {
   private final Holder<DamageType> a;
   @Nullable
   private final Entity b;
   @Nullable
   private final Entity c;
   @Nullable
   private final Vec3D d;
   private boolean sweep;
   private boolean melting;
   private boolean poison;

   public boolean isSweep() {
      return this.sweep;
   }

   public DamageSource sweep() {
      this.sweep = true;
      return this;
   }

   public boolean isMelting() {
      return this.melting;
   }

   public DamageSource melting() {
      this.melting = true;
      return this;
   }

   public boolean isPoison() {
      return this.poison;
   }

   public DamageSource poison() {
      this.poison = true;
      return this;
   }

   @Override
   public String toString() {
      return "DamageSource (" + this.j().a() + ")";
   }

   public float a() {
      return this.j().c();
   }

   public boolean b() {
      return this.b != this.c;
   }

   private DamageSource(Holder<DamageType> holder, @Nullable Entity entity, @Nullable Entity entity1, @Nullable Vec3D vec3d) {
      this.a = holder;
      this.b = entity1;
      this.c = entity;
      this.d = vec3d;
   }

   public DamageSource(Holder<DamageType> holder, @Nullable Entity entity, @Nullable Entity entity1) {
      this(holder, entity, entity1, null);
   }

   public DamageSource(Holder<DamageType> holder, Vec3D vec3d) {
      this(holder, null, null, vec3d);
   }

   public DamageSource(Holder<DamageType> holder, @Nullable Entity entity) {
      this(holder, entity, entity);
   }

   public DamageSource(Holder<DamageType> holder) {
      this(holder, null, null, null);
   }

   @Nullable
   public Entity c() {
      return this.c;
   }

   @Nullable
   public Entity d() {
      return this.b;
   }

   public IChatBaseComponent a(EntityLiving entityliving) {
      String s = "death.attack." + this.j().a();
      if (this.b == null && this.c == null) {
         EntityLiving entityliving1 = entityliving.eD();
         String s1 = s + ".player";
         return entityliving1 != null ? IChatBaseComponent.a(s1, entityliving.G_(), entityliving1.G_()) : IChatBaseComponent.a(s, entityliving.G_());
      } else {
         IChatBaseComponent ichatbasecomponent = this.b == null ? this.c.G_() : this.b.G_();
         Entity entity = this.b;
         ItemStack itemstack;
         if (entity instanceof EntityLiving entityliving2) {
            itemstack = entityliving2.eK();
         } else {
            itemstack = ItemStack.b;
         }

         return !itemstack.b() && itemstack.z()
            ? IChatBaseComponent.a(s + ".item", entityliving.G_(), ichatbasecomponent, itemstack.I())
            : IChatBaseComponent.a(s, entityliving.G_(), ichatbasecomponent);
      }
   }

   public String e() {
      return this.j().a();
   }

   public boolean f() {
      return switch(this.j().b()) {
         case a -> false;
         case b -> this.b instanceof EntityLiving && !(this.b instanceof EntityHuman);
         case c -> true;
      };
   }

   public boolean g() {
      Entity entity = this.d();
      if (entity instanceof EntityHuman entityhuman && entityhuman.fK().d) {
         return true;
      }

      return false;
   }

   @Nullable
   public Vec3D h() {
      return this.d != null ? this.d : (this.b != null ? this.b.de() : null);
   }

   @Nullable
   public Vec3D i() {
      return this.d;
   }

   public boolean a(TagKey<DamageType> tagkey) {
      return this.a.a(tagkey);
   }

   public boolean a(ResourceKey<DamageType> resourcekey) {
      return this.a.a(resourcekey);
   }

   public DamageType j() {
      return this.a.a();
   }

   public Holder<DamageType> k() {
      return this.a;
   }
}
