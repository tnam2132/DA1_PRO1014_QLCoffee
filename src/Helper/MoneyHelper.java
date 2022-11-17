package Helper;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyHelper {

    private static final NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public static String moneyToString(double money) {
        return currencyVN.format(money);
    }
}
