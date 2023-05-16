package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.UtilColor;
import net.minecraft.world.level.storage.SavedFile;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class NameReferencingFileConverter {
   static final Logger e = LogUtils.getLogger();
   public static final File a = new File("banned-ips.txt");
   public static final File b = new File("banned-players.txt");
   public static final File c = new File("ops.txt");
   public static final File d = new File("white-list.txt");

   static List<String> a(File file, Map<String, String[]> map) throws IOException {
      List<String> list = Files.readLines(file, StandardCharsets.UTF_8);

      for(String s : list) {
         s = s.trim();
         if (!s.startsWith("#") && s.length() >= 1) {
            String[] astring = s.split("\\|");
            map.put(astring[0].toLowerCase(Locale.ROOT), astring);
         }
      }

      return list;
   }

   private static void a(MinecraftServer minecraftserver, Collection<String> collection, ProfileLookupCallback profilelookupcallback) {
      String[] astring = collection.stream().filter(sx -> !UtilColor.b(sx)).toArray(i -> new String[i]);
      if (!minecraftserver.U() && !SpigotConfig.bungee) {
         for(String s : astring) {
            UUID uuid = UUIDUtil.a(new GameProfile(null, s));
            GameProfile gameprofile = new GameProfile(uuid, s);
            profilelookupcallback.onProfileLookupSucceeded(gameprofile);
         }
      } else {
         minecraftserver.ao().findProfilesByNames(astring, Agent.MINECRAFT, profilelookupcallback);
      }
   }

   public static boolean a(final MinecraftServer minecraftserver) {
      final GameProfileBanList gameprofilebanlist = new GameProfileBanList(PlayerList.b);
      if (b.exists() && b.isFile()) {
         if (gameprofilebanlist.b().exists()) {
            try {
               gameprofilebanlist.f();
            } catch (IOException var6) {
               e.warn("Could not load existing file {}", gameprofilebanlist.b().getName());
            }
         }

         try {
            final Map<String, String[]> map = Maps.newHashMap();
            a(b, map);
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile gameprofile) {
                  minecraftserver.ap().a(gameprofile);
                  String[] astring = (String[])map.get(gameprofile.getName().toLowerCase(Locale.ROOT));
                  if (astring == null) {
                     NameReferencingFileConverter.e.warn("Could not convert user banlist entry for {}", gameprofile.getName());
                     throw new NameReferencingFileConverter.FileConversionException("Profile not in the conversionlist");
                  } else {
                     Date date = astring.length > 1 ? NameReferencingFileConverter.a(astring[1], null) : null;
                     String s = astring.length > 2 ? astring[2] : null;
                     Date date1 = astring.length > 3 ? NameReferencingFileConverter.a(astring[3], null) : null;
                     String s1 = astring.length > 4 ? astring[4] : null;
                     gameprofilebanlist.a(new GameProfileBanEntry(gameprofile, date, s, date1, s1));
                  }
               }

               public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                  NameReferencingFileConverter.e.warn("Could not lookup user banlist entry for {}", gameprofile.getName(), exception);
                  if (!(exception instanceof ProfileNotFoundException)) {
                     throw new NameReferencingFileConverter.FileConversionException(
                        "Could not request user " + gameprofile.getName() + " from backend systems", exception
                     );
                  }
               }
            };
            a(minecraftserver, map.keySet(), profilelookupcallback);
            gameprofilebanlist.e();
            b(b);
            return true;
         } catch (IOException var4) {
            e.warn("Could not read old user banlist to convert it!", var4);
            return false;
         } catch (NameReferencingFileConverter.FileConversionException var5) {
            e.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean b(MinecraftServer minecraftserver) {
      IpBanList ipbanlist = new IpBanList(PlayerList.c);
      if (a.exists() && a.isFile()) {
         if (ipbanlist.b().exists()) {
            try {
               ipbanlist.f();
            } catch (IOException var11) {
               e.warn("Could not load existing file {}", ipbanlist.b().getName());
            }
         }

         try {
            Map<String, String[]> map = Maps.newHashMap();
            a(a, map);

            for(String s : map.keySet()) {
               String[] astring = (String[])map.get(s);
               Date date = astring.length > 1 ? a(astring[1], null) : null;
               String s1 = astring.length > 2 ? astring[2] : null;
               Date date1 = astring.length > 3 ? a(astring[3], null) : null;
               String s2 = astring.length > 4 ? astring[4] : null;
               ipbanlist.a(new IpBanEntry(s, date, s1, date1, s2));
            }

            ipbanlist.e();
            b(a);
            return true;
         } catch (IOException var10) {
            e.warn("Could not parse old ip banlist to convert it!", var10);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean c(final MinecraftServer minecraftserver) {
      final OpList oplist = new OpList(PlayerList.d);
      if (c.exists() && c.isFile()) {
         if (oplist.b().exists()) {
            try {
               oplist.f();
            } catch (IOException var6) {
               e.warn("Could not load existing file {}", oplist.b().getName());
            }
         }

         try {
            List<String> list = Files.readLines(c, StandardCharsets.UTF_8);
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile gameprofile) {
                  minecraftserver.ap().a(gameprofile);
                  oplist.a(new OpListEntry(gameprofile, minecraftserver.i(), false));
               }

               public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                  NameReferencingFileConverter.e.warn("Could not lookup oplist entry for {}", gameprofile.getName(), exception);
                  if (!(exception instanceof ProfileNotFoundException)) {
                     throw new NameReferencingFileConverter.FileConversionException(
                        "Could not request user " + gameprofile.getName() + " from backend systems", exception
                     );
                  }
               }
            };
            a(minecraftserver, list, profilelookupcallback);
            oplist.e();
            b(c);
            return true;
         } catch (IOException var4) {
            e.warn("Could not read old oplist to convert it!", var4);
            return false;
         } catch (NameReferencingFileConverter.FileConversionException var5) {
            e.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean d(final MinecraftServer minecraftserver) {
      final WhiteList whitelist = new WhiteList(PlayerList.e);
      if (d.exists() && d.isFile()) {
         if (whitelist.b().exists()) {
            try {
               whitelist.f();
            } catch (IOException var6) {
               e.warn("Could not load existing file {}", whitelist.b().getName());
            }
         }

         try {
            List<String> list = Files.readLines(d, StandardCharsets.UTF_8);
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile gameprofile) {
                  minecraftserver.ap().a(gameprofile);
                  whitelist.a(new WhiteListEntry(gameprofile));
               }

               public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                  NameReferencingFileConverter.e.warn("Could not lookup user whitelist entry for {}", gameprofile.getName(), exception);
                  if (!(exception instanceof ProfileNotFoundException)) {
                     throw new NameReferencingFileConverter.FileConversionException(
                        "Could not request user " + gameprofile.getName() + " from backend systems", exception
                     );
                  }
               }
            };
            a(minecraftserver, list, profilelookupcallback);
            whitelist.e();
            b(d);
            return true;
         } catch (IOException var4) {
            e.warn("Could not read old whitelist to convert it!", var4);
            return false;
         } catch (NameReferencingFileConverter.FileConversionException var5) {
            e.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   @Nullable
   public static UUID a(final MinecraftServer minecraftserver, String s) {
      if (!UtilColor.b(s) && s.length() <= 16) {
         Optional<UUID> optional = minecraftserver.ap().a(s).map(GameProfile::getId);
         if (optional.isPresent()) {
            return optional.get();
         } else if (!minecraftserver.O() && minecraftserver.U()) {
            final List<GameProfile> list = Lists.newArrayList();
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile gameprofile) {
                  minecraftserver.ap().a(gameprofile);
                  list.add(gameprofile);
               }

               public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                  NameReferencingFileConverter.e.warn("Could not lookup user whitelist entry for {}", gameprofile.getName(), exception);
               }
            };
            a(minecraftserver, Lists.newArrayList(new String[]{s}), profilelookupcallback);
            return !list.isEmpty() && ((GameProfile)list.get(0)).getId() != null ? ((GameProfile)list.get(0)).getId() : null;
         } else {
            return UUIDUtil.a(new GameProfile(null, s));
         }
      } else {
         try {
            return UUID.fromString(s);
         } catch (IllegalArgumentException var5) {
            return null;
         }
      }
   }

   public static boolean a(final DedicatedServer dedicatedserver) {
      final File file = g(dedicatedserver);
      final File file1 = new File(file.getParentFile(), "playerdata");
      final File file2 = new File(file.getParentFile(), "unknownplayers");
      if (file.exists() && file.isDirectory()) {
         File[] afile = file.listFiles();
         List<String> list = Lists.newArrayList();

         for(File file3 : afile) {
            String s = file3.getName();
            if (s.toLowerCase(Locale.ROOT).endsWith(".dat")) {
               String s1 = s.substring(0, s.length() - ".dat".length());
               if (!s1.isEmpty()) {
                  list.add(s1);
               }
            }
         }

         try {
            final String[] astring = list.toArray(new String[list.size()]);
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile gameprofile) {
                  dedicatedserver.ap().a(gameprofile);
                  UUID uuid = gameprofile.getId();
                  if (uuid == null) {
                     throw new NameReferencingFileConverter.FileConversionException("Missing UUID for user profile " + gameprofile.getName());
                  } else {
                     this.a(file1, this.a(gameprofile), uuid.toString());
                  }
               }

               public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                  NameReferencingFileConverter.e.warn("Could not lookup user uuid for {}", gameprofile.getName(), exception);
                  if (exception instanceof ProfileNotFoundException) {
                     String s2 = this.a(gameprofile);
                     this.a(file2, s2, s2);
                  } else {
                     throw new NameReferencingFileConverter.FileConversionException(
                        "Could not request user " + gameprofile.getName() + " from backend systems", exception
                     );
                  }
               }

               private void a(File file4, String s2, String s3) {
                  File file5 = new File(file, s2 + ".dat");
                  File file6 = new File(file4, s3 + ".dat");
                  NBTTagCompound root = null;

                  try {
                     root = NBTCompressedStreamTools.a(new FileInputStream(file5));
                  } catch (Exception var10) {
                     var10.printStackTrace();
                  }

                  if (root != null) {
                     if (!root.e("bukkit")) {
                        root.a("bukkit", new NBTTagCompound());
                     }

                     NBTTagCompound data = root.p("bukkit");
                     data.a("lastKnownName", s2);

                     try {
                        NBTCompressedStreamTools.a(root, new FileOutputStream(file2));
                     } catch (Exception var9) {
                        var9.printStackTrace();
                     }
                  }

                  NameReferencingFileConverter.a(file4);
                  if (!file5.renameTo(file6)) {
                     throw new NameReferencingFileConverter.FileConversionException("Could not convert file for " + s2);
                  }
               }

               private String a(GameProfile gameprofile) {
                  String s2 = null;

                  for(String s3 : astring) {
                     if (s3 != null && s3.equalsIgnoreCase(gameprofile.getName())) {
                        s2 = s3;
                        break;
                     }
                  }

                  if (s2 == null) {
                     throw new NameReferencingFileConverter.FileConversionException("Could not find the filename for " + gameprofile.getName() + " anymore");
                  } else {
                     return s2;
                  }
               }
            };
            a(dedicatedserver, Lists.newArrayList(astring), profilelookupcallback);
            return true;
         } catch (NameReferencingFileConverter.FileConversionException var12) {
            e.error("Conversion failed, please try again later", var12);
            return false;
         }
      } else {
         return true;
      }
   }

   static void a(File file) {
      if (file.exists()) {
         if (!file.isDirectory()) {
            throw new NameReferencingFileConverter.FileConversionException("Can't create directory " + file.getName() + " in world save directory.");
         }
      } else if (!file.mkdirs()) {
         throw new NameReferencingFileConverter.FileConversionException("Can't create directory " + file.getName() + " in world save directory.");
      }
   }

   public static boolean e(MinecraftServer minecraftserver) {
      boolean flag = a();
      return flag && f(minecraftserver);
   }

   private static boolean a() {
      boolean flag = false;
      if (b.exists() && b.isFile()) {
         flag = true;
      }

      boolean flag1 = false;
      if (a.exists() && a.isFile()) {
         flag1 = true;
      }

      boolean flag2 = false;
      if (c.exists() && c.isFile()) {
         flag2 = true;
      }

      boolean flag3 = false;
      if (d.exists() && d.isFile()) {
         flag3 = true;
      }

      if (!flag && !flag1 && !flag2 && !flag3) {
         return true;
      } else {
         e.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
         e.warn("** please remove the following files and restart the server:");
         if (flag) {
            e.warn("* {}", b.getName());
         }

         if (flag1) {
            e.warn("* {}", a.getName());
         }

         if (flag2) {
            e.warn("* {}", c.getName());
         }

         if (flag3) {
            e.warn("* {}", d.getName());
         }

         return false;
      }
   }

   private static boolean f(MinecraftServer minecraftserver) {
      File file = g(minecraftserver);
      if (!file.exists() || !file.isDirectory() || file.list().length <= 0 && file.delete()) {
         return true;
      } else {
         e.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
         e.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
         e.warn("** please restart the server and if the problem persists, remove the directory '{}'", file.getPath());
         return false;
      }
   }

   private static File g(MinecraftServer minecraftserver) {
      return minecraftserver.a(SavedFile.d).toFile();
   }

   private static void b(File file) {
      File file1 = new File(file.getName() + ".converted");
      file.renameTo(file1);
   }

   static Date a(String s, Date date) {
      Date date1;
      try {
         date1 = ExpirableListEntry.a.parse(s);
      } catch (ParseException var4) {
         date1 = date;
      }

      return date1;
   }

   private static class FileConversionException extends RuntimeException {
      FileConversionException(String s, Throwable throwable) {
         super(s, throwable);
      }

      FileConversionException(String s) {
         super(s);
      }
   }
}
