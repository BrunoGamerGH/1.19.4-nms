package net.minecraft.server.gui;

import com.google.common.collect.Lists;
import com.mojang.logging.LogQueues;
import com.mojang.logging.LogUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.server.dedicated.DedicatedServer;
import org.slf4j.Logger;

public class ServerGUI extends JComponent {
   private static final Font a = new Font("Monospaced", 0, 12);
   private static final Logger b = LogUtils.getLogger();
   private static final String c = "Minecraft server";
   private static final String d = "Minecraft server - shutting down!";
   private final DedicatedServer e;
   private Thread f;
   private final Collection<Runnable> g = Lists.newArrayList();
   final AtomicBoolean h = new AtomicBoolean();
   private static final Pattern ANSI = Pattern.compile("\\x1B\\[([0-9]{1,2}(;[0-9]{1,2})*)?[m|K]");

   public static ServerGUI a(final DedicatedServer dedicatedserver) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var3) {
      }

      final JFrame jframe = new JFrame("Minecraft server");
      final ServerGUI servergui = new ServerGUI(dedicatedserver);
      jframe.setDefaultCloseOperation(2);
      jframe.add(servergui);
      jframe.pack();
      jframe.setLocationRelativeTo(null);
      jframe.setVisible(true);
      jframe.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent windowevent) {
            if (!servergui.h.getAndSet(true)) {
               jframe.setTitle("Minecraft server - shutting down!");
               dedicatedserver.a(true);
               servergui.f();
            }
         }
      });
      servergui.a(jframe::dispose);
      servergui.a();
      return servergui;
   }

   private ServerGUI(DedicatedServer dedicatedserver) {
      this.e = dedicatedserver;
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(new BorderLayout());

      try {
         this.add(this.e(), "Center");
         this.add(this.c(), "West");
      } catch (Exception var3) {
         b.error("Couldn't build server GUI", var3);
      }
   }

   public void a(Runnable runnable) {
      this.g.add(runnable);
   }

   private JComponent c() {
      JPanel jpanel = new JPanel(new BorderLayout());
      GuiStatsComponent guistatscomponent = new GuiStatsComponent(this.e);
      Collection<Runnable> collection = this.g;
      collection.add(guistatscomponent::a);
      jpanel.add(guistatscomponent, "North");
      jpanel.add(this.d(), "Center");
      jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
      return jpanel;
   }

   private JComponent d() {
      JList<?> jlist = new PlayerListBox(this.e);
      JScrollPane jscrollpane = new JScrollPane(jlist, 22, 30);
      jscrollpane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
      return jscrollpane;
   }

   private JComponent e() {
      JPanel jpanel = new JPanel(new BorderLayout());
      JTextArea jtextarea = new JTextArea();
      JScrollPane jscrollpane = new JScrollPane(jtextarea, 22, 30);
      jtextarea.setEditable(false);
      jtextarea.setFont(a);
      JTextField jtextfield = new JTextField();
      jtextfield.addActionListener(actionevent -> {
         String s = jtextfield.getText().trim();
         if (!s.isEmpty()) {
            this.e.a(s, this.e.aD());
         }

         jtextfield.setText("");
      });
      jtextarea.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent focusevent) {
         }
      });
      jpanel.add(jscrollpane, "Center");
      jpanel.add(jtextfield, "South");
      jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
      this.f = new Thread(() -> {
         String s;
         while((s = LogQueues.getNextLogEvent("ServerGuiConsole")) != null) {
            this.a(jtextarea, jscrollpane, s);
         }
      });
      this.f.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(b));
      this.f.setDaemon(true);
      return jpanel;
   }

   public void a() {
      this.f.start();
   }

   public void b() {
      if (!this.h.getAndSet(true)) {
         this.f();
      }
   }

   void f() {
      this.g.forEach(Runnable::run);
   }

   public void a(JTextArea jtextarea, JScrollPane jscrollpane, String s) {
      if (!SwingUtilities.isEventDispatchThread()) {
         SwingUtilities.invokeLater(() -> this.a(jtextarea, jscrollpane, s));
      } else {
         Document document = jtextarea.getDocument();
         JScrollBar jscrollbar = jscrollpane.getVerticalScrollBar();
         boolean flag = false;
         if (jscrollpane.getViewport().getView() == jtextarea) {
            flag = (double)jscrollbar.getValue() + jscrollbar.getSize().getHeight() + (double)(a.getSize() * 4) > (double)jscrollbar.getMaximum();
         }

         try {
            document.insertString(document.getLength(), ANSI.matcher(s).replaceAll(""), null);
         } catch (BadLocationException var8) {
         }

         if (flag) {
            jscrollbar.setValue(Integer.MAX_VALUE);
         }
      }
   }
}
