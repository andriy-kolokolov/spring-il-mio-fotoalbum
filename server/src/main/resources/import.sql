-- Users
INSERT INTO experis_photoalbum.users (username)
VALUES ('Alice'),
       ('Bob'),
       ('Charlie'),
       ('David'),
       ('Emma'),
       ('Fiona'),
       ('George'),
       ('Hannah'),
       ('Ian'),
       ('Julia');


-- Categories
INSERT INTO experis_photoalbum.categories (name)
VALUES ('Nature'),
       ('Urban'),
       ('Portrait'),
       ('Landscape'),
       ('Abstract'),
       ('Wildlife'),
       ('Street'),
       ('Travel'),
       ('Food'),
       ('Fashion');


-- Photos
INSERT INTO experis_photoalbum.photos (title, description, is_visible, user_id)
VALUES ('Sunset', 'Beautiful sunset at the beach', TRUE, 1),
       ('City Lights', 'Night view of the city skyline', TRUE, 2),
       ('Smile', 'Portrait of a smiling person', TRUE, 3),
       ('Mountain', 'A view of the mountain', TRUE, 4),
       ('Abstract Art', 'Colorful abstract painting', TRUE, 5),
       ('Tiger', 'Wild tiger in the jungle', TRUE, 6),
       ('Street Life', 'Everyday street life', TRUE, 7),
       ('Eiffel Tower', 'Eiffel Tower in Paris', TRUE, 8),
       ('Delicious Cake', 'A slice of chocolate cake', TRUE, 9),
       ('Fashion Show', 'Models on the runway', TRUE, 10);


-- Photo-Category Relationships
INSERT INTO experis_photoalbum.photo_category (photo_id, category_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);


--  Messages
INSERT INTO experis_photoalbum.messages (content, sent_at, sender_id, receiver_id)
VALUES ('Hi, great photo!', FROM_UNIXTIME(
            UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
            ), 1, 2),
       ('Thank you!', FROM_UNIXTIME(
                   UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
           ), 2, 1),
       ('Can we meet for a photoshoot?', FROM_UNIXTIME(
                   UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
           ), 3, 1),
       ('Loved your latest post!', FROM_UNIXTIME(
                   UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
           ), 4, 3),
       ('Let\'s collaborate on a project.', FROM_UNIXTIME(
                   UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
           ), 5, 4),
       ('Can I purchase your photo?', FROM_UNIXTIME(
                   UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
           ), 6, 5),
       ('Join our photography contest!', FROM_UNIXTIME(
                   UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
           ), 7, 6),
       ('Your portfolio is impressive.', FROM_UNIXTIME(
                   UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
           ), 8, 7),
       ('Seeking permission to feature your work.', FROM_UNIXTIME(
                   UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
           ), 9, 8),
       ('How do you achieve such clarity?', FROM_UNIXTIME(
                   UNIX_TIMESTAMP('2021-10-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))
           ), 10, 9);
