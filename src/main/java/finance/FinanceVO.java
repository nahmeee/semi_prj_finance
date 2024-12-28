package finance;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceVO {

	private String account_id;
	private String account_name;
	private String account_type;
	private String parent_type;
	private int diff;
	
	private String voucher_date;
	private String descript;
	private int debit;
	private int credit;
	
	
}



