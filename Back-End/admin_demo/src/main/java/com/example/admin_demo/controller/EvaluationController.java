package com.example.admin_demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.admin_demo.entity.Evaluation;
import com.example.admin_demo.service.EvaluationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EvaluationController {
    private final EvaluationService evaluationService = new EvaluationService();

    @GetMapping("/get_evaluations_list")
    public JSONObject getEvaluations() {
        JSONObject json = new JSONObject();
        try {
            List<Evaluation> evaluations = evaluationService.getAllEvaluations();
            JSONArray evaluationArray = new JSONArray();

            for (Evaluation evaluation : evaluations) {
                JSONObject evaluationJson = new JSONObject();
                evaluationJson.put("id", evaluation.getId());
                evaluationJson.put("username", evaluation.getUsername());
                evaluationJson.put("date", evaluation.getDate().toString());
                evaluationJson.put("question", evaluation.getQuestion());
                evaluationJson.put("score", evaluation.getScore());
                evaluationArray.add(evaluationJson);
            }

            json.put("status", "success");
            json.put("evaluations", evaluationArray);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "Failed to fetch evaluations: " + e.getMessage());
        }
        System.out.println("/history:"+json);
        return json;
    }

    /**
     * 根据用户名获取评测记录
     */
    @PostMapping("/history")
    public JSONObject getUserEvaluations(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        try {
            String username = param.getString("username");
            if (username == null || username.isEmpty()) {
                json.put("status", "error");
                json.put("message", "用户名不能为空");
                return json;
            }

            List<Evaluation> evaluations = evaluationService.getEvaluationsByUsername(username);
            JSONArray evaluationArray = new JSONArray();

            for (Evaluation evaluation : evaluations) {
                JSONObject evaluationJson = new JSONObject();
                evaluationJson.put("id", evaluation.getId());
                evaluationJson.put("username", evaluation.getUsername());
                evaluationJson.put("date", evaluation.getDate().toString());
                evaluationJson.put("question", evaluation.getQuestion());
                evaluationJson.put("score", evaluation.getScore());
                evaluationArray.add(evaluationJson);
            }

            json.put("status", "success");
            json.put("evaluations", evaluationArray);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "系统异常：" + e.getMessage());
        }
        System.out.println("/getUserEvaluations:"+json);
        return json;
    }
}
