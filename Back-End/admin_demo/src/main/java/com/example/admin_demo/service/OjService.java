package com.example.admin_demo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.admin_demo.dao.OjDao;
import com.example.admin_demo.entity.Evaluation;
import com.example.admin_demo.entity.OjProblem;
import com.example.admin_demo.entity.OjResource;
import com.example.admin_demo.entity.OjTest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class OjService {

    /**
     * 调用 DAO 层添加题目
     */
    public void addProblem(OjProblem problem, JSONObject json) {
        OjDao dao = new OjDao();
        dao.addProblem(problem, json);
    }

    /**
     * 调用 DAO 层添加测试用例
     */
    public void addTest(OjTest test, JSONObject json) {
        OjDao dao = new OjDao();
        dao.addTest(test, json);
    }

    /**
     * 调用 DAO 层添加资源
     */
    public void addResource(OjResource resource, JSONObject json) {
        OjDao dao = new OjDao();
        dao.addResource(resource, json);
    }
    /**
     * 批量添加测试用例
     * @param testDir 测试用例文件目录
     * @param problemId 对应题目 ID
     * @param json 返回的 JSON 结果
     */

    public void addTestsFromFiles(String testDir, Long problemId, JSONObject json) {
        File dir = new File(testDir);
        if (!dir.exists() || !dir.isDirectory()) {
            json.put("result_code", 1);
            json.put("result_msg", "测试用例目录不存在或无效");
            return;
        }

        // 获取所有文件并按文件名排序
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            json.put("result_code", 1);
            json.put("result_msg", "测试用例目录为空");
            return;
        }

        // 将测试用例读取并存入数据库
        OjDao dao = new OjDao();
        List<OjTest> tests = new ArrayList<>();
        for (File file : files) {
            if (!file.getName().endsWith(".a")) { // 如果是输入文件
                String inputFilePath = file.getAbsolutePath();
                String outputFilePath = inputFilePath + ".a";

                try {
                    // 读取输入文件内容
                    String testInput = new String(Files.readAllBytes(file.toPath()));

                    // 读取对应的输出文件内容
                    File outputFile = new File(outputFilePath);
                    String testOutput = outputFile.exists()
                            ? new String(Files.readAllBytes(outputFile.toPath()))
                            : "";

                    // 创建 OjTest 对象
                    OjTest test = new OjTest();
                    test.setProblemId(problemId);
                    test.setTestInput(testInput);
                    test.setTestOutput(testOutput);

                    tests.add(test);
                } catch (IOException e) {
                    e.printStackTrace();
                    json.put("result_code", 1);
                    json.put("result_msg", "文件读取失败：" + file.getName());
                    return;
                }
            }
        }

        // 调用 DAO 层批量插入
        dao.addTestsBatch(tests, json);
    }

    // 删除题目
    public static void deleteProblem(Long problemId, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            // 先删除关联记录
            dao.deleteResourcesByProblemId(problemId);
            dao.deleteTestsByProblemId(problemId);

            // 删除问题
            if (dao.deleteProblem(problemId)) {
                // 调用重新编号方法
                dao.resetProblemIds();
                json.put("result_code", 0);
                json.put("result_msg", "题目及关联数据删除成功，ID 已重新编号");
            } else {
                json.put("result_code", 1);
                json.put("result_msg", "题目删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "系统异常: " + e.getMessage());
        }
    }

    // 修改题目
    public static void updateProblem(OjProblem problem, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            boolean result = dao.updateProblem(problem);
            json.put("result_code", result ? 0 : 1);
            json.put("result_msg", result ? "题目更新成功" : "题目更新失败");
        } catch (Exception e) {
            json.put("result_code", 1);
            json.put("result_msg", "系统异常：" + e.getMessage());
        }
    }

    // 删除测试用例
    public static void deleteTest(Long testId, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            boolean result = dao.deleteTest(testId);
            json.put("result_code", result ? 0 : 1);
            json.put("result_msg", result ? "测试用例删除成功" : "测试用例删除失败");
        } catch (Exception e) {
            json.put("result_code", 1);
            json.put("result_msg", "系统异常：" + e.getMessage());
        }
    }

    // 修改测试用例
    public static void updateTest(OjTest test, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            boolean result = dao.updateTest(test);
            json.put("result_code", result ? 0 : 1);
            json.put("result_msg", result ? "测试用例更新成功" : "测试用例更新失败");
        } catch (Exception e) {
            json.put("result_code", 1);
            json.put("result_msg", "系统异常：" + e.getMessage());
        }
    }

    // 删除资源
    public static void deleteResource(Long resourceId, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            boolean result = dao.deleteResource(resourceId);
            json.put("result_code", result ? 0 : 1);
            json.put("result_msg", result ? "资源删除成功" : "资源删除失败");
        } catch (Exception e) {
            json.put("result_code", 1);
            json.put("result_msg", "系统异常：" + e.getMessage());
        }
    }

    // 修改资源
    public static void updateResource(OjResource resource, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            boolean result = dao.updateResource(resource);
            json.put("result_code", result ? 0 : 1);
            json.put("result_msg", result ? "资源更新成功" : "资源更新失败");
        } catch (Exception e) {
            json.put("result_code", 1);
            json.put("result_msg", "系统异常：" + e.getMessage());
        }
    }

    // 获取题目详细信息
    public void getProblemDetail(Long problemId, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            // 获取题目基本信息
            OjProblem problem = dao.getProblemById(problemId);
            if (problem == null) {
                json.put("status", "error");
                json.put("message", "题目不存在");
                return;
            }

            // 获取第一个测试用例
            OjTest sampleTest = dao.getFirstTestByProblemId(problemId);

            // 获取资源列表
            List<OjResource> resources = dao.getResourcesByProblemId(problemId);

            // 组装返回数据
            json.put("status", "ok");
            json.put("problem", problem);

            if (sampleTest != null) {
                JSONObject testJson = new JSONObject();
                testJson.put("testInput", sampleTest.getTestInput());
                testJson.put("testOutput", sampleTest.getTestOutput());
                json.put("sampleTest", testJson);
            }

            JSONArray resourceArray = new JSONArray();
            for (OjResource resource : resources) {
                JSONObject resourceJson = new JSONObject();
                resourceJson.put("resourceId", resource.getResourceId());
                resourceJson.put("resource", resource.getResource());
                resourceJson.put("resourceDescription", resource.getResourceDescription());
                resourceArray.add(resourceJson);
            }
            json.put("resources", resourceArray);

        } catch (SQLException e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "系统错误: " + e.getMessage());
        }
    }

    /**
     * 获取题目列表
     *
     * @param sortBy 排序字段
     * @param json   返回的 JSON 对象
     */
    public void getProblemList(String sortBy, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            List<OjProblem> problemList = dao.getProblemList(sortBy);

            JSONArray problemArray = new JSONArray();
            for (OjProblem problem : problemList) {
                JSONObject problemJson = new JSONObject();
                problemJson.put("problemId", problem.getProblemId());
                problemJson.put("title", problem.getTitle());
                problemJson.put("level", problem.getLevel());
                problemArray.add(problemJson);
            }

            json.put("status", "ok");
            json.put("problemList", problemArray);

        } catch (SQLException e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "系统错误: " + e.getMessage());
        }
    }

    public void getProblemsByLevel(String level, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            List<OjProblem> problems = dao.getProblemsByLevel(level); // 调用 DAO 层
            if (problems.isEmpty()) {
                json.put("status", "ok");
                json.put("message", "没有找到符合条件的题目");
                json.put("problems", new JSONArray());
            } else {
                json.put("status", "ok");
                JSONArray problemArray = new JSONArray();
                for (OjProblem problem : problems) {
                    JSONObject problemJson = new JSONObject();
                    problemJson.put("problemId", problem.getProblemId());
                    problemJson.put("title", problem.getTitle());
                    problemJson.put("level", problem.getLevel());
                    problemArray.add(problemJson);
                }
                json.put("problems", problemArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "系统错误：" + e.getMessage());
        }
    }

    /**
     * 根据题目 ID 获取题目信息
     *
     * @param problemId 题目 ID
     * @return 题目信息
     * @throws Exception 异常
     */
    public OjProblem getProblemById(Long problemId) throws Exception {
        OjDao dao = new OjDao();
        return dao.getProblemById(problemId);
    }

    /**
     * 根据分类获取题目列表
     * @param category 分类名称
     * @return 题目列表
     * @throws Exception 数据库访问异常
     */
    public List<OjProblem> getProblemsByCategory(String category) throws Exception {
        OjDao dao = new OjDao();
        return dao.getProblemsByCategory(category);
    }

    /**
     * 根据题目名称获取题目信息
     * @param problemName 题目名称
     * @return 返回 OjProblem 实体
     */
    public OjProblem getProblemByName(String problemName) {
        OjDao dao = new OjDao();
        return dao.findProblemByName(problemName);
    }

    public OjTest getFirstTestCase(Long problemId) throws Exception {
        OjDao dao = new OjDao();
        return dao.getFirstTestByProblemId(problemId);
    }

    public List<OjProblem> getProblemsByLevel(String level) throws Exception {
        OjDao dao = new OjDao();
        return dao.getProblemsByLevel(level);
    }

    public void getDeleteProblemList(String sortBy, JSONObject json) {
        try {
            OjDao dao = new OjDao();
            List<OjProblem> problemList = dao.getDeleteProblemList(sortBy);

            JSONArray problemArray = new JSONArray();
            for (OjProblem problem : problemList) {
                JSONObject problemJson = new JSONObject();
                problemJson.put("problemId", problem.getProblemId());
                problemJson.put("title", problem.getTitle());
                problemJson.put("level", problem.getLevel());
                problemJson.put("time_limit",problem.getTimeLimit());
                problemJson.put("memory_limit",problem.getMemoryLimit());
                problemJson.put("description",problem.getDescription());
                problemJson.put("input",problem.getInput());
                problemJson.put("output",problem.getOutput());
                problemJson.put("problem_catogory",problem.getProblemCategory());

                problemArray.add(problemJson);
            }

            json.put("status", "ok");
            json.put("problemList", problemArray);

        } catch (SQLException e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "系统错误: " + e.getMessage());
        }
    }
    // 评测--获取测试用例
    public List<OjTest> getTestsByProblemId(Long problemId) {
        try {
            OjDao dao = new OjDao();
            return dao.getTestsByProblemId(problemId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}