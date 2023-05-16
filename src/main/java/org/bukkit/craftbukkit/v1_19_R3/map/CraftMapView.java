package org.bukkit.craftbukkit.v1_19_R3.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.World;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public final class CraftMapView implements MapView {
   private final Map<CraftPlayer, RenderData> renderCache = new HashMap<>();
   private final List<MapRenderer> renderers = new ArrayList();
   private final Map<MapRenderer, Map<CraftPlayer, CraftMapCanvas>> canvases = new HashMap<>();
   protected final WorldMap worldMap;

   public CraftMapView(WorldMap worldMap) {
      this.worldMap = worldMap;
      this.addRenderer(new CraftMapRenderer(this, worldMap));
   }

   public int getId() {
      String text = this.worldMap.id;
      if (text.startsWith("map_")) {
         try {
            return Integer.parseInt(text.substring("map_".length()));
         } catch (NumberFormatException var3) {
            throw new IllegalStateException("Map has non-numeric ID");
         }
      } else {
         throw new IllegalStateException("Map has invalid ID");
      }
   }

   public boolean isVirtual() {
      return this.renderers.size() > 0 && !(this.renderers.get(0) instanceof CraftMapRenderer);
   }

   public Scale getScale() {
      return Scale.valueOf(this.worldMap.f);
   }

   public void setScale(Scale scale) {
      this.worldMap.f = scale.getValue();
   }

   public org.bukkit.World getWorld() {
      ResourceKey<World> dimension = this.worldMap.e;
      WorldServer world = MinecraftServer.getServer().a(dimension);
      return world == null ? null : world.getWorld();
   }

   public void setWorld(org.bukkit.World world) {
      this.worldMap.e = ((CraftWorld)world).getHandle().ab();
   }

   public int getCenterX() {
      return this.worldMap.c;
   }

   public int getCenterZ() {
      return this.worldMap.d;
   }

   public void setCenterX(int x) {
      this.worldMap.c = x;
   }

   public void setCenterZ(int z) {
      this.worldMap.d = z;
   }

   public List<MapRenderer> getRenderers() {
      return new ArrayList(this.renderers);
   }

   public void addRenderer(MapRenderer renderer) {
      if (!this.renderers.contains(renderer)) {
         this.renderers.add(renderer);
         this.canvases.put(renderer, new HashMap<>());
         renderer.initialize(this);
      }
   }

   public boolean removeRenderer(MapRenderer renderer) {
      if (!this.renderers.contains(renderer)) {
         return false;
      } else {
         this.renderers.remove(renderer);

         for(Entry<CraftPlayer, CraftMapCanvas> entry : this.canvases.get(renderer).entrySet()) {
            for(int x = 0; x < 128; ++x) {
               for(int y = 0; y < 128; ++y) {
                  entry.getValue().setPixel(x, y, (byte)-1);
               }
            }
         }

         this.canvases.remove(renderer);
         return true;
      }
   }

   private boolean isContextual() {
      for(MapRenderer renderer : this.renderers) {
         if (renderer.isContextual()) {
            return true;
         }
      }

      return false;
   }

   public RenderData render(CraftPlayer player) {
      boolean context = this.isContextual();
      RenderData render = this.renderCache.get(context ? player : null);
      if (render == null) {
         render = new RenderData();
         this.renderCache.put(context ? player : null, render);
      }

      if (context && this.renderCache.containsKey(null)) {
         this.renderCache.remove(null);
      }

      Arrays.fill(render.buffer, (byte)0);
      render.cursors.clear();

      for(MapRenderer renderer : this.renderers) {
         CraftMapCanvas canvas = this.canvases.get(renderer).get(renderer.isContextual() ? player : null);
         if (canvas == null) {
            canvas = new CraftMapCanvas(this);
            this.canvases.get(renderer).put(renderer.isContextual() ? player : null, canvas);
         }

         canvas.setBase(render.buffer);

         try {
            renderer.render(this, canvas, player);
         } catch (Throwable var10) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not render map using renderer " + renderer.getClass().getName(), var10);
         }

         byte[] buf = canvas.getBuffer();

         for(int i = 0; i < buf.length; ++i) {
            byte color = buf[i];
            if (color >= 0 || color <= -9) {
               render.buffer[i] = color;
            }
         }

         for(int i = 0; i < canvas.getCursors().size(); ++i) {
            render.cursors.add(canvas.getCursors().getCursor(i));
         }
      }

      return render;
   }

   public boolean isTrackingPosition() {
      return this.worldMap.l;
   }

   public void setTrackingPosition(boolean trackingPosition) {
      this.worldMap.l = trackingPosition;
   }

   public boolean isUnlimitedTracking() {
      return this.worldMap.m;
   }

   public void setUnlimitedTracking(boolean unlimited) {
      this.worldMap.m = unlimited;
   }

   public boolean isLocked() {
      return this.worldMap.h;
   }

   public void setLocked(boolean locked) {
      this.worldMap.h = locked;
   }
}
