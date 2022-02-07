CREATE TABLE `client` (
  `idClient` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `age` int(11) DEFAULT NULL
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `orders` (
  `idOrder` int(11) NOT NULL,
  `idClient` int(11) NOT NULL,
  `nameClient` varchar(45) NOT NULL,
  `idProduct` int(11) NOT NULL,
  `nameProduct` varchar(45) NOT NULL,
  `quantity` int(11) NOT NULL,
  `priceAll` int(11) NOT NULL,
  PRIMARY KEY (`idOrder`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `product` (
  `idProduct` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`idProduct`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8