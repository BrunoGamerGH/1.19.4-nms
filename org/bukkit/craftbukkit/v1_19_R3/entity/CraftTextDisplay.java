package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.Display;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.TextDisplay.TextAligment;

public class CraftTextDisplay extends CraftDisplay implements TextDisplay {
   public CraftTextDisplay(CraftServer server, Display.TextDisplay entity) {
      super(server, entity);
   }

   public Display.TextDisplay getHandle() {
      return (Display.TextDisplay)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftTextDisplay";
   }

   @Override
   public EntityType getType() {
      return EntityType.TEXT_DISPLAY;
   }

   public String getText() {
      return CraftChatMessage.fromComponent(this.getHandle().o());
   }

   public void setText(String text) {
      this.getHandle().c(CraftChatMessage.fromString(text, true)[0]);
   }

   public int getLineWidth() {
      return this.getHandle().p();
   }

   public void setLineWidth(int width) {
      this.getHandle().b(width);
   }

   public Color getBackgroundColor() {
      int color = this.getHandle().s();
      return color == -1 ? null : Color.fromARGB(color);
   }

   public void setBackgroundColor(Color color) {
      if (color == null) {
         this.getHandle().c(-1);
      } else {
         this.getHandle().c(color.asARGB());
      }
   }

   public byte getTextOpacity() {
      return this.getHandle().r();
   }

   public void setTextOpacity(byte opacity) {
      this.getHandle().c(opacity);
   }

   public boolean isShadowed() {
      return this.getFlag(1);
   }

   public void setShadowed(boolean shadow) {
      this.setFlag(1, shadow);
   }

   public boolean isSeeThrough() {
      return this.getFlag(2);
   }

   public void setSeeThrough(boolean seeThrough) {
      this.setFlag(2, seeThrough);
   }

   public boolean isDefaultBackground() {
      return this.getFlag(4);
   }

   public void setDefaultBackground(boolean defaultBackground) {
      this.setFlag(4, defaultBackground);
   }

   public TextAligment getAlignment() {
      Display.TextDisplay.Align nms = Display.TextDisplay.a(this.getHandle().q());
      return TextAligment.valueOf(nms.name());
   }

   public void setAlignment(TextAligment alignment) {
      Preconditions.checkArgument(alignment != null, "Alignment cannot be null");
      switch(alignment) {
         case CENTER:
            this.setFlag(8, false);
            this.setFlag(16, false);
            break;
         case LEFT:
            this.setFlag(8, true);
            this.setFlag(16, false);
            break;
         case RIGHT:
            this.setFlag(8, false);
            this.setFlag(16, true);
            break;
         default:
            throw new IllegalArgumentException("Unknown alignment " + alignment);
      }
   }

   private boolean getFlag(int flag) {
      return (this.getHandle().q() & flag) != 0;
   }

   private void setFlag(int flag, boolean set) {
      byte flagBits = this.getHandle().q();
      if (set) {
         flagBits = (byte)(flagBits | flag);
      } else {
         flagBits = (byte)(flagBits & ~flag);
      }

      this.getHandle().d(flagBits);
   }
}
