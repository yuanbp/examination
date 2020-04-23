package com.chieftain.examination;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author chieftain
 * @date 2019-12-03 19:23
 */
public class ModifyClass {

    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        //设置目标类的路径(即目标类的class路径，我这里的test2.class是在工程下WebRoot/WEB-INF/classes/com/hcj/javaassist)
        pool.insertClassPath("/Users/chieftain/Library/Application Support/IntelliJIdea2019.1/MavenRunHelper/lib/MavenRunHelper");
        //获得要修改的类（注意，一定要类的全称）
        CtClass cc = pool.get("krasa.mavenhelper.analyzer.MyHighlightingTree");
        CtMethod ctMethod = cc.getDeclaredMethod("getFileColorFor");
        ctMethod.setBody("{  \n" +
                "  if ($1 instanceof krasa.mavenhelper.analyzer.MyTreeUserObject) {  \n" +
                "    if (((krasa.mavenhelper.analyzer.MyTreeUserObject) $1).isHighlight()) {  \n" +
                "      if (com.intellij.util.ui.UIUtil.isUnderDarcula()) {  \n" +
                "        return com.intellij.ui.ColorUtil.darker(com.intellij.ui.JBColor.CYAN, 8);  \n" +
                "      } else {  \n" +
                "        return com.intellij.ui.ColorUtil.softer(new java.awt.Color(240, 248, 255));  \n" +
                "      }  \n" +
                "    }  \n" +
                "  }  return super.getFileColorFor($1);  \n" +
                "}");
        cc.writeFile("/Users/chieftain/Library/Application Support/IntelliJIdea2019.1/MavenRunHelper/lib/MavenRunHelper");
    }
}
