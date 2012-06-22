package xite;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.github.enr.xite.Casts;
import com.google.common.collect.Maps;


/**
 * pezzi di codice sparsi
 */
public class CastsTest
{

	Map<String, Object> objects = Maps.newHashMap();
	
    @BeforeClass
    public void initData()
    {
    	objects.put("pojo", new PojoImpl());
    }

    @Test
    public void objectCastingTest()
    {
        Object pojo = objects.get("pojo");
        assertTrue(pojo instanceof PojoImpl);
        Pojo poj = Casts.castOrFail(objects.get("pojo"), Pojo.class);
        assertNotNull(poj);
        PojoImpl po = Casts.castOrNull(objects.get("pojo"), PojoImpl.class);
        assertNotNull(po);
    }

    @Test(expectedExceptions = { ClassCastException.class })
    public void objectCastFailingWithExceptionTest()
    {
        @SuppressWarnings("unused")
        Date poj = Casts.castOrFail(objects.get("pojo"), Date.class);
    }

    @Test
    public void objectCastFailingWithNull()
    {
        Date poj = Casts.castOrNull(objects.get("pojo"), Date.class);
        assertNull(poj);
    }

    @Test
    public void nullReferenceCasting()
    {
        Date poj = Casts.castOrNull(null, Date.class);
        assertNull(poj);
    }
}
