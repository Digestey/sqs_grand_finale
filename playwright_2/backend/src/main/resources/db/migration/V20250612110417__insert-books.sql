-- Inserts 100 books with generated titles and random lent status
INSERT INTO book (title, lent)
SELECT
    CONCAT('Book Title ', seq.n) AS title,
    FLOOR(RAND() * 2) AS lent
FROM (
         SELECT
             (@rownum := @rownum + 1) AS n
         FROM
             information_schema.tables t1,
             information_schema.tables t2,
             (SELECT @rownum := 0) r
         LIMIT 100
     ) AS seq;