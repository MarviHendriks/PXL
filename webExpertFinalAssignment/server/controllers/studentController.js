const studentService = require("../services/studentService");

class StudentController {
    async getStudentCourses(req, res, next) {
        try {
            const studentId = req.params.id;
            const courses = await studentService.getStudentCourses(studentId);
            res.json({ courses });
        } catch (error) {
            next(error);
        }
    }

    async getEvaluationsByCourse(req, res, next) {
        try {
            const { id: studentId, courseId } = req.params;
            const evaluations = await studentService.getEvaluationsByCourse(studentId, courseId);
            res.json({ evaluations });
        } catch (error) {
            next(error);
        }
    }
}

module.exports = new StudentController();
