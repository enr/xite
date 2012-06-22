package xite;

import java.util.Date;

/**
 * usata x casts test.
 * 
 * @author enr
 *
 */
public class PojoImpl implements Pojo
{

    protected Date d;
    protected String n;

    public Date getD()
    {
        return d;
    }

    public void setD(Date d)
    {
        this.d = d;
    }

    public String getN()
    {
        return n;
    }

    public void setN(String n)
    {
        this.n = n;
    }

    public void parentProcess()
    {
        System.out.println("parentProcess N: " + n);
        n = "paaaarenttt";
    }
}
