package org.bukkit.craftbukkit.v1_19_R3.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Dynamic;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.SharedConstants;
import net.minecraft.advancements.critereon.LootDeserializationContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.AdvancementDataWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.datafix.DataConverterRegistry;
import net.minecraft.util.datafix.fixes.DataConverterTypes;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.storage.SavedFile;
import org.bukkit.Bukkit;
import org.bukkit.Fluid;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.UnsafeValues;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.attribute.CraftAttributeInstance;
import org.bukkit.craftbukkit.v1_19_R3.attribute.CraftAttributeMap;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;

public final class CraftMagicNumbers implements UnsafeValues {
   public static final UnsafeValues INSTANCE = new CraftMagicNumbers();
   private static final Map<Block, Material> BLOCK_MATERIAL = new HashMap<>();
   private static final Map<Item, Material> ITEM_MATERIAL = new HashMap<>();
   private static final BiMap<FluidType, Fluid> FLUIDTYPE_FLUID = HashBiMap.create();
   private static final Map<Material, Item> MATERIAL_ITEM = new HashMap<>();
   private static final Map<Material, Block> MATERIAL_BLOCK = new HashMap<>();
   private static final List<String> SUPPORTED_API;

   static {
      for(Block block : BuiltInRegistries.f) {
         BLOCK_MATERIAL.put(block, Material.getMaterial(BuiltInRegistries.f.b(block).a().toUpperCase(Locale.ROOT)));
      }

      for(Item item : BuiltInRegistries.i) {
         ITEM_MATERIAL.put(item, Material.getMaterial(BuiltInRegistries.i.b(item).a().toUpperCase(Locale.ROOT)));
      }

      for(FluidType fluidType : BuiltInRegistries.d) {
         Fluid fluid = (Fluid)Registry.FLUID.get(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.d.b(fluidType)));
         FLUIDTYPE_FLUID.put(fluidType, fluid);
      }

      Material[] var3;
      for(Material material : var3 = Material.values()) {
         if (!material.isLegacy()) {
            MinecraftKey key = key(material);
            BuiltInRegistries.i.b(key).ifPresent(item -> MATERIAL_ITEM.put(material, item));
            BuiltInRegistries.f.b(key).ifPresent(block -> MATERIAL_BLOCK.put(material, block));
         }
      }

      SUPPORTED_API = Arrays.asList("1.13", "1.14", "1.15", "1.16", "1.17", "1.18", "1.19");
   }

   private CraftMagicNumbers() {
   }

   public static IBlockData getBlock(MaterialData material) {
      return getBlock(material.getItemType(), material.getData());
   }

   public static IBlockData getBlock(Material material, byte data) {
      return org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.fromLegacyData(org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.toLegacy(material), data);
   }

   public static MaterialData getMaterial(IBlockData data) {
      return org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.toLegacy(getMaterial(data.b())).getNewData(toLegacyData(data));
   }

   public static Item getItem(Material material, short data) {
      return material.isLegacy()
         ? org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.fromLegacyData(org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.toLegacy(material), data)
         : getItem(material);
   }

   public static MaterialData getMaterialData(Item item) {
      return org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.toLegacyData(getMaterial(item));
   }

   public static Material getMaterial(Block block) {
      return (Material)BLOCK_MATERIAL.get(block);
   }

   public static Material getMaterial(Item item) {
      return (Material)ITEM_MATERIAL.getOrDefault(item, Material.AIR);
   }

   public static Fluid getFluid(FluidType fluid) {
      return (Fluid)FLUIDTYPE_FLUID.get(fluid);
   }

   public static Item getItem(Material material) {
      if (material != null && material.isLegacy()) {
         material = org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.fromLegacy(material);
      }

      return MATERIAL_ITEM.get(material);
   }

   public static Block getBlock(Material material) {
      if (material != null && material.isLegacy()) {
         material = org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.fromLegacy(material);
      }

      return MATERIAL_BLOCK.get(material);
   }

   public static FluidType getFluid(Fluid fluid) {
      return (FluidType)FLUIDTYPE_FLUID.inverse().get(fluid);
   }

   public static MinecraftKey key(Material mat) {
      return CraftNamespacedKey.toMinecraft(mat.getKey());
   }

   public static byte toLegacyData(IBlockData data) {
      return org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.toLegacyData(data);
   }

   public Material toLegacy(Material material) {
      return org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.toLegacy(material);
   }

   public Material fromLegacy(Material material) {
      return org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.fromLegacy(material);
   }

   public Material fromLegacy(MaterialData material) {
      return org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.fromLegacy(material);
   }

   public Material fromLegacy(MaterialData material, boolean itemPriority) {
      return org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.fromLegacy(material, itemPriority);
   }

   public BlockData fromLegacy(Material material, byte data) {
      return CraftBlockData.fromData(getBlock(material, data));
   }

   public Material getMaterial(String material, int version) {
      Preconditions.checkArgument(material != null, "material == null");
      Preconditions.checkArgument(version <= this.getDataVersion(), "Newer version! Server downgrades are not supported!");
      if (version == this.getDataVersion()) {
         return Material.getMaterial(material);
      } else {
         Dynamic<NBTBase> name = new Dynamic(DynamicOpsNBT.a, NBTTagString.a("minecraft:" + material.toLowerCase(Locale.ROOT)));
         Dynamic<NBTBase> converted = DataConverterRegistry.a().update(DataConverterTypes.s, name, version, this.getDataVersion());
         if (name.equals(converted)) {
            converted = DataConverterRegistry.a().update(DataConverterTypes.r, name, version, this.getDataVersion());
         }

         return Material.matchMaterial(converted.asString(""));
      }
   }

   public String getMappingsVersion() {
      return "3009edc0fff87fa34680686663bd59df";
   }

   public int getDataVersion() {
      return SharedConstants.b().d().c();
   }

   public ItemStack modifyItemStack(ItemStack stack, String arguments) {
      net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

      try {
         nmsStack.c(MojangsonParser.a(arguments));
      } catch (CommandSyntaxException var5) {
         Logger.getLogger(CraftMagicNumbers.class.getName()).log(Level.SEVERE, null, var5);
      }

      stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
      return stack;
   }

   private static File getBukkitDataPackFolder() {
      return new File(MinecraftServer.getServer().a(SavedFile.j).toFile(), "bukkit");
   }

   public Advancement loadAdvancement(NamespacedKey key, String advancement) {
      if (Bukkit.getAdvancement(key) != null) {
         throw new IllegalArgumentException("Advancement " + key + " already exists.");
      } else {
         MinecraftKey minecraftkey = CraftNamespacedKey.toMinecraft(key);
         JsonElement jsonelement = (JsonElement)AdvancementDataWorld.b.fromJson(advancement, JsonElement.class);
         JsonObject jsonobject = ChatDeserializer.m(jsonelement, "advancement");
         net.minecraft.advancements.Advancement.SerializedAdvancement nms = net.minecraft.advancements.Advancement.SerializedAdvancement.a(
            jsonobject, new LootDeserializationContext(minecraftkey, MinecraftServer.getServer().aI())
         );
         if (nms != null) {
            MinecraftServer.getServer().az().c.a(Maps.newHashMap(Collections.singletonMap(minecraftkey, nms)));
            Advancement bukkit = Bukkit.getAdvancement(key);
            if (bukkit != null) {
               File file = new File(
                  getBukkitDataPackFolder(),
                  "data" + File.separator + key.getNamespace() + File.separator + "advancements" + File.separator + key.getKey() + ".json"
               );
               file.getParentFile().mkdirs();

               try {
                  Files.write(advancement, file, Charsets.UTF_8);
               } catch (IOException var10) {
                  Bukkit.getLogger().log(Level.SEVERE, "Error saving advancement " + key, (Throwable)var10);
               }

               MinecraftServer.getServer().ac().u();
               return bukkit;
            }
         }

         return null;
      }
   }

   public boolean removeAdvancement(NamespacedKey key) {
      File file = new File(
         getBukkitDataPackFolder(), "data" + File.separator + key.getNamespace() + File.separator + "advancements" + File.separator + key.getKey() + ".json"
      );
      return file.delete();
   }

   public void checkSupported(PluginDescriptionFile pdf) throws InvalidPluginException {
      String minimumVersion = MinecraftServer.getServer().server.minimumAPI;
      int minimumIndex = SUPPORTED_API.indexOf(minimumVersion);
      if (pdf.getAPIVersion() != null) {
         int pluginIndex = SUPPORTED_API.indexOf(pdf.getAPIVersion());
         if (pluginIndex == -1) {
            throw new InvalidPluginException("Unsupported API version " + pdf.getAPIVersion());
         }

         if (pluginIndex < minimumIndex) {
            throw new InvalidPluginException(
               "Plugin API version " + pdf.getAPIVersion() + " is lower than the minimum allowed version. Please update or replace it."
            );
         }
      } else {
         if (minimumIndex != -1) {
            throw new InvalidPluginException(
               "Plugin API version " + pdf.getAPIVersion() + " is lower than the minimum allowed version. Please update or replace it."
            );
         }

         org.bukkit.craftbukkit.v1_19_R3.legacy.CraftLegacy.init();
         Bukkit.getLogger().log(Level.WARNING, "Legacy plugin " + pdf.getFullName() + " does not specify an api-version.");
      }
   }

   public static boolean isLegacy(PluginDescriptionFile pdf) {
      return pdf.getAPIVersion() == null;
   }

   public byte[] processClass(PluginDescriptionFile pdf, String path, byte[] clazz) {
      try {
         clazz = Commodore.convert(clazz, !isLegacy(pdf));
      } catch (Exception var5) {
         Bukkit.getLogger().log(Level.SEVERE, "Fatal error trying to convert " + pdf.getFullName() + ":" + path, (Throwable)var5);
      }

      return clazz;
   }

   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(Material material, EquipmentSlot slot) {
      Builder<Attribute, AttributeModifier> defaultAttributes = ImmutableMultimap.builder();
      Multimap<AttributeBase, net.minecraft.world.entity.ai.attributes.AttributeModifier> nmsDefaultAttributes = getItem(material)
         .a(CraftEquipmentSlot.getNMS(slot));

      for(Entry<AttributeBase, net.minecraft.world.entity.ai.attributes.AttributeModifier> mapEntry : nmsDefaultAttributes.entries()) {
         Attribute attribute = CraftAttributeMap.fromMinecraft(BuiltInRegistries.u.b(mapEntry.getKey()).toString());
         defaultAttributes.put(attribute, CraftAttributeInstance.convert(mapEntry.getValue(), slot));
      }

      return defaultAttributes.build();
   }

   public CreativeCategory getCreativeCategory(Material material) {
      return CreativeCategory.BUILDING_BLOCKS;
   }

   public String getBlockTranslationKey(Material material) {
      Block block = getBlock(material);
      return block != null ? block.h() : null;
   }

   public String getItemTranslationKey(Material material) {
      Item item = getItem(material);
      return item != null ? item.a() : null;
   }

   public String getTranslationKey(EntityType entityType) {
      Preconditions.checkArgument(entityType.getName() != null, "Invalid name of EntityType %s for translation key", entityType);
      return EntityTypes.a(entityType.getName()).map(EntityTypes::g).orElseThrow();
   }

   public String getTranslationKey(ItemStack itemStack) {
      net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
      return nmsItemStack.c().j(nmsItemStack);
   }

   public static class NBT {
      public static final int TAG_END = 0;
      public static final int TAG_BYTE = 1;
      public static final int TAG_SHORT = 2;
      public static final int TAG_INT = 3;
      public static final int TAG_LONG = 4;
      public static final int TAG_FLOAT = 5;
      public static final int TAG_DOUBLE = 6;
      public static final int TAG_BYTE_ARRAY = 7;
      public static final int TAG_STRING = 8;
      public static final int TAG_LIST = 9;
      public static final int TAG_COMPOUND = 10;
      public static final int TAG_INT_ARRAY = 11;
      public static final int TAG_ANY_NUMBER = 99;
   }
}
