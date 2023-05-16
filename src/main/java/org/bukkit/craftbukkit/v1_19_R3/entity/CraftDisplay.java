package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Display.Brightness;
import org.bukkit.util.Transformation;

public class CraftDisplay extends CraftEntity implements Display {
   public CraftDisplay(CraftServer server, net.minecraft.world.entity.Display entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.Display getHandle() {
      return (net.minecraft.world.entity.Display)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftDisplay";
   }

   public EntityType getType() {
      return EntityType.UNKNOWN;
   }

   public Transformation getTransformation() {
      com.mojang.math.Transformation nms = net.minecraft.world.entity.Display.a(this.getHandle().aj());
      return new Transformation(nms.d(), nms.e(), nms.f(), nms.g());
   }

   public void setTransformation(Transformation transformation) {
      Preconditions.checkArgument(transformation != null, "Transformation cannot be null");
      this.getHandle()
         .a(
            new com.mojang.math.Transformation(
               transformation.getTranslation(), transformation.getLeftRotation(), transformation.getScale(), transformation.getRightRotation()
            )
         );
   }

   public int getInterpolationDuration() {
      return this.getHandle().o();
   }

   public void setInterpolationDuration(int duration) {
      this.getHandle().b(duration);
   }

   public float getViewRange() {
      return this.getHandle().r();
   }

   public void setViewRange(float range) {
      this.getHandle().g(range);
   }

   public float getShadowRadius() {
      return this.getHandle().s();
   }

   public void setShadowRadius(float radius) {
      this.getHandle().h(radius);
   }

   public float getShadowStrength() {
      return this.getHandle().t();
   }

   public void setShadowStrength(float strength) {
      this.getHandle().w(strength);
   }

   public float getDisplayWidth() {
      return this.getHandle().v();
   }

   public void setDisplayWidth(float width) {
      this.getHandle().x(width);
   }

   public float getDisplayHeight() {
      return this.getHandle().x();
   }

   public void setDisplayHeight(float height) {
      this.getHandle().y(height);
   }

   public int getInterpolationDelay() {
      return this.getHandle().p();
   }

   public void setInterpolationDelay(int ticks) {
      this.getHandle().c(ticks);
   }

   public Billboard getBillboard() {
      return Billboard.valueOf(this.getHandle().j().name());
   }

   public void setBillboard(Billboard billboard) {
      Preconditions.checkArgument(billboard != null, "Billboard cannot be null");
      this.getHandle().a(net.minecraft.world.entity.Display.BillboardConstraints.valueOf(billboard.name()));
   }

   public Color getGlowColorOverride() {
      int color = this.getHandle().w();
      return color == -1 ? null : Color.fromARGB(color);
   }

   public void setGlowColorOverride(Color color) {
      if (color == null) {
         this.getHandle().d(-1);
      } else {
         this.getHandle().d(color.asARGB());
      }
   }

   public Brightness getBrightness() {
      net.minecraft.util.Brightness nms = this.getHandle().q();
      return nms != null ? new Brightness(nms.b(), nms.c()) : null;
   }

   public void setBrightness(Brightness brightness) {
      if (brightness != null) {
         this.getHandle().a(new net.minecraft.util.Brightness(brightness.getBlockLight(), brightness.getSkyLight()));
      } else {
         this.getHandle().a(null);
      }
   }
}
