package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.SystemUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;

public class DebugEntityNameGenerator {
   private static final String[] a = new String[]{
      "Slim",
      "Far",
      "River",
      "Silly",
      "Fat",
      "Thin",
      "Fish",
      "Bat",
      "Dark",
      "Oak",
      "Sly",
      "Bush",
      "Zen",
      "Bark",
      "Cry",
      "Slack",
      "Soup",
      "Grim",
      "Hook",
      "Dirt",
      "Mud",
      "Sad",
      "Hard",
      "Crook",
      "Sneak",
      "Stink",
      "Weird",
      "Fire",
      "Soot",
      "Soft",
      "Rough",
      "Cling",
      "Scar"
   };
   private static final String[] b = new String[]{
      "Fox",
      "Tail",
      "Jaw",
      "Whisper",
      "Twig",
      "Root",
      "Finder",
      "Nose",
      "Brow",
      "Blade",
      "Fry",
      "Seek",
      "Wart",
      "Tooth",
      "Foot",
      "Leaf",
      "Stone",
      "Fall",
      "Face",
      "Tongue",
      "Voice",
      "Lip",
      "Mouth",
      "Snail",
      "Toe",
      "Ear",
      "Hair",
      "Beard",
      "Shirt",
      "Fist"
   };

   public static String a(Entity var0) {
      if (var0 instanceof EntityHuman) {
         return var0.Z().getString();
      } else {
         IChatBaseComponent var1 = var0.ab();
         return var1 != null ? var1.getString() : a(var0.cs());
      }
   }

   public static String a(UUID var0) {
      RandomSource var1 = b(var0);
      return a(var1, a) + a(var1, b);
   }

   private static String a(RandomSource var0, String[] var1) {
      return SystemUtils.a(var1, var0);
   }

   private static RandomSource b(UUID var0) {
      return RandomSource.a((long)(var0.hashCode() >> 2));
   }
}
