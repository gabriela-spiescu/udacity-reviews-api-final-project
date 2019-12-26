package com.gabriela.fabricadefumuri.reviews;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.gabriela.fabricadefumuri.reviews.entity.CommentDocument;
import com.gabriela.fabricadefumuri.reviews.repository.CommentMongoRepository;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

@DataMongoTest
@RunWith(SpringRunner.class)
public class CommentRepositoryMongoTest {

    @Autowired MongoTemplate mongoTemplate;
    @Autowired CommentMongoRepository commentMongoRepository;

    @Test
    public void test() {
        // given
    	mongoTemplate.remove(CommentDocument.class);
        DBObject objectToSave = BasicDBObjectBuilder.start()
            .add("body", "Ups")
            .add("title", "Milk")
        	.add("isPositiv", true)
        	.add("reviewId", 1)
            .get();

        // when
        mongoTemplate.save(objectToSave, "commentdocument");

      // then
        List<CommentDocument> comments = new ArrayList<CommentDocument>();
        CommentDocument comment = new CommentDocument();
    	comment.setBody("Ups");
    	comment.setTitle("Milk");
    	comment.setIsPositiv(true);
    	comment.setReviewId(1);
        comments.add(comment);
        
        org.assertj.core.api.Assertions.assertThat(commentMongoRepository.findByReviewId(1).get()).extracting("title").contains("Milk");
        org.assertj.core.api.Assertions.assertThat(commentMongoRepository.findByTitle("Milk").get()).extracting("isPositiv").contains(true);
        List<CommentDocument> reviewsList =  mongoTemplate.findAll(CommentDocument.class, "commentdocument");
        Assert.assertEquals(reviewsList.get(0).getBody(), objectToSave.get("body"));
        
}
}