CREATE TABLE Material (
      codigo_interno VARCHAR(20) PRIMARY KEY,
      titulo VARCHAR(150) NOT NULL
      estado VARCHAR(15) DEFAULT 'Activo'
);

CREATE TABLE MaterialEscrito (
     codigo_interno VARCHAR(20) PRIMARY KEY,
     editorial VARCHAR(100),
     FOREIGN KEY (codigo_interno) REFERENCES Material(codigo_interno) ON DELETE CASCADE
);

CREATE TABLE MaterialAudiovisual (
    codigo_interno VARCHAR(20) PRIMARY KEY,
    duracion VARCHAR(50),
    genero VARCHAR(50),
    FOREIGN KEY (codigo_interno) REFERENCES Material(codigo_interno) ON DELETE CASCADE
);

CREATE TABLE Libro (
   codigo_interno VARCHAR(20) PRIMARY KEY,
   autor VARCHAR(100),
   numero_paginas INT,
   isbn VARCHAR(50),
   año_publicacion INT,
   unidades_disponibles INT,
   FOREIGN KEY (codigo_interno) REFERENCES MaterialEscrito(codigo_interno) ON DELETE CASCADE
);

CREATE TABLE Revista (
     codigo_interno VARCHAR(20) PRIMARY KEY,
     periodicidad VARCHAR(50),
     fecha_publicacion DATE,
     unidades_disponibles INT,
     FOREIGN KEY (codigo_interno) REFERENCES MaterialEscrito(codigo_interno) ON DELETE CASCADE
);

CREATE TABLE CD_Audio (
    codigo_interno VARCHAR(20) PRIMARY KEY,
    artista VARCHAR(100),
    num_canciones INT,
    unidades_disponibles INT,
    FOREIGN KEY (codigo_interno) REFERENCES MaterialAudiovisual(codigo_interno) ON DELETE CASCADE
);

CREATE TABLE DVD (
     codigo_interno VARCHAR(20) PRIMARY KEY,
     director VARCHAR(100),
     FOREIGN KEY (codigo_interno) REFERENCES MaterialAudiovisual(codigo_interno) ON DELETE CASCADE
);