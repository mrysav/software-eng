CREATE TABLE favorites (
    name VARCHAR(100) NOT NULL, 
    plan_id INTEGER NOT NULL,
    FOREIGN KEY (name) REFERENCES users (name),
    FOREIGN KEY (plan_id) REFERENCES plans ("id")
);
