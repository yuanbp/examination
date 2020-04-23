package com.chieftain.examination;

import java.io.Serializable;

/**
 * com.chieftain.jtestcodes [workset]
 * Created by chieftain on 2018/11/6
 *
 * @author chieftain on 2018/11/6
 */
public class Point implements Serializable
{

    private static final long serialVersionUID = -4557924992764602188L;

    public Point()
    {

    }

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int x;
    public int y;
}
