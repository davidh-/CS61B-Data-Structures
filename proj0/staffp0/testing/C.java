// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package staffp0;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.TreeSet;
import javax.swing.*;

public final class C
    implements ActionListener, MouseListener, MouseMotionListener, KeyListener
{

    private C()
    {
    }

    private static void L()
    {
        if(B != null)
            B.setVisible(false);
        B = new JFrame();
        Z = new BufferedImage(A, D, 2);
        P = new BufferedImage(A, D, 2);
        h = Z.createGraphics();
        Q = P.createGraphics();
        J();
        C();
        h.setColor(o);
        h.fillRect(0, 0, A, D);
        H();
        G();
        A();
        E();
        RenderingHints renderinghints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderinghints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        h.addRenderingHints(renderinghints);
        ImageIcon imageicon = new ImageIcon(P);
        JLabel jlabel = new JLabel(imageicon);
        jlabel.addMouseListener(g);
        jlabel.addMouseMotionListener(g);
        B.setContentPane(jlabel);
        B.addKeyListener(g);
        B.setResizable(false);
        B.setDefaultCloseOperation(3);
        B.setTitle("Checkers61B");
        B.pack();
        B.requestFocusInWindow();
        B.setVisible(true);
    }

    public static void J()
    {
        B(0.0D, 1.0D);
    }

    public static void C()
    {
        C(0.0D, 1.0D);
    }

    public static void B(double d1, double d2)
    {
        double d3 = d2 - d1;
        synchronized(E)
        {
            R = d1 - 0.050000000000000003D * d3;
            e = d2 + 0.050000000000000003D * d3;
        }
    }

    public static void C(double d1, double d2)
    {
        double d3 = d2 - d1;
        synchronized(E)
        {
            _ = d1 - 0.050000000000000003D * d3;
            J = d2 + 0.050000000000000003D * d3;
        }
    }

    private static double F(double d1)
    {
        return ((double)A * (d1 - R)) / (e - R);
    }

    private static double C(double d1)
    {
        return ((double)D * (J - d1)) / (J - _);
    }

    private static double G(double d1)
    {
        return (d1 * (double)A) / Math.abs(e - R);
    }

    private static double D(double d1)
    {
        return (d1 * (double)D) / Math.abs(J - _);
    }

    private static double E(double d1)
    {
        return R + (d1 * (e - R)) / (double)A;
    }

    private static double B(double d1)
    {
        return J - (d1 * (J - _)) / (double)D;
    }

    public static void E()
    {
        A(o);
    }

    public static void A(Color color)
    {
        h.setColor(color);
        h.fillRect(0, 0, A, D);
        h.setColor(H);
        D();
    }

    public static void G()
    {
        A(0.002D);
    }

    public static void A(double d1)
    {
        if(d1 < 0.0D)
        {
            throw new IllegalArgumentException("pen radius must be nonnegative");
        } else
        {
            N = d1;
            float f1 = (float)(d1 * 512D);
            BasicStroke basicstroke = new BasicStroke(f1, 1, 1);
            h.setStroke(basicstroke);
            return;
        }
    }

    public static void H()
    {
        B(T);
    }

    public static void B(Color color)
    {
        H = color;
        h.setColor(H);
    }

    public static void A()
    {
        A(U);
    }

    public static void A(Font font)
    {
        l = font;
    }

    private static void A(double d1, double d2)
    {
        h.fillRect((int)Math.round(F(d1)), (int)Math.round(C(d2)), 1, 1);
    }

    public static void A(double d1, double d2, double d3)
    {
        if(d3 < 0.0D)
            throw new IllegalArgumentException("square side length must be nonnegative");
        double d4 = F(d1);
        double d5 = C(d2);
        double d6 = G(2D * d3);
        double d7 = D(2D * d3);
        if(d6 <= 1.0D && d7 <= 1.0D)
            A(d1, d2);
        else
            h.fill(new java.awt.geom.Rectangle2D.Double(d4 - d6 / 2D, d5 - d7 / 2D, d6, d7));
        D();
    }

    private static Image A(String s)
    {
        ImageIcon imageicon = new ImageIcon(s);
        if(imageicon == null || imageicon.getImageLoadStatus() != 8)
            try
            {
                URL url = new URL(s);
                imageicon = new ImageIcon(url);
            }
            catch(Exception exception) { }
        if(imageicon == null || imageicon.getImageLoadStatus() != 8)
        {
            URL url1 = staffp0/C.getResource(s);
            if(url1 == null)
                throw new IllegalArgumentException((new StringBuilder()).append("image ").append(s).append(" not found").toString());
            imageicon = new ImageIcon(url1);
        }
        return imageicon.getImage();
    }

    public static void A(double d1, double d2, String s, double d3, double d4)
    {
        Image image = A(s);
        double d5 = F(d1);
        double d6 = C(d2);
        if(d3 < 0.0D)
            throw new IllegalArgumentException((new StringBuilder()).append("width is negative: ").append(d3).toString());
        if(d4 < 0.0D)
            throw new IllegalArgumentException((new StringBuilder()).append("height is negative: ").append(d4).toString());
        double d7 = G(d3);
        double d8 = D(d4);
        if(d7 < 0.0D || d8 < 0.0D)
            throw new IllegalArgumentException((new StringBuilder()).append("image ").append(s).append(" is corrupt").toString());
        if(d7 <= 1.0D && d8 <= 1.0D)
            A(d1, d2);
        else
            h.drawImage(image, (int)Math.round(d5 - d7 / 2D), (int)Math.round(d6 - d8 / 2D), (int)Math.round(d7), (int)Math.round(d8), null);
        D();
    }

    public static void B(int i1)
    {
        I = false;
        D();
        try
        {
            Thread.sleep(i1);
        }
        catch(InterruptedException interruptedexception)
        {
            System.out.println("Error sleeping");
        }
        I = true;
    }

    private static void D()
    {
        if(I)
        {
            return;
        } else
        {
            Q.drawImage(Z, 0, 0, null);
            B.repaint();
            return;
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
    }

    public static boolean I()
    {
        Object obj = E;
        JVM INSTR monitorenter ;
        return a;
        Exception exception;
        exception;
        throw exception;
    }

    public static double F()
    {
        Object obj = E;
        JVM INSTR monitorenter ;
        return d;
        Exception exception;
        exception;
        throw exception;
    }

    public static double K()
    {
        Object obj = E;
        JVM INSTR monitorenter ;
        return c;
        Exception exception;
        exception;
        throw exception;
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        synchronized(E)
        {
            d = E(mouseevent.getX());
            c = B(mouseevent.getY());
            a = true;
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        synchronized(E)
        {
            a = false;
        }
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
        synchronized(E)
        {
            d = E(mouseevent.getX());
            c = B(mouseevent.getY());
        }
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
        synchronized(E)
        {
            d = E(mouseevent.getX());
            c = B(mouseevent.getY());
        }
    }

    public static boolean A(int i1)
    {
        Object obj = O;
        JVM INSTR monitorenter ;
        return b.contains(Integer.valueOf(i1));
        Exception exception;
        exception;
        throw exception;
    }

    public static boolean B()
    {
        return A(32);
    }

    public void keyTyped(KeyEvent keyevent)
    {
        synchronized(O)
        {
            j.addFirst(Character.valueOf(keyevent.getKeyChar()));
        }
    }

    public void keyPressed(KeyEvent keyevent)
    {
        synchronized(O)
        {
            b.add(Integer.valueOf(keyevent.getKeyCode()));
        }
    }

    public void keyReleased(KeyEvent keyevent)
    {
        synchronized(O)
        {
            b.remove(Integer.valueOf(keyevent.getKeyCode()));
        }
    }

    public static final Color n;
    public static final Color i;
    public static final Color M;
    public static final Color V;
    public static final Color Y;
    public static final Color m;
    public static final Color W;
    public static final Color X;
    public static final Color G;
    public static final Color K;
    public static final Color S;
    public static final Color L;
    public static final Color f;
    public static final Color C = new Color(9, 90, 166);
    public static final Color F = new Color(103, 198, 243);
    public static final Color k = new Color(150, 35, 31);
    private static final Color T;
    private static final Color o;
    private static Color H;
    private static int A = 512;
    private static int D = 512;
    private static double N;
    private static boolean I = false;
    private static double R;
    private static double _;
    private static double e;
    private static double J;
    private static Object E = new Object();
    private static Object O = new Object();
    private static final Font U = new Font("SansSerif", 0, 16);
    private static Font l;
    private static BufferedImage Z;
    private static BufferedImage P;
    private static Graphics2D h;
    private static Graphics2D Q;
    private static C g = new C();
    private static JFrame B;
    private static boolean a = false;
    private static double d = 0.0D;
    private static double c = 0.0D;
    private static LinkedList j = new LinkedList();
    private static TreeSet b = new TreeSet();

    static 
    {
        n = Color.BLACK;
        i = Color.BLUE;
        M = Color.CYAN;
        V = Color.DARK_GRAY;
        Y = Color.GRAY;
        m = Color.GREEN;
        W = Color.LIGHT_GRAY;
        X = Color.MAGENTA;
        G = Color.ORANGE;
        K = Color.PINK;
        S = Color.RED;
        L = Color.WHITE;
        f = Color.YELLOW;
        T = n;
        o = L;
        L();
    }
}
