package com.chieftain.examination;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * com.chieftain.jtestcodes [workset]
 * Created by chieftain on 2018/10/9
 *
 * @author chieftain on 2018/10/9
 */
public class PropTest
{
    public Properties prop = null;

    public PropTest()
    {
        try
        {
            InputStream inputStream = PropTest.class.getClassLoader().getResourceAsStream("application.properties");
            prop = new Properties();
            prop.load(inputStream);
            inputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException
    {
        PropTest propTest = new PropTest();
        System.out.println(propTest.prop.getProperty("test"));
    }
}
