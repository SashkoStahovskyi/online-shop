INSERT INTO users  (user_name, hashed_password, salt, role)
VALUES ('admin', 'f9018e69fd2b5797f4276c48613b8e35', '3af49e9e-8708-488f-b4de-655544817409', 'ADMIN'),
       ('user', 'b8f815d0421eb92bc79b688c23a88021', '425b2082-bb83-4e17-8120-f73ac42c8107', 'USER'),
       ('guest', 'bd1cbcaf117c40bca3932a0c418b6f1c', '2fb85a99-1bce-4be1-bfcd-4400f63f6c6a', 'GUEST');