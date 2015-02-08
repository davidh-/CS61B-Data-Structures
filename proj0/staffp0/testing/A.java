// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package staffp0;

import java.io.File;

// Referenced classes of package staffp0:
//            B

public class A
{

    private String D()
    {
        return F;
    }

    public boolean C()
    {
        return D == 0;
    }

    public int F()
    {
        return D;
    }

    public A(boolean flag, B b, int i, int j, String s)
    {
        if(flag)
            D = 0;
        else
            D = 1;
        C = false;
        E = b;
        A = i;
        G = j;
        F = s;
        B = false;
    }

    public boolean A()
    {
        return F.equals("bomb");
    }

    public boolean I()
    {
        return F.equals("shield");
    }

    public boolean G()
    {
        return C;
    }

    public void B(int i, int j)
    {
        E.E(A, G);
        E.A(this, i, j);
        if(Math.abs(A - i) == 2)
        {
            int k = (A + i) / 2;
            int l = (G + j) / 2;
            A = i;
            G = j;
            C(k, l);
        } else
        {
            A = i;
            G = j;
        }
        D(i, j);
    }

    private void J()
    {
        C = true;
    }

    private void D(int i, int j)
    {
        boolean flag = C() && j == 7;
        boolean flag1 = !C() && j == 0;
        if(flag || flag1)
            J();
    }

    private void C(int i, int j)
    {
        E.E(i, j);
        if(A())
        {
            A(A + 1, G + 1);
            A(A + 1, G - 1);
            A(A - 1, G + 1);
            A(A - 1, G - 1);
            E.E(A, G);
        }
        B = true;
    }

    private void A(int i, int j)
    {
        A a = E.C(i, j);
        if(a != null && !a.I())
            E.E(i, j);
    }

    public boolean E()
    {
        return B;
    }

    public void H()
    {
        B = false;
    }

    public String B()
    {
        String s = (new StringBuilder()).append("img").append(File.separatorChar).append(D()).toString();
        if(C())
            s = (new StringBuilder()).append(s).append("-fire").toString();
        else
            s = (new StringBuilder()).append(s).append("-water").toString();
        if(G())
            s = (new StringBuilder()).append(s).append("-crowned").toString();
        s = (new StringBuilder()).append(s).append(".png").toString();
        return s;
    }

    private int D;
    private String F;
    private boolean C;
    private boolean B;
    private int A;
    private int G;
    private B E;
}
