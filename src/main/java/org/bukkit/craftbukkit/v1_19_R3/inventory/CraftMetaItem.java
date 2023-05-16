package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.ItemBlock;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.Overridden;
import org.bukkit.craftbukkit.v1_19_R3.attribute.CraftAttributeInstance;
import org.bukkit.craftbukkit.v1_19_R3.attribute.CraftAttributeMap;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.inventory.tags.DeprecatedCustomTagContainer;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNBTTagConfigSerializer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;
import org.spigotmc.ValidateUtils;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaItem implements ItemMeta, Damageable, Repairable, BlockDataMeta {
   static final CraftMetaItem.ItemMetaKey NAME = new CraftMetaItem.ItemMetaKey("Name", "display-name");
   static final CraftMetaItem.ItemMetaKey LOCNAME = new CraftMetaItem.ItemMetaKey("LocName", "loc-name");
   static final CraftMetaItem.ItemMetaKey DISPLAY = new CraftMetaItem.ItemMetaKey("display");
   static final CraftMetaItem.ItemMetaKey LORE = new CraftMetaItem.ItemMetaKey("Lore", "lore");
   static final CraftMetaItem.ItemMetaKey CUSTOM_MODEL_DATA = new CraftMetaItem.ItemMetaKey("CustomModelData", "custom-model-data");
   static final CraftMetaItem.ItemMetaKey ENCHANTMENTS = new CraftMetaItem.ItemMetaKey("Enchantments", "enchants");
   static final CraftMetaItem.ItemMetaKey ENCHANTMENTS_ID = new CraftMetaItem.ItemMetaKey("id");
   static final CraftMetaItem.ItemMetaKey ENCHANTMENTS_LVL = new CraftMetaItem.ItemMetaKey("lvl");
   static final CraftMetaItem.ItemMetaKey REPAIR = new CraftMetaItem.ItemMetaKey("RepairCost", "repair-cost");
   static final CraftMetaItem.ItemMetaKey ATTRIBUTES = new CraftMetaItem.ItemMetaKey("AttributeModifiers", "attribute-modifiers");
   static final CraftMetaItem.ItemMetaKey ATTRIBUTES_IDENTIFIER = new CraftMetaItem.ItemMetaKey("AttributeName");
   static final CraftMetaItem.ItemMetaKey ATTRIBUTES_NAME = new CraftMetaItem.ItemMetaKey("Name");
   static final CraftMetaItem.ItemMetaKey ATTRIBUTES_VALUE = new CraftMetaItem.ItemMetaKey("Amount");
   static final CraftMetaItem.ItemMetaKey ATTRIBUTES_TYPE = new CraftMetaItem.ItemMetaKey("Operation");
   static final CraftMetaItem.ItemMetaKey ATTRIBUTES_UUID_HIGH = new CraftMetaItem.ItemMetaKey("UUIDMost");
   static final CraftMetaItem.ItemMetaKey ATTRIBUTES_UUID_LOW = new CraftMetaItem.ItemMetaKey("UUIDLeast");
   static final CraftMetaItem.ItemMetaKey ATTRIBUTES_SLOT = new CraftMetaItem.ItemMetaKey("Slot");
   static final CraftMetaItem.ItemMetaKey HIDEFLAGS = new CraftMetaItem.ItemMetaKey("HideFlags", "ItemFlags");
   static final CraftMetaItem.ItemMetaKey UNBREAKABLE = new CraftMetaItem.ItemMetaKey("Unbreakable");
   static final CraftMetaItem.ItemMetaKey DAMAGE = new CraftMetaItem.ItemMetaKey("Damage");
   static final CraftMetaItem.ItemMetaKey BLOCK_DATA = new CraftMetaItem.ItemMetaKey("BlockStateTag");
   static final CraftMetaItem.ItemMetaKey BUKKIT_CUSTOM_TAG = new CraftMetaItem.ItemMetaKey("PublicBukkitValues");
   private String displayName;
   private String locName;
   private List<String> lore;
   private Integer customModelData;
   private NBTTagCompound blockData;
   private Map<Enchantment, Integer> enchantments;
   private Multimap<Attribute, AttributeModifier> attributeModifiers;
   private int repairCost;
   private int hideFlag;
   private boolean unbreakable;
   private int damage;
   private static final Set<String> HANDLED_TAGS = Sets.newHashSet();
   private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
   private NBTTagCompound internalTag;
   final Map<String, NBTBase> unhandledTags = new HashMap<>();
   private CraftPersistentDataContainer persistentDataContainer = new CraftPersistentDataContainer(DATA_TYPE_REGISTRY);
   private int version = CraftMagicNumbers.INSTANCE.getDataVersion();

   CraftMetaItem(CraftMetaItem meta) {
      if (meta != null) {
         this.displayName = meta.displayName;
         this.locName = meta.locName;
         if (meta.lore != null) {
            this.lore = new ArrayList<>(meta.lore);
         }

         this.customModelData = meta.customModelData;
         this.blockData = meta.blockData;
         if (meta.enchantments != null) {
            this.enchantments = new LinkedHashMap<>(meta.enchantments);
         }

         if (meta.hasAttributeModifiers()) {
            this.attributeModifiers = LinkedHashMultimap.create(meta.attributeModifiers);
         }

         this.repairCost = meta.repairCost;
         this.hideFlag = meta.hideFlag;
         this.unbreakable = meta.unbreakable;
         this.damage = meta.damage;
         this.unhandledTags.putAll(meta.unhandledTags);
         this.persistentDataContainer.putAll(meta.persistentDataContainer.getRaw());
         this.internalTag = meta.internalTag;
         if (this.internalTag != null) {
            this.deserializeInternal(this.internalTag, meta);
         }

         this.version = meta.version;
      }
   }

   CraftMetaItem(NBTTagCompound tag) {
      if (tag.e(DISPLAY.NBT)) {
         NBTTagCompound display = tag.p(DISPLAY.NBT);
         if (display.e(NAME.NBT)) {
            this.displayName = ValidateUtils.limit(display.l(NAME.NBT), 8192);
         }

         if (display.e(LOCNAME.NBT)) {
            this.locName = ValidateUtils.limit(display.l(LOCNAME.NBT), 8192);
         }

         if (display.e(LORE.NBT)) {
            NBTTagList list = display.c(LORE.NBT, 8);
            this.lore = new ArrayList<>(list.size());

            for(int index = 0; index < list.size(); ++index) {
               String line = ValidateUtils.limit(list.j(index), 8192);
               this.lore.add(line);
            }
         }
      }

      if (tag.b(CUSTOM_MODEL_DATA.NBT, 3)) {
         this.customModelData = tag.h(CUSTOM_MODEL_DATA.NBT);
      }

      if (tag.b(BLOCK_DATA.NBT, 10)) {
         this.blockData = tag.p(BLOCK_DATA.NBT).h();
      }

      this.enchantments = buildEnchantments(tag, ENCHANTMENTS);
      this.attributeModifiers = buildModifiers(tag, ATTRIBUTES);
      if (tag.e(REPAIR.NBT)) {
         this.repairCost = tag.h(REPAIR.NBT);
      }

      if (tag.e(HIDEFLAGS.NBT)) {
         this.hideFlag = tag.h(HIDEFLAGS.NBT);
      }

      if (tag.e(UNBREAKABLE.NBT)) {
         this.unbreakable = tag.q(UNBREAKABLE.NBT);
      }

      if (tag.e(DAMAGE.NBT)) {
         this.damage = tag.h(DAMAGE.NBT);
      }

      if (tag.e(BUKKIT_CUSTOM_TAG.NBT)) {
         NBTTagCompound compound = tag.p(BUKKIT_CUSTOM_TAG.NBT);

         for(String key : compound.e()) {
            this.persistentDataContainer.put(key, compound.c(key).d());
         }
      }

      for(String key : tag.e()) {
         if (!getHandledTags().contains(key)) {
            this.unhandledTags.put(key, tag.c(key).d());
         }
      }
   }

   static Map<Enchantment, Integer> buildEnchantments(NBTTagCompound tag, CraftMetaItem.ItemMetaKey key) {
      if (!tag.e(key.NBT)) {
         return null;
      } else {
         NBTTagList ench = tag.c(key.NBT, 10);
         Map<Enchantment, Integer> enchantments = new LinkedHashMap<>(ench.size());

         for(int i = 0; i < ench.size(); ++i) {
            String id = ((NBTTagCompound)ench.k(i)).l(ENCHANTMENTS_ID.NBT);
            int level = '\uffff' & ((NBTTagCompound)ench.k(i)).g(ENCHANTMENTS_LVL.NBT);
            Enchantment enchant = Enchantment.getByKey(CraftNamespacedKey.fromStringOrNull(id));
            if (enchant != null) {
               enchantments.put(enchant, level);
            }
         }

         return enchantments;
      }
   }

   static Multimap<Attribute, AttributeModifier> buildModifiers(NBTTagCompound tag, CraftMetaItem.ItemMetaKey key) {
      Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
      if (!tag.b(key.NBT, 9)) {
         return modifiers;
      } else {
         NBTTagList mods = tag.c(key.NBT, 10);
         int size = mods.size();

         for(int i = 0; i < size; ++i) {
            NBTTagCompound entry = mods.a(i);
            if (!entry.g()) {
               net.minecraft.world.entity.ai.attributes.AttributeModifier nmsModifier = net.minecraft.world.entity.ai.attributes.AttributeModifier.a(entry);
               if (nmsModifier != null) {
                  AttributeModifier attribMod = CraftAttributeInstance.convert(nmsModifier);
                  String attributeName = entry.l(ATTRIBUTES_IDENTIFIER.NBT);
                  if (attributeName != null && !attributeName.isEmpty()) {
                     Attribute attribute = CraftAttributeMap.fromMinecraft(attributeName);
                     if (attribute != null) {
                        if (entry.b(ATTRIBUTES_SLOT.NBT, 8)) {
                           String slotName = entry.l(ATTRIBUTES_SLOT.NBT);
                           if (slotName == null || slotName.isEmpty()) {
                              modifiers.put(attribute, attribMod);
                              continue;
                           }

                           EquipmentSlot slot = null;

                           try {
                              slot = CraftEquipmentSlot.getSlot(EnumItemSlot.a(slotName.toLowerCase(Locale.ROOT)));
                           } catch (IllegalArgumentException var14) {
                           }

                           if (slot == null) {
                              modifiers.put(attribute, attribMod);
                              continue;
                           }

                           attribMod = new AttributeModifier(
                              attribMod.getUniqueId(), attribMod.getName(), attribMod.getAmount(), attribMod.getOperation(), slot
                           );
                        }

                        modifiers.put(attribute, attribMod);
                     }
                  }
               }
            }
         }

         return modifiers;
      }
   }

   CraftMetaItem(Map<String, Object> map) {
      this.displayName = CraftChatMessage.fromJSONOrStringOrNullToJSON(CraftMetaItem.SerializableMeta.getString(map, NAME.BUKKIT, true));
      this.locName = CraftChatMessage.fromJSONOrStringOrNullToJSON(CraftMetaItem.SerializableMeta.getString(map, LOCNAME.BUKKIT, true));
      Iterable<?> lore = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, LORE.BUKKIT, true);
      if (lore != null) {
         safelyAdd(lore, this.lore = new ArrayList<>(), true);
      }

      Integer customModelData = CraftMetaItem.SerializableMeta.getObject(Integer.class, map, CUSTOM_MODEL_DATA.BUKKIT, true);
      if (customModelData != null) {
         this.setCustomModelData(customModelData);
      }

      Map blockData = CraftMetaItem.SerializableMeta.getObject(Map.class, map, BLOCK_DATA.BUKKIT, true);
      if (blockData != null) {
         this.blockData = (NBTTagCompound)CraftNBTTagConfigSerializer.deserialize(blockData);
      }

      this.enchantments = buildEnchantments(map, ENCHANTMENTS);
      this.attributeModifiers = buildModifiers(map, ATTRIBUTES);
      Integer repairCost = CraftMetaItem.SerializableMeta.getObject(Integer.class, map, REPAIR.BUKKIT, true);
      if (repairCost != null) {
         this.setRepairCost(repairCost);
      }

      Iterable<?> hideFlags = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, HIDEFLAGS.BUKKIT, true);
      if (hideFlags != null) {
         for(Object hideFlagObject : hideFlags) {
            String hideFlagString = (String)hideFlagObject;

            try {
               ItemFlag hideFlatEnum = ItemFlag.valueOf(hideFlagString);
               this.addItemFlags(hideFlatEnum);
            } catch (IllegalArgumentException var14) {
            }
         }
      }

      Boolean unbreakable = CraftMetaItem.SerializableMeta.getObject(Boolean.class, map, UNBREAKABLE.BUKKIT, true);
      if (unbreakable != null) {
         this.setUnbreakable(unbreakable);
      }

      Integer damage = CraftMetaItem.SerializableMeta.getObject(Integer.class, map, DAMAGE.BUKKIT, true);
      if (damage != null) {
         this.setDamage(damage);
      }

      String internal = CraftMetaItem.SerializableMeta.getString(map, "internal", true);
      if (internal != null) {
         ByteArrayInputStream buf = new ByteArrayInputStream(Base64.getDecoder().decode(internal));

         try {
            this.internalTag = NBTCompressedStreamTools.a(buf);
            this.deserializeInternal(this.internalTag, map);

            for(String key : this.internalTag.e()) {
               if (!getHandledTags().contains(key)) {
                  this.unhandledTags.put(key, this.internalTag.c(key));
               }
            }
         } catch (IOException var15) {
            Logger.getLogger(CraftMetaItem.class.getName()).log(Level.SEVERE, null, var15);
         }
      }

      Map nbtMap = CraftMetaItem.SerializableMeta.getObject(Map.class, map, BUKKIT_CUSTOM_TAG.BUKKIT, true);
      if (nbtMap != null) {
         this.persistentDataContainer.putAll((NBTTagCompound)CraftNBTTagConfigSerializer.deserialize(nbtMap));
      }
   }

   void deserializeInternal(NBTTagCompound tag, Object context) {
      if (tag.b(ATTRIBUTES.NBT, 9)) {
         this.attributeModifiers = buildModifiers(tag, ATTRIBUTES);
      }
   }

   static Map<Enchantment, Integer> buildEnchantments(Map<String, Object> map, CraftMetaItem.ItemMetaKey key) {
      Map<?, ?> ench = CraftMetaItem.SerializableMeta.getObject(Map.class, map, key.BUKKIT, true);
      if (ench == null) {
         return null;
      } else {
         Map<Enchantment, Integer> enchantments = new LinkedHashMap<>(ench.size());

         for(Entry<?, ?> entry : ench.entrySet()) {
            String enchantKey = entry.getKey().toString();
            if (enchantKey.equals("SWEEPING")) {
               enchantKey = "SWEEPING_EDGE";
            }

            Enchantment enchantment = Enchantment.getByName(enchantKey);
            if (enchantment != null && entry.getValue() instanceof Integer) {
               enchantments.put(enchantment, (Integer)entry.getValue());
            }
         }

         return enchantments;
      }
   }

   static Multimap<Attribute, AttributeModifier> buildModifiers(Map<String, Object> map, CraftMetaItem.ItemMetaKey key) {
      Map<?, ?> mods = CraftMetaItem.SerializableMeta.getObject(Map.class, map, key.BUKKIT, true);
      Multimap<Attribute, AttributeModifier> result = LinkedHashMultimap.create();
      if (mods == null) {
         return result;
      } else {
         for(Object obj : mods.keySet()) {
            if (obj instanceof String attributeName && !Strings.isNullOrEmpty(attributeName)) {
               List<?> list = CraftMetaItem.SerializableMeta.getObject(List.class, mods, attributeName, true);
               if (list == null || list.isEmpty()) {
                  return result;
               }

               for(Object o : list) {
                  if (o instanceof AttributeModifier modifier) {
                     Attribute attribute = (Attribute)EnumUtils.getEnum(Attribute.class, attributeName.toUpperCase(Locale.ROOT));
                     if (attribute != null) {
                        result.put(attribute, modifier);
                     }
                  }
               }
            }
         }

         return result;
      }
   }

   @Overridden
   void applyToItem(NBTTagCompound itemTag) {
      if (this.hasDisplayName()) {
         this.setDisplayTag(itemTag, NAME.NBT, NBTTagString.a(this.displayName));
      }

      if (this.hasLocalizedName()) {
         this.setDisplayTag(itemTag, LOCNAME.NBT, NBTTagString.a(this.locName));
      }

      if (this.lore != null) {
         this.setDisplayTag(itemTag, LORE.NBT, this.createStringList(this.lore));
      }

      if (this.hasCustomModelData()) {
         itemTag.a(CUSTOM_MODEL_DATA.NBT, this.customModelData);
      }

      if (this.hasBlockData()) {
         itemTag.a(BLOCK_DATA.NBT, this.blockData);
      }

      if (this.hideFlag != 0) {
         itemTag.a(HIDEFLAGS.NBT, this.hideFlag);
      }

      applyEnchantments(this.enchantments, itemTag, ENCHANTMENTS);
      applyModifiers(this.attributeModifiers, itemTag, ATTRIBUTES);
      if (this.hasRepairCost()) {
         itemTag.a(REPAIR.NBT, this.repairCost);
      }

      if (this.isUnbreakable()) {
         itemTag.a(UNBREAKABLE.NBT, this.unbreakable);
      }

      if (this.hasDamage()) {
         itemTag.a(DAMAGE.NBT, this.damage);
      }

      for(Entry<String, NBTBase> e : this.unhandledTags.entrySet()) {
         itemTag.a(e.getKey(), e.getValue());
      }

      if (!this.persistentDataContainer.isEmpty()) {
         NBTTagCompound bukkitCustomCompound = new NBTTagCompound();
         Map<String, NBTBase> rawPublicMap = this.persistentDataContainer.getRaw();

         for(Entry<String, NBTBase> nbtBaseEntry : rawPublicMap.entrySet()) {
            bukkitCustomCompound.a(nbtBaseEntry.getKey(), nbtBaseEntry.getValue());
         }

         itemTag.a(BUKKIT_CUSTOM_TAG.NBT, bukkitCustomCompound);
      }
   }

   NBTTagList createStringList(List<String> list) {
      if (list == null) {
         return null;
      } else {
         NBTTagList tagList = new NBTTagList();

         for(String value : list) {
            tagList.add(NBTTagString.a(this.version > 0 && this.version < 1803 ? CraftChatMessage.fromJSONComponent(value) : value));
         }

         return tagList;
      }
   }

   static void applyEnchantments(Map<Enchantment, Integer> enchantments, NBTTagCompound tag, CraftMetaItem.ItemMetaKey key) {
      if (enchantments != null) {
         NBTTagList list = new NBTTagList();

         for(Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            NBTTagCompound subtag = new NBTTagCompound();
            subtag.a(ENCHANTMENTS_ID.NBT, ((Enchantment)entry.getKey()).getKey().toString());
            subtag.a(ENCHANTMENTS_LVL.NBT, entry.getValue().shortValue());
            list.add(subtag);
         }

         tag.a(key.NBT, list);
      }
   }

   static void applyModifiers(Multimap<Attribute, AttributeModifier> modifiers, NBTTagCompound tag, CraftMetaItem.ItemMetaKey key) {
      if (modifiers != null && !modifiers.isEmpty()) {
         NBTTagList list = new NBTTagList();

         for(Entry<Attribute, AttributeModifier> entry : modifiers.entries()) {
            if (entry.getKey() != null && entry.getValue() != null) {
               net.minecraft.world.entity.ai.attributes.AttributeModifier nmsModifier = CraftAttributeInstance.convert((AttributeModifier)entry.getValue());
               NBTTagCompound sub = nmsModifier.e();
               if (!sub.g()) {
                  String name = ((Attribute)entry.getKey()).getKey().toString();
                  if (name != null && !name.isEmpty()) {
                     sub.a(ATTRIBUTES_IDENTIFIER.NBT, name);
                     if (((AttributeModifier)entry.getValue()).getSlot() != null) {
                        EnumItemSlot slot = CraftEquipmentSlot.getNMS(((AttributeModifier)entry.getValue()).getSlot());
                        if (slot != null) {
                           sub.a(ATTRIBUTES_SLOT.NBT, slot.d());
                        }
                     }

                     list.add(sub);
                  }
               }
            }
         }

         tag.a(key.NBT, list);
      }
   }

   void setDisplayTag(NBTTagCompound tag, String key, NBTBase value) {
      NBTTagCompound display = tag.p(DISPLAY.NBT);
      if (!tag.e(DISPLAY.NBT)) {
         tag.a(DISPLAY.NBT, display);
      }

      display.a(key, value);
   }

   @Overridden
   boolean applicableTo(Material type) {
      return type != Material.AIR;
   }

   @Overridden
   boolean isEmpty() {
      return !this.hasDisplayName()
         && !this.hasLocalizedName()
         && !this.hasEnchants()
         && this.lore == null
         && !this.hasCustomModelData()
         && !this.hasBlockData()
         && !this.hasRepairCost()
         && this.unhandledTags.isEmpty()
         && this.persistentDataContainer.isEmpty()
         && this.hideFlag == 0
         && !this.isUnbreakable()
         && !this.hasDamage()
         && !this.hasAttributeModifiers();
   }

   public String getDisplayName() {
      return CraftChatMessage.fromJSONComponent(this.displayName);
   }

   public final void setDisplayName(String name) {
      this.displayName = CraftChatMessage.fromStringOrNullToJSON(name);
   }

   public boolean hasDisplayName() {
      return this.displayName != null;
   }

   public String getLocalizedName() {
      return CraftChatMessage.fromJSONComponent(this.locName);
   }

   public void setLocalizedName(String name) {
      this.locName = CraftChatMessage.fromStringOrNullToJSON(name);
   }

   public boolean hasLocalizedName() {
      return this.locName != null;
   }

   public boolean hasLore() {
      return this.lore != null && !this.lore.isEmpty();
   }

   public boolean hasRepairCost() {
      return this.repairCost > 0;
   }

   public boolean hasEnchant(Enchantment ench) {
      Validate.notNull(ench, "Enchantment cannot be null");
      return this.hasEnchants() && this.enchantments.containsKey(ench);
   }

   public int getEnchantLevel(Enchantment ench) {
      Validate.notNull(ench, "Enchantment cannot be null");
      Integer level = this.hasEnchants() ? this.enchantments.get(ench) : null;
      return level == null ? 0 : level;
   }

   public Map<Enchantment, Integer> getEnchants() {
      return this.hasEnchants() ? ImmutableMap.copyOf(this.enchantments) : ImmutableMap.of();
   }

   public boolean addEnchant(Enchantment ench, int level, boolean ignoreRestrictions) {
      Validate.notNull(ench, "Enchantment cannot be null");
      if (this.enchantments == null) {
         this.enchantments = new LinkedHashMap<>(4);
      }

      if (ignoreRestrictions || level >= ench.getStartLevel() && level <= ench.getMaxLevel()) {
         Integer old = this.enchantments.put(ench, level);
         return old == null || old != level;
      } else {
         return false;
      }
   }

   public boolean removeEnchant(Enchantment ench) {
      Validate.notNull(ench, "Enchantment cannot be null");
      boolean b = this.hasEnchants() && this.enchantments.remove(ench) != null;
      if (this.enchantments != null && this.enchantments.isEmpty()) {
         this.enchantments = null;
      }

      return b;
   }

   public boolean hasEnchants() {
      return this.enchantments != null && !this.enchantments.isEmpty();
   }

   public boolean hasConflictingEnchant(Enchantment ench) {
      return checkConflictingEnchants(this.enchantments, ench);
   }

   public void addItemFlags(ItemFlag... hideFlags) {
      for(ItemFlag f : hideFlags) {
         this.hideFlag |= this.getBitModifier(f);
      }
   }

   public void removeItemFlags(ItemFlag... hideFlags) {
      for(ItemFlag f : hideFlags) {
         this.hideFlag &= ~this.getBitModifier(f);
      }
   }

   public Set<ItemFlag> getItemFlags() {
      Set<ItemFlag> currentFlags = EnumSet.noneOf(ItemFlag.class);

      ItemFlag[] var5;
      for(ItemFlag f : var5 = ItemFlag.values()) {
         if (this.hasItemFlag(f)) {
            currentFlags.add(f);
         }
      }

      return currentFlags;
   }

   public boolean hasItemFlag(ItemFlag flag) {
      int bitModifier = this.getBitModifier(flag);
      return (this.hideFlag & bitModifier) == bitModifier;
   }

   private byte getBitModifier(ItemFlag hideFlag) {
      return (byte)(1 << hideFlag.ordinal());
   }

   public List<String> getLore() {
      return this.lore == null ? null : new ArrayList<>(Lists.transform(this.lore, CraftChatMessage::fromJSONComponent));
   }

   public void setLore(List<String> lore) {
      if (lore != null && !lore.isEmpty()) {
         if (this.lore == null) {
            this.lore = new ArrayList<>(lore.size());
         } else {
            this.lore.clear();
         }

         safelyAdd(lore, this.lore, false);
      } else {
         this.lore = null;
      }
   }

   public boolean hasCustomModelData() {
      return this.customModelData != null;
   }

   public int getCustomModelData() {
      Preconditions.checkState(this.hasCustomModelData(), "We don't have CustomModelData! Check hasCustomModelData first!");
      return this.customModelData;
   }

   public void setCustomModelData(Integer data) {
      this.customModelData = data;
   }

   public boolean hasBlockData() {
      return this.blockData != null;
   }

   public BlockData getBlockData(Material material) {
      return CraftBlockData.fromData(ItemBlock.getBlockState(CraftMagicNumbers.getBlock(material).o(), this.blockData));
   }

   public void setBlockData(BlockData blockData) {
      this.blockData = blockData == null ? null : ((CraftBlockData)blockData).toStates();
   }

   public int getRepairCost() {
      return this.repairCost;
   }

   public void setRepairCost(int cost) {
      this.repairCost = cost;
   }

   public boolean isUnbreakable() {
      return this.unbreakable;
   }

   public void setUnbreakable(boolean unbreakable) {
      this.unbreakable = unbreakable;
   }

   public boolean hasAttributeModifiers() {
      return this.attributeModifiers != null && !this.attributeModifiers.isEmpty();
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
      return this.hasAttributeModifiers() ? ImmutableMultimap.copyOf(this.attributeModifiers) : null;
   }

   private void checkAttributeList() {
      if (this.attributeModifiers == null) {
         this.attributeModifiers = LinkedHashMultimap.create();
      }
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nullable EquipmentSlot slot) {
      this.checkAttributeList();
      SetMultimap<Attribute, AttributeModifier> result = LinkedHashMultimap.create();

      for(Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entries()) {
         if (((AttributeModifier)entry.getValue()).getSlot() == null || ((AttributeModifier)entry.getValue()).getSlot() == slot) {
            result.put((Attribute)entry.getKey(), (AttributeModifier)entry.getValue());
         }
      }

      return result;
   }

   public Collection<AttributeModifier> getAttributeModifiers(@Nonnull Attribute attribute) {
      Preconditions.checkNotNull(attribute, "Attribute cannot be null");
      return this.attributeModifiers.containsKey(attribute) ? ImmutableList.copyOf(this.attributeModifiers.get(attribute)) : null;
   }

   public boolean addAttributeModifier(@Nonnull Attribute attribute, @Nonnull AttributeModifier modifier) {
      Preconditions.checkNotNull(attribute, "Attribute cannot be null");
      Preconditions.checkNotNull(modifier, "AttributeModifier cannot be null");
      this.checkAttributeList();

      for(Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entries()) {
         Preconditions.checkArgument(
            !((AttributeModifier)entry.getValue()).getUniqueId().equals(modifier.getUniqueId()),
            "Cannot register AttributeModifier. Modifier is already applied! %s",
            modifier
         );
      }

      return this.attributeModifiers.put(attribute, modifier);
   }

   public void setAttributeModifiers(@Nullable Multimap<Attribute, AttributeModifier> attributeModifiers) {
      if (attributeModifiers != null && !attributeModifiers.isEmpty()) {
         this.checkAttributeList();
         this.attributeModifiers.clear();
         Iterator<Entry<Attribute, AttributeModifier>> iterator = attributeModifiers.entries().iterator();

         while(iterator.hasNext()) {
            Entry<Attribute, AttributeModifier> next = iterator.next();
            if (next.getKey() != null && next.getValue() != null) {
               this.attributeModifiers.put((Attribute)next.getKey(), (AttributeModifier)next.getValue());
            } else {
               iterator.remove();
            }
         }
      } else {
         this.attributeModifiers = LinkedHashMultimap.create();
      }
   }

   public boolean removeAttributeModifier(@Nonnull Attribute attribute) {
      Preconditions.checkNotNull(attribute, "Attribute cannot be null");
      this.checkAttributeList();
      return !this.attributeModifiers.removeAll(attribute).isEmpty();
   }

   public boolean removeAttributeModifier(@Nullable EquipmentSlot slot) {
      this.checkAttributeList();
      int removed = 0;
      Iterator<Entry<Attribute, AttributeModifier>> iter = this.attributeModifiers.entries().iterator();

      while(iter.hasNext()) {
         Entry<Attribute, AttributeModifier> entry = iter.next();
         if (((AttributeModifier)entry.getValue()).getSlot() == null || ((AttributeModifier)entry.getValue()).getSlot() == slot) {
            iter.remove();
            ++removed;
         }
      }

      return removed > 0;
   }

   public boolean removeAttributeModifier(@Nonnull Attribute attribute, @Nonnull AttributeModifier modifier) {
      Preconditions.checkNotNull(attribute, "Attribute cannot be null");
      Preconditions.checkNotNull(modifier, "AttributeModifier cannot be null");
      this.checkAttributeList();
      int removed = 0;
      Iterator<Entry<Attribute, AttributeModifier>> iter = this.attributeModifiers.entries().iterator();

      while(iter.hasNext()) {
         Entry<Attribute, AttributeModifier> entry = iter.next();
         if (entry.getKey() != null && entry.getValue() != null) {
            if (entry.getKey() == attribute && ((AttributeModifier)entry.getValue()).getUniqueId().equals(modifier.getUniqueId())) {
               iter.remove();
               ++removed;
            }
         } else {
            iter.remove();
            ++removed;
         }
      }

      return removed > 0;
   }

   public String getAsString() {
      NBTTagCompound tag = new NBTTagCompound();
      this.applyToItem(tag);
      return tag.toString();
   }

   public CustomItemTagContainer getCustomTagContainer() {
      return new DeprecatedCustomTagContainer(this.getPersistentDataContainer());
   }

   public PersistentDataContainer getPersistentDataContainer() {
      return this.persistentDataContainer;
   }

   private static boolean compareModifiers(Multimap<Attribute, AttributeModifier> first, Multimap<Attribute, AttributeModifier> second) {
      if (first != null && second != null) {
         for(Entry<Attribute, AttributeModifier> entry : first.entries()) {
            if (!second.containsEntry(entry.getKey(), entry.getValue())) {
               return false;
            }
         }

         for(Entry<Attribute, AttributeModifier> entry : second.entries()) {
            if (!first.containsEntry(entry.getKey(), entry.getValue())) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean hasDamage() {
      return this.damage > 0;
   }

   public int getDamage() {
      return this.damage;
   }

   public void setDamage(int damage) {
      this.damage = damage;
   }

   @Override
   public final boolean equals(Object object) {
      if (object == null) {
         return false;
      } else if (object == this) {
         return true;
      } else {
         return !(object instanceof CraftMetaItem) ? false : CraftItemFactory.instance().equals(this, (ItemMeta)object);
      }
   }

   @Overridden
   boolean equalsCommon(CraftMetaItem that) {
      return (this.hasDisplayName() ? that.hasDisplayName() && this.displayName.equals(that.displayName) : !that.hasDisplayName())
         && (this.hasLocalizedName() ? that.hasLocalizedName() && this.locName.equals(that.locName) : !that.hasLocalizedName())
         && (this.hasEnchants() ? that.hasEnchants() && this.enchantments.equals(that.enchantments) : !that.hasEnchants())
         && Objects.equals(this.lore, that.lore)
         && (this.hasCustomModelData() ? that.hasCustomModelData() && this.customModelData.equals(that.customModelData) : !that.hasCustomModelData())
         && (this.hasBlockData() ? that.hasBlockData() && this.blockData.equals(that.blockData) : !that.hasBlockData())
         && (this.hasRepairCost() ? that.hasRepairCost() && this.repairCost == that.repairCost : !that.hasRepairCost())
         && (
            this.hasAttributeModifiers()
               ? that.hasAttributeModifiers() && compareModifiers(this.attributeModifiers, that.attributeModifiers)
               : !that.hasAttributeModifiers()
         )
         && this.unhandledTags.equals(that.unhandledTags)
         && this.persistentDataContainer.equals(that.persistentDataContainer)
         && this.hideFlag == that.hideFlag
         && this.isUnbreakable() == that.isUnbreakable()
         && (this.hasDamage() ? that.hasDamage() && this.damage == that.damage : !that.hasDamage())
         && this.version == that.version;
   }

   @Overridden
   boolean notUncommon(CraftMetaItem meta) {
      return true;
   }

   @Override
   public final int hashCode() {
      return this.applyHash();
   }

   @Overridden
   int applyHash() {
      int hash = 3;
      hash = 61 * hash + (this.hasDisplayName() ? this.displayName.hashCode() : 0);
      hash = 61 * hash + (this.hasLocalizedName() ? this.locName.hashCode() : 0);
      hash = 61 * hash + (this.lore != null ? this.lore.hashCode() : 0);
      hash = 61 * hash + (this.hasCustomModelData() ? this.customModelData.hashCode() : 0);
      hash = 61 * hash + (this.hasBlockData() ? this.blockData.hashCode() : 0);
      hash = 61 * hash + (this.hasEnchants() ? this.enchantments.hashCode() : 0);
      hash = 61 * hash + (this.hasRepairCost() ? this.repairCost : 0);
      hash = 61 * hash + this.unhandledTags.hashCode();
      hash = 61 * hash + (!this.persistentDataContainer.isEmpty() ? this.persistentDataContainer.hashCode() : 0);
      hash = 61 * hash + this.hideFlag;
      hash = 61 * hash + (this.isUnbreakable() ? 1231 : 1237);
      hash = 61 * hash + (this.hasDamage() ? this.damage : 0);
      hash = 61 * hash + (this.hasAttributeModifiers() ? this.attributeModifiers.hashCode() : 0);
      return 61 * hash + this.version;
   }

   @Overridden
   public CraftMetaItem clone() {
      try {
         CraftMetaItem clone = (CraftMetaItem)super.clone();
         if (this.lore != null) {
            clone.lore = new ArrayList<>(this.lore);
         }

         clone.customModelData = this.customModelData;
         clone.blockData = this.blockData;
         if (this.enchantments != null) {
            clone.enchantments = new LinkedHashMap<>(this.enchantments);
         }

         if (this.hasAttributeModifiers()) {
            clone.attributeModifiers = LinkedHashMultimap.create(this.attributeModifiers);
         }

         clone.persistentDataContainer = new CraftPersistentDataContainer(this.persistentDataContainer.getRaw(), DATA_TYPE_REGISTRY);
         clone.hideFlag = this.hideFlag;
         clone.unbreakable = this.unbreakable;
         clone.damage = this.damage;
         clone.version = this.version;
         return clone;
      } catch (CloneNotSupportedException var2) {
         throw new Error(var2);
      }
   }

   public final Map<String, Object> serialize() {
      Builder<String, Object> map = ImmutableMap.builder();
      map.put("meta-type", CraftMetaItem.SerializableMeta.classMap.get(this.getClass()));
      this.serialize(map);
      return map.build();
   }

   @Overridden
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      if (this.hasDisplayName()) {
         builder.put(NAME.BUKKIT, this.displayName);
      }

      if (this.hasLocalizedName()) {
         builder.put(LOCNAME.BUKKIT, this.locName);
      }

      if (this.lore != null) {
         builder.put(LORE.BUKKIT, ImmutableList.copyOf(this.lore));
      }

      if (this.hasCustomModelData()) {
         builder.put(CUSTOM_MODEL_DATA.BUKKIT, this.customModelData);
      }

      if (this.hasBlockData()) {
         builder.put(BLOCK_DATA.BUKKIT, CraftNBTTagConfigSerializer.serialize(this.blockData));
      }

      serializeEnchantments(this.enchantments, builder, ENCHANTMENTS);
      serializeModifiers(this.attributeModifiers, builder, ATTRIBUTES);
      if (this.hasRepairCost()) {
         builder.put(REPAIR.BUKKIT, this.repairCost);
      }

      List<String> hideFlags = new ArrayList<>();

      for(ItemFlag hideFlagEnum : this.getItemFlags()) {
         hideFlags.add(hideFlagEnum.name());
      }

      if (!hideFlags.isEmpty()) {
         builder.put(HIDEFLAGS.BUKKIT, hideFlags);
      }

      if (this.isUnbreakable()) {
         builder.put(UNBREAKABLE.BUKKIT, this.unbreakable);
      }

      if (this.hasDamage()) {
         builder.put(DAMAGE.BUKKIT, this.damage);
      }

      Map<String, NBTBase> internalTags = new HashMap<>(this.unhandledTags);
      this.serializeInternal(internalTags);
      if (!internalTags.isEmpty()) {
         NBTTagCompound internal = new NBTTagCompound();

         for(Entry<String, NBTBase> e : internalTags.entrySet()) {
            internal.a(e.getKey(), e.getValue());
         }

         try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            NBTCompressedStreamTools.a(internal, buf);
            builder.put("internal", Base64.getEncoder().encodeToString(buf.toByteArray()));
         } catch (IOException var7) {
            Logger.getLogger(CraftMetaItem.class.getName()).log(Level.SEVERE, null, var7);
         }
      }

      if (!this.persistentDataContainer.isEmpty()) {
         builder.put(BUKKIT_CUSTOM_TAG.BUKKIT, this.persistentDataContainer.serialize());
      }

      return builder;
   }

   void serializeInternal(Map<String, NBTBase> unhandledTags) {
   }

   Material updateMaterial(Material material) {
      return material;
   }

   static void serializeEnchantments(Map<Enchantment, Integer> enchantments, Builder<String, Object> builder, CraftMetaItem.ItemMetaKey key) {
      if (enchantments != null && !enchantments.isEmpty()) {
         Builder<String, Integer> enchants = ImmutableMap.builder();

         for(Entry<? extends Enchantment, Integer> enchant : enchantments.entrySet()) {
            enchants.put(((Enchantment)enchant.getKey()).getName(), enchant.getValue());
         }

         builder.put(key.BUKKIT, enchants.build());
      }
   }

   static void serializeModifiers(Multimap<Attribute, AttributeModifier> modifiers, Builder<String, Object> builder, CraftMetaItem.ItemMetaKey key) {
      if (modifiers != null && !modifiers.isEmpty()) {
         Map<String, List<Object>> mods = new LinkedHashMap<>();

         for(Entry<Attribute, AttributeModifier> entry : modifiers.entries()) {
            if (entry.getKey() != null) {
               Collection<AttributeModifier> modCollection = modifiers.get((Attribute)entry.getKey());
               if (modCollection != null && !modCollection.isEmpty()) {
                  mods.put(((Attribute)entry.getKey()).name(), new ArrayList<>(modCollection));
               }
            }
         }

         builder.put(key.BUKKIT, mods);
      }
   }

   static void safelyAdd(Iterable<?> addFrom, Collection<String> addTo, boolean possiblyJsonInput) {
      if (addFrom != null) {
         for(Object object : addFrom) {
            if (!(object instanceof String)) {
               if (object != null) {
                  throw new IllegalArgumentException(addFrom + " cannot contain non-string " + object.getClass().getName());
               }

               addTo.add(CraftChatMessage.toJSON(IChatBaseComponent.h()));
            } else {
               String entry = object.toString();
               if (possiblyJsonInput) {
                  addTo.add(CraftChatMessage.fromJSONOrStringToJSON(entry));
               } else {
                  addTo.add(CraftChatMessage.fromStringToJSON(entry));
               }
            }
         }
      }
   }

   static boolean checkConflictingEnchants(Map<Enchantment, Integer> enchantments, Enchantment ench) {
      if (enchantments != null && !enchantments.isEmpty()) {
         for(Enchantment enchant : enchantments.keySet()) {
            if (enchant.conflictsWith(ench)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public final String toString() {
      return (String)CraftMetaItem.SerializableMeta.classMap.get(this.getClass()) + "_META:" + this.serialize();
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int version) {
      this.version = version;
   }

   public static Set<String> getHandledTags() {
      synchronized(HANDLED_TAGS) {
         if (HANDLED_TAGS.isEmpty()) {
            HANDLED_TAGS.addAll(
               Arrays.asList(
                  DISPLAY.NBT,
                  CUSTOM_MODEL_DATA.NBT,
                  BLOCK_DATA.NBT,
                  REPAIR.NBT,
                  ENCHANTMENTS.NBT,
                  HIDEFLAGS.NBT,
                  UNBREAKABLE.NBT,
                  DAMAGE.NBT,
                  BUKKIT_CUSTOM_TAG.NBT,
                  ATTRIBUTES.NBT,
                  ATTRIBUTES_IDENTIFIER.NBT,
                  ATTRIBUTES_NAME.NBT,
                  ATTRIBUTES_VALUE.NBT,
                  ATTRIBUTES_UUID_HIGH.NBT,
                  ATTRIBUTES_UUID_LOW.NBT,
                  ATTRIBUTES_SLOT.NBT,
                  CraftMetaMap.MAP_SCALING.NBT,
                  CraftMetaMap.MAP_COLOR.NBT,
                  CraftMetaMap.MAP_ID.NBT,
                  CraftMetaPotion.POTION_EFFECTS.NBT,
                  CraftMetaPotion.DEFAULT_POTION.NBT,
                  CraftMetaPotion.POTION_COLOR.NBT,
                  CraftMetaSkull.SKULL_OWNER.NBT,
                  CraftMetaSkull.SKULL_PROFILE.NBT,
                  CraftMetaSpawnEgg.ENTITY_TAG.NBT,
                  CraftMetaBlockState.BLOCK_ENTITY_TAG.NBT,
                  CraftMetaBook.BOOK_TITLE.NBT,
                  CraftMetaBook.BOOK_AUTHOR.NBT,
                  CraftMetaBook.BOOK_PAGES.NBT,
                  CraftMetaBook.RESOLVED.NBT,
                  CraftMetaBook.GENERATION.NBT,
                  CraftMetaFirework.FIREWORKS.NBT,
                  CraftMetaEnchantedBook.STORED_ENCHANTMENTS.NBT,
                  CraftMetaCharge.EXPLOSION.NBT,
                  CraftMetaBlockState.BLOCK_ENTITY_TAG.NBT,
                  CraftMetaKnowledgeBook.BOOK_RECIPES.NBT,
                  CraftMetaTropicalFishBucket.VARIANT.NBT,
                  CraftMetaAxolotlBucket.VARIANT.NBT,
                  CraftMetaCrossbow.CHARGED.NBT,
                  CraftMetaCrossbow.CHARGED_PROJECTILES.NBT,
                  CraftMetaSuspiciousStew.EFFECTS.NBT,
                  CraftMetaCompass.LODESTONE_DIMENSION.NBT,
                  CraftMetaCompass.LODESTONE_POS.NBT,
                  CraftMetaCompass.LODESTONE_TRACKED.NBT,
                  CraftMetaBundle.ITEMS.NBT,
                  CraftMetaMusicInstrument.GOAT_HORN_INSTRUMENT.NBT
               )
            );
         }

         return HANDLED_TAGS;
      }
   }

   static class ItemMetaKey {
      final String BUKKIT;
      final String NBT;

      ItemMetaKey(String both) {
         this(both, both);
      }

      ItemMetaKey(String nbt, String bukkit) {
         this.NBT = nbt;
         this.BUKKIT = bukkit;
      }

      @Retention(RetentionPolicy.SOURCE)
      @Target({ElementType.FIELD})
      @interface Specific {
         CraftMetaItem.ItemMetaKey.Specific.To value();

         public static enum To {
            BUKKIT,
            NBT;
         }
      }
   }

   @SerializableAs("ItemMeta")
   public static final class SerializableMeta implements ConfigurationSerializable {
      static final String TYPE_FIELD = "meta-type";
      static final ImmutableMap<Class<? extends CraftMetaItem>, String> classMap = ImmutableMap.builder()
         .put(CraftMetaArmorStand.class, "ARMOR_STAND")
         .put(CraftMetaBanner.class, "BANNER")
         .put(CraftMetaBlockState.class, "TILE_ENTITY")
         .put(CraftMetaBook.class, "BOOK")
         .put(CraftMetaBookSigned.class, "BOOK_SIGNED")
         .put(CraftMetaSkull.class, "SKULL")
         .put(CraftMetaLeatherArmor.class, "LEATHER_ARMOR")
         .put(CraftMetaMap.class, "MAP")
         .put(CraftMetaPotion.class, "POTION")
         .put(CraftMetaSpawnEgg.class, "SPAWN_EGG")
         .put(CraftMetaEnchantedBook.class, "ENCHANTED")
         .put(CraftMetaFirework.class, "FIREWORK")
         .put(CraftMetaCharge.class, "FIREWORK_EFFECT")
         .put(CraftMetaKnowledgeBook.class, "KNOWLEDGE_BOOK")
         .put(CraftMetaTropicalFishBucket.class, "TROPICAL_FISH_BUCKET")
         .put(CraftMetaAxolotlBucket.class, "AXOLOTL_BUCKET")
         .put(CraftMetaCrossbow.class, "CROSSBOW")
         .put(CraftMetaSuspiciousStew.class, "SUSPICIOUS_STEW")
         .put(CraftMetaEntityTag.class, "ENTITY_TAG")
         .put(CraftMetaCompass.class, "COMPASS")
         .put(CraftMetaBundle.class, "BUNDLE")
         .put(CraftMetaMusicInstrument.class, "MUSIC_INSTRUMENT")
         .put(CraftMetaItem.class, "UNSPECIFIC")
         .build();
      static final ImmutableMap<String, Constructor<? extends CraftMetaItem>> constructorMap;

      static {
         Builder<String, Constructor<? extends CraftMetaItem>> classConstructorBuilder = ImmutableMap.builder();

         for(Entry<Class<? extends CraftMetaItem>, String> mapping : classMap.entrySet()) {
            try {
               classConstructorBuilder.put(mapping.getValue(), mapping.getKey().getDeclaredConstructor(Map.class));
            } catch (NoSuchMethodException var4) {
               throw new AssertionError(var4);
            }
         }

         constructorMap = classConstructorBuilder.build();
      }

      private SerializableMeta() {
      }

      public static ItemMeta deserialize(Map<String, Object> map) throws Throwable {
         Validate.notNull(map, "Cannot deserialize null map");
         String type = getString(map, "meta-type", false);
         Constructor<? extends CraftMetaItem> constructor = (Constructor)constructorMap.get(type);
         if (constructor == null) {
            throw new IllegalArgumentException(type + " is not a valid " + "meta-type");
         } else {
            try {
               return constructor.newInstance(map);
            } catch (InstantiationException var4) {
               throw new AssertionError(var4);
            } catch (IllegalAccessException var5) {
               throw new AssertionError(var5);
            } catch (InvocationTargetException var6) {
               throw var6.getCause();
            }
         }
      }

      public Map<String, Object> serialize() {
         throw new AssertionError();
      }

      static String getString(Map<?, ?> map, Object field, boolean nullable) {
         return getObject(String.class, map, field, nullable);
      }

      static boolean getBoolean(Map<?, ?> map, Object field) {
         Boolean value = getObject(Boolean.class, map, field, true);
         return value != null && value;
      }

      static <T> T getObject(Class<T> clazz, Map<?, ?> map, Object field, boolean nullable) {
         Object object = map.get(field);
         if (clazz.isInstance(object)) {
            return clazz.cast(object);
         } else if (object == null) {
            if (!nullable) {
               throw new NoSuchElementException(map + " does not contain " + field);
            } else {
               return null;
            }
         } else {
            throw new IllegalArgumentException(field + "(" + object + ") is not a valid " + clazz);
         }
      }
   }
}
