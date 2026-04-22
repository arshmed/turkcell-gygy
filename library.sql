-- KÜTÜPHANE YÖNETİM SİSTEMİ

CREATE TYPE uye_durum    AS ENUM ('aktif', 'pasif', 'mezun');
CREATE TYPE gorevli_rol  AS ENUM ('kutuphane_memuru', 'yonetici');
CREATE TYPE odunc_durum  AS ENUM ('devam', 'iade_edildi', 'gecikti');
CREATE TYPE kitap_durum  AS ENUM ('iyi', 'hasarli', 'kayip');
CREATE TYPE ceza_turu    AS ENUM ('gecikme', 'hasar', 'kayip');


-- ============================================================
-- 1. KATEGORİ
-- ============================================================
CREATE TABLE KATEGORI (
    kategori_id   SERIAL        PRIMARY KEY,
    kategori_adi  VARCHAR(100)  NOT NULL,
    aciklama      TEXT
);

-- ============================================================
-- 2. YAYINEVİ
-- ============================================================
CREATE TABLE YAYINEVI (
    yayinevi_id   SERIAL        PRIMARY KEY,
    yayinevi_adi  VARCHAR(200)  NOT NULL,
    adres         VARCHAR(300),
    telefon       VARCHAR(20),
    email         VARCHAR(150)
);

-- ============================================================
-- 3. YAZAR
-- ============================================================
CREATE TABLE YAZAR (
    yazar_id      SERIAL        PRIMARY KEY,
    yazar_adi     VARCHAR(100)  NOT NULL,
    yazar_soyadi  VARCHAR(100)  NOT NULL,
    biyografi     TEXT
);

-- ============================================================
-- 4. KİTAP
-- ============================================================
CREATE TABLE KITAP (
    kitap_id      SERIAL        PRIMARY KEY,
    isbn          VARCHAR(13)   NOT NULL UNIQUE,
    kitap_adi     VARCHAR(255)  NOT NULL,
    kategori_id   INT           NOT NULL,
    yayinevi_id   INT           NOT NULL,
    yayin_yili    INT,
    sayfa_sayisi  INT,
    toplam_adet   INT           NOT NULL DEFAULT 1,
    mevcut_adet   INT           NOT NULL DEFAULT 1,

    CONSTRAINT fk_kitap_kategori
        FOREIGN KEY (kategori_id) REFERENCES KATEGORI (kategori_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_kitap_yayinevi
        FOREIGN KEY (yayinevi_id) REFERENCES YAYINEVI (yayinevi_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ============================================================
-- 5. KİTAP_YAZAR (Ara Tablo – M:N)
-- ============================================================
CREATE TABLE KITAP_YAZAR (
    kitap_id  INT NOT NULL,
    yazar_id  INT NOT NULL,

    PRIMARY KEY (kitap_id, yazar_id),

    CONSTRAINT fk_ky_kitap
        FOREIGN KEY (kitap_id) REFERENCES KITAP (kitap_id)
        ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT fk_ky_yazar
        FOREIGN KEY (yazar_id) REFERENCES YAZAR (yazar_id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ============================================================
-- 6. ÖĞRENCİ
-- ============================================================
CREATE TABLE OGRENCI (
    ogrenci_id    SERIAL        PRIMARY KEY,
    ogrenci_no    VARCHAR(20)   NOT NULL UNIQUE,
    ad            VARCHAR(100)  NOT NULL,
    soyad         VARCHAR(100)  NOT NULL,
    email         VARCHAR(150),
    telefon       VARCHAR(20),
    bolum         VARCHAR(150),
    kayit_tarihi  DATE          NOT NULL DEFAULT CURRENT_DATE,
    durum         uye_durum     NOT NULL DEFAULT 'aktif'
);

-- ============================================================
-- 7. GÖREVLİ
-- ============================================================
CREATE TABLE GOREVLI (
    gorevli_id    SERIAL        PRIMARY KEY,
    ad            VARCHAR(100)  NOT NULL,
    soyad         VARCHAR(100)  NOT NULL,
    email         VARCHAR(150),
    telefon       VARCHAR(20),
    gorev         gorevli_rol   NOT NULL DEFAULT 'kutuphane_memuru',
    ise_baslama   DATE          NOT NULL
);

-- ============================================================
-- 8. ÖDÜNÇ ALMA
-- ============================================================
CREATE TABLE ODUNC_ALMA (
    odunc_id        SERIAL      PRIMARY KEY,
    kitap_id        INT         NOT NULL,
    ogrenci_id      INT         NOT NULL,
    gorevli_id      INT         NOT NULL,
    odunc_tarihi    DATE        NOT NULL DEFAULT CURRENT_DATE,
    son_iade_tarihi DATE        NOT NULL,
    durum           odunc_durum NOT NULL DEFAULT 'devam',

    CONSTRAINT fk_odunc_kitap
        FOREIGN KEY (kitap_id) REFERENCES KITAP (kitap_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_odunc_ogrenci
        FOREIGN KEY (ogrenci_id) REFERENCES OGRENCI (ogrenci_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_odunc_gorevli
        FOREIGN KEY (gorevli_id) REFERENCES GOREVLI (gorevli_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ============================================================
-- 9. İADE
-- ============================================================
CREATE TABLE IADE (
    iade_id        SERIAL       PRIMARY KEY,
    odunc_id       INT          NOT NULL,
    gorevli_id     INT          NOT NULL,
    iade_tarihi    DATE         NOT NULL DEFAULT CURRENT_DATE,
    kitap_durumu   kitap_durum  NOT NULL DEFAULT 'iyi',
    notlar         TEXT,

    CONSTRAINT fk_iade_odunc
        FOREIGN KEY (odunc_id) REFERENCES ODUNC_ALMA (odunc_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_iade_gorevli
        FOREIGN KEY (gorevli_id) REFERENCES GOREVLI (gorevli_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ============================================================
-- 10. CEZA
-- ============================================================
CREATE TABLE CEZA (
    ceza_id           SERIAL       PRIMARY KEY,
    odunc_id          INT          NOT NULL,
    ceza_turu_alan    ceza_turu    NOT NULL,
    tutar             NUMERIC(10,2) NOT NULL,
    odendi_mi         BOOLEAN      NOT NULL DEFAULT FALSE,
    olusturma_tarihi  DATE         NOT NULL DEFAULT CURRENT_DATE,
    odeme_tarihi      DATE,

    CONSTRAINT fk_ceza_odunc
        FOREIGN KEY (odunc_id) REFERENCES ODUNC_ALMA (odunc_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);


INSERT INTO KATEGORI (kategori_adi, aciklama) VALUES
    ('Roman',       'Yerli ve yabancı roman türleri'),
    ('Bilim',       'Fizik, kimya, biyoloji vb. bilim dalları'),
    ('Tarih',       'Dünya ve Türk tarihi kaynakları'),
    ('Felsefe',     'Felsefe ve düşünce kitapları'),
    ('Bilgisayar',  'Yazılım, donanım ve bilişim teknolojileri');


UPDATE KATEGORI SET aciklama = 'Yerli ve çeviri roman eserleri' WHERE kategori_id = 1;


SELECT * FROM KATEGORI;
SELECT kategori_adi FROM KATEGORI WHERE kategori_id = 3;



INSERT INTO YAYINEVI (yayinevi_adi, adres, telefon, email) VALUES
    ('Yapı Kredi Yayınları',        'İstanbul, Beyoğlu',    '0212-252-4700', 'info@ykykultur.com.tr'),
    ('İş Bankası Kültür Yayınları', 'İstanbul, Şişli',      '0212-252-3900', 'info@iskultur.com.tr'),
    ('Can Yayınları',               'İstanbul, Cağaloğlu',  '0212-528-7070', 'info@canyayinlari.com'),
    ('Alfa Yayınları',              'İstanbul, Ataşehir',   '0216-520-1881', 'info@alfayayin.com'),
    ('Kodlab Yayınları',            'İstanbul, Fatih',      '0212-511-1440', 'info@kodlab.com');

UPDATE YAYINEVI SET telefon = '0212-252-4701' WHERE yayinevi_id = 1;


SELECT * FROM YAYINEVI;
SELECT yayinevi_adi, email FROM YAYINEVI WHERE yayinevi_id IN (1, 2, 3);


INSERT INTO YAZAR (yazar_adi, yazar_soyadi, biyografi) VALUES
    ('Orhan',       'Pamuk',        'Nobel ödüllü Türk romancı.'),
    ('Sabahattin',  'Ali',          '20. yüzyıl Türk edebiyatının öncü yazarı.'),
    ('Yuval Noah',  'Harari',       'İsrailli tarihçi ve yazar.'),
    ('Albert',      'Camus',        'Fransız yazar ve filozof, Nobel ödüllü.'),
    ('Robert C.',   'Martin',       'Yazılım mühendisliği alanında ünlü yazar.'),
    ('Fyodor',      'Dostoyevski',  'Rus edebiyatının en büyük romancılarından.'),
    ('Oğuz',        'Atay',         'Türk edebiyatında postmodern akımın öncülerinden.');

UPDATE YAZAR SET biyografi = 'Nobel ödüllü Türk romancı ve İstanbul sevdalısı.' WHERE yazar_id = 1;


SELECT * FROM YAZAR;
SELECT yazar_adi, yazar_soyadi FROM YAZAR WHERE yazar_soyadi LIKE 'A%';


INSERT INTO KITAP (isbn, kitap_adi, kategori_id, yayinevi_id, yayin_yili, sayfa_sayisi, toplam_adet, mevcut_adet) VALUES
    ('9789750826740', 'Masumiyet Müzesi',       1, 1, 2008, 592, 3, 3),
    ('9789750719387', 'Kürk Mantolu Madonna',    1, 2, 1943, 160, 5, 5),
    ('9786053757238', 'Sapiens',                 3, 3, 2011, 464, 4, 4),
    ('9789750726439', 'Yabancı',                 4, 3, 1942, 120, 3, 3),
    ('9786052118467', 'Temiz Kod',               5, 4, 2008, 431, 2, 2),
    ('9789750738609', 'Suç ve Ceza',             1, 2, 1866, 687, 4, 4),
    ('9789750803871', 'Tutunamayanlar',          1, 2, 1972, 724, 3, 3);

UPDATE KITAP SET mevcut_adet = mevcut_adet - 1 WHERE kitap_id = 1;
UPDATE KITAP SET toplam_adet = 6, mevcut_adet = 6 WHERE kitap_id = 2;


SELECT * FROM KITAP;
SELECT k.kitap_adi, kat.kategori_adi
  FROM KITAP k
  JOIN KATEGORI kat ON k.kategori_id = kat.kategori_id;


INSERT INTO KITAP_YAZAR (kitap_id, yazar_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7);

SELECT * FROM KITAP_YAZAR;

SELECT k.kitap_adi, y.yazar_adi || ' ' || y.yazar_soyadi AS yazar
  FROM KITAP_YAZAR ky
  JOIN KITAP k ON ky.kitap_id = k.kitap_id
  JOIN YAZAR y ON ky.yazar_id = y.yazar_id;

SELECT y.yazar_adi, y.yazar_soyadi, COUNT(ky.kitap_id) AS kitap_sayisi
  FROM YAZAR y
  LEFT JOIN KITAP_YAZAR ky ON y.yazar_id = ky.yazar_id
  GROUP BY y.yazar_id, y.yazar_adi, y.yazar_soyadi
  ORDER BY kitap_sayisi DESC;


INSERT INTO OGRENCI (ogrenci_no, ad, soyad, email, telefon, bolum, kayit_tarihi, durum) VALUES
    ('2021001', 'Ahmet',   'Yılmaz',  'ahmet.yilmaz@stu.edu.tr',  '0532-111-2233', 'Bilgisayar Mühendisliği',   '2021-09-15', 'aktif'),
    ('2021002', 'Elif',    'Demir',   'elif.demir@stu.edu.tr',    '0533-222-3344', 'Elektrik-Elektronik Müh.',  '2021-09-15', 'aktif'),
    ('2020003', 'Mehmet',  'Kaya',    'mehmet.kaya@stu.edu.tr',   '0534-333-4455', 'Makine Mühendisliği',       '2020-09-14', 'aktif'),
    ('2019004', 'Zeynep',  'Arslan',  'zeynep.arslan@stu.edu.tr', '0535-444-5566', 'Tarih',                     '2019-09-16', 'mezun'),
    ('2022005', 'Burak',   'Çelik',   'burak.celik@stu.edu.tr',   '0536-555-6677', 'Felsefe',                   '2022-09-12', 'aktif'),
    ('2023006', 'Ayşe',    'Koç',     'ayse.koc@stu.edu.tr',      '0537-666-7788', 'Bilgisayar Mühendisliği',   '2023-09-11', 'aktif');

UPDATE OGRENCI SET durum = 'pasif' WHERE ogrenci_no = '2019004';
UPDATE OGRENCI SET telefon = '0532-111-9999' WHERE ogrenci_id = 1;


SELECT * FROM OGRENCI;
SELECT ad, soyad, bolum FROM OGRENCI WHERE durum = 'aktif' ORDER BY soyad;


INSERT INTO GOREVLI (ad, soyad, email, telefon, gorev, ise_baslama) VALUES
    ('Hasan',  'Öztürk',  'hasan.ozturk@kutuphane.edu.tr',  '0538-100-1001', 'yonetici',          '2015-03-01'),
    ('Fatma',  'Aydın',   'fatma.aydin@kutuphane.edu.tr',    '0538-100-1002', 'kutuphane_memuru',  '2018-06-15'),
    ('Ali',    'Şahin',   'ali.sahin@kutuphane.edu.tr',      '0538-100-1003', 'kutuphane_memuru',  '2020-01-10'),
    ('Selin',  'Doğan',   'selin.dogan@kutuphane.edu.tr',    '0538-100-1004', 'kutuphane_memuru',  '2021-09-01'),
    ('Emre',   'Güneş',   'emre.gunes@kutuphane.edu.tr',     '0538-100-1005', 'kutuphane_memuru',  '2023-02-20');

UPDATE GOREVLI SET gorev = 'yonetici' WHERE gorevli_id = 2;
UPDATE GOREVLI SET email = 'ali.sahin.yeni@kutuphane.edu.tr' WHERE gorevli_id = 3;

SELECT * FROM GOREVLI;
SELECT ad, soyad, gorev FROM GOREVLI WHERE gorev = 'kutuphane_memuru';


INSERT INTO ODUNC_ALMA (kitap_id, ogrenci_id, gorevli_id, odunc_tarihi, son_iade_tarihi, durum) VALUES
    (1, 1, 2, '2026-03-01', '2026-03-15', 'iade_edildi'),
    (2, 2, 2, '2026-03-05', '2026-03-19', 'iade_edildi'),
    (3, 3, 3, '2026-03-10', '2026-03-24', 'devam'),
    (4, 5, 3, '2026-04-01', '2026-04-15', 'devam'),
    (5, 1, 4, '2026-04-05', '2026-04-19', 'devam'),
    (6, 4, 2, '2026-02-01', '2026-02-15', 'gecikti'),
    (7, 6, 4, '2026-04-10', '2026-04-24', 'devam');

UPDATE ODUNC_ALMA SET durum = 'iade_edildi' WHERE odunc_id = 3;


SELECT * FROM ODUNC_ALMA;

-- Devam eden ödünç işlemleri
SELECT o.odunc_id, k.kitap_adi,
       og.ad || ' ' || og.soyad AS ogrenci,
       o.odunc_tarihi, o.son_iade_tarihi, o.durum
  FROM ODUNC_ALMA o
  JOIN KITAP k    ON o.kitap_id    = k.kitap_id
  JOIN OGRENCI og ON o.ogrenci_id  = og.ogrenci_id
  WHERE o.durum = 'devam';

-- Öğrenci başına ödünç sayısı
SELECT og.ad, og.soyad, COUNT(o.odunc_id) AS toplam_odunc
  FROM OGRENCI og
  JOIN ODUNC_ALMA o ON og.ogrenci_id = o.ogrenci_id
  GROUP BY og.ogrenci_id, og.ad, og.soyad
  ORDER BY toplam_odunc DESC;


INSERT INTO IADE (odunc_id, gorevli_id, iade_tarihi, kitap_durumu, notlar) VALUES
    (1, 2, '2026-03-14', 'iyi',     NULL),
    (2, 2, '2026-03-20', 'iyi',     'Bir gün gecikmeli iade edildi.'),
    (6, 3, '2026-03-01', 'hasarli', 'Kapak kısmında yırtık mevcut.'),
    (3, 3, '2026-03-22', 'iyi',     'Zamanında iade edildi.'),
    (4, 4, '2026-04-14', 'iyi',     NULL);

UPDATE IADE SET notlar = 'Temiz ve düzgün iade edildi.' WHERE iade_id = 1;
UPDATE IADE SET kitap_durumu = 'iyi' WHERE iade_id = 3;

SELECT * FROM IADE;

-- İade detay raporu
SELECT i.iade_id, k.kitap_adi, i.iade_tarihi, i.kitap_durumu,
       g.ad || ' ' || g.soyad AS islem_yapan_gorevli
  FROM IADE i
  JOIN ODUNC_ALMA o ON i.odunc_id  = o.odunc_id
  JOIN KITAP k      ON o.kitap_id  = k.kitap_id
  JOIN GOREVLI g    ON i.gorevli_id = g.gorevli_id;


INSERT INTO CEZA (odunc_id, ceza_turu_alan, tutar, odendi_mi, olusturma_tarihi, odeme_tarihi) VALUES
    (6, 'gecikme',  25.00,  TRUE,   '2026-03-01', '2026-03-05'),
    (6, 'hasar',    50.00,  FALSE,  '2026-03-01', NULL),
    (2, 'gecikme',  5.00,   TRUE,   '2026-03-20', '2026-03-20'),
    (3, 'gecikme',  10.00,  FALSE,  '2026-03-25', NULL),
    (1, 'gecikme',  0.00,   TRUE,   '2026-03-14', '2026-03-14');

UPDATE CEZA SET odendi_mi = TRUE, odeme_tarihi = '2026-04-01' WHERE ceza_id = 2;
UPDATE CEZA SET tutar = 15.00 WHERE ceza_id = 4;

SELECT * FROM CEZA;

-- Ödenmemiş cezalar
SELECT c.ceza_id, k.kitap_adi,
       og.ad || ' ' || og.soyad AS ogrenci,
       c.ceza_turu_alan, c.tutar, c.odendi_mi
  FROM CEZA c
  JOIN ODUNC_ALMA o ON c.odunc_id  = o.odunc_id
  JOIN KITAP k      ON o.kitap_id  = k.kitap_id
  JOIN OGRENCI og   ON o.ogrenci_id = og.ogrenci_id
  WHERE c.odendi_mi = FALSE;

-- Genel ceza özeti
SELECT SUM(tutar)                                          AS toplam_ceza,
       SUM(CASE WHEN odendi_mi THEN tutar ELSE 0 END)     AS odenen,
       SUM(CASE WHEN NOT odendi_mi THEN tutar ELSE 0 END) AS odenmemis
  FROM CEZA;
