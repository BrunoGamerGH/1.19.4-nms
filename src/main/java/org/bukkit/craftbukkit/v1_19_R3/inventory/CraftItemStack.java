package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_19_R3.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftLegacy;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

@DelegateDeserialization(ItemStack.class)
public final class CraftItemStack extends ItemStack {
   net.minecraft.world.item.ItemStack handle;

   public static net.minecraft.world.item.ItemStack asNMSCopy(ItemStack original) {
      if (original instanceof CraftItemStack stack) {
         return stack.handle == null ? net.minecraft.world.item.ItemStack.b : stack.handle.o();
      } else if (original != null && original.getType() != Material.AIR) {
         Item item = CraftMagicNumbers.getItem(original.getType(), original.getDurability());
         if (item == null) {
            return net.minecraft.world.item.ItemStack.b;
         } else {
            net.minecraft.world.item.ItemStack stack = new net.minecraft.world.item.ItemStack(item, original.getAmount());
            if (original.hasItemMeta()) {
               setItemMeta(stack, original.getItemMeta());
            }

            return stack;
         }
      } else {
         return net.minecraft.world.item.ItemStack.b;
      }
   }

   public static net.minecraft.world.item.ItemStack copyNMSStack(net.minecraft.world.item.ItemStack original, int amount) {
      net.minecraft.world.item.ItemStack stack = original.o();
      stack.f(amount);
      return stack;
   }

   public static ItemStack asBukkitCopy(net.minecraft.world.item.ItemStack original) {
      if (original.b()) {
         return new ItemStack(Material.AIR);
      } else {
         ItemStack stack = new ItemStack(CraftMagicNumbers.getMaterial(original.c()), original.K());
         if (hasItemMeta(original)) {
            stack.setItemMeta(getItemMeta(original));
         }

         return stack;
      }
   }

   public static CraftItemStack asCraftMirror(net.minecraft.world.item.ItemStack original) {
      return new CraftItemStack(original != null && !original.b() ? original : null);
   }

   public static CraftItemStack asCraftCopy(ItemStack original) {
      return original instanceof CraftItemStack stack ? new CraftItemStack(stack.handle == null ? null : stack.handle.o()) : new CraftItemStack(original);
   }

   public static CraftItemStack asNewCraftStack(Item item) {
      return asNewCraftStack(item, 1);
   }

   public static CraftItemStack asNewCraftStack(Item item, int amount) {
      return new CraftItemStack(CraftMagicNumbers.getMaterial(item), amount, (short)0, null);
   }

   private CraftItemStack(net.minecraft.world.item.ItemStack item) {
      this.handle = item;
   }

   private CraftItemStack(ItemStack item) {
      this(item.getType(), item.getAmount(), item.getDurability(), item.hasItemMeta() ? item.getItemMeta() : null);
   }

   private CraftItemStack(Material type, int amount, short durability, ItemMeta itemMeta) {
      this.setType(type);
      this.setAmount(amount);
      this.setDurability(durability);
      this.setItemMeta(itemMeta);
   }

   public MaterialData getData() {
      return this.handle != null ? CraftMagicNumbers.getMaterialData(this.handle.c()) : super.getData();
   }

   public Material getType() {
      return this.handle != null ? CraftMagicNumbers.getMaterial(this.handle.c()) : Material.AIR;
   }

   public void setType(Material type) {
      if (this.getType() != type) {
         if (type == Material.AIR) {
            this.handle = null;
         } else if (CraftMagicNumbers.getItem(type) == null) {
            this.handle = null;
         } else if (this.handle == null) {
            this.handle = new net.minecraft.world.item.ItemStack(CraftMagicNumbers.getItem(type), 1);
         } else {
            this.handle.setItem(CraftMagicNumbers.getItem(type));
            if (this.hasItemMeta()) {
               setItemMeta(this.handle, getItemMeta(this.handle));
            }
         }

         this.setData(null);
      }
   }

   public int getAmount() {
      return this.handle != null ? this.handle.K() : 0;
   }

   public void setAmount(int amount) {
      if (this.handle != null) {
         this.handle.f(amount);
         if (amount == 0) {
            this.handle = null;
         }
      }
   }

   public void setDurability(short durability) {
      if (this.handle != null) {
         this.handle.b(durability);
      }
   }

   public short getDurability() {
      return this.handle != null ? (short)this.handle.j() : -1;
   }

   public int getMaxStackSize() {
      return this.handle == null ? Material.AIR.getMaxStackSize() : this.handle.c().l();
   }

   public void addUnsafeEnchantment(Enchantment ench, int level) {
      Validate.notNull(ench, "Cannot add null enchantment");
      if (makeTag(this.handle)) {
         NBTTagList list = getEnchantmentList(this.handle);
         if (list == null) {
            list = new NBTTagList();
            this.handle.u().a(CraftMetaItem.ENCHANTMENTS.NBT, list);
         }

         int size = list.size();

         for(int i = 0; i < size; ++i) {
            NBTTagCompound tag = (NBTTagCompound)list.k(i);
            String id = tag.l(CraftMetaItem.ENCHANTMENTS_ID.NBT);
            if (ench.getKey().equals(NamespacedKey.fromString(id))) {
               tag.a(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short)level);
               return;
            }
         }

         NBTTagCompound tag = new NBTTagCompound();
         tag.a(CraftMetaItem.ENCHANTMENTS_ID.NBT, ench.getKey().toString());
         tag.a(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short)level);
         list.add(tag);
      }
   }

   static boolean makeTag(net.minecraft.world.item.ItemStack item) {
      if (item == null) {
         return false;
      } else {
         if (item.u() == null) {
            item.c(new NBTTagCompound());
         }

         return true;
      }
   }

   public boolean containsEnchantment(Enchantment ench) {
      return this.getEnchantmentLevel(ench) > 0;
   }

   public int getEnchantmentLevel(Enchantment ench) {
      Validate.notNull(ench, "Cannot find null enchantment");
      return this.handle == null ? 0 : EnchantmentManager.a(CraftEnchantment.getRaw(ench), this.handle);
   }

   public int removeEnchantment(Enchantment ench) {
      Validate.notNull(ench, "Cannot remove null enchantment");
      NBTTagList list = getEnchantmentList(this.handle);
      if (list == null) {
         return 0;
      } else {
         int index = Integer.MIN_VALUE;
         int level = Integer.MIN_VALUE;
         int size = list.size();

         for(int i = 0; i < size; ++i) {
            NBTTagCompound enchantment = (NBTTagCompound)list.k(i);
            String id = enchantment.l(CraftMetaItem.ENCHANTMENTS_ID.NBT);
            if (ench.getKey().equals(NamespacedKey.fromString(id))) {
               index = i;
               level = '\uffff' & enchantment.g(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
               break;
            }
         }

         if (index == Integer.MIN_VALUE) {
            return 0;
         } else if (size == 1) {
            this.handle.u().r(CraftMetaItem.ENCHANTMENTS.NBT);
            if (this.handle.u().g()) {
               this.handle.c(null);
            }

            return level;
         } else {
            NBTTagList listCopy = new NBTTagList();

            for(int i = 0; i < size; ++i) {
               if (i != index) {
                  listCopy.add(list.k(i));
               }
            }

            this.handle.u().a(CraftMetaItem.ENCHANTMENTS.NBT, listCopy);
            return level;
         }
      }
   }

   public Map<Enchantment, Integer> getEnchantments() {
      return getEnchantments(this.handle);
   }

   static Map<Enchantment, Integer> getEnchantments(net.minecraft.world.item.ItemStack item) {
      NBTTagList list = item != null && item.D() ? item.w() : null;
      if (list != null && list.size() != 0) {
         Builder<Enchantment, Integer> result = ImmutableMap.builder();

         for(int i = 0; i < list.size(); ++i) {
            String id = ((NBTTagCompound)list.k(i)).l(CraftMetaItem.ENCHANTMENTS_ID.NBT);
            int level = '\uffff' & ((NBTTagCompound)list.k(i)).g(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
            Enchantment enchant = Enchantment.getByKey(CraftNamespacedKey.fromStringOrNull(id));
            if (enchant != null) {
               result.put(enchant, level);
            }
         }

         return result.build();
      } else {
         return ImmutableMap.of();
      }
   }

   static NBTTagList getEnchantmentList(net.minecraft.world.item.ItemStack item) {
      return item != null && item.D() ? item.w() : null;
   }

   public CraftItemStack clone() {
      CraftItemStack itemStack = (CraftItemStack)super.clone();
      if (this.handle != null) {
         itemStack.handle = this.handle.o();
      }

      return itemStack;
   }

   public ItemMeta getItemMeta() {
      return getItemMeta(this.handle);
   }

   public static ItemMeta getItemMeta(net.minecraft.world.item.ItemStack item) {
      if (!hasItemMeta(item)) {
         return CraftItemFactory.instance().getItemMeta(getType(item));
      } else {
         switch($SWITCH_TABLE$org$bukkit$Material()[getType(item).ordinal()]) {
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
               return new CraftMetaBlockState(item.u(), CraftMagicNumbers.getMaterial(item.c()));
            case 813:
            case 814:
            case 815:
            case 816:
            case 1078:
               return new CraftMetaLeatherArmor(item.u());
            case 840:
            case 872:
            case 873:
            case 874:
            case 1045:
            case 1046:
               return new CraftMetaEntityTag(item.u());
            case 875:
               return new CraftMetaTropicalFishBucket(item.u());
            case 876:
               return new CraftMetaAxolotlBucket(item.u());
            case 885:
               return new CraftMetaCompass(item.u());
            case 887:
               return new CraftMetaBundle(item.u());
            case 938:
               return new CraftMetaMap(item.u());
            case 954:
            case 1108:
            case 1110:
            case 1111:
               return new CraftMetaPotion(item.u());
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
               return new CraftMetaSpawnEgg(item.u());
            case 1043:
               return new CraftMetaBook(item.u());
            case 1044:
               return new CraftMetaBookSigned(item.u());
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
               return new CraftMetaSkull(item.u());
            case 1063:
               return new CraftMetaFirework(item.u());
            case 1064:
               return new CraftMetaCharge(item.u());
            case 1065:
               return new CraftMetaEnchantedBook(item.u());
            case 1074:
               return new CraftMetaArmorStand(item.u());
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
               return new CraftMetaBanner(item.u());
            case 1116:
               return new CraftMetaKnowledgeBook(item.u());
            case 1138:
               return new CraftMetaCrossbow(item.u());
            case 1139:
               return new CraftMetaSuspiciousStew(item.u());
            case 1147:
               return new CraftMetaMusicInstrument(item.u());
            default:
               return new CraftMetaItem(item.u());
         }
      }
   }

   static Material getType(net.minecraft.world.item.ItemStack item) {
      return item == null ? Material.AIR : CraftMagicNumbers.getMaterial(item.c());
   }

   public boolean setItemMeta(ItemMeta itemMeta) {
      return setItemMeta(this.handle, itemMeta);
   }

   public static boolean setItemMeta(net.minecraft.world.item.ItemStack item, ItemMeta itemMeta) {
      if (item == null) {
         return false;
      } else if (CraftItemFactory.instance().equals(itemMeta, null)) {
         item.c(null);
         return true;
      } else if (!CraftItemFactory.instance().isApplicable(itemMeta, getType(item))) {
         return false;
      } else {
         itemMeta = CraftItemFactory.instance().asMetaFor(itemMeta, getType(item));
         if (itemMeta == null) {
            return true;
         } else {
            Item oldItem = item.c();
            Item newItem = CraftMagicNumbers.getItem(CraftItemFactory.instance().updateMaterial(itemMeta, CraftMagicNumbers.getMaterial(oldItem)));
            if (oldItem != newItem) {
               item.setItem(newItem);
            }

            NBTTagCompound tag = new NBTTagCompound();
            item.c(tag);
            ((CraftMetaItem)itemMeta).applyToItem(tag);
            item.convertStack(((CraftMetaItem)itemMeta).getVersion());
            if (item.c() != null && item.c().o()) {
               item.b(item.j());
            }

            return true;
         }
      }
   }

   public boolean isSimilar(ItemStack stack) {
      if (stack == null) {
         return false;
      } else if (stack == this) {
         return true;
      } else if (!(stack instanceof CraftItemStack)) {
         return stack.getClass() == ItemStack.class && stack.isSimilar(this);
      } else {
         CraftItemStack that = (CraftItemStack)stack;
         if (this.handle == that.handle) {
            return true;
         } else if (this.handle != null && that.handle != null) {
            Material comparisonType = CraftLegacy.fromLegacy(that.getType());
            if (comparisonType == this.getType() && this.getDurability() == that.getDurability()) {
               return this.hasItemMeta() ? that.hasItemMeta() && this.handle.u().equals(that.handle.u()) : !that.hasItemMeta();
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public boolean hasItemMeta() {
      return hasItemMeta(this.handle) && !CraftItemFactory.instance().equals(this.getItemMeta(), null);
   }

   static boolean hasItemMeta(net.minecraft.world.item.ItemStack item) {
      return item != null && item.u() != null && !item.u().g();
   }
}
