CREATE TABLE irs_schema.document
(
    ID         int IDENTITY (1,1) NOT NULL PRIMARY KEY,
    Title      varchar(100)       NOT NULL,
    AddingTime DATETIME           NOT NULL,
    FilePath   varchar(500)       NOT NULL UNIQUE
);