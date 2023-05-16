package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.BannerMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaBanner extends CraftMetaItem implements BannerMeta {
   private static final Set<Material> BANNER_MATERIALS = Sets.newHashSet(
      new Material[]{
         Material.BLACK_BANNER,
         Material.BLACK_WALL_BANNER,
         Material.BLUE_BANNER,
         Material.BLUE_WALL_BANNER,
         Material.BROWN_BANNER,
         Material.BROWN_WALL_BANNER,
         Material.CYAN_BANNER,
         Material.CYAN_WALL_BANNER,
         Material.GRAY_BANNER,
         Material.GRAY_WALL_BANNER,
         Material.GREEN_BANNER,
         Material.GREEN_WALL_BANNER,
         Material.LIGHT_BLUE_BANNER,
         Material.LIGHT_BLUE_WALL_BANNER,
         Material.LIGHT_GRAY_BANNER,
         Material.LIGHT_GRAY_WALL_BANNER,
         Material.LIME_BANNER,
         Material.LIME_WALL_BANNER,
         Material.MAGENTA_BANNER,
         Material.MAGENTA_WALL_BANNER,
         Material.ORANGE_BANNER,
         Material.ORANGE_WALL_BANNER,
         Material.PINK_BANNER,
         Material.PINK_WALL_BANNER,
         Material.PURPLE_BANNER,
         Material.PURPLE_WALL_BANNER,
         Material.RED_BANNER,
         Material.RED_WALL_BANNER,
         Material.WHITE_BANNER,
         Material.WHITE_WALL_BANNER,
         Material.YELLOW_BANNER,
         Material.YELLOW_WALL_BANNER
      }
   );
   static final CraftMetaItem.ItemMetaKey BASE = new CraftMetaItem.ItemMetaKey("Base", "base-color");
   static final CraftMetaItem.ItemMetaKey PATTERNS = new CraftMetaItem.ItemMetaKey("Patterns", "patterns");
   static final CraftMetaItem.ItemMetaKey COLOR = new CraftMetaItem.ItemMetaKey("Color", "color");
   static final CraftMetaItem.ItemMetaKey PATTERN = new CraftMetaItem.ItemMetaKey("Pattern", "pattern");
   private DyeColor base;
   private List<Pattern> patterns = new ArrayList();

   CraftMetaBanner(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaBanner) {
         CraftMetaBanner banner = (CraftMetaBanner)meta;
         this.base = banner.base;
         this.patterns = new ArrayList(banner.patterns);
      }
   }

   CraftMetaBanner(NBTTagCompound tag) {
      super(tag);
      if (tag.e("BlockEntityTag")) {
         NBTTagCompound entityTag = tag.p("BlockEntityTag");
         this.base = entityTag.e(BASE.NBT) ? DyeColor.getByWoolData((byte)entityTag.h(BASE.NBT)) : null;
         if (entityTag.e(PATTERNS.NBT)) {
            NBTTagList patterns = entityTag.c(PATTERNS.NBT, 10);

            for(int i = 0; i < Math.min(patterns.size(), 20); ++i) {
               NBTTagCompound p = patterns.a(i);
               DyeColor color = DyeColor.getByWoolData((byte)p.h(COLOR.NBT));
               PatternType pattern = PatternType.getByIdentifier(p.l(PATTERN.NBT));
               if (color != null && pattern != null) {
                  this.patterns.add(new Pattern(color, pattern));
               }
            }
         }
      }
   }

   CraftMetaBanner(Map<String, Object> map) {
      super(map);
      String baseStr = CraftMetaItem.SerializableMeta.getString(map, BASE.BUKKIT, true);
      if (baseStr != null) {
         this.base = DyeColor.legacyValueOf(baseStr);
      }

      Iterable<?> rawPatternList = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, PATTERNS.BUKKIT, true);
      if (rawPatternList != null) {
         for(Object obj : rawPatternList) {
            if (!(obj instanceof Pattern)) {
               throw new IllegalArgumentException("Object in pattern list is not valid. " + obj.getClass());
            }

            this.addPattern((Pattern)obj);
         }
      }
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      NBTTagCompound entityTag = new NBTTagCompound();
      if (this.base != null) {
         entityTag.a(BASE.NBT, this.base.getWoolData());
      }

      NBTTagList newPatterns = new NBTTagList();

      for(Pattern p : this.patterns) {
         NBTTagCompound compound = new NBTTagCompound();
         compound.a(COLOR.NBT, p.getColor().getWoolData());
         compound.a(PATTERN.NBT, p.getPattern().getIdentifier());
         newPatterns.add(compound);
      }

      entityTag.a(PATTERNS.NBT, newPatterns);
      tag.a("BlockEntityTag", entityTag);
   }

   public DyeColor getBaseColor() {
      return this.base;
   }

   public void setBaseColor(DyeColor color) {
      this.base = color;
   }

   public List<Pattern> getPatterns() {
      return new ArrayList(this.patterns);
   }

   public void setPatterns(List<Pattern> patterns) {
      this.patterns = new ArrayList(patterns);
   }

   public void addPattern(Pattern pattern) {
      this.patterns.add(pattern);
   }

   public Pattern getPattern(int i) {
      return (Pattern)this.patterns.get(i);
   }

   public Pattern removePattern(int i) {
      return (Pattern)this.patterns.remove(i);
   }

   public void setPattern(int i, Pattern pattern) {
      this.patterns.set(i, pattern);
   }

   public int numberOfPatterns() {
      return this.patterns.size();
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.base != null) {
         builder.put(BASE.BUKKIT, this.base.toString());
      }

      if (!this.patterns.isEmpty()) {
         builder.put(PATTERNS.BUKKIT, ImmutableList.copyOf(this.patterns));
      }

      return builder;
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.base != null) {
         hash = 31 * hash + this.base.hashCode();
      }

      if (!this.patterns.isEmpty()) {
         hash = 31 * hash + this.patterns.hashCode();
      }

      return original != hash ? CraftMetaBanner.class.hashCode() ^ hash : hash;
   }

   @Override
   public boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (meta instanceof CraftMetaBanner that) {
         return this.base == that.base && this.patterns.equals(that.patterns);
      } else {
         return true;
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaBanner || this.patterns.isEmpty() && this.base == null);
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.patterns.isEmpty() && this.base == null;
   }

   @Override
   boolean applicableTo(Material type) {
      return BANNER_MATERIALS.contains(type);
   }

   public CraftMetaBanner clone() {
      CraftMetaBanner meta = (CraftMetaBanner)super.clone();
      meta.patterns = new ArrayList(this.patterns);
      return meta;
   }
}
