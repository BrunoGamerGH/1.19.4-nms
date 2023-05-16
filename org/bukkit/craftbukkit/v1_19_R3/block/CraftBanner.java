package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.block.BlockBannerAbstract;
import net.minecraft.world.level.block.entity.TileEntityBanner;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

public class CraftBanner extends CraftBlockEntityState<TileEntityBanner> implements Banner {
   private DyeColor base;
   private List<Pattern> patterns;

   public CraftBanner(World world, TileEntityBanner tileEntity) {
      super(world, tileEntity);
   }

   public void load(TileEntityBanner banner) {
      super.load(banner);
      this.base = DyeColor.getByWoolData((byte)((BlockBannerAbstract)this.data.b()).b().a());
      this.patterns = new ArrayList();
      if (banner.g != null) {
         for(int i = 0; i < banner.g.size(); ++i) {
            NBTTagCompound p = (NBTTagCompound)banner.g.k(i);
            this.patterns.add(new Pattern(DyeColor.getByWoolData((byte)p.h("Color")), PatternType.getByIdentifier(p.l("Pattern"))));
         }
      }
   }

   public DyeColor getBaseColor() {
      return this.base;
   }

   public void setBaseColor(DyeColor color) {
      Preconditions.checkArgument(color != null, "color");
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

   public void applyTo(TileEntityBanner banner) {
      super.applyTo(banner);
      banner.f = EnumColor.a(this.base.getWoolData());
      NBTTagList newPatterns = new NBTTagList();

      for(Pattern p : this.patterns) {
         NBTTagCompound compound = new NBTTagCompound();
         compound.a("Color", p.getColor().getWoolData());
         compound.a("Pattern", p.getPattern().getIdentifier());
         newPatterns.add(compound);
      }

      banner.g = newPatterns;
   }
}
