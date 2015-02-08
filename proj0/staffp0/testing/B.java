// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package staffp0;

import java.io.PrintStream;

// Referenced classes of package staffp0:
//            A, C

public class B
{

    public static void A(String as[])
    {
        B b = new B(false);
        b.D();
    }

    public B(boolean flag)
    {
        F = false;
        D = 0;
        E = new A[8][8];
        if(!flag)
            B();
    }

    private void B()
    {
        for(int i = 0; i < 8; i += 2)
        {
            E[i][0] = new A(true, this, i, 0, "pawn");
            E[i + 1][1] = new A(true, this, i + 1, 1, "shield");
            E[i][2] = new A(true, this, i, 2, "bomb");
            E[i + 1][7] = new A(false, this, i + 1, 7, "pawn");
            E[i][6] = new A(false, this, i, 6, "shield");
            E[i + 1][5] = new A(false, this, i + 1, 5, "bomb");
        }

    }

    private void C()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if((i + j) % 2 == 0)
                    staffp0.C.B(C.Y);
                else
                    staffp0.C.B(C.S);
                if(G != null && i == C && j == B)
                    staffp0.C.B(C.L);
                staffp0.C.A((double)i + 0.5D, (double)j + 0.5D, 0.5D);
                A a = E[i][j];
                if(a != null)
                    staffp0.C.A((double)i + 0.5D, (double)j + 0.5D, a.B(), 1.0D, 1.0D);
            }

        }

    }

    public A C(int i, int j)
    {
        if(B(i, j))
            return null;
        else
            return E[i][j];
    }

    public boolean A(int i, int j)
    {
        if(F)
            return false;
        if(B(i, j))
            return false;
        A a = C(i, j);
        if(G == null)
            return a != null && a.F() == D;
        if(a == null)
            return (!A || G.E()) && A(C, B, i, j);
        else
            return !A && a.F() == D;
    }

    public boolean A(int i, int j, int k, int l)
    {
        if(B(k, l) || C(k, l) != null)
            return false;
        A a = C(i, j);
        boolean flag = a.G();
        int i1 = Math.abs(k - i);
        int j1 = l - j;
        int k1 = Math.abs(j1);
        if(i1 == 1 && !a.E())
        {
            if(flag)
                return k1 == 1;
            if(a.C())
                return j1 == 1;
            else
                return j1 == -1;
        }
        if(i1 == 2)
        {
            if(k1 != 2)
                return false;
            int l1 = (k + i) / 2;
            int i2 = (l + j) / 2;
            A a1 = C(l1, i2);
            if(a1 == null || a1.F() == a.F())
                return false;
            if(flag)
                return true;
            if(a.C())
                return j1 == 2;
            else
                return j1 == -2;
        } else
        {
            return false;
        }
    }

    public void D(int i, int j)
    {
        if(C(i, j) == null)
        {
            G.B(i, j);
            A = true;
        }
        G = C(i, j);
        C = i;
        B = j;
    }

    public void A(A a, int i, int j)
    {
        E[i][j] = a;
    }

    public A E(int i, int j)
    {
        if(B(i, j))
        {
            String s = "Bad (x, y) remove request at (";
            s = (new StringBuilder()).append(s).append(i).append(", ").append(j).append(")!").toString();
            System.out.println(s);
            return null;
        }
        A a = E[i][j];
        if(a == null)
        {
            String s1 = "Attemped to remove a null at (";
            s1 = (new StringBuilder()).append(s1).append(i).append(", ").append(j).append(")!").toString();
            System.out.println(s1);
            return null;
        } else
        {
            E[i][j] = null;
            return a;
        }
    }

    public void A()
    {
        D = (D + 1) % 2;
        A = false;
        if(G != null)
        {
            G.H();
            G = null;
        }
    }

    public void D()
    {
        staffp0.C.B(0.0D, 8D);
        staffp0.C.C(0.0D, 8D);
        do
        {
            C();
            if(staffp0.C.I())
            {
                int i = (int)staffp0.C.F();
                int j = (int)staffp0.C.K();
                if(A(i, j))
                    D(i, j);
            }
            if(staffp0.C.B() && A)
                A();
            staffp0.C.B(10);
        } while(true);
    }

    public boolean B(int i, int j)
    {
        return i < 0 || j < 0 || i > 7 || j > 7;
    }

    private boolean F;
    private A E[][];
    private int D;
    private A G;
    private int C;
    private int B;
    private boolean A;
}
