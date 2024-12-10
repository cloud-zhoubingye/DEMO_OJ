package com.example.admin_demo.controller;

import com.example.admin_demo.entity.*;
import com.example.admin_demo.service.*;
import com.example.admin_demo.Task.Task;
import com.example.admin_demo.util.AiUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class DataController {

    private final Task task;

    private final OjService ojService;
    private final EvaluationService evaluationService;

    private static final Map<String, Integer> stringToIntegerMap;

    static {
        stringToIntegerMap = new HashMap<>();
        stringToIntegerMap.put("C++", 1);
        stringToIntegerMap.put("Python", 2);
        stringToIntegerMap.put("Java", 3);
    }

    @Autowired
    public DataController(Task task, OjService ojService, EvaluationService evaluationService) {
        this.task = task;
        this.ojService = ojService;
        this.evaluationService = evaluationService;
    }

    @PostMapping("/sendData")
    public ReturnData receiveData(@RequestBody Data data) {
        System.out.println("评测ID："+data.getId());
        System.out.println("题目ID："+data.getProb());
        System.out.println("评测语言:"+data.getLanguage());
        System.out.println("评测代码:\n"+data.getCode());
        // 调用AI接口检测代码是否包含危险代码
        AiUtil aiUtil = new AiUtil();
        String STATUS = aiUtil.aiCaller(data.getCode());
        System.out.println(STATUS);
        OjProblem problem = null;
        Evaluation judgeRecord = null;
        ReturnData returnData = new ReturnData();
        // 创建问题对象并设置限制
        try{
            problem = ojService.getProblemById(data.getProb());
            LocalDateTime date = LocalDateTime.now();
            judgeRecord = new Evaluation(data.getId(), data.getUsername(), date,problem.getTitle(),0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        // 创建评测记录对象
        // 保存结果列表
        List<Result> resultList = new ArrayList<>();
        OjService ojService = new OjService();

        // 判断是否包含危险代码，不是则进行评测
        if(STATUS.equals("False")||STATUS.equals("\"False\"")){
            Integer choice = stringToIntegerMap.get(data.getLanguage());
            System.out.println("语言映射值："+choice);

            // 设置提交的代码
            Submission submission = new Submission();
            submission.setCode(data.getCode());

            // 创建测试用例对象
            List<OjTest> test = ojService.getTestsByProblemId(problem.getProblemId());
            int count = 0;
            // 循环测试用例
            for (OjTest ojTest : test) {
                // 设置输入输出文件路径
                String testInput = ojTest.getTestInput();
                String testOutput = ojTest.getTestOutput();
                Result result = new Result(ojTest.getTestId(),0,0,0,"");
                // 使用 Task 编译和运行代码
                Answer answer = task.compileAndRun(submission, problem, choice, testInput);

                // 创建结果对象
                String resultMessage = task.checkAnswer(answer, testOutput);
                if(resultMessage.equals("AC")){
                    result.setResultScore(100);
                    count++;
                }
                result.setResultInformation(resultMessage);
                result.setTimeCost(answer.getTime());
                resultList.add(result);
            }
            int score = count * 100 / test.size();
            System.out.println("得分："+score);
            judgeRecord.setScore(score);
            returnData.setResultScore(score);
            returnData.setResultData(resultList);
        }
        // 包含危险代码
        else{
            // 创建结果对象
            Result result = new Result(1,0,0,0,"WA");
            resultList.add(result);
            judgeRecord.setScore(0);
            returnData.setResultScore(0);
            returnData.setResultData(resultList);
        }
        // 保存评测记录
        try{
            evaluationService.addEvaluation(judgeRecord);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        // 返回评测记录
        return returnData;
    }
}
