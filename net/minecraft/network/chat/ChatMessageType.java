package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;

public record ChatMessageType(ChatDecoration chat, ChatDecoration narration) {
   private final ChatDecoration j;
   private final ChatDecoration k;
   public static final Codec<ChatMessageType> a = RecordCodecBuilder.create(
      instance -> instance.group(
               ChatDecoration.a.fieldOf("chat").forGetter(ChatMessageType::a), ChatDecoration.a.fieldOf("narration").forGetter(ChatMessageType::b)
            )
            .apply(instance, ChatMessageType::new)
   );
   public static final ChatDecoration b = ChatDecoration.a("chat.type.text");
   public static final ResourceKey<ChatMessageType> c = a("chat");
   public static final ResourceKey<ChatMessageType> d = a("say_command");
   public static final ResourceKey<ChatMessageType> e = a("msg_command_incoming");
   public static final ResourceKey<ChatMessageType> f = a("msg_command_outgoing");
   public static final ResourceKey<ChatMessageType> g = a("team_msg_command_incoming");
   public static final ResourceKey<ChatMessageType> h = a("team_msg_command_outgoing");
   public static final ResourceKey<ChatMessageType> i = a("emote_command");
   public static final ResourceKey<ChatMessageType> RAW = a("raw");

   public ChatMessageType(ChatDecoration chat, ChatDecoration narration) {
      this.j = chat;
      this.k = narration;
   }

   private static ResourceKey<ChatMessageType> a(String s) {
      return ResourceKey.a(Registries.ao, new MinecraftKey(s));
   }

   public static void a(BootstapContext<ChatMessageType> bootstapcontext) {
      bootstapcontext.a(c, new ChatMessageType(b, ChatDecoration.a("chat.type.text.narrate")));
      bootstapcontext.a(d, new ChatMessageType(ChatDecoration.a("chat.type.announcement"), ChatDecoration.a("chat.type.text.narrate")));
      bootstapcontext.a(e, new ChatMessageType(ChatDecoration.b("commands.message.display.incoming"), ChatDecoration.a("chat.type.text.narrate")));
      bootstapcontext.a(f, new ChatMessageType(ChatDecoration.c("commands.message.display.outgoing"), ChatDecoration.a("chat.type.text.narrate")));
      bootstapcontext.a(g, new ChatMessageType(ChatDecoration.d("chat.type.team.text"), ChatDecoration.a("chat.type.text.narrate")));
      bootstapcontext.a(h, new ChatMessageType(ChatDecoration.d("chat.type.team.sent"), ChatDecoration.a("chat.type.text.narrate")));
      bootstapcontext.a(i, new ChatMessageType(ChatDecoration.a("chat.type.emote"), ChatDecoration.a("chat.type.emote")));
      bootstapcontext.a(
         RAW,
         new ChatMessageType(
            new ChatDecoration("%s", List.of(ChatDecoration.a.c), ChatModifier.a), new ChatDecoration("%s", List.of(ChatDecoration.a.c), ChatModifier.a)
         )
      );
   }

   public static ChatMessageType.a a(ResourceKey<ChatMessageType> resourcekey, Entity entity) {
      return a(resourcekey, entity.H.u_(), entity.G_());
   }

   public static ChatMessageType.a a(ResourceKey<ChatMessageType> resourcekey, CommandListenerWrapper commandlistenerwrapper) {
      return a(resourcekey, commandlistenerwrapper.u(), commandlistenerwrapper.b());
   }

   public static ChatMessageType.a a(ResourceKey<ChatMessageType> resourcekey, IRegistryCustom iregistrycustom, IChatBaseComponent ichatbasecomponent) {
      IRegistry<ChatMessageType> iregistry = iregistrycustom.d(Registries.ao);
      return iregistry.e(resourcekey).a(ichatbasecomponent);
   }

   public ChatMessageType.a a(IChatBaseComponent ichatbasecomponent) {
      return new ChatMessageType.a(this, ichatbasecomponent);
   }

   public ChatDecoration a() {
      return this.j;
   }

   public ChatDecoration b() {
      return this.k;
   }

   public static record a(ChatMessageType chatType, IChatBaseComponent name, @Nullable IChatBaseComponent targetName) {
      private final ChatMessageType a;
      private final IChatBaseComponent b;
      @Nullable
      private final IChatBaseComponent c;

      a(ChatMessageType chatmessagetype, IChatBaseComponent ichatbasecomponent) {
         this(chatmessagetype, ichatbasecomponent, null);
      }

      public IChatBaseComponent a(IChatBaseComponent ichatbasecomponent) {
         return this.a.a().a(ichatbasecomponent, this);
      }

      public IChatBaseComponent b(IChatBaseComponent ichatbasecomponent) {
         return this.a.b().a(ichatbasecomponent, this);
      }

      public ChatMessageType.a c(IChatBaseComponent ichatbasecomponent) {
         return new ChatMessageType.a(this.a, this.b, ichatbasecomponent);
      }

      public ChatMessageType.b a(IRegistryCustom iregistrycustom) {
         IRegistry<ChatMessageType> iregistry = iregistrycustom.d(Registries.ao);
         return new ChatMessageType.b(iregistry.a(this.a), this.b, this.c);
      }

      public a(ChatMessageType var1, IChatBaseComponent var2, IChatBaseComponent var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }
   }

   public static record b(int chatType, IChatBaseComponent name, @Nullable IChatBaseComponent targetName) {
      private final int a;
      private final IChatBaseComponent b;
      @Nullable
      private final IChatBaseComponent c;

      public b(PacketDataSerializer packetdataserializer) {
         this(packetdataserializer.m(), packetdataserializer.l(), packetdataserializer.c(PacketDataSerializer::l));
      }

      public void a(PacketDataSerializer packetdataserializer) {
         packetdataserializer.d(this.a);
         packetdataserializer.a(this.b);
         packetdataserializer.a(this.c, PacketDataSerializer::a);
      }

      public Optional<ChatMessageType.a> a(IRegistryCustom iregistrycustom) {
         IRegistry<ChatMessageType> iregistry = iregistrycustom.d(Registries.ao);
         ChatMessageType chatmessagetype = iregistry.a(this.a);
         return Optional.ofNullable(chatmessagetype).map(chatmessagetype1 -> new ChatMessageType.a(chatmessagetype1, this.b, this.c));
      }

      public b(int var1, IChatBaseComponent var2, IChatBaseComponent var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }
   }
}
