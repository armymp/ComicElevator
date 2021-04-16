BEGIN TRANSACTION;
DROP TABLE IF EXISTS collections_user;
DROP TABLE IF EXISTS collections_comics;
DROP TABLE IF EXISTS characters_comics;
DROP TABLE IF EXISTS creator_comics;
DROP TABLE IF EXISTS series_comics;
DROP TABLE IF EXISTS publisher_comics;
DROP TABLE IF EXISTS characters_series;
DROP TABLE IF EXISTS friend_user;
DROP TABLE IF EXISTS comics;
DROP TABLE IF EXISTS collections;
DROP TABLE IF EXISTS characters;
DROP TABLE IF EXISTS creator;
DROP TABLE IF EXISTS series;
DROP TABLE IF EXISTS publisher;
DROP TABLE IF EXISTS friend;

DROP SEQUENCE IF EXISTS seq_comic_id;
DROP SEQUENCE IF EXISTS seq_collection_id;
DROP SEQUENCE IF EXISTS seq_character_id;
DROP SEQUENCE IF EXISTS seq_creator_id;
DROP SEQUENCE IF EXISTS seq_series_id;
DROP SEQUENCE IF EXISTS seq_publisher_id;
DROP SEQUENCE IF EXISTS seq_friend_id;
DROP SEQUENCE IF EXISTS seq_id;

CREATE SEQUENCE seq_comic_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE SEQUENCE seq_collection_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE SEQUENCE seq_character_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE SEQUENCE seq_creator_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE SEQUENCE seq_series_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE SEQUENCE seq_publisher_id
  INCREMENT BY 1
  START WITH 4001
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE SEQUENCE seq_friend_id
  INCREMENT BY 1
  START WITH 4001
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE SEQUENCE seq_id
  INCREMENT BY 1
  START WITH 4001
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
CREATE TABLE comics (
	comic_id int DEFAULT nextval('seq_comic_id'::regclass) NOT NULL,
	title varchar(200) NOT NULL,
	issue_num int,
	img varchar(200),
	marvel_id int,
	
	CONSTRAINT PK_comics PRIMARY KEY (comic_id)
);
CREATE TABLE collections (
	collection_id int DEFAULT nextval('seq_collection_id'::regclass) NOT NULL,
	user_id int NOT NULL,
	name varchar(200) NOT NULL,
	private boolean DEFAULT false,
	
	CONSTRAINT PK_collections PRIMARY KEY (collection_id)
);
CREATE TABLE characters (
	character_id int DEFAULT nextval('seq_character_id'::regclass) NOT NULL,
	name varchar(200) NOT NULL,
	
	CONSTRAINT PK_characters PRIMARY KEY (character_id)
	--CONSTRAINT UQ_username UNIQUE (username)
);
CREATE TABLE creator (
	creator_id int DEFAULT nextval('seq_creator_id'::regclass) NOT NULL,
	full_name varchar(200)NOT NULL,
	
        CONSTRAINT PK_creator PRIMARY KEY (creator_id)
	--CONSTRAINT FK_accounts_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);
CREATE TABLE series (
	series_id int DEFAULT nextval('seq_series_id'::regclass) NOT NULL,
	title varchar(200) NOT NULL,
	--rating varchar(50) NOT NULL,
	
	CONSTRAINT PK_series PRIMARY KEY (series_id)
	
);
	
CREATE TABLE publisher (
	publisher_id int DEFAULT nextval('seq_publisher_id'::regclass) NOT NULL,
	name varchar(200) NOT NULL,
	
	CONSTRAINT PK_publisher PRIMARY KEY (publisher_id)
);
CREATE TABLE friend (
        id int DEFAULT nextval('seq_id':: regclass) NOT NULL,
        friend_id int NOT NULL,
        user_id int NOT NULL,
        status_id int NOT NULL,
        status_id_desc varchar(50),
        CONSTRAINT PK_friend PRIMARY KEY (id),
        CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
        CONSTRAINT friend_id FOREIGN KEY (friend_id) REFERENCES users(user_id)
);
CREATE TABLE collections_comics (
        collection_id int NOT NULL,
        comic_id int NOT NULL,
        CONSTRAINT PK_collections_comics PRIMARY KEY (collection_id, comic_id),
        CONSTRAINT FK_collections_comics_collections FOREIGN KEY (collection_id) REFERENCES collections(collection_id),
        CONSTRAINT FK_collections_comics_comics FOREIGN KEY (comic_id) REFERENCES comics(comic_id)
);
CREATE TABLE characters_comics (
        character_id int NOT NULL,
        comic_id int NOT NULL,
        CONSTRAINT PK_characters_comics PRIMARY KEY (character_id, comic_id),
        CONSTRAINT FK_charactars_comics_characters FOREIGN KEY (character_id) REFERENCES characters(character_id),
        CONSTRAINT FK_characters_comics_comics FOREIGN KEY (comic_id) REFERENCES comics(comic_id)
);
CREATE TABLE series_comics (
        series_id int NOT NULL,
        comic_id int NOT NULL,
        CONSTRAINT PK_series_comics PRIMARY KEY (series_id, comic_id),
        CONSTRAINT FK_series_comics_series FOREIGN KEY (series_id) REFERENCES series(series_id),
        CONSTRAINT FK_series_comics_comics FOREIGN KEY (comic_id) REFERENCES comics(comic_id)
);
CREATE TABLE creator_comics (
        creator_id int NOT NULL,
        comic_id int NOT NULL,
        CONSTRAINT PK_creator_comics PRIMARY KEY (creator_id, comic_id),
        CONSTRAINT FK_creator_comics_creator FOREIGN KEY (creator_id) REFERENCES creator(creator_id),
        CONSTRAINT FK_creator_comics_comics FOREIGN KEY (comic_id) REFERENCES comics(comic_id)
);
CREATE TABLE collections_user (
        collection_id int NOT NULL,
        user_id int NOT NULL,
        CONSTRAINT PK_collections_user PRIMARY KEY (collection_id, user_id),
        CONSTRAINT FK_collections_user_collections FOREIGN KEY (collection_id) REFERENCES collections(collection_id),
        CONSTRAINT FK_collections_user_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE publisher_comics (
        publisher_id int NOT NULL,
        comic_id int NOT NULL,
        CONSTRAINT PK_publisher_comic PRIMARY KEY (publisher_id, comic_id),
        CONSTRAINT FK_publisher_comic_publisher FOREIGN KEY (publisher_id) REFERENCES publisher(publisher_id),
        CONSTRAINT FK_publisher_comic_comic FOREIGN KEY (comic_id) REFERENCES comics(comic_id)
);
/*CREATE TABLE friend_user (
        friend_id int NOT NULL,
        user_id int NOT NULL,
        CONSTRAINT PK_friend_user PRIMARY KEY (friend_id, user_id),
        CONSTRAINT FK_friend_user_friend FOREIGN KEY (friend_id) REFERENCES user(user_id),
        CONSTRAINT FK_friend_user_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);*/
--INSERT INTO friend (status_id_desc) VALUES ('Pending');
--INSERT INTO friend (status_id_desc) VALUES ('Approved');
--INSERT INTO friend (status_id_desc) VALUES ('Rejected');
--ROLLBACK;
COMMIT TRANSACTION;