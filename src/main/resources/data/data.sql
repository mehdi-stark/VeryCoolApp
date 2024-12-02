CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255),
    username VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    user_role VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP);

CREATE TABLE IF NOT EXISTS ideas (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Identifiant unique pour l'idée
                                     title VARCHAR(255),                    -- Titre de l'idée
    description TEXT,                      -- Description détaillée de l'idée
    nb_likes INT,                          -- Nombre de likes pour l'idée
    media_url VARCHAR(255),                -- URL vers un média associé (photo ou vidéo)
    user_id BIGINT,                        -- Identifiant de l'utilisateur qui a soumis l'idée
    created_at TIMESTAMP,                  -- Date de création de l'idée
    updated_at TIMESTAMP,                  -- Date de la dernière mise à jour de l'idée
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS comments (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- Identifiant unique du commentaire
                                        content TEXT,                             -- Contenu du commentaire
                                        created_at TIMESTAMP,                     -- Date de création du commentaire
                                        updated_at TIMESTAMP,                     -- Date de mise à jour du commentaire
                                        idea_id BIGINT,                           -- L'idée à laquelle ce commentaire appartient
                                        user_id BIGINT,                           -- L'utilisateur qui a écrit ce commentaire
                                        FOREIGN KEY (idea_id) REFERENCES ideas(id),  -- Lier le commentaire à une idée
    FOREIGN KEY (user_id) REFERENCES users(id)   -- Lier le commentaire à un utilisateur
    );

-- Insert Users
INSERT INTO users (id, full_name, email, password, user_role, created_at, updated_at)
VALUES
    (1, 'Alice Smith', 'alice.smith@example.com', 'password123', 'admin', NOW(), NOW()),
    (2, 'Bob Johnson', 'bob.johnson@example.com', 'password456', 'user', NOW(), NOW()),
    (3, 'Charlie Brown', 'charlie.brown@example.com', 'password789', 'user', NOW(), NOW()),
    (4, 'Diana Prince', 'diana.prince@example.com', 'password101', 'admin', NOW(), NOW());

-- Insert Ideas
-- INSERT INTO ideas (id, title, description, nb_likes, media_url, user_id, created_at, updated_at)
-- VALUES
--     (1, 'First Idea', 'This is Alices first idea', 5, 'https://example.com/media1.jpg', 1, NOW(), NOW()),
--     (2, 'Second Idea', 'This is Alices second idea with more likes', 10, 'https://example.com/media2.jpg', 1, NOW(), NOW()),
--     (3, 'Third Idea', 'This is Bobs first idea', 3, 'https://example.com/media3.jpg', 2, NOW(), NOW()),
--     (4, 'Fourth Idea', 'This is Charlies innovative idea', 7, 'https://example.com/media4.jpg', 3, NOW(), NOW()),
--     (5, 'Fifth Idea', 'This is Dianas groundbreaking idea', 12, 'https://example.com/media5.jpg', 4, NOW(), NOW());
--
-- -- Insert Comments
-- INSERT INTO comments (id, content, created_at, updated_at, idea_id, user_id)
-- VALUES
--     (1, 'Great idea! I think it can be improved with more details.', NOW(), NOW(), 1, 1),
--     (2, 'I like the concept, but I would add a feature for user feedback.', NOW(), NOW(), 1, 1),
--     (3, 'This idea is amazing! I can see it becoming very popular.', NOW(), NOW(), 2, 1),
--     (4, 'I think this idea has potential but needs more research.', NOW(), NOW(), 3, 2),
--     (5, 'Great start! Keep developing it.', NOW(), NOW(), 3, 3),
--     (6, 'This idea is revolutionary! It could change the industry.', NOW(), NOW(), 4, 1),
--     (7, 'I have seen similar ideas before, but this one stands out.', NOW(), NOW(), 4, 4),
--     (8, 'Incredible idea! I would love to see a prototype.', NOW(), NOW(), 5, 2),
--     (9, 'This idea is ahead of its time. Well done!', NOW(), NOW(), 5, 3);
