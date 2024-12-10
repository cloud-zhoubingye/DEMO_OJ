package com.example.admin_demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.admin_demo.entity.OjProblem;
import com.example.admin_demo.entity.OjResource;
import com.example.admin_demo.entity.OjTest;
import com.example.admin_demo.service.OjService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller 用于处理 HTTP 请求
 */
@RestController
@RequestMapping("/api")
public class OjController {

    /**
     * 添加题目接口
     * @param problem 前端传入的题目对象
     * @return 返回操作结果
     */
    @RequestMapping("/add_new_problem")
    public JSONObject addProblem(@RequestBody(required = false) OjProblem problem) {
        System.out.println("add_new_problem:" + problem);
        JSONObject json = new JSONObject();
        OjService service = new OjService();
        service.addProblem(problem, json);
        return json;
    }

    /**
     * 添加测试用例接口
     * @param test 前端传入的测试用例对象
     * @return 返回操作结果
     */
    @RequestMapping("/add_test")
    public JSONObject addTest(@RequestBody(required = false) OjTest test) {
        JSONObject json = new JSONObject();
        System.out.println("[OjController/addTest] " + test);
        OjService service = new OjService();
        service.addTest(test, json);
        return json;
    }

    /**
     * 添加资源接口
     * @param resource 前端传入的资源对象
     * @return 返回操作结果
     */
    @RequestMapping("/add_resource")
    public JSONObject addResource(@RequestBody(required = false) OjResource resource) {
        JSONObject json = new JSONObject();
        System.out.println("[OjController/addResource] " + resource);
        OjService service = new OjService();
        service.addResource(resource, json);
        return json;
    }

    /**
     * 批量添加测试用例接口
     */
    @RequestMapping("/add_tests_from_files")
    public JSONObject addTestsFromFiles(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();

        String testDir = param.getString("testDir"); // 测试用例文件目录
        Long problemId = param.getLong("problemId"); // 对应题目 ID

        OjService service = new OjService();
        service.addTestsFromFiles(testDir, problemId, json);

        return json;
    }

    // 删除题目
    @RequestMapping("/delete_problem")
    public JSONObject deleteProblem(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        Long problemId = param.getLong("id");
        OjService.deleteProblem(problemId, json);
        return json;
    }

    // 修改题目
    @RequestMapping("/save_problem")
    public JSONObject updateProblem(@RequestBody OjProblem problem) {
        System.out.println("/save_problem:" + problem);
        JSONObject json = new JSONObject();
        OjService.updateProblem(problem, json);
        return json;
    }

    // 删除测试用例
    @RequestMapping("/delete_test")
    public JSONObject deleteTest(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        Long testId = param.getLong("testId");
        OjService.deleteTest(testId, json);
        return json;
    }

    // 修改测试用例
    @RequestMapping("/update_test")
    public JSONObject updateTest(@RequestBody OjTest test) {
        JSONObject json = new JSONObject();
        OjService.updateTest(test, json);
        return json;
    }

    // 删除资源
    @RequestMapping("/delete_resource")
    public JSONObject deleteResource(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        Long resourceId = param.getLong("resourceId");
        OjService.deleteResource(resourceId, json);
        return json;
    }

    // 修改资源
    @RequestMapping("/update_resource")
    public JSONObject updateResource(@RequestBody OjResource resource) {
        JSONObject json = new JSONObject();
        OjService.updateResource(resource, json);
        return json;
    }

    // 获取题目详细信息
    @RequestMapping("/get_problem_detail")
    public JSONObject getProblemDetail(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        OjService service = new OjService();
        Long problemId = param.getLong("problemId");

        if (problemId == null) {
            json.put("status", "error");
            json.put("message", "题目ID不能为空");
            return json;
        }

        service.getProblemDetail(problemId, json);
        return json;
    }

    @RequestMapping("/problems_list")
    public JSONObject getProblemList() {
        JSONObject json = new JSONObject();

        OjService service = new OjService();

        String  sortBy = "problem_id"; // 默认按编号排序

        service.getProblemList(sortBy, json);
        System.out.println("/problem_list:" + json);
        return json;
    }

    @RequestMapping("/delete_problem_detail")
    public JSONObject getDeleteProblemList() {
        JSONObject json = new JSONObject();

        OjService service = new OjService();

        String  sortBy = "problem_id"; // 默认按编号排序

        service.getDeleteProblemList(sortBy, json);
        System.out.println("/delete_problem_detail:" + json);
        return json;
    }
    /**
     * 根据题目 ID 获取题目详细信息
     *
     * @param param 请求参数，包含题目 ID
     * @return 返回题目详细信息的 JSON
     */
    @PostMapping("/get_problem_by_id")
    public JSONObject getProblemById(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        try {
            OjService service = new OjService();
            Long problemId = param.getLong("problemId");
            if (problemId == null) {
                json.put("result", "failure");
                json.put("message", "题目 ID 不能为空");
                return json;
            }
            OjProblem problem = service.getProblemById(problemId);
            if (problem == null) {
                json.put("result", "failure");
                json.put("message", "题目不存在");
                return json;
            }
            json.put("result", "success");
            json.put("problem", problem);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "failure");
            json.put("message", "系统异常：" + e.getMessage());
        }
        return json;
    }

    /**
     * 根据分类获取题目列表
     * @param param 包含分类名的请求参数
     * @return 返回题目列表的 JSON
     */
    @PostMapping("/category_problem")
    public JSONObject getProblemsByCategory(@RequestBody JSONObject param) {
        System.out.println("/category_problem:"+param);
        JSONObject json = new JSONObject();
        try {
            String category = param.getString("category");
            if (category == null || category.isEmpty()) {
                json.put("result", "failure");
                json.put("message", "分类不能为空");
                return json;
            }
            OjService service = new OjService();
            List<OjProblem> problemList = service.getProblemsByCategory(category);
            JSONArray problemArray = new JSONArray();
            for (OjProblem problem : problemList) {
                JSONObject problemJson = new JSONObject();
                problemJson.put("problemId", problem.getProblemId());
                problemJson.put("title", problem.getTitle());
                problemJson.put("level", problem.getLevel());
                problemArray.add(problemJson);
            }

            json.put("result", "success");
            json.put("problems", problemArray);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "failure");
            json.put("message", "系统异常：" + e.getMessage());
        }
        System.out.println("/category_problem:"+json);
        return json;
    }

    /**
     * 根据题目名称获取题目信息
     * @param param 请求参数，包括题目名称
     * @return 返回题目信息的 JSON 对象
     */
    @PostMapping("/getProblem")
    public JSONObject getProblem(@RequestBody JSONObject param) {
        System.out.println("/getProblem:前端post的参数是" + param);
        JSONObject json = new JSONObject();
        try {
            Long problemId = param.getLong("prob");
            if (problemId <= 0) {
                json.put("status", "error");
                json.put("message", "题目编号有误");
                return json;
            }

            OjService service = new OjService();
            OjProblem problem = service.getProblemById(problemId);
            if (problem == null) {
                json.put("status", "error");
                json.put("message", "题目不存在");
            } else {
                json.put("status", "success");
                json.put("problemName", problem.getTitle());
                json.put("problemDescription", problem.getDescription());
                json.put("problemInputDescription", problem.getInput());
                json.put("problemOutputDescription", problem.getOutput());
                json.put("timeLimit", problem.getTimeLimit());
                json.put("memoryLimit", problem.getMemoryLimit());
                json.put("difficultyLevel", problem.getLevel());
                json.put("problemCategory", problem.getProblemCategory());

                // 获取对应题目的第一个测试用例
                OjTest sampleTest = service.getFirstTestCase(problem.getProblemId());
                if (sampleTest != null) {
                    JSONObject sample = new JSONObject();
                    sample.put("input", sampleTest.getTestInput());
                    sample.put("output", sampleTest.getTestOutput());
                    json.put("problemInputOutputSample", sample);
                } else {
                    json.put("problemInputOutputSample", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "系统异常：" + e.getMessage());
        }
        System.out.println("/getProblem:" + json);
        return json;
    }

    /**
     * 根据难度获取题目列表
     * @param param 包含分类名的请求参数
     * @return 返回题目列表的 JSON
     */
    @PostMapping("/get_hard_based_problems")
    public JSONObject getProblemsByLevel(@RequestBody JSONObject param) {
        System.out.println("/get_hard_based_problems"+param);
        JSONObject json = new JSONObject();
        try {
            String level = param.getString("hard_choose");
            if (level == null || level.isEmpty()) {
                json.put("result", "failure");
                json.put("message", "分类不能为空");
                return json;
            }
            OjService service = new OjService();
            List<OjProblem> problemList = service.getProblemsByLevel(level);
            JSONArray problemArray = new JSONArray();
            for (OjProblem problem : problemList) {
                JSONObject problemJson = new JSONObject();
                problemJson.put("problemId", problem.getProblemId());
                problemJson.put("title", problem.getTitle());
                problemJson.put("level", problem.getLevel());
                problemArray.add(problemJson);
            }

            json.put("result", "success");
            json.put("problems", problemArray);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "failure");
            json.put("message", "系统异常：" + e.getMessage());
        }
        System.out.println("/get_hard_based_problems:"+json);
        return json;
    }

    /**
     * 根据题目 ID 获取题目详细信息
     *
     * @param param 请求参数，包含题目 ID
     * @return 返回题目详细信息的 JSON
     */
    @PostMapping("/show_problem_detailbyName")
    public JSONObject getProblembyName(@RequestBody JSONObject param) {
        System.out.println("/show_problem_detailbyName:前端post的参数是" + param);
        JSONObject json = new JSONObject();
        try {
            String problemName = param.getString("problem_name");

            OjService service = new OjService();
            OjProblem problem = service.getProblemByName(problemName);
            if (problem == null) {
                json.put("status", "error");
                json.put("message", "题目不存在");
            } else {
                json.put("status", "success");
                json.put("problemId",problem.getProblemId());
                json.put("problemName", problem.getTitle());
                json.put("problemDescription", problem.getDescription());
                json.put("problemInputDescription", problem.getInput());
                json.put("problemOutputDescription", problem.getOutput());
                json.put("timeLimit", problem.getTimeLimit());
                json.put("memoryLimit", problem.getMemoryLimit());
                json.put("difficultyLevel", problem.getLevel());
                json.put("problemCategory", problem.getProblemCategory());

                // 获取对应题目的第一个测试用例
                OjTest sampleTest = service.getFirstTestCase(problem.getProblemId());
                if (sampleTest != null) {
                    JSONObject sample = new JSONObject();
                    sample.put("input", sampleTest.getTestInput());
                    sample.put("output", sampleTest.getTestOutput());
                    json.put("problemInputOutputSample", sample);
                } else {
                    json.put("problemInputOutputSample", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "系统异常：" + e.getMessage());
        }
        System.out.println("/show_problem_detailbyName:" + json);
        return json;
    }
}