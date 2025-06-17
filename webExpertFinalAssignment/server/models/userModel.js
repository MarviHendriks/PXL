const mongoose = require("mongoose");
const bcrypt = require("bcryptjs");
const validator = require("validator");

const userSchema = new mongoose.Schema(
    {
        username: {
            type: String,
            required: true,
            unique: true,
            trim: true,
            minLength: 2,
            maxLength: 20,
            validate: {
                validator: (value) => validator.isAlphanumeric(value),

                message: (props) => `${props.value} is not a valid username.`
            }
        },
        hashedPassword: {
            type: String,
            required: true,
        },
        role: {
            type: String,
            enum: ["teacher", "student"],
            required: [true, "Role is required"],
        },
    },
    {collection: "users"}
);

userSchema
    .virtual("password")
    .set(function (password) {
        password = password.trim();
        if (password.length < 6) {
            this.invalidate("password", "must be at least 6 characters.");
        }
        if (password.length > 20) {
            this.invalidate("password", "must be shorter than 20 characters.");
        }
        this.hashedPassword = bcrypt.hashSync(password, 8);
    });

userSchema.statics.findOneByCredentials = async function (username, password) {
    const user = await this.findOne({username});
    if (!user) {
        return null;
    }
    try {
        if (password === undefined) {
            console.error("Password is undefined.");
            return null;
        }
        //user.hashedPassword = user._doc.hashedPassword;
        console.log(user.hashedPassword);
        let passwordIsCorrect = await bcrypt.compare(password, user.hashedPassword);
        console.log("Password Comparison Result:", passwordIsCorrect);
        if (!passwordIsCorrect) {
            return null;
        }

        return user;
    } catch (error) {
        console.error("Error during password comparison:", error);
        return null;
    }
};

module.exports = mongoose.model("User", userSchema);

