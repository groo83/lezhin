-- 초기 Content 데이터를 삽입

INSERT INTO member (email, name, password, authority, adult_state)
VALUES ('user1@example.com', 'User One', '$2a$10$7QkHeD8Ix/SnrVvF8/FT/OtRgN78Qw9NUeUS.puVotgHTcMEQFCie', 'USER', 'GENERAL'); -- password123

INSERT INTO member (email, name, password, authority, adult_state)
VALUES ('admin@example.com', 'Admin User', '$2a$10$9vCklcPldPIklj1PjfbFf.NQ3pA4Vns.3HlOZxZUNL6fXHFHdK7Iu', 'ADMIN', 'GENERAL'); -- adminpassword

INSERT INTO member (email, name, password, authority, adult_state)
VALUES ('user2@example.com', 'User Two', '$2a$10$Erb.rTHK9oQsMg7gbK1s3OnI52FB97zXLy9J0XFGXs77HYktvvG.W', 'USER', 'ADULT'); -- password456


INSERT INTO content (title, genres, coin, pay_type, rating_type, created_date, modified_date)
VALUES ('Romantic Story', 'Romance', 3, 'PAID', 'ADULT', '2023-01-01 10:00:00', '2023-01-01 10:00:00');

INSERT INTO content (title, genres, coin, pay_type, rating_type, created_date, modified_date)
VALUES ('Adventure Saga', 'Adventure', 0, 'FREE', 'GENERAL', '2023-02-01 12:00:00', '2023-02-01 12:00:00');

INSERT INTO content (title, genres, coin, pay_type, rating_type, created_date, modified_date)
VALUES ('Mystery Chronicles', 'Mystery', 4, 'PAID', 'ADULT', '2023-03-01 15:00:00', '2023-03-01 15:00:00');
