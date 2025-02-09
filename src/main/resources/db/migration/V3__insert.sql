COPY book(id, title, author, main_genre, sub_genre, type, price, rating, no_people_rated, url)
FROM '/docker-entrypoint-initdb.d/books.csv'
DELIMITER ','
CSV HEADER;