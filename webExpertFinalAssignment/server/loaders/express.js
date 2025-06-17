const express = require("express");
const cors = require("cors");
const cookieParser = require("cookie-parser");
const userRouter = require("../routes/user");
const studentRouter = require("../routes/student");
const teacherRouter = require("../routes/teacher");
const mongoSanitizeMiddleware = require("../middleware/mongoSanitize");
const helmetMiddleware = require("../middleware/helmet");
const rateLimitMiddleware = require("../middleware/rateLimit");
const rateLimit = require("express-rate-limit");

const {errorHandler, routeNotFound} = require("../middleware/error");


// 5req per minuut
const limiter = rateLimit({
    windowMs: 60 * 1000, // 1 minute
    max: 50, // limit each IP to 5 requests per windowMs
    message: "Too many requests from this IP, please try again later.",
});
function loadExpress(CORS_OPTIONS) {
    const app = express();
    app.use(limiter);
    app.use("*", cors(CORS_OPTIONS));
    app.use(cookieParser());
    app.use(express.json());

    app.use(mongoSanitizeMiddleware.sanitize());
    app.use(helmetMiddleware.getMiddleware());
    app.use(rateLimitMiddleware.getMiddleware());
    app.use("/user", userRouter);
    app.use("/teacher", teacherRouter);
    app.use("/student", studentRouter);
    app.use(routeNotFound);
    app.use(errorHandler);
    return app;
}

module.exports = loadExpress;


