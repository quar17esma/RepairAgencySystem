package com.quar17esma.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeFmt extends TagSupport {
    private java.time.LocalDateTime dateTime;
    private String pattern;

    public void setDateTime(java.time.LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            String formatDateTime = dateTime.format(formatter);
            pageContext.getOut().write(formatDateTime);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
