package com.example.admin_demo.service;

import com.example.admin_demo.dao.EvaluationDao;
import com.example.admin_demo.entity.Evaluation;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class EvaluationService {
    private final EvaluationDao evaluationDAO = new EvaluationDao();

    public List<Evaluation> getAllEvaluations() throws SQLException {
        return evaluationDAO.getAllEvaluations();
    }

    /**
     * 根据用户名获取评测记录
     */
    public List<Evaluation> getEvaluationsByUsername(String username) throws SQLException {
        return evaluationDAO.getEvaluationsByUsername(username);
    }

    /**
     * 添加评测记录
     */
    public boolean addEvaluation(Evaluation evaluation) throws SQLException {
        return evaluationDAO.addEvaluation(evaluation);
    }
}
