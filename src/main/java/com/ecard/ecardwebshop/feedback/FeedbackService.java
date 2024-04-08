package com.ecard.ecardwebshop.feedback;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {


    private FeedbackDao feedbackDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public List<Feedback> listFeedBacksByProductId(long productId) {
        return feedbackDao.listFeedBacksByProductId(productId);
    }

    public boolean giveAFeedback(Feedback feedback) {

        long userId = feedback.getUser().getId();
        long productId = feedback.getProduct().getId();
        long feedbackId;
        boolean feedbackWasSuccessful = false;

        try {
            feedbackId = feedbackDao.getFeedbackIdByUserIdAndProductId(userId, productId);
        } catch (DataAccessException dae) {
            feedbackId = 0;
        }
        if (feedbackDao.alreadyGaveAFeedback(userId, productId)) {

            feedbackDao.updateFeedback(feedback, feedbackId);
            feedbackWasSuccessful = true;
            return feedbackWasSuccessful;
        }
        if (feedbackDao.userCanGiveAFeedback(userId, productId)) {

            feedbackDao.giveAFeedback(feedback);
            feedbackWasSuccessful = true;
            return feedbackWasSuccessful;
        }
        return feedbackWasSuccessful;
    }

    public boolean deleteFeedbackWasSuccessful(long id) {
        boolean sizeOfFeedbacksHasDecreasedByOne = false;
        int sizeOfFeedbackListBeforeDeletion = feedbackDao.getSizeOfFeedbacks();
        feedbackDao.deleteFeedbackById(id);
        int sizeOfFeedbackListAfterDeletion = feedbackDao.getSizeOfFeedbacks();
        if (sizeOfFeedbackListBeforeDeletion > sizeOfFeedbackListAfterDeletion) {
            sizeOfFeedbacksHasDecreasedByOne = true;
        }
        return sizeOfFeedbacksHasDecreasedByOne;
    }

}
