import kotlin.system.exitProcess

data class Student(
    val studentId: String,
    var fullName: String,
    var age: Int,
    var major: String,
    var gpa: Double
)

object StudentManager {
    private val students = mutableListOf<Student>()

    init {
        // 5 sinh vien mau
        students.add(Student("SV27001", "Nguyen Quoc Tan", 21, "Ky thuat phan mem", 8.7))
        students.add(Student("SV27002", "Tran Thi Kim Ngan", 20, "Cong nghe thong tin", 6.4))
        students.add(Student("SV27003", "Le Van Hoang", 22, "Ky thuat phan mem", 4.8))
        students.add(Student("SV27004", "Pham Thi Thu Ha", 21, "Khoa hoc may tinh", 7.9))
        students.add(Student("SV27005", "Vo Minh Duc", 23, "Cong nghe thong tin", 9.1))
    }

    // ===== 6 chuc nang menu co ban =====

    // 1. Add student
    fun addStudent() {
        print("Student ID: ")
        val id = readLine()?.trim() ?: return
        if (students.any { it.studentId.equals(id, ignoreCase = true) }) {
            println("Student ID already exists!")
            return
        }
        print("Full Name: ")
        val name = readLine()?.trim() ?: ""
        print("Age: ")
        val age = readLine()?.trim()?.toIntOrNull() ?: 0
        print("Major: ")
        val major = readLine()?.trim() ?: ""
        print("GPA: ")
        val gpa = readLine()?.trim()?.toDoubleOrNull() ?: 0.0

        students.add(Student(id, name, age, major, gpa))
        println("Added successfully!")
    }

    // 2. Display all students
    fun displayAll() {
        if (students.isEmpty()) {
            println("No students available.")
            return
        }
        printTable(students)
    }

    // 3. Search student (by ID)
    fun searchStudent() {
        print("Enter Student ID to search: ")
        val id = readLine()?.trim() ?: return
        val result = students.find { it.studentId.equals(id, ignoreCase = true) }
        if (result != null) printTable(listOf(result))
        else println("Student not found.")
    }

    // 4. Calculate average GPA (toan bo sinh vien)
    fun calculateAverageGpa() {
        if (students.isEmpty()) {
            println("No students available.")
            return
        }
        val avg = students.sumOf { it.gpa } / students.size
        println("Average GPA: %.2f".format(avg))
    }

    // 5. Find student with highest GPA
    fun findHighestGpa() {
        val result = students.maxByOrNull { it.gpa }
        if (result != null) printTable(listOf(result)) else println("No students available.")
    }

    // 6. Remove student
    fun removeStudent() {
        print("Enter Student ID to remove: ")
        val id = readLine()?.trim() ?: return
        val removed = students.removeIf { it.studentId.equals(id, ignoreCase = true) }
        if (removed) println("Removed successfully!") else println("Student not found.")
    }

    // ===== 12 yeu cau bo sung =====

    // Yeu cau 1: Dem so sinh vien co GPA >= 8.0
    fun countGpaGte8() {
        val count = students.count { it.gpa >= 8.0 }
        println("Number of students with GPA >= 8.0: $count")
    }

    // Yeu cau 2: Dem so sinh vien co GPA < 5.0
    fun countGpaLt5() {
        val count = students.count { it.gpa < 5.0 }
        println("Number of students with GPA < 5.0: $count")
    }

    // Yeu cau 3: Tinh GPA trung binh cua sinh vien nganh duoc giao
    fun averageGpaByMajor() {
        print("Enter major: ")
        val major = readLine()?.trim() ?: return
        val filtered = students.filter { it.major.equals(major, ignoreCase = true) }
        if (filtered.isEmpty()) {
            println("No students found in major: $major")
            return
        }
        val avg = filtered.sumOf { it.gpa } / filtered.size
        println("Average GPA of $major: %.2f".format(avg))
    }

    // Yeu cau 4: Tim sinh vien co GPA cao nhat -> dung lai findHighestGpa() o menu 5

    // Yeu cau 5: Tim sinh vien lon tuoi nhat
    fun findOldestStudent() {
        val result = students.maxByOrNull { it.age }
        if (result != null) printTable(listOf(result)) else println("No students available.")
    }

    // Yeu cau 6: Tim sinh vien co GPA trong khoang 7.0 -> 8.5
    fun findGpaInRange() {
        val result = students.filter { it.gpa in 7.0..8.5 }
        if (result.isNotEmpty()) printTable(result) else println("No students found in this GPA range.")
    }

    // Yeu cau 7: Tim tat ca sinh vien thuoc mot nganh
    fun findByMajor() {
        print("Enter major: ")
        val major = readLine()?.trim() ?: return
        val result = students.filter { it.major.equals(major, ignoreCase = true) }
        if (result.isNotEmpty()) printTable(result) else println("No students found in major: $major")
    }

    // Yeu cau 8: Tim sinh vien theo mot phan ten
    fun findByNamePart() {
        print("Enter part of name: ")
        val keyword = readLine()?.trim() ?: return
        val result = students.filter { it.fullName.contains(keyword, ignoreCase = true) }
        if (result.isNotEmpty()) printTable(result) else println("No students found with name containing: $keyword")
    }

    // Yeu cau 9: Sap xep sinh vien theo GPA giam dan
    fun sortByGpaDesc() {
        val result = students.sortedByDescending { it.gpa }
        printTable(result)
    }

    // Yeu cau 10: Hien thi 3 sinh vien co GPA cao nhat
    fun top3Gpa() {
        val result = students.sortedByDescending { it.gpa }.take(3)
        printTable(result)
    }

    // Yeu cau 11: Sap xep sinh vien theo tuoi
    fun sortByAge() {
        val result = students.sortedBy { it.age }
        printTable(result)
    }

    // Yeu cau 12: Sap xep sinh vien theo ten
    fun sortByName() {
        val result = students.sortedBy { it.fullName }
        printTable(result)
    }

    private fun printTable(list: List<Student>) {
        println("%-10s %-25s %-5s %-20s %-6s".format("ID", "Full Name", "Age", "Major", "GPA"))
        println("-".repeat(70))
        for (s in list) {
            println("%-10s %-25s %-5d %-20s %-6.2f".format(s.studentId, s.fullName, s.age, s.major, s.gpa))
        }
    }
}

fun printMenu() {
    println(
        """
        ========== STUDENT MANAGEMENT ==========
        1. Add student
        2. Display all students
        3. Search student
        4. Calculate average GPA
        5. Find student with highest GPA
        6. Remove student
        7. Count students with GPA >= 8.0
        8. Count students with GPA < 5.0
        9. Average GPA by major
        10. Find oldest student
        11. Find students with GPA in range 7.0 - 8.5
        12. Find all students by major
        13. Find students by partial name
        14. Sort students by GPA descending
        15. Display top 3 students by GPA
        16. Sort students by age
        17. Sort students by name
        0. Exit
        ========================================
        """.trimIndent()
    )
    print("Choose: ")
}

fun main() {
    while (true) {
        printMenu()
        val input = readLine()?.trim()
        if (input == null) break
        when (input) {
            "1" -> StudentManager.addStudent()
            "2" -> StudentManager.displayAll()
            "3" -> StudentManager.searchStudent()
            "4" -> StudentManager.calculateAverageGpa()
            "5" -> StudentManager.findHighestGpa()
            "6" -> StudentManager.removeStudent()
            "7" -> StudentManager.countGpaGte8()
            "8" -> StudentManager.countGpaLt5()
            "9" -> StudentManager.averageGpaByMajor()
            "10" -> StudentManager.findOldestStudent()
            "11" -> StudentManager.findGpaInRange()
            "12" -> StudentManager.findByMajor()
            "13" -> StudentManager.findByNamePart()
            "14" -> StudentManager.sortByGpaDesc()
            "15" -> StudentManager.top3Gpa()
            "16" -> StudentManager.sortByAge()
            "17" -> StudentManager.sortByName()
            "0" -> {
                println("Goodbye!")
                exitProcess(0)
            }
            else -> println("Invalid choice, try again.")
        }
        println()
    }
}