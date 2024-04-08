package com.ecard.ecardwebshop.feedback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedbackValidator {


    public boolean containsHTMLCode(Feedback feedback){
        Pattern p = Pattern.compile("<[^>]*>");
        Matcher m = p.matcher(feedback.getFeedback());
        return m.find();
    }

}
