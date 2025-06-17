const helmet = require("helmet");

class HelmetMiddleware {
    getMiddleware() {
        return helmet();
    }
}

module.exports = new HelmetMiddleware();
