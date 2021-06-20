CREATE TABLE IF NOT EXISTS `address` (
                           `address_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `country` varchar(255) NOT NULL,
                           `city` varchar(255) NOT NULL,
                           `street` varchar(255) NOT NULL,
                           `house` int(11) NOT NULL,
                           `flat` mediumint(1) NOT NULL,
                           `postal_code` varchar(255) NOT NULL,
                           PRIMARY KEY (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `roles` (
                         `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `role_name` varchar(255) NOT NULL,
                         PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `category` (
                            `category_id` mediumint(15) NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) NOT NULL,
                            PRIMARY KEY (`category_id`),
                            UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `parameters` (
                              `parameters_id` int(11) NOT NULL AUTO_INCREMENT,
                              `age` smallint(6) NOT NULL,
                              `gender` varchar(255) NOT NULL,
                              `lifespan` varchar(255) NOT NULL,
                              `weight` smallint(6) NOT NULL,
                              PRIMARY KEY (`parameters_id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `users` (
                         `user_id` int(11) NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(255) NOT NULL,
                         `last_name` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `birthday` date NOT NULL,
                         `user_adress` bigint(20) NOT NULL,
                         `user_roles` bigint(20) NOT NULL,
                         PRIMARY KEY (`user_id`),
                         KEY `address_fk` (`user_adress`),
                         KEY `role_fk` (`user_roles`),
                         CONSTRAINT `address_fk` FOREIGN KEY (`user_adress`) REFERENCES `address` (`address_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                         CONSTRAINT `role_fk` FOREIGN KEY (`user_roles`) REFERENCES `roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

INSERT INTO `my_store`.`users` (`first_name`,
                                `last_name`,
                                `password`,
                                `email`,
                                `birthday`,
                                `user_adress`,
                                `user_roles`)
VALUES ('guest',
        'guest',
        '$2a$10$xEgmCM9cZe1Hoq8f9zSqTOiw8nS/kq1QMBcuJQ.QtxfFhbTI3.jsC',
        'guest@gmail.com',
        '1990-11-11',
        '148', '3');

CREATE TABLE IF NOT EXISTS `products` (
                            `product_id` int(11) NOT NULL AUTO_INCREMENT,
                            `productTitle` varchar(255) NOT NULL,
                            `price` decimal(19,2) NOT NULL,
                            `category` mediumint(15) NOT NULL,
                            `quantity` tinyint(4) NOT NULL,
                            `product_param` int(11) NOT NULL,
                            `foto_id` varchar(255) NOT NULL,
                            PRIMARY KEY (`product_id`),
                            KEY `params_fk` (`product_param`),
                            KEY `category_fk_idx` (`category`),
                            CONSTRAINT `category_fk` FOREIGN KEY (`category`) REFERENCES `category` (`category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                            CONSTRAINT `params_fk` FOREIGN KEY (`product_param`) REFERENCES `parameters` (`parameters_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `carts` (
                         `cart_id` binary(16) NOT NULL,
                         `owner_id` int(11) DEFAULT NULL,
                         `price` decimal(19,2) DEFAULT NULL,
                         PRIMARY KEY (`cart_id`),
                         KEY `owner_fk_idx` (`owner_id`),
                         CONSTRAINT `owner_fk` FOREIGN KEY (`owner_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `cart_items` (
                              `cart_item_id` int(11) NOT NULL AUTO_INCREMENT,
                              `cart_id` binary(16) DEFAULT NULL,
                              `product_id` int(11) DEFAULT NULL,
                              `quantity` int(11) DEFAULT NULL,
                              `price_per_product` decimal(19,2) DEFAULT NULL,
                              `cart_item_price` decimal(19,2) DEFAULT NULL,
                              `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (`cart_item_id`),
                              KEY `cart_id_fk_idx` (`cart_id`),
                              KEY `product_id_fk_idx` (`product_id`),
                              CONSTRAINT `cart_id_fk` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                              CONSTRAINT `product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `orders` (
                          `order_id` binary(16) NOT NULL,
                          `owner_id` int(11) NOT NULL,
                          `price` decimal(19,2) NOT NULL,
                          `user_address` bigint(20) NOT NULL,
                          `payment_method` varchar(255) DEFAULT 'cash',
                          `delivery_method` varchar(255) DEFAULT 'from store',
                          `payment_state` tinyint(4) DEFAULT '0',
                          `order_state` varchar(255) NOT NULL,
                          `created_at` date DEFAULT NULL,
                          PRIMARY KEY (`order_id`),
                          KEY `address_fk_idx` (`user_address`),
                          KEY `user_fk_idx` (`owner_id`),
                          KEY `order_state_fk_idx` (`order_state`),
                          CONSTRAINT `order_user_address_fk` FOREIGN KEY (`user_address`) REFERENCES `address` (`address_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                          CONSTRAINT `user_fk` FOREIGN KEY (`owner_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `order_items` (
                               `order_items_id` int(11) NOT NULL AUTO_INCREMENT,
                               `order_id` binary(16) NOT NULL,
                               `product_id` int(11) NOT NULL,
                               `quantity` int(11) NOT NULL,
                               `price_per_product` decimal(19,2) NOT NULL,
                               `price` decimal(19,2) NOT NULL,
                               `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                               `order_state` varchar(255) NOT NULL,
                               PRIMARY KEY (`order_items_id`),
                               KEY `order_product_if_fk_idx` (`product_id`),
                               CONSTRAINT `order_product_if_fk` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=571 DEFAULT CHARSET=utf8;
