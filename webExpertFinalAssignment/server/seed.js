
const dotenv = require("dotenv");
dotenv.config();

const DATABASE_CONNECTION = process.env.DATABASE_CONNECTION;
const DB_CONNECTION_OPTIONS = JSON.parse(process.env.DATABASE_CONNECTION_OPTIONS);

const User = require("./models/userModel");
const Course = require("./models/courseModel.js");
const Evaluation = require("./models/evaluationModel.js");
const mongoose = require("mongoose");
const $console = require("Console");

mongoose.connect(DATABASE_CONNECTION, DB_CONNECTION_OPTIONS).catch((error) => {
    $console.error(error.message);
    cleanup();
});

async function run() {
    try {
        // Drop existing user data
        //await User.collection.drop();
        await mongoose.connection.dropDatabase();

        // Create users
        /*const users = [
            { username: 'Timmer', password: 'tim12321', role: 'teacher' },
            { username: 'Sofie', password: 'sofie123', role: 'student' },
        ];*/
        let user1 = new User({username: "timmer", password: "123321", role: "teacher"});
        await user1.save();
        let user2 = new User({username: "sofie", password: "123321", role: "student"});
        await user2.save();
        let user3 = new User({username: "Dewit", password: "123321", role: "teacher"});
        await user3.save();
        //await User.insertMany(users);
        console.log("______________-----_________---");
        console.log(user1);
        // Create courses
        /*const courses = [
            { name: 'Wiskunde', teacher: user1._id },
            { name: 'Nederlands', teacher: user1._id },
        ];*/
        let course1 = new Course({name: "Wiskunde", teacher: user1._id, students: user2._id});
        await course1.save();
        let course2 = new Course({name: "Nederlands", teacher: user1._id});
        await course2.save();
        let course3 = new Course({name: "Web Expert", teacher: user3._id, students: user2._id});
        await course3.save();
        let course4 = new Course({name: "Chemie", teacher: user3._id, students: user2._id});
        await course4.save();
        //await Course.insertMany(courses);

        // Create evaluations
        const evaluations = [
            {
                result: 10,
                weight: 3,
                student: user2._id,
                course: course1._id,
                date: new Date(),
            },
            {
                result: 5,
                weight: 1,
                student: user2._id,
                course: course4._id,
                date: new Date(),
            },
            {
                result: 0,
                weight: 1,
                student: user2._id,
                course: course2._id,
                date: new Date(),
            },
            {
                result: 10,
                weight: 2,
                student: user2._id,
                course: course1._id,
                date: new Date(),
            },
            {
                result: 10,
                weight: 2,
                student: user2._id,
                course: course3._id,
                date: new Date(),
            },
            {
                result: 7,
                weight: 4,
                student: user2._id,
                course: course3._id,
                date: new Date(),
            },
        ];

        await Evaluation.insertMany(evaluations);

        // Fetch all users
        const allUsers = await User.find({});
        console.log("Users:");
        console.log(allUsers);
    } catch (error) {
        console.error(error);
    } finally {
        cleanup();
    }
}

run().catch((err) => { console.log(err.stack); });

process.on("SIGINT", cleanup);
process.on("SIGTERM", cleanup);

function cleanup() {
    $console.log("\nBye!");
    mongoose.connection.close();
    process.exit();
}
