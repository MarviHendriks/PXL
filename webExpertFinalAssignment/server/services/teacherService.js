const Course = require("../models/courseModel");
const Evaluation = require("../models/evaluationModel");

exports.getTeacherCourses = async (teacherId) => {
    const courses = await Course.find({ teacher: teacherId }).populate("students", "username");
    return courses;
};

exports.getCourseStudents = async (teacherId, courseId) => {
    const course = await Course.findOne({ _id: courseId, teacher: teacherId }).populate("students", "username");

    if (!course) {
        throw new Error("Course not found");
    }

    return course.students;
};

exports.createEvaluation = async (teacherId, courseId, studentId, result, weight, message) => {

    const isParticipation = result >= 0;

    const evaluation = new Evaluation({
        teacher: teacherId,
        course: courseId,
        student: studentId,
        result: isParticipation ? result : null,
        weight: weight,
        message: message || null,
        date: new Date()
    });
    await evaluation.save();

    return evaluation;
};
