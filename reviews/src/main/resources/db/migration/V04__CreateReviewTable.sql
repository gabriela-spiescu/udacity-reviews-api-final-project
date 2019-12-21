CREATE TABLE review (
  id INT NOT NULL AUTO_INCREMENT,
  author VARCHAR(300) NOT NULL,
  title VARCHAR(300) NOT NULL,
  score INT,
  product_id INT NOT NULL,
  created_time TIMESTAMP NOT NULL,
  constraint review_pk primary key (id));