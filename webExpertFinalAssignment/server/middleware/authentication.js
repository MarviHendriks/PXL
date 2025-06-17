const jwt = require("jsonwebtoken");
const createError = require("http-errors");
const JWT_SECRET = process.env.JWT_SECRET;
//const { ObjectId } = require("mongodb");
module.exports.authenticate = async (req, res, next) => {
    try {
        // Get the token from the Authorization header
        const authorizationHeader = req.headers.authorization;

        if (!authorizationHeader) {
            return next(createError(403, "A token is required for authentication."));
        }

        // Extract the token from the Authorization header (Bearer token)
        const token = authorizationHeader.replace("Bearer ", "");

        req.decodedPayload = await jwt.verify(token, JWT_SECRET, {algorithm: "HS256"});
        console.log("Decoded Payload:", req.decodedPayload);
    } catch (err) {
        return next(createError(401, "Invalid authentication token."));
    }
    next();
};

module.exports.authorize = function (...permissions) {
    const authorize = async (req, res, next) => {
        try {
            const decodedPayload = req.decodedPayload;
            console.log("Decoded Payload:", req.decodedPayload);

            for (let permission of permissions) {
                console.log("Checking permission:", permission);
                console.log(permission.role);

                if (!decodedPayload.roles.includes(permission.role)) {
                    continue;
                }

                if (permission.owner && req.params.id !== decodedPayload._id.toString()) {
                    console.log("Mismatched IDs:", req.params.id, decodedPayload._id.toString());
                    continue;
                }

                console.log("Authorized!");
                return next();
            }

            console.log("Unauthorized!");
            return next(createError(401, "Unauthorized."));
        } catch (err) {
            console.error("Authorization error:", err);
            return next(createError(401, "Invalid authentication token."));
        }
    };
    return authorize;
};

