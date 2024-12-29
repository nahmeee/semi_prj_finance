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
	
//	public ArrayList<FinanceVO> sumStatementList()  {
//    	DBManager dbm = OracleDBManager.getInstance();  	//new OracleDBManager();
//		Connection conn = dbm.connect();
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		ArrayList<FinanceVO> fList = new ArrayList<FinanceVO>();
//		try {
//			String query = "SELECT A.PARENT_TYPE, A.ACCOUNT_ID, A.ACCOUNT_NAME, SUM(J.DEBIT) - SUM(J.CREDIT) AS DIFF\r\n"
//                    + " FROM ACCOUNTS A LEFT JOIN VOUCHER J ON A.ACCOUNT_ID = J.ACCOUNT_ID\r\n"
//                    + " GROUP BY A.PARENT_TYPE, A.ACCOUNT_ID, A.ACCOUNT_NAME\r\n"
//                    + " ORDER BY A.PARENT_TYPE, A.ACCOUNT_ID";
//        	System.out.println(query);
//        	
//			pstmt = conn.prepareStatement(query);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//            	FinanceVO vo = new FinanceVO();
//            	vo.setParent_type(rs.getString("PARENT_TYPE"));
//            	vo.setAccount_id(rs.getString("ACCOUNT_ID"));
//            	vo.setAccount_name(rs.getString("ACCOUNT_NAME"));
//            	vo.setDiff(rs.getInt("DIFF"));
//            	fList.add(vo);
//            }
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}	finally {
//				dbm.close(conn, pstmt, rs);
//		}
//		return fList;
//    }
	

	public ArrayList<FinanceVO> sumStatementList()  {
    	DBManager dbm = OracleDBManager.getInstance();  	//new OracleDBManager();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<FinanceVO> fList = new ArrayList<FinanceVO>();
		try {
			String query =   "SELECT " +
	                 "CASE " +
	                 "WHEN GROUPING(A.PARENT_TYPE) = 1 THEN A.PARENT_TYPE || ' 총합' " +
	                 "ELSE A.PARENT_TYPE " +
	                 "END AS PARENT_TYPE, " +
	                 "CASE " +
	                 "WHEN GROUPING(A.ACCOUNT_TYPE) = 1 AND GROUPING(A.PARENT_TYPE) = 0 THEN '총계' " +
	                 "        WHEN GROUPING(A.ACCOUNT_TYPE) = 0 THEN A.ACCOUNT_TYPE " +
	                 "        ELSE NULL " +
	                 "    END AS ACCOUNT_TYPE, " +
	                 "    SUM(NVL(V.DEBIT, 0) - NVL(V.CREDIT, 0)) AS DIFF " +
	                 "FROM ACCOUNTS A " +
	                 "JOIN VOUCHER V ON A.ACCOUNT_ID = V.ACCOUNT_ID " +
	                  "WHERE " +
	                 "    A.PARENT_TYPE IN ('자산', '부채', '자본') " +
	                 "GROUP BY " +
	                "    ROLLUP(A.PARENT_TYPE, A.ACCOUNT_TYPE) " +
	                "HAVING " +
	               "    NOT (GROUPING(A.PARENT_TYPE) = 1 AND GROUPING(A.ACCOUNT_TYPE) = 1) " +
	               "ORDER BY " +
	               "CASE " +
	               "WHEN A.PARENT_TYPE = '자산' THEN 1 " +
	                "WHEN A.PARENT_TYPE = '부채' THEN 2 " +
	               "        WHEN A.PARENT_TYPE = '자본' THEN 3 " +
	               "        ELSE 4 " +
	               "END, " +
	               "CASE " +
	               "     WHEN A.ACCOUNT_TYPE = '유동자산' THEN 1 " +
	               "     WHEN A.ACCOUNT_TYPE = '비유동자산' THEN 2 " +
	               "     WHEN A.ACCOUNT_TYPE = '유동부채' THEN 3 " +
	               "     WHEN A.ACCOUNT_TYPE = '비유동부채' THEN 4 " +
	               "     WHEN A.ACCOUNT_TYPE = '기본자본' THEN 5 " +
	               "     WHEN A.ACCOUNT_TYPE = '기타자본' THEN 6 " +
	               "     ELSE 7 " +
	               "END";
        	System.out.println(query);
        	
			pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	FinanceVO vo = new FinanceVO();
            	vo.setParent_type(rs.getString("PARENT_TYPE"));
            	vo.setAccount_type(rs.getString("ACCOUNT_TYPE"));
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
	
	public ArrayList<FinanceVO> sumIncomeList()  {
    	DBManager dbm = OracleDBManager.getInstance();  	//new OracleDBManager();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<FinanceVO> fList = new ArrayList<FinanceVO>();
		try {
			String query = "SELECT A.PARENT_TYPE, A.ACCOUNT_ID, A.ACCOUNT_NAME, SUM(J.DEBIT) - SUM(J.CREDIT) AS DIFF\r\n"
							+ "	FROM ACCOUNTS A LEFT JOIN VOUCHER J ON A.ACCOUNT_ID = J.ACCOUNT_ID\r\n"
							+ " WHERE A.PARENT_TYPE IN ('수익', '비용')"
							+ "	GROUP BY A.PARENT_TYPE, A.ACCOUNT_ID, A.ACCOUNT_NAME\r\n"
							+ "	ORDER BY \r\n"
							+ "    CASE \r\n"
							+ "        WHEN A.PARENT_TYPE = '수익' THEN 1\r\n"
							+ "        WHEN A.PARENT_TYPE = '비용' THEN 2\r\n"
							+ "        ELSE 4 -- 기타 값\r\n"
							+ "    END, A.ACCOUNT_ID";
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
