const mongoose = require("mongoose");
mongoose.set("strictQuery", true);

async function connectMongoose(DATABASE_CONNECTION, DB_CONNECTION_OPTIONS) {
    return await mongoose.connect(DATABASE_CONNECTION, DB_CONNECTION_OPTIONS);
}

module.exports = connectMongoose;


