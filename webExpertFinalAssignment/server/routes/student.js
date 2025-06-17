const express = require("express");
const studentController = require("../controllers/studentController");
const {authenticate, authorize} = require("../middleware/authentication");

const router = express.Router();


router.get("/:id/courses",
    authenticate,
    authorize({role: "student"}),
    studentController.getStudentCourses);
router.get("/:id/courses/:courseId/evaluations",
    authenticate,
    authorize({role: "student"}),
    studentController.getEvaluationsByCourse);

module.exports = router;
