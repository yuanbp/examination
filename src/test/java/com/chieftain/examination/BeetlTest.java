package com.chieftain.examination;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.chieftain.examination [workset]
 * Created by chieftain on 2018-12-22
 *
 * @author chieftain on 2018-12-22
 */
public class BeetlTest {

    private ResourceLoader resourceLoader;
    private GroupTemplate groupTemplate = new GroupTemplate();

    public void init (String classpathResource) throws IOException {
        resourceLoader = new ClasspathResourceLoader(classpathResource);
        Configuration configuration = Configuration.defaultConfiguration();
        configuration.setStatementStart("@");
        configuration.setStatementEnd(null);
        configuration.setCharset("UTF-8");
        groupTemplate.setResourceLoader(resourceLoader);
        groupTemplate.setConf(configuration);
    }

    public static void main(String[] args) {
        try {
            renderStr();
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("hello", "hei");
            List<String> strs = new ArrayList<>();
            strs.add("1");
            strs.add("2");
            strs.add("3");
            objectMap.put("hello", "hei");
            objectMap.put("strs", strs);
            BeetlTest beetlTest = new BeetlTest();
            beetlTest.init("/");
            beetlTest.renderFile("beetltest.btl", objectMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void renderStr() throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("hello,${name}");
        t.binding("name", "beetl");
        String str = t.render();
        System.out.println(str);
    }

    public void renderFile (String templatePath, Map<String, Object> objectMap) {
        Template template = this.groupTemplate.getTemplate(templatePath);
        template.binding(objectMap);
        System.out.println(template.render());

//        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFile))){
//            Template template = this.groupTemplate.getTemplate(templatePath);
//            template.binding(objectMap);
//            template.renderTo(new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
