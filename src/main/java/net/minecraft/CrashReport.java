package net.minecraft;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletionException;
import net.minecraft.util.MemoryReserve;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.craftbukkit.v1_19_R3.CraftCrashReport;
import org.slf4j.Logger;

public class CrashReport {
   private static final Logger a = LogUtils.getLogger();
   private static final DateTimeFormatter b = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
   private final String c;
   private final Throwable d;
   private final List<CrashReportSystemDetails> e = Lists.newArrayList();
   private File f;
   private boolean g = true;
   private StackTraceElement[] h = new StackTraceElement[0];
   private final SystemReport i = new SystemReport();

   public CrashReport(String s, Throwable throwable) {
      this.c = s;
      this.d = throwable;
      this.i.a("CraftBukkit Information", new CraftCrashReport());
   }

   public String a() {
      return this.c;
   }

   public Throwable b() {
      return this.d;
   }

   public String c() {
      StringBuilder stringbuilder = new StringBuilder();
      this.a(stringbuilder);
      return stringbuilder.toString();
   }

   public void a(StringBuilder stringbuilder) {
      if ((this.h == null || this.h.length <= 0) && !this.e.isEmpty()) {
         this.h = (StackTraceElement[])ArrayUtils.subarray(this.e.get(0).a(), 0, 1);
      }

      if (this.h != null && this.h.length > 0) {
         stringbuilder.append("-- Head --\n");
         stringbuilder.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         stringbuilder.append("Stacktrace:\n");

         for(StackTraceElement stacktraceelement : this.h) {
            stringbuilder.append("\t").append("at ").append(stacktraceelement);
            stringbuilder.append("\n");
         }

         stringbuilder.append("\n");
      }

      for(CrashReportSystemDetails crashreportsystemdetails : this.e) {
         crashreportsystemdetails.a(stringbuilder);
         stringbuilder.append("\n\n");
      }

      this.i.a(stringbuilder);
   }

   public String d() {
      StringWriter stringwriter = null;
      PrintWriter printwriter = null;
      Object object = this.d;
      if (((Throwable)object).getMessage() == null) {
         if (object instanceof NullPointerException) {
            object = new NullPointerException(this.c);
         } else if (object instanceof StackOverflowError) {
            object = new StackOverflowError(this.c);
         } else if (object instanceof OutOfMemoryError) {
            object = new OutOfMemoryError(this.c);
         }

         ((Throwable)object).setStackTrace(this.d.getStackTrace());
      }

      String s;
      try {
         stringwriter = new StringWriter();
         printwriter = new PrintWriter(stringwriter);
         ((Throwable)object).printStackTrace(printwriter);
         s = stringwriter.toString();
      } finally {
         IOUtils.closeQuietly(stringwriter);
         IOUtils.closeQuietly(printwriter);
      }

      return s;
   }

   public String e() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("---- Minecraft Crash Report ----\n");
      stringbuilder.append("// ");
      stringbuilder.append(i());
      stringbuilder.append("\n\n");
      stringbuilder.append("Time: ");
      stringbuilder.append(b.format(ZonedDateTime.now()));
      stringbuilder.append("\n");
      stringbuilder.append("Description: ");
      stringbuilder.append(this.c);
      stringbuilder.append("\n\n");
      stringbuilder.append(this.d());
      stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for(int i = 0; i < 87; ++i) {
         stringbuilder.append("-");
      }

      stringbuilder.append("\n\n");
      this.a(stringbuilder);
      return stringbuilder.toString();
   }

   public File f() {
      return this.f;
   }

   public boolean a(File file) {
      if (this.f != null) {
         return false;
      } else {
         if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
         }

         OutputStreamWriter outputstreamwriter = null;

         boolean flag;
         try {
            outputstreamwriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            outputstreamwriter.write(this.e());
            this.f = file;
            return true;
         } catch (Throwable var9) {
            a.error("Could not save crash report to {}", file, var9);
            flag = false;
         } finally {
            IOUtils.closeQuietly(outputstreamwriter);
         }

         return flag;
      }
   }

   public SystemReport g() {
      return this.i;
   }

   public CrashReportSystemDetails a(String s) {
      return this.a(s, 1);
   }

   public CrashReportSystemDetails a(String s, int i) {
      CrashReportSystemDetails crashreportsystemdetails = new CrashReportSystemDetails(s);
      if (this.g) {
         int j = crashreportsystemdetails.a(i);
         StackTraceElement[] astacktraceelement = this.d.getStackTrace();
         StackTraceElement stacktraceelement = null;
         StackTraceElement stacktraceelement1 = null;
         int k = astacktraceelement.length - j;
         if (k < 0) {
            System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + j + ")");
         }

         if (astacktraceelement != null && k >= 0 && k < astacktraceelement.length) {
            stacktraceelement = astacktraceelement[k];
            if (astacktraceelement.length + 1 - j < astacktraceelement.length) {
               stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - j];
            }
         }

         this.g = crashreportsystemdetails.a(stacktraceelement, stacktraceelement1);
         if (astacktraceelement != null && astacktraceelement.length >= j && k >= 0 && k < astacktraceelement.length) {
            this.h = new StackTraceElement[k];
            System.arraycopy(astacktraceelement, 0, this.h, 0, this.h.length);
         } else {
            this.g = false;
         }
      }

      this.e.add(crashreportsystemdetails);
      return crashreportsystemdetails;
   }

   private static String i() {
      String[] astring = new String[]{
         "Who set us up the TNT?",
         "Everything's going to plan. No, really, that was supposed to happen.",
         "Uh... Did I do that?",
         "Oops.",
         "Why did you do that?",
         "I feel sad now :(",
         "My bad.",
         "I'm sorry, Dave.",
         "I let you down. Sorry :(",
         "On the bright side, I bought you a teddy bear!",
         "Daisy, daisy...",
         "Oh - I know what I did wrong!",
         "Hey, that tickles! Hehehe!",
         "I blame Dinnerbone.",
         "You should try our sister game, Minceraft!",
         "Don't be sad. I'll do better next time, I promise!",
         "Don't be sad, have a hug! <3",
         "I just don't know what went wrong :(",
         "Shall we play a game?",
         "Quite honestly, I wouldn't worry myself about that.",
         "I bet Cylons wouldn't have this problem.",
         "Sorry :(",
         "Surprise! Haha. Well, this is awkward.",
         "Would you like a cupcake?",
         "Hi. I'm Minecraft, and I'm a crashaholic.",
         "Ooh. Shiny.",
         "This doesn't make any sense!",
         "Why is it breaking :(",
         "Don't do that.",
         "Ouch. That hurt :(",
         "You're mean.",
         "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]",
         "There are four lights!",
         "But it works on my machine."
      };

      try {
         return astring[(int)(SystemUtils.c() % (long)astring.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   public static CrashReport a(Throwable throwable, String s) {
      while(throwable instanceof CompletionException && throwable.getCause() != null) {
         throwable = throwable.getCause();
      }

      CrashReport crashreport;
      if (throwable instanceof ReportedException) {
         crashreport = ((ReportedException)throwable).a();
      } else {
         crashreport = new CrashReport(s, throwable);
      }

      return crashreport;
   }

   public static void h() {
      MemoryReserve.a();
      new CrashReport("Don't panic!", new Throwable()).e();
   }
}
