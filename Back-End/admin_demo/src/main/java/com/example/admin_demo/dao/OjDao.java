package com.example.admin_demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.admin_demo.entity.OjProblem;
import com.example.admin_demo.entity.OjResource;
import com.example.admin_demo.entity.OjTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO 层，用于直接操作数据库
 */
public class OjDao {

    private final String url = "jdbc:mysql://localhost:3306/oj_database"; // 替换为你的数据库 URL
    private final String username = "root"; // 替换为你的数据库用户名
    private final String password = "flt020913"; // 替换为你的数据库密码
    /**
     * 添加题目到数据库
     */
    public void addProblem(OjProblem problem, JSONObject json) {
        System.out.println("[OjDao/addProblem]执行到这里problem=" + problem.toString());
        /* ----------数据库访问开始---------- */

        // 加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");



            System.out.println("[OjDao/addProblem]驱动加载完毕");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "数据库驱动加载失败");
            return;
        }

        // 建立数据库连接
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("[OjDao/addProblem]数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "数据库连接失败");
            return;
        }

        // 创建 Statement
        PreparedStatement stmt = null;
        String sql = "INSERT INTO oj_problems (title, time_limit, memory_limit, description, input, output, level, problem_category) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, problem.getTitle());
            stmt.setString(2, problem.getTimeLimit());
            stmt.setString(3, problem.getMemoryLimit());
            stmt.setString(4, problem.getDescription());
            stmt.setString(5, problem.getInput());
            stmt.setString(6, problem.getOutput());
            stmt.setString(7, problem.getLevel());
            stmt.setString(8, problem.getProblemCategory());
            stmt.executeUpdate();
            System.out.println("[OjDao/addProblem]SQL 执行成功");

            json.put("result_code", 0);
            json.put("result_msg", "题目添加成功");
        } catch (SQLException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "SQL 执行失败");
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
                System.out.println("[OjDao/addProblem]资源释放成功");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTest(OjTest test, JSONObject json) {
        System.out.println("[OjDao/addTest]执行到这里test=" + test.toString());
        /* ----------数据库访问开始---------- */

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[OjDao/addTest]驱动加载完毕");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "数据库驱动加载失败");
            return;
        }

        Connection connection = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO oj_tests (problem_id, test_input, test_output) VALUES (?, ?, ?)";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("[OjDao/addTest]数据库连接成功");

            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, test.getProblemId());
            stmt.setString(2, test.getTestInput());
            stmt.setString(3, test.getTestOutput());
            stmt.executeUpdate();
            System.out.println("[OjDao/addTest]SQL 执行成功");

            json.put("result_code", 0);
            json.put("result_msg", "测试用例添加成功");
        } catch (SQLException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "SQL 执行失败");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
                System.out.println("[OjDao/addTest]资源释放成功");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addResource(OjResource resource, JSONObject json) {
        System.out.println("[OjDao/addResource]执行到这里resource=" + resource.toString());
        /* ----------数据库访问开始---------- */

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[OjDao/addResource]驱动加载完毕");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "数据库驱动加载失败");
            return;
        }

        Connection connection = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO oj_resources (problem_id, resource, resource_description) VALUES (?, ?, ?)";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("[OjDao/addResource]数据库连接成功");

            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, resource.getProblemId());
            stmt.setString(2, resource.getResource());
            stmt.setString(3, resource.getResourceDescription());
            stmt.executeUpdate();
            System.out.println("[OjDao/addResource]SQL 执行成功");

            json.put("result_code", 0);
            json.put("result_msg", "资源添加成功");
        } catch (SQLException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "SQL 执行失败");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
                System.out.println("[OjDao/addResource]资源释放成功");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTestsBatch(List<OjTest> tests, JSONObject json) {
        String sql = "INSERT INTO oj_tests (problem_id, test_input, test_output) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (OjTest test : tests) {
                stmt.setLong(1, test.getProblemId());
                stmt.setString(2, test.getTestInput());
                stmt.setString(3, test.getTestOutput());
                stmt.addBatch();
            }

            stmt.executeBatch(); // 批量执行
            json.put("result_code", 0);
            json.put("result_msg", "批量添加测试用例成功");
        } catch (SQLException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "批量添加测试用例失败");
        }
    }

    // 删除题目
    public boolean deleteProblem(Long problemId) throws Exception {
        String sql = "DELETE FROM oj_problems WHERE problem_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, problemId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // 修改题目
    public boolean updateProblem(OjProblem problem) throws Exception {
        String sql = "UPDATE oj_problems SET title = ?, time_limit = ?, memory_limit = ?, description = ?, input = ?, output = ?, level = ?, problem_category = ? WHERE problem_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, problem.getTitle());
            pstmt.setString(2, problem.getTimeLimit());
            pstmt.setString(3, problem.getMemoryLimit());
            pstmt.setString(4, problem.getDescription());
            pstmt.setString(5, problem.getInput());
            pstmt.setString(6, problem.getOutput());
            pstmt.setString(7, problem.getLevel());
            pstmt.setString(8, problem.getProblemCategory()); // 添加 problem_category 字段
            pstmt.setLong(9, problem.getProblemId());
            return pstmt.executeUpdate() > 0;
        }
    }


    // 删除测试用例
    public boolean deleteTest(Long testId) throws Exception {
        String sql = "DELETE FROM oj_tests WHERE test_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, testId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // 修改测试用例
    public boolean updateTest(OjTest test) throws Exception {
        String sql = "UPDATE oj_tests SET test_input = ?, test_output = ? WHERE test_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, test.getTestInput());
            pstmt.setString(2, test.getTestOutput());
            pstmt.setLong(3, test.getTestId());
            return pstmt.executeUpdate() > 0;
        }
    }

    // 删除资源
    public boolean deleteResource(Long resourceId) throws Exception {
        String sql = "DELETE FROM oj_resources WHERE resource_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, resourceId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // 修改资源
    public boolean updateResource(OjResource resource) throws Exception {
        String sql = "UPDATE oj_resources SET resource = ?, resource_description = ? WHERE resource_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, resource.getResource());
            pstmt.setString(2, resource.getResourceDescription());
            pstmt.setLong(3, resource.getResourceId());
            return pstmt.executeUpdate() > 0;
        }
    }

    // 删除关联的资源
    public void deleteResourcesByProblemId(Long problemId) throws Exception {
        String sql = "DELETE FROM oj_resources WHERE problem_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, problemId);
            pstmt.executeUpdate();
        }
    }

    // 删除关联的测试用例
    public void deleteTestsByProblemId(Long problemId) throws Exception {
        String sql = "DELETE FROM oj_tests WHERE problem_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, problemId);
            pstmt.executeUpdate();
        }
    }

    public void resetProblemIds() throws Exception {
        String resetSql = "SET @count = 0;";
        String updateSql = "UPDATE oj_problems SET problem_id = (@count := @count + 1);";
        String alterSql = "ALTER TABLE oj_problems AUTO_INCREMENT = 1;";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            // 重置问题 ID
            stmt.execute(resetSql);
            stmt.execute(updateSql);
            stmt.execute(alterSql);
        }
    }

    public OjProblem getProblemById(Long problemId) throws SQLException {
        String sql = "SELECT problem_id, title, time_limit, memory_limit, description, input, output, level, problem_category " +
                "FROM oj_problems WHERE problem_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, problemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                OjProblem problem = new OjProblem();
                problem.setProblemId(rs.getLong("problem_id"));
                problem.setTitle(rs.getString("title"));
                problem.setTimeLimit(rs.getString("time_limit"));
                problem.setMemoryLimit(rs.getString("memory_limit"));
                problem.setDescription(rs.getString("description"));
                problem.setInput(rs.getString("input"));
                problem.setOutput(rs.getString("output"));
                problem.setLevel(rs.getString("level"));
                problem.setProblemCategory(rs.getString("problem_category"));
                return problem;
            }
        }
        return null;
    }

    // 查询第一个测试用例
    public OjTest getFirstTestByProblemId(Long problemId) throws SQLException {
        String sql = "SELECT test_id AS testId, problem_id AS problemId, test_input AS testInput, " +
                "test_output AS testOutput FROM oj_tests WHERE problem_id = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, problemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                OjTest test = new OjTest();
                test.setTestId(rs.getLong("testId"));
                test.setProblemId(rs.getLong("problemId"));
                test.setTestInput(rs.getString("testInput"));
                test.setTestOutput(rs.getString("testOutput"));
                return test;
            }
            return null;
        }
    }


    // 查询题目相关资源
    public List<OjResource> getResourcesByProblemId(Long problemId) throws SQLException {
        String sql = "SELECT resource_id AS resourceId, problem_id AS problemId, resource, " +
                "resource_description AS resourceDescription FROM oj_resources WHERE problem_id = ?";
        List<OjResource> resources = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, problemId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OjResource resource = new OjResource();
                resource.setResourceId(rs.getLong("resourceId"));
                resource.setProblemId(rs.getLong("problemId"));
                resource.setResource(rs.getString("resource"));
                resource.setResourceDescription(rs.getString("resourceDescription"));
                resources.add(resource);
            }
        }
        return resources;
    }

    public List<OjProblem> getProblemList(String sortBy) throws SQLException {
        List<OjProblem> problemList = new ArrayList<>();

        // 验证排序字段是否合法，默认为 problem_id
        if (!"problem_id".equals(sortBy) && !"level".equals(sortBy)) {
            sortBy = "problem_id";
        }

        String sql = "SELECT problem_id AS problemId, title, level FROM oj_problems ORDER BY " + sortBy;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OjProblem problem = new OjProblem();
                problem.setProblemId(rs.getLong("problemId"));
                problem.setTitle(rs.getString("title"));
                problem.setLevel(rs.getString("level"));
                problemList.add(problem);
            }
        }

        return problemList;
    }

    public List<OjProblem> getDeleteProblemList(String sortBy) throws SQLException {
        List<OjProblem> problemList = new ArrayList<>();

        // 验证排序字段是否合法，默认为 problem_id
        if (!"problem_id".equals(sortBy) && !"level".equals(sortBy)) {
            sortBy = "problem_id";
        }

        String sql = "SELECT * FROM oj_problems ORDER BY " + sortBy;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OjProblem problem = new OjProblem();
                problem.setProblemId(rs.getLong("problem_id"));
                problem.setTitle(rs.getString("title"));
                problem.setLevel(rs.getString("level"));
                problem.setDescription(rs.getString("description"));
                problem.setInput(rs.getString("input"));
                problem.setOutput(rs.getString("output"));
                problem.setMemoryLimit(rs.getString("memory_limit"));
                problem.setTimeLimit(rs.getString("time_limit"));
                problem.setProblemCategory(rs.getString("problem_category"));
                problemList.add(problem);
            }
        }

        return problemList;
    }

    public List<OjProblem> getProblemsByLevel(String level) throws SQLException {
        List<OjProblem> problems = new ArrayList<>();
        String sql = "SELECT problem_id, title, level FROM oj_problems WHERE level = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, level); // 设置查询的难度等级
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OjProblem problem = new OjProblem();
                problem.setProblemId(rs.getLong("problem_id"));
                problem.setTitle(rs.getString("title"));
                problem.setLevel(rs.getString("level"));
                problems.add(problem);
            }
        }
        return problems;
    }

    /**
     * 根据分类获取题目列表
     * @param category 分类名称
     * @return 题目列表
     * @throws SQLException 数据库访问异常
     */
    public List<OjProblem> getProblemsByCategory(String category) throws SQLException {
        String sql = "SELECT problem_id, title, level FROM oj_problems WHERE problem_category = ?";
        List<OjProblem> problemList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OjProblem problem = new OjProblem();
                problem.setProblemId(rs.getLong("problem_id"));
                problem.setTitle(rs.getString("title"));
                problem.setLevel(rs.getString("level"));
                problemList.add(problem);
            }
        }
        return problemList;
    }

    /**
     * 根据题目名称查询题目信息
     * @param problemName 题目名称
     * @return 返回 OjProblem 实体
     */
    public OjProblem findProblemByName(String problemName) {
        String sql = "SELECT * FROM oj_problems WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, problemName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                OjProblem problem = new OjProblem();
                problem.setProblemId(rs.getLong("problem_id"));
                problem.setTitle(rs.getString("title"));
                problem.setDescription(rs.getString("description"));
                problem.setInput(rs.getString("input"));
                problem.setOutput(rs.getString("output"));
                problem.setTimeLimit(rs.getString("time_limit"));
                problem.setMemoryLimit(rs.getString("memory_limit"));
                problem.setLevel(rs.getString("level"));
                problem.setProblemCategory(rs.getString("problem_category"));
                return problem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //评测--查询所有测试用例
    public List<OjTest> getTestsByProblemId(Long problemId) throws SQLException {
        String sql = "SELECT test_id AS testId, problem_id AS problemId, test_input AS testInput, " +
                "test_output AS testOutput FROM oj_tests WHERE problem_id = ?";
        List<OjTest> tests = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, problemId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OjTest test = new OjTest();
                test.setTestId(rs.getLong("testId"));
                test.setProblemId(rs.getLong("problemId"));
                test.setTestInput(rs.getString("testInput"));
                test.setTestOutput(rs.getString("testOutput"));
                tests.add(test);
            }
        }
        return tests;
    }


}
