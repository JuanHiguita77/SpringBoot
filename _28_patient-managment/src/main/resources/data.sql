CREATE TABLE patient (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    registry_date DATE NOT NULL
);

INSERT INTO patient (
    id, 
    name, 
    email, 
    phone, 
    address, 
    birth_date, 
    registry_date
) VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'John Doe', 'johndoe@example.com', '1234567890', '123 Main St', '1985-05-15', '2023-01-01'),
    ('550e8400-e29b-41d4-a716-446655440001', 'Jane Smith', 'janesmith@example.com', '0987654321', '456 Elm St', '1990-07-20', '2023-01-02'),
    ('550e8400-e29b-41d4-a716-446655440002', 'Alice Johnson', 'alicej@example.com', '1122334455', '789 Oak St', '1988-03-10', '2023-01-03'),
    ('550e8400-e29b-41d4-a716-446655440003', 'Bob Brown', 'bobbrown@example.com', '2233445566', '321 Pine St', '1975-12-25', '2023-01-04'),
    ('550e8400-e29b-41d4-a716-446655440004', 'Charlie Davis', 'charlied@example.com', '3344556677', '654 Maple St', '1995-09-30', '2023-01-05'),
    ('550e8400-e29b-41d4-a716-446655440005', 'Diana Evans', 'dianae@example.com', '4455667788', '987 Birch St', '1982-11-11', '2023-01-06'),
    ('550e8400-e29b-41d4-a716-446655440006', 'Ethan Harris', 'ethanh@example.com', '5566778899', '159 Cedar St', '1998-02-14', '2023-01-07'),
    ('550e8400-e29b-41d4-a716-446655440007', 'Fiona Green', 'fionag@example.com', '6677889900', '753 Walnut St', '1980-08-08', '2023-01-08'),
    ('550e8400-e29b-41d4-a716-446655440008', 'George Hill', 'georgeh@example.com', '7788990011', '852 Spruce St', '1970-04-18', '2023-01-09'),
    ('550e8400-e29b-41d4-a716-446655440009', 'Hannah Lee', 'hannahl@example.com', '8899001122', '951 Cherry St', '1992-06-22', '2023-01-10');