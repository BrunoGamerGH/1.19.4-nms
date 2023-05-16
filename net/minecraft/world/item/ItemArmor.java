package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.EnumMap;
import java.util.List;
import java.util.UUID;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.ISourceBlock;
import net.minecraft.core.dispenser.DispenseBehaviorItem;
import net.minecraft.core.dispenser.IDispenseBehavior;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDispenser;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseArmorEvent;

public class ItemArmor extends Item implements Equipable {
   private static final EnumMap<ItemArmor.a, UUID> e = SystemUtils.a(new EnumMap<>(ItemArmor.a.class), enummap -> {
      enummap.put(ItemArmor.a.d, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
      enummap.put(ItemArmor.a.c, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
      enummap.put(ItemArmor.a.b, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
      enummap.put(ItemArmor.a.a, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
   });
   public static final IDispenseBehavior a = new DispenseBehaviorItem() {
      @Override
      protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
         return ItemArmor.a(isourceblock, itemstack) ? itemstack : super.a(isourceblock, itemstack);
      }
   };
   protected final ItemArmor.a b;
   private final int f;
   private final float g;
   protected final float c;
   protected final ArmorMaterial d;
   private final Multimap<AttributeBase, AttributeModifier> h;

   public static boolean a(ISourceBlock isourceblock, ItemStack itemstack) {
      BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
      List<EntityLiving> list = isourceblock.g()
         .a(EntityLiving.class, new AxisAlignedBB(blockposition), IEntitySelector.f.and(new IEntitySelector.EntitySelectorEquipable(itemstack)));
      if (list.isEmpty()) {
         return false;
      } else {
         EntityLiving entityliving = list.get(0);
         EnumItemSlot enumitemslot = EntityInsentient.h(itemstack);
         ItemStack itemstack1 = itemstack.a(1);
         World world = isourceblock.g();
         Block block = world.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
         BlockDispenseArmorEvent event = new BlockDispenseArmorEvent(block, craftItem.clone(), (CraftLivingEntity)entityliving.getBukkitEntity());
         if (!BlockDispenser.eventFired) {
            world.getCraftServer().getPluginManager().callEvent(event);
         }

         if (event.isCancelled()) {
            itemstack.g(1);
            return false;
         } else {
            if (!event.getItem().equals(craftItem)) {
               itemstack.g(1);
               ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
               IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
               if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != a) {
                  idispensebehavior.dispense(isourceblock, eventStack);
                  return true;
               }
            }

            entityliving.a(enumitemslot, CraftItemStack.asNMSCopy(event.getItem()));
            if (entityliving instanceof EntityInsentient) {
               ((EntityInsentient)entityliving).a(enumitemslot, 2.0F);
               ((EntityInsentient)entityliving).fz();
            }

            return true;
         }
      }
   }

   public ItemArmor(ArmorMaterial armormaterial, ItemArmor.a itemarmor_a, Item.Info item_info) {
      super(item_info.b(armormaterial.a(itemarmor_a)));
      this.d = armormaterial;
      this.b = itemarmor_a;
      this.f = armormaterial.b(itemarmor_a);
      this.g = armormaterial.f();
      this.c = armormaterial.g();
      BlockDispenser.a(this, a);
      Builder<AttributeBase, AttributeModifier> builder = ImmutableMultimap.builder();
      UUID uuid = e.get(itemarmor_a);
      builder.put(GenericAttributes.i, new AttributeModifier(uuid, "Armor modifier", (double)this.f, AttributeModifier.Operation.a));
      builder.put(GenericAttributes.j, new AttributeModifier(uuid, "Armor toughness", (double)this.g, AttributeModifier.Operation.a));
      if (armormaterial == EnumArmorMaterial.g) {
         builder.put(GenericAttributes.c, new AttributeModifier(uuid, "Armor knockback resistance", (double)this.c, AttributeModifier.Operation.a));
      }

      this.h = builder.build();
   }

   public ItemArmor.a b() {
      return this.b;
   }

   @Override
   public int c() {
      return this.d.a();
   }

   public ArmorMaterial d() {
      return this.d;
   }

   @Override
   public boolean a(ItemStack itemstack, ItemStack itemstack1) {
      return this.d.d().a(itemstack1) || super.a(itemstack, itemstack1);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      return this.a(this, world, entityhuman, enumhand);
   }

   @Override
   public Multimap<AttributeBase, AttributeModifier> a(EnumItemSlot enumitemslot) {
      return enumitemslot == this.b.a() ? this.h : super.a(enumitemslot);
   }

   public int e() {
      return this.f;
   }

   public float f() {
      return this.g;
   }

   @Override
   public EnumItemSlot g() {
      return this.b.a();
   }

   @Override
   public SoundEffect ak_() {
      return this.d().b();
   }

   public static enum a {
      a(EnumItemSlot.f, "helmet"),
      b(EnumItemSlot.e, "chestplate"),
      c(EnumItemSlot.d, "leggings"),
      d(EnumItemSlot.c, "boots");

      private final EnumItemSlot e;
      private final String f;

      private a(EnumItemSlot enumitemslot, String s) {
         this.e = enumitemslot;
         this.f = s;
      }

      public EnumItemSlot a() {
         return this.e;
      }

      public String b() {
         return this.f;
      }
   }
}
