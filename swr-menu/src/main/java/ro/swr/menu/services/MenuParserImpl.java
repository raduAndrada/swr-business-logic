package ro.swr.menu.services;

import lombok.Data;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ro.swr.menu.model.Category;
import ro.swr.menu.model.MenuItem;
import ro.swr.menu.model.Subcategory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Data
public class MenuParserImpl implements MenuParser {

    public static final String H2_TAG = "h2";
    public static final String STRONG_TAG = "strong";
    public static final String A_TAG = "a";
    public static final String LI_TAG = "li";
    public static final String TEXT = "#text";

    private final Elements subcategoriesHtml;
    private final Elements categoriesHtml;

    @Override
    public LinkedList<Category> parseMenu() {
        AtomicInteger currentCategoryIndex = new AtomicInteger(-1);
        LinkedList<Category> categories = new LinkedList<>();
        categoriesHtml.get(0)
                .childNodes().stream().filter(e -> e instanceof Element).forEach(el -> {
                    Element element = (Element) el;
                    Element first = element.getElementsByTag(H2_TAG).first();
                    if (Objects.nonNull(first)) {
                        categories.add(new Category(getElementText(first), new LinkedList<>()));
                        currentCategoryIndex.set(currentCategoryIndex.intValue() + 1);
                    } else {
                        Element subCategory = element.getElementsByTag(A_TAG).first();
                        categories.get(currentCategoryIndex.intValue()).subcategories().add(new Subcategory(getElementText(subCategory), new LinkedList<>(), new ArrayList<>()));
                    }
                });
        AtomicInteger startCategory = new AtomicInteger(0);
        for (Category category : categories) {
            for (int i = 0; i < category.subcategories().size(); i++) {
                AtomicInteger iCopy = new AtomicInteger(i);
                subcategoriesHtml.get(i + startCategory.intValue()).getElementsByTag(LI_TAG).stream().forEach(li -> {
                    Subcategory sub = category.subcategories().get(iCopy.intValue());
                    var menuItem = buildMenuItem(li);
                    if (!StringUtils.isEmpty(menuItem.img())) {
                        sub.img().add(menuItem.img());
                    }
                    sub.menuItems().add(menuItem);

                });
            }
            startCategory.set(startCategory.intValue() + category.subcategories().size());

        }

        return categories;
    }


    private static MenuItem buildMenuItem(Element element) {
        String name = getElementText(element.getElementsByTag(H2_TAG).first());
        String price = getElementText(element.getElementsByTag(STRONG_TAG).first());
        String imgSrc = getElementAttribute(element.getElementsByTag("img").first());
        String description = getElementText(element.getElementsByClass("fs-12 mb-2").first());
        String[] dsc = Objects.nonNull(description) ? description.split("\\.") : new String[2];
        String quantity = dsc.length > 1 ? dsc[1] : null;
        if (Objects.nonNull(name) && name.endsWith("ml")) {
            quantity = name.substring(name.lastIndexOf(" "))
                    .replace(" ", "")
                    .replace("DOC", "")
                    .replace("-", "");
            name = name.substring(0, name.lastIndexOf(" "))
                    .replace("-", "");
        }
        return new MenuItem(name, dsc[0], quantity, price, imgSrc);
    }

    private static String getElementAttribute(Element img) {
        try {
            return img.attributes().get("src");
        } catch (NullPointerException e) {
            return null;
        }
    }


    private static String getElementText(Element element) {
        try {
            return element.childNodes().get(0).attributes().get(TEXT);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
    }
}
