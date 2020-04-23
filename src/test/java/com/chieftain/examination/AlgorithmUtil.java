package com.chieftain.examination;

/**
 * com.chieftain.jtestcodes [workset]
 * Created by chieftain on 2018/11/6
 *
 * @author chieftain on 2018/11/6
 */
public class AlgorithmUtil
{
    public static void main(String[] args)
    {
//        AlgorithmUtil.GetIntersection(new Point(1, 2), new Point(1, 2),
//                new Point(1, 2), new Point(1, 2));
//        AlgorithmUtil.GetIntersection(new Point(1, 2), new Point(1, 2),
//                new Point(1, 4), new Point(1, 4));
//        AlgorithmUtil.GetIntersection(new Point(100, 1), new Point(100, 100),
//                new Point(100, 101), new Point(100, 400));
        AlgorithmUtil.GetIntersection(new Point(5, 5), new Point(100, 100),
                new Point(100, 5), new Point(5, 100));
    }

    /**
     *  * 判断两条线是否相交 a 线段 1 起点坐标 b 线段 1 终点坐标 c 线段 2 起点坐标 d 线段 2 终点坐标 intersection 相交点坐标
     *  * reutrn 是否相交: 0 : 两线平行 -1 : 不平行且未相交 1 : 两线相交
     *  
     */


    private static int GetIntersection(Point a, Point b, Point c, Point d)
    {
        Point intersection = new Point(0, 0);

        if (Math.abs(b.y - a.y) + Math.abs(b.x - a.x) + Math.abs(d.y - c.y)
                + Math.abs(d.x - c.x) == 0)
        {
            if ((c.x - a.x) + (c.y - a.y) == 0)
            {
                System.out.println("ABCD 是同一个点！");
            } else
            {
                System.out.println("AB 是一个点，CD 是一个点，且 AC 不同！");
            }
            return 0;
        }

        if (Math.abs(b.y - a.y) + Math.abs(b.x - a.x) == 0)
        {
            if ((a.x - d.x) * (c.y - d.y) - (a.y - d.y) * (c.x - d.x) == 0)
            {
                System.out.println("A、B 是一个点，且在 CD 线段上！");
            } else
            {
                System.out.println("A、B 是一个点，且不在 CD 线段上！");
            }
            return 0;
        }
        if (Math.abs(d.y - c.y) + Math.abs(d.x - c.x) == 0)
        {
            if ((d.x - b.x) * (a.y - b.y) - (d.y - b.y) * (a.x - b.x) == 0)
            {
                System.out.println("C、D 是一个点，且在 AB 线段上！");
            } else
            {
                System.out.println("C、D 是一个点，且不在 AB 线段上！");
            }
            return 0;
        }

        if ((b.y - a.y) * (c.x - d.x) - (b.x - a.x) * (c.y - d.y) == 0)
        {
            System.out.println("线段平行，无交点！");
            return 0;
        }

        intersection.x = ((b.x - a.x) * (c.x - d.x) * (c.y - a.y) -
                c.x * (b.x - a.x) * (c.y - d.y) + a.x * (b.y - a.y) * (c.x - d.x)) /
                ((b.y - a.y) * (c.x - d.x) - (b.x - a.x) * (c.y - d.y));
        intersection.y = ((b.y - a.y) * (c.y - d.y) * (c.x - a.x) - c.y
                * (b.y - a.y) * (c.x - d.x) + a.y * (b.x - a.x) * (c.y - d.y))
                / ((b.x - a.x) * (c.y - d.y) - (b.y - a.y) * (c.x - d.x));

        if ((intersection.x - a.x) * (intersection.x - b.x) <= 0
                && (intersection.x - c.x) * (intersection.x - d.x) <= 0
                && (intersection.y - a.y) * (intersection.y - b.y) <= 0
                && (intersection.y - c.y) * (intersection.y - d.y) <= 0)
        {

            System.out.println("线段相交于点 (" + intersection.x + "," + intersection.y + ")！");
            return 1; // '相交
        } else
        {
            System.out.println("线段相交于虚交点 (" + intersection.x + "," + intersection.y + ")！");
            return -1; // '相交但不在线段上
        }
    }
}
