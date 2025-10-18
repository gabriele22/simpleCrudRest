// mongo-init.js
// This script runs automatically when MongoDB container starts for the first time

// Switch to the petdb database
db = db.getSiblingDB('petdb');

// Create the pets collection (MongoDB creates it automatically on first insert)
// Insert sample data
db.pets.insertMany([
    { _id: NumberLong(1), name: 'Max', species: 'Dog', age: 3, owner_name: 'John Doe' },
    { _id: NumberLong(2), name: 'Bella', species: 'Cat', age: 2, owner_name: 'Jane Smith' },
    { _id: NumberLong(3), name: 'Charlie', species: 'Dog', age: 5, owner_name: 'Bob Johnson' },
    { _id: NumberLong(4), name: 'Luna', species: 'Cat', age: 1, owner_name: 'Alice Brown' },
    { _id: NumberLong(5), name: 'Rocky', species: 'Dog', age: 4, owner_name: 'Mike Wilson' },
    { _id: NumberLong(6), name: 'Mittens', species: 'Cat', age: 6, owner_name: 'Sarah Davis' },
    { _id: NumberLong(7), name: 'Buddy', species: 'Dog', age: 7, owner_name: 'Tom Anderson' }
]);

// Create indexes for better performance
db.pets.createIndex({ species: 1 });
db.pets.createIndex({ owner_name: 1 });

print('MongoDB initialization complete - 10 pets inserted');