package domainSimilarity.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by lenovo on 2017/7/19.
 */
public class HTMLTesting {
    public static void main(String[] args) {
        String url="http://www.ausimm.com";
        try {
            Document html=Jsoup.connect(url).get();
            System.out.println(html.body().text());
           // Document doc=Jsoup.parse(html.body().te);
        }catch (IOException e){

        }

    }
}
