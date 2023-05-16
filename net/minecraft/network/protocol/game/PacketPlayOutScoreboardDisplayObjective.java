package net.minecraft.network.protocol.game;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.scores.ScoreboardObjective;

public class PacketPlayOutScoreboardDisplayObjective implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final String b;

   public PacketPlayOutScoreboardDisplayObjective(int var0, @Nullable ScoreboardObjective var1) {
      this.a = var0;
      if (var1 == null) {
         this.b = "";
      } else {
         this.b = var1.b();
      }
   }

   public PacketPlayOutScoreboardDisplayObjective(PacketDataSerializer var0) {
      this.a = var0.readByte();
      this.b = var0.s();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a);
      var0.a(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   @Nullable
   public String c() {
      return Objects.equals(this.b, "") ? null : this.b;
   }
}
