-- Inserimento di alcuni record nella tabella "drink"
INSERT INTO drink (id, nome, categoria, descrizione, prezzo, immagine)
VALUES
(1, 'Mojito', 'Alcolici', 'Un cocktail a base di rum, lime, zucchero di canna, soda e foglie di menta', 7.50, pg_read_binary_file('images/mojito.jpg')),
(2, 'Spritz', 'Alcolici', 'Un cocktail a base di prosecco, aperol e soda', 6.50, pg_read_binary_file('images/spritz.jpg')),
(3, 'Acqua', 'Analcolici', 'Acqua naturale o frizzante', 1.50, pg_read_binary_file('images/acqua.jpg')),
(4, 'Coca-Cola', 'Analcolici', 'Bevanda gassata al gusto di cola', 2.00, pg_read_binary_file('images/cocacola.jpg')),
(5, 'Frullato di frutta', 'Frullati', 'Frullato di fragole, banane e yogurt', 4.50, pg_read_binary_file('images/frullato.jpg'));

-- Inserimento di alcuni record nella tabella "utente"
INSERT INTO utente (id, nome, cognome, email, password)
VALUES
(1, 'Mario', 'Rossi', 'mario.rossi@example.com', 'mario123'),
(2, 'Luca', 'Bianchi', 'luca.bianchi@example.com', 'luca123'),
(3, 'Anna', 'Verdi', 'anna.verdi@example.com', 'anna123');

-- Inserimento di alcuni record nella tabella "ordine"
INSERT INTO ordine (id, utente_id, data_ordine)
VALUES
(1, 1, '2022-03-15 14:30:00'),
(2, 1, '2022-03-17 16:45:00'),
(3, 2, '2022-03-18 10:15:00'),
(4, 3, '2022-03-19 12:00:00'),
(5, 3, '2022-03-20 19:30:00');

-- Inserimento di alcuni record nella tabella "drink_ordine"
INSERT INTO drink_ordine (drink_id, ordine_id, quantita, prezzo)
VALUES
(1, 1, 1, 7.50),
(2, 1, 2, 6.50),
(3, 2, 1, 1.50),
(4, 2, 1, 2.00),
(5, 3, 1, 4.50),
(1, 3, 1, 7.50),
(2, 4, 1, 6.50),
(5, 4, 2, 9.00),
(1, 5, 2, 15.00),
(3, 5, 1, 1.50);



INSERT INTO ingredienti (nome, origine, allergene)
VALUES ('Rum', 'Caraibi', false),
       ('Lime', 'Messico', false),
       ('Zucchero di canna', 'Brasile', false),
       ('Soda', 'USA', false),
       ('Foglie di menta', 'Marocco', false),
       ('Prosecco', 'Italia', true),
       ('Aperol', 'Italia', false),
       ('Fragole', 'Spagna', false),
       ('Banane', 'Costa Rica', false),
       ('Yogurt', 'Grecia', true),
       ('Acqua naturale', 'Francia', false),
       ('Coca-Cola', 'USA', false),
       ('Gin', 'UK', false),
       ('Vermouth rosso', 'Italia', true),
       ('Campari', 'Italia', false),
       ('Brandy', 'Francia', false),
       ('Crema di cacao', 'Belgio', false),
       ('Panna fresca', 'Italia', true),
       ('Frutta fresca', 'Spagna', false),
       ('Succo di cocco', 'Thailandia', false),
       ('Magia', 'Non specificato', false);
INSERT INTO Composizione (drink_id, ingrediente, quantita)
VALUES (1, 'Rum', 50.00),
       (1, 'Lime', 10.00),
       (1, 'Zucchero di canna', 10.00),
       (1, 'Soda', 100.00),
       (1, 'Foglie di menta', 5.00),
       (2, 'Prosecco', 75.00),
       (2, 'Aperol', 25.00),
       (2, 'Soda', 25.00),
       (5, 'Fragole', 50.00),
       (5, 'Banane', 50.00),
       (5, 'Yogurt', 100.00),
       (3, 'Acqua naturale', 500.00),
       (4, 'Coca-Cola', 330.00),
       (6, 'Gin', 30.00),
       (6, 'Vermouth rosso', 30.00),
       (6, 'Campari', 30.00),
       (9, 'Brandy', 50.00),
       (9, 'Crema di cacao', 25.00),
       (9, 'Panna fresca', 25.00),
       (7, 'Frutta fresca', 50.00),
       (7, 'Succo di cocco', 50.00),
       (7, 'Magia', 5.00);



























-- Inserimento del drink "Pina Colada"
INSERT INTO drink (id,nome, categoria, descrizione, prezzo, immagine)
VALUES (11,'Pina Colada', 'Alcolico', 'Un delizioso cocktail a base di rum, succo di ananas e latte di cocco.', 8.50, '/images/pina_colada.jpg');

-- Inserimento degli ingredienti per il drink "Pina Colada"
INSERT INTO ingredienti (nome, origine, allergene)
VALUES
    ('Rum', 'Internazionale', FALSE),
    ('Succo di Ananas', 'Internazionale', FALSE),
    ('Latte di Cocco', 'Internazionale', FALSE);

-- Inserimento della composizione del drink "Pina Colada"
INSERT INTO composizione (drink_id, ingrediente, quantita)
VALUES
    ((SELECT id FROM drink WHERE nome = 'Pina Colada'), 'Rum', 50),
    ((SELECT id FROM drink WHERE nome = 'Pina Colada'), 'Succo di Ananas', 100),
    ((SELECT id FROM drink WHERE nome = 'Pina Colada'), 'Latte di Cocco', 50);


INSERT INTO ingredienti (nome, origine, allergene)
VALUES
    ('Tequila', 'Messico', false),
    ('Triple sec', 'Varie', false),
    ('Sale', 'Varie', false);

-- Inserimento della composizione del Margarita
INSERT INTO Composizione (drink_ID, ingrediente, quantita)
VALUES
    (10, 'Tequila', 50),
    (10, 'Triple sec', 20),
    (10, 'Lime', 30),
    (10, 'Sale', 5);