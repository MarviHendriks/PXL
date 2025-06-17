const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const validator = require("validator");

const courseSchema = new Schema({
    name: {
        type: String,
        required: [true, "Course name is required"],
        trim: true,
        minlength: [3, "Course name must be at least 3 characters"],
        maxlength: [50, "Course name cannot exceed 50 characters"],
        validate: {
            validator: (value) => validator.isAscii(value),
            message: "Course name should only contain ASCII characters",
        },
    },
    teacher: {
        type: Schema.Types.ObjectId,
        ref: "User",
        required: [true, "Teacher ID is required"],
    },
    students: [{
        type: Schema.Types.ObjectId,
        ref: "User",
    }],
    evaluations: [{
        type: Schema.Types.ObjectId,
        ref: "Evaluation",
    }],
});

const Course = mongoose.model("Course", courseSchema);

module.exports = Course;
