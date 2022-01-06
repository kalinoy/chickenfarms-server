CREATE DATABASE IF NOT EXISTS chickenFarm_db;


CREATE TABLE IF NOT EXISTS `problem_table` (
                          `problem_id` int NOT NULL,
                          `problem_name` varchar(50) DEFAULT NULL,
                          PRIMARY KEY (`problem_id`)
);

CREATE TABLE IF NOT EXISTS `customer_table` (
                          `customer_id` bigint NOT NULL,
                          `customer_first_name` varchar(50) DEFAULT NULL, 
                          `customer_surname` varchar(50) DEFAULT NULL,
                          `customer_username` varchar(50) DEFAULT NULL,
                          PRIMARY KEY (`customer_id`)
);

CREATE TABLE IF NOT EXISTS `user_table` (
                          `user_id` bigint NOT NULL,
                          `user_type` varchar(50) DEFAULT NULL, 
                          `user_name` varchar(50) DEFAULT NULL,
                          PRIMARY KEY (`user_id`)
);

CREATE TABLE IF NOT EXISTS `root_cause_table` (
                          `root_cause_id` int NOT NULL AUTO_INCREMENT,
                          `root_cause_name` varchar(50) DEFAULT NULL, 
                          PRIMARY KEY (`root_cause_id`)
);

CREATE TABLE IF NOT EXISTS `tags_table` (
                          `tag_name` varchar(50) NOT NULL,
                          PRIMARY KEY (`tag_name`)
);



