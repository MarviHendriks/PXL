const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const validator = require("validator");

const evaluationSchema = new Schema({
    student: {
        type: Schema.Types.ObjectId,
        ref: "User",
        required: [true, "Student ID is required"],
    },
    course: {
        type: Schema.Types.ObjectId,
        ref: "Course",
        required: [true, "Course ID is required"],
    },
    result: {
        type: Number,
        required: [true, "Result is required"],
        min: [0, "Result must be at least 0"],
        max: [10, "Result cannot exceed 10"],
    },
    weight: {
        type: Number,
        required: [true, "Weight is required"],
        min: [1, "Weight must be at least 1"],
        max: [19, "Weight cannot exceed 19"],
    },
    message: {
        type: String,
        trim: true,
        validate: {
            validator: (value) => validator.isAscii(value),
            message: "Message should only contain ASCII characters",
        },
    },
    date: {
        type: Date,
        default: Date.now,
    },
});

const Evaluation = mongoose.model("Evaluation", evaluationSchema);

module.exports = Evaluation;
