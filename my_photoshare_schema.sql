DROP TABLE Pictures CASCADE;
DROP TABLE Users CASCADE;
DROP TABLE Albums CASCADE;
DROP TABLE Comments CASCADE;
DROP TABLE Tags CASCADE;
DROP TABLE Friends CASCADE;
DROP TABLE Likes;
DROP SEQUENCE Pictures_picture_id_seq;
DROP SEQUENCE Pictures_comment_id_seq;
DROP SEQUENCE Pictures_tag_id_seq;
DROP SEQUENCE Albums_album_id_seq;
DROP SEQUENCE Users_user_id_seq;


CREATE SEQUENCE Users_user_id_seq
  INCREMENT 1
  MINVALUE 1

  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Users
(
  user_id int4 NOT NULL DEFAULT nextval('Users_user_id_seq'),
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role_name varchar(255) NOT NULL DEFAULT 'RegisteredUser',
  hometown varchar(75),
  first_name varchar(75) NOT NULL,
  last_name varchar(75) NOT NULL,
  dob varchar(8) NOT NULL,
  gender char(1),
  CONSTRAINT users_pk PRIMARY KEY (user_id)
);

INSERT INTO Users (email, password, hometown, first_name, last_name, dob, gender) VALUES ('test@bu.edu', 'test', 'boston', 'test', 'test', '11062015', 'm');
INSERT INTO Users (email, password, role_name, hometown, first_name, last_name, dob, gender) VALUES ('whoknows@bu.edu', 'anon', 'UnregisteredUser', 'nowhere', 'anon', 'ymous', '11062015', 'm');


CREATE TABLE Friends
(
  f_user_id int4 NOT NULL,
  owner_id int4 NOT NULL,
  CONSTRAINT fid_pk PRIMARY KEY (owner_id,f_user_id),
  CONSTRAINT f_id_fk FOREIGN KEY (f_user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
  CONSTRAINT f_oid_fk FOREIGN KEY (owner_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE SEQUENCE Albums_album_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;
  
CREATE TABLE Albums
(  
  album_id int4 NOT NULL DEFAULT nextval('Albums_album_id_seq'),
  name varchar(75),
  date_created varchar(11) NOT NULL,
  owner_id int4 NOT NULL,
  CONSTRAINT album_id_pk PRIMARY KEY (album_id),
  CONSTRAINT owner_id_fk FOREIGN KEY (owner_id) REFERENCES Users(user_id)
);

CREATE SEQUENCE Pictures_picture_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Pictures
(
  picture_id int4 NOT NULL DEFAULT nextval('Pictures_picture_id_seq'),
  album_id int4 NOT NULL,
  caption varchar(255) NOT NULL,
  imgdata bytea NOT NULL,
  size int4 NOT NULL,
  content_type varchar(255) NOT NULL,
  thumbdata bytea NOT NULL,
  owner_id int4 NOT NULL,
  CONSTRAINT pictures_pk PRIMARY KEY (picture_id),
  CONSTRAINT pictures_user_fk FOREIGN KEY (owner_id) REFERENCES Users(user_id),
  CONSTRAINT album_id_fk FOREIGN KEY (album_id) REFERENCES Albums(album_id) ON DELETE CASCADE
);

CREATE SEQUENCE Pictures_tag_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;
  
CREATE TABLE Tags
(
  tag_id int4 NOT NULL DEFAULT nextval('Pictures_tag_id_seq'),
  tag_word varchar(75) NOT NULL,
  pic_id int4 NOT NULL,
  CONSTRAINT tag_pk PRIMARY KEY (tag_id),
  CONSTRAINT pic_id_fk FOREIGN KEY (pic_id) REFERENCES Pictures(pic_id)
);

CREATE SEQUENCE Pictures_comment_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;
  
CREATE TABLE Comments
(
  comment_id int4 NOT NULL DEFAULT nextval('Pictures_comment_id_seq'), 
  text varchar(150) NOT NULL,
  comment_date varchar(11) NOT NULL,
  pic_id int4 NOT NULL,
  owner_id int4 NOT NULL,
  
  CONSTRAINT comment_id_pk PRIMARY KEY (comment_id),
  CONSTRAINT owner_id_fk2 FOREIGN KEY (owner_id) REFERENCES Users(user_id)
);

CREATE TABLE Likes (
   pic_id int4 NOT NULL,
   wholiked_id int4 NOT NULL,
   CONSTRAINT liked_id_fk FOREIGN KEY (wholiked_id) REFERENCES Users(user_id)
); 
 





