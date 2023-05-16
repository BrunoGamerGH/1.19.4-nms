package net.minecraft.network.protocol.game;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.List;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutTabComplete implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final Suggestions b;

   public PacketPlayOutTabComplete(int var0, Suggestions var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutTabComplete(PacketDataSerializer var0) {
      this.a = var0.m();
      int var1 = var0.m();
      int var2 = var0.m();
      StringRange var3 = StringRange.between(var1, var1 + var2);
      List<Suggestion> var4 = var0.a((PacketDataSerializer.a)(var1x -> {
         String var2x = var1x.s();
         IChatBaseComponent var3x = var1x.c(PacketDataSerializer::l);
         return new Suggestion(var3, var2x, var3x);
      }));
      this.b = new Suggestions(var3, var4);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.d(this.b.getRange().getStart());
      var0.d(this.b.getRange().getLength());
      var0.a(this.b.getList(), (var0x, var1x) -> {
         var0x.a(var1x.getText());
         var0x.a(var1x.getTooltip(), (var0xx, var1xx) -> var0xx.a(ChatComponentUtils.a(var1xx)));
      });
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public Suggestions c() {
      return this.b;
   }
}
