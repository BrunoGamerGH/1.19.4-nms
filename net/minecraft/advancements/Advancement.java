package net.minecraft.advancements;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.advancements.critereon.LootDeserializationContext;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IMaterial;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.craftbukkit.v1_19_R3.advancement.CraftAdvancement;

public class Advancement {
   @Nullable
   private final Advancement a;
   @Nullable
   private final AdvancementDisplay b;
   private final AdvancementRewards c;
   private final MinecraftKey d;
   private final Map<String, Criterion> e;
   private final String[][] f;
   private final Set<Advancement> g = Sets.newLinkedHashSet();
   private final IChatBaseComponent h;
   public final org.bukkit.advancement.Advancement bukkit = new CraftAdvancement(this);

   public Advancement(
      MinecraftKey minecraftkey,
      @Nullable Advancement advancement,
      @Nullable AdvancementDisplay advancementdisplay,
      AdvancementRewards advancementrewards,
      Map<String, Criterion> map,
      String[][] astring
   ) {
      this.d = minecraftkey;
      this.b = advancementdisplay;
      this.e = ImmutableMap.copyOf(map);
      this.a = advancement;
      this.c = advancementrewards;
      this.f = astring;
      if (advancement != null) {
         advancement.b(this);
      }

      if (advancementdisplay == null) {
         this.h = IChatBaseComponent.b(minecraftkey.toString());
      } else {
         IChatBaseComponent ichatbasecomponent = advancementdisplay.a();
         EnumChatFormat enumchatformat = advancementdisplay.e().c();
         IChatMutableComponent ichatmutablecomponent = ChatComponentUtils.a(ichatbasecomponent.e(), ChatModifier.a.a(enumchatformat))
            .f("\n")
            .b(advancementdisplay.b());
         IChatMutableComponent ichatmutablecomponent1 = ichatbasecomponent.e()
            .a(chatmodifier -> chatmodifier.a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, ichatmutablecomponent)));
         this.h = ChatComponentUtils.a((IChatBaseComponent)ichatmutablecomponent1).a(enumchatformat);
      }
   }

   public Advancement.SerializedAdvancement a() {
      return new Advancement.SerializedAdvancement(this.a == null ? null : this.a.i(), this.b, this.c, this.e, this.f);
   }

   @Nullable
   public Advancement b() {
      return this.a;
   }

   public Advancement c() {
      return a(this);
   }

   public static Advancement a(Advancement advancement) {
      Advancement advancement1 = advancement;

      while(true) {
         Advancement advancement2 = advancement1.b();
         if (advancement2 == null) {
            return advancement1;
         }

         advancement1 = advancement2;
      }
   }

   @Nullable
   public AdvancementDisplay d() {
      return this.b;
   }

   public AdvancementRewards e() {
      return this.c;
   }

   @Override
   public String toString() {
      MinecraftKey minecraftkey = this.i();
      return "SimpleAdvancement{id="
         + minecraftkey
         + ", parent="
         + (this.a == null ? "null" : this.a.i())
         + ", display="
         + this.b
         + ", rewards="
         + this.c
         + ", criteria="
         + this.e
         + ", requirements="
         + Arrays.deepToString(this.f)
         + "}";
   }

   public Iterable<Advancement> f() {
      return this.g;
   }

   public Map<String, Criterion> g() {
      return this.e;
   }

   public int h() {
      return this.f.length;
   }

   public void b(Advancement advancement) {
      this.g.add(advancement);
   }

   public MinecraftKey i() {
      return this.d;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof Advancement)) {
         return false;
      } else {
         Advancement advancement = (Advancement)object;
         return this.d.equals(advancement.d);
      }
   }

   @Override
   public int hashCode() {
      return this.d.hashCode();
   }

   public String[][] j() {
      return this.f;
   }

   public IChatBaseComponent k() {
      return this.h;
   }

   public static class SerializedAdvancement {
      @Nullable
      private MinecraftKey a;
      @Nullable
      private Advancement b;
      @Nullable
      private AdvancementDisplay c;
      private AdvancementRewards d = AdvancementRewards.a;
      private Map<String, Criterion> e = Maps.newLinkedHashMap();
      @Nullable
      private String[][] f;
      private AdvancementRequirements g = AdvancementRequirements.a;

      SerializedAdvancement(
         @Nullable MinecraftKey minecraftkey,
         @Nullable AdvancementDisplay advancementdisplay,
         AdvancementRewards advancementrewards,
         Map<String, Criterion> map,
         String[][] astring
      ) {
         this.a = minecraftkey;
         this.c = advancementdisplay;
         this.d = advancementrewards;
         this.e = map;
         this.f = astring;
      }

      private SerializedAdvancement() {
      }

      public static Advancement.SerializedAdvancement a() {
         return new Advancement.SerializedAdvancement();
      }

      public Advancement.SerializedAdvancement a(Advancement advancement) {
         this.b = advancement;
         return this;
      }

      public Advancement.SerializedAdvancement a(MinecraftKey minecraftkey) {
         this.a = minecraftkey;
         return this;
      }

      public Advancement.SerializedAdvancement a(
         ItemStack itemstack,
         IChatBaseComponent ichatbasecomponent,
         IChatBaseComponent ichatbasecomponent1,
         @Nullable MinecraftKey minecraftkey,
         AdvancementFrameType advancementframetype,
         boolean flag,
         boolean flag1,
         boolean flag2
      ) {
         return this.a(new AdvancementDisplay(itemstack, ichatbasecomponent, ichatbasecomponent1, minecraftkey, advancementframetype, flag, flag1, flag2));
      }

      public Advancement.SerializedAdvancement a(
         IMaterial imaterial,
         IChatBaseComponent ichatbasecomponent,
         IChatBaseComponent ichatbasecomponent1,
         @Nullable MinecraftKey minecraftkey,
         AdvancementFrameType advancementframetype,
         boolean flag,
         boolean flag1,
         boolean flag2
      ) {
         return this.a(
            new AdvancementDisplay(
               new ItemStack(imaterial.k()), ichatbasecomponent, ichatbasecomponent1, minecraftkey, advancementframetype, flag, flag1, flag2
            )
         );
      }

      public Advancement.SerializedAdvancement a(AdvancementDisplay advancementdisplay) {
         this.c = advancementdisplay;
         return this;
      }

      public Advancement.SerializedAdvancement a(AdvancementRewards.a advancementrewards_a) {
         return this.a(advancementrewards_a.a());
      }

      public Advancement.SerializedAdvancement a(AdvancementRewards advancementrewards) {
         this.d = advancementrewards;
         return this;
      }

      public Advancement.SerializedAdvancement a(String s, CriterionInstance criterioninstance) {
         return this.a(s, new Criterion(criterioninstance));
      }

      public Advancement.SerializedAdvancement a(String s, Criterion criterion) {
         if (this.e.containsKey(s)) {
            throw new IllegalArgumentException("Duplicate criterion " + s);
         } else {
            this.e.put(s, criterion);
            return this;
         }
      }

      public Advancement.SerializedAdvancement a(AdvancementRequirements advancementrequirements) {
         this.g = advancementrequirements;
         return this;
      }

      public Advancement.SerializedAdvancement a(String[][] astring) {
         this.f = astring;
         return this;
      }

      public boolean a(Function<MinecraftKey, Advancement> function) {
         if (this.a == null) {
            return true;
         } else {
            if (this.b == null) {
               this.b = function.apply(this.a);
            }

            return this.b != null;
         }
      }

      public Advancement b(MinecraftKey minecraftkey) {
         if (!this.a(minecraftkey1 -> null)) {
            throw new IllegalStateException("Tried to build incomplete advancement!");
         } else {
            if (this.f == null) {
               this.f = this.g.createRequirements(this.e.keySet());
            }

            return new Advancement(minecraftkey, this.b, this.c, this.d, this.e, this.f);
         }
      }

      public Advancement a(Consumer<Advancement> consumer, String s) {
         Advancement advancement = this.b(new MinecraftKey(s));
         consumer.accept(advancement);
         return advancement;
      }

      public JsonObject b() {
         if (this.f == null) {
            this.f = this.g.createRequirements(this.e.keySet());
         }

         JsonObject jsonobject = new JsonObject();
         if (this.b != null) {
            jsonobject.addProperty("parent", this.b.i().toString());
         } else if (this.a != null) {
            jsonobject.addProperty("parent", this.a.toString());
         }

         if (this.c != null) {
            jsonobject.add("display", this.c.k());
         }

         jsonobject.add("rewards", this.d.b());
         JsonObject jsonobject1 = new JsonObject();

         for(Entry<String, Criterion> entry : this.e.entrySet()) {
            jsonobject1.add(entry.getKey(), entry.getValue().b());
         }

         jsonobject.add("criteria", jsonobject1);
         JsonArray jsonarray = new JsonArray();

         for(String[] astring1 : this.f) {
            JsonArray jsonarray1 = new JsonArray();

            for(String s : astring1) {
               jsonarray1.add(s);
            }

            jsonarray.add(jsonarray1);
         }

         jsonobject.add("requirements", jsonarray);
         return jsonobject;
      }

      public void a(PacketDataSerializer packetdataserializer) {
         if (this.f == null) {
            this.f = this.g.createRequirements(this.e.keySet());
         }

         packetdataserializer.a(this.a, PacketDataSerializer::a);
         packetdataserializer.a(this.c, (packetdataserializer1, advancementdisplay) -> advancementdisplay.a(packetdataserializer1));
         Criterion.a(this.e, packetdataserializer);
         packetdataserializer.d(this.f.length);

         for(String[] astring1 : this.f) {
            packetdataserializer.d(astring1.length);

            for(String s : astring1) {
               packetdataserializer.a(s);
            }
         }
      }

      @Override
      public String toString() {
         return "Task Advancement{parentId="
            + this.a
            + ", display="
            + this.c
            + ", rewards="
            + this.d
            + ", criteria="
            + this.e
            + ", requirements="
            + Arrays.deepToString(this.f)
            + "}";
      }

      public static Advancement.SerializedAdvancement a(JsonObject jsonobject, LootDeserializationContext lootdeserializationcontext) {
         MinecraftKey minecraftkey = jsonobject.has("parent") ? new MinecraftKey(ChatDeserializer.h(jsonobject, "parent")) : null;
         AdvancementDisplay advancementdisplay = jsonobject.has("display") ? AdvancementDisplay.a(ChatDeserializer.t(jsonobject, "display")) : null;
         AdvancementRewards advancementrewards = jsonobject.has("rewards")
            ? AdvancementRewards.a(ChatDeserializer.t(jsonobject, "rewards"))
            : AdvancementRewards.a;
         Map<String, Criterion> map = Criterion.b(ChatDeserializer.t(jsonobject, "criteria"), lootdeserializationcontext);
         if (map.isEmpty()) {
            throw new JsonSyntaxException("Advancement criteria cannot be empty");
         } else {
            JsonArray jsonarray = ChatDeserializer.a(jsonobject, "requirements", new JsonArray());
            String[][] astring = new String[jsonarray.size()][];

            for(int i = 0; i < jsonarray.size(); ++i) {
               JsonArray jsonarray1 = ChatDeserializer.n(jsonarray.get(i), "requirements[" + i + "]");
               astring[i] = new String[jsonarray1.size()];

               for(int j = 0; j < jsonarray1.size(); ++j) {
                  astring[i][j] = ChatDeserializer.a(jsonarray1.get(j), "requirements[" + i + "][" + j + "]");
               }
            }

            if (astring.length == 0) {
               astring = new String[map.size()][];
               int var19 = 0;

               for(String s : map.keySet()) {
                  astring[var19++] = new String[]{s};
               }
            }

            for(String[] astring2 : astring) {
               if (astring2.length == 0 && map.isEmpty()) {
                  throw new JsonSyntaxException("Requirement entry cannot be empty");
               }

               for(String s1 : astring2) {
                  if (!map.containsKey(s1)) {
                     throw new JsonSyntaxException("Unknown required criterion '" + s1 + "'");
                  }
               }
            }

            for(String s2 : map.keySet()) {
               boolean flag = false;

               for(String[] astring5 : astring) {
                  if (ArrayUtils.contains(astring5, s2)) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  throw new JsonSyntaxException(
                     "Criterion '" + s2 + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required."
                  );
               }
            }

            return new Advancement.SerializedAdvancement(minecraftkey, advancementdisplay, advancementrewards, map, astring);
         }
      }

      public static Advancement.SerializedAdvancement b(PacketDataSerializer packetdataserializer) {
         MinecraftKey minecraftkey = packetdataserializer.c(PacketDataSerializer::t);
         AdvancementDisplay advancementdisplay = packetdataserializer.c(AdvancementDisplay::b);
         Map<String, Criterion> map = Criterion.c(packetdataserializer);
         String[][] astring = new String[packetdataserializer.m()][];

         for(int i = 0; i < astring.length; ++i) {
            astring[i] = new String[packetdataserializer.m()];

            for(int j = 0; j < astring[i].length; ++j) {
               astring[i][j] = packetdataserializer.s();
            }
         }

         return new Advancement.SerializedAdvancement(minecraftkey, advancementdisplay, AdvancementRewards.a, map, astring);
      }

      public Map<String, Criterion> c() {
         return this.e;
      }
   }
}
