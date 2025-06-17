const userService = require("../services/userService");
const jwt = require("jsonwebtoken");
const JWT_SECRET = process.env.JWT_SECRET;
const JWT_EXPIRATION = process.env.JWT_EXPIRATION;

module.exports.login = async function (req, res, next) {
    try {
        const status = 200;
        const username = req.body.username;
        const password = req.body.password;
        console.log("Username:", username);
        console.log("Password:", password);
        let user = await userService.findUserByCredentials(username, password);
        if (!user) {
            return res.status(401).json({ error: "Invalid credentials" });
        }
        console.log("User:", user);
        const token = jwt.sign(
            {_id: user._id, roles: user.role}
            , JWT_SECRET
            , {expiresIn: JWT_EXPIRATION, algorithm: "HS256"}
        );
        console.log("Token:", token);
        user = {
            _id: user._id,
            username: user.username,
            roles: user.role
        };
        res.cookie("token", token,
            {
                httpOnly: true,
                sameSite: "strict",
                expires: new Date(Date.now() + parseInt(JWT_EXPIRATION))
            }
        );
        res.status(status).json({user, token});
    } catch (err) {
        next(err);
    }
};

module.exports.logout = async function (req, res, next) {
    try {
        const status = 200;
        res.clearCookie("token");
        res.status(status).json({});
    } catch (err) {
        next(err);
    }
};

module.exports.checkLogin = async function (req, res, next) {
    try {
        const status = 200;
        const id = req.decodedPayload._id;
        let user = await userService.findById(id);
        user = {
            _id: user._id,
            username: user.username,
            roles: user.role
        };
        res.status(status).json({user});
    } catch (err) {
        next(err);
    }
};


