package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.block.entity.TileEntitySign;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class CraftSign<T extends TileEntitySign> extends CraftBlockEntityState<T> implements Sign {
   private String[] originalLines = null;
   private String[] lines = null;

   public CraftSign(World world, T tileEntity) {
      super(world, tileEntity);
   }

   public String[] getLines() {
      if (this.lines == null) {
         TileEntitySign sign = this.getSnapshot();
         this.lines = new String[sign.f.length];
         System.arraycopy(revertComponents(sign.f), 0, this.lines, 0, this.lines.length);
         this.originalLines = new String[this.lines.length];
         System.arraycopy(this.lines, 0, this.originalLines, 0, this.originalLines.length);
      }

      return this.lines;
   }

   public String getLine(int index) throws IndexOutOfBoundsException {
      return this.getLines()[index];
   }

   public void setLine(int index, String line) throws IndexOutOfBoundsException {
      this.getLines()[index] = line;
   }

   public boolean isEditable() {
      return this.getSnapshot().h;
   }

   public void setEditable(boolean editable) {
      this.getSnapshot().h = editable;
   }

   public boolean isGlowingText() {
      return this.getSnapshot().v();
   }

   public void setGlowingText(boolean glowing) {
      this.getSnapshot().b(glowing);
   }

   public DyeColor getColor() {
      return DyeColor.getByWoolData((byte)this.getSnapshot().j().a());
   }

   public void setColor(DyeColor color) {
      this.getSnapshot().a(EnumColor.a(color.getWoolData()));
   }

   public void applyTo(T sign) {
      super.applyTo(sign);
      if (this.lines != null) {
         for(int i = 0; i < this.lines.length; ++i) {
            String line = this.lines[i] == null ? "" : this.lines[i];
            if (!line.equals(this.originalLines[i])) {
               sign.a(i, CraftChatMessage.fromString(line)[0]);
            }
         }
      }
   }

   public static void openSign(Sign sign, Player player) {
      Preconditions.checkArgument(sign != null, "sign == null");
      Preconditions.checkArgument(sign.isPlaced(), "Sign must be placed");
      Preconditions.checkArgument(sign.getWorld() == player.getWorld(), "Sign must be in same world as Player");
      TileEntitySign handle = (TileEntitySign)((CraftSign)sign).getTileEntity();
      handle.h = true;
      ((CraftPlayer)player).getHandle().a(handle);
   }

   public static IChatBaseComponent[] sanitizeLines(String[] lines) {
      IChatBaseComponent[] components = new IChatBaseComponent[4];

      for(int i = 0; i < 4; ++i) {
         if (i < lines.length && lines[i] != null) {
            components[i] = CraftChatMessage.fromString(lines[i])[0];
         } else {
            components[i] = IChatBaseComponent.h();
         }
      }

      return components;
   }

   public static String[] revertComponents(IChatBaseComponent[] components) {
      String[] lines = new String[components.length];

      for(int i = 0; i < lines.length; ++i) {
         lines[i] = revertComponent(components[i]);
      }

      return lines;
   }

   private static String revertComponent(IChatBaseComponent component) {
      return CraftChatMessage.fromComponent(component);
   }
}
