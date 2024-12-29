package finance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import finance.common.DBManager;
import finance.common.OracleDBManager;

public class FinanceDAO {

	
	private String account_id;
	private String account_name;
	private String account_type;
	private String parent_type;
	private int diff;
	
	private String voucher_date;
	private String descript;
	private int debit;
	private int credit;
	
	
	

	public ArrayList<FinanceVO> sumStatementList()  {
    	DBManager dbm = OracleDBManager.getInstance();  	//new OracleDBManager();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<FinanceVO> fList = new ArrayList<FinanceVO>();
		try {
			String query = "SELECT A.PARENT_TYPE, A.ACCOUNT_ID, A.ACCOUNT_NAME, SUM(J.DEBIT) - SUM(J.CREDIT) AS DIFF\r\n"
							+ "	FROM ACCOUNTS A LEFT JOIN VOUCHER J ON A.ACCOUNT_ID = J.ACCOUNT_ID\r\n"
							+ "	GROUP BY A.PARENT_TYPE, A.ACCOUNT_ID, A.ACCOUNT_NAME\r\n"
							+ "	ORDER BY A.PARENT_TYPE, A.ACCOUNT_ID";
        	System.out.println(query);
        	
			pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	FinanceVO vo = new FinanceVO();
            	vo.setParent_type(rs.getString("PARENT_TYPE"));
            	vo.setAccount_id(rs.getString("ACCOUNT_ID"));
            	vo.setAccount_name(rs.getString("ACCOUNT_NAME"));
            	vo.setDiff(rs.getInt("DIFF"));
            	fList.add(vo);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
				dbm.close(conn, pstmt, rs);
		}
		return fList;
    }
	
	
	public ArrayList<FinanceVO> sumFinanceList()  {
    	DBManager dbm = OracleDBManager.getInstance();  	//new OracleDBManager();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<FinanceVO> fList = new ArrayList<FinanceVO>();
		try {
			String query = "WITH DATA_WITH_DIFF AS ( " +
							" SELECT  J.VOUCHER_DATE,J.DESCRIPT, J.ACCOUNT_ID, A.ACCOUNT_NAME, J.DEBIT, J.CREDIT, J.DEBIT-J.CREDIT AS DIFF " +
							"FROM VOUCHER J " +
							"JOIN  ACCOUNTS A ON J.ACCOUNT_ID = A.ACCOUNT_ID), " +
							"SUMMARY AS ( SELECT  ACCOUNT_ID, SUM(DEBIT) AS TOTAL_DEBIT, SUM(CREDIT) AS TOTAL_CREDIT, SUM(DEBIT) - SUM(CREDIT) AS TOTAL_DIFF  " +
							"FROM VOUCHER GROUP BY ACCOUNT_ID )  " +
							"SELECT VOUCHER_DATE,DESCRIPT,ACCOUNT_ID,ACCOUNT_NAME,DEBIT,CREDIT,DIFF  " +
							"FROM DATA_WITH_DIFF  " +
							"UNION ALL " +
							"SELECT NULL AS VOUCHER_DATE,NULL AS DESCRIPT,ACCOUNT_ID,NULL AS ACCOUNT_NAME,TOTAL_DEBIT AS DEB,TOTAL_CREDIT AS CRE,TOTAL_DIFF AS DIFF  " +
							"FROM SUMMARY  " +
							"ORDER BY ACCOUNT_ID, VOUCHER_DATE NULLS LAST  ";
        	System.out.println(query);
        	
        	
        	
			pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	FinanceVO vo = new FinanceVO();
            	vo.setVoucher_date(rs.getString("VOUCHER_DATE"));
            	vo.setDescript(rs.getString("DESCRIPT"));
            	vo.setAccount_id(rs.getString("ACCOUNT_ID"));
            	vo.setAccount_name(rs.getString("ACCOUNT_NAME"));
            	vo.setDebit(rs.getInt("DEBIT"));
            	vo.setCredit(rs.getInt("CREDIT"));
            	vo.setDiff(rs.getInt("DIFF"));
            	fList.add(vo);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
				dbm.close(conn, pstmt, rs);
		}
		return fList;
    }

}
