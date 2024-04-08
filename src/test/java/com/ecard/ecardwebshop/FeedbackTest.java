package com.ecard.ecardwebshop;

import com.ecard.ecardwebshop.feedback.Feedback;
import com.ecard.ecardwebshop.feedback.FeedbackController;
import com.ecard.ecardwebshop.feedback.FeedbackService;
import com.ecard.ecardwebshop.feedback.ResultStatus;
import com.ecard.ecardwebshop.product.Product;
import com.ecard.ecardwebshop.product.ProductService;
import com.ecard.ecardwebshop.user.User;
import com.ecard.ecardwebshop.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class FeedbackTest {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    FeedbackController feedbackController;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Test
    public void testListFeedBacksByProductId() {

        List<Feedback> feedbacks = feedbackService.listFeedBacksByProductId(1);
        assertEquals(3, feedbacks.get(0).getUser().getId());
        assertEquals("Never a better shop!", feedbacks.get(0).getFeedback());

    }


    @Test
    public void testDeleteFeedBackById() {

//        Given (we have ONE product of which we have ONE feedback)

        Product exampleproduct = productService.getProductById(1);

        List<Feedback> feedbackListOfExampleProduct = feedbackService.listFeedBacksByProductId(exampleproduct.getId());

        assertEquals(feedbackListOfExampleProduct.size(), 1);


//        When (deleting ONE feedback by it's ID )

        long idOfExampleFeedback = feedbackListOfExampleProduct.get(0).getId();

        feedbackController.deleteFeedbackById(idOfExampleFeedback);

//      Then  (the list of Feedbacks decreases by one as well)
        feedbackListOfExampleProduct = feedbackService.listFeedBacksByProductId(exampleproduct.getId());

        assertEquals(feedbackListOfExampleProduct, Collections.emptyList());

    }

    @Test
    public void testUserDidNotReceiveSuchProductThereforeCanNotGiveAnyFeedback() {

//      Given  (A user whom the company haven't shipped the given product yet)
        User exampleUser = userService.getUserById(1);
        Product exampleproduct = productService.getProductById(2);

//      When (The user tries to send a feedback)
        feedbackController.giveAFeedback(new Feedback("Awesome!", 5, exampleUser,
                exampleproduct));

        List<Feedback> feedbacks = feedbackService.listFeedBacksByProductId(exampleproduct.getId());

//      Then (It will have no effect. The feedback list for the product will remain empty)
        assertEquals(feedbacks, Collections.emptyList());

    }

    @Test
    public void testGivingHTMLCodeAsFeedbackIsNotAccepted() {


//      Given (One User & OneProduct)
        User exampleUser = userService.getUserById(2);
        Product exampleproduct = productService.getProductById(8);


//      When (User gives a feedback that contains html code)
        ResultStatus rs = feedbackController.giveAFeedback(new Feedback("Awesome shop! <span style=\"color: #3333\">Action</span>!", 4, exampleUser,
                exampleproduct));

//      Then (An error message appears)
        boolean expected = rs.getMessage().equals("HTML kód nem megengedett");

        assertTrue(expected);
    }
}
