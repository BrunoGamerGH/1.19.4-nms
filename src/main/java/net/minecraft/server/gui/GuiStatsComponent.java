package net.minecraft.server.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.SystemUtils;
import net.minecraft.server.MinecraftServer;

public class GuiStatsComponent extends JComponent {
   private static final DecimalFormat a = SystemUtils.a(
      new DecimalFormat("########0.000"), var0 -> var0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   private final int[] b = new int[256];
   private int c;
   private final String[] d = new String[11];
   private final MinecraftServer e;
   private final Timer f;

   public GuiStatsComponent(MinecraftServer var0) {
      this.e = var0;
      this.setPreferredSize(new Dimension(456, 246));
      this.setMinimumSize(new Dimension(456, 246));
      this.setMaximumSize(new Dimension(456, 246));
      this.f = new Timer(500, var0x -> this.b());
      this.f.start();
      this.setBackground(Color.BLACK);
   }

   private void b() {
      long var0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      this.d[0] = "Memory use: " + var0 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
      this.d[1] = "Avg tick: " + a.format(this.a(this.e.k) * 1.0E-6) + " ms";
      this.b[this.c++ & 0xFF] = (int)(var0 * 100L / Runtime.getRuntime().maxMemory());
      this.repaint();
   }

   private double a(long[] var0) {
      long var1 = 0L;

      for(long var6 : var0) {
         var1 += var6;
      }

      return (double)var1 / (double)var0.length;
   }

   @Override
   public void paint(Graphics var0) {
      var0.setColor(new Color(16777215));
      var0.fillRect(0, 0, 456, 246);

      for(int var1 = 0; var1 < 256; ++var1) {
         int var2 = this.b[var1 + this.c & 0xFF];
         var0.setColor(new Color(var2 + 28 << 16));
         var0.fillRect(var1, 100 - var2, 1, var2);
      }

      var0.setColor(Color.BLACK);

      for(int var1 = 0; var1 < this.d.length; ++var1) {
         String var2 = this.d[var1];
         if (var2 != null) {
            var0.drawString(var2, 32, 116 + var1 * 16);
         }
      }
   }

   public void a() {
      this.f.stop();
   }
}
