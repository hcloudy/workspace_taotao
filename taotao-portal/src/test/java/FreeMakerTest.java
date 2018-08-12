import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreeMakerTest {

    @Test
    public void testFreeMaker() throws IOException, TemplateException {
        //1.导入jar包
        //2.新建测试类
        //3.创建configuration对象
        Configuration config = new Configuration(Configuration.getVersion());
        //4.为configuration对象设置模板目录
        String path = "D:\\code\\workspace_taotao\\taotao-portal\\src\\main\\webapp\\WEB-INF\\ftl";
        config.setDirectoryForTemplateLoading(new File(path));
        //5.为模板设置编码格式
        config.setDefaultEncoding("utf-8");
        //6.获取模板
        Template template = config.getTemplate("first.ftl");
        Map map = new HashMap();
        map.put("hello", "hello freemaker");
        //7.创建一个writer对象
        String pathname = "D:\\code\\workspace_taotao\\taotao-portal\\src\\main\\webapp\\tmp\\first.html";
        Writer out = new FileWriter(new File(pathname));
        //8.调用template的process方法
        template.process(map, out);
        //9.关流
        out.flush();
        out.close();
    }
}
