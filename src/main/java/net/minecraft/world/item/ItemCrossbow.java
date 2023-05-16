package net.minecraft.world.item;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.monster.ICrossbow;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ItemCrossbow extends ItemProjectileWeapon implements ItemVanishable {
   private static final String b = "Charged";
   private static final String e = "ChargedProjectiles";
   private static final int f = 25;
   public static final int a = 8;
   private boolean g = false;
   private boolean h = false;
   private static final float i = 0.2F;
   private static final float j = 0.5F;
   private static final float k = 3.15F;
   private static final float r = 1.6F;

   public ItemCrossbow(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public Predicate<ItemStack> e() {
      return d;
   }

   @Override
   public Predicate<ItemStack> b() {
      return c;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (d(itemstack)) {
         a(world, entityhuman, enumhand, itemstack, o(itemstack), 1.0F);
         a(itemstack, false);
         return InteractionResultWrapper.b(itemstack);
      } else if (!entityhuman.g(itemstack).b()) {
         if (!d(itemstack)) {
            this.g = false;
            this.h = false;
            entityhuman.c(enumhand);
         }

         return InteractionResultWrapper.b(itemstack);
      } else {
         return InteractionResultWrapper.d(itemstack);
      }
   }

   private static float o(ItemStack itemstack) {
      return a(itemstack, Items.tw) ? 1.6F : 3.15F;
   }

   @Override
   public void a(ItemStack itemstack, World world, EntityLiving entityliving, int i) {
      int j = this.b(itemstack) - i;
      float f = a(j, itemstack);
      if (f >= 1.0F && !d(itemstack) && a(entityliving, itemstack)) {
         a(itemstack, true);
         SoundCategory soundcategory = entityliving instanceof EntityHuman ? SoundCategory.h : SoundCategory.f;
         world.a(
            null, entityliving.dl(), entityliving.dn(), entityliving.dr(), SoundEffects.fg, soundcategory, 1.0F, 1.0F / (world.r_().i() * 0.5F + 1.0F) + 0.2F
         );
      }
   }

   private static boolean a(EntityLiving entityliving, ItemStack itemstack) {
      int i = EnchantmentManager.a(Enchantments.I, itemstack);
      int j = i == 0 ? 1 : 3;
      boolean flag = entityliving instanceof EntityHuman && ((EntityHuman)entityliving).fK().d;
      ItemStack itemstack1 = entityliving.g(itemstack);
      ItemStack itemstack2 = itemstack1.o();

      for(int k = 0; k < j; ++k) {
         if (k > 0) {
            itemstack1 = itemstack2.o();
         }

         if (itemstack1.b() && flag) {
            itemstack1 = new ItemStack(Items.nD);
            itemstack2 = itemstack1.o();
         } else if (itemstack1.b()) {
            return false;
         }

         if (!a(entityliving, itemstack, itemstack1, k > 0, flag)) {
            return false;
         }
      }

      return true;
   }

   private static boolean a(EntityLiving entityliving, ItemStack itemstack, ItemStack itemstack1, boolean flag, boolean flag1) {
      if (itemstack1.b()) {
         return false;
      } else {
         boolean flag2 = flag1 && itemstack1.c() instanceof ItemArrow;
         ItemStack itemstack2;
         if (!flag2 && !flag1 && !flag) {
            itemstack2 = itemstack1.a(1);
            if (itemstack1.b() && entityliving instanceof EntityHuman) {
               ((EntityHuman)entityliving).fJ().g(itemstack1);
            }
         } else {
            itemstack2 = itemstack1.o();
         }

         b(itemstack, itemstack2);
         return true;
      }
   }

   public static boolean d(ItemStack itemstack) {
      NBTTagCompound nbttagcompound = itemstack.u();
      return nbttagcompound != null && nbttagcompound.q("Charged");
   }

   public static void a(ItemStack itemstack, boolean flag) {
      NBTTagCompound nbttagcompound = itemstack.v();
      nbttagcompound.a("Charged", flag);
   }

   private static void b(ItemStack itemstack, ItemStack itemstack1) {
      NBTTagCompound nbttagcompound = itemstack.v();
      NBTTagList nbttaglist;
      if (nbttagcompound.b("ChargedProjectiles", 9)) {
         nbttaglist = nbttagcompound.c("ChargedProjectiles", 10);
      } else {
         nbttaglist = new NBTTagList();
      }

      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      itemstack1.b(nbttagcompound1);
      nbttaglist.add(nbttagcompound1);
      nbttagcompound.a("ChargedProjectiles", nbttaglist);
   }

   private static List<ItemStack> p(ItemStack itemstack) {
      List<ItemStack> list = Lists.newArrayList();
      NBTTagCompound nbttagcompound = itemstack.u();
      if (nbttagcompound != null && nbttagcompound.b("ChargedProjectiles", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("ChargedProjectiles", 10);
         if (nbttaglist != null) {
            for(int i = 0; i < nbttaglist.size(); ++i) {
               NBTTagCompound nbttagcompound1 = nbttaglist.a(i);
               list.add(ItemStack.a(nbttagcompound1));
            }
         }
      }

      return list;
   }

   private static void q(ItemStack itemstack) {
      NBTTagCompound nbttagcompound = itemstack.u();
      if (nbttagcompound != null) {
         NBTTagList nbttaglist = nbttagcompound.c("ChargedProjectiles", 9);
         nbttaglist.clear();
         nbttagcompound.a("ChargedProjectiles", nbttaglist);
      }
   }

   public static boolean a(ItemStack itemstack, Item item) {
      return p(itemstack).stream().anyMatch(itemstack1 -> itemstack1.a(item));
   }

   private static void a(
      World world,
      EntityLiving entityliving,
      EnumHand enumhand,
      ItemStack itemstack,
      ItemStack itemstack1,
      float f,
      boolean flag,
      float f1,
      float f2,
      float f3
   ) {
      if (!world.B) {
         boolean flag1 = itemstack1.a(Items.tw);
         Object object;
         if (flag1) {
            object = new EntityFireworks(world, itemstack1, entityliving, entityliving.dl(), entityliving.dp() - 0.15F, entityliving.dr(), true);
         } else {
            object = a(world, entityliving, itemstack, itemstack1);
            if (flag || f3 != 0.0F) {
               ((EntityArrow)object).d = EntityArrow.PickupStatus.c;
            }
         }

         if (entityliving instanceof ICrossbow icrossbow) {
            icrossbow.a(icrossbow.P_(), itemstack, (IProjectile)object, f3);
         } else {
            Vec3D vec3d = entityliving.m(1.0F);
            Quaternionf quaternionf = new Quaternionf().setAngleAxis((double)(f3 * (float) (Math.PI / 180.0)), vec3d.c, vec3d.d, vec3d.e);
            Vec3D vec3d1 = entityliving.j(1.0F);
            Vector3f vector3f = vec3d1.j().rotate(quaternionf);
            ((IProjectile)object).c((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), f1, f2);
         }

         EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(entityliving, itemstack, itemstack1, (Entity)object, entityliving.ff(), f, true);
         if (event.isCancelled()) {
            event.getProjectile().remove();
            return;
         }

         itemstack.a(flag1 ? 3 : 1, entityliving, entityliving1 -> entityliving1.d(enumhand));
         if (event.getProjectile() == ((Entity)object).getBukkitEntity() && !world.b((Entity)object)) {
            if (entityliving instanceof EntityPlayer) {
               ((EntityPlayer)entityliving).getBukkitEntity().updateInventory();
            }

            return;
         }

         world.a(null, entityliving.dl(), entityliving.dn(), entityliving.dr(), SoundEffects.fm, SoundCategory.h, 1.0F, f);
      }
   }

   private static EntityArrow a(World world, EntityLiving entityliving, ItemStack itemstack, ItemStack itemstack1) {
      ItemArrow itemarrow = (ItemArrow)(itemstack1.c() instanceof ItemArrow ? itemstack1.c() : Items.nD);
      EntityArrow entityarrow = itemarrow.a(world, itemstack1, entityliving);
      if (entityliving instanceof EntityHuman) {
         entityarrow.a(true);
      }

      entityarrow.b(SoundEffects.ff);
      entityarrow.q(true);
      int i = EnchantmentManager.a(Enchantments.K, itemstack);
      if (i > 0) {
         entityarrow.a((byte)i);
      }

      return entityarrow;
   }

   public static void a(World world, EntityLiving entityliving, EnumHand enumhand, ItemStack itemstack, float f, float f1) {
      List<ItemStack> list = p(itemstack);
      float[] afloat = a(entityliving.dZ());

      for(int i = 0; i < list.size(); ++i) {
         ItemStack itemstack1 = list.get(i);
         boolean flag = entityliving instanceof EntityHuman && ((EntityHuman)entityliving).fK().d;
         if (!itemstack1.b()) {
            if (i == 0) {
               a(world, entityliving, enumhand, itemstack, itemstack1, afloat[i], flag, f, f1, 0.0F);
            } else if (i == 1) {
               a(world, entityliving, enumhand, itemstack, itemstack1, afloat[i], flag, f, f1, -10.0F);
            } else if (i == 2) {
               a(world, entityliving, enumhand, itemstack, itemstack1, afloat[i], flag, f, f1, 10.0F);
            }
         }
      }

      a(world, entityliving, itemstack);
   }

   private static float[] a(RandomSource randomsource) {
      boolean flag = randomsource.h();
      return new float[]{1.0F, a(flag, randomsource), a(!flag, randomsource)};
   }

   private static float a(boolean flag, RandomSource randomsource) {
      float f = flag ? 0.63F : 0.43F;
      return 1.0F / (randomsource.i() * 0.5F + 1.8F) + f;
   }

   private static void a(World world, EntityLiving entityliving, ItemStack itemstack) {
      if (entityliving instanceof EntityPlayer entityplayer) {
         if (!world.B) {
            CriterionTriggers.F.a(entityplayer, itemstack);
         }

         entityplayer.b(StatisticList.c.b(itemstack.c()));
      }

      q(itemstack);
   }

   @Override
   public void a(World world, EntityLiving entityliving, ItemStack itemstack, int i) {
      if (!world.B) {
         int j = EnchantmentManager.a(Enchantments.J, itemstack);
         SoundEffect soundeffect = this.a(j);
         SoundEffect soundeffect1 = j == 0 ? SoundEffects.fh : null;
         float f = (float)(itemstack.q() - i) / (float)k(itemstack);
         if (f < 0.2F) {
            this.g = false;
            this.h = false;
         }

         if (f >= 0.2F && !this.g) {
            this.g = true;
            world.a(null, entityliving.dl(), entityliving.dn(), entityliving.dr(), soundeffect, SoundCategory.h, 0.5F, 1.0F);
         }

         if (f >= 0.5F && soundeffect1 != null && !this.h) {
            this.h = true;
            world.a(null, entityliving.dl(), entityliving.dn(), entityliving.dr(), soundeffect1, SoundCategory.h, 0.5F, 1.0F);
         }
      }
   }

   @Override
   public int b(ItemStack itemstack) {
      return k(itemstack) + 3;
   }

   public static int k(ItemStack itemstack) {
      int i = EnchantmentManager.a(Enchantments.J, itemstack);
      return i == 0 ? 25 : 25 - 5 * i;
   }

   @Override
   public EnumAnimation c(ItemStack itemstack) {
      return EnumAnimation.g;
   }

   private SoundEffect a(int i) {
      switch(i) {
         case 1:
            return SoundEffects.fj;
         case 2:
            return SoundEffects.fk;
         case 3:
            return SoundEffects.fl;
         default:
            return SoundEffects.fi;
      }
   }

   private static float a(int i, ItemStack itemstack) {
      float f = (float)i / (float)k(itemstack);
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   @Override
   public void a(ItemStack itemstack, @Nullable World world, List<IChatBaseComponent> list, TooltipFlag tooltipflag) {
      List<ItemStack> list1 = p(itemstack);
      if (d(itemstack) && !list1.isEmpty()) {
         ItemStack itemstack1 = list1.get(0);
         list.add(IChatBaseComponent.c("item.minecraft.crossbow.projectile").b(CommonComponents.q).b(itemstack1.I()));
         if (tooltipflag.a() && itemstack1.a(Items.tw)) {
            List<IChatBaseComponent> list2 = Lists.newArrayList();
            Items.tw.a(itemstack1, world, list2, tooltipflag);
            if (!list2.isEmpty()) {
               for(int i = 0; i < list2.size(); ++i) {
                  list2.set(i, IChatBaseComponent.b("  ").b(list2.get(i)).a(EnumChatFormat.h));
               }

               list.addAll(list2);
            }
         }
      }
   }

   @Override
   public boolean l(ItemStack itemstack) {
      return itemstack.a(this);
   }

   @Override
   public int d() {
      return 8;
   }
}
