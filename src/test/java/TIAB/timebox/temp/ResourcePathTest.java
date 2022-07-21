package TIAB.timebox.temp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@SpringBootTest
public class ResourcePathTest {

    @Autowired
    private ResourceLoader resourceLoader;
    @Test
    public void getResourcePath() throws IOException {
        String resourceLoaderPath=resourceLoader.getResource("classpath:static/messagebox").getURL().getPath();
        System.out.println(resourceLoaderPath);

        String systemPath=System.getProperty("user.dir");
        System.out.println(systemPath);
    }
}
