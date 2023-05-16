package net.minecraft.server.gui;

import java.util.Vector;
import javax.swing.JList;
import net.minecraft.server.MinecraftServer;

public class PlayerListBox extends JList<String> {
   private final MinecraftServer a;
   private int b;

   public PlayerListBox(MinecraftServer var0) {
      this.a = var0;
      var0.b(this::a);
   }

   public void a() {
      if (this.b++ % 20 == 0) {
         Vector<String> var0 = new Vector<>();

         for(int var1 = 0; var1 < this.a.ac().t().size(); ++var1) {
            var0.add(this.a.ac().t().get(var1).fI().getName());
         }

         this.setListData(var0);
      }
   }
}
