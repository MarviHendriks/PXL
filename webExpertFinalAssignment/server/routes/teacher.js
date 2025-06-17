const express = require("express");
const teacherController = require("../controllers/teacherController");
const {authenticate, authorize} = require("../middleware/authentication");

const router = express.Router();

router.get("/:userId/courses",
    authenticate,
    authorize({role: "teacher"}),
    teacherController.getTeacherCourses);
console.log("na authenticate in teacherroueter");
router.get("/:id/courses/:courseId/students",
    authenticate,
    authorize({role: "teacher"}),
    teacherController.getCourseStudents);
router.post("/:id/courses/:courseId/students/:studentId/evaluations",
    authenticate,
    authorize({role: "teacher"}),
    teacherController.createEvaluation);
router.get("/:id/courses/:courseId/students",
    authenticate,
    authorize({ role: "teacher" }),
    teacherController.getCourseStudentsWithScore);

module.exports = router;
