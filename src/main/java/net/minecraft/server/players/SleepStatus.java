package net.minecraft.server.players;

import java.util.Iterator;
import java.util.List;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.player.EntityHuman;

public class SleepStatus {
   private int a;
   private int b;

   public boolean a(int i) {
      return this.b >= this.b(i);
   }

   public boolean a(int i, List<EntityPlayer> list) {
      int j = (int)list.stream().filter(eh -> eh.fM() || eh.fauxSleeping).count();
      boolean anyDeepSleep = list.stream().anyMatch(EntityHuman::fM);
      return anyDeepSleep && j >= this.b(i);
   }

   public int b(int i) {
      return Math.max(1, MathHelper.f((float)(this.a * i) / 100.0F));
   }

   public void a() {
      this.b = 0;
   }

   public int b() {
      return this.b;
   }

   public boolean a(List<EntityPlayer> list) {
      int i = this.a;
      int j = this.b;
      this.a = 0;
      this.b = 0;
      Iterator iterator = list.iterator();
      boolean anySleep = false;

      while(iterator.hasNext()) {
         EntityPlayer entityplayer = (EntityPlayer)iterator.next();
         if (!entityplayer.F_()) {
            ++this.a;
            if (entityplayer.fu() || entityplayer.fauxSleeping) {
               ++this.b;
            }

            if (entityplayer.fu()) {
               anySleep = true;
            }
         }
      }

      return anySleep && (j > 0 || this.b > 0) && (i != this.a || j != this.b);
   }
}
