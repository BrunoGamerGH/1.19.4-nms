package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;

public class PacketPlayOutScoreboardObjective implements Packet<PacketListenerPlayOut> {
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;
   private final String d;
   private final IChatBaseComponent e;
   private final IScoreboardCriteria.EnumScoreboardHealthDisplay f;
   private final int g;

   public PacketPlayOutScoreboardObjective(ScoreboardObjective var0, int var1) {
      this.d = var0.b();
      this.e = var0.d();
      this.f = var0.f();
      this.g = var1;
   }

   public PacketPlayOutScoreboardObjective(PacketDataSerializer var0) {
      this.d = var0.s();
      this.g = var0.readByte();
      if (this.g != 0 && this.g != 2) {
         this.e = CommonComponents.a;
         this.f = IScoreboardCriteria.EnumScoreboardHealthDisplay.a;
      } else {
         this.e = var0.l();
         this.f = var0.b(IScoreboardCriteria.EnumScoreboardHealthDisplay.class);
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.d);
      var0.writeByte(this.g);
      if (this.g == 0 || this.g == 2) {
         var0.a(this.e);
         var0.a(this.f);
      }
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public String a() {
      return this.d;
   }

   public IChatBaseComponent c() {
      return this.e;
   }

   public int d() {
      return this.g;
   }

   public IScoreboardCriteria.EnumScoreboardHealthDisplay e() {
      return this.f;
   }
}
