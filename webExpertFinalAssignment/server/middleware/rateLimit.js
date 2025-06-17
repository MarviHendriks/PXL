const rateLimit = require("express-rate-limit");

class RateLimitMiddleware {
    getMiddleware() {
        return rateLimit({
            windowMs: 60 * 1000, // 1 minute
            max: 50, // limit each IP to 5 requests per windowMs
            message: "Too many requests from this IP, please try again later.",
        });
    }
}

module.exports = new RateLimitMiddleware();
