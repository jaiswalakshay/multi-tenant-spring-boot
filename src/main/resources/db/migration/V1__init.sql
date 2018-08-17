CREATE TABLE users (
  id varchar(100) NOT NULL,
  username varchar(100) NOT NULL,
  firstname varchar(50) NOT NULL,
  lastname varchar(50) DEFAULT NULL,
  contactNo varchar(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
