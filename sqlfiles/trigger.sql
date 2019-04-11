use `PowiescDoSukcesuDB`;
drop trigger if exists calculate_files_rating_after_insert;
DELIMITER $$
create trigger calculate_files_rating_after_insert
	AFTER INSERT ON `books_votes`
	FOR EACH ROW
BEGIN
	UPDATE `books`
    SET `books`.`rating` = (select sum(`books_votes`.`rating`)/count(`books_votes`.`rating`) from `books_votes` where `book_id`=`books`.`id`);

END $$
DELIMITER ;
drop trigger if exists calculate_files_rating_after_update;
DELIMITER $$
create trigger calculate_files_rating_after_update
	AFTER update ON `books_votes`
	FOR EACH ROW
BEGIN
	UPDATE `books`
    SET `books`.`rating` = (select sum(`books_votes`.`rating`)/count(`books_votes`.`rating`) from `books_votes` where `book_id`=`books`.`id`);

END $$
DELIMITER ;