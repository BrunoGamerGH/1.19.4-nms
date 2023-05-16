package org.bukkit.craftbukkit.v1_19_R3.map;

import net.minecraft.world.level.saveddata.maps.MapIcon;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class CraftMapRenderer extends MapRenderer {
   private final WorldMap worldMap;

   public CraftMapRenderer(CraftMapView mapView, WorldMap worldMap) {
      super(false);
      this.worldMap = worldMap;
   }

   public void render(MapView map, MapCanvas canvas, Player player) {
      for(int x = 0; x < 128; ++x) {
         for(int y = 0; y < 128; ++y) {
            canvas.setPixel(x, y, this.worldMap.g[y * 128 + x]);
         }
      }

      MapCursorCollection cursors = canvas.getCursors();

      while(cursors.size() > 0) {
         cursors.removeCursor(cursors.getCursor(0));
      }

      for(String key : this.worldMap.q.keySet()) {
         Player other = Bukkit.getPlayerExact(key);
         if (other == null || player.canSee(other)) {
            MapIcon decoration = this.worldMap.q.get(key);
            cursors.addCursor(
               decoration.c(), decoration.d(), (byte)(decoration.e() & 15), decoration.b().a(), true, CraftChatMessage.fromComponent(decoration.g())
            );
         }
      }
   }
}
