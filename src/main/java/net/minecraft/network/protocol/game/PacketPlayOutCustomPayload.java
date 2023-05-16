package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;

public class PacketPlayOutCustomPayload implements Packet<PacketListenerPlayOut> {
   private static final int s = 1048576;
   public static final MinecraftKey a = new MinecraftKey("brand");
   public static final MinecraftKey b = new MinecraftKey("debug/path");
   public static final MinecraftKey c = new MinecraftKey("debug/neighbors_update");
   public static final MinecraftKey d = new MinecraftKey("debug/structures");
   public static final MinecraftKey e = new MinecraftKey("debug/worldgen_attempt");
   public static final MinecraftKey f = new MinecraftKey("debug/poi_ticket_count");
   public static final MinecraftKey g = new MinecraftKey("debug/poi_added");
   public static final MinecraftKey h = new MinecraftKey("debug/poi_removed");
   public static final MinecraftKey i = new MinecraftKey("debug/village_sections");
   public static final MinecraftKey j = new MinecraftKey("debug/goal_selector");
   public static final MinecraftKey k = new MinecraftKey("debug/brain");
   public static final MinecraftKey l = new MinecraftKey("debug/bee");
   public static final MinecraftKey m = new MinecraftKey("debug/hive");
   public static final MinecraftKey n = new MinecraftKey("debug/game_test_add_marker");
   public static final MinecraftKey o = new MinecraftKey("debug/game_test_clear");
   public static final MinecraftKey p = new MinecraftKey("debug/raids");
   public static final MinecraftKey q = new MinecraftKey("debug/game_event");
   public static final MinecraftKey r = new MinecraftKey("debug/game_event_listeners");
   private final MinecraftKey t;
   private final PacketDataSerializer u;

   public PacketPlayOutCustomPayload(MinecraftKey var0, PacketDataSerializer var1) {
      this.t = var0;
      this.u = var1;
      if (var1.writerIndex() > 1048576) {
         throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
      }
   }

   public PacketPlayOutCustomPayload(PacketDataSerializer var0) {
      this.t = var0.t();
      int var1 = var0.readableBytes();
      if (var1 >= 0 && var1 <= 1048576) {
         this.u = new PacketDataSerializer(var0.readBytes(var1));
      } else {
         throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.t);
      var0.writeBytes(this.u.copy());
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public MinecraftKey a() {
      return this.t;
   }

   public PacketDataSerializer c() {
      return new PacketDataSerializer(this.u.copy());
   }
}
