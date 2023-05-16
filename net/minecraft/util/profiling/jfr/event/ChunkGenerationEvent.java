package net.minecraft.util.profiling.jfr.event;

import jdk.jfr.Category;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.World;

@Name("minecraft.ChunkGeneration")
@Label("Chunk Generation")
@Category({"Minecraft", "World Generation"})
@StackTrace(false)
@Enabled(false)
@DontObfuscate
public class ChunkGenerationEvent extends Event {
   public static final String EVENT_NAME = "minecraft.ChunkGeneration";
   public static final EventType TYPE = EventType.getEventType(ChunkGenerationEvent.class);
   @Name("worldPosX")
   @Label("First Block X World Position")
   public final int worldPosX;
   @Name("worldPosZ")
   @Label("First Block Z World Position")
   public final int worldPosZ;
   @Name("chunkPosX")
   @Label("Chunk X Position")
   public final int chunkPosX;
   @Name("chunkPosZ")
   @Label("Chunk Z Position")
   public final int chunkPosZ;
   @Name("status")
   @Label("Status")
   public final String targetStatus;
   @Name("level")
   @Label("Level")
   public final String level;

   public ChunkGenerationEvent(ChunkCoordIntPair var0, ResourceKey<World> var1, String var2) {
      this.targetStatus = var2;
      this.level = var1.toString();
      this.chunkPosX = var0.e;
      this.chunkPosZ = var0.f;
      this.worldPosX = var0.d();
      this.worldPosZ = var0.e();
   }

   public static class a {
      public static final String a = "worldPosX";
      public static final String b = "worldPosZ";
      public static final String c = "chunkPosX";
      public static final String d = "chunkPosZ";
      public static final String e = "status";
      public static final String f = "level";

      private a() {
      }
   }
}
