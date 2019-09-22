CREATE TABLE irs_schema.keyword
(
    ID         int IDENTITY (1,1) NOT NULL PRIMARY KEY,
    Value      varchar(50)        NOT NULL,
    DocumentID int FOREIGN KEY REFERENCES irs_schema.document (ID)
);