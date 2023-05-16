package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityFireballFireball;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.projectile.EntityWitherSkull;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3D;

public class DamageSources {
   private final IRegistry<DamageType> a;
   private final DamageSource b;
   private final DamageSource c;
   private final DamageSource d;
   private final DamageSource e;
   private final DamageSource f;
   private final DamageSource g;
   private final DamageSource h;
   private final DamageSource i;
   private final DamageSource j;
   private final DamageSource k;
   private final DamageSource l;
   private final DamageSource m;
   private final DamageSource n;
   private final DamageSource o;
   private final DamageSource p;
   private final DamageSource q;
   private final DamageSource r;
   private final DamageSource s;
   private final DamageSource t;
   private final DamageSource u;
   private final DamageSource v;
   public final DamageSource melting;
   public final DamageSource poison;

   public DamageSources(IRegistryCustom iregistrycustom) {
      this.a = iregistrycustom.d(Registries.o);
      this.melting = this.a(DamageTypes.c).melting();
      this.poison = this.a(DamageTypes.o).poison();
      this.b = this.a(DamageTypes.a);
      this.c = this.a(DamageTypes.b);
      this.d = this.a(DamageTypes.c);
      this.e = this.a(DamageTypes.d);
      this.f = this.a(DamageTypes.e);
      this.g = this.a(DamageTypes.f);
      this.h = this.a(DamageTypes.g);
      this.i = this.a(DamageTypes.h);
      this.j = this.a(DamageTypes.i);
      this.k = this.a(DamageTypes.j);
      this.l = this.a(DamageTypes.k);
      this.m = this.a(DamageTypes.l);
      this.n = this.a(DamageTypes.m);
      this.o = this.a(DamageTypes.n);
      this.p = this.a(DamageTypes.o);
      this.q = this.a(DamageTypes.p);
      this.r = this.a(DamageTypes.q);
      this.s = this.a(DamageTypes.r);
      this.t = this.a(DamageTypes.s);
      this.u = this.a(DamageTypes.t);
      this.v = this.a(DamageTypes.u);
   }

   private DamageSource a(ResourceKey<DamageType> resourcekey) {
      return new DamageSource(this.a.f(resourcekey));
   }

   private DamageSource a(ResourceKey<DamageType> resourcekey, @Nullable Entity entity) {
      return new DamageSource(this.a.f(resourcekey), entity);
   }

   private DamageSource a(ResourceKey<DamageType> resourcekey, @Nullable Entity entity, @Nullable Entity entity1) {
      return new DamageSource(this.a.f(resourcekey), entity, entity1);
   }

   public DamageSource a() {
      return this.b;
   }

   public DamageSource b() {
      return this.c;
   }

   public DamageSource c() {
      return this.d;
   }

   public DamageSource d() {
      return this.e;
   }

   public DamageSource e() {
      return this.f;
   }

   public DamageSource f() {
      return this.g;
   }

   public DamageSource g() {
      return this.h;
   }

   public DamageSource h() {
      return this.i;
   }

   public DamageSource i() {
      return this.j;
   }

   public DamageSource j() {
      return this.k;
   }

   public DamageSource k() {
      return this.l;
   }

   public DamageSource l() {
      return this.m;
   }

   public DamageSource m() {
      return this.n;
   }

   public DamageSource n() {
      return this.o;
   }

   public DamageSource o() {
      return this.p;
   }

   public DamageSource p() {
      return this.q;
   }

   public DamageSource q() {
      return this.r;
   }

   public DamageSource r() {
      return this.s;
   }

   public DamageSource s() {
      return this.t;
   }

   public DamageSource t() {
      return this.u;
   }

   public DamageSource u() {
      return this.v;
   }

   public DamageSource a(Entity entity) {
      return this.a(DamageTypes.v, entity);
   }

   public DamageSource b(Entity entity) {
      return this.a(DamageTypes.w, entity);
   }

   public DamageSource c(Entity entity) {
      return this.a(DamageTypes.x, entity);
   }

   public DamageSource a(EntityLiving entityliving) {
      return this.a(DamageTypes.y, entityliving);
   }

   public DamageSource b(EntityLiving entityliving) {
      return this.a(DamageTypes.z, entityliving);
   }

   public DamageSource c(EntityLiving entityliving) {
      return this.a(DamageTypes.A, entityliving);
   }

   public DamageSource a(EntityHuman entityhuman) {
      return this.a(DamageTypes.B, entityhuman);
   }

   public DamageSource a(EntityArrow entityarrow, @Nullable Entity entity) {
      return this.a(DamageTypes.C, entityarrow, entity);
   }

   public DamageSource a(Entity entity, @Nullable Entity entity1) {
      return this.a(DamageTypes.D, entity, entity1);
   }

   public DamageSource a(Entity entity, @Nullable EntityLiving entityliving) {
      return this.a(DamageTypes.E, entity, entityliving);
   }

   public DamageSource a(EntityFireworks entityfireworks, @Nullable Entity entity) {
      return this.a(DamageTypes.F, entityfireworks, entity);
   }

   public DamageSource a(EntityFireballFireball entityfireballfireball, @Nullable Entity entity) {
      return entity == null ? this.a(DamageTypes.H, entityfireballfireball) : this.a(DamageTypes.G, entityfireballfireball, entity);
   }

   public DamageSource a(EntityWitherSkull entitywitherskull, Entity entity) {
      return this.a(DamageTypes.I, entitywitherskull, entity);
   }

   public DamageSource b(Entity entity, @Nullable Entity entity1) {
      return this.a(DamageTypes.J, entity, entity1);
   }

   public DamageSource c(Entity entity, @Nullable Entity entity1) {
      return this.a(DamageTypes.K, entity, entity1);
   }

   public DamageSource d(Entity entity) {
      return this.a(DamageTypes.L, entity);
   }

   public DamageSource a(@Nullable Explosion explosion) {
      return explosion != null ? this.d(explosion.f(), explosion.e()) : this.d(null, null);
   }

   public DamageSource d(@Nullable Entity entity, @Nullable Entity entity1) {
      return this.a(entity1 != null && entity != null ? DamageTypes.N : DamageTypes.M, entity, entity1);
   }

   public DamageSource e(Entity entity) {
      return this.a(DamageTypes.O, entity);
   }

   public DamageSource a(Vec3D vec3d) {
      return new DamageSource(this.a.f(DamageTypes.P), vec3d);
   }
}
