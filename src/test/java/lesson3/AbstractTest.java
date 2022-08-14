package lesson3;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;




public class AbstractTest {

    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseUrl;
    private static String contentType;

    private static Integer responsecode;
    private static Long sla;




// Получаем переменные из файла свойств

    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configFile);

        apiKey =  prop.getProperty("apiKey");
        baseUrl= prop.getProperty("base_url");
        responsecode = Integer.valueOf(prop.getProperty("response_code"));
        sla =  Long.valueOf(prop.getProperty("sla"));
        contentType = prop.getProperty("contentType");

    }

    public static String getApiKey() {
        return apiKey;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }

    public static Integer getResponseCode() { return  responsecode; }
    public static Long getSla() { return sla; }
    public static String getContentType () {return contentType;}



}
