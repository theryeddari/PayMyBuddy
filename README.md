# PayMyBuddy
financial application

read it with Code view and not Preview


Transaction                                  Client                                             ClientRelationships   
___________________________                 _____________________________                       __________________________
| id BIGINT(20)            |                |     id BIGINT(20)         |                       |  client_id BIGINT(20)  |
| description VARCHAR(100) |                |    username VARCHAR(50)   |                       |  friend_id BIGINT(20)  |
|amount DOUBLE             |                |   email VARCHAR(100)      |                       |                        |
|sender BIGINT(20)         |_M_ _ _ _ _ _1__|  password VARCHAR(100)    | 1___________________M |                        |
|receiver BIGINT(20)       |                |  role VARCHAR(20)         |                       |                        |
|                          |_M_ _ _ _ _ _1__|  saving DOUBLE            | 1___________________M |                        |
____________________________                ____________________________                        __________________________
|INDEXES                   |                |  INDEXES                  |                       |    INDEXES             |    
|__________________________|                |___________________________|                       |________________________|
| PRIMARY                  |                | PRIMARY                   |                       | PRIMARY                |
|FK_sender_idx             |                |email_idx                  |                       |FK_connection_id_idx    |
|FK_receiver_idx           |                |                           |                       |                        |
|__________________________|                |___________________________|                       |________________________|



Non identifying relationShip :     _ _ _ _ _ _
Identifying relationShip :         ____________
