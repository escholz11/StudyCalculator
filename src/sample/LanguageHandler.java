package sample;

import java.util.Locale;

/**
 * Content: this class informas about the current language of the appplication
 * Created by Enrico Scholz on 10.05.2014 at 15:55
 *
 * @author Enrico Scholz
 * @version 1.0
 * @since 10.05.2014
 */
public class LanguageHandler {

    private Locale locale;
    private static String country;


    public Locale getLanguage() {
        return new Locale(country);
    }

    public void changeLanguage(String country) {
        this.country = country;
    }
}
