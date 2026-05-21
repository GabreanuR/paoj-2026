-- 1. DROP-URILE (în ordine inversă a cheilor străine)
DROP TABLE IF EXISTS recenzii;
DROP TABLE IF EXISTS comenzi_produse;
DROP TABLE IF EXISTS comenzi;
DROP TABLE IF EXISTS produse;
DROP TABLE IF EXISTS restaurante;
DROP TABLE IF EXISTS soferi;
DROP TABLE IF EXISTS clienti;

-- 2. TABELELE DE BAZĂ
CREATE TABLE clienti
(
    id         VARCHAR(36) PRIMARY KEY,
    nume       VARCHAR(100)        NOT NULL,
    telefon    VARCHAR(20),
    email      VARCHAR(100) UNIQUE NOT NULL,
    strada     VARCHAR(100),
    oras       VARCHAR(50),
    cod_postal VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE soferi
(
    id                  VARCHAR(36) PRIMARY KEY,
    nume                VARCHAR(100)        NOT NULL,
    telefon             VARCHAR(20),
    email               VARCHAR(100) UNIQUE NOT NULL,
    numar_inmatriculare VARCHAR(20),
    este_disponibil     BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE restaurante
(
    id            VARCHAR(36) PRIMARY KEY,
    nume          VARCHAR(100) NOT NULL,
    strada        VARCHAR(100),
    oras          VARCHAR(50),
    cod_postal    VARCHAR(20),
    rating DOUBLE DEFAULT 0.0,
    numar_reviews INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. TABELELE DEPENDENTE (Cu Foreign Keys)
CREATE TABLE produse
(
    id            VARCHAR(36) PRIMARY KEY,
    id_restaurant VARCHAR(36)  NOT NULL,
    nume          VARCHAR(100) NOT NULL,
    pret DOUBLE NOT NULL,
    FOREIGN KEY (id_restaurant) REFERENCES restaurante (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE comenzi
(
    id            VARCHAR(36) PRIMARY KEY,
    id_client     VARCHAR(36) NOT NULL,
    id_restaurant VARCHAR(36) NOT NULL,
    id_sofer      VARCHAR(36),
    pret_total DOUBLE NOT NULL,
    status        VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_client) REFERENCES clienti (id) ON DELETE CASCADE,
    FOREIGN KEY (id_restaurant) REFERENCES restaurante (id) ON DELETE CASCADE,
    FOREIGN KEY (id_sofer) REFERENCES soferi (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE comenzi_produse
(
    id_comanda VARCHAR(36) NOT NULL,
    id_produs  VARCHAR(36) NOT NULL,
    PRIMARY KEY (id_comanda, id_produs),
    FOREIGN KEY (id_comanda) REFERENCES comenzi (id) ON DELETE CASCADE,
    FOREIGN KEY (id_produs) REFERENCES produse (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE recenzii
(
    id            VARCHAR(36) PRIMARY KEY,
    id_client     VARCHAR(36) NOT NULL,
    id_restaurant VARCHAR(36) NOT NULL,
    nota          INT         NOT NULL,
    comentariu    TEXT,
    FOREIGN KEY (id_client) REFERENCES clienti (id) ON DELETE CASCADE,
    FOREIGN KEY (id_restaurant) REFERENCES restaurante (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;