INSERT IGNORE INTO problem_table (problem_id,problem_name) VALUES (101,'General Error');
INSERT IGNORE INTO problem_table (problem_id,problem_name) VALUES (102,'Temporary issue');
INSERT IGNORE INTO problem_table (problem_id,problem_name) VALUES (103,'Invalid chicken name');

INSERT IGNORE INTO customer_table (customer_id,customer_first_name,customer_surname,customer_username) VALUES (1,'Mor','Gutman','mgutman');
INSERT IGNORE INTO customer_table (customer_id,customer_first_name,customer_surname,customer_username) VALUES (2,'Hadar','Klien','hklien');
INSERT IGNORE INTO customer_table (customer_id,customer_first_name,customer_surname,customer_username) VALUES (3,'Shani','Gad','sgad');

INSERT IGNORE INTO user_table (user_id,user_type,user_name) VALUES (1,'Engineer','lkalachman');
INSERT IGNORE INTO user_table (user_id,user_type,user_name) VALUES (2,'Admin','dima');
INSERT IGNORE INTO user_table (user_id,user_type,user_name) VALUES (3,'Agent','dana');

INSERT IGNORE INTO root_cause_table (root_cause_id,root_cause_name) VALUES (1,'Bad username');
INSERT IGNORE INTO root_cause_table (root_cause_id,root_cause_name) VALUES (2,'Invalid chicken');

INSERT IGNORE INTO tags_table (tag_name) VALUES ('check again');
INSERT IGNORE INTO tags_table (tag_name) VALUES ('unknown issue');




