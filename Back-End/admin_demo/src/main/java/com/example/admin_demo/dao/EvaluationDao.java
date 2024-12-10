package com.example.admin_demo.dao;

import com.example.admin_demo.entity.Evaluation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluationDao {
    private final String url = "jdbc:mysql://localhost:3306/oj_database"; // 替换为你的数据库 URL
    private final String username = "root"; // 替换为你的数据库用户名
    private final String password = "flt020913"; // 替换为你的数据库密码

    public List<Evaluation> getAllEvaluations() throws SQLException {
        String sql = "SELECT * FROM oj_evaluation ORDER BY date DESC";
        List<Evaluation> evaluations = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Evaluation evaluation = new Evaluation();
                evaluation.setId(rs.getInt("id"));
                evaluation.setUsername(rs.getString("username"));
                evaluation.setDate(rs.getTimestamp("date").toLocalDateTime());
                evaluation.setQuestion(rs.getString("question"));
                evaluation.setScore(rs.getInt("score"));
                evaluations.add(evaluation);
            }
        }
        return evaluations;
    }

    /**
     * 根据用户名查询评测记录
     */
    public List<Evaluation> getEvaluationsByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM oj_evaluation WHERE username = ? ORDER BY date DESC";
        List<Evaluation> evaluations = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, this.username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Evaluation evaluation = new Evaluation();
                    evaluation.setId(rs.getInt("id"));
                    evaluation.setUsername(rs.getString("username"));
                    evaluation.setDate(rs.getTimestamp("date").toLocalDateTime());
                    evaluation.setQuestion(rs.getString("question"));
                    evaluation.setScore(rs.getInt("score"));
                    evaluations.add(evaluation);
                }
            }
        }
        return evaluations;
    }

    // 添加评测记录
    public Boolean addEvaluation(Evaluation evaluation) throws SQLException {
        String sql = "INSERT INTO oj_evaluation (username, date, question, score) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evaluation.getUsername());
            stmt.setTimestamp(2, Timestamp.valueOf(evaluation.getDate()));
            stmt.setString(3, evaluation.getQuestion());
            stmt.setInt(4, evaluation.getScore());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
    }
}
