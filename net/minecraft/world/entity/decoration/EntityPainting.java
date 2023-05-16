package net.minecraft.world.entity.decoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public class EntityPainting extends EntityHanging implements VariantHolder<Holder<PaintingVariant>> {
   private static final DataWatcherObject<Holder<PaintingVariant>> f = DataWatcher.a(EntityPainting.class, DataWatcherRegistry.y);
   private static final ResourceKey<PaintingVariant> g = PaintingVariants.a;
   public static final String e = "variant";

   private static Holder<PaintingVariant> j() {
      return BuiltInRegistries.m.f(g);
   }

   public EntityPainting(EntityTypes<? extends EntityPainting> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected void a_() {
      this.am.a(f, j());
   }

   @Override
   public void a(DataWatcherObject<?> var0) {
      if (f.equals(var0)) {
         this.r();
      }
   }

   public void a(Holder<PaintingVariant> var0) {
      this.am.b(f, var0);
   }

   public Holder<PaintingVariant> i() {
      return this.am.a(f);
   }

   public static Optional<EntityPainting> a(World var0, BlockPosition var1, EnumDirection var2) {
      EntityPainting var3 = new EntityPainting(var0, var1);
      List<Holder<PaintingVariant>> var4 = new ArrayList<>();
      BuiltInRegistries.m.c(PaintingVariantTags.a).forEach(var4::add);
      if (var4.isEmpty()) {
         return Optional.empty();
      } else {
         var3.a(var2);
         var4.removeIf(var1x -> {
            var3.a(var1x);
            return !var3.s();
         });
         if (var4.isEmpty()) {
            return Optional.empty();
         } else {
            int var5 = var4.stream().mapToInt(EntityPainting::b).max().orElse(0);
            var4.removeIf(var1x -> b(var1x) < var5);
            Optional<Holder<PaintingVariant>> var6 = SystemUtils.b(var4, var3.af);
            if (var6.isEmpty()) {
               return Optional.empty();
            } else {
               var3.a(var6.get());
               var3.a(var2);
               return Optional.of(var3);
            }
         }
      }
   }

   private static int b(Holder<PaintingVariant> var0) {
      return var0.a().a() * var0.a().b();
   }

   private EntityPainting(World var0, BlockPosition var1) {
      super(EntityTypes.ar, var0, var1);
   }

   public EntityPainting(World var0, BlockPosition var1, EnumDirection var2, Holder<PaintingVariant> var3) {
      this(var0, var1);
      this.a(var3);
      this.a(var2);
   }

   @Override
   public void b(NBTTagCompound var0) {
      a(var0, this.i());
      var0.a("facing", (byte)this.d.e());
      super.b(var0);
   }

   @Override
   public void a(NBTTagCompound var0) {
      Holder<PaintingVariant> var1 = c(var0).orElseGet(EntityPainting::j);
      this.a(var1);
      this.d = EnumDirection.b(var0.f("facing"));
      super.a(var0);
      this.a(this.d);
   }

   public static void a(NBTTagCompound var0, Holder<PaintingVariant> var1) {
      var0.a("variant", var1.e().orElse(g).a().toString());
   }

   public static Optional<Holder<PaintingVariant>> c(NBTTagCompound var0) {
      return Optional.ofNullable(MinecraftKey.a(var0.l("variant"))).map(var0x -> ResourceKey.a(Registries.O, var0x)).flatMap(BuiltInRegistries.m::b);
   }

   @Override
   public int t() {
      return this.i().a().a();
   }

   @Override
   public int v() {
      return this.i().a().b();
   }

   @Override
   public void a(@Nullable Entity var0) {
      if (this.H.W().b(GameRules.h)) {
         this.a(SoundEffects.qs, 1.0F, 1.0F);
         if (var0 instanceof EntityHuman var1 && var1.fK().d) {
            return;
         }

         this.a(Items.ph);
      }
   }

   @Override
   public void w() {
      this.a(SoundEffects.qt, 1.0F, 1.0F);
   }

   @Override
   public void b(double var0, double var2, double var4, float var6, float var7) {
      this.e(var0, var2, var4);
   }

   @Override
   public void a(double var0, double var2, double var4, float var6, float var7, int var8, boolean var9) {
      this.e(var0, var2, var4);
   }

   @Override
   public Vec3D df() {
      return Vec3D.a(this.c);
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      return new PacketPlayOutSpawnEntity(this, this.d.d(), this.x());
   }

   @Override
   public void a(PacketPlayOutSpawnEntity var0) {
      super.a(var0);
      this.a(EnumDirection.a(var0.n()));
   }

   @Override
   public ItemStack dt() {
      return new ItemStack(Items.ph);
   }
}
