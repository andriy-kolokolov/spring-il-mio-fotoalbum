-- Users
INSERT INTO experis_photoalbum.users (username)
VALUES ('Alice'),
       ('Bob'),
       ('Charlie');

-- Categories
INSERT INTO experis_photoalbum.categories (name)
VALUES ('Nature'),
       ('Urban'),
       ('Portrait');

-- Photos
INSERT INTO experis_photoalbum.photos (title, description, is_visible, user_id)
VALUES ('Sunset', 'Beautiful sunset at the beach', TRUE, 1),
       ('City Lights', 'Night view of the city skyline', TRUE, 2),
       ('Smile', 'Portrait of a smiling person', TRUE, 3);

-- Photo-Category Relationships
INSERT INTO experis_photoalbum.photo_category (photo_id, category_id)
VALUES (1, 1), -- Sunset in Nature
       (2, 2), -- City Lights in Urban
       (3, 3); -- Smile in Portrait


-- Messages
INSERT INTO experis_photoalbum.messages (content, sent_at, sender_id, receiver_id)
VALUES ('Hi, great photo!', NOW(), 1, 2),
       ('Thank you!', NOW(), 2, 1),
       ('Can we meet for a photoshoot?', NOW(), 3, 1);
