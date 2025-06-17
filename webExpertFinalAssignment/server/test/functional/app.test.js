const dotenv = require("dotenv");
dotenv.config();

const loadExpress = require("../../loaders/express.js");
const DATABASE_CONNECTION = process.env.DATABASE_CONNECTION_TEST;
const DB_CONNECTION_OPTIONS = JSON.parse(process.env.DATABASE_CONNECTION_OPTIONS);
const CORS_OPTIONS = JSON.parse(process.env.CORS_OPTIONS);
const connectMongoose = require("../../loaders/database");

const {MongoClient} = require("mongodb");
const request = require("supertest");
const mongoose = require("mongoose");

let client = null;
let app = null;

const {users, usersIncludingPasswords, tasks} = require("./data");

beforeAll(async () => {
    await connectMongoose(DATABASE_CONNECTION, DB_CONNECTION_OPTIONS);
    app = loadExpress(CORS_OPTIONS);

    client = new MongoClient(DATABASE_CONNECTION);
    await client.connect();
});

afterAll(async () => {
    await mongoose.connection.close();
    await client.close();
});


beforeEach(async () => {
    const database = client.db();
    await database.collection("users").drop();
    await database.collection("tasks").drop();
    await database.collection("users").insertMany(users);
    await database.collection("tasks").insertMany(tasks);
});

describe("app", () => {

    describe("post /user/login", () => {

        it("should return statuscode 401 if username and password are not provided",
            async () => {
                await request(app)
                    .post("/user/login")
                    .expect(401);
            });

        it("should return statuscode 401 if wrong password is provided",
            async () => {
                const username = usersIncludingPasswords[0].username;
                await request(app)
                    .post("/user/login")
                    .send({username, password: "wrongpassword1"})
                    .expect(401);
            });

        it("should return message 200 if username and password are correct",
            async () => {
                const username = usersIncludingPasswords[0].username;
                const password = usersIncludingPasswords[0].password;
                await request(app)
                    .post("/user/login")
                    .send({username, password})
                    .expect(200);
            });

        it("should contain user and token if username and password are correct",
            async () => {
                const username = usersIncludingPasswords[0].username;
                const password = usersIncludingPasswords[0].password;

                const response = await request(app)
                    .post("/user/login")
                    .send({username, password})
                    .expect(200);
                const cookie = response.headers["set-cookie"].pop().split(";")[0];
                expect(cookie).toMatch(/^token=/);
                const token = cookie.substring(6);
                const user = response.body.user;
                expect(user).toBeDefined();
                expect(token).toBeDefined();
                expect(user.username).toEqual(username);
            });
    });
});


