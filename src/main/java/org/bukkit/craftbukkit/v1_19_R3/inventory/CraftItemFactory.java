package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.item.ArgumentParserItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.item.Item;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftLegacy;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class CraftItemFactory implements ItemFactory {
   static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(10511680);
   private static final CraftItemFactory instance = new CraftItemFactory();

   static {
      ConfigurationSerialization.registerClass(CraftMetaItem.SerializableMeta.class);
   }

   private CraftItemFactory() {
   }

   public boolean isApplicable(ItemMeta meta, ItemStack itemstack) {
      return itemstack == null ? false : this.isApplicable(meta, itemstack.getType());
   }

   public boolean isApplicable(ItemMeta meta, Material type) {
      type = CraftLegacy.fromLegacy(type);
      if (type == null || meta == null) {
         return false;
      } else if (!(meta instanceof CraftMetaItem)) {
         throw new IllegalArgumentException("Meta of " + meta.getClass().toString() + " not created by " + CraftItemFactory.class.getName());
      } else {
         return ((CraftMetaItem)meta).applicableTo(type);
      }
   }

   public ItemMeta getItemMeta(Material material) {
      Validate.notNull(material, "Material cannot be null");
      return this.getItemMeta(material, null);
   }

   private ItemMeta getItemMeta(Material material, CraftMetaItem meta) {
      material = CraftLegacy.fromLegacy(material);
      switch($SWITCH_TABLE$org$bukkit$Material()[material.ordinal()]) {
         case 1:
            return null;
         case 46:
         case 264:
         case 265:
         case 275:
         case 276:
         case 279:
         case 287:
         case 350:
         case 351:
         case 352:
         case 358:
         case 372:
         case 373:
         case 491:
         case 492:
         case 499:
         case 500:
         case 501:
         case 502:
         case 503:
         case 504:
         case 505:
         case 506:
         case 507:
         case 508:
         case 509:
         case 510:
         case 511:
         case 512:
         case 513:
         case 514:
         case 515:
         case 637:
         case 643:
         case 644:
         case 645:
         case 646:
         case 650:
         case 651:
         case 653:
         case 751:
         case 752:
         case 843:
         case 844:
         case 845:
         case 846:
         case 847:
         case 848:
         case 849:
         case 850:
         case 851:
         case 852:
         case 853:
         case 854:
         case 855:
         case 856:
         case 857:
         case 858:
         case 859:
         case 860:
         case 861:
         case 862:
         case 863:
         case 864:
         case 960:
         case 1112:
         case 1149:
         case 1150:
         case 1151:
         case 1157:
         case 1162:
         case 1163:
         case 1166:
         case 1167:
         case 1238:
         case 1239:
         case 1240:
         case 1241:
         case 1242:
         case 1243:
         case 1244:
         case 1245:
         case 1246:
         case 1247:
         case 1248:
         case 1249:
         case 1250:
         case 1251:
         case 1252:
         case 1253:
         case 1254:
         case 1255:
         case 1256:
         case 1257:
         case 1346:
         case 1347:
            return new CraftMetaBlockState(meta, material);
         case 813:
         case 814:
         case 815:
         case 816:
         case 1078:
            return (ItemMeta)(meta instanceof CraftMetaLeatherArmor ? meta : new CraftMetaLeatherArmor(meta));
         case 840:
         case 872:
         case 873:
         case 874:
         case 1045:
         case 1046:
            return (ItemMeta)(meta instanceof CraftMetaEntityTag ? meta : new CraftMetaEntityTag(meta));
         case 875:
            return (ItemMeta)(meta instanceof CraftMetaTropicalFishBucket ? meta : new CraftMetaTropicalFishBucket(meta));
         case 876:
            return (ItemMeta)(meta instanceof CraftMetaAxolotlBucket ? meta : new CraftMetaAxolotlBucket(meta));
         case 885:
            return (ItemMeta)(meta instanceof CraftMetaCompass ? meta : new CraftMetaCompass(meta));
         case 887:
            return (ItemMeta)(meta instanceof CraftMetaBundle ? meta : new CraftMetaBundle(meta));
         case 938:
            return (ItemMeta)(meta instanceof CraftMetaMap ? meta : new CraftMetaMap(meta));
         case 954:
         case 1108:
         case 1110:
         case 1111:
            return (ItemMeta)(meta instanceof CraftMetaPotion ? meta : new CraftMetaPotion(meta));
         case 964:
         case 965:
         case 966:
         case 967:
         case 968:
         case 969:
         case 970:
         case 971:
         case 972:
         case 973:
         case 974:
         case 975:
         case 976:
         case 977:
         case 978:
         case 979:
         case 980:
         case 981:
         case 982:
         case 983:
         case 984:
         case 985:
         case 986:
         case 987:
         case 988:
         case 989:
         case 990:
         case 991:
         case 992:
         case 993:
         case 994:
         case 995:
         case 996:
         case 997:
         case 998:
         case 999:
         case 1000:
         case 1001:
         case 1002:
         case 1003:
         case 1004:
         case 1005:
         case 1006:
         case 1007:
         case 1008:
         case 1009:
         case 1010:
         case 1011:
         case 1012:
         case 1013:
         case 1014:
         case 1015:
         case 1016:
         case 1017:
         case 1018:
         case 1019:
         case 1020:
         case 1021:
         case 1022:
         case 1023:
         case 1024:
         case 1025:
         case 1026:
         case 1027:
         case 1028:
         case 1029:
         case 1030:
         case 1031:
         case 1032:
         case 1033:
         case 1034:
         case 1035:
         case 1036:
         case 1037:
         case 1038:
         case 1039:
         case 1040:
            return (ItemMeta)(meta instanceof CraftMetaSpawnEgg ? meta : new CraftMetaSpawnEgg(meta));
         case 1043:
            return (ItemMeta)(meta != null && meta.getClass().equals(CraftMetaBook.class) ? meta : new CraftMetaBook(meta));
         case 1044:
            return (ItemMeta)(meta instanceof CraftMetaBookSigned ? meta : new CraftMetaBookSigned(meta));
         case 1054:
         case 1055:
         case 1056:
         case 1057:
         case 1058:
         case 1059:
         case 1060:
         case 1300:
         case 1301:
         case 1302:
         case 1303:
         case 1304:
         case 1305:
         case 1306:
            return (ItemMeta)(meta instanceof CraftMetaSkull ? meta : new CraftMetaSkull(meta));
         case 1063:
            return (ItemMeta)(meta instanceof CraftMetaFirework ? meta : new CraftMetaFirework(meta));
         case 1064:
            return (ItemMeta)(meta instanceof CraftMetaCharge ? meta : new CraftMetaCharge(meta));
         case 1065:
            return (ItemMeta)(meta instanceof CraftMetaEnchantedBook ? meta : new CraftMetaEnchantedBook(meta));
         case 1074:
            return (ItemMeta)(meta instanceof CraftMetaArmorStand ? meta : new CraftMetaArmorStand(meta));
         case 1084:
         case 1085:
         case 1086:
         case 1087:
         case 1088:
         case 1089:
         case 1090:
         case 1091:
         case 1092:
         case 1093:
         case 1094:
         case 1095:
         case 1096:
         case 1097:
         case 1098:
         case 1099:
         case 1307:
         case 1308:
         case 1309:
         case 1310:
         case 1311:
         case 1312:
         case 1313:
         case 1314:
         case 1315:
         case 1316:
         case 1317:
         case 1318:
         case 1319:
         case 1320:
         case 1321:
         case 1322:
            return (ItemMeta)(meta instanceof CraftMetaBanner ? meta : new CraftMetaBanner(meta));
         case 1116:
            return (ItemMeta)(meta instanceof CraftMetaKnowledgeBook ? meta : new CraftMetaKnowledgeBook(meta));
         case 1138:
            return (ItemMeta)(meta instanceof CraftMetaCrossbow ? meta : new CraftMetaCrossbow(meta));
         case 1139:
            return (ItemMeta)(meta instanceof CraftMetaSuspiciousStew ? meta : new CraftMetaSuspiciousStew(meta));
         case 1147:
            return (ItemMeta)(meta instanceof CraftMetaMusicInstrument ? meta : new CraftMetaMusicInstrument(meta));
         default:
            return new CraftMetaItem(meta);
      }
   }

   public boolean equals(ItemMeta meta1, ItemMeta meta2) {
      if (meta1 == meta2) {
         return true;
      } else if (meta1 != null && !(meta1 instanceof CraftMetaItem)) {
         throw new IllegalArgumentException("First meta of " + meta1.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
      } else if (meta2 != null && !(meta2 instanceof CraftMetaItem)) {
         throw new IllegalArgumentException("Second meta " + meta2.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
      } else if (meta1 == null) {
         return ((CraftMetaItem)meta2).isEmpty();
      } else {
         return meta2 == null ? ((CraftMetaItem)meta1).isEmpty() : this.equals((CraftMetaItem)meta1, (CraftMetaItem)meta2);
      }
   }

   boolean equals(CraftMetaItem meta1, CraftMetaItem meta2) {
      return meta1.equalsCommon(meta2) && meta1.notUncommon(meta2) && meta2.notUncommon(meta1);
   }

   public static CraftItemFactory instance() {
      return instance;
   }

   public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack) {
      Validate.notNull(stack, "Stack cannot be null");
      return this.asMetaFor(meta, stack.getType());
   }

   public ItemMeta asMetaFor(ItemMeta meta, Material material) {
      Validate.notNull(material, "Material cannot be null");
      if (!(meta instanceof CraftMetaItem)) {
         throw new IllegalArgumentException(
            "Meta of " + (meta != null ? meta.getClass().toString() : "null") + " not created by " + CraftItemFactory.class.getName()
         );
      } else {
         return this.getItemMeta(material, (CraftMetaItem)meta);
      }
   }

   public Color getDefaultLeatherColor() {
      return DEFAULT_LEATHER_COLOR;
   }

   public ItemStack createItemStack(String input) throws IllegalArgumentException {
      try {
         ArgumentParserItemStack.a arg = ArgumentParserItemStack.a(BuiltInRegistries.i.p(), new StringReader(input));
         Item item = arg.a().a();
         net.minecraft.world.item.ItemStack nmsItemStack = new net.minecraft.world.item.ItemStack(item);
         NBTTagCompound nbt = arg.b();
         if (nbt != null) {
            nmsItemStack.c(nbt);
         }

         return CraftItemStack.asCraftMirror(nmsItemStack);
      } catch (CommandSyntaxException var6) {
         throw new IllegalArgumentException("Could not parse ItemStack: " + input, var6);
      }
   }

   public Material updateMaterial(ItemMeta meta, Material material) throws IllegalArgumentException {
      return ((CraftMetaItem)meta).updateMaterial(material);
   }
}
