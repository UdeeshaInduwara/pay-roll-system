DROP TABLE IF EXISTS designation;
CREATE TABLE designation(
  did VARCHAR (20) PRIMARY KEY ,
  levels VARCHAR (100),
  basicSalary DECIMAL (10,2),
  medicleAllowence DECIMAL (10,2)
);
DROP TABLE IF EXISTS allowance;
CREATE TABLE allowance(
  alid VARCHAR (20) PRIMARY KEY ,
  types VARCHAR (100),
  percentage DECIMAL (10,2)
);
DROP TABLE IF EXISTS employee;
CREATE TABLE employee(
  eid VARCHAR (20) PRIMARY KEY ,
  empName VARCHAR (100),
  nic VARCHAR (20)
);
DROP TABLE IF EXISTS designationDetail;
CREATE TABLE designationDetail(
  dateSince DATE,
  did VARCHAR (20),
  eid VARCHAR (20),
  CONSTRAINT PRIMARY KEY (did,eid),
  CONSTRAINT FOREIGN KEY (did) REFERENCES designation(did)
  ON DELETE CASCADE ON UPDATE CASCADE ,
  CONSTRAINT FOREIGN KEY (eid) REFERENCES employee(eid)
  ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS rates;
CREATE TABLE rates(
  types VARCHAR (50) PRIMARY KEY ,
  percentage DECIMAL (10,2)
);
DROP TABLE IF EXISTS attendenceDetail;
CREATE TABLE attendenceDetail(
  adid INT AUTO_INCREMENT ,
  eid VARCHAR (20),
  monthNo INT,
  yearNo INT,
  currenMonthLeaves INT,
  CONSTRAINT PRIMARY KEY (adid),
  CONSTRAINT FOREIGN KEY (eid) REFERENCES employee(eid)
  ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS montlyWorkDays;
CREATE TABLE montlyWorkDays(
  monthNo INT,
  yearNo INT,
  noOfWorkDays INT
);
DROP TABLE IF EXISTS yearlyLeaves;
CREATE TABLE yearlyLeaves(
  yearNo INT PRIMARY KEY ,
  noOfLeaves INT
);
DROP TABLE IF EXISTS attendence;
CREATE TABLE attendence(
  atid INT AUTO_INCREMENT,
  eid VARCHAR (20),
  status VARCHAR (10),
  intime VARCHAR (100),
  outtime VARCHAR (100),
  currentDate DATE,
  ot DECIMAL (10,2),
  nopay DECIMAL (10,2),
  CONSTRAINT PRIMARY KEY (atid),
  CONSTRAINT FOREIGN KEY (eid) REFERENCES employee(eid)
  ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS salaryAdvance;
CREATE TABLE salaryAdvance(
  said INT AUTO_INCREMENT,
  eid VARCHAR (20),
  currentDate DATE,
  advance DECIMAL (10,2),
  CONSTRAINT PRIMARY KEY (said),
  CONSTRAINT FOREIGN KEY (eid) REFERENCES employee(eid)
  ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS netSalary;
CREATE TABLE netSalary(
  sid INT AUTO_INCREMENT,
  eid VARCHAR (20),
  monthName VARCHAR (20),
  ot DECIMAL (10,2),
  noPay DECIMAL (10,2),
  etf DECIMAL (10,2),
  epf DECIMAL (10,2),
  food DECIMAL (10,2),
  medicle DECIMAL (10,2),
  attendence DECIMAL (10,2),
  transport DECIMAL (10,2),
  salaryAdvance DECIMAL (10,2),
  total DECIMAL (10,2),
  CONSTRAINT PRIMARY KEY (sid),
  CONSTRAINT FOREIGN KEY (eid) REFERENCES employee(eid)
  ON DELETE CASCADE ON UPDATE CASCADE
);
-- creating procedures
-- add rates
DROP PROCEDURE IF EXISTS addRates;
DELIMITER $$;
CREATE PROCEDURE addRates(IN types VARCHAR (50),IN percentage DECIMAL (10,2))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  INSERT INTO rates VALUES (types,percentage);
END$$;
DELIMITER ;
call addRates('epf',22);
call addRates('etf',8);
call addRates('ot per hour',500);

-- add designation
DROP PROCEDURE IF EXISTS addDesignation;
DELIMITER $$;
CREATE PROCEDURE addDesignation(
IN did VARCHAR (20),IN levels VARCHAR (100),IN bSalary DECIMAL (10,2),IN mdAllowence DECIMAL (10,2))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  INSERT INTO designation VALUES (did,levels,bSalary,mdAllowence);
END$$;
DELIMITER ;
call addDesignation('d001','level 1',75000,25000);
call addDesignation('d002','level 2',50000,10000);
call addDesignation('d003','level 3',25000,5000);

-- add Employee
DROP PROCEDURE IF EXISTS addEmployee;
DELIMITER $$;
CREATE PROCEDURE addEmployee(IN eid VARCHAR (20),IN empName VARCHAR (100),IN nic VARCHAR (20),IN desId VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
	BEGIN
		SELECT 'Duplicate key error encountered';
		ROLLBACK;
	END;
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		SELECT 'SQLException encountered';
		ROLLBACK;
	END;

  START TRANSACTION;
    INSERT INTO employee VALUES (eid,empName,nic);
    INSERT INTO designationDetail VALUES (CURDATE(),desId,eid);
  COMMIT;
END$$;
DELIMITER ;
call addEmployee('e001','udeesha induwara','982852234v','d001');

-- add Allowence
DROP PROCEDURE IF EXISTS addAllowence;
DELIMITER $$;
CREATE PROCEDURE addAllowence(IN alid VARCHAR (20),IN types VARCHAR (100),IN percentage DECIMAL (10,2))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  INSERT INTO allowance VALUES (alid,types,percentage);
END$$;
DELIMITER ;
call addAllowence('al001','food',5);
call addAllowence('al002','transport',8);
call addAllowence('al003','attendence',2);

-- set monthly work days
DROP PROCEDURE IF EXISTS setMonthlyWorkDays;
DELIMITER $$;
CREATE PROCEDURE setMonthlyWorkDays(IN monthNo INT,IN noOfWDays INT)
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  INSERT INTO montlyworkdays VALUES (monthNo,YEAR(curdate()),noOfWDays);
END$$;
DELIMITER ;
call montlyworkdays(9,22);

-- set yearly leaves
DROP PROCEDURE IF EXISTS setYearlyLeaves;
DELIMITER $$;
CREATE PROCEDURE setYearlyLeaves(IN yearNo INT,IN noOfLeaves INT)
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  INSERT INTO yearlyleaves VALUES (yearNo,noOfLeaves);
END$$;
DELIMITER ;
call setYearlyLeaves(2018,10);

-- get noPay
DROP FUNCTION IF EXISTS noPayAmount;
DELIMITER $$
CREATE FUNCTION noPayAmount(empId VARCHAR (20)) RETURNS DECIMAL
  BEGIN
    DECLARE workDays INT ;
    DECLARE bSalary DECIMAL (10,2);
    DECLARE noPayAmount DECIMAL (10,2);
    SELECT noOfWorkDays INTO workDays FROM montlyWorkDays WHERE monthNo=MONTH(CURDATE());
    SELECT basicSalary INTO bSalary FROM employee e,designationDetail dd,designation d
    WHERE e.eid=dd.eid AND d.did=dd.did AND e.eid=empId;

    SET noPayAmount=bSalary/workDays;
    RETURN noPayAmount;
  END$$
DELIMITER ;

-- set attendence detail
DROP PROCEDURE IF EXISTS setAttendenceDetail;
DELIMITER $$;
CREATE PROCEDURE setAttendenceDetail(IN empId VARCHAR (20))
BEGIN
  DECLARE thisMonthLeaves INT ;

  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT SUM(currenMonthLeaves) INTO thisMonthLeaves FROM attendenceDetail WHERE eid=empId AND monthNo=MONTH (CURDATE()) AND yearNo=YEAR (CURDATE());
  IF(thisMonthLeaves is null)THEN
    SET thisMonthLeaves=1;
    INSERT INTO attendenceDetail(eid,monthNo,yearNo,currenMonthLeaves) VALUES (empId,MONTH (CURDATE()),YEAR (CURDATE()),thisMonthLeaves);
  ELSE
    UPDATE attendenceDetail SET currenMonthLeaves=(thisMonthLeaves+1) WHERE eid=empId AND monthNo=MONTH (CURDATE()) AND yearNo=YEAR (CURDATE());
  END IF;
END$$;
DELIMITER ;

-- mark Attendence intime
DROP PROCEDURE IF EXISTS markIntime;
DELIMITER $$;
CREATE PROCEDURE markIntime(IN empId VARCHAR (20),IN status BOOLEAN)
BEGIN
  DECLARE totalLeave INT ;
  DECLARE allMonthsLeaveCount INT ;
  DECLARE lastDate DATE ;

  DECLARE EXIT HANDLER FOR 1062
	BEGIN
		SELECT 'Duplicate key error encountered';
		ROLLBACK;
	END;
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		SELECT 'SQLException encountered';
		ROLLBACK;
	END;

  START TRANSACTION;
    SELECT currentDate INTO lastDate FROM attendence WHERE eid=empId ORDER BY currentDate DESC LIMIT 1;
    IF(lastDate is null) THEN
      set lastDate=0;
    END IF;

    IF(DATE(curdate())!=DATE(lastDate))THEN
      SELECT noOfLeaves INTO totalLeave FROM yearlyLeaves WHERE yearNo=YEAR (CURDATE());
      SELECT SUM(currenMonthLeaves) INTO allMonthsLeaveCount FROM attendenceDetail WHERE eid=empId AND yearNo=YEAR (CURDATE());

      IF(allMonthsLeaveCount is null)THEN
        SET allMonthsLeaveCount=0;
      END IF;
      IF(status) THEN
        INSERT INTO attendence(eid,status,intime,outtime,currentDate,ot,nopay) VALUES(
        empId,'present',current_time,0,current_date,0,0 );
      ELSE
        IF(totalLeave > allMonthsLeaveCount) THEN
          INSERT INTO attendence(eid,status,intime,outtime,currentDate,ot,nopay) VALUES(
          empId,'absent',0,0,current_date,0,0 );
          call setAttendenceDetail(empId);
        ELSE
          INSERT INTO attendence(eid,status,intime,outtime,currentDate,ot,nopay) VALUES(
          empId,'no_pay',0,0,current_date,0,noPayAmount(empId) );
        END IF;
      END IF;
    END IF;
  COMMIT;
END$$;
DELIMITER ;
call markIntime('e001',1);

-- mark out time attendence
DROP PROCEDURE IF EXISTS markOutTime;
DELIMITER $$;
CREATE PROCEDURE markOutTime(IN empId VARCHAR (20))
BEGIN
  DECLARE overTime INT ;
  DECLARE otPerHour DECIMAL (10,2);
  DECLARE inTime VARCHAR (100);

  DECLARE EXIT HANDLER FOR 1062
	BEGIN
		SELECT 'Duplicate key error encountered';
		ROLLBACK;
	END;
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		SELECT 'SQLException encountered';
		ROLLBACK;
	END;

  START TRANSACTION;
    SELECT intime INTO inTime FROM attendence WHERE eid=empId AND currentDate=CURDATE();
    IF(inTime not like 0)THEN
      UPDATE attendence SET outtime=current_time WHERE eid=empId AND currentDate=CURDATE();

      SET overTime=timediff(current_time,'17:00:00');
      IF(overTime>0) THEN
        SELECT percentage INTO otPerHour FROM rates WHERE types='ot per hour';
        UPDATE attendence SET ot=(HOUR(overTime)*otPerHour) WHERE eid=empId AND currentDate=CURDATE();
      END IF;
    END IF;
  COMMIT;
END$$;
DELIMITER ;
call markOutTime('e001');

-- setSalaryAdvance
DROP PROCEDURE IF EXISTS setSalaryAdvance;
DELIMITER $$;
CREATE PROCEDURE setSalaryAdvance(IN empId VARCHAR (20),IN aPrice DECIMAL (10,2))
  BEGIN
    DECLARE saTotal DECIMAL (10,2) ;
    DECLARE bSalary DECIMAL (10,2);

    DECLARE EXIT HANDLER FOR 1062
    BEGIN
      SELECT 'Duplicate key error encountered';
      ROLLBACK;
    END;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
      SELECT 'SQLException encountered';
      ROLLBACK;
    END;

  START TRANSACTION;
    SELECT basicSalary INTO bSalary FROM employee e,designationDetail dd,designation d
    WHERE e.eid=dd.eid AND d.did=dd.did AND e.eid=empId;
    SELECT SUM(advance) INTO saTotal FROM salaryAdvance WHERE eid=empId AND MONTH(currentDate)=MONTH(CURDATE());

    IF(saTotal is null) THEN
      SET saTotal=0;
    END IF;
    IF(bSalary>(saTotal+aPrice)) THEN
      INSERT INTO salaryAdvance(eid,currentDate,advance) values(empId,curdate(),aPrice);
    END IF;
  COMMIT;
  END$$;
DELIMITER ;
call setSalaryAdvance('e001',5000);

-- get food allowence
DROP FUNCTION IF EXISTS getFoodAllowence;
DELIMITER $$
CREATE FUNCTION getFoodAllowence(bSalary DECIMAL (10,2)) RETURNS DECIMAL
  BEGIN
    DECLARE foodRate DECIMAL (10,2);
    DECLARE foodAllow DECIMAL (10,2);

    SELECT percentage INTO foodRate FROM allowance WHERE types='food';
    SET foodAllow=bSalary*(foodRate/100);

    RETURN foodAllow;
  END$$
DELIMITER ;

-- get transport allowence
DROP FUNCTION IF EXISTS getTransportAllowence;
DELIMITER $$
CREATE FUNCTION getTransportAllowence(bSalary DECIMAL (10,2)) RETURNS DECIMAL
  BEGIN
    DECLARE transportRate DECIMAL (10,2);
    DECLARE transportAllow DECIMAL (10,2);

    SELECT percentage INTO transportRate FROM allowance WHERE types='transport';
    SET transportAllow=bSalary*(transportRate/100);

    RETURN transportAllow;
  END$$
DELIMITER ;

--get attendence allowence
DROP FUNCTION IF EXISTS getAttendenceAllowence;
DELIMITER $$
CREATE FUNCTION getAttendenceAllowence(bSalary DECIMAL (10,2)) RETURNS DECIMAL
  BEGIN
    DECLARE attendenceRate DECIMAL (10,2);
    DECLARE attendenceAllow DECIMAL (10,2);

    SELECT percentage INTO attendenceRate FROM allowance WHERE types='attendence';
    SET attendenceAllow=bSalary*(attendenceRate/100);

    RETURN attendenceAllow;
  END$$
DELIMITER ;

-- to food transport attendence allowence total
DROP FUNCTION IF EXISTS getFullAllowence;
DELIMITER $$
CREATE FUNCTION getFullAllowence(bSalary DECIMAL (10,2)) RETURNS DECIMAL
  BEGIN
    DECLARE total DECIMAL (10,2);
    SET total=getAttendenceAllowence(bSalary)+getTransportAllowence(bSalary)+getFoodAllowence(bSalary);
    RETURN total;
  END$$
DELIMITER ;

-- calculate ETF
DROP FUNCTION IF EXISTS getETF;
DELIMITER $$
CREATE FUNCTION getETF(bSalary DECIMAL (10,2)) RETURNS DECIMAL
  BEGIN
    DECLARE etfRate DECIMAL (10,2);
    DECLARE etfTotal DECIMAL (10,2);

    SELECT percentage INTO etfRate FROM rates WHERE types='etf';
    SET etfTotal=bSalary*(etfRate/100);

    RETURN etfTotal;
  END$$
DELIMITER ;

-- calculate epf
DROP FUNCTION IF EXISTS getEPF;
DELIMITER $$
CREATE FUNCTION getEPF(bSalary DECIMAL (10,2)) RETURNS DECIMAL
  BEGIN
    DECLARE epfRate DECIMAL (10,2);
    DECLARE epfTotal DECIMAL (10,2);
    SELECT percentage INTO epfRate FROM rates WHERE types='epf';

    SET epfTotal=bSalary*(epfRate/100);

    RETURN epfTotal;
  END$$
DELIMITER ;

-- to get salary advance count
DROP FUNCTION IF EXISTS getSalaryAdvance;
DELIMITER $$
CREATE FUNCTION getSalaryAdvance(empId VARCHAR (20)) RETURNS DECIMAL
  BEGIN
    DECLARE totalSalAdvance DECIMAL (10,2);
    SELECT SUM(advance) INTO totalSalAdvance FROM salaryAdvance WHERE eid=empId AND MONTH(currentDate)=MONTH(curdate());
    IF(totalSalAdvance is null)THEN
      SET totalSalAdvance=0;
    END IF;
    RETURN totalSalAdvance;
  END$$
DELIMITER ;

-- calculate salary
DROP PROCEDURE IF EXISTS calculateSalary;
DELIMITER $$;
CREATE PROCEDURE calculateSalary(IN empId VARCHAR (20))
  BEGIN
    DECLARE netSalary DECIMAL (10,2);
    DECLARE bSalary DECIMAL (10,2);
    DECLARE totalOT DECIMAL (10,2);
    DECLARE totalNoPay DECIMAL (10,2);
    DECLARE medicleAl DECIMAL (10,2);
    DECLARE empDid VARCHAR (20);
    DECLARE totalSalAdvance DECIMAL (10,2);
    DECLARE thisMonthLeaves INT;

    DECLARE EXIT HANDLER FOR 1062
    BEGIN
      SELECT 'Duplicate key error encountered';
      ROLLBACK;
    END;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
      SELECT 'SQLException encountered';
      ROLLBACK;
    END;

  START TRANSACTION;
    SELECT currenMonthLeaves INTO thisMonthLeaves FROM attendenceDetail WHERE eid=empId AND monthNo=MONTH(curdate());

    SELECT did INTO empDid FROM designationDetail WHERE eid=empId;

    SELECT basicSalary INTO bSalary FROM designation WHERE did=empDid;
    SELECT medicleAllowence INTO medicleAl FROM designation WHERE did=empDid;

    SELECT SUM(ot) INTO totalOT FROM attendence WHERE eid=empId AND MONTH(currentDate)=MONTH(CURDATE());
    SELECT SUM(nopay) INTO totalNoPay FROM attendence WHERE eid=empId AND MONTH(currentDate)=MONTH(CURDATE());

    SET totalSalAdvance=getSalaryAdvance(empId);

    IF(thisMonthLeaves=0) THEN
      SET netSalary=(bSalary+totalOT+medicleAl+getFullAllowence(bSalary))-(totalSalAdvance+getETF(bSalary)+totalNoPay);

      INSERT INTO netSalary(eid,monthName,ot,noPay,etf,epf,food,medicle,attendence,transport,salaryAdvance,total) VALUES
        (empID,MONTHNAME(CURDATE()),totalOT,totalNoPay,getETF(bSalary),getEPF(bSalary),getFoodAllowence(bSalary),medicleAl,getAttendenceAllowence(bSalary),getTransportAllowence(bSalary),totalSalAdvance,netSalary);
    ELSE
      SET netSalary=(bSalary+totalOT+medicleAl+getFoodAllowence(bSalary)+getTransportAllowence(bSalary))-(totalSalAdvance+getETF(bSalary)+totalNoPay);

      INSERT INTO netSalary(eid,monthName,ot,noPay,etf,epf,food,medicle,attendence,transport,salaryAdvance,total) VALUES
        (empID,MONTHNAME(CURDATE()),totalOT,totalNoPay,getETF(bSalary),getEPF(bSalary),getFoodAllowence(bSalary),medicleAl,0,getTransportAllowence(bSalary),totalSalAdvance,netSalary);
    END IF;
  COMMIT;
  END$$;
DELIMITER ;
call calculateSalary('e001');

-- to get all rates detail
DROP PROCEDURE IF EXISTS viewRates;
DELIMITER $$;
CREATE PROCEDURE viewRates()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT * FROM rates;
END$$;
DELIMITER ;

-- to get all allowence detail
DROP PROCEDURE IF EXISTS viewAllowance;
DELIMITER $$;
CREATE PROCEDURE viewAllowance()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT * FROM allowance;
END$$;
DELIMITER ;

-- to get all designation detail
DROP PROCEDURE IF EXISTS viewDesignation;
DELIMITER $$;
CREATE PROCEDURE viewDesignation()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT * FROM designation;
END$$;
DELIMITER ;

-- to view employee and employee detail
DROP PROCEDURE IF EXISTS viewEmployee;
DELIMITER $$;
CREATE PROCEDURE viewEmployee()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT e.eid,e.empName,e.nic,d.did FROM employee e,designationDetail d WHERE d.eid=e.eid;
END$$;
DELIMITER ;

-- to view salary advance with empoyee name
DROP PROCEDURE IF EXISTS viewSalaryAdvance;
DELIMITER $$;
CREATE PROCEDURE viewSalaryAdvance()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT s.said,e.eid,e.empName,s.currentDate,s.advance FROM salaryAdvance s,employee e WHERE s.eid=e.eid;
END$$;
DELIMITER ;

-- view attendence with empoyee name
DROP PROCEDURE IF EXISTS viewAttendence;
DELIMITER $$;
CREATE PROCEDURE viewAttendence()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT a.atid,a.eid,e.empName,a.status,a.intime,a.outtime,a.currentDate,a.ot,a.nopay FROM attendence a,employee e WHERE a.eid=e.eid;
END$$;
DELIMITER ;

-- to add monthly work days
DROP PROCEDURE IF EXISTS addMonthlyWorkDays;
DELIMITER $$;
CREATE PROCEDURE addMonthlyWorkDays(IN monthNO INT ,IN noOfWdays INT )
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  INSERT INTO montlyWorkDays VALUES(monthNO,YEAR(CURDATE()),noOfWdays);
END$$;
DELIMITER ;

-- to add yearly leaves
DROP PROCEDURE IF EXISTS addYearlyLeaves;
DELIMITER $$;
CREATE PROCEDURE addYearlyLeaves(IN yearNO INT ,IN noOfleaves INT )
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  INSERT INTO yearlyLeaves VALUES(yearNO,noOfleaves);
END$$;
DELIMITER ;

-- to view attendence detail
DROP PROCEDURE IF EXISTS viewAttendenceDetail;
DELIMITER $$;
CREATE PROCEDURE viewAttendenceDetail()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT * FROM attendenceDetail;
END$$;
DELIMITER ;

-- to view monthly work days
DROP PROCEDURE IF EXISTS viewMonthlyWorkDays;
DELIMITER $$;
CREATE PROCEDURE viewMonthlyWorkDays()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT * FROM montlyWorkDays;
END$$;
DELIMITER ;

-- to view yearly leaves
DROP PROCEDURE IF EXISTS viewYearlyLeaves;
DELIMITER $$;
CREATE PROCEDURE viewYearlyLeaves()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT * FROM yearlyLeaves;
END$$;
DELIMITER ;

-- view net salary table
DROP PROCEDURE IF EXISTS viewNetSalary;
DELIMITER $$;
CREATE PROCEDURE viewNetSalary()
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT * FROM netSalary;
END$$;
DELIMITER ;

-- to delete rates
DROP PROCEDURE IF EXISTS deleteRates;
DELIMITER $$;
CREATE PROCEDURE deleteRates(IN t VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  DELETE FROM rates WHERE types=t;
END$$;
DELIMITER ;

-- to update rates
DROP PROCEDURE IF EXISTS updateRates;
DELIMITER $$;
CREATE PROCEDURE updateRates(IN t varchar(50), IN per decimal(10, 2))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  UPDATE rates SET percentage=per WHERE types=t;
END$$;
DELIMITER ;

-- to delete designation
DROP PROCEDURE IF EXISTS deleteDesignation;
DELIMITER $$;
CREATE PROCEDURE deleteDesignation(IN d VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  DELETE FROM designation WHERE did=d;
END$$;
DELIMITER ;

-- to update rates
DROP PROCEDURE IF EXISTS updateDesignation;
DELIMITER $$;
CREATE PROCEDURE updateDesignation(IN d varchar(20), IN lvl varchar(100), IN bSalary decimal(10, 2),IN mdAllowence decimal(10, 2))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  UPDATE designation SET levels=lvl,basicSalary=bSalary,medicleAllowence=mdAllowence WHERE did=d;
END$$;
DELIMITER ;

-- to delete allowence
DROP PROCEDURE IF EXISTS deleteAllowence;
DELIMITER $$;
CREATE PROCEDURE deleteAllowence(IN a VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  DELETE FROM allowance WHERE alid=a;
END$$;
DELIMITER ;

-- to update allowence
DROP PROCEDURE IF EXISTS updateAllowence;
DELIMITER $$;
CREATE PROCEDURE updateAllowence(IN id varchar(20), IN t varchar(100), IN p decimal(10, 2))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  UPDATE allowance SET types=t,percentage=p WHERE alid=id;
END$$;
DELIMITER ;

-- to delete salary advance
DROP PROCEDURE IF EXISTS deleteSalaryAdvance;
DELIMITER $$;
CREATE PROCEDURE deleteSalaryAdvance(IN id VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  DELETE FROM salaryAdvance WHERE said=id;
END$$;
DELIMITER ;

-- to update salary advance
DROP PROCEDURE IF EXISTS updateSalaryAdvance;
DELIMITER $$;
CREATE PROCEDURE updateSalaryAdvance(IN Id varchar(20), IN aPrice decimal(10, 2))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  UPDATE salaryAdvance SET advance=aPrice WHERE said=id;
END$$;
DELIMITER ;

-- to delete a employee
DROP PROCEDURE IF EXISTS deleteEmployee;
DELIMITER $$;
CREATE PROCEDURE deleteEmployee(IN id VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  DELETE FROM employee WHERE eid=id;
END$$;
DELIMITER ;

-- to update employee with designation detail tbl
DROP PROCEDURE IF EXISTS updateEmployee;
DELIMITER $$;
CREATE PROCEDURE updateEmployee(IN empId varchar(20), IN eName varchar(100), IN nicNo varchar(20), IN desId varchar(20))
BEGIN
  DECLARE oldDesId VARCHAR (20);

  DECLARE EXIT HANDLER FOR 1062
	BEGIN
		SELECT 'Duplicate key error encountered';
		ROLLBACK;
	END;
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		SELECT 'SQLException encountered';
		ROLLBACK;
	END;

  START TRANSACTION;
    SELECT did INTO oldDesId FROM designationDetail WHERE eid=empId ORDER BY dateSince DESC LIMIT 1;
    IF(oldDesId=desId)THEN
      UPDATE employee SET empName=eName,nic=nicNo WHERE eid=empId;
    ELSE
      UPDATE employee SET empName=eName,nic=nicNo WHERE eid=empId;
      INSERT INTO designationDetail VALUES (CURDATE(),desId,empId);
    END IF;
  COMMIT;
END$$;
DELIMITER ;

-- to delete net salary
DROP PROCEDURE IF EXISTS deleteNetSalary;
DELIMITER $$;
CREATE PROCEDURE deleteNetSalary(IN id INT)
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  DELETE FROM netSalary WHERE sid=id;
END$$;
DELIMITER ;

-- to delete attendence
DROP PROCEDURE IF EXISTS deleteAttendence;
DELIMITER $$;
CREATE PROCEDURE deleteAttendence(IN id INT)
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  DELETE FROM attendence WHERE atid=id;
END$$;
DELIMITER ;

-- to delete monthly work days
DROP PROCEDURE IF EXISTS deleteMonthlyWorkDays;
DELIMITER $$;
CREATE PROCEDURE deleteMonthlyWorkDays(IN mNo INT)
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  DELETE FROM montlyworkdays WHERE monthNo=mNo AND yearNo=YEAR(CURDATE());
END$$;
DELIMITER ;

-- to delete yearly leaves
DROP PROCEDURE IF EXISTS deleteYearlyLeaves;
DELIMITER $$;
CREATE PROCEDURE deleteYearlyLeaves(IN yNo INT)
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  DELETE FROM yearlyleaves WHERE yearNo=yNo;
END$$;
DELIMITER ;

-- search salary advance using employee id
DROP PROCEDURE IF EXISTS searchSalaryAdvance;
DELIMITER $$;
CREATE PROCEDURE searchSalaryAdvance(IN empId VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT s.said,e.eid,e.empName,s.currentDate,s.advance FROM salaryAdvance s,employee e WHERE s.eid=e.eid AND s.eid like empId;
END$$;
DELIMITER ;

-- search net salary  using employee id
DROP PROCEDURE IF EXISTS searchNetSalary;
DELIMITER $$;
CREATE PROCEDURE searchNetSalary(IN empId VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT * FROM netSalary WHERE eid=empId;
END$$;
DELIMITER ;

-- search employee using eid
DROP PROCEDURE IF EXISTS searchEmployee;
DELIMITER $$;
CREATE PROCEDURE searchEmployee(IN empId VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT e.eid,e.empName,e.nic,d.did FROM employee e,designationDetail d WHERE d.eid=e.eid AND d.eid=empId;
END$$;
DELIMITER ;

-- search attendence using eid
DROP PROCEDURE IF EXISTS searchAttendence;
DELIMITER $$;
CREATE PROCEDURE searchAttendence(IN empId VARCHAR (20))
BEGIN
  DECLARE EXIT HANDLER FOR 1062
  SELECT 'Duplicate key error encountered';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  SELECT 'SQLException encountered';

  SELECT a.atid,a.eid,e.empName,a.status,a.intime,a.outtime,a.currentDate,a.ot,a.nopay FROM attendence a,employee e WHERE a.eid=e.eid AND e.eid=empId;
END$$;
DELIMITER ;


-- atid INT AUTO_INCREMENT,
--   eid VARCHAR (20),
--   status VARCHAR (10),
--   intime VARCHAR (100),
--   outtime VARCHAR (100),
--   currentDate DATE,
--   ot DECIMAL (10,2),
--   nopay DECIMAL (10,2),
-- # select DATE_FORMAT('3:00:00','%T')
-- # SELECT TIME_FORMAT('08:42:03', '%T');