package db.models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import db.InputHelper;
import db.JDBCConnection;

public class FactType {

	private String typeName;
	private String questionWording;
	private int id;
	private boolean isLiteral;

	public FactType(int id, String typeName, boolean isLiteral) {
		this.setId(id);
		this.setTypeName(typeName);
		this.isLiteral = isLiteral;
	}

	public FactType(int id, String typeName, boolean isLiteral, String questionWording) {
		this.setId(id);
		this.setTypeName(typeName);
		this.setQuestionWording(questionWording);
		this.isLiteral = isLiteral;

	}

	public FactType(String typeName, boolean isLiteral) {
		this.setTypeName(typeName);
		this.isLiteral = isLiteral;
	}
	
	public int save() {
		Connection conn;
		try {
			conn = JDBCConnection.getConnection();
			try (Statement statement = conn.createStatement()){
				statement.executeUpdate(String.format(""
						+ "INSERT INTO fact_type(name, is_literal) "
						+ "VALUES('%s', %b)", getTypeName(), isLiteral), Statement.RETURN_GENERATED_KEYS);

				try (ResultSet genKeys = statement.getGeneratedKeys()) {
					if (genKeys.next()) {
						int id = (int) genKeys.getLong(1);
						this.setId(id);
						return id;
					}
				}

			} catch (SQLException e) {
				System.out.println("ERROR executeQuery - " + e.getMessage());
			}
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		return -1;

	}
	

	public void update() {
		Connection conn;
		try {
			conn = JDBCConnection.getConnection();
			try (Statement statement = conn.createStatement()){

				statement.executeUpdate(String.format(""
						+ "UPDATE fact_type SET name = '%s', updated = 1 WHERE id = %d", InputHelper.santize(typeName), id));

				
			} catch (SQLException e) {
				System.out.println("ERROR executeQuery - " + e.getMessage());
			}
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}		
	}



	public void delete() {
		Connection conn;
		try {
			conn = JDBCConnection.getConnection();
			try (Statement statement = conn.createStatement()){

				statement.executeUpdate(String.format(""
						+ "UPDATE fact_type SET deleted = 1, updated = 1 WHERE id = %d", id));

				
			} catch (SQLException e) {
				System.out.println("ERROR executeQuery - " + e.getMessage());
			}
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}				
	}
	
	
	
	public static FactType getRandom(boolean isLiteral) {
		Connection conn;
		try {
			conn = JDBCConnection.getConnection();
			try (Statement statement = conn.createStatement();
					ResultSet rs = statement.executeQuery(String.format("SELECT * FROM fact_type "
							+ " JOIN fact_type_question_wording on fact_type_question_wording.fact_id = fact_type.id"
							+ " WHERE question_wording IS NOT NULL"
							+ " and is_literal = %s"
							+ " ORDER BY RAND() LIMIT 0,1", isLiteral ? "true" : "false"));) {
				while (rs.next() == true) {
					return new FactType(rs.getInt("id"),
							rs.getString("name"),
							rs.getBoolean("is_literal"),
							rs.getString("question_wording"));
				}
			} catch (SQLException e) {
				System.out.println("ERROR executeQuery - " + e.getMessage());
			}

		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		return null;
	}


	public static List<FactType> fetchAll() {
		List<FactType> result = new ArrayList<FactType>();
		Connection conn;
		try {
			conn = JDBCConnection.getConnection();
			try (Statement statement = conn.createStatement();
					ResultSet rs = statement.executeQuery("SELECT * FROM fact_type JOIN fact_type_question_wording on fact_type_question_wording.fact_id = fact_type.id");) {
				while (rs.next() == true) {
					result.add(new FactType(rs.getInt("id"), rs.getString("name"), rs.getBoolean("is_literal"), rs.getString("question_wording")));
				}
			} catch (SQLException e) {
				System.out.println("ERROR executeQuery - " + e.getMessage());
			}

		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestionWording() {
		return questionWording;
	}

	public void setQuestionWording(String questionWording) {
		this.questionWording = questionWording;
	}

	public static FactType fetchById(Integer id2) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
