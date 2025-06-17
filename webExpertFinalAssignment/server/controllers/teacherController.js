const teacherService = require("../services/teacherService");

class TeacherController {
    async getTeacherCourses(req, res, next) {
        try {
            const userId = req.params.userId;
            const courses = await teacherService.getTeacherCourses(userId);
            res.json({ courses });
        } catch (error) {
            console.error("Error in getTeacherCourses:", error);
            next(error);
        }
    }

    async getCourseStudents(req, res, next) {
        try {
            const { id: teacherId, courseId } = req.params;
            const students = await teacherService.getCourseStudents(teacherId, courseId);
            res.json({ students });
        } catch (error) {
            next(error);
        }
    }

    async createEvaluation(req, res, next) {
        try {
            const { id: teacherId, courseId, studentId } = req.params;
            const { result, weight, message } = req.body;
            await teacherService.createEvaluation(teacherId, courseId, studentId, result, weight, message);
            res.json({ message: "Evaluation created successfully" });
        } catch (error) {
            next(error);
        }
    }

    async getCourseStudentsWithScore(req, res, next) {
        try {
            const { id: teacherId, courseId } = req.params;
            const { score_lowerbound, score_upperbound } = req.query;

            let students = await teacherService.getCourseStudents(teacherId, courseId);

            if (!students) {
                return res.status(404).json({ error: "Course not found" });
            }
            if (score_lowerbound || score_upperbound) {
                students = students.filter(student => {
                    const studentScore = this.calculateStudentScore(student.evaluations);
                    return (
                        (!score_lowerbound || studentScore >= score_lowerbound) &&
                        (!score_upperbound || studentScore <= score_upperbound)
                    );
                });
            }

            res.json({ students });
        } catch (error) {
            next(error);
        }
    }

    calculateStudentScore(evaluations) {
        let totalScore = 0;
        let totalWeight = 0;

        evaluations.forEach(evaluation => {
            if (evaluation.result >= 0 && evaluation.weight > 0) {
                totalScore += evaluation.result * evaluation.weight;
                totalWeight += evaluation.weight;
            }
        });

        if (totalWeight === 0) {
            return 0; // To avoid division by zero
        }

        const percentageScore = (totalScore / (totalWeight * 10)) * 100;
        return Math.round(percentageScore);
    }
}

module.exports = new TeacherController();
