-- Courses and lessons
INSERT INTO COURSES (id, name) VALUES (1, 'Programming');

INSERT INTO LESSONS (id, title, position, course_id) VALUES (1, 'Introduction to Programming', 1, 1);
INSERT INTO LESSONS (id, title, position, course_id) VALUES (2, 'Variables and Data Types', 2, 1);
INSERT INTO LESSONS (id, title, position, course_id) VALUES (3, 'Control Structures', 3, 1);

-- Posts and comments
INSERT INTO POSTS (id, title, content, date_of_creation) VALUES (1, 'Lab internet', 'Content', '2025-11-20');
INSERT INTO COMMENTS (id, content, date_of_creation, post_id) VALUES (1, 'First comment', '2025-12-18', 1);
INSERT INTO COMMENTS (id, content, date_of_creation, post_id) VALUES (2, 'Second comment', '2025-12-19', 1);

-- Categories
INSERT INTO CATEGORIES (name, parent_name) VALUES ('Books', NULL);
INSERT INTO CATEGORIES (name, parent_name) VALUES ('Non-Fiction', 'Books');
INSERT INTO CATEGORIES (name, parent_name) VALUES ('Philosophy', 'Non-Fiction');
INSERT INTO CATEGORIES (name, parent_name) VALUES ('Economy', 'Non-Fiction');
INSERT INTO CATEGORIES (name, parent_name) VALUES ('Fiction', 'Books');
INSERT INTO CATEGORIES (name, parent_name) VALUES ('Fantasy', 'Fiction');
INSERT INTO CATEGORIES (name, parent_name) VALUES ('Romance', 'Fiction');

-- Products
INSERT INTO PRODUCTS (id, name, price) VALUES (1, 'Economics 101', 15.50);
INSERT INTO PRODUCTS (id, name, price) VALUES (2, 'Love Story', 9.00);

INSERT INTO PRODUCT_CATEGORIES (product_id, category_name) VALUES (1, 'Economy');
INSERT INTO PRODUCT_CATEGORIES (product_id, category_name) VALUES (2, 'Fiction');
INSERT INTO PRODUCT_CATEGORIES (product_id, category_name) VALUES (2, 'Romance');
