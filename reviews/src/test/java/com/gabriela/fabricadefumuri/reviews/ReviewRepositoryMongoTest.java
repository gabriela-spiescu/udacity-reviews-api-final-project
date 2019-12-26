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

import com.gabriela.fabricadefumuri.reviews.entity.ReviewDocument;
import com.gabriela.fabricadefumuri.reviews.repository.ReviewMongoRepository;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ReviewRepositoryMongoTest {

    @Autowired MongoTemplate mongoTemplate;
    @Autowired ReviewMongoRepository reviewMongoRepository;

    @Test
    public void test() {
        // given
    	mongoTemplate.remove(ReviewDocument.class);
        DBObject objectToSave = BasicDBObjectBuilder.start()
            .add("author", "Monica")
            .add("title", "Baboi")
        	.add("score", 37)
        	.add("productid", 1)
            .get();

        // when
        mongoTemplate.save(objectToSave, "reviewdocument");

      // then
        List<ReviewDocument> reviews = new ArrayList<ReviewDocument>();
        ReviewDocument review = new ReviewDocument();
    	review.setAuthor("Monica");
    	review.setTitle("Baboi");
    	review.setScore(37);
    	review.setProductid(1);
        reviews.add(review);
        
        org.assertj.core.api.Assertions.assertThat(reviewMongoRepository.findByTitle("Baboi")).extracting("score").contains(37);
        org.assertj.core.api.Assertions.assertThat(reviewMongoRepository.findAllByProductid(1)).extracting("author").contains("Monica");
        List<ReviewDocument> reviewsList =  mongoTemplate.findAll(ReviewDocument.class, "reviewdocument");
        Assert.assertEquals(reviewsList.get(0).getAuthor(), objectToSave.get("author"));
        
}
}