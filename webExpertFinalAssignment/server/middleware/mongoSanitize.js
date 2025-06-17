const expressMongoSanitize = require("express-mongo-sanitize");

class MongoSanitizeMiddleware {
    sanitize() {
        return expressMongoSanitize();
    }
}

module.exports = new MongoSanitizeMiddleware();
