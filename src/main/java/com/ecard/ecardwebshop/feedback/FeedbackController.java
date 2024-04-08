package com.ecard.ecardwebshop.feedback;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {

    private FeedbackService feedbackService;

    private FeedbackValidator feedbackValidator;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
        feedbackValidator = new FeedbackValidator();
    }

    @PostMapping("/feedback")
    public ResultStatus giveAFeedback(@RequestBody Feedback feedback){
        if(feedbackValidator.containsHTMLCode(feedback)){
            return new ResultStatus(ResultStatusEnum.NOT_OK,"HTML kód nem megengedett");
        }
        if(feedbackService.giveAFeedback(feedback)){
            return new ResultStatus(ResultStatusEnum.OK, "Az értékelést megkaptuk, köszönjük.");
        }
        return new ResultStatus(ResultStatusEnum.NOT_OK,"Még nem szállítottunk ilyen terméket Önnek.");
    }

    @GetMapping("/feedback/{productId}")
    public List<Feedback> listFeedBacksByProductId(@PathVariable long productId){
        return feedbackService.listFeedBacksByProductId(productId);
    }

    @DeleteMapping("/feedback/{id}")
    public ResultStatus deleteFeedbackById(@PathVariable long id){
        if(feedbackService.deleteFeedbackWasSuccessful(id)){
            return new ResultStatus(ResultStatusEnum.OK,"Sikeres törlés!");
        }
        return new ResultStatus(ResultStatusEnum.NOT_OK,"A törlés sikertelen.");
    }



}
