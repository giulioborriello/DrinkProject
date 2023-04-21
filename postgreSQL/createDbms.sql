CREATE DATABASE catalogo_drink;

--c catalogo_drink;

CREATE TABLE drink (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    descrizione TEXT,
    prezzo DECIMAL(5,2) NOT NULL,
    immagine BYTEA NOT NULL
);
-- Inserimento di un record nella tabella "drink"
-- INSERT INTO drink (nome, categoria, descrizione, immagine)
-- VALUES ('Mojito', 'Alcoholic','lista bla blabla', pg_read_binary_file('/images/drink.jpg'));

CREATE TABLE utente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    dati_Biometrici bytea,
    password VARCHAR(100) NOT NULL
);

-- Creazione della tabella "Ordine"
CREATE TABLE ordine (
  id SERIAL PRIMARY KEY,
  utente_id INTEGER REFERENCES utente(id),
  data_ordine TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Creazione della tabella "DrinkOrdine"
CREATE TABLE drink_ordine (
  id SERIAL PRIMARY KEY,
  drink_id INTEGER REFERENCES drink(id),
  ordine_id INTEGER REFERENCES ordine(id),
  quantita INTEGER NOT NULL,
  prezzo DECIMAL(10,2) NOT NULL
);

--creazione della tabella "Ingredienti"
CREATE TABLE ingredienti(
    nome VARCHAR(50) PRIMARY KEY,
    origine VARCHAR(50) NOT NULL,
    allergene BOOLEAN NOT NULL
);

create table if not exists Composizione(
	drink_ID integer references drink(id),
	ingrediente varchar(50) references ingredienti(nome),
	quantita DECIMAL(10,2) NOT NULL
);


CREATE OR REPLACE VIEW public.frequenza_ingredienti as
SELECT ing.nome as nome_ingrediente, storico.quantita as frequenza,
ord.utente_id as compratore_id 
from drink_ordine as storico 
	join ordine as ord on storico.ordine_id=ord.id 
	join drink on drink.id=storico.drink_id 
	join composizione as comp on comp.drink_id=drink.id 
	join ingredienti as ing on comp.ingrediente=ing.nome 
order by storico.quantita;
-- FUNCTION: public.raccomanda_drink(integer)

-- DROP FUNCTION IF EXISTS public.raccomanda_drink(integer);

CREATE OR REPLACE FUNCTION public.raccomanda_drink(
	id_utente integer)
    RETURNS TABLE(id integer, nome character varying, categoria character varying, descrizione text, prezzo numeric, immagine bytea, punteggio bigint ) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
BEGIN
    RETURN QUERY SELECT 
        d.id, 
        d.nome, 
        d.categoria, 
        d.descrizione, 
        d.prezzo, 
        d.immagine, 
        SUM(CASE WHEN do1.drink_id IN (
                SELECT drink_id 
                FROM drink_ordine do2 
                WHERE do2.ordine_id IN (
                    SELECT ordine_id 
                    FROM drink_ordine 
                    WHERE drink_id = d.id AND ordine_id IN (
                        SELECT ordine.id 
                        FROM ordine 
                        WHERE utente_id = id_utente
                    )
                ) AND do2.drink_id != d.id
            ) THEN 1 ELSE 0 END) AS punteggio
    FROM drink d
    INNER JOIN drink_ordine do1 ON d.id = do1.drink_id
    INNER JOIN ordine o ON do1.ordine_id = o.id
    WHERE o.utente_id <> id_utente -- questa riga da più varieta escludendo gli ordini dell'utente
    GROUP BY d.id
    ORDER BY punteggio DESC
    LIMIT 10;
END;
$BODY$;

ALTER FUNCTION public.raccomanda_drink(integer)
    OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.racconada_drink_Ingrediente(IN id_utente integer)
    RETURNS setof frequenza_ingredienti
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

    as $BODY$
    DECLARE 
        ingrediente_Candidato character varying;
    BEGIN
        SELECT nome_ingrediente INTO ingrediente_Candidato FROM frequenza_ingredienti WHERE compratore_id=id_utente;
        RETURN QUery select * from drink join composizione on drink.id=composizione.drink_id order by random() limit 1;
    END;
    $BODY$;