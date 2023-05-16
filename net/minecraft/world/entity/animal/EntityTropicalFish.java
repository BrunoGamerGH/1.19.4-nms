package net.minecraft.world.entity.animal;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;

public class EntityTropicalFish extends EntityFishSchool implements VariantHolder<EntityTropicalFish.Variant> {
   public static final String b = "BucketVariantTag";
   private static final DataWatcherObject<Integer> d = DataWatcher.a(EntityTropicalFish.class, DataWatcherRegistry.b);
   public static final List<EntityTropicalFish.d> c = List.of(
      new EntityTropicalFish.d(EntityTropicalFish.Variant.h, EnumColor.b, EnumColor.h),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.g, EnumColor.h, EnumColor.h),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.g, EnumColor.h, EnumColor.l),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.l, EnumColor.a, EnumColor.h),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.b, EnumColor.l, EnumColor.h),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.a, EnumColor.b, EnumColor.a),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.f, EnumColor.g, EnumColor.d),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.j, EnumColor.k, EnumColor.e),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.l, EnumColor.a, EnumColor.o),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.f, EnumColor.a, EnumColor.e),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.i, EnumColor.a, EnumColor.h),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.l, EnumColor.a, EnumColor.b),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.d, EnumColor.j, EnumColor.g),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.e, EnumColor.f, EnumColor.d),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.k, EnumColor.o, EnumColor.a),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.c, EnumColor.h, EnumColor.o),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.j, EnumColor.o, EnumColor.a),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.g, EnumColor.a, EnumColor.e),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.a, EnumColor.o, EnumColor.a),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.b, EnumColor.h, EnumColor.a),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.d, EnumColor.j, EnumColor.e),
      new EntityTropicalFish.d(EntityTropicalFish.Variant.g, EnumColor.e, EnumColor.e)
   );
   private boolean e = true;

   public EntityTropicalFish(EntityTypes<? extends EntityTropicalFish> var0, World var1) {
      super(var0, var1);
   }

   public static String c(int var0) {
      return "entity.minecraft.tropical_fish.predefined." + var0;
   }

   static int a(EntityTropicalFish.Variant var0, EnumColor var1, EnumColor var2) {
      return var0.b() & 65535 | (var1.a() & 0xFF) << 16 | (var2.a() & 0xFF) << 24;
   }

   public static EnumColor r(int var0) {
      return EnumColor.a(var0 >> 16 & 0xFF);
   }

   public static EnumColor s(int var0) {
      return EnumColor.a(var0 >> 24 & 0xFF);
   }

   public static EntityTropicalFish.Variant t(int var0) {
      return EntityTropicalFish.Variant.a(var0 & 65535);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(d, 0);
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("Variant", this.ge());
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.u(var0.h("Variant"));
   }

   public void u(int var0) {
      this.am.b(d, var0);
   }

   @Override
   public boolean d(int var0) {
      return !this.e;
   }

   public int ge() {
      return this.am.a(d);
   }

   public EnumColor gb() {
      return r(this.ge());
   }

   public EnumColor gc() {
      return s(this.ge());
   }

   public EntityTropicalFish.Variant gd() {
      return t(this.ge());
   }

   public void a(EntityTropicalFish.Variant var0) {
      int var1 = this.ge();
      EnumColor var2 = r(var1);
      EnumColor var3 = s(var1);
      this.u(a(var0, var2, var3));
   }

   @Override
   public void l(ItemStack var0) {
      super.l(var0);
      NBTTagCompound var1 = var0.v();
      var1.a("BucketVariantTag", this.ge());
   }

   @Override
   public ItemStack b() {
      return new ItemStack(Items.pQ);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.xA;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.xB;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.xD;
   }

   @Override
   protected SoundEffect fT() {
      return SoundEffects.xC;
   }

   @Nullable
   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      var3 = super.a(var0, var1, var2, var3, var4);
      if (var2 == EnumMobSpawn.l && var4 != null && var4.b("BucketVariantTag", 3)) {
         this.u(var4.h("BucketVariantTag"));
         return var3;
      } else {
         RandomSource var6 = var0.r_();
         EntityTropicalFish.d var5;
         if (var3 instanceof EntityTropicalFish.c var7) {
            var5 = var7.b;
         } else if ((double)var6.i() < 0.9) {
            var5 = SystemUtils.a(c, var6);
            var3 = new EntityTropicalFish.c(this, var5);
         } else {
            this.e = false;
            EntityTropicalFish.Variant[] var8 = EntityTropicalFish.Variant.values();
            EnumColor[] var9 = EnumColor.values();
            EntityTropicalFish.Variant var10 = SystemUtils.a(var8, var6);
            EnumColor var11 = SystemUtils.a(var9, var6);
            EnumColor var12 = SystemUtils.a(var9, var6);
            var5 = new EntityTropicalFish.d(var10, var11, var12);
         }

         this.u(var5.a());
         return var3;
      }
   }

   public static boolean b(EntityTypes<EntityTropicalFish> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      return var1.b_(var3.d()).a(TagsFluid.a)
         && var1.a_(var3.c()).a(Blocks.G)
         && (var1.v(var3).a(BiomeTags.al) || EntityWaterAnimal.c(var0, var1, var2, var3, var4));
   }

   public static enum Base {
      a(0),
      b(1);

      final int c;

      private Base(int var2) {
         this.c = var2;
      }
   }

   public static enum Variant implements INamable {
      a("kob", EntityTropicalFish.Base.a, 0),
      b("sunstreak", EntityTropicalFish.Base.a, 1),
      c("snooper", EntityTropicalFish.Base.a, 2),
      d("dasher", EntityTropicalFish.Base.a, 3),
      e("brinely", EntityTropicalFish.Base.a, 4),
      f("spotty", EntityTropicalFish.Base.a, 5),
      g("flopper", EntityTropicalFish.Base.b, 0),
      h("stripey", EntityTropicalFish.Base.b, 1),
      i("glitter", EntityTropicalFish.Base.b, 2),
      j("blockfish", EntityTropicalFish.Base.b, 3),
      k("betty", EntityTropicalFish.Base.b, 4),
      l("clayfish", EntityTropicalFish.Base.b, 5);

      public static final Codec<EntityTropicalFish.Variant> m = INamable.a(EntityTropicalFish.Variant::values);
      private static final IntFunction<EntityTropicalFish.Variant> n = ByIdMap.a(EntityTropicalFish.Variant::b, values(), a);
      private final String o;
      private final IChatBaseComponent p;
      private final EntityTropicalFish.Base q;
      private final int r;

      private Variant(String var2, EntityTropicalFish.Base var3, int var4) {
         this.o = var2;
         this.q = var3;
         this.r = var3.c | var4 << 8;
         this.p = IChatBaseComponent.c("entity.minecraft.tropical_fish.type." + this.o);
      }

      public static EntityTropicalFish.Variant a(int var0) {
         return n.apply(var0);
      }

      public EntityTropicalFish.Base a() {
         return this.q;
      }

      public int b() {
         return this.r;
      }

      @Override
      public String c() {
         return this.o;
      }

      public IChatBaseComponent d() {
         return this.p;
      }
   }

   static class c extends EntityFishSchool.a {
      final EntityTropicalFish.d b;

      c(EntityTropicalFish var0, EntityTropicalFish.d var1) {
         super(var0);
         this.b = var1;
      }
   }

   public static record d(EntityTropicalFish.Variant pattern, EnumColor baseColor, EnumColor patternColor) {
      private final EntityTropicalFish.Variant a;
      private final EnumColor b;
      private final EnumColor c;

      public d(EntityTropicalFish.Variant var0, EnumColor var1, EnumColor var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public int a() {
         return EntityTropicalFish.a(this.a, this.b, this.c);
      }

      public EntityTropicalFish.Variant b() {
         return this.a;
      }

      public EnumColor c() {
         return this.b;
      }

      public EnumColor d() {
         return this.c;
      }
   }
}
