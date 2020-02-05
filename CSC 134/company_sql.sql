/*
 CREATE VIEW Section
 */
CREATE VIEW BUSINESS_VIEW AS
SELECT 		B.id,
			name,
			address,
			city,
			state,
			zip,
			latitude,
			longitude,
			COUNT(R.id) AS review_count,
			AVG(stars) AS average_stars
FROM 		BUSINESS AS B LEFT OUTER JOIN REVIEW AS R
ON 			B.id = bus_id
GROUP BY 	B.id;


CREATE VIEW USER_VIEW AS
SELECT 		U.id,
			name,
			date_joined,
			COUNT(R.id) AS review_count,
			AVG(stars) AS average_stars
FROM 		USER AS U LEFT OUTER JOIN REVIEW AS R
ON 			U.id = user_id
GROUP BY 	U.id;

/*
 INSERT Section
 */
INSERT INTO BUSINESS (id, name, address, city, state, zip, latitude, longitude)
VALUES 	(1, 'Tiny Tacos', '123 Roosevelt Street', 'San Jose', 'CA', '95762-1234', 123.12345, 123.12345),
		(2, 'Average Cabbage', '789 Bush Avenue', 'Sacramento', 'CA', '95762-1234', 123.23456, 123.23456);


INSERT INTO PHONE (bus_id, phone)
VALUES 	(1, '19162206789');


INSERT INTO BUSINESS_CATEGORY (bus_id, category)
VALUES 	(2, 'restaurant');


INSERT INTO USER (ID, name, date_joined)
VALUES 	(1, 'Justin V', '1997/09/22'),
		(2, 'Brianna A', '1997/05/02'),
		(3, 'Jenny S', '2019/02/15');


INSERT INTO FRIEND (user_id1, user_id2, date_friended)
VALUES 	(1, 2, '1990/05/24'),
		(2, 1, '1990/05/24');


INSERT INTO REVIEW (ID, bus_id, user_id, stars, rev_text)
VALUES	(1, 1, 1, 5, 'It was great. Scrumptious flavor.'),
		(2, 1, 2, 4, 'It was pretty good. Wouldve been 5 stars if the service was better.'),
		(3, 2, 3, 5, 'Worth the wait. Id come here again.');


INSERT INTO CHECK_IN (user_id, bus_id, cdate)
VALUES	(1, 1, '2019/10/21'),
		(2, 1, '2019/10/20'),
		(3, 2, '2019/02/14');

/*
 Data Testing Section
 */
SELECT 		name, id
FROM 		BUSINESS JOIN BUSINESS_CATEGORY AS BC ON id = BC.bus_id 
JOIN 		CHECK_IN AS C ON id = C.bus_id
WHERE 		category = 'restaurant' AND cdate = '2019-02-14';


SELECT 		id, name, address, city, state, zip
FROM 		BUSINESS LEFT OUTER JOIN PHONE ON id = bus_id
WHERE 		phone IS NULL;

/*
 I used (id = user_id1 OR id = user_id2) assuming each user did not have their own tuple.
 For example, if each user DID have their own tuple, two users that were friends would create
 two tuples, each of their ID's in user_id1 and the other in user_id2. If this is actually the
 case, (id = user_id1 OR id = user_id2) can be replaced with a shorter (id = user_id1)
 */
(SELECT 	name, date_joined
FROM 		USER JOIN FRIEND ON (id = user_id1 OR id = user_id2)
WHERE 		date_friended < date_joined)
UNION
(SELECT 	name, date_joined
FROM 		USER JOIN CHECK_IN ON id = user_id
WHERE 		cdate < date_joined);


SELECT 		zip, SUM(review_count)
FROM 		BUSINESS_VIEW
GROUP BY 	zip
ORDER BY 	zip;

/*
 DROP VIEW/TABLE Section
 */
DROP VIEW BUSINESS_VIEW;

DROP VIEW USER_VIEW;

DROP TABLE CHECK_IN;

DROP TABLE FRIEND;

DROP TABLE REVIEW;

DROP TABLE USER;

DROP TABLE BUSINESS_CATEGORY;

DROP TABLE PHONE;

DROP TABLE BUSINESS;
