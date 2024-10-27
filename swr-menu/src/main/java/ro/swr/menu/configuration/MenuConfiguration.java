package ro.swr.menu.configuration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;

@Configuration
public class MenuConfiguration {


    @Bean
    @SessionScope
    public Element doc() throws IOException {
        return Jsoup.connect("https://erp.foodostech.com/caffee/oatt/11x0/0bfae9110c1068fa329def869c300cdecd5247e4/").get().body();
    }


    @Bean
    @SessionScope
    public Elements subcategoriesHtml(Element doc) {
        return doc.getElementsByTag("ul");
    }

    @Bean
    @SessionScope
    public Elements categoriesHtml(Element doc) {
        return doc.getElementsByClass("dropdown-menu dropdown-menu-left");
    }
}
