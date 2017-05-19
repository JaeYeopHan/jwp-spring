package next.dao;

import next.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class QuestionDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate) VALUES (?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection con) -> {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, question.getWriter());
			pstmt.setString(2, question.getTitle());
			pstmt.setString(3, question.getContents());
			pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
			return pstmt;
		}, keyHolder);

        return findById((Long) keyHolder.getKey());
    }
	
	public List<Question> findAll() {
		String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
				+ "order by questionId desc";

		return jdbcTemplate.query(
				sql, (ResultSet rs, int rowNum) -> new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"), null,
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfAnswer"))
		);
	}

	public Question findById(long questionId) {
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
				+ "WHERE questionId = ?";
		
		return jdbcTemplate.queryForObject(
				sql, (ResultSet rs, int rowNum) ->
				new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"),
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfAnswer"))
				, questionId);
	}

	public void update(Question question) {
		String sql = "UPDATE QUESTIONS set title = ?, contents = ? WHERE questionId = ?";
        jdbcTemplate.update(sql, 
        		question.getTitle(),
                question.getContents(),
                question.getQuestionId());
	}

	public void delete(long questionId) {
		String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
		jdbcTemplate.update(sql, questionId);
	}

	public void updateCountOfAnswer(long questionId) {
		String sql = "UPDATE QUESTIONS set countOfAnswer = countOfAnswer + 1 WHERE questionId = ?";
		jdbcTemplate.update(sql, questionId);
	}
}
