package com.quar17esma.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class Price extends TagSupport {
    private int price;

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String priceString = String.valueOf(price);
            String integerPart = priceString.substring(0, priceString.length() - 2);
            if (integerPart == null || integerPart.isEmpty()) {
                integerPart = "0";
            }
            String fractionalPart = priceString.substring(priceString.length() - 2);
            if (fractionalPart == null || fractionalPart.isEmpty()) {
                fractionalPart = "00";
            }
            pageContext.getOut().write(integerPart + "," + fractionalPart);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
