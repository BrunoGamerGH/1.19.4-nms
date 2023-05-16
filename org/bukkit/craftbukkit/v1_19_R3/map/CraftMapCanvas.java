package org.bukkit.craftbukkit.v1_19_R3.map;

import java.awt.Color;
import java.awt.Image;
import java.util.Arrays;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapFont.CharacterSprite;

public class CraftMapCanvas implements MapCanvas {
   private final byte[] buffer = new byte[16384];
   private final CraftMapView mapView;
   private byte[] base;
   private MapCursorCollection cursors = new MapCursorCollection();

   protected CraftMapCanvas(CraftMapView mapView) {
      this.mapView = mapView;
      Arrays.fill(this.buffer, (byte)-1);
   }

   public CraftMapView getMapView() {
      return this.mapView;
   }

   public MapCursorCollection getCursors() {
      return this.cursors;
   }

   public void setCursors(MapCursorCollection cursors) {
      this.cursors = cursors;
   }

   public void setPixelColor(int x, int y, Color color) {
      this.setPixel(x, y, color == null ? -1 : MapPalette.matchColor(color));
   }

   public Color getPixelColor(int x, int y) {
      byte pixel = this.getPixel(x, y);
      return pixel == -1 ? null : MapPalette.getColor(pixel);
   }

   public Color getBasePixelColor(int x, int y) {
      return MapPalette.getColor(this.getBasePixel(x, y));
   }

   public void setPixel(int x, int y, byte color) {
      if (x >= 0 && y >= 0 && x < 128 && y < 128) {
         if (this.buffer[y * 128 + x] != color) {
            this.buffer[y * 128 + x] = color;
            this.mapView.worldMap.a(x, y);
         }
      }
   }

   public byte getPixel(int x, int y) {
      return x >= 0 && y >= 0 && x < 128 && y < 128 ? this.buffer[y * 128 + x] : 0;
   }

   public byte getBasePixel(int x, int y) {
      return x >= 0 && y >= 0 && x < 128 && y < 128 ? this.base[y * 128 + x] : 0;
   }

   protected void setBase(byte[] base) {
      this.base = base;
   }

   protected byte[] getBuffer() {
      return this.buffer;
   }

   public void drawImage(int x, int y, Image image) {
      byte[] bytes = MapPalette.imageToBytes(image);

      for(int x2 = 0; x2 < image.getWidth(null); ++x2) {
         for(int y2 = 0; y2 < image.getHeight(null); ++y2) {
            this.setPixel(x + x2, y + y2, bytes[y2 * image.getWidth(null) + x2]);
         }
      }
   }

   public void drawText(int x, int y, MapFont font, String text) {
      int xStart = x;
      byte color = 44;
      if (!font.isValid(text)) {
         throw new IllegalArgumentException("text contains invalid characters");
      } else {
         int i = 0;

         while(true) {
            if (i >= text.length()) {
               return;
            }

            char ch = text.charAt(i);
            if (ch == '\n') {
               x = xStart;
               y += font.getHeight() + 1;
            } else if (ch == 167) {
               int j = text.indexOf(59, i);
               if (j < 0) {
                  break;
               }

               try {
                  color = Byte.parseByte(text.substring(i + 1, j));
                  i = j;
               } catch (NumberFormatException var12) {
                  break;
               }
            } else {
               CharacterSprite sprite = font.getChar(text.charAt(i));

               for(int r = 0; r < font.getHeight(); ++r) {
                  for(int c = 0; c < sprite.getWidth(); ++c) {
                     if (sprite.get(r, c)) {
                        this.setPixel(x + c, y + r, color);
                     }
                  }
               }

               x += sprite.getWidth() + 1;
            }

            ++i;
         }

         throw new IllegalArgumentException("Text contains unterminated color string");
      }
   }
}
