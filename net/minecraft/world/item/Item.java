package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.food.FoodInfo;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.slf4j.Logger;

public class Item implements FeatureElement, IMaterial {
   private static final Logger a = LogUtils.getLogger();
   public static final Map<Block, Item> l = Maps.newHashMap();
   protected static final UUID m = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
   protected static final UUID n = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
   public static final int o = 64;
   public static final int p = 32;
   public static final int q = 13;
   private final Holder.c<Item> b = BuiltInRegistries.i.f(this);
   private final EnumItemRarity c;
   private final int d;
   private final int e;
   private final boolean f;
   @Nullable
   private final Item g;
   @Nullable
   private String h;
   @Nullable
   private final FoodInfo i;
   private final FeatureFlagSet j;

   public static int a(Item var0) {
      return var0 == null ? 0 : BuiltInRegistries.i.a(var0);
   }

   public static Item b(int var0) {
      return BuiltInRegistries.i.a(var0);
   }

   @Deprecated
   public static Item a(Block var0) {
      return l.getOrDefault(var0, Items.a);
   }

   public Item(Item.Info var0) {
      this.c = var0.d;
      this.g = var0.c;
      this.e = var0.b;
      this.d = var0.a;
      this.i = var0.e;
      this.f = var0.f;
      this.j = var0.g;
      if (SharedConstants.aO) {
         String var1 = this.getClass().getSimpleName();
         if (!var1.endsWith("Item")) {
            a.error("Item classes should end with Item and {} doesn't.", var1);
         }
      }
   }

   @Deprecated
   public Holder.c<Item> j() {
      return this.b;
   }

   public void a(World var0, EntityLiving var1, ItemStack var2, int var3) {
   }

   public void a(EntityItem var0) {
   }

   public void b(NBTTagCompound var0) {
   }

   public boolean a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3) {
      return true;
   }

   @Override
   public Item k() {
      return this;
   }

   public EnumInteractionResult a(ItemActionContext var0) {
      return EnumInteractionResult.d;
   }

   public float a(ItemStack var0, IBlockData var1) {
      return 1.0F;
   }

   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      if (this.u()) {
         ItemStack var3 = var1.b(var2);
         if (var1.t(this.v().d())) {
            var1.c(var2);
            return InteractionResultWrapper.b(var3);
         } else {
            return InteractionResultWrapper.d(var3);
         }
      } else {
         return InteractionResultWrapper.c(var1.b(var2));
      }
   }

   public ItemStack a(ItemStack var0, World var1, EntityLiving var2) {
      return this.u() ? var2.a(var1, var0) : var0;
   }

   public final int l() {
      return this.d;
   }

   public final int n() {
      return this.e;
   }

   public boolean o() {
      return this.e > 0;
   }

   public boolean e(ItemStack var0) {
      return var0.i();
   }

   public int f(ItemStack var0) {
      return Math.round(13.0F - (float)var0.j() * 13.0F / (float)this.e);
   }

   public int g(ItemStack var0) {
      float var1 = Math.max(0.0F, ((float)this.e - (float)var0.j()) / (float)this.e);
      return MathHelper.h(var1 / 3.0F, 1.0F, 1.0F);
   }

   public boolean a(ItemStack var0, Slot var1, ClickAction var2, EntityHuman var3) {
      return false;
   }

   public boolean a(ItemStack var0, ItemStack var1, Slot var2, ClickAction var3, EntityHuman var4, SlotAccess var5) {
      return false;
   }

   public boolean a(ItemStack var0, EntityLiving var1, EntityLiving var2) {
      return false;
   }

   public boolean a(ItemStack var0, World var1, IBlockData var2, BlockPosition var3, EntityLiving var4) {
      return false;
   }

   public boolean a_(IBlockData var0) {
      return false;
   }

   public EnumInteractionResult a(ItemStack var0, EntityHuman var1, EntityLiving var2, EnumHand var3) {
      return EnumInteractionResult.d;
   }

   public IChatBaseComponent p() {
      return IChatBaseComponent.c(this.a());
   }

   @Override
   public String toString() {
      return BuiltInRegistries.i.b(this).a();
   }

   protected String q() {
      if (this.h == null) {
         this.h = SystemUtils.a("item", BuiltInRegistries.i.b(this));
      }

      return this.h;
   }

   public String a() {
      return this.q();
   }

   public String j(ItemStack var0) {
      return this.a();
   }

   public boolean r() {
      return true;
   }

   @Nullable
   public final Item s() {
      return this.g;
   }

   public boolean t() {
      return this.g != null;
   }

   public void a(ItemStack var0, World var1, Entity var2, int var3, boolean var4) {
   }

   public void b(ItemStack var0, World var1, EntityHuman var2) {
   }

   public boolean ac_() {
      return false;
   }

   public EnumAnimation c(ItemStack var0) {
      return var0.c().u() ? EnumAnimation.b : EnumAnimation.a;
   }

   public int b(ItemStack var0) {
      if (var0.c().u()) {
         return this.v().e() ? 16 : 32;
      } else {
         return 0;
      }
   }

   public void a(ItemStack var0, World var1, EntityLiving var2, int var3) {
   }

   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
   }

   public Optional<TooltipComponent> h(ItemStack var0) {
      return Optional.empty();
   }

   public IChatBaseComponent m(ItemStack var0) {
      return IChatBaseComponent.c(this.j(var0));
   }

   public boolean i(ItemStack var0) {
      return var0.D();
   }

   public EnumItemRarity n(ItemStack var0) {
      if (!var0.D()) {
         return this.c;
      } else {
         switch(this.c) {
            case a:
            case b:
               return EnumItemRarity.c;
            case c:
               return EnumItemRarity.d;
            case d:
            default:
               return this.c;
         }
      }
   }

   public boolean d_(ItemStack var0) {
      return this.l() == 1 && this.o();
   }

   protected static MovingObjectPositionBlock a(World var0, EntityHuman var1, RayTrace.FluidCollisionOption var2) {
      float var3 = var1.dy();
      float var4 = var1.dw();
      Vec3D var5 = var1.bk();
      float var6 = MathHelper.b(-var4 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var7 = MathHelper.a(-var4 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var8 = -MathHelper.b(-var3 * (float) (Math.PI / 180.0));
      float var9 = MathHelper.a(-var3 * (float) (Math.PI / 180.0));
      float var10 = var7 * var8;
      float var12 = var6 * var8;
      double var13 = 5.0;
      Vec3D var15 = var5.b((double)var10 * 5.0, (double)var9 * 5.0, (double)var12 * 5.0);
      return var0.a(new RayTrace(var5, var15, RayTrace.BlockCollisionOption.b, var2, var1));
   }

   public int c() {
      return 0;
   }

   public boolean a(ItemStack var0, ItemStack var1) {
      return false;
   }

   public Multimap<AttributeBase, AttributeModifier> a(EnumItemSlot var0) {
      return ImmutableMultimap.of();
   }

   public boolean l(ItemStack var0) {
      return false;
   }

   public ItemStack ad_() {
      return new ItemStack(this);
   }

   public boolean u() {
      return this.i != null;
   }

   @Nullable
   public FoodInfo v() {
      return this.i;
   }

   public SoundEffect ae_() {
      return SoundEffects.iM;
   }

   public SoundEffect af_() {
      return SoundEffects.iN;
   }

   public boolean w() {
      return this.f;
   }

   public boolean a(DamageSource var0) {
      return !this.f || !var0.a(DamageTypeTags.i);
   }

   public boolean ag_() {
      return true;
   }

   @Override
   public FeatureFlagSet m() {
      return this.j;
   }

   public static class Info {
      int a = 64;
      int b;
      @Nullable
      Item c;
      EnumItemRarity d = EnumItemRarity.a;
      @Nullable
      FoodInfo e;
      boolean f;
      FeatureFlagSet g = FeatureFlags.f;

      public Item.Info a(FoodInfo var0) {
         this.e = var0;
         return this;
      }

      public Item.Info a(int var0) {
         if (this.b > 0) {
            throw new RuntimeException("Unable to have damage AND stack.");
         } else {
            this.a = var0;
            return this;
         }
      }

      public Item.Info b(int var0) {
         return this.b == 0 ? this.c(var0) : this;
      }

      public Item.Info c(int var0) {
         this.b = var0;
         this.a = 1;
         return this;
      }

      public Item.Info a(Item var0) {
         this.c = var0;
         return this;
      }

      public Item.Info a(EnumItemRarity var0) {
         this.d = var0;
         return this;
      }

      public Item.Info a() {
         this.f = true;
         return this;
      }

      public Item.Info a(FeatureFlag... var0) {
         this.g = FeatureFlags.d.a(var0);
         return this;
      }
   }
}
