/*const {
    ValidationError,
    AuthenticationError,
} = require("../middleware/error");*/
const Course = require("../models/courseModel");
const Evaluation = require("../models/evaluationModel");

//const mongoose = require("mongoose");
//const ObjectId = mongoose.Types.ObjectId;

async function getStudentCourses(studentId) {
    const courses = await Course.find({ students: studentId }, "name");
    return courses;
}

async function getEvaluationsByCourse(studentId, courseId) {

    const evaluations = await Evaluation.find({ student: studentId, course: courseId });

    return evaluations;

}



module.exports = {getStudentCourses, getEvaluationsByCourse};